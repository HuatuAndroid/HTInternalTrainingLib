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
import com.example.module_employees_world.ui.PostsDetailActivity;
import com.example.module_employees_world.utils.CircleTransform;
import com.example.module_employees_world.utils.RxBusMessageBean;
import com.squareup.picasso.Picasso;
import com.wb.baselib.http.HttpConfig;
import com.wb.baselib.image.GlideManager;
import com.wb.baselib.utils.ToastUtils;
import com.wb.rxbus.taskBean.RxBus;
import com.wb.rxbus.taskBean.RxMessageBean;

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
    PostsDetailActivity context;
    PostsDetailActivity.MyHandler myHandler;
    private List<CommentListBean.ListBean> commentList;

    public PostDetailAdapter(PostsDetailActivity context, List<CommentListBean.ListBean> commentList, PostsDetailActivity.MyHandler myHandler) {
        this.context=context;
        this.commentList=commentList;
        this.myHandler=myHandler;
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
            GlideManager.getInstance().setCommonPhoto(holder.ivCommentImg, R.drawable.course_image ,context , HttpConfig.newInstance().getmBaseUrl()+"/"+ listBean.commentPicture ,false );
        }else {
            holder.ivCommentImg.setVisibility(View.GONE);
        }
        // TODO: 2019/3/29 点赞状态
//        if (listBean.is)

        //删除权限  0：无权限   1：有权限
        if (listBean.allowDel==0){
            holder.ivCommentDel.setVisibility(View.GONE);
        }else {
            holder.ivCommentDel.setVisibility(View.VISIBLE);
        }
        holder.ivCommentDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what=RxBusMessageBean.MessageType.POST_101;
                message.arg1=listBean.id;
                message.arg1=position;
                myHandler.handleMessage(message);
//                RxBus.getIntanceBus().post(new RxBusMessageBean(RxBusMessageBean.MessageType.POST_101,listBean.id+"",position));
            }
        });

        //帖子类型 1交流 2建议 3提问
        if (listBean.type==1||listBean.type==3){
            holder.ivChecked.setVisibility(View.GONE);
        }else if (listBean.type==2){
            // 0 未解决，未采纳 1已解决，已采纳
            if (listBean.solveStatus==0){
                holder.ivChecked.setVisibility(View.VISIBLE);
            }else {
                holder.ivChecked.setVisibility(View.GONE);
            }
        }
        holder.ivChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what=RxBusMessageBean.MessageType.POST_103;
                message.obj=listBean;
                myHandler.handleMessage(message);
//                RxBus.getIntanceBus().post(new RxBusMessageBean(RxBusMessageBean.MessageType.POST_103,listBean));
            }
        });
        holder.tvCommentReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what=RxBusMessageBean.MessageType.POST_109;
                message.arg1=listBean.id;
                myHandler.handleMessage(message);
            }
        });
        holder.tvCommentZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what=RxBusMessageBean.MessageType.POST_106;
                message.obj=holder.tvCommentZan;
                message.arg1=listBean.id;
                myHandler.handleMessage(message);
//                RxBus.getIntanceBus().post(new RxBusMessageBean(RxBusMessageBean.MessageType.POST_106,holder.tvCommentZan,listBean.id));
            }
        });

        holder.rvOnerComment.setNestedScrollingEnabled(false);
        holder.rvOnerComment.setLayoutManager(new LinearLayoutManager(context));
        holder.rvOnerComment.setAdapter(new CommentOnerAdapter(context,listBean.parent,listBean.count,position,myHandler));

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
        private ImageView ivAvatar,ivCommentDel,ivCommentImg,ivCommentGif,ivChecked;

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
            ivChecked = itemView.findViewById(R.id.iv_conmment_check);
        }
    }
}