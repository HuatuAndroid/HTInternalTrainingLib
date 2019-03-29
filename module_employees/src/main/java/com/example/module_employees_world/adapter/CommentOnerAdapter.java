package com.example.module_employees_world.adapter;

import android.app.Activity;
import android.content.Intent;
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
import com.example.module_employees_world.bean.CommentListBean;
import com.example.module_employees_world.ui.CommentDetailctivity;
import com.example.module_employees_world.ui.CommentDialogActivity;
import com.example.module_employees_world.utils.CircleTransform;
import com.example.module_employees_world.utils.RxBusMessageBean;
import com.squareup.picasso.Picasso;
import com.wb.baselib.image.GlideManager;
import com.wb.baselib.utils.ToastUtils;
import com.wb.rxbus.taskBean.RxBus;

import java.util.List;

/**
 * author:LIENLIN
 * date:2019/3/27
 */
public class CommentOnerAdapter extends RecyclerView.Adapter<CommentOnerAdapter.ViewHolder> {

    LayoutInflater inflater;
    Activity context;
    int count;
    private final int FLAG_IS_LAST=0;
    private final int FLAG_NOT_LAST=1;
    List<CommentListBean.ListBean.ParentBean> parentBeanList;

    public CommentOnerAdapter(Activity context, List<CommentListBean.ListBean.ParentBean> parentBeanList, int count) {
        this.context=context;
        this.count=count;
        this.parentBeanList=parentBeanList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case FLAG_IS_LAST:
                view = inflater.inflate(R.layout.post_comment_oner_last_item, parent, false);
                break;
            case FLAG_NOT_LAST:
                view = inflater.inflate(R.layout.post_comment_oner_item, parent, false);
                break;
        }
        return new CommentOnerAdapter.ViewHolder(view,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (position>1){
            holder.tvLast.setText("查看全部"+count+"条回复");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, CommentDetailctivity.class));
                }
            });
        }else {
            final CommentListBean.ListBean.ParentBean parentBean = parentBeanList.get(position);
            if (!TextUtils.isEmpty(parentBean.avatar))
            Picasso.with(context).load(parentBean.avatar).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(holder.ivAvatar);
            holder.tvName.setText(parentBean.userName);
            holder.tvPartName.setText(parentBean.departmentName);
            holder.tvTime.setText(parentBean.createdAt);
            holder.tvZan.setText(parentBean.likeCount+"");
            String content = "回复<font color=\"#007AFF\">" + "@"+parentBean.parent_name + "</font>";
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
                    RxBus.getIntanceBus().post(new RxBusMessageBean(RxBusMessageBean.MessageType.POST_102,parentBean));
                }
            });
            holder.tvReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showToast(context,"回复："+parentBean.userName);
                }
            });
            holder.tvZan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showToast(context,"点赞："+parentBean.userName);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (count>2){
            return 3;
        }else {
            return parentBeanList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position>1){
            return FLAG_IS_LAST;
        }else {
            return FLAG_NOT_LAST;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvPartName,tvName,tvTitle,tvTime,tvZan,tvReply,tvLast;
        private ImageView ivAvatar,ivDel,ivImg,ivGif;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            switch (viewType){
                case FLAG_IS_LAST:
                    tvLast=itemView.findViewById(R.id.tv_oner_last);
                    break;
                case FLAG_NOT_LAST:
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
                    break;
            }



        }
    }
}
