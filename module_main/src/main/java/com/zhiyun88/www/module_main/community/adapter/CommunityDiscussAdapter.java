package com.zhiyun88.www.module_main.community.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wb.baselib.adapter.ListBaseAdapter;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.bean.DiscussListBean;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_main_discuss, null);
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
            viewHolder.tvDing = convertView.findViewById(R.id.discuss_ding);
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
            /*SpannableStringBuilder sb = new SpannableStringBuilder();
            String title = discussListBean.getTitle();
            title = "  "+title;
            sb.append(title);
            Drawable d = context.getResources().getDrawable(R.drawable.ding);
            d.setBounds(0, 0,d.getMinimumWidth()+8, d.getMinimumHeight()+8);//设置图片大小
            sb.setSpan(new ImageSpan(d,ImageSpan.ALIGN_BASELINE), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);*/
            viewHolder.tvDing.setVisibility(View.VISIBLE);
            //回复
            viewHolder.title.setText("     "+discussListBean.getTitle());
        }else {
            viewHolder.tvDing.setVisibility(View.GONE);
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
        viewHolder.group.setText("「"+discussListBean.getGroup_name()+"」");
        viewHolder.partName.setText(discussListBean.getDepartment_name());
        return convertView;
    }
    class ViewHolder{
        ImageView image,tvDing;
        TextView title,read,name,like,comment,time,group,partName;
    }
}
