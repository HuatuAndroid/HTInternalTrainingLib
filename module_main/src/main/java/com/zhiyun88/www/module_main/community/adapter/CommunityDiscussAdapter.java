package com.zhiyun88.www.module_main.community.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wb.baselib.adapter.ListBaseAdapter;
import com.wb.baselib.image.GlideManager;
import com.wb.baselib.utils.SpanUtil;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.bean.DiscussListBean;
import com.zhiyun88.www.module_main.main.config.MainConfig;
import com.zhiyun88.www.module_main.utils.CircleTransform;

import java.util.List;

/**
 * 员工天地-帖子列表
 */
public class CommunityDiscussAdapter extends ListBaseAdapter {

    public CommunityDiscussAdapter(Context context, List<DiscussListBean> discussListBeans) {
        super(discussListBeans,context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DiscussListBean discussListBean = (DiscussListBean) getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.main_item_community_discuss, null);
            viewHolder.title = convertView.findViewById(R.id.discuss_title);
            viewHolder.read = convertView.findViewById(R.id.discuss_read);
            //  viewHolder.content = convertView.findViewById(R.id.discuss_content);
            viewHolder.time = convertView.findViewById(R.id.discuss_time);
            viewHolder.group = convertView.findViewById(R.id.discuss_group);
            viewHolder.image = convertView.findViewById(R.id.discuss_image);
            viewHolder.name = convertView.findViewById(R.id.discuss_name);
            viewHolder.like = convertView.findViewById(R.id.discuss_like);
            viewHolder.comment = convertView.findViewById(R.id.discuss_comment);
            viewHolder.partName = convertView.findViewById(R.id.discuss_part);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (discussListBean.getIs_anonymity().equals("1")) {
            viewHolder.name.setText("匿名");
        }else {
            viewHolder.name.setText(discussListBean.getUser_name());
        }

        if (discussListBean.getIs_top().equals("1")) {
            SpannableStringBuilder sb = new SpannableStringBuilder();
            String title = discussListBean.getTitle();
            title = "  "+title;
            sb.append(title);
            Drawable d = context.getResources().getDrawable(R.drawable.ding) ;
            d.setBounds(0, 0,d.getMinimumWidth()+8, d.getMinimumHeight()+8);//设置图片大小
            sb.setSpan(new ImageSpan(d,ImageSpan.ALIGN_BASELINE), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            //回复
            viewHolder.title.setText(sb);
        }else {
            viewHolder.title.setText(discussListBean.getTitle());
        }

        //头像统一由服务器获取
        if (!TextUtils.isEmpty(discussListBean.getAvatar()))
        Picasso.with(context).load(discussListBean.getAvatar()).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(viewHolder.image);
        viewHolder.read.setText(discussListBean.getRead_count());
        //  viewHolder.content.setText(discussListBean.getContent());

//        viewHolder.title.setText(discussListBean.getTitle());
        viewHolder.like.setText(discussListBean.getLike_count());
        viewHolder.comment.setText(discussListBean.getComment_count());
        viewHolder.time.setText(discussListBean.getCreated_at());
        viewHolder.group.setText(discussListBean.getGroup_name());
        viewHolder.partName.setText(discussListBean.getDepartment_name());
        return convertView;
    }
    class ViewHolder{
        ImageView image;
        TextView title,read,name,like,comment,time,group,partName;
    }
}
