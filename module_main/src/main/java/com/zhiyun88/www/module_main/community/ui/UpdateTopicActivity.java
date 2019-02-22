package com.zhiyun88.www.module_main.community.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sendtion.xrichtext.RichTextEditor;
import com.wb.baselib.base.activity.BaseActivity;
import com.wb.baselib.permissions.PerMissionsManager;
import com.wb.baselib.permissions.interfaces.PerMissionCall;
import com.wb.baselib.utils.ToastUtils;
import com.wb.baselib.view.TopBarView;
import com.wngbo.www.common_postphoto.ISNav;
import com.wngbo.www.common_postphoto.config.ISListConfig;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.utils.StringUtils;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UpdateTopicActivity extends BaseActivity {

    private EditText et_new_content;
    private EditText et_update_topic_title;
    private LinearLayout llBottom;
    private TopBarView topBarView;;
    public static final String TOPIN_TITLE="topicTitle";
    public static final String TOPIN_CONTENT="topicContent";
    public static final String TOPIN_NIMING="niming";
    private String topicContent;
    private String topicTitle;
    private String topicAnonymity;//0：实名   1：匿名
    private ImageView ivTopicCamera;
    private ImageView ivTopicNiMing;
    private List<String> result;
    private MyHandle myHandle;
    public final int TAG_1=100;
    public final int TAG_ACTIVTY_RESULT_CODE=200;
    private int picWidth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myHandle = new MyHandle(this);
        initView(savedInstanceState);
        setListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAG_ACTIVTY_RESULT_CODE) {
            if (data == null) return;
            result = data.getStringArrayListExtra("result");
            for (int i = 0; i < result.size(); i++) {
                // TODO: 2019/2/22 图片等待添加到光标处  暂写死
                int selectionStart = et_new_content.getSelectionStart();
                Editable text = et_new_content.getText();
                text.insert(selectionStart,"<img src=\""+"http:\\/\\/peixun.huatu.com\\/uploads\\/images\\/20190128\\/b0ca76bee951b1ee7c25ef55726a48b4.png"+"\"/>");
                //讲文本转为html格式重新加载数据
                String toHtml = Html.toHtml(et_new_content.getText()).replace(" dir=\"ltr\"", "").replace("\n", "<br>");
                toHtml = StringEscapeUtils.unescapeHtml4(toHtml);
                ToastUtils.showToast(UpdateTopicActivity.this,"修改后标签："+toHtml);
                setActivityContent(toHtml);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myHandle!=null){
            myHandle.removeMessages(TAG_1);
            myHandle.removeCallbacksAndMessages(null);
            myHandle=null;
        }
    }

    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.activity_update_topic);
        topicTitle = getIntent().getStringExtra(TOPIN_TITLE);
        topicContent = getIntent().getStringExtra(TOPIN_CONTENT);
        topicAnonymity = getIntent().getStringExtra(TOPIN_NIMING);

        topBarView = findViewById(R.id.topbarview);
        et_new_content = findViewById(R.id.et_new_content);
        et_update_topic_title = findViewById(R.id.et_update_topic_title);
        llBottom = findViewById(R.id.ll_bottom);
        ivTopicCamera = findViewById(R.id.iv_update_topic_camare);
        ivTopicNiMing = findViewById(R.id.iv_update_topic_niming);
        ivTopicCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlbue();
            }
        });
        ivTopicNiMing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topicAnonymity.equals("0")){
                    ivTopicNiMing.setImageResource(R.drawable.topic_update_niming2);
                    topicAnonymity="1";
                }else {
                    ivTopicNiMing.setImageResource(R.drawable.topic_update_niming1);
                    topicAnonymity="0";
                }
            }
        });

        et_new_content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                picWidth = et_new_content.getWidth();
            }
        });


        et_update_topic_title.setText(topicTitle);
        setActivityContent(topicContent);
        if (topicAnonymity.equals("0")){
            ivTopicNiMing.setImageResource(R.drawable.topic_update_niming1);
        }else {
            ivTopicNiMing.setImageResource(R.drawable.topic_update_niming2);
        }

    }

    private void setActivityContent(final String activityContent) {
//        final int screenWidth = (int) (getWindowManager().getDefaultDisplay().getWidth()*0.95);
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
                        int height = (int) (((float)picWidth / minimumWidth) * minimumHeight);
                        drawable.setBounds(0, 0,picWidth ,height);
                        return drawable;
                    }
                };
                CharSequence charSequence = Html.fromHtml(activityContent.trim(), imageGetter, null);
                Message ms = Message.obtain();
                ms.what = TAG_1;
                ms.obj = charSequence;
                myHandle.sendMessage(ms);
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

    public Drawable getBase64ImageNetwork(String imageUrl) {
        byte[] decode = Base64.decode(imageUrl.split(",")[1], Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
        return new BitmapDrawable(bitmap);
    }


    /**
     * 调起相册拍照
     */
    private void showAlbue() {
        PerMissionsManager.newInstance().getUserPerMissions(this, new PerMissionCall() {
            @Override
            public void userPerMissionStatus(boolean b) {
                if (b) {
                    ISListConfig config = new ISListConfig.Builder()
                            .multiSelect(true)
                            .rememberSelected(true)
                            .maxNum(9)
                            .needCamera(true)
                            // .backResId()
                            .build();
                    ISNav.getInstance().toListActivity(UpdateTopicActivity.this, config, TAG_ACTIVTY_RESULT_CODE);
                }else {
                    ToastUtils.showToast(UpdateTopicActivity.this,"无相应权限" );
                }
            }
        },new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE});

    }

    @Override
    protected void setListener() {
        topBarView.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == TopBarView.ACTION_LEFT_BUTTON) {
                    finish();
                }else if (action == TopBarView.ACTION_RIGHT_TEXT){
                    String toHtml = Html.toHtml(et_new_content.getText()).replace(" dir=\"ltr\"", "").replace("\n", "<br>");

                    toHtml = StringEscapeUtils.unescapeHtml4(toHtml);
                    ToastUtils.showToast(UpdateTopicActivity.this,"修改后标签："+toHtml);
                    // TODO: 2019/2/22 封装富文本，上传服务器

                }
            }
        });

        et_update_topic_title.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                UpdateTopicActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight =  UpdateTopicActivity.this.getWindow().getDecorView().getRootView().getHeight();
                //获取键盘的高度，在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                if (heightDifference>0){
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llBottom.getLayoutParams();
                    layoutParams.setMargins(0,0,0,heightDifference);
                    llBottom.setLayoutParams(layoutParams);
                }else {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llBottom.getLayoutParams();
                    layoutParams.setMargins(0,0,0,0);
                    llBottom.setLayoutParams(layoutParams);
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle bundle) {
    }

    static class MyHandle extends Handler{
        WeakReference<UpdateTopicActivity> mActivity;
        public MyHandle(UpdateTopicActivity activity) {
            mActivity=new WeakReference<UpdateTopicActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            UpdateTopicActivity updateTopicActivity = mActivity.get();
            switch (msg.what) {
                case 100:
                    CharSequence charSequence = (CharSequence) msg.obj;
                    if (charSequence != null) {
                        updateTopicActivity.et_new_content.setText(charSequence);
//                        updateTopicActivity.et_new_content.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                    break;
                default:
                    break;
            }

        }
    }

}
