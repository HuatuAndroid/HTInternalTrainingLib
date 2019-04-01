package com.example.module_employees_world.ui;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.adapter.CommentChildrenAdapter;
import com.example.module_employees_world.adapter.CommentOnerAdapter;
import com.example.module_employees_world.bean.CommentChildrenBean;
import com.example.module_employees_world.bean.CommentDetailBean;
import com.example.module_employees_world.bean.CommentLikeBean;
import com.example.module_employees_world.contranct.CommentDetailContranct;
import com.example.module_employees_world.presenter.CommentDetailPresenter;
import com.example.module_employees_world.utils.CircleTransform;
import com.example.module_employees_world.utils.RxBusMessageBean;
import com.example.module_employees_world.view.CommontPopw;
import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.image.GlideManager;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.utils.StatusBarUtil;
import com.wb.baselib.utils.ToastUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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
    private List<CommentChildrenBean> commentChildrenList = new ArrayList<>();
    private int page = 1;
    private int limit = 6;
    private MyHandler myHandler;
    private CommentChildrenAdapter commentChildrenAdapter;
    private NestedScrollView svComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusLayout(this, Color.parseColor("#007AFF"));
        StatusBarUtil.StatusBarDarkMode(this, StatusBarUtil.StatusBarLightMode(this));

        commentId = getIntent().getIntExtra(TAG_JUMP, -1);
        mPresenter.commentDetail(commentId);
        mPresenter.commentChildrenList(commentId,page,limit,1);
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
            }
        });
        tvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/3/30 评论回复
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
        CommentDetailBean commentDetailBean = (CommentDetailBean)o;
        Picasso.with(this).load(commentDetailBean.avatar).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(ivAvatar);
        tvName.setText(commentDetailBean.userName);
        tvPartName.setText(commentDetailBean.departmentName);
        tvContent.setText(commentDetailBean.content);
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

    }

    @Override
    public void commentChildrenList(List<CommentChildrenBean> childrenBeans) {
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
        commentChildrenList.remove(position);
        commentChildrenAdapter.notifyDataSetChanged();
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
                    activity.mPresenter.deleteComment(commentId+"",position);
                    activity.showLoadDiaLog("");
                    break;
                case RxBusMessageBean.MessageType.POST_111:
                    // TODO: 2019/3/30 回复子评论
                    commentId= msg.arg1;
                    ToastUtils.showToast(activity,"回复评论"+commentId);
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
