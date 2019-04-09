package com.example.module_employees_world.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.bean.DiscussListBean;
import com.example.module_employees_world.utils.CircleTransform;
import com.squareup.picasso.Picasso;
import com.wb.baselib.adapter.ListBaseAdapter;

import java.util.List;

/**
 * 员工天地-帖子列表
 */
public class CommunityDiscussAdapter extends ListBaseAdapter {

    private String type;//1热门 2最新

    public CommunityDiscussAdapter(Context context, String type, List<DiscussListBean> discussListBeans) {
        super(discussListBeans, context);
        this.type = type;
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
            viewHolder.vLine = convertView.findViewById(R.id.vLine);
            viewHolder.discussDing = convertView.findViewById(R.id.discussDing);
            viewHolder.discussTitle = convertView.findViewById(R.id.discussTitle);
            viewHolder.llDing = convertView.findViewById(R.id.llDing);
            viewHolder.llNoDing = convertView.findViewById(R.id.llNoDing);
            viewHolder.rlHead = convertView.findViewById(R.id.rlHead);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (discussListBean.getIs_anonymity().equals("1")) {
            viewHolder.name.setText("匿名");
        } else {
            viewHolder.name.setText(discussListBean.getUser_name());
        }

        if (discussListBean.getIs_top().equals("1") && "2".equals(type)) {
            /*SpannableStringBuilder sb = new SpannableStringBuilder();
            String title = discussListBean.getTitle();
            title = "  "+title;
            sb.append(title);
            Drawable d = context.getResources().getDrawable(R.drawable.ding);
            d.setBounds(0, 0,d.getMinimumWidth()+8, d.getMinimumHeight()+8);//设置图片大小
            sb.setSpan(new ImageSpan(d,ImageSpan.ALIGN_BASELINE), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);*/
            //viewHolder.tvDing.setVisibility(View.VISIBLE);
            //回复
            viewHolder.llDing.setVisibility(View.VISIBLE);
            viewHolder.llNoDing.setVisibility(View.GONE);
            viewHolder.rlHead.setVisibility(View.GONE);
            viewHolder.discussTitle.setText(discussListBean.getTitle());
            if (position + 1 < getCount() && "1".equals(((DiscussListBean) getItem(position + 1)).getIs_top())) {
                viewHolder.vLine.setVisibility(View.GONE);
            } else {
                viewHolder.vLine.setVisibility(View.VISIBLE);
            }
        } else {
            viewHolder.llDing.setVisibility(View.GONE);
            viewHolder.llNoDing.setVisibility(View.VISIBLE);
            viewHolder.rlHead.setVisibility(View.VISIBLE);
            String type = "";
            if (discussListBean.getType() == 2) {
                type = "【建议】";
            } else if (discussListBean.getType() == 3) {
                type = "【问题】";
            }
            viewHolder.title.setText(type + discussListBean.getTitle());
            //头像统一由服务器获取
            if (!TextUtils.isEmpty(discussListBean.getAvatar())) {
                Picasso.with(context).load(discussListBean.getAvatar()).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(viewHolder.image);
            }
            viewHolder.read.setText(discussListBean.getRead_count());
            //  viewHolder.content.setText(discussListBean.getContent());

//        viewHolder.title.setText(discussListBean.getTitle());
            viewHolder.like.setText(discussListBean.getLike_count());
            viewHolder.comment.setText(discussListBean.getComment_count());
            if (discussListBean.getType() == 1) {
                viewHolder.time.setText(discussListBean.getCreated_at());
            } else {
                if (discussListBean.getType() == 2) {
                    String status = " | 未采纳";
                    if (discussListBean.getSolve_status() == 1) {
                        status = " | 已采纳";
                    }
                    viewHolder.time.setText(discussListBean.getCreated_at() + status);
                } else {
                    String status = " | 未解决";
                    if (discussListBean.getSolve_status() == 1) {
                        status = " | 已解决";
                    }
                    viewHolder.time.setText(discussListBean.getCreated_at() + status);
                }
            }
            viewHolder.group.setText("【" + discussListBean.getGroup_name() + "】");
            viewHolder.partName.setText(discussListBean.getDepartment_name());
        }

        return convertView;
    }

    class ViewHolder {
        ImageView image, tvDing, discussDing;
        TextView discussTitle, title, read, name, like, comment, time, group, partName;
        LinearLayout llDing, llNoDing;
        View vLine;
        RelativeLayout rlHead;
    }
}
