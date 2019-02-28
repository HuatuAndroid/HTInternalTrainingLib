package com.zhiyun88.www.module_main.community.ui;

import android.Manifest;
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
import android.text.TextUtils;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hss01248.dialog.interfaces.MyDialogListener;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.http.HttpConfig;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.permissions.PerMissionsManager;
import com.wb.baselib.permissions.interfaces.PerMissionCall;
import com.wb.baselib.utils.ToastUtils;
import com.wb.baselib.view.TopBarView;
import com.wngbo.www.common_postphoto.ISNav;
import com.wngbo.www.common_postphoto.config.ISListConfig;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.bean.ImageBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.UpdateTopicContranct;
import com.zhiyun88.www.module_main.community.mvp.presenter.EditTopicPresenter;
import com.zhiyun88.www.module_main.community.view.CommontPopw;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 员工天地-编辑帖子
 * author:LIENLIN
 * date:2019/2/22
 */
public class EditTopicActivity extends MvpActivity<EditTopicPresenter> implements UpdateTopicContranct.UpdateTopicView {

    private EditText et_new_content;
    private EditText et_update_topic_title;
    private LinearLayout llBottom;
    private TopBarView topBarView;;
    public static final String TOPIN_ID="topicId";
    public static final String TOPIN_TITLE="topicTitle";
    public static final String TOPIN_CONTENT="topicContent";
    public static final String TOPIN_NIMING="niming";
    private String topicContent;
    private String topicId;
    private String topicTitle;
    private String topicAnonymity;//0：实名   1：匿名
    private ImageView ivTopicCamera;
    private ImageView ivTopicNiMing;
    private List<String> result;
    private MyHandle myHandle;
    public final int TAG_1=100;
    public final int TAG_ACTIVTY_RESULT_CODE=200;
    private int picWidth;
    //已上传图片个数
    private int counter=0;
    private CommontPopw sureBackPopw;

    @Override
    protected EditTopicPresenter onCreatePresenter() {
        return new EditTopicPresenter(this);
    }

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
            //先把图片传服务器 todo
            if (result!=null&&result.size()>0){
                Map<String, File> map = new HashMap<>();
                for (int i = 0; i < result.size(); i++) {
                    File file = new File(result.get(i));
                    map.put("file" + i, file);
                }
                Map<String, RequestBody> bodyMap = HttpManager.newInstance().getRequestBodyMap(map, MediaType.parse("image/*"));
                mPresenter.commitImage(bodyMap);
                showLoadDiaLog("");
            }
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
    protected void initView(Bundle bundle) {
        setContentView(R.layout.activity_update_topic);
        topicId = getIntent().getStringExtra(TOPIN_ID);
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
                counter=0;
                //通过判断字符串确定当前输入已经有几张图片
                String toHtml = Html.toHtml(et_new_content.getText()).replace(" dir=\"ltr\"", "").replace("\n", "<br>");
                toHtml = StringEscapeUtils.unescapeHtml4(toHtml);
                int imgNum = countStr(toHtml, "<img src=");
                showAlbue(9-imgNum);
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (sureBackPopw!=null&&sureBackPopw.isShowing())
            return false;

        return super.dispatchTouchEvent(ev);
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

            /*BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is,null,options);
            options.inSampleSize=4;
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);*/

            Bitmap bitmap = getFitSampleBitmap(is);
            drawable = new BitmapDrawable(bitmap);

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
        /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, baos);*/

        options.inSampleSize = 2;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
//        return bitmap;
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


    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
//        bitmap.setConfig(Bitmap.Config.RGB_565);
        return bitmap;
    }

    public Drawable getBase64ImageNetwork(String imageUrl) {
        byte[] decode = Base64.decode(imageUrl.split(",")[1], Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
        return new BitmapDrawable(bitmap);
    }


    /**
     * 调起相册拍照
     * @param picNum
     */
    private void showAlbue(final int picNum) {
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
                    ISNav.getInstance().toListActivity(EditTopicActivity.this, config, TAG_ACTIVTY_RESULT_CODE);
                }else {
                    ToastUtils.showToast(EditTopicActivity.this,"无相应权限" );
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
                    sureBackPopw = new CommontPopw(EditTopicActivity.this, "确定放弃编辑吗?", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sureBackPopw.myDismiss();
                            EditTopicActivity.this.finish();
                        }
                    });

                }else if (action == TopBarView.ACTION_RIGHT_TEXT){
                    String title = et_update_topic_title.getText().toString();
                    String content = et_new_content.getText().toString();
                    if (TextUtils.isEmpty(title)) {
                        showShortToast("标题不能为空");
                        return;
                    }
                    if (TextUtils.isEmpty(content)) {
                        showShortToast("内容不能为空");
                        return;
                    }

                    String toHtml = Html.toHtml(et_new_content.getText()).replace(" dir=\"ltr\"", "").replace("\n", "<br>");
                    toHtml = StringEscapeUtils.unescapeHtml4(toHtml);
                    String newTitle = et_update_topic_title.getText().toString();
                    // TODO: 2019/2/22 封装富文本，上传服务器
                    mPresenter.sendUpdateTopic(topicId,newTitle,toHtml,topicAnonymity);
                }
            }
        });

        et_update_topic_title.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                EditTopicActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight =  EditTopicActivity.this.getWindow().getDecorView().getRootView().getHeight();
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
        List<ImageBean> imageBeans = (List<ImageBean>) o;
        for (int i = 0; i < imageBeans.size(); i++) {
            // TODO: 2019/2/22 图片等待添加到光标处
            int selectionStart = et_new_content.getSelectionStart();
            Editable text = et_new_content.getText();
            text.insert(selectionStart,"<br><img src=\""+ HttpConfig.newInstance().getmBaseUrl()+"/"+imageBeans.get(i).getPath()+"\"><br>");
            //讲文本转为html格式重新加载数据
            String toHtml = Html.toHtml(et_new_content.getText()).replace(" dir=\"ltr\"", "").replace("\n", "<br>");
            toHtml = StringEscapeUtils.unescapeHtml4(toHtml);
            setActivityContent(toHtml);
        }
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    static class MyHandle extends Handler{
        WeakReference<EditTopicActivity> mActivity;
        public MyHandle(EditTopicActivity activity) {
            mActivity=new WeakReference<EditTopicActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            EditTopicActivity editTopicActivity = mActivity.get();
            switch (msg.what) {
                case 100:
                    CharSequence charSequence = (CharSequence) msg.obj;
                    if (charSequence != null) {
                        editTopicActivity.et_new_content.setText(charSequence);
//                        editTopicActivity.et_new_content.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                    break;
                default:
                    break;
            }

        }
    }

    /**
     * 压缩本地图片
     * @param srcPath
     * @return
     */
    private Bitmap compressImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//
        float ww = 480f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        //其实是无效的,大家尽管尝试
        return bitmap;
    }

    /**
     * 判断str1中包含str2的个数
     * @param str1
     * @param str2
     * @return counter
     */
    public int countStr(String str1, String str2) {
        if (str1.indexOf(str2) == -1) {
            return 0;
        } else if (str1.indexOf(str2) != -1) {
            counter++;
            countStr(str1.substring(str1.indexOf(str2) +
                    str2.length()), str2);
            return counter;
        }
        return 0;
    }

}
