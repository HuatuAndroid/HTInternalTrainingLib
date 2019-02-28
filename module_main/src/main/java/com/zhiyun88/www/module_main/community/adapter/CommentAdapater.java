package com.zhiyun88.www.module_main.community.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wb.baselib.image.GlideManager;
import com.wb.baselib.view.MyListView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.bean.DetailsCommentListBean;
import com.zhiyun88.www.module_main.community.config.CommunityConfig;
import com.zhiyun88.www.module_main.community.ui.TopicDetailsActivity;
import com.zhiyun88.www.module_main.utils.CircleTransform;

import java.util.List;

/**
 * 帖子详情，帖子回复列表
 */
public class CommentAdapater extends BaseAdapter {
    private Context mContext;
    private List<DetailsCommentListBean> listBeans;
    private CommunityConfig.OnReplyListener onReplyListener;

    public CommentAdapater(Context context, List<DetailsCommentListBean> listBeans) {
        this.mContext = context;
        this.listBeans = listBeans;
    }

    @Override
    public int getCount() {
        return listBeans.size();
    }

    @Override
    public DetailsCommentListBean getItem(int position) {
        return listBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final DetailsCommentListBean listBean = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_comment, null);
            viewHolder.comment_title = convertView.findViewById(R.id.comment_title);
            viewHolder.comment_time = convertView.findViewById(R.id.tv_comment_time);
            viewHolder.comment_name = convertView.findViewById(R.id.comment_name);
            viewHolder.comment_reply = convertView.findViewById(R.id.tv_comment_reply);
            viewHolder.comment_image = convertView.findViewById(R.id.comment_image);
            viewHolder.iv_conmment_del = convertView.findViewById(R.id.iv_conmment_del);
            viewHolder.reply_rl = convertView.findViewById(R.id.reply_rl);
            viewHolder.tvCommentPart = convertView.findViewById(R.id.comment_part);
            viewHolder.reply_content = convertView.findViewById(R.id.reply_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (listBean.getIs_anonymity().equals("1")) {
            viewHolder.comment_name.setText("匿名");
        } else {
            viewHolder.comment_name.setText(listBean.getUser_name());
        }
        //头像统一由后台获取
        if (!TextUtils.isEmpty(listBean.getAvatar()))
        Picasso.with(mContext).load(listBean.getAvatar()).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(viewHolder.comment_image);
        viewHolder.comment_title.setText(listBean.getContent());
        viewHolder.comment_time.setText(listBean.getCreated_at());
        viewHolder.tvCommentPart.setText(listBean.getDepartment_name());

        if (listBean.getParent() == null) {
            viewHolder.reply_rl.setVisibility(View.GONE);
        } else {
            viewHolder.reply_rl.setVisibility(View.VISIBLE);
            String content;
            if (listBean.getParent().getIs_anonymity()!=null){
                String replyName=listBean.getParent().getIs_anonymity().equals("1")?"匿名":listBean.getParent().getUser_name();
                content="<font color=\"#007AFF\">"+"@"+replyName+":  "+"</font>"+listBean.getParent().getContent();
            }else {
                content=listBean.getParent().getContent();
            }

            viewHolder.reply_content.setText(Html.fromHtml(content));
        }

        if (listBean.getAllow_del()==1)
            viewHolder.iv_conmment_del.setVisibility(View.VISIBLE);
        else
            viewHolder.iv_conmment_del.setVisibility(View.GONE);

        viewHolder.comment_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReplyListener == null) return;
                onReplyListener.setReplyClick(position);
            }
        });

        viewHolder.iv_conmment_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReplyListener == null) return;
                onReplyListener.setDeleteConmment(listBean.getId(),position);
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView comment_title, comment_time, comment_name, comment_reply, reply_content,tvCommentPart;
        ImageView comment_image,iv_conmment_del;
        LinearLayout reply_rl;
    }

    public void setOnReplyListener(CommunityConfig.OnReplyListener onReplyListener) {
        this.onReplyListener = onReplyListener;
    }
}
