package com.example.module_employees_world.ui.topic;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.module_employees_world.R;
import com.example.module_employees_world.bean.EmojiconBean;
import com.example.module_employees_world.bean.TopicContentItem;
import com.example.module_employees_world.bean.TutuIconBean;
import com.example.module_employees_world.common.CommonUtils;
import com.example.module_employees_world.common.LocalImageHelper;
import com.example.module_employees_world.common.StartActivityCommon;
import com.example.module_employees_world.contranct.TopicEditContranct;
import com.example.module_employees_world.presenter.TopicEditPresenter;
import com.example.module_employees_world.utils.AndroidBug5497Workaround;
import com.example.module_employees_world.utils.EmojiUtils;
import com.example.module_employees_world.view.TopicEditView;
import com.example.module_employees_world.ui.emoji.EmojiItemClickListener;
import com.example.module_employees_world.ui.emoji.EmojiKeyboardFragment;
import com.example.module_employees_world.utils.SoftKeyboardUtils;
import com.hss01248.dialog.StyledDialog;
import com.thefinestartist.utils.log.LogUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.http.HttpConfig;
import com.wb.baselib.utils.StatusBarUtil;
import com.wb.baselib.utils.ToastUtils;
import com.wb.baselib.view.NCommontPopw;
import com.wb.baselib.view.TopBarView;

import java.io.File;
import java.util.List;

/**
 * @author liuzhe
 * @date 2019/3/22
 * <p>
 * 发帖界面
 */
public class NTopicEditActivity extends MvpActivity<TopicEditPresenter> implements TopicEditContranct.View, EmojiItemClickListener {

    private EditText mEtTopicTitle;
    private LinearLayout llBottom, mLinearLayout, mViewInputField;
    private RelativeLayout mHideView, mScrollView;
    private TopicEditView mTopicEditView;
    private TopBarView topBarView;
    private TextView mTvJiaoLiu, mTvJianYi, mTvTiWen, mTvXiaoXu;
    private ImageView mIvA, mIvPhotograph, mIvHyperLink, mIvPicture, mIvFace, mIvLineFeed;

    private EmojiKeyboardFragment emojiKeyboardFragment;

    //退出时，弹框
    private NCommontPopw sureBackPopw;

    //1交流 2建议 3提问
    private int type = 1;

    //小组的id和名字
    private String groupId = "";
    private String groupName = "";

    private File mFileTemp;

    //判断键盘是否显示/隐藏
    private boolean emojiKeyboardOpen = false;

    private Dialog mDiaLog;

    @Override
    protected TopicEditPresenter onCreatePresenter() {
        return new TopicEditPresenter(this, this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusLayout(this, Color.parseColor("#007AFF"));
        StatusBarUtil.StatusBarDarkMode(this, StatusBarUtil.StatusBarLightMode(this));
        initView(savedInstanceState);
        setListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CommonUtils.REQUEST_CODE_GETIMAGE_BYCROP:
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
                    } else {
                        if (data != null) {
                            String path = data.getStringExtra("mFileTemp");
                            String ints[] = {path};
                            mTopicEditView.addImgs(ints);
                        }
                    }
                    //清空选中的图片
                    LocalImageHelper.getInstance().getCheckedItems().clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case CommonUtils.SELECT_GROUP:

                if (data != null) {
                    groupId = data.getStringExtra("group_id");
                    groupName = data.getStringExtra("groupName");

                    setmTvXiaoXuText(groupName);
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
        if (sureBackPopw != null){
            sureBackPopw.clearFlags();
            sureBackPopw = null;
        }
    }

    @Override
    protected void initView(Bundle bundle) {

        setContentView(R.layout.activity_ntopic_edit);

        groupId = getIntent().getStringExtra("groupId");
        groupName = getIntent().getStringExtra("groupName");
        mScrollView = findViewById(R.id.mScrollView);
        topBarView = findViewById(R.id.topbarview);
        mEtTopicTitle = findViewById(R.id.mEtTopicTitle);
        mTopicEditView = findViewById(R.id.mTopicEditView);
        llBottom = findViewById(R.id.ll_bottom);
        mLinearLayout = findViewById(R.id.mLinearLayout);
        mIvA = findViewById(R.id.mIvA);
        mIvPhotograph = findViewById(R.id.mIvPhotograph);
        mIvHyperLink = findViewById(R.id.mIvHyperLink);
        mIvPicture = findViewById(R.id.mIvPicture);
        mIvFace = findViewById(R.id.mIvFace);
        mIvLineFeed = findViewById(R.id.mIvLineFeed);

        mTvJiaoLiu = findViewById(R.id.mTvJiaoLiu);
        mTvJianYi = findViewById(R.id.mTvJianYi);
        mTvTiWen = findViewById(R.id.mTvTiWen);
        mTvXiaoXu = findViewById(R.id.mTvXiaoXu);
        mHideView = findViewById(R.id.mHideView);
        mViewInputField = findViewById(R.id.mViewInputField);
        getSupportFragmentManager().beginTransaction().replace(R.id.flEmoji,
                emojiKeyboardFragment = EmojiKeyboardFragment.newInstance(this, this)).commit();

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

            @Override
            public void hideEmojiKeyboard() {
                mHideView(false);
                hideEmojiKeyboardFragment();
            }

            @Override
            public void deletePic(View view) {
                sureBackPopw = new NCommontPopw(NTopicEditActivity.this, "确定删除图片吗?", v1 -> {

                    mTopicEditView.delete(view);
                    sureBackPopw.myDismiss();

                });
            }
        });
        mTopicEditView.addData(0, new TopicContentItem(""));

        //本地图片辅助类初始化
        LocalImageHelper.init(this);

        mEtTopicTitle.setFocusable(true);
        mEtTopicTitle.setFocusableInTouchMode(true);
        mEtTopicTitle.requestFocus();

//        mEtTopicTitle.postDelayed(() -> SoftKeyboardUtils.showORhideSoftKeyboard(NTopicEditActivity.this), 1000);

        mHideView(true);

        setmTvXiaoXuText(groupName);

        AndroidBug5497Workaround.assistActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void setListener() {
        mScrollView.setOnClickListener(this);
        mTvJiaoLiu.setOnClickListener(this);
        mTvJianYi.setOnClickListener(this);
        mTvTiWen.setOnClickListener(this);
        mTvXiaoXu.setOnClickListener(this);
        mHideView.setOnClickListener(this);

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

                isContentEmpty();
            } else if (action == TopBarView.ACTION_RIGHT_TEXT) {     //点击发布时，按键响应

                String title = mEtTopicTitle.getText().toString();
                List<TopicContentItem> datas = mTopicEditView.getDatas();
                if (TextUtils.isEmpty(title)) {
                    showShortToast("标题不能为空");
                    return;
                }
                if (datas == null || datas.size() == 0) {
                    showShortToast("内容不能为空");
                    return;
                }

                if (TextUtils.isEmpty(groupId) || "".equals(groupId)) {
                    showShortToast("请选择小组");
                    return;
                }

                showLoadV("提交中....");

                TopicContentItem[] imageItems = mTopicEditView.getImageItems();

                if (imageItems != null && imageItems.length != 0) {
                    mPresenter.processImage(imageItems);
                } else {
                    mPresenter.processData(getData());
                }

            }
        });

//        mLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
//            Rect r = new Rect();
//            //获取当前界面可视部分
//            NTopicEditActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
//            //获取屏幕的高度
//            int screenHeight = NTopicEditActivity.this.getWindow().getDecorView().getRootView().getHeight();
//            //获取键盘的高度，在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
//            int heightDifference = screenHeight - r.bottom;
//            if (heightDifference > 0) {
//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llBottom.getLayoutParams();
//                int height = llBottom.getHeight();
//                layoutParams.setMargins(0, 0, 0, heightDifference);
//                llBottom.setLayoutParams(layoutParams);
//            } else {
//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llBottom.getLayoutParams();
//                layoutParams.setMargins(0, 0, 0, 0);
//                llBottom.setLayoutParams(layoutParams);
//            }
//        });

        mEtTopicTitle.setOnClickListener((v) -> {
            mHideView(true);
            //隐藏表情视图
            hideEmojiKeyboardFragment();

        });

        mEtTopicTitle.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mHideView(true);
                //隐藏表情视图
                hideEmojiKeyboardFragment();
            }

        });
    }

    /**
     * 修改ui， 选择小组
     *
     * @param groupName
     */
    public void setmTvXiaoXuText(String groupName) {

        if (TextUtils.isEmpty(groupName)) {
            mTvXiaoXu.setText("选择小组");
        } else {
            mTvXiaoXu.setText(groupName);
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.mTvJiaoLiu) {
            //点击 交流
            type = 1;
            setUpdataUi_Type(mTvJiaoLiu, mTvJianYi, mTvTiWen);
        } else if (v.getId() == R.id.mTvJianYi) {
            //点击 建议
            type = 2;
            setUpdataUi_Type(mTvJianYi, mTvJiaoLiu, mTvTiWen);

        } else if (v.getId() == R.id.mTvTiWen) {
            //点击 提问
            type = 3;
            setUpdataUi_Type(mTvTiWen, mTvJiaoLiu, mTvJianYi);

        } else if (v.getId() == R.id.mTvXiaoXu) {
            //点击 选择小组
            //先要判断是否选择过小组
            if (TextUtils.isEmpty(groupId) || "".equals(groupId)){
                StartActivityCommon.startActivityForResult(this, SelectGroupActivity.class, CommonUtils.SELECT_GROUP);
            }else{
                Intent intent = new Intent(this, SelectGroupActivity.class);
                intent.putExtra("groupId", groupId);
                startActivityForResult(intent, CommonUtils.SELECT_GROUP);
            }

        } else if (v.getId() == R.id.mIvA) {
            //点击 @
        } else if (v.getId() == R.id.mIvPhotograph) {
            //点击 拍照

        } else if (v.getId() == R.id.mIvHyperLink) {
            //点击 连接
            if (emojiKeyboardOpen) {
                //隐藏软键盘
                SoftKeyboardUtils.hideSoftKeyboard(this);
                //隐藏表情视图
                hideEmojiKeyboardFragment();
            }

            myAlertDialog();

        } else if (v.getId() == R.id.mIvPicture) {
            //点击 照片
            Intent intent = new Intent(this, LocalAlbumDetailActicity.class);
            intent.putExtra("pic_size", mTopicEditView.getImageCount());
            intent.putExtra("maxicSize", 9);
            startActivityForResult(intent, CommonUtils.REQUEST_CODE_GETIMAGE_BYCROP);

        } else if (v.getId() == R.id.mIvFace) {
            //点击 表情
            if (emojiKeyboardFragment != null) {
                emojiKeyboardOpen = !emojiKeyboardOpen;
                if (emojiKeyboardOpen) {
                    //隐藏软键盘
                    SoftKeyboardUtils.hideSoftKeyboard(this);
                    //是否显示表情视图
                    emojiKeyboardFragment.setflEmojiContentShow(true);
                } else {

                    View focusedChild = mTopicEditView.getFocusedChild();
                    EditText editText = null;
                    if (focusedChild instanceof EditText) {
                        editText = (EditText) focusedChild;
                    }

                    if (editText != null) {
                        SoftKeyboardUtils.showSoftKeyboard(editText);
                    } else {
                        //显示软键盘
                        SoftKeyboardUtils.showSoftKeyboard(mEtTopicTitle);
                    }

                    //是否显示表情视图
                    emojiKeyboardFragment.setflEmojiContentShow(false);

                }

            }

        } else if (v.getId() == R.id.mIvLineFeed) {
            //点击 换行
            mTopicEditView.AddLineFeed(0, null);
        } else if (v.getId() == R.id.mHideView) {
            hideEmojiKeyboardFragment();
            SoftKeyboardUtils.hideSoftKeyboard(this);
        } else if (v.getId() == R.id.mScrollView) {

            hideEmojiKeyboardFragment();

            View childAt = mTopicEditView.getChildAt(0);

            if (childAt != null) {

                if (childAt instanceof EditText) {
                    EditText editText = (EditText) childAt;

                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();

                    SoftKeyboardUtils.showSoftKeyboard(editText);

                }
            }

        }

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
    public void showLoadV(String msg) {
        mDiaLog = StyledDialog.buildLoading(msg).show();
    }

    @Override
    public void closeLoadV() {
        if (mDiaLog == null)
            return;
        if (mDiaLog.isShowing()) {
            mDiaLog.dismiss();
        }
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
     *
     * @param mTextView1
     * @param mTextView2
     * @param mTextView3
     */
    public void setUpdataUi_Type(TextView mTextView1, TextView mTextView2, TextView mTextView3) {

        mTextView1.setTextColor(this.getResources().getColor(R.color.white));
        mTextView1.setBackground(this.getResources().getDrawable(R.drawable.shape_fill_ff4a88fb_r90));

        mTextView2.setTextColor(this.getResources().getColor(R.color.color_FF999CAA));
        mTextView2.setBackground(this.getResources().getDrawable(R.drawable.shape_border_ff999caa_r90));

        mTextView3.setTextColor(this.getResources().getColor(R.color.color_FF999CAA));
        mTextView3.setBackground(this.getResources().getDrawable(R.drawable.shape_border_ff999caa_r90));

    }

    private void myAlertDialog() {

        Dialog dialog = new Dialog(this, R.style.MDialogNoPadding);
        // 设置它的ContentView
        dialog.setContentView(R.layout.dialog_insert_connect);

        EditText mEtConnect = dialog.findViewById(R.id.mEtConnect);
        EditText mEtConnectContent = dialog.findViewById(R.id.mEtConnectContent);
        TextView tvDialogLeft = dialog.findViewById(R.id.tvDialogLeft);
        TextView tvDialogRight = dialog.findViewById(R.id.tvDialogRight);

        tvDialogLeft.setOnClickListener(v -> {
            dialog.dismiss();
        });

        /**
         * 插入超链接判断
         *
         * 需要去除 空格数据，
         */
        tvDialogRight.setOnClickListener(v -> {

            String mEtConnectString = mEtConnect.getText().toString();

            String mEtConnectContentString = mEtConnectContent.getText().toString();

            SpannableString spannableString;

            if (TextUtils.isEmpty(mEtConnectString)) {

//                spannableString = new SpannableString(mEtConnectContentString);
                ToastUtils.showToast(this, "请输入链接地址");
                return;

            }else{

                if (TextUtils.isEmpty(mEtConnectContentString)){

                    mEtConnectContentString = mEtConnectString;

                }

                spannableString = new SpannableString(mEtConnectContentString);

                spannableString.setSpan(new URLSpan(mEtConnectString), 0, spannableString.length(),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }

            mTopicEditView.AddConnect(spannableString);

            dialog.dismiss();
        });


        if (!isFinishing()) {
            dialog.show();
        }

        //过滤掉 输入的空格
        InputFilter filter= (source, start, end, dest, dstart, dend) -> {
            if(" ".equals(source)){
                return "";
            }else{
                return null;
            }
        };
        mEtConnect.setFilters(new InputFilter[]{filter});
        mEtConnectContent.setFilters(new InputFilter[]{filter});

    }

    /**
     * emoji点击表情传回的数据
     */
    @Override
    public void onItemClick(EmojiconBean emojicon) {
        mTopicEditView.AddLineFeed(1, emojicon);
    }

    /**
     * emoji点击删除
     */
    @Override
    public void onDeleteClick() {

        View focusedChild = mTopicEditView.getFocusedChild();
        if (focusedChild instanceof EditText) {
            EditText editText = (EditText) focusedChild;

            if (editText == null) {
                return;
            }
            KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0,
                    0, KeyEvent.KEYCODE_ENDCALL);
            editText.dispatchKeyEvent(event);
        }

    }

    /**
     * 兔兔表情点击传回的数据
     *
     * @param tutuIconBean
     */
    @Override
    public void onItemClick(TutuIconBean tutuIconBean) {
        LogUtil.e("onItemClick -- " + tutuIconBean.type + " --- " + tutuIconBean.key);

        mTopicEditView.addTutuImg(tutuIconBean.TutuId, tutuIconBean.key);
    }

    /**
     * emoji点击删除
     */
    @Override
    public void onDeleteTutuClick() {

    }

    @Override
    public List<TopicContentItem> getData() {

        return mTopicEditView.getDatas();
    }

    @Override
    public void commitTopicData(String content) {

        try {

            LogUtil.e("commitTopicData = " + content + "");

            String encode = EmojiUtils.getString(content);

            LogUtil.e("commitTopicData = " + encode + "");

            mPresenter.commitTopicData(groupId, mEtTopicTitle.getText().toString(), encode, 0 + "", type + "");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 显示/隐藏输入按钮， ： 表情、换行...
     *
     * @param boo
     */
    public void mHideView(boolean boo) {

        if (boo) {
            mViewInputField.setVisibility(View.GONE);
        } else {
            mViewInputField.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 判断内容是否为空
     * @return
     */
    public void isContentEmpty(){

        boolean isEmpty = false;

        List<TopicContentItem> topicContentItems = mTopicEditView.getDatas();

        for (TopicContentItem topicContentItem : topicContentItems) {
            if (TopicContentItem.TYPE_IMG.equals(topicContentItem.type.toString())) {

                if (!"".equals(topicContentItem.localUrl)) {
                    isEmpty = true;
                    break;
                }

            } else if (TopicContentItem.TYPE_TXT.equals(topicContentItem.type.toString())) {

                if (!"".equals(topicContentItem.content)) {
                    isEmpty = true;
                    break;
                }
            }
        }

        if ("".equals(mEtTopicTitle.getText().toString()) && !isEmpty) {

            NTopicEditActivity.this.finish();
            return ;
        }

        sureBackPopw = new NCommontPopw(NTopicEditActivity.this, "确定放弃编辑吗?", v1 -> {
            sureBackPopw.myDismiss();
            NTopicEditActivity.this.finish();
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                isContentEmpty();
                return true;
            case KeyEvent.KEYCODE_MENU:
                break;
            case KeyEvent.KEYCODE_HOME:
                break;
            case KeyEvent.KEYCODE_APP_SWITCH:
                break;
            default:
                break;
        }

        return super.onKeyDown(keyCode, event);

    }

}
