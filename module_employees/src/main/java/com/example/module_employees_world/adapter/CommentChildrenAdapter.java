package com.example.module_employees_world.adapter;

import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.bean.CommentChildrenBean;
import com.example.module_employees_world.bean.ParentBean;
import com.example.module_employees_world.ui.CommentDetailctivity;
import com.example.module_employees_world.ui.PostsDetailActivity;
import com.example.module_employees_world.utils.CircleTransform;
import com.example.module_employees_world.utils.RxBusMessageBean;
import com.squareup.picasso.Picasso;
import com.wb.baselib.image.GlideManager;

import java.util.List;

/**
 * author:LIENLIN
 * date:2019/3/27
 */
public class CommentChildrenAdapter extends RecyclerView.Adapter<CommentChildrenAdapter.ViewHolder> {

    LayoutInflater inflater;
    CommentDetailctivity context;
    List<CommentChildrenBean> parentBeanList;
    CommentDetailctivity.MyHandler myHandler;

    public CommentChildrenAdapter(CommentDetailctivity context, List<CommentChildrenBean> parentBeanList, CommentDetailctivity.MyHandler myHandler) {
        this.context=context;
        this.parentBeanList=parentBeanList;
        this.myHandler=myHandler;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.post_comment_children_item, parent, false);
        return new CommentChildrenAdapter.ViewHolder(view,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final CommentChildrenBean parentBean = parentBeanList.get(position);
        if (!TextUtils.isEmpty(parentBean.avatar))
        Picasso.with(context).load(parentBean.avatar).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(holder.ivAvatar);
        holder.tvName.setText(parentBean.userName);
        holder.tvPartName.setText(parentBean.departmentName);
        holder.tvTime.setText(parentBean.createdAt);
        holder.tvZan.setText(parentBean.likeCount+"");
        String content = "回复<font color=\"#007AFF\">" + "@"+parentBean.parentName + "</font>";
        holder.tvTitle.setText(Html.fromHtml(content+parentBean.content));

        if (!TextUtils.isEmpty(parentBean.commentPicture)){
            holder.ivImg.setVisibility(View.VISIBLE);
            GlideManager.getInstance().setCommonPhoto(holder.ivImg, R.drawable.course_image ,context , parentBean.commentPicture ,false );
        }else {
            holder.ivImg.setVisibility(View.GONE);
        }

        //删除权限  0：无权限   1：有权限
        if (parentBean.allowDel==0){
            holder.ivDel.setVisibility(View.GONE);
        }else {
            holder.ivDel.setVisibility(View.VISIBLE);
        }
        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what=RxBusMessageBean.MessageType.POST_110;
                message.arg1=parentBean.id;
                message.arg2=position;
                myHandler.handleMessage(message);
            }
        });
        holder.tvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what=RxBusMessageBean.MessageType.POST_111;
                message.arg1=parentBean.id;
                myHandler.handleMessage(message);
            }
        });
        holder.tvZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what=RxBusMessageBean.MessageType.POST_112;
                message.obj=holder.tvZan;
                message.arg1=parentBean.id;
                myHandler.handleMessage(message);
            }
        });
    }

    @Override
    public int getItemCount() {
        return parentBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvPartName,tvName,tvTitle,tvTime,tvZan,tvReply;
        private ImageView ivAvatar,ivDel,ivImg,ivGif;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
                ivAvatar=itemView.findViewById(R.id.iv_oner_image);
                tvName=itemView.findViewById(R.id.tv_oner_name);
                tvPartName=itemView.findViewById(R.id.tv_oner_part);
                ivDel=itemView.findViewById(R.id.iv_oner_del);
                tvTitle=itemView.findViewById(R.id.tv_oner_title);
                ivImg=itemView.findViewById(R.id.iv_oner_img);
                ivGif=itemView.findViewById(R.id.iv_oner_gif);
                tvTime=itemView.findViewById(R.id.tv_oner_time);
                tvZan=itemView.findViewById(R.id.tv_oner_zan);
                tvReply=itemView.findViewById(R.id.tv_oner_reply);



        }
    }
}
