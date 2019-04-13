package com.example.module_employees_world.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.bean.CommentInsertBean;
import com.example.module_employees_world.bean.EmojiconBean;
import com.example.module_employees_world.bean.NImageBean;
import com.example.module_employees_world.bean.TutuIconBean;
import com.example.module_employees_world.common.CommonUtils;
import com.example.module_employees_world.common.LocalImageHelper;
import com.example.module_employees_world.contranct.CommentSendDialogContranct;
import com.example.module_employees_world.presenter.CommentSendDialogPresenter;
import com.example.module_employees_world.ui.emoji.EmojiItemClickListener;
import com.example.module_employees_world.ui.emoji.EmojiKeyboardFragment;
import com.example.module_employees_world.ui.topic.LocalAlbumDetailActicity;
import com.example.module_employees_world.utils.EmojiUtils;
import com.example.module_employees_world.utils.PhotoBitmapUtils;
import com.example.module_employees_world.utils.RxBusMessageBean;
import com.example.module_employees_world.utils.SoftKeyboardUtils;
import com.thefinestartist.utils.log.LogUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.http.HttpConfig;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.image.GlideManager;
import com.wb.baselib.permissions.PerMissionsManager;
import com.wb.baselib.permissions.interfaces.PerMissionCall;
import com.wb.baselib.utils.StatusBarUtilNeiXun;
import com.wb.baselib.utils.ToastUtils;
import com.wb.rxbus.taskBean.RxBus;
import com.wngbo.www.common_postphoto.ISNav;
import com.wngbo.www.common_postphoto.config.ISListConfig;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * author:LIENLIN
 * date:2019/3/27
 * 评论窗口
 */
public class CommentDialogActivity extends MvpActivity<CommentSendDialogPresenter> implements CommentSendDialogContranct.ICommentSendDialogView, EmojiItemClickListener {

    public final int TAG_ACTIVTY_RESULT_CODE = 200;
    private ImageView ivEditArea, ivReplyOval, ivReplyPic, ivReplyret, ivReplyimg, ivReplyGif, ivDelGif, ivDelImg;
    private TextView tvParentName, tvReplySend;
    private LinearLayout llRoot, llBottom;
    private EditText etContent;
    private int initHeight;
    private int RootViewHeight;
    private int screenHeight;
    public static final String TAG_QUESTION_ID = "question_id";
    public static final String TAG_COMMENT_ID = "comment_id";
    public static final String TAG_COMMENT_NAME = "comment_name";
    //帖子id
    private String question_id;
    //父评论id，对帖子评论是默认为“0”
    private String comment_id;
    //评论对象名称
    private String comment_name = "";
    //评论图片地址
    private String commentPicture = "";
    //评论表情包地址
    private String commentFace = "";
    //1：不匿名
    private String isAnonymity = "0";
    //相册选择照片集合
    private List<String> result;
    //允许上传图片个数
    private int picNum = 1;
    //判断键盘是否显示/隐藏
    private boolean emojiKeyboardOpen = false;
    private EmojiKeyboardFragment emojiKeyboardFragment;
    private int heightDifference;
    private int viewHeight;
    private View view;
    private StringBuffer editTextSB = new StringBuffer();
    private RelativeLayout rlImg, rlGif;

    private boolean isHasPic=false;
    private boolean isHasGif=false;

    @Override
    protected CommentSendDialogPresenter onCreatePresenter() {
        return new CommentSendDialogPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //关闭基类状态栏设置
//        setStatusBarEnable(false);
        super.onCreate(savedInstanceState);
        StatusBarUtilNeiXun.setStatusLayout(this, Color.parseColor("#007AFF"));
        StatusBarUtilNeiXun.StatusBarDarkMode(this, StatusBarUtilNeiXun.StatusBarLightMode(this));
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {

        if (contentUri != null && contentUri.toString().toLowerCase().startsWith("content:")) {

        } else {
            return contentUri.getPath();
        }

        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return Uri.fromFile(new File(res)).getPath();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAG_ACTIVTY_RESULT_CODE) {
//            if (data == null) return;
//            result = data.getStringArrayListExtra("result");
//            if (result != null && result.size() > 0) {
//                Map<String, File> map = new HashMap<>();
//                for (int i = 0; i < result.size(); i++) {
//                    File file = new File(result.get(i));
//                    map.put("file" + i, file);
//                }
//                Map<String, RequestBody> bodyMap = HttpManager.newInstance().getRequestBodyMap(map, MediaType.parse("image/*"));
//                showLoadDiaLog("");
//                mPresenter.commitImage(bodyMap);
//            }
        } else if (requestCode == CommonUtils.REQUEST_CODE_GETIMAGE_BYCROP) {

            try {
                if (LocalImageHelper.getInstance().isResultOk()) {
                    LocalImageHelper.getInstance().setResultOk(false);
                    //获取选中的图片
                    List<LocalImageHelper.LocalFile> files = LocalImageHelper.getInstance().getCheckedItems();
                    String[] imgs = new String[files.size()];
                    for (int i = 0; i < files.size(); i++) {
                        String path = getRealPathFromURI(this, Uri.parse(files.get(i).getOriginalUri()));
                        String rotatPath = PhotoBitmapUtils.amendRotatePhoto(path, this);
                        imgs[i] = rotatPath;
                    }
                    //清空选中的图片
                    files.clear();
                    Map<String, File> map = new HashMap<>();
                    for (int i = 0; i < imgs.length; i++) {
                        File file = new File(imgs[i]);
                        map.put("file" + i, file);
                    }
                    Map<String, RequestBody> bodyMap = HttpManager.newInstance().getRequestBodyMap(map, MediaType.parse("image/*"));
                    etContent.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showLoadDiaLog("");
                        }
                    },300);
                    mPresenter.commitImage(bodyMap);

                } else {
                    if (data != null) {
                        String path = data.getStringExtra("mFileTemp");
                        String rotatPath = PhotoBitmapUtils.amendRotatePhoto(path, this);
                        String imgs[] = {rotatPath};
                        Map<String, File> map = new HashMap<>();
                        for (int i = 0; i < imgs.length; i++) {
                            File file = new File(imgs[i]);
                            map.put("file" + i, file);
                        }

                        Map<String, RequestBody> bodyMap = HttpManager.newInstance().getRequestBodyMap(map, MediaType.parse("image/*"));
                        etContent.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showLoadDiaLog("");
                            }
                        },300);
                        mPresenter.commitImage(bodyMap);
                    }
                }

                LocalImageHelper.getInstance().getCheckedItems().clear();

            } catch (Exception e) {
                LogUtil.e(e.toString());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getIntanceBus().unSubscribe(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_comment_dialog);
        question_id = getIntent().getStringExtra(TAG_QUESTION_ID);
        comment_id = getIntent().getStringExtra(TAG_COMMENT_ID);
        comment_name = getIntent().getStringExtra(TAG_COMMENT_NAME);
        ivEditArea = findViewById(R.id.iv_edit_area);
        ivReplyOval = findViewById(R.id.iv_reply_oval);
        ivReplyPic = findViewById(R.id.iv_reply_pic);
        ivReplyret = findViewById(R.id.iv_reply_ret);
        ivReplyimg = findViewById(R.id.iv_peply_img);
        ivReplyGif = findViewById(R.id.iv_peply_gif);
        ivDelImg = findViewById(R.id.iv_del_img);
        ivDelGif = findViewById(R.id.iv_del_gif);
        tvReplySend = findViewById(R.id.tv_reply_send);
        tvParentName = findViewById(R.id.tv_parent_name);
        etContent = findViewById(R.id.et_comment_text);
        llRoot = findViewById(R.id.ll_edit_root);
        llBottom = findViewById(R.id.ll_comment_bottom);
        rlImg = findViewById(R.id.rl_reply_img);
        rlGif = findViewById(R.id.rl_reply_gif);
        view = findViewById(R.id.tv_view);

        tvParentName.setText(comment_name + "（作者）");
        emojiKeyboardFragment = EmojiKeyboardFragment.newInstance(this, this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_dialog, emojiKeyboardFragment).commit();
        llRoot.post(new Runnable() {
            @Override
            public void run() {
                initHeight = llRoot.getHeight();
                screenHeight = CommentDialogActivity.this.getWindow().getDecorView().getRootView().getHeight();
            }
        });


        llRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                CommentDialogActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = CommentDialogActivity.this.getWindow().getDecorView().getRootView().getHeight();
                //获取键盘的高度，在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                heightDifference = screenHeight - r.bottom;
                viewHeight = view.getHeight();
                if (heightDifference > 0) {
                    //弹出了软键盘
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llBottom.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, heightDifference);
                    llBottom.setLayoutParams(layoutParams);
                } else {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llBottom.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    llBottom.setLayoutParams(layoutParams);
                }

            }
        });

        //相册初始化
        LocalImageHelper.init(this);
    }

    int oldHeight = 0;

    @Override
    protected void setListener() {
        /*ivEditArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int stateBarHeight = StatusBarUtilNeiXun.getStateBar(CommentDialogActivity.this);
                int targetHight = screenHeight - stateBarHeight;
                ValueAnimator valueAnimator = null;
                int emokiKeyheight = emojiKeyboardFragment.getmRootView().getHeight();
                if (viewHeight==stateBarHeight){
                    //需要收起
                    valueAnimator = ValueAnimator.ofInt(targetHight , initHeight);
                }else {
                    //需要展开
                    valueAnimator = ValueAnimator.ofInt(initHeight , targetHight);
                }
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int h =(Integer) animation.getAnimatedValue();
                        llRoot.getLayoutParams().height=h;
                        llRoot.requestLayout();
                    }
                });
                valueAnimator.setDuration(100);
                valueAnimator.start();
            }
        });*/

        ivEditArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvReplySend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encode = EmojiUtils.getString(editTextSB.toString());

                if (AppUtils.is_banned == 0) {
                    if (!TextUtils.isEmpty(encode) || !TextUtils.isEmpty(commentPicture) || !TextUtils.isEmpty(commentFace)) {
                        mPresenter.sendComment(question_id, encode, commentPicture, commentFace, isAnonymity, comment_id);
                        showLoadDiaLog("");
                    } else {
                        showShortToast("评论不能为空");
                    }
                }else{
                    showShortToast("你已被禁言");
                }
            }
        });
        ivReplyOval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/4/1 表情包
                if (emojiKeyboardFragment != null) {
                    emojiKeyboardOpen = !emojiKeyboardOpen;
                    if (emojiKeyboardOpen) {
                        //隐藏软键盘
                        SoftKeyboardUtils.hideSoftKeyboard(CommentDialogActivity.this);
                        //是否显示表情视图
                        emojiKeyboardFragment.setflEmojiContentShow(true);
                    } else {
                        //是否显示表情视图
                        emojiKeyboardFragment.setflEmojiContentShow(false);
                        //显示软键盘
                        SoftKeyboardUtils.showSoftKeyboard(CommentDialogActivity.this);
                    }
                }
            }
        });
        ivReplyPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // : 2019/4/1 相册
//                showAlbue(picNum);


                Intent intent = new Intent(CommentDialogActivity.this, LocalAlbumDetailActicity.class);
                intent.putExtra("pic_size", 0);
                intent.putExtra("maxicSize", 1);
                startActivityForResult(intent, CommonUtils.REQUEST_CODE_GETIMAGE_BYCROP);
            }
        });
        ivReplyret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                editTextSB.append("\n");
////                etContent.setText(editTextSB);

                //解决换行时，光标随着改变
                Editable editable = etContent.getText();
                int index = etContent.getSelectionStart();
                editable.insert(index, "\n");

            }
        });
        //删除图片
        ivDelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentPicture = "";
                picNum = 1;
                isHasPic=false;
                rlImg.setVisibility(View.GONE);
            }
        });
        //删除表情包
        ivDelGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentFace = "";
                isHasGif=false;
                rlGif.setVisibility(View.GONE);
            }
        });
        etContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideEmojiKeyboardFragment();
            }
        });
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextSB.delete(0, editTextSB.length());
                editTextSB.append(s.toString());
            }
        });

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }

    @Override
    public void ShowLoadView() {
    }

    @Override
    public void NoNetWork() {
    }

    @Override
    public void NoData() {
    }

    @Override
    public void ErrorData() {
    }

    @Override
    public void showErrorMsg(String msg) {
        hidLoadDiaLog();
        showShortToast(msg);
    }

    @Override
    public void showLoadV(String msg) {
    }

    @Override
    public void closeLoadV() {
    }

    @Override
    public void SuccessData(Object o) {
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void sendCommment(CommentInsertBean insertBean) {
        // TODO: 2019/4/3
        hidLoadDiaLog();
        /*Intent intent = new Intent();
        intent.putExtra("data",insertBean);
        intent.putExtra("comment_id",comment_id);
        setResult(RxBusMessageBean.MessageType.POST_114,intent);*/
        llRoot.postDelayed(new Runnable() {
            @Override
            public void run() {
                RxBus.getIntanceBus().post(new RxBusMessageBean(RxBusMessageBean.MessageType.POST_114, insertBean, comment_id));
                showShortToast("评论成功");
                finish();
            }
        },200);

    }

    @Override
    public void commitImage(List<NImageBean> pathList) {
        hidLoadDiaLog();
        if (pathList.size() > 0) {
            picNum = 0;
            rlImg.setVisibility(View.VISIBLE);
            commentPicture = HttpConfig.newInstance().getmBaseUrl() + "/"+pathList.get(0).getPath();
            GlideManager.getInstance().setCommonPhoto(ivReplyimg, R.drawable.course_image, this, HttpConfig.newInstance().getmBaseUrl() + "/" + pathList.get(0).getPath(), false);

            //如果上传图片，替换已有表情包
            isHasPic=true;
            if (isHasGif){
                isHasGif=false;
                commentFace="";
                ivReplyGif.setImageBitmap(null);
                rlGif.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 调起相册拍照
     *
     * @param picNum
     */
    private void showAlbue(final int picNum) {
        if (picNum > 0) {
            PerMissionsManager.newInstance().getUserPerMissions(this, new PerMissionCall() {
                @Override
                public void userPerMissionStatus(boolean b) {
                    if (b) {
                        ISListConfig config = new ISListConfig.Builder()
                                .multiSelect(true)
                                .rememberSelected(false)
                                .maxNum(picNum)
                                .needCamera(true)
                                // .backResId()
                                .build();
                        ISNav.getInstance().toListActivity(CommentDialogActivity.this, config, TAG_ACTIVTY_RESULT_CODE);
                    } else {
                        ToastUtils.showToast(CommentDialogActivity.this, "无相应权限");
                    }
                }
            }, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE});
        }
    }

    @Override
    public void onItemClick(EmojiconBean emojicon) {
        editTextSB.append(emojicon.emojiChart);
        etContent.setText(editTextSB.toString());
        etContent.setSelection(etContent.getText().length());
    }

    @Override
    public void onDeleteClick() {

        KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0,
                0, KeyEvent.KEYCODE_ENDCALL);
        etContent.dispatchKeyEvent(event);

    }

    @Override
    public void onItemClick(TutuIconBean tutuIconBean) {
        commentFace = tutuIconBean.key;
        rlGif.setVisibility(View.VISIBLE);
        GlideManager.getInstance().setGlideResourceImage(ivReplyGif, tutuIconBean.TutuId, R.drawable.image_failure, R.drawable.course_image, this);
// TODO: 2019/4/9
        //如果上传表情包，替换已有图片
        isHasGif=true;
        if (isHasPic){
            isHasPic=false;
            commentPicture="";
            ivReplyPic.setImageBitmap(null);
            rlImg.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDeleteTutuClick() {

    }

    /**
     * 表情视图隐藏
     */
    public void hideEmojiKeyboardFragment() {
        if (emojiKeyboardFragment != null) {
            emojiKeyboardOpen = false;
            //隐藏表情视图
            emojiKeyboardFragment.setflEmojiContentShow(false);
        }
    }


}
