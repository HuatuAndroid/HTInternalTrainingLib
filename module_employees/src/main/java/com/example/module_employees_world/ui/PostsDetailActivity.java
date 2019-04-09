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
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.module_employees_world.R;
import com.example.module_employees_world.adapter.ImgAdapter;
import com.example.module_employees_world.adapter.PostDetailAdapter;
import com.example.module_employees_world.bean.CommentInsertBean;
import com.example.module_employees_world.bean.CommentLikeBean;
import com.example.module_employees_world.bean.CommentListBean;
import com.example.module_employees_world.bean.IsBannedBean;
import com.example.module_employees_world.bean.ParentBean;
import com.example.module_employees_world.bean.PostDetailBean;
import com.example.module_employees_world.common.TutuPicInit;
import com.example.module_employees_world.contranct.PostsDetailContranct;
import com.example.module_employees_world.presenter.PostDetailPersenter;
import com.example.module_employees_world.ui.home.CommunityActivity;
import com.example.module_employees_world.ui.topic.NTopicEditActivity;
import com.example.module_employees_world.utils.CircleTransform;
import com.example.module_employees_world.utils.EmojiUtils;
import com.example.module_employees_world.utils.MyInterpolator;
import com.example.module_employees_world.utils.RxBusMessageBean;
import com.example.module_employees_world.view.CommontPopw;
import com.example.module_employees_world.view.PostsDetailPopw;
import com.liuxiaoji.module_contacts.selectparticipant.bean.ContactsBean;
import com.liuxiaoji.module_contacts.selectparticipant.ui.ContactContract;
import com.liuxiaoji.module_contacts.selectparticipant.ui.SelectParticipantActivity;
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
import com.wb.baselib.utils.StatusBarUtil;
import com.wb.baselib.utils.ToastUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.wb.rxbus.taskBean.RxBus;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.functions.Consumer;

/**
 * author:LIENLIN
 * date:2019/3/20
 * 帖子详情
 */
public class PostsDetailActivity extends MvpActivity<PostDetailPersenter> implements PostsDetailContranct.PostsDetailView {

    private static Message msg;
    private TopBarView topBarView;
    private PostDetailAdapter postDetailAdapter;
    private RecyclerView tvImg, mRvPost;
    private RelativeLayout rlOpen;
    private TextView tvDetailText, tvName, tvOpen, tvHtml, tvClose, tvTitle, tvPartName, tvPostType, tvTime, tvBrowseNum, tvTopicGroup, tvCommentNum, tvComment, tvPostNum, tvPostZan, tvSoleName, tvSolePartName, tvSoleTitle, tvSoleTime;
    private SmartRefreshLayout smartRefreshLayout;
    private ImageView ivAvatar, ivBgUser, ivSoleAvatar, ivSoleImg, ivSolegif;
    private LinearLayout llDev1, llDev2, llContainerFab, ll_solve_root;
    private ArrayList<String> imgList = new ArrayList<>();
    ;
    private NestedScrollView scvPost;
    private FloatingActionButton fabAll, fabEdit, fabTop;
    private boolean fabEnable;
    private MultipleStatusView multipleStatusview;
    private List<CommentListBean.ListBean> commentList = new ArrayList<>();
    private int page = 1;
    private int limit = 10;
    private ImgAdapter imgAdapter;
    private String question_id;
    private PostsDetailPopw postsDetailPopw;
    private PostDetailBean postDetailBean;
    private CommontPopw commontPopw;
    private MyHandler myHandler;
    private final static int ACTIVITY_FOR_REQUEST_ID = 1000;

    @Override
    protected PostDetailPersenter onCreatePresenter() {
        return new PostDetailPersenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusLayout(this, Color.parseColor("#007AFF"));
        StatusBarUtil.StatusBarDarkMode(this, StatusBarUtil.StatusBarLightMode(this));
        question_id = getIntent().getStringExtra("question_id");

//        mPresenter.getCommentList(question_id,"1",page+"",limit+"");
    }

    @Override
    protected void onResume() {
        super.onResume();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                mPresenter.getPostDetail(question_id, "1");
                mPresenter.getCommentList(question_id, "1", page + "", limit + "");
            }
        }, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SelectParticipantActivity.intentCode && data != null) {
            ContactsBean.DataBean.StaffsBean staffsBean = (ContactsBean.DataBean.StaffsBean) data.getSerializableExtra("staffsBean");
            showLoadDiaLog("");
            mPresenter.invitationUser(staffsBean.id + "", question_id);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
            myHandler = null;
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
        tvSoleName = findViewById(R.id.tv_solve_name);
        tvSolePartName = findViewById(R.id.tv_solve_part);
        tvSoleTitle = findViewById(R.id.tv_solve_title);
        tvSoleTime = findViewById(R.id.tv_solve_time);
        ll_solve_root = findViewById(R.id.ll_solve_root);

        multipleStatusview.showContent();
        tvCommentNum.setText("全部评论 (" + 0 + ")");
        TextView centerTextView = topBarView.getCenterTextView();
        centerTextView.setVisibility(View.INVISIBLE);
        imgAdapter = new ImgAdapter(this, imgList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostsDetailActivity.this, PictuirePreviewActivity.class);
                intent.putStringArrayListExtra(PictuirePreviewActivity.TAG_JUMP, imgList);
                startActivity(intent);
            }
        });
        tvImg.setNestedScrollingEnabled(false);
        tvImg.setLayoutManager(new GridLayoutManager(this, 4));
        tvImg.setAdapter(imgAdapter);
        postDetailAdapter = new PostDetailAdapter(this, commentList, myHandler);
        mRvPost.setNestedScrollingEnabled(false);
        mRvPost.setLayoutManager(new LinearLayoutManager(this));
        mRvPost.setAdapter(postDetailAdapter);
        /*mRvPost.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });*/

        RefreshUtils.getInstance(smartRefreshLayout, this).defaultRefreSh();
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableLoadMore(false);
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
                if (postsDetailPopw != null) {
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
                        scvPost.scrollTo(0, 0);
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
                        scvPost.scrollTo(0, 0);
                    }
                });
            }
        });

        //发布评论
        tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtils.is_banned == 0) {
                    //发帖
                    Intent intent = new Intent(PostsDetailActivity.this, NTopicEditActivity.class);
                    startActivity(intent);
                }else{
                    showShortToast("你已被禁言");
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
                if (postDetailBean != null) {
                    showLoadDiaLog("");
                    mPresenter.postsLike(postDetailBean.questionInfo.id + "");
                }
            }
        });


        fabAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabEnable) {
                    llContainerFab.setVisibility(View.GONE);
                    fabEnable = false;
                    fabAll.setImageResource(R.drawable.post_action_button_2);
                } else {
                    AnimationSet animationSet = new AnimationSet(true);
                    animationSet.addAnimation(new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
                    animationSet.addAnimation(new AlphaAnimation(0.5f, 1.0f));
                    animationSet.setInterpolator(new MyInterpolator(0.5f));
                    animationSet.setDuration(200);
                    llContainerFab.setAnimation(animationSet);
                    llContainerFab.setVisibility(View.VISIBLE);
                    fabEnable = true;
                    fabAll.setImageResource(R.drawable.post_action_button_all);
                }
            }
        });

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/3/29
                //发帖
                mPresenter.getIsBanned(1);
            }
        });
        fabTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scvPost.post(new Runnable() {
                    @Override
                    public void run() {
                        scvPost.scrollTo(0, 0);
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
                } else {
                    tvTitle.setVisibility(View.INVISIBLE);
                    topBarView.getCenterTextView().setVisibility(View.VISIBLE);

                }
            }
        });

        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getCommentList(question_id, "1", page + "", limit + "");
            }
        });

        RxBus.getIntanceBus().registerRxBus(RxBusMessageBean.class, new Consumer<RxBusMessageBean>() {
            @Override
            public void accept(RxBusMessageBean rxMessageBean) throws Exception {
                if (rxMessageBean.getMessageCode() == RxBusMessageBean.MessageType.POST_114) {
                    RxBus.getIntanceBus().post(new RxBusMessageBean(RxBusMessageBean.MessageType.SEARCH_POST_COMMENT, ++postDetailBean.questionInfo.commentCount + ""));
// TODO: 2019/4/5 添加二级评论时，点赞状态错乱，需修改后才能用下面这段代码，现在暂时掉接口刷新
                    /*CommentInsertBean insertBean = (CommentInsertBean) rxMessageBean.getMessage();
                    String comment_id = (String) rxMessageBean.getMessage1();
                    if ("0".equals(comment_id)){
                        //一级评论
                        commentList.add(insertBean.first);
                        postDetailAdapter.notifyDataSetChanged();
//                        multipleStatusview.showContent();
                        tvCommentNum.setText("全部评论 (" + ++postDetailBean.questionInfo.commentCount+ ")");
                        smartRefreshLayout.setEnableLoadMore(true);
                    }else {
                        //二级评论
                        for (int i = 0; i < commentList.size(); i++) {
                            if (comment_id.equals(commentList.get(i).id+"")){
                                List<ParentBean> parent = commentList.get(i).parent;
                                commentList.get(i).parent.clear();
                                commentList.get(i).parent.add(insertBean.second);
                                commentList.get(i).parent.addAll(parent);
                                commentList.get(i).count++;
                            }
                        }
                        postDetailAdapter.notifyDataSetChanged();
                    }*/
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }

    /**
     * 加载HTML文本
     *
     * @param activityContent
     * @param tvHtml
     */
    private void setActivityContent(final String activityContent, final TextView tvHtml) {
        //表情解码
        String decodeContent = new String(EmojiUtils.decode(activityContent.trim()));
        for (int i = 0; i < TutuPicInit.EMOJICONS.size(); i++) {
            String key = TutuPicInit.EMOJICONS.get(i).key;
            if (decodeContent.contains(key)) {
                String content = "<\\br><img src=\"date:res=" + TutuPicInit.EMOJICONS.get(i).TutuId + "\"><\\br>";
                decodeContent = decodeContent.replace(key, content);
            }
        }

        final int screenWidth = (int) (getWindowManager().getDefaultDisplay().getWidth() * 0.95);
        String finalDecodeContent = decodeContent;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Html.ImageGetter imageGetter = new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        Drawable drawable;
                        int resType;
                        //临时解决方案，介于目前前端上传图片没base64编码，web端有base64编码，判断是否通过base64编码临时解决
                        if (source.startsWith("data:image/")) {
                            drawable = getBase64ImageNetwork(source);
                            resType = 0;
                        } else if (source.startsWith("date:res")) {
                            String resId = source.substring(source.indexOf("=") + 1, source.length());
                            drawable = getResources().getDrawable(Integer.valueOf(resId));
                            resType = 1;
                        } else {
                            drawable = getImageNetwork(source);
                            resType = 2;
                        }

                        if (drawable == null) {
                            drawable = getResources().getDrawable(R.drawable.image_failure);
                            resType = 0;
                        }
                        switch (resType) {
                            case 0:
                            case 2:
                                int minimumWidth = drawable.getMinimumWidth();
                                int minimumHeight = drawable.getMinimumHeight();
                                int height = (int) (((float) screenWidth / minimumWidth) * minimumHeight);
                                drawable.setBounds(0, 0, screenWidth, height);
                                break;
                            case 1:
                                //GIF大小暂写死
                                drawable.setBounds(0, 0, 360, 360);
                                break;
                        }

                        return drawable;
                    }
                };
                final CharSequence charSequence = Html.fromHtml(finalDecodeContent, imageGetter, null);
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
     *
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
     *
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
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static Bitmap getFitSampleBitmap(InputStream inputStream) throws Exception {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.ALPHA_8;
        byte[] bytes = readStream(inputStream);
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        options.inSampleSize = 4;
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
//        multipleStatusview.showLoading();
    }

    @Override
    public void NoNetWork() {
//        multipleStatusview.showNoNetwork();
    }

    @Override
    public void NoData() {
//        multipleStatusview.showEmpty();
    }

    @Override
    public void ErrorData() {
//        multipleStatusview.showError();
    }

    @Override
    public void showErrorMsg(String msg) {
        showShortToast(msg);
        hidLoadDiaLog();
    }

    @Override
    public void showLoadV(String msg) {
    }

    @Override
    public void closeLoadV() {
    }

    @Override
    public void SuccessData(Object o) {
        List<CommentListBean.ListBean> listBeanList = (List<CommentListBean.ListBean>) o;
        if (page == 1) {
            commentList.clear();
        }
        commentList.addAll(listBeanList);
        postDetailAdapter.notifyDataSetChanged();
//        multipleStatusview.showContent();
        smartRefreshLayout.setEnableLoadMore(true);
        page++;
        /*if (page==1){
            scvPost.post(new Runnable() {
                @Override
                public void run() {
                    scvPost.scrollTo(0,0);
                }
            });
        }*/

    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void getIdBanned(IsBannedBean isBannedBean, int type) {
        if (isBannedBean.isBanned == 0) {
            //type = 1 是发帖 2是评论
            if (type == 1) {
                //发帖
                Intent intent = new Intent(PostsDetailActivity.this, NTopicEditActivity.class);
                startActivity(intent);
            } else if (type == 2) {
                String userName = (String) msg.obj;
                int questionId = msg.arg1;
                int commentId = msg.arg2;
                Intent intent = new Intent(PostsDetailActivity.this, CommentDialogActivity.class);
                intent.putExtra(CommentDialogActivity.TAG_QUESTION_ID, questionId + "");
                intent.putExtra(CommentDialogActivity.TAG_COMMENT_ID, commentId + "");
                intent.putExtra(CommentDialogActivity.TAG_COMMENT_NAME, userName);
                startActivity(intent);
            }else {
                Intent intent = new Intent(PostsDetailActivity.this, CommentDialogActivity.class);
                intent.putExtra(CommentDialogActivity.TAG_QUESTION_ID, postDetailBean.questionInfo.id + "");
                intent.putExtra(CommentDialogActivity.TAG_COMMENT_ID, "0");
                intent.putExtra(CommentDialogActivity.TAG_COMMENT_NAME, postDetailBean.questionInfo.userName);
                startActivity(intent);
            }
        } else {
            showShortToast("你已被禁言");
        }
    }

    @Override
    public void isLoadMore(boolean moreEnable) {
        RefreshUtils.getInstance(smartRefreshLayout, this).isLoadData(moreEnable);
    }

    @Override
    public void getPostDetail(PostDetailBean postDetailBean) {
        hidLoadDiaLog();
        // : 2019/3/26
        this.postDetailBean = postDetailBean;
        tvTitle.setText(postDetailBean.questionInfo.title);
        topBarView.getCenterTextView().setText(postDetailBean.questionInfo.title);
        Picasso.with(this).load(postDetailBean.questionInfo.avatar).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(ivAvatar);
        tvName.setText(postDetailBean.questionInfo.userName);
        tvPartName.setText(postDetailBean.questionInfo.departmentName);
        tvTime.setText(postDetailBean.questionInfo.createdAt);
        tvBrowseNum.setText("l  " + postDetailBean.questionInfo.readCount + "人浏览");
        tvTopicGroup.setText("【" + postDetailBean.questionInfo.groupName + "】");
        tvCommentNum.setText("全部评论 (" + postDetailBean.questionInfo.commentCount + ")");
        tvPostNum.setText(postDetailBean.questionInfo.commentCount + "");
        tvPostZan.setText(postDetailBean.questionInfo.likeCount + "");
        tvDetailText.setText(postDetailBean.questionInfo.contentText);
        setActivityContent(postDetailBean.questionInfo.contentText, tvDetailText);
        //1=已点赞 0 未点赞
        if (postDetailBean.questionInfo.isLike == 0) {
            Drawable drawable = getResources().getDrawable(R.drawable.post_comment_zan);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvPostZan.setCompoundDrawables(drawable, null, null, null);
        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.post_comment_zan_able);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvPostZan.setCompoundDrawables(drawable, null, null, null);
        }
        //帖子类型 1交流 2建议 3提问
        if (postDetailBean.questionInfo.type == 1) {
            tvPostType.setVisibility(View.GONE);
        } else if (postDetailBean.questionInfo.type == 2) {
            tvPostType.setVisibility(View.VISIBLE);
            if (postDetailBean.questionInfo.solveStatus == 0) {
                tvPostType.setText("未采纳");
            } else {
                topBarView.getRightImageButton().setVisibility(View.GONE);
                tvPostType.setText("已采纳");
                ivBgUser.setVisibility(View.VISIBLE);
            }
        } else if (postDetailBean.questionInfo.type == 3) {
            tvPostType.setVisibility(View.VISIBLE);
            if (postDetailBean.questionInfo.solveStatus == 0) {
                tvPostType.setText("未解决");
                ll_solve_root.setVisibility(View.GONE);
            } else {
                tvPostType.setText("已解决");
                ll_solve_root.setVisibility(View.VISIBLE);
                topBarView.getRightImageButton().setVisibility(View.GONE);
                //展示已采纳的评论
                if (postDetailBean.solve_comment != null) {
                    Picasso.with(this).load(postDetailBean.solve_comment.avatar).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(ivSoleAvatar);
                    if (!TextUtils.isEmpty(postDetailBean.solve_comment.commentPicture)) {
                        ivSoleImg.setVisibility(View.VISIBLE);
                        GlideManager.getInstance().setCommonPhoto(ivSoleImg, R.drawable.course_image, this, HttpConfig.newInstance().getmBaseUrl() + "/" + postDetailBean.solve_comment.commentPicture, false);
                    } else {
                        ivSoleImg.setVisibility(View.GONE);
                    }
                    if (!TextUtils.isEmpty(postDetailBean.solve_comment.commentFace)) {
                        ivSolegif.setVisibility(View.VISIBLE);
                        GlideManager.getInstance().setGlideResourceImage(ivSolegif, TutuPicInit.getResFromEmojicList(postDetailBean.solve_comment.commentFace), R.drawable.image_failure, R.drawable.course_image, this);
                    } else {
                        ivSolegif.setVisibility(View.GONE);
                    }
                    tvSoleName.setText(postDetailBean.solve_comment.userName);
                    tvSolePartName.setText(postDetailBean.solve_comment.departmentName);
                    tvSoleTitle.setText(EmojiUtils.decode(postDetailBean.solve_comment.content));
                    tvSoleTime.setText(postDetailBean.solve_comment.createdAt);
                }

            }
        }

        //显示“展开全部”条件：文本等于五行或者有图片
        tvDetailText.post(new Runnable() {
            @Override
            public void run() {
                if (tvDetailText.getLineCount() == 5 || postDetailBean.questionInfo.contentImg.size() > 0 || tvDetailText.toString().contains("...")) {
                    tvOpen.setVisibility(View.VISIBLE);
                } else {
                    tvOpen.setVisibility(View.INVISIBLE);
                }
            }
        });
        setActivityContent(postDetailBean.questionInfo.content, tvHtml);

        imgList.clear();
        imgList.addAll(postDetailBean.questionInfo.contentImg);
        imgAdapter.notifyDataSetChanged();

        postsDetailPopw = new PostsDetailPopw(PostsDetailActivity.this, postDetailBean.questionInfo.type, postDetailBean.info, postDetailBean.questionInfo.solveStatus, myHandler);

    }

    @Override
    public void commentLike(CommentLikeBean commentLikeBean, TextView tvZan) {
        hidLoadDiaLog();
        int integer = Integer.valueOf(tvZan.getText().toString());
        if (commentLikeBean.resultType == 1) {
            //取消成功
            tvZan.setText(--integer + "");
            Drawable drawable = getResources().getDrawable(R.drawable.post_comment_zan);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvZan.setCompoundDrawables(drawable, null, null, null);

        } else if (commentLikeBean.resultType == 2) {
            //点赞成功
            tvZan.setText(++integer + "");
            Drawable drawable = getResources().getDrawable(R.drawable.post_comment_zan_able);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvZan.setCompoundDrawables(drawable, null, null, null);
        }

    }

    @Override
    public void postsLike(CommentLikeBean commentLikeBean) {
        // : 2019/3/29 帖子点赞
        hidLoadDiaLog();
        int integer = Integer.valueOf(tvPostZan.getText().toString());
        if (commentLikeBean.resultType == 1) {
            tvPostZan.setText(--integer + "");
            Drawable drawable = getResources().getDrawable(R.drawable.post_comment_zan);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvPostZan.setCompoundDrawables(drawable, null, null, null);
        } else if (commentLikeBean.resultType == 2) {
            //点赞成功
            tvPostZan.setText(++integer + "");
            Drawable drawable = getResources().getDrawable(R.drawable.post_comment_zan_able);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvPostZan.setCompoundDrawables(drawable, null, null, null);
        }
        RxBus.getIntanceBus().post(new RxBusMessageBean(RxBusMessageBean.MessageType.SEARCH_POST_LIKE, tvPostZan.getText().toString().trim()));
    }

    @Override
    public void deletePost() {
        hidLoadDiaLog();
        // TODO: 2019/3/29 事件通知首页刷新数据
        RxBus.getIntanceBus().post(new RxBusMessageBean(RxBusMessageBean.MessageType.SEARCH_POST_DELETE, ""));
        finish();
    }

    @Override
    public void deleteComment(int partenPosition, int position) {
        hidLoadDiaLog();
        String comment = "";
        if (position == -1) {
            //删除评论
            commentList.remove(partenPosition);
            postDetailAdapter.notifyDataSetChanged();
        } else {
            //删除子评论
            int postId = commentList.get(partenPosition).id;
            mPresenter.commentChildrenList(partenPosition, postId, 1, 2, 1);
            showLoadDiaLog("");
        }
        if (postDetailBean.questionInfo.commentCount > 0) {
            comment = --postDetailBean.questionInfo.commentCount + "";
            tvCommentNum.setText("全部评论 (" + comment + ")");
            tvPostNum.setText(comment);
        } else {
            comment = "0";
            tvPostNum.setText(comment);
            tvCommentNum.setText("全部评论 (" + comment + ")");
        }
        RxBus.getIntanceBus().post(new RxBusMessageBean(RxBusMessageBean.MessageType.SEARCH_CHANGE_KEYWORD, comment));
    }

    @Override
    public void editQuestion() {
        hidLoadDiaLog();
        //刷新数据
        page = 1;
        showLoadDiaLog("");
        mPresenter.getPostDetail(question_id, "1");
        mPresenter.getCommentList(question_id, "1", page + "", limit + "");
    }

    @Override
    public void acceptPosts() {
        // : 2019/4/4 采纳帖子
        hidLoadDiaLog();
        showLoadDiaLog("");
        mPresenter.getPostDetail(question_id, "1");
//        mPresenter.getCommentList(question_id,"1",page+"",limit+"");
    }

    @Override
    public void acceptComment() {
        // : 2019/4/4 采纳评论
        hidLoadDiaLog();
        showLoadDiaLog("");
        page = 1;
        mPresenter.getPostDetail(question_id, "1");
        mPresenter.getCommentList(question_id, "1", page + "", limit + "");
    }

    @Override
    public void invitationUser() {
        //邀请回答
        hidLoadDiaLog();
        ToastUtils.showToast(this, "邀请回答成功");
    }

    @Override
    public void commentChildrenList(List<ParentBean> childrenBeans, int partenPosition) {
        hidLoadDiaLog();
        // : 2019/4/4 子评论更新
        if (commentList.get(partenPosition).parent != null) {
            commentList.get(partenPosition).parent.clear();
            commentList.get(partenPosition).parent.addAll(childrenBeans);
        } else {
            commentList.get(partenPosition).parent = childrenBeans;
        }
        commentList.get(partenPosition).count--;
        postDetailAdapter.notifyDataSetChanged();
    }

    public static class MyHandler extends Handler {

        private final WeakReference<PostsDetailActivity> weakReference;

        public MyHandler(PostsDetailActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            int commentId;
            PostsDetailActivity activity = weakReference.get();
            switch (what) {
                case RxBusMessageBean.MessageType.POST_101:
                    // : 2019/3/29 删除评论接口
                    commentId = msg.arg1;
                    int commentPosition = msg.arg2;
                    activity.commontPopw = new CommontPopw(activity, "删除评论后，评论下所有回复都会被删除。", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            activity.mPresenter.deleteComment(commentId + "", commentPosition, -1);
                            activity.showLoadDiaLog("");
                            activity.commontPopw.myDismiss();
                        }
                    });
                    break;
                case RxBusMessageBean.MessageType.POST_102:
                    // : 2019/3/29 删除子评论接口
                    commentId = (int) msg.obj;
                    int partenPosition = msg.arg1;
                    int position = msg.arg2;
                    activity.mPresenter.deleteComment(commentId + "", partenPosition, position);
                    break;
                case RxBusMessageBean.MessageType.POST_103:
                    CommentListBean.ListBean listBean = (CommentListBean.ListBean) msg.obj;
                    // : 2019/3/29 采纳评论
                    activity.commontPopw = new CommontPopw(activity, "确定采纳该建议吗？", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            activity.mPresenter.acceptComment(listBean.id + "");
                            activity.showLoadDiaLog("");
                            activity.commontPopw.myDismiss();
                        }
                    });
                    break;
                case RxBusMessageBean.MessageType.POST_104:
                    // : 2019/3/29  采纳帖子
                    activity.commontPopw = new CommontPopw(activity, "确定采纳该建议吗?", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            activity.mPresenter.acceptPosts(activity.question_id, "1");
                            activity.showLoadDiaLog("");
                            activity.commontPopw.myDismiss();
                        }
                    });
                    break;
                case RxBusMessageBean.MessageType.POST_105:
                    //  2019/3/29  邀请回答
                    activity.startActivityForResult(new Intent(activity, SelectParticipantActivity.class), SelectParticipantActivity.intentCode);
                    break;
                case RxBusMessageBean.MessageType.POST_106:
                    // : 2019/3/29  评论点赞
                    TextView tvZan = (TextView) msg.obj;
                    commentId = msg.arg1;
                    activity.mPresenter.commentLike(commentId + "", tvZan);
                    activity.showLoadDiaLog("");
                    break;
                case RxBusMessageBean.MessageType.POST_107:
                    // : 2019/4/3 删除帖子
                    activity.mPresenter.deletePost(activity.question_id);
                    activity.showLoadDiaLog("");
                    break;
                case RxBusMessageBean.MessageType.POST_108:
                    // : 2019/3/29  子评论点赞
                    TextView tvChildrenZan = (TextView) msg.obj;
                    commentId = msg.arg1;
                    activity.mPresenter.commentLike(commentId + "", tvChildrenZan);
                    activity.showLoadDiaLog("");
                    break;
                case RxBusMessageBean.MessageType.POST_109:
                    activity.msg = msg;
                    //评论
                    activity.mPresenter.getIsBanned(2);
                    break;
                case RxBusMessageBean.MessageType.POST_113:
                    // 修改帖子类型
                    String type = msg.arg1 + "";
                    activity.showLoadDiaLog("");
                    activity.mPresenter.editQuestion(type, activity.question_id);
                    break;
                case RxBusMessageBean.MessageType.POST_115:
                    // TODO: 2019/4/6 编辑
                    Intent intent1 = new Intent(activity, EditPostsActivity.class);
                    intent1.putExtra(EditPostsActivity.TAG_TITLE_STR, activity.postDetailBean.questionInfo.title);
                    intent1.putExtra(EditPostsActivity.TAG_CONTENT_STR, activity.postDetailBean.questionInfo.content);
                    intent1.putExtra(EditPostsActivity.TAG_CONTENT_ID, activity.postDetailBean.questionInfo.id + "");
                    activity.startActivity(intent1);
                    break;
            }
        }
    }

}
