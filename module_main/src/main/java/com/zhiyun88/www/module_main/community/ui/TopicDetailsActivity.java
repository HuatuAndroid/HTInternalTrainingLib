package com.zhiyun88.www.module_main.community.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;



import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle2.LifecycleTransformer;

import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.MyListView;
import com.wb.baselib.view.TopBarView;
import com.wb.rxbus.taskBean.RxBus;
import com.wb.rxbus.taskBean.RxMessageBean;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.adapter.CommentAdapater;
import com.zhiyun88.www.module_main.community.bean.DetailsCommentListBean;
import com.zhiyun88.www.module_main.community.bean.QuestionInfoBean;
import com.zhiyun88.www.module_main.community.config.CommunityConfig;
import com.zhiyun88.www.module_main.community.mvp.contranct.CommunityDetailsContranct;
import com.zhiyun88.www.module_main.community.mvp.presenter.CommunityDetailsPresenter;
import com.zhiyun88.www.module_main.community.view.CustomDialog;
import com.zhiyun88.www.module_main.utils.CircleTransform;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class TopicDetailsActivity extends MvpActivity<CommunityDetailsPresenter> implements CommunityDetailsContranct.CommunityDetailsView {

    private TopBarView topBarView;
    private ImageView like, headImage;
    private TextView comment_count, text, htmlTextView, details_name, details_time, details_browse;
    private String question_id;
    private MyListView details_list;
    private CommentAdapater mAdapter;
    private MultipleStatusView multiplestatusview;
    private SmartRefreshLayout smartRefreshLayout;
    private int page = 1;
    private int index;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    CharSequence charSequence = (CharSequence) msg.obj;
                    if (charSequence != null) {
                        htmlTextView.setText(charSequence);
                        multiplestatusview.showContent();
                        htmlTextView.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private List<DetailsCommentListBean> listBeans;
    private CustomDialog customDialog;
    private QuestionInfoBean questionInfoBean;

    @Override
    protected CommunityDetailsPresenter onCreatePresenter() {
        return new CommunityDetailsPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_activity_mu_community_details);
        try {
            question_id = getIntent().getStringExtra("question_id");
        }catch (NullPointerException e) {
            showShortToast("参数错误");
            return;
        }
        multiplestatusview = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        topBarView = getViewById(R.id.topbarview);
        htmlTextView = getViewById(R.id.details_textview);
        comment_count = getViewById(R.id.comment_count);
        details_list = getViewById(R.id.p_mlv);
        text = getViewById(R.id.details_text);
        like = getViewById(R.id.details_like);
        headImage = getViewById(R.id.details_head);
        details_name = getViewById(R.id.details_name);
        details_time = getViewById(R.id.details_time);
        details_browse = getViewById(R.id.details_browse);
        RefreshUtils.getInstance(smartRefreshLayout,this ).defaultRefreSh();
        smartRefreshLayout.setEnableRefresh(false);
        listBeans = new ArrayList<>();
        mAdapter = new CommentAdapater(this, listBeans);
        details_list.setDivider(null);
        details_list.setAdapter(mAdapter);
        multiplestatusview.showLoading();
        mPresenter.getCommunityDetails(question_id);
    }

    @Override
    protected void setListener() {
        topBarView.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == TopBarView.ACTION_LEFT_BUTTON) {
                    finish();
                }
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.setLike(question_id);
                like.setEnabled(false);
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLog(false);
            }
        });
        mAdapter.setOnReplyListener(new CommunityConfig.OnReplyListener() {
            @Override
            public void setReplyClick(int position) {
                index = position;
                showDiaLog(true);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getCommentList(question_id, "1", page);
            }
        });
    }

    private void showDiaLog(final boolean isReply) {
        customDialog = new CustomDialog(this, R.style.main_MyDialogStyle);
        Window window = customDialog.getWindow();
        window.getDecorView().setPadding(15, 15, 15, 15);
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置宽高
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        //设置点击Dialog外部任意区域关闭Dialog
        customDialog.setCanceledOnTouchOutside(true);
        window.setGravity(Gravity.BOTTOM);
        if (isReply) {
            customDialog.setIsReply(listBeans.get(index).getUser_name());
        }
        customDialog.setOnEditChangeListener(new CommunityConfig.OnEditChangeListener() {
            @Override
            public void getEditChange(String content, boolean is_show) {
                if (isReply) {
                    mPresenter.sendComment(question_id, content, is_show ? "1" : "0", listBeans.get(index).getId());
                } else {
                    mPresenter.sendComment(question_id, content, is_show ? "1" : "0", "0");
                }
            }
        });
        customDialog.show();
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void showErrorMsg(String msg) {
        showShortToast(msg);
    }

    @Override
    public void showLoadV(String msg) {
        showLoadDiaLog(msg);
    }

    @Override
    public void closeLoadV() {
        hidLoadDiaLog();
    }

    @Override
    public void SuccessData(Object o) {
        questionInfoBean = (QuestionInfoBean) o;
        setActivityContent(questionInfoBean.getContent());

        //topBarView.getCenterTextView().setText(questionInfoBean.getTitle());

        if (questionInfoBean.getIs_anonymity().equals("0")) {
            details_name.setText(questionInfoBean.getUser_name());
            if (questionInfoBean.getAvatar() == null || questionInfoBean.getAvatar().equals("")) {
                Picasso.with(TopicDetailsActivity.this).load("www").error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(headImage);
            }else {
                Picasso.with(TopicDetailsActivity.this).load(questionInfoBean.getAvatar()).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(headImage);
            }
        }else {
            details_name.setText("匿名");
            Picasso.with(TopicDetailsActivity.this).load(R.drawable.name_no).error(R.drawable.name_no).placeholder(R.drawable.name_no).transform(new CircleTransform()).into(headImage);
        }
        details_time.setText(questionInfoBean.getCreated_at());
        details_browse.setText(questionInfoBean.getRead_count() + "次浏览");

        comment_count.setText("全部评论 (" + 0 + ")");
        mPresenter.getCommentList(question_id, "1", page);
    }

    private void setActivityContent(final String activityContent) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Html.ImageGetter imageGetter = new Html.ImageGetter() {

                    @Override
                    public Drawable getDrawable(String source) {
                        Drawable drawable;
                        drawable = getImageNetwork(source);
                        if (drawable == null) {
                            drawable = getResources().getDrawable(R.drawable.image_failure);
                        }
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        return drawable;
                    }
                };
                CharSequence charSequence = Html.fromHtml(activityContent.trim(), imageGetter, null);
                Message ms = Message.obtain();
                ms.what = 1;
                ms.obj = charSequence;
                mHandler.sendMessage(ms);
            }
        }).start();
    }

    public Drawable getImageNetwork(String imageUrl) {
        URL myFileUrl = null;
        Drawable drawable = null;
        try {
            myFileUrl = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            // 在这一步最好先将图片进行压缩，避免消耗内存过多
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            drawable = new BitmapDrawable(bitmap);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void ShowLoadView() {
        multiplestatusview.showLoading();
    }

    @Override
    public void NoNetWork() {
        multiplestatusview.showNoNetwork();
    }

    @Override
    public void NoData() {
        multiplestatusview.showEmpty();
    }

    @Override
    public void ErrorData() {
        multiplestatusview.showError();
    }

    @Override
    public void isLoadMore(boolean isLoadMore) {
        RefreshUtils.getInstance(smartRefreshLayout, TopicDetailsActivity.this).isLoadData(isLoadMore);
    }

    @Override
    public void CommentListData(int total, List<DetailsCommentListBean> list) {
        if (page == 1) {
            listBeans.clear();
        }
        listBeans.addAll(list);
        mAdapter.notifyDataSetChanged();
        comment_count.setText("全部评论 (" + total + ")");
        page++;
        RxBus.getIntanceBus().post(new RxMessageBean(486,total+"",""));
    }

    @Override
    public void sendSuccess(String msg) {
        customDialog.setSendSuccess();
        showShortToast(msg);
        page = 1;
        mPresenter.getCommentList(question_id, "1", page);
    }

    @Override
    public void setLikeSuccess(String msg) {
        like.setImageResource(R.drawable.details_like_org);
        int likeCount = Integer.parseInt(questionInfoBean.getLike_count()) + 1;
        RxBus.getIntanceBus().post(new RxMessageBean(487,likeCount+"",""));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getIntanceBus().unSubscribe(this);
    }
}
