package com.example.module_employees_world.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.bean.CommentListBean;
import com.example.module_employees_world.utils.CircleTransform;
import com.squareup.picasso.Picasso;
import com.wb.baselib.image.GlideManager;
import com.wb.baselib.utils.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * author:LIENLIN
 * date:2019/3/20
 * 帖子详情列表
 */
public class PostDetailAdapter extends RecyclerView.Adapter<PostDetailAdapter.ViewHolder>{

    LayoutInflater inflater;
    Activity context;
    private List<CommentListBean.ListBean> commentList;

    public PostDetailAdapter(Activity context, List<CommentListBean.ListBean> commentList) {
        this.context=context;
        this.commentList=commentList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.post_comment_item, parent, false);
        return new ViewHolder(view,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        CommentListBean.ListBean listBean = commentList.get(position);
        holder.tvName.setText(listBean.userName);
        holder.tvPartName.setText(listBean.departmentName);
        holder.tvCommentTitle.setText(listBean.content);
        holder.tvCommentTime.setText(listBean.createdAt);
        holder.tvCommentZan.setText(listBean.likeCount+"");
        Picasso.with(context).load(listBean.avatar).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(holder.ivAvatar);
        if (!TextUtils.isEmpty(listBean.commentPicture)){
            holder.ivCommentImg.setVisibility(View.VISIBLE);
            GlideManager.getInstance().setCommonPhoto(holder.ivCommentImg, R.drawable.course_image ,context , listBean.commentPicture ,false );
        }else {
            holder.ivCommentImg.setVisibility(View.GONE);
        }

        holder.ivCommentDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(context,"删除："+position);
            }
        });
        holder.tvCommentReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(context,"回复："+position);
            }
        });

        holder.rvOnerComment.setNestedScrollingEnabled(false);
        holder.rvOnerComment.setLayoutManager(new LinearLayoutManager(context));
        holder.rvOnerComment.setAdapter(new CommentOnerAdapter(context,listBean.parent,listBean.count));

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private RecyclerView rvOnerComment;
        private TextView tvPartName,tvName,tvCommentTitle,tvCommentTime,tvCommentZan,tvCommentReply;
        private ImageView ivAvatar,ivCommentDel,ivCommentImg,ivCommentGif;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            tvName = itemView.findViewById(R.id.comment_name);
            ivAvatar = itemView.findViewById(R.id.comment_image);
            tvPartName = itemView.findViewById(R.id.comment_part);
            ivCommentDel = itemView.findViewById(R.id.iv_conmment_del);
            tvCommentTitle = itemView.findViewById(R.id.comment_title);
            ivCommentImg = itemView.findViewById(R.id.iv_comment_img);
            ivCommentGif = itemView.findViewById(R.id.iv_comment_gif);
            tvCommentTime = itemView.findViewById(R.id.tv_comment_time);
            tvCommentZan = itemView.findViewById(R.id.tv_comment_zan);
            tvCommentReply = itemView.findViewById(R.id.tv_comment_reply);
            rvOnerComment = itemView.findViewById(R.id.rv_oner_comment);
        }
    }
}
