package com.example.module_employees_world.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.adapter.CommentOnerAdapter;
import com.example.module_employees_world.adapter.ImgAdapter;
import com.example.module_employees_world.adapter.PostDetailAdapter;
import com.example.module_employees_world.bean.CommentLikeBean;
import com.example.module_employees_world.bean.CommentListBean;
import com.example.module_employees_world.bean.ParentBean;
import com.example.module_employees_world.bean.PostDetailBean;
import com.example.module_employees_world.contranct.PostsDetailContranct;
import com.example.module_employees_world.presenter.PostDetailPersenter;
import com.example.module_employees_world.utils.CircleTransform;
import com.example.module_employees_world.utils.MyInterpolator;
import com.example.module_employees_world.utils.RxBusMessageBean;
import com.example.module_employees_world.view.CommontPopw;
import com.example.module_employees_world.view.PostsDetailPopw;
import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.image.GlideManager;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.utils.StatusBarUtil;
import com.wb.baselib.utils.ToastUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.wb.rxbus.taskBean.RxBus;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * author:LIENLIN
 * date:2019/3/20
 * 帖子详情
 */
public class PostsDetailActivity extends MvpActivity<PostDetailPersenter> implements PostsDetailContranct.PostsDetailView {

    private TopBarView topBarView;
    private PostDetailAdapter postDetailAdapter;
    private RecyclerView tvImg,mRvPost;
    private RelativeLayout rlOpen;
    private TextView tvDetailText,tvName,tvOpen,tvHtml,tvClose,tvTitle,tvPartName,tvPostType,tvTime,tvBrowseNum,tvTopicGroup,tvCommentNum
            ,tvComment,tvPostNum,tvPostZan,tvSoleName,tvSolePartName,tvSoleTitle,tvSoleTime;
    private SmartRefreshLayout smartRefreshLayout;
    private ImageView ivAvatar,ivBgUser,ivSoleAvatar,ivSoleImg,ivSolegif;
    private LinearLayout llDev1,llDev2,llContainerFab,ll_solve_root;
    private ArrayList<String> imgList = new ArrayList<>();;
    private NestedScrollView scvPost;
    private FloatingActionButton fabAll,fabEdit,fabTop;
    private boolean fabEnable;
    private MultipleStatusView multipleStatusview;
    private List<CommentListBean.ListBean> commentList=new ArrayList<>();
    private int page = 1;
    private int limit = 6;
    private ImgAdapter imgAdapter;
    private String question_id;
    private PostsDetailPopw postsDetailPopw;
    private PostDetailBean postDetailBean;
    private CommontPopw commontPopw;
    private MyHandler myHandler;

    @Override
    protected PostDetailPersenter onCreatePresenter() {
        return new PostDetailPersenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusLayout(this,Color.parseColor("#007AFF"));
        StatusBarUtil.StatusBarDarkMode(this, StatusBarUtil.StatusBarLightMode(this));
        question_id = getIntent().getStringExtra("question_id");

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getPostDetail(question_id,"1");
        mPresenter.getCommentList(question_id,"1",page+"",limit+"");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myHandler!=null){
            myHandler.removeCallbacksAndMessages(null);
            myHandler=null;
        }
        RxBus.getIntanceBus().unSubscribe(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_posts_detail);
        myHandler = new MyHandler(this);
        topBarView = findViewById(R.id.topbarview_post_detail);
        multipleStatusview = findViewById(R.id.post_multipleStatusview);
        mRvPost = findViewById(R.id.rv_post_detail);
        tvDetailText = findViewById(R.id.tv_details_text);
        tvImg = findViewById(R.id.rv_img);
        rlOpen = findViewById(R.id.rl_details_open);
        tvOpen = findViewById(R.id.tv_details_open);
        llDev1 = findViewById(R.id.ll_dev_1);
        llDev2 = findViewById(R.id.ll_dev_2);
        tvHtml = findViewById(R.id.tv_detail_html);
        tvClose = findViewById(R.id.tv_details_close);
        tvTitle = findViewById(R.id.tv_detail_title);
        scvPost = findViewById(R.id.scv_post);
        llContainerFab = findViewById(R.id.ll_container_actionbutton);
        fabAll = findViewById(R.id.fab_all);
        fabEdit = findViewById(R.id.fab_edit);
        fabTop = findViewById(R.id.fab_top);
        ivAvatar = findViewById(R.id.details_head);
        tvName = findViewById(R.id.details_name);
        tvPartName = findViewById(R.id.details_part);
        tvPostType = findViewById(R.id.tv_post_type);
        tvTime = findViewById(R.id.details_time);
        tvBrowseNum = findViewById(R.id.details_browse);
        tvTopicGroup = findViewById(R.id.tv_topic_group);
        tvCommentNum = findViewById(R.id.tv_comment_num);
        tvComment = findViewById(R.id.tv_post_comment);
        tvPostNum = findViewById(R.id.tv_post_num);
        tvPostZan = findViewById(R.id.tv_post_zan);
        ivBgUser = findViewById(R.id.iv_post_type_user);
        smartRefreshLayout = findViewById(R.id.rfl_post);
        //已采纳布局
        ivSoleAvatar = findViewById(R.id.iv_solve_avatar);
        ivSoleImg = findViewById(R.id.iv_solve_img);
        ivSolegif = findViewById(R.id.iv_solve_gif);
        tvSoleName=findViewById(R.id.tv_solve_name);
        tvSolePartName=findViewById(R.id.tv_solve_part);
        tvSoleTitle=findViewById(R.id.tv_solve_title);
        tvSoleTime=findViewById(R.id.tv_solve_time);
        ll_solve_root=findViewById(R.id.ll_solve_root);

        tvCommentNum.setText("全部评论 (" + 0 + ")");
        TextView centerTextView = topBarView.getCenterTextView();
        centerTextView.setVisibility(View.INVISIBLE);
        imgAdapter = new ImgAdapter(this, imgList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(PostsDetailActivity.this,"点击看大图");
            }
        });
        tvImg.setNestedScrollingEnabled(false);
        tvImg.setLayoutManager(new GridLayoutManager(this,4));
        tvImg.setAdapter(imgAdapter);
        postDetailAdapter = new PostDetailAdapter(this,commentList,myHandler);
        mRvPost.setNestedScrollingEnabled(false);
        mRvPost.setLayoutManager(new LinearLayoutManager(this));
        mRvPost.setAdapter(postDetailAdapter);
        /*mRvPost.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });*/

        RefreshUtils.getInstance(smartRefreshLayout,this ).defaultRefreSh();
        smartRefreshLayout.setEnableRefresh(false);


    }

    @Override
    protected void setListener() {
        topBarView.getLeftImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topBarView.getRightImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postsDetailPopw!=null){
                    postsDetailPopw.myShow();
                }
            }
        });
        rlOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llDev1.setVisibility(View.GONE);
                llDev2.setVisibility(View.VISIBLE);
                scvPost.post(new Runnable() {
                    @Override
                    public void run() {
                        scvPost.scrollTo(0,0);
                    }
                });
            }
        });
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llDev1.setVisibility(View.VISIBLE);
                llDev2.setVisibility(View.GONE);
                scvPost.post(new Runnable() {
                    @Override
                    public void run() {
                        scvPost.scrollTo(0,0);
                    }
                });
            }
        });
        //发布评论
        tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postDetailBean!=null){
                    Intent intent = new Intent(PostsDetailActivity.this, CommentDialogActivity.class);
                    intent.putExtra(CommentDialogActivity.TAG_QUESTION_ID,postDetailBean.questionInfo.id+"");
                    intent.putExtra(CommentDialogActivity.TAG_COMMENT_ID,"0");
                    intent.putExtra(CommentDialogActivity.TAG_COMMENT_NAME,postDetailBean.questionInfo.userName);
                    startActivity(intent);
                }

            }
        });
        tvPostNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/3/27 评论置顶
            }
        });
        tvPostZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // : 2019/3/27 点赞
                if (postDetailBean!=null){
                    showLoadDiaLog("");
                    mPresenter.postsLike(postDetailBean.questionInfo.id+"");
                }
            }
        });


        fabAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabEnable){
                    llContainerFab.setVisibility(View.GONE);
                    fabEnable=false;
                }else {
                    AnimationSet animationSet = new AnimationSet(true);
                    animationSet.addAnimation(new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
                    animationSet.addAnimation(new AlphaAnimation(0.5f,1.0f));
                    animationSet.setInterpolator(new MyInterpolator(0.5f));
                    animationSet.setDuration(200);
                    llContainerFab.setAnimation(animationSet);
                    llContainerFab.setVisibility(View.VISIBLE);
                    fabEnable=true;
                }
            }
        });

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/3/29
                ToastUtils.showToast(PostsDetailActivity.this,"发帖");

            }
        });
        fabTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scvPost.post(new Runnable() {
                    @Override
                    public void run() {
                        scvPost.scrollTo(0,0);
                    }
                });
            }
        });

        scvPost.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Rect rect = new Rect();
                boolean cover = tvTitle.getGlobalVisibleRect(rect);
                if (cover) {
                    tvTitle.setVisibility(View.VISIBLE);
                    topBarView.getCenterTextView().setVisibility(View.INVISIBLE);
                }else {
                    tvTitle.setVisibility(View.INVISIBLE);
                    topBarView.getCenterTextView().setVisibility(View.VISIBLE);

                }
            }
        });

        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getCommentList(question_id,"1",page+"",limit+"");
            }
        });

        RxBus.getIntanceBus().registerRxBus(RxBusMessageBean.class, new Consumer<RxBusMessageBean>() {
            @Override
            public void accept(RxBusMessageBean rxMessageBean) throws Exception {
                if (rxMessageBean.getMessageCode() == RxBusMessageBean.MessageType.POST_107){
                    mPresenter.deletePost(question_id);
                    showLoadDiaLog("");
                }else if (rxMessageBean.getMessageCode() == RxBusMessageBean.MessageType.POST_104){
                    // TODO: 2019/3/29  采纳接口掉用
                    ToastUtils.showToast(PostsDetailActivity.this,"采纳接口掉用");
                }else if (rxMessageBean.getMessageCode() == RxBusMessageBean.MessageType.POST_105){
                    // TODO: 2019/3/29  邀请回答
                    ToastUtils.showToast(PostsDetailActivity.this,"邀请回答");
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {}

    /**
     * 加载HTML文本
     * @param activityContent
     * @param tvHtml
     */
    private void setActivityContent(final String activityContent, final TextView tvHtml) {
        final int screenWidth = (int) (getWindowManager().getDefaultDisplay().getWidth()*0.95);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Html.ImageGetter imageGetter = new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        Drawable drawable;
                        //临时解决方案，介于目前前端上传图片没base64编码，web端有base64编码，判断是否通过base64编码临时解决
                        if (source.startsWith("data:image/")){
                            drawable = getBase64ImageNetwork(source);
                        }else {
                            drawable = getImageNetwork(source);
                        }

                        if (drawable == null) {
                            drawable = getResources().getDrawable(R.drawable.image_failure);
                        }
                        int minimumWidth = drawable.getMinimumWidth();
                        int minimumHeight = drawable.getMinimumHeight();
                        int height = (int) (((float)screenWidth / minimumWidth) * minimumHeight);
                        drawable.setBounds(0, 0,screenWidth ,height);
                        return drawable;
                    }
                };
                final CharSequence charSequence = Html.fromHtml(activityContent.trim(), imageGetter, null);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvHtml.setText(charSequence);
                    }
                });
            }
        }).start();
    }

    /**
     * 过去Base64格式的图片
     * @param imageUrl
     * @return
     */
    public Drawable getBase64ImageNetwork(String imageUrl) {
        byte[] decode = Base64.decode(imageUrl.split(",")[1], Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
        return new BitmapDrawable(bitmap);
    }

    /**
     * 获取URL格式图片
     * @param imageUrl
     * @return
     */
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
            // 先将图片进行压缩，避免消耗内存过多
            Bitmap bitmap = getFitSampleBitmap(is);
            drawable = new BitmapDrawable(bitmap);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;
    }

    /**
     * 对网络获取图片压缩处理
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static Bitmap getFitSampleBitmap(InputStream  inputStream) throws Exception{
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig=Bitmap.Config.ALPHA_8;
        byte[] bytes = readStream(inputStream);
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        options.inSampleSize = 2;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 从inputStream中获取字节流 数组大小
     **/
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    @Override
    public void ShowLoadView() {
        multipleStatusview.showLoading();
    }

    @Override
    public void NoNetWork() {
        multipleStatusview.showNoNetwork();
    }

    @Override
    public void NoData() {
        multipleStatusview.showEmpty();
    }

    @Override
    public void ErrorData() {
        multipleStatusview.showError();
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
    public void SuccessData(Object o) {
        List<CommentListBean.ListBean> listBeanList = (List<CommentListBean.ListBean>) o;
        if (page==1){
            commentList.clear();
        }
        commentList.addAll(listBeanList);
        postDetailAdapter.notifyDataSetChanged();
        multipleStatusview.showContent();
        page++;
        if (page==1){
            scvPost.post(new Runnable() {
                @Override
                public void run() {
                    scvPost.scrollTo(0,0);
                }
            });
        }

    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void isLoadMore(boolean moreEnable) {
        RefreshUtils.getInstance(smartRefreshLayout, this).isLoadData(moreEnable);
    }

    @Override
    public void getPostDetail(PostDetailBean postDetailBean) {
        // TODO: 2019/3/26
        this.postDetailBean=postDetailBean;
        tvTitle.setText(postDetailBean.questionInfo.title);
        topBarView.getCenterTextView().setText(postDetailBean.questionInfo.title);
        Picasso.with(this).load(postDetailBean.questionInfo.avatar).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(ivAvatar);
        tvName.setText(postDetailBean.questionInfo.userName);
        tvPartName.setText(postDetailBean.questionInfo.departmentName);
        tvTime.setText(postDetailBean.questionInfo.createdAt);
        tvBrowseNum.setText("l  "+postDetailBean.questionInfo.readCount+"人浏览");
        tvTopicGroup.setText("【"+postDetailBean.questionInfo.groupName+"】");
        tvCommentNum.setText("全部评论 (" + postDetailBean.questionInfo.commentCount+ ")");
        tvPostNum.setText(postDetailBean.questionInfo.commentCount+"");
        tvPostZan.setText(postDetailBean.questionInfo.likeCount+"");
        tvDetailText.setText(postDetailBean.questionInfo.contentText);
        setActivityContent(postDetailBean.questionInfo.contentText,tvDetailText);
        //1=已点赞 0 未点赞
        if (postDetailBean.questionInfo.isLike==0){
            Drawable drawable = getResources().getDrawable(R.drawable.post_comment_zan);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            tvPostZan.setCompoundDrawables(drawable,null,null,null);
        }else{
            Drawable drawable = getResources().getDrawable(R.drawable.post_comment_zan_able);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            tvPostZan.setCompoundDrawables(drawable,null,null,null);
        }
        //帖子类型 1交流 2建议 3提问
        if (postDetailBean.questionInfo.type==1){
            tvPostType.setVisibility(View.INVISIBLE);
        }else if (postDetailBean.questionInfo.type==2){
            tvPostType.setVisibility(View.VISIBLE);
            if (postDetailBean.questionInfo.solveStatus==0){
                tvPostType.setText("未采纳");
            }else {
                tvPostType.setVisibility(View.INVISIBLE);
                ivBgUser.setVisibility(View.VISIBLE);
            }
        }else if (postDetailBean.questionInfo.type==3){
            tvPostType.setVisibility(View.VISIBLE);
            if (postDetailBean.questionInfo.solveStatus==0){
                tvPostType.setText("未解决");
                ll_solve_root.setVisibility(View.GONE);
            }else {
                tvPostType.setText("已解决");
                ll_solve_root.setVisibility(View.VISIBLE);

                Picasso.with(this).load(postDetailBean.solve_comment.avatar).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(ivSoleAvatar);
                if (!TextUtils.isEmpty(postDetailBean.solve_comment.commentPicture)){
                    ivSoleImg.setVisibility(View.VISIBLE);
                    GlideManager.getInstance().setCommonPhoto(ivSoleImg, R.drawable.course_image ,this , postDetailBean.solve_comment.commentPicture ,false );
                }else {
                    ivSoleImg.setVisibility(View.GONE);
                }
                tvSoleName.setText(postDetailBean.solve_comment.userName);
                tvSolePartName.setText(postDetailBean.solve_comment.departmentName);
                tvSoleTitle.setText(postDetailBean.solve_comment.content);
                tvSoleTime.setText(postDetailBean.solve_comment.createdAt);
            }
        }

        //显示“展开全部”条件：文本等于五行或者有图片
        tvDetailText.post(new Runnable() {
            @Override
            public void run() {
                if (tvDetailText.getLineCount()==5||postDetailBean.questionInfo.contentImg.size()>0){
                    tvOpen.setVisibility(View.VISIBLE);
                }else {
                    tvOpen.setVisibility(View.INVISIBLE);
                }
            }
        });
        setActivityContent(postDetailBean.questionInfo.content,tvHtml);

        imgList.clear();
        imgList.addAll(postDetailBean.questionInfo.contentImg);
        imgAdapter.notifyDataSetChanged();

        postsDetailPopw = new PostsDetailPopw(PostsDetailActivity.this,postDetailBean.questionInfo.type,postDetailBean.questionInfo.solveStatus);

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
    public void postsLike(CommentLikeBean commentLikeBean) {
        // : 2019/3/29 帖子点赞
        hidLoadDiaLog();
        int integer = Integer.valueOf(tvPostZan.getText().toString());
        if (commentLikeBean.resultType==1){
            tvPostZan.setText(--integer+"");
            Drawable drawable = getResources().getDrawable(R.drawable.post_comment_zan);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            tvPostZan.setCompoundDrawables(drawable,null,null,null);
        }else if (commentLikeBean.resultType==2){
            //点赞成功
            tvPostZan.setText(++integer+"");
            Drawable drawable = getResources().getDrawable(R.drawable.post_comment_zan_able);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            tvPostZan.setCompoundDrawables(drawable,null,null,null);
        }
    }

    @Override
    public void deletePost() {
        hidLoadDiaLog();
        // TODO: 2019/3/29 事件通知首页刷新数据
        finish();
    }

    @Override
    public void deleteComment(int position, int partenPosition) {
        hidLoadDiaLog();
        if (partenPosition == -1){
            //删除评论
            commentList.remove(position);
        }else {
            //删除子评论
            commentList.get(position).parent.remove(partenPosition);
        }
        postDetailAdapter.notifyDataSetChanged();
    }

    public static class MyHandler extends Handler{

        private final WeakReference<PostsDetailActivity> weakReference;

        public MyHandler(PostsDetailActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            int commentId;
            PostsDetailActivity activity = weakReference.get();
            switch (what){
                case RxBusMessageBean.MessageType.POST_101:
                    // TODO: 2019/3/29 删除评论接口
                    commentId = msg.arg1;
                    int commentPosition = msg.arg2;
                    activity.commontPopw = new CommontPopw(activity, "删除评论后，评论下所有回复都会被删除。", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            activity.mPresenter.deleteComment(commentId+"", commentPosition, -1);
                            activity.showLoadDiaLog("");
                            activity.commontPopw.myDismiss();
                        }
                    });
                    break;
                case RxBusMessageBean.MessageType.POST_102:
                    // TODO: 2019/3/29 删除子评论接口
                    commentId=(int)msg.obj;
                    int position = msg.arg1;
                    int partenPosition = msg.arg2;
                    activity.mPresenter.deleteComment(commentId+"",position,partenPosition);
                    break;
                case RxBusMessageBean.MessageType.POST_103:
                    CommentListBean.ListBean listBean= (CommentListBean.ListBean) msg.obj;
                    // TODO: 2019/3/29 采纳确认弹窗
                    ToastUtils.showToast(activity,"采纳："+listBean.userName);
                    break;
                case RxBusMessageBean.MessageType.POST_106:
                    // : 2019/3/29  评论点赞
                    TextView tvZan= (TextView) msg.obj;
                    commentId=msg.arg1;
                    activity.mPresenter.commentLike(commentId+"",tvZan);
                    activity.showLoadDiaLog("");
                    break;
                case RxBusMessageBean.MessageType.POST_108:
                    // : 2019/3/29  子评论点赞
                    TextView tvChildrenZan= (TextView) msg.obj;
                    commentId=msg.arg1;
                    activity.mPresenter.commentLike(commentId+"",tvChildrenZan);
                    activity.showLoadDiaLog("");
                    break;
                case RxBusMessageBean.MessageType.POST_109:
                    String userName= (String) msg.obj;
                    int questionId = msg.arg1;
                    commentId = msg.arg2;
                    Intent intent = new Intent(activity, CommentDialogActivity.class);
                    intent.putExtra(CommentDialogActivity.TAG_QUESTION_ID,questionId+"");
                    intent.putExtra(CommentDialogActivity.TAG_COMMENT_ID,commentId+"");
                    intent.putExtra(CommentDialogActivity.TAG_COMMENT_NAME,userName);
                    activity.startActivity(intent);
                    break;
            }
        }
    }

}
