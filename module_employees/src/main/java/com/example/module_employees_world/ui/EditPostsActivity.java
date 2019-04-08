package com.example.module_employees_world.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.bean.EmojiconBean;
import com.example.module_employees_world.bean.NImageBean;
import com.example.module_employees_world.bean.TutuIconBean;
import com.example.module_employees_world.common.CommonUtils;
import com.example.module_employees_world.common.LocalImageHelper;
import com.example.module_employees_world.common.StartActivityCommon;
import com.example.module_employees_world.common.TutuPicInit;
import com.example.module_employees_world.contranct.EditPoatsContranct;
import com.example.module_employees_world.presenter.EditPostsPresenter;
import com.example.module_employees_world.ui.emoji.EmojiItemClickListener;
import com.example.module_employees_world.ui.emoji.EmojiKeyboardFragment;
import com.example.module_employees_world.ui.topic.LocalAlbumDetailActicity;
import com.example.module_employees_world.ui.topic.NTopicEditActivity;
import com.example.module_employees_world.ui.topic.SelectGroupActivity;
import com.example.module_employees_world.utils.EmojiUtils;
import com.example.module_employees_world.utils.SoftKeyboardUtils;
import com.example.module_employees_world.view.TopicEditView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.http.HttpConfig;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.view.NCommontPopw;
import com.wb.baselib.view.TopBarView;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 员工天地-编辑帖子
 * author:LIENLIN
 * date:2019/4/5
 */
public class EditPostsActivity extends MvpActivity<EditPostsPresenter> implements EditPoatsContranct.IEditPoatsView,EmojiItemClickListener {


    private EditText mEtTopicTitle,etContent;
    private LinearLayout llBottom, mLinearLayout, mViewInputField;
    private RelativeLayout mHideView;
    private TopBarView topBarView;
    private TextView mTvJiaoLiu, mTvJianYi, mTvTiWen, mTvXiaoXu;
    private ImageView mIvA, mIvPhotograph, mIvHyperLink, mIvPicture, mIvFace, mIvLineFeed;

    private EmojiKeyboardFragment emojiKeyboardFragment;
    private NCommontPopw sureBackPopw;
    public final static String TAG_TITLE_STR="title";
    public final static String TAG_CONTENT_STR="content";
    public final static String TAG_CONTENT_ID="contentid";
    public final static int TAG_1=100;
    private int picWidth;
    private MyHandle myHandle;
    //判断键盘是否显示/隐藏
    private boolean emojiKeyboardOpen = false;
    //1交流 2建议 3提问
    private int type = 1;
    //已上传图片个数
    private int picNum;
    private String groupId;
    private String editContent;
    private String title;
    private String commentId;

    @Override
    protected EditPostsPresenter onCreatePresenter() {
        return new EditPostsPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myHandle = new MyHandle(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CommonUtils.REQUEST_CODE_GETIMAGE_BYCROP:
                try {
                    if (LocalImageHelper.getInstance().isResultOk()) {
                        LocalImageHelper.getInstance().setResultOk(false);
                        //的获取选中图片
                        List<LocalImageHelper.LocalFile> files = LocalImageHelper.getInstance().getCheckedItems();
                        String[] imgs = new String[files.size()];
                        for (int i = 0; i < files.size(); i++) {
                            String path = getRealPathFromURI(this, Uri.parse(files.get(i).getOriginalUri()));
                            imgs[i] = path;
                        }
                        //清空选中的图片
                        files.clear();
                        Map<String, File> map = new HashMap<>();
                        for (int i = 0; i < imgs.length; i++) {
                            File file = new File(imgs[i]);
                            map.put("file" + i, file);
                        }
                        Map<String, RequestBody> bodyMap = HttpManager.newInstance().getRequestBodyMap(map, MediaType.parse("image*//*"));
                        showLoadDiaLog("");
                        mPresenter.commitImage(bodyMap);
                    } else {
                        if (data != null) {
                            String path = data.getStringExtra("mFileTemp");
                            String imgs[] = {path};
                            Map<String, File> map = new HashMap<>();
                            for (int i = 0; i < imgs.length; i++) {
                                File file = new File(imgs[i]);
                                map.put("file" + i, file);
                            }
                            Map<String, RequestBody> bodyMap = HttpManager.newInstance().getRequestBodyMap(map, MediaType.parse("image*//*"));
                            showLoadDiaLog("");
                            mPresenter.commitImage(bodyMap);
                        }
                    }
                    //清空选中的图片
                    LocalImageHelper.getInstance().getCheckedItems().clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                /*if (data == null) return;
                List<String> result = data.getStringArrayListExtra("result");
                if (result != null && result.size() > 0) {
                    Map<String, File> map = new HashMap<>();
                    for (int i = 0; i < result.size(); i++) {
                        File file = new File(result.get(i));
                        map.put("file" + i, file);
                    }
                    Map<String, RequestBody> bodyMap = HttpManager.newInstance().getRequestBodyMap(map, MediaType.parse("image*//*"));
                    showLoadDiaLog("");
                    mPresenter.commitImage(bodyMap);
                }*/
                break;
            case CommonUtils.SELECT_GROUP:
                if (data != null) {
                    groupId = data.getStringExtra("group_id");
                }
                break;
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        if (myHandle!=null){
            myHandle.removeMessages(TAG_1);
            myHandle.removeCallbacksAndMessages(null);
            myHandle=null;
        }
        if (sureBackPopw!=null&&sureBackPopw.isShowing())
            sureBackPopw.myDismiss();

        super.onDestroy();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_posts);
        title = getIntent().getStringExtra(TAG_TITLE_STR);
        editContent = getIntent().getStringExtra(TAG_CONTENT_STR);
        commentId = getIntent().getStringExtra(TAG_CONTENT_ID);

        topBarView = findViewById(R.id.topbarview);
        mEtTopicTitle = findViewById(R.id.mEtTopicTitle);
        etContent = findViewById(R.id.et_new_content);
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
        etContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                picWidth = etContent.getWidth();
            }
        });
        //本地图片辅助类初始化
        LocalImageHelper.init(this);
        mEtTopicTitle.setText(title);
        mEtTopicTitle.setFocusable(true);
        mEtTopicTitle.setFocusableInTouchMode(true);
        mEtTopicTitle.requestFocus();

        mHideView(true);
        setActivityContent(editContent,etContent);

    }

    @Override
    protected void setListener() {
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

        topBarView.getLeftImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sureBackPopw = new NCommontPopw(EditPostsActivity.this, "确定放弃编辑吗?", v1 -> {
                    sureBackPopw.myDismiss();
                    EditPostsActivity.this.finish();
                });
            }
        });
        topBarView.getRightTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/4/6 发布按钮
                Log.d("aaaaaa",etContent.getText().toString());
                String newTitle = mEtTopicTitle.getText().toString();
                mPresenter.commitTopicData(commentId,newTitle,editContent,"0",type+"");
            }
        });
        mEtTopicTitle.setOnClickListener((v) -> {
            mHideView(true);
            //隐藏表情视图
            hideEmojiKeyboardFragment();
        });
        /*etContent.setOnClickListener((v) -> {
            mHideView(false);
            //隐藏表情视图
            hideEmojiKeyboardFragment();

        });*/
        etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    mHideView(false);
                }else {
                    mHideView(true);
                }
                //隐藏表情视图
                hideEmojiKeyboardFragment();
            }
        });
        String oldStr="";
        etContent.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("edittext", "beforeTextChanged   CharSequence:(" + s + ");  start: (" + start + "); count: (" + count + "); after: (" + after + ")");

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("edittext", "onTextChanged   CharSequence:(" + s + ");  start: (" + start + "); before: (" + before + "); count: (" + count + ")");

            }
            @Override
            public void afterTextChanged(Editable s) {
                // TODO: 2019/4/7
//                editContent=s.toString();
                int selectionEnd = etContent.getSelectionEnd();
                int selectionStart = etContent.getSelectionStart();
                Log.d("aaaaa",s.toString());
                Log.d("aaaaa","selectionStart:"+selectionStart+",selectionEnd:"+selectionEnd);
            }
        });

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
            StartActivityCommon.startActivityForResult(this, SelectGroupActivity.class, CommonUtils.SELECT_GROUP);

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
            intent.putExtra("pic_size", picNum);
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
                    //显示软键盘
                    SoftKeyboardUtils.showSoftKeyboard(mEtTopicTitle);
                    //是否显示表情视图
                    emojiKeyboardFragment.setflEmojiContentShow(false);
                }
            }

        } else if (v.getId() == R.id.mIvLineFeed) {
            //点击 换行
            int index = etContent.getSelectionStart();
            etContent.setText(editContent);
            Editable editable = etContent.getText();
            editable.insert(index,"</br>");
            editContent=editable.toString();
            setActivityContent(editContent,etContent);
        }else if (v.getId() == R.id.mHideView){
            hideEmojiKeyboardFragment();
            SoftKeyboardUtils.hideSoftKeyboard(this);
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

    /**
     * 点击类型，修改ui
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

        tvDialogRight.setOnClickListener(v -> {
            String mEtConnectString = mEtConnect.getText().toString();
            String mEtConnectContentString = mEtConnectContent.getText().toString();
            // TODO: 2019/4/6 插入超链接
//            mTopicEditView.AddConnect(mEtConnectString,mEtConnectContentString);
            String httpStr="<a href=\""+mEtConnectString+"\">"+mEtConnectContentString+"</a>";
            int selectionEnd = etContent.getSelectionEnd();
            etContent.setText(editContent);
            Editable editable = etContent.getText();
            editable.insert(selectionEnd,httpStr);
            editContent=editable.toString();
            setActivityContent(editContent,etContent);
            dialog.dismiss();
        });

        if (!isFinishing()) {
            dialog.show();
        }
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onItemClick(EmojiconBean emojicon) {
//        int selectionEnd = etContent.getSelectionEnd();
        etContent.setText(editContent);
        Editable editable = etContent.getText();
        editable.insert(etContent.getSelectionEnd(),emojicon.emojiChart);
//        etContent.setSelection(selectionEnd+1);
        editContent=editable.toString();
        setActivityContent(editContent,etContent);
    }

    @Override
    public void onDeleteClick() {

    }

    @Override
    public void onItemClick(TutuIconBean tutuIconBean) {
        // TODO: 2019/4/6
//        int selectionEnd = etContent.getSelectionEnd();
        etContent.setText(editContent);
        Editable editable = etContent.getText();
        editable.insert(etContent.getSelectionEnd(),tutuIconBean.key);
        editContent=editable.toString();

        setActivityContent(editContent,etContent);
    }

    @Override
    public void onDeleteTutuClick() {

    }

    /**
     * 加载HTML文本
     * @param activityContent
     * @param tvHtml
     */
    private void setActivityContent(final String activityContent, final EditText tvHtml) {
        //表情解码
        String decodeContent = new String(EmojiUtils.decode(activityContent.trim()));
        for (int i = 0; i < TutuPicInit.EMOJICONS.size(); i++) {
            String key = TutuPicInit.EMOJICONS.get(i).key;
            if (decodeContent.contains(key)){
                String content="<\\br><img src=\"date:res="+TutuPicInit.EMOJICONS.get(i).TutuId+"\"><\\br>";
                decodeContent=decodeContent.replace(key,content);
            }
        }

        final int screenWidth = (int) (getWindowManager().getDefaultDisplay().getWidth()*0.95);
        String finalDecodeContent = decodeContent;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Html.ImageGetter imageGetter = new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        Drawable drawable;
                        int resType;
                        picNum++;
                        //临时解决方案，介于目前前端上传图片没base64编码，web端有base64编码，判断是否通过base64编码临时解决
                        if (source.startsWith("data:image/")){
                            drawable = getBase64ImageNetwork(source);
                            resType=0;
                        }else if (source.startsWith("date:res")){
                            String resId = source.substring(source.indexOf("=")+1, source.length());
                            drawable = getResources().getDrawable(Integer.valueOf(resId));
                            resType=1;
                        }else {
                            drawable = getImageNetwork(source);
                            resType=2;
                        }

                        if (drawable == null) {
                            drawable = getResources().getDrawable(R.drawable.image_failure);
                            resType=0;
                        }
                        switch (resType){
                            case 0:
                            case 2:
                                int minimumWidth = drawable.getMinimumWidth();
                                int minimumHeight = drawable.getMinimumHeight();
                                int height = (int) (((float)screenWidth / minimumWidth) * minimumHeight);
                                drawable.setBounds(0, 0,screenWidth ,height);
                                break;
                            case 1:
                                //GIF大小暂写死
                                drawable.setBounds(0, 0,360 ,360);
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
     * 发起图片请求
     * @param imageUrl
     * @return
     */
    public Drawable getImageNetwork(String imageUrl) {

        URL myFileUrl = null;
        Drawable drawable = null;
        try {
            myFileUrl = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            // 在这一步最好先将图片进行压缩，避免消耗内存过多
            Bitmap bitmap = getFitSampleBitmap(is);
            drawable = new BitmapDrawable(bitmap);
            //再次休息100毫秒，等待图片加载
            Thread.sleep(100);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;
    }


    public  static  Bitmap  getFitSampleBitmap(InputStream  inputStream) throws Exception{
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig=Bitmap.Config.ALPHA_8;
        byte[] bytes = readStream(inputStream);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
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

    public Drawable getBase64ImageNetwork(String imageUrl) {
        byte[] decode = Base64.decode(imageUrl.split(",")[1], Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
        return new BitmapDrawable(bitmap);
    }

    /**
     * 显示/隐藏输入按钮， ： 表情、换行...
     * @param boo
     */
    public void mHideView(boolean boo){
        if (boo) {
            mViewInputField.setVisibility(View.GONE);
        }else{
            mViewInputField.setVisibility(View.VISIBLE);
        }
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
    public void commitImage(List<NImageBean> pathList) {
        picNum+=pathList.size();
        for (int i = 0; i < pathList.size(); i++) {
            // TODO: 2019/2/22 图片等待添加到光标处
            int selectionStart = etContent.getSelectionStart();
            etContent.setText(editContent);
            Editable text = etContent.getText();
            text.insert(selectionStart,"<br><img src=\""+ HttpConfig.newInstance().getmBaseUrl()+"/"+pathList.get(i).getPath()+"\"><br>");
            editContent=text.toString();
            //讲文本转为html格式重新加载数据
            String toHtml = Html.toHtml(etContent.getText()).replace(" dir=\"ltr\"", "").replace("\n", "<br>");
            toHtml = StringEscapeUtils.unescapeHtml4(toHtml);
            setActivityContent(toHtml,etContent);
        }
    }

    @Override
    public void commitTopicData(String content) {
        // TODO: 2019/4/6 提交编辑
        finish();
    }

    static class MyHandle extends Handler {
        WeakReference<EditPostsActivity> mActivity;
        public MyHandle(EditPostsActivity activity) {
            mActivity=new WeakReference<EditPostsActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            EditPostsActivity editTopicActivity = mActivity.get();
            switch (msg.what) {
                case 100:
                    CharSequence charSequence = (CharSequence) msg.obj;
                    if (charSequence != null) {
                        editTopicActivity.etContent.setText(charSequence);
//                        editTopicActivity.et_new_content.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                    break;
                default:
                    break;
            }

        }
    }

}