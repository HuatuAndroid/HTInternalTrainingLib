package com.example.module_employees_world.ui.topic;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.module_employees_world.R;
import com.example.module_employees_world.bean.TopicContentItem;
import com.example.module_employees_world.common.LocalImageHelper;
import com.example.module_employees_world.common.StartActivityCommon;
import com.example.module_employees_world.contranct.TopicEditContranct;
import com.example.module_employees_world.presenter.TopicEditPresenter;
import com.example.module_employees_world.ui.TopicEditView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.view.NCommontPopw;
import com.wb.baselib.view.TopBarView;

import java.io.File;
import java.util.List;

/**
 * @author liuzhe
 * @date 2019/3/22
 *
 * 发帖界面
 */
public class NTopicEditActivity extends MvpActivity<TopicEditPresenter> implements TopicEditContranct.View {

    private EditText mEtTopicTitle;
    private LinearLayout llBottom;
    private TopicEditView mTopicEditView;
    private TopBarView topBarView;
    private TextView mTvJiaoLiu, mTvJianYi, mTvTiWen, mTvXiaoXu;
    private ImageView mIvA, mIvPhotograph, mIvHyperLink, mIvPicture, mIvFace, mIvLineFeed;

    //退出时，弹框
    private NCommontPopw sureBackPopw;

    //1交流 2建议 3提问
    private int type = 1;

    private String groupId;

    private File mFileTemp;

    @Override
    protected TopicEditPresenter onCreatePresenter() {
        return new TopicEditPresenter(this, this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        setListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_GETIMAGE_BYCROP:
                try {
                    if (LocalImageHelper.getInstance().isResultOk()) {
                        LocalImageHelper.getInstance().setResultOk(false);
                        //获取选中的图片
                        List<LocalImageHelper.LocalFile> files = LocalImageHelper.getInstance().getCheckedItems();
                        String[] imgs = new String[files.size()];
                        for (int i = 0; i < files.size(); i++) {

                            String path = getRealPathFromURI(this, Uri.parse(files.get(i).getOriginalUri()));
                            imgs[i] = path;

                        }

                        //清空选中的图片
                        files.clear();
                        mTopicEditView.addImgs(imgs);
                    }
                    //清空选中的图片
                    LocalImageHelper.getInstance().getCheckedItems().clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case REQUEST_CODE_TAKE_PICTURE:
                if (resultCode == RESULT_OK) {

//                    if (mFileTemp != null)
//                        mTopicEditView.addImg(Uri.fromFile(mFileTemp).toString());
                }
                break;

            case SELECT_GROUP:

                if (data != null) {
                    groupId = data.getStringExtra("group_id");
                }

                break;

            default:
                break;
        }

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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.activity_ntopic_edit);

        groupId = getIntent().getStringExtra("groupId");

        topBarView = findViewById(R.id.topbarview);
        mEtTopicTitle = findViewById(R.id.mEtTopicTitle);
        mTopicEditView = findViewById(R.id.mTopicEditView);
        llBottom = findViewById(R.id.ll_bottom);
        mIvA = findViewById(R.id.mIvA);
        mIvPhotograph = findViewById(R.id.mIvPhotograph);
        mIvHyperLink = findViewById(R.id.mIvHyperLink);
        mIvPicture = findViewById(R.id.mIvPicture);
        mIvFace = findViewById(R.id.mIvFace);
        mIvLineFeed = findViewById(R.id.mIvLineFeed);

        mTvJiaoLiu= findViewById(R.id.mTvJiaoLiu);
        mTvJianYi= findViewById(R.id.mTvJianYi);
        mTvTiWen= findViewById(R.id.mTvTiWen);
        mTvXiaoXu= findViewById(R.id.mTvXiaoXu);

        //添加是否可编辑状态
        mTopicEditView.setEditable(true);
        mTopicEditView.setShowHint(true);
        mTopicEditView.setHint("输入内容");
        //输入的最大字符个数
        mTopicEditView.setMaxTxtCount(10000);
        mTopicEditView.setContentWatch(new TopicEditView.ContentWatch() {
            @Override
            public void onEmpty(boolean empty) {
                //当编辑为空时，回调
            }

            @Override
            public void onTypedCount(float count) {
                //编辑时，字符输入的个数回调
            }
        });
        mTopicEditView.addData(0, new TopicContentItem(""));

        //本地图片辅助类初始化
        LocalImageHelper.init(this);

    }

    @Override
    protected void setListener() {

        mTvJiaoLiu.setOnClickListener(this);
        mTvJianYi.setOnClickListener(this);
        mTvTiWen.setOnClickListener(this);
        mTvXiaoXu.setOnClickListener(this);

        mIvA.setOnClickListener(this);
        mIvPhotograph.setOnClickListener(this);
        mIvHyperLink.setOnClickListener(this);
        mIvPicture.setOnClickListener(this);
        mIvFace.setOnClickListener(this);
        mIvLineFeed.setOnClickListener(this);

        mTopicEditView.setOnClickListener(this);
        mTopicEditView.setItemClickListener((view, index) -> {
            if (view instanceof ImageView || view instanceof SubsamplingScaleImageView) {
                //TODO 点击图片，点击响应
            }
        });

        topBarView.setListener((v, action, extra) -> {
            //点击back键退出时，按键响应
            if (action == TopBarView.ACTION_LEFT_BUTTON) {
                sureBackPopw = new NCommontPopw(NTopicEditActivity.this, "确定放弃编辑吗?", v1 -> {
                    sureBackPopw.myDismiss();
                    NTopicEditActivity.this.finish();
                });

            } else if (action == TopBarView.ACTION_RIGHT_TEXT) {     //点击发布时，按键响应
//                String title = et_update_topic_title.getText().toString();
//                String content = et_new_content.getText().toString();
//                if (TextUtils.isEmpty(title)) {
//                    showShortToast("标题不能为空");
//                    return;
//                }
//                if (TextUtils.isEmpty(content)) {
//                    showShortToast("内容不能为空");
//                    return;
//                }
//
//                String toHtml = Html.toHtml(et_new_content.getText()).replace(" dir=\"ltr\"", "").replace("\n", "<br>");
//                toHtml = StringEscapeUtils.unescapeHtml4(toHtml);
//                String newTitle = et_update_topic_title.getText().toString();
//                // TODO: 2019/2/22 封装富文本，上传服务器
////                    mPresenter.sendUpdateTopic(topicId,newTitle,toHtml,topicAnonymity);
//                mPresenter.commitTopicData(groupId, title, content, newTitle, "");

            }
        });

        mEtTopicTitle.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            //获取当前界面可视部分
            NTopicEditActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
            //获取屏幕的高度
            int screenHeight = NTopicEditActivity.this.getWindow().getDecorView().getRootView().getHeight();
            //获取键盘的高度，在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
            int heightDifference = screenHeight - r.bottom;
            if (heightDifference > 0) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llBottom.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, heightDifference);
                llBottom.setLayoutParams(layoutParams);
            } else {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llBottom.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                llBottom.setLayoutParams(layoutParams);
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.mTvJiaoLiu) {
            //点击 交流
            type = 1;
            setUpdataUi_Type(mTvJiaoLiu, mTvJianYi, mTvTiWen);
        }else if (v.getId() == R.id.mTvJianYi) {
            //点击 建议
            type = 2;
            setUpdataUi_Type(mTvJianYi, mTvJiaoLiu, mTvTiWen);

        }else if (v.getId() == R.id.mTvTiWen) {
            //点击 提问
            type = 3;
            setUpdataUi_Type(mTvTiWen, mTvJiaoLiu, mTvJianYi);

        }else if (v.getId() == R.id.mTvXiaoXu) {
            //点击 选择小组
            StartActivityCommon.startActivityForResult(this,SelectGroupActivity.class, SELECT_GROUP);

        }else if (v.getId() == R.id.mIvA){
            //点击 @
        } else  if (v.getId() == R.id.mIvPhotograph){
            //点击 拍照

        } else  if (v.getId() == R.id.mIvHyperLink){
            //点击 连接

        } else  if (v.getId() == R.id.mIvPicture){
            //点击 照片
            Intent intent = new Intent(this, LocalAlbumDetailActicity.class);
            intent.putExtra("pic_size", mTopicEditView.getImageCount());
            startActivityForResult(intent, REQUEST_CODE_GETIMAGE_BYCROP);

        } else  if (v.getId() == R.id.mIvFace){
            //点击 表情

        } else  if (v.getId() == R.id.mIvLineFeed){
            //点击 换行
            mTopicEditView.AddLineFeed();
        }

    }

    /** 请求相册 */
    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0;
    /** 请求相机 */
    public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;
    /** 请求裁剪 */
    public static final int REQUEST_CODE_GETIMAGE_BYCROP = 2;

    public static final int REQUEST_CODE_TAKE_PICTURE = 3;
    /** 选择小组 */
    public static final int SELECT_GROUP = 4;

    @Override
    protected void processLogic(Bundle bundle) {
    }

    /**
     * 提交成功
     *
     * @param msg
     */
    @Override
    public void commitSuccess(String msg) {
        hidLoadDiaLog();
        showShortToast(msg);
//        RxBus.getIntanceBus().post(new RxMessageBean(852, "", ""));
        finish();
    }

    @Override
    public void updateTopicSuccess(String msg) {
        showShortToast(msg);
        finish();
    }

    @Override
    public void showErrorMsg(String s) {
        showShortToast(s);
//        hidLoadDiaLog();
    }

    @Override
    public void showLoadV(String s) {
    }

    @Override
    public void closeLoadV() {
    }

    @Override
    public void SuccessData(Object o) {
//        hidLoadDiaLog();
//        List<NImageBean> imageBeans = (List<NImageBean>) o;
//        for (int i = 0; i < imageBeans.size(); i++) {
//            // TODO: 2019/2/22 图片等待添加到光标处
//            int selectionStart = et_new_content.getSelectionStart();
//            Editable text = et_new_content.getText();
//            text.insert(selectionStart, "<br><img src=\"" + HttpConfig.newInstance().getmBaseUrl() + "/" + imageBeans.get(i).getPath() + "\"><br>");
//            //讲文本转为html格式重新加载数据
//            String toHtml = Html.toHtml(et_new_content.getText()).replace(" dir=\"ltr\"", "").replace("\n", "<br>");
//            toHtml = StringEscapeUtils.unescapeHtml4(toHtml);
//            setActivityContent(toHtml);
//        }
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }


    /**
     * 点击类型，修改ui
     * @param mTextView1
     * @param mTextView2
     * @param mTextView3
     */
    public void setUpdataUi_Type(TextView mTextView1, TextView mTextView2, TextView mTextView3){

        mTextView1.setTextColor(this.getResources().getColor(R.color.white));
        mTextView1.setBackground(this.getResources().getDrawable(R.drawable.shape_fill_ff4a88fb_r90));

        mTextView2.setTextColor(this.getResources().getColor(R.color.color_FF999CAA));
        mTextView2.setBackground(this.getResources().getDrawable(R.drawable.shape_border_ff999caa_r90));

        mTextView3.setTextColor(this.getResources().getColor(R.color.color_FF999CAA));
        mTextView3.setBackground(this.getResources().getDrawable(R.drawable.shape_border_ff999caa_r90));

    }

}
