package com.zhiyun88.www.module_main.community.ui;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ReleaseOrUpdateTopicActivity extends BaseActivity {

    private Disposable subsLoading;
    private ProgressDialog loadingDialog;
    private RichTextEditor et_new_content;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        setListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666) {
            if (data == null) return;
            result = data.getStringArrayListExtra("result");
            for (int i = 0; i < result.size(); i++) {
                et_new_content.addImageViewAtIndex(et_new_content.getLastIndex(), result.get(i));
            }
        }
    }

    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.activity_release_or_update_topic);
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


        et_update_topic_title.setText(topicTitle);
        if (topicAnonymity.equals("0")){
            ivTopicNiMing.setImageResource(R.drawable.topic_update_niming1);
        }else {
            ivTopicNiMing.setImageResource(R.drawable.topic_update_niming2);
        }
        showDataSync(topicContent);

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
                    ISNav.getInstance().toListActivity(ReleaseOrUpdateTopicActivity.this, config, 666);
                }else {
                    ToastUtils.showToast(ReleaseOrUpdateTopicActivity.this,"无相应权限" );
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
                    List<RichTextEditor.EditData> editData = et_new_content.buildEditData();
                    ToastUtils.showToast(ReleaseOrUpdateTopicActivity.this,editData.size()+"");
                    // TODO: 2019/2/22 封装富文本，上传服务器

                }
            }
        });

        et_update_topic_title.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                ReleaseOrUpdateTopicActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight =  ReleaseOrUpdateTopicActivity.this.getWindow().getDecorView().getRootView().getHeight();
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

    @Override
    protected void onStop() {
        super.onStop();
        if (subsLoading != null && subsLoading.isDisposed()){
            subsLoading.dispose();
        }
    }

    /**
     * 异步方式显示数据
     * @param html
     */
    private void showDataSync(final String html){
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("数据加载中...");
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                showEditData(emitter, html);
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
            @Override
            public void onComplete() {
                if (loadingDialog != null){
                    loadingDialog.dismiss();
                }
                if (et_new_content != null) {
                    //在图片全部插入完毕后，再插入一个EditText，防止最后一张图片后无法插入文字
                    et_new_content.addEditTextAtIndex(et_new_content.getLastIndex(), "");
                }
            }

            @Override
            public void onError(Throwable e) {
                if (loadingDialog != null){
                    loadingDialog.dismiss();
                }
                ToastUtils.showToast(ReleaseOrUpdateTopicActivity.this,"解析错误：图片不存在或已损坏");
            }

            @Override
            public void onSubscribe(Disposable d) {
                subsLoading = d;
            }

            @Override
            public void onNext(String text) {
                if (et_new_content != null) {
                    if (text.contains("<img") && text.contains("src=")) {
                        //imagePath可能是本地路径，也可能是网络地址
                        String imagePath = StringUtils.getImgSrc(text);
                        //插入空的EditText，以便在图片前后插入文字
//                        et_new_content.addEditTextAtIndex(et_new_content.getLastIndex(), "");
                        et_new_content.addImageViewAtIndex(et_new_content.getLastIndex(), imagePath);
                    } else {
                        et_new_content.addEditTextAtIndex(et_new_content.getLastIndex(), "        "+text);
                    }
                }
            }
        });
    }
    /**
     * 显示数据
     */
    protected void showEditData(ObservableEmitter<String> emitter, String html) {
        try{
            List<String> textList = StringUtils.cutStringByImgTag(html);
            for (int i = 0; i < textList.size(); i++) {
//                String text =textList.get(i).replaceAll("<p>","").replaceAll("</p>","");
                String text =textList.get(i);
                emitter.onNext(text);
            }
            emitter.onComplete();
        }catch (Exception e){
            e.printStackTrace();
            emitter.onError(e);
        }
    }
}
