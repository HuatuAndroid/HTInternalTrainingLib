package com.example.module_employees_world.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.module_employees_world.R;
import com.example.module_employees_world.adapter.CommentChildrenAdapter;
import com.example.module_employees_world.bean.CommentDetailBean;
import com.example.module_employees_world.bean.CommentInsertBean;
import com.example.module_employees_world.bean.CommentLikeBean;
import com.example.module_employees_world.bean.ParentBean;
import com.example.module_employees_world.common.TutuPicInit;
import com.example.module_employees_world.contranct.CommentDetailContranct;
import com.example.module_employees_world.presenter.CommentDetailPresenter;
import com.example.module_employees_world.utils.CircleTransform;
import com.example.module_employees_world.utils.EmojiUtils;
import com.example.module_employees_world.utils.RxBusMessageBean;
import com.example.module_employees_world.view.CommontPopw;
import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.http.HttpConfig;
import com.wb.baselib.image.GlideManager;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.utils.StatusBarUtilNeiXun;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.wb.rxbus.taskBean.RxBus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * author:LIENLIN
 * date:2019/3/27
 * 评论详情
 */
public class CommentDetailctivity extends MvpActivity<CommentDetailPresenter> implements CommentDetailContranct.CommentDetialView{

    public static final String TAG_JUMP="CommentDetailctivity";
    private int commentId;
    private TopBarView topBarView;
    private SmartRefreshLayout refreshLayout;
    private ImageView ivAvatar;
    private ImageView ivDel;
    private ImageView ivImg;
    private ImageView ivGif;
    private TextView tvName;
    private TextView tvPartName;
    private TextView tvContent;
    private TextView tvTime;
    private TextView tvZan;
    private TextView tvReply;
    private TextView tvChildrenNum;
    private TextView tvBottomReply;
    private TextView tvBottomSend;
    private MultipleStatusView multiplsStatusView;
    private RecyclerView rvCommentDetail;
    private List<ParentBean> commentChildrenList = new ArrayList<>();
    private int page = 1;
    private int limit = 6;
    private MyHandler myHandler;
    private CommentChildrenAdapter commentChildrenAdapter;
    private NestedScrollView svComment;
    private CommentDetailBean commentDetailBean;
    private CommontPopw commontPopw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtilNeiXun.setStatusLayout(this, Color.parseColor("#007AFF"));
        StatusBarUtilNeiXun.StatusBarDarkMode(this, StatusBarUtilNeiXun.StatusBarLightMode(this));

        commentId = getIntent().getIntExtra(TAG_JUMP, -1);
        mPresenter.commentDetail(commentId);
        mPresenter.commentChildrenList(commentId,page,limit,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        myHandler = new MyHandler(this);
        setContentView(R.layout.activity_comment_detailctivity);
        topBarView = findViewById(R.id.topbar_comment_detail);
        refreshLayout = findViewById(R.id.refresh_layout);
        svComment = findViewById(R.id.scv_comment);
        ivAvatar = findViewById(R.id.comment_detail_avatar);
        ivDel = findViewById(R.id.iv_comment_detail_del);
        ivImg = findViewById(R.id.iv_comment_detail_img);
        ivGif = findViewById(R.id.iv_comment_detail_gif);
        tvName = findViewById(R.id.tv_comment_detail_name);
        tvPartName = findViewById(R.id.tv_comment_detail_part);
        tvContent = findViewById(R.id.tv_comment_detail_title);
        tvTime = findViewById(R.id.tv_comment_detail_time);
        tvZan = findViewById(R.id.tv_comment_detail_zan);
        tvReply = findViewById(R.id.tv_comment_detail_reply);
        tvChildrenNum = findViewById(R.id.tv_comment_detail_num);
        tvBottomReply = findViewById(R.id.tv_comment_detail_bottom_reply);
        tvBottomSend = findViewById(R.id.tv_comment_detail_send);
        multiplsStatusView = findViewById(R.id.comment_multipleStatusview);
        rvCommentDetail = findViewById(R.id.rv_post_detail);

        RefreshUtils.getInstance(refreshLayout,this ).defaultRefreSh();
        refreshLayout.setEnableRefresh(false);

        rvCommentDetail.setNestedScrollingEnabled(false);
        rvCommentDetail.setLayoutManager(new LinearLayoutManager(this));
        commentChildrenAdapter = new CommentChildrenAdapter(this, commentChildrenList, myHandler);
        rvCommentDetail.setAdapter(commentChildrenAdapter);

    }

    @Override
    protected CommentDetailPresenter onCreatePresenter() {
        return new CommentDetailPresenter(this);
    }

    @Override
    protected void setListener() {
        topBarView.getLeftImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/3/30 删除评论
                commontPopw = new CommontPopw(CommentDetailctivity.this, "删除评论后，评论下所有回复都会被删除。", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.deleteComment(commentId+"",-1);
                        showLoadDiaLog("");
                        commontPopw.myDismiss();
                    }
                });
            }
        });
        tvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/3/30 评论回复
                if (AppUtils.is_banned == 0) {
                    //发帖
                    if (commentDetailBean!=null){
                        Intent intent = new Intent(CommentDetailctivity.this, CommentDialogActivity.class);
                        intent.putExtra(CommentDialogActivity.TAG_QUESTION_ID,commentDetailBean.questionId+"");
                        intent.putExtra(CommentDialogActivity.TAG_COMMENT_ID,commentDetailBean.id+"");
                        intent.putExtra(CommentDialogActivity.TAG_COMMENT_NAME,commentDetailBean.userName);
                        startActivity(intent);
                    }
                }else{
                    showShortToast("你已被禁言");
                }

            }
        });
        tvBottomReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/3/30 评论回复

                if (AppUtils.is_banned == 0) {
                    //发帖
                    if (commentDetailBean!=null){
                        Intent intent = new Intent(CommentDetailctivity.this, CommentDialogActivity.class);
                        intent.putExtra(CommentDialogActivity.TAG_QUESTION_ID,commentDetailBean.questionId+"");
                        intent.putExtra(CommentDialogActivity.TAG_COMMENT_ID,commentDetailBean.id+"");
                        intent.putExtra(CommentDialogActivity.TAG_COMMENT_NAME,commentDetailBean.userName);
                        startActivity(intent);
                    }
                }else{
                    showShortToast("你已被禁言");
                }

            }
        });
        tvBottomSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/3/30 发布回复
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.commentChildrenList(commentId,page,limit,1);
            }
        });
        tvZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.commentLike(commentId+"",tvZan);
                showLoadDiaLog("");
            }
        });

        RxBus.getIntanceBus().registerRxBus(RxBusMessageBean.class, new Consumer<RxBusMessageBean>() {
            @Override
            public void accept(RxBusMessageBean rxMessageBean) throws Exception {
                if (rxMessageBean.getMessageCode() == RxBusMessageBean.MessageType.POST_114){
                    CommentInsertBean insertBean = (CommentInsertBean) rxMessageBean.getMessage();
                    String comment_id = (String) rxMessageBean.getMessage1();
                    if ("0".equals(comment_id)){
                        //一级评论
                    }else {
                        //二级评论
                        commentChildrenList.add(insertBean.second);
                    }
                    commentChildrenAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {}

    @Override
    public void ShowLoadView() {
        multiplsStatusView.showLoading();
    }

    @Override
    public void NoNetWork() {
        multiplsStatusView.showNoNetwork();
    }

    @Override
    public void NoData() {
        multiplsStatusView.showEmpty();
    }

    @Override
    public void ErrorData() {
        multiplsStatusView.showError();
    }

    @Override
    public void showErrorMsg(String msg) {
        showShortToast(msg);
        hidLoadDiaLog();
    }

    @Override
    public void showLoadV(String msg) {}

    @Override
    public void closeLoadV() {}

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void SuccessData(Object o) {
        //评论详情
        commentDetailBean = (CommentDetailBean)o;
        Picasso.with(this).load(commentDetailBean.avatar).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(ivAvatar);
        tvName.setText(commentDetailBean.userName);
        tvPartName.setText(commentDetailBean.departmentName);
        tvContent.setText(EmojiUtils.decode(commentDetailBean.content));
        tvTime.setText(commentDetailBean.createdAt);
        tvZan.setText(commentDetailBean.likeCount+"");
        tvChildrenNum.setText(commentDetailBean.count+"条回复");
        // 0不可以 1可以删除
        if (commentDetailBean.allowDel == 1){
            ivDel.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(commentDetailBean.commentPicture)){
            ivImg.setVisibility(View.VISIBLE);
            GlideManager.getInstance().setCommonPhoto(ivImg, R.drawable.course_image ,this , commentDetailBean.commentPicture ,false );
        }else {
            ivImg.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(commentDetailBean.commentFace)){
            ivGif.setVisibility(View.VISIBLE);
            GlideManager.getInstance().setGlideResourceImage(ivGif, TutuPicInit.getResFromEmojicList(commentDetailBean.commentFace),R.drawable.image_failure, R.drawable.course_image ,this);
        }else {
            ivGif.setVisibility(View.GONE);
        }
        //1已经点赞 0没有点赞
        if (commentDetailBean.commentLike==0){
            Drawable drawable = getResources().getDrawable(R.drawable.post_comment_zan);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            tvZan.setCompoundDrawables(drawable,null,null,null);
        }else {
            Drawable drawable = getResources().getDrawable(R.drawable.post_comment_zan_able);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            tvZan.setCompoundDrawables(drawable,null,null,null);
        }
    }

    @Override
    public void commentChildrenList(List<ParentBean> childrenBeans) {
        if (page==1){
            commentChildrenList.clear();
        }
        commentChildrenList.addAll(childrenBeans);
        commentChildrenAdapter.notifyDataSetChanged();
        multiplsStatusView.showContent();
        page++;
    }

    @Override
    public void commentLike(CommentLikeBean commentLikeBean, TextView tvZan) {
        hidLoadDiaLog();
        int integer = Integer.valueOf(tvZan.getText().toString());
        if (commentLikeBean.resultType==1){
            //取消成功
            tvZan.setText(--integer+"");
            Drawable drawable = getResources().getDrawable(R.drawable.post_comment_zan);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            tvZan.setCompoundDrawables(drawable,null,null,null);

        }else if (commentLikeBean.resultType==2){
            //点赞成功
            tvZan.setText(++integer+"");
            Drawable drawable = getResources().getDrawable(R.drawable.post_comment_zan_able);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            tvZan.setCompoundDrawables(drawable,null,null,null);
        }
    }

    @Override
    public void deleteComment(int position) {
        hidLoadDiaLog();
        if (position==-1){
            //删除该评论
            finish();
        }else {
            //删除子评论
            commentChildrenList.remove(position);
            commentChildrenAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void isLoadMore(boolean isLoadMore) {
        RefreshUtils.getInstance(refreshLayout, this).isLoadData(isLoadMore);
    }

    public static class MyHandler extends Handler {

        private final WeakReference<CommentDetailctivity> weakReference;

        public MyHandler(CommentDetailctivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            int commentId;
            CommentDetailctivity activity = weakReference.get();
            switch (what){
                case RxBusMessageBean.MessageType.POST_110:
                    // : 2019/3/30 删除子评论
                    commentId= msg.arg1;
                    int position=msg.arg2;
                    activity.commontPopw = new CommontPopw(activity, "确定删除这条回复吗?", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            activity.mPresenter.deleteComment(commentId+"",position);
                            activity.showLoadDiaLog("");
                            activity.commontPopw.myDismiss();
                        }
                    });
                   break;
                case RxBusMessageBean.MessageType.POST_111:
                    // 2019/3/30 回复子评论
                    if (AppUtils.is_banned == 0) {
                        //发帖
                        ParentBean parentBean= (ParentBean) msg.obj;
                        Intent intent = new Intent(activity, CommentDialogActivity.class);
                        intent.putExtra(CommentDialogActivity.TAG_QUESTION_ID,parentBean.questionId+"");
                        intent.putExtra(CommentDialogActivity.TAG_COMMENT_ID,parentBean.id+"");
                        intent.putExtra(CommentDialogActivity.TAG_COMMENT_NAME,parentBean.userName);
                        activity.startActivity(intent);
                        activity.startActivity(intent);
                    }else{
                        Toast.makeText(AppUtils.getContext(), "你已被禁言", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case RxBusMessageBean.MessageType.POST_112:
                    // : 2019/3/30 子评论点赞
                    TextView tvZan= (TextView) msg.obj;
                    commentId= msg.arg1;
                    activity.mPresenter.commentLike(commentId+"",tvZan);
                    activity.showLoadDiaLog("");
                    break;

            }
        }
    }

}
