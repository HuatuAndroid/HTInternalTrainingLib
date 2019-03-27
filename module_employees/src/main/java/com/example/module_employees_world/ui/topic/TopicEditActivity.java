package com.example.module_employees_world.ui.topic;

import android.Manifest;
import android.content.Context;
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
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.bean.NImageBean;
import com.example.module_employees_world.contranct.TopicEditContranct;
import com.example.module_employees_world.presenter.TopicEditPresenter;
import com.thefinestartist.utils.log.LogUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.http.HttpConfig;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.permissions.PerMissionsManager;
import com.wb.baselib.permissions.interfaces.PerMissionCall;
import com.wb.baselib.utils.ToastUtils;
import com.wb.baselib.view.NCommontPopw;
import com.wb.baselib.view.TopBarView;
import com.wngbo.www.common_postphoto.ISNav;
import com.wngbo.www.common_postphoto.config.ISListConfig;

import org.apache.commons.lang3.StringEscapeUtils;
import org.xml.sax.XMLReader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author liuzhe
 * @date 2019/3/22
 *
 * 原来  发帖界面
 */
public class TopicEditActivity extends MvpActivity<TopicEditPresenter> implements TopicEditContranct.View {

    public final int TAG_1 = 100;
    public final int TAG_ACTIVTY_RESULT_CODE = 200;

    private EditText et_new_content;
    private EditText et_update_topic_title;
    private LinearLayout llBottom;
    private TopBarView topBarView;
    private ScrollView mScrollView;
    private TextView mTvJiaoLiu, mTvJianYi, mTvTiWen, mTvXiaoXu;
    private ImageView mIvA, mIvPhotograph, mIvHyperLink, mIvPicture, mIvFace, mIvLineFeed;

    private List<String> result;
    private MyHandle myHandle;

    private int picWidth;
    //已上传图片个数
    private int counter = 0;
    private NCommontPopw sureBackPopw;

    private String groupId;
    //1交流 2建议 3提问
    private int type = 1;

    @Override
    protected TopicEditPresenter onCreatePresenter() {
        return new TopicEditPresenter(this, this);
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
            if (result != null && result.size() > 0) {
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
        if (myHandle != null) {
            myHandle.removeMessages(TAG_1);
            myHandle.removeCallbacksAndMessages(null);
            myHandle = null;
        }
        if (sureBackPopw != null && sureBackPopw.isShowing())
            sureBackPopw.myDismiss();

        super.onDestroy();
    }

    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.activity_topic_edit);

        groupId = getIntent().getStringExtra("groupId");

        mScrollView = findViewById(R.id.mScrollView);
        topBarView = findViewById(R.id.topbarview);
        et_new_content = findViewById(R.id.et_new_content);
        et_update_topic_title = findViewById(R.id.et_update_topic_title);
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

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (sureBackPopw != null && sureBackPopw.isShowing())
            return false;

        return super.dispatchTouchEvent(ev);
    }

    private void setActivityContent(final String activityContent) {
        new Thread(() -> {
            Html.ImageGetter imageGetter = source -> {
                Drawable drawable;
                //临时解决方案，介于目前前端上传图片没base64编码，web端有base64编码，判断是否通过base64编码临时解决
                if (source.startsWith("data:image/")) {
                    drawable = getBase64ImageNetwork(source);
                } else {
                    drawable = getImageNetwork(source);
                }

                if (drawable == null) {
                    drawable = getResources().getDrawable(R.drawable.image_failure);
                }
                int minimumWidth = drawable.getMinimumWidth();
                int minimumHeight = drawable.getMinimumHeight();
                int height = (int) (((float) picWidth / minimumWidth) * minimumHeight);
                drawable.setBounds(0, 0, picWidth, height);
                return drawable;
            };
            CharSequence charSequence = Html.fromHtml(activityContent.trim(), imageGetter, new DetailTagHandler(this));
            Message ms = Message.obtain();
            ms.what = TAG_1;
            ms.obj = charSequence;
            myHandle.sendMessage(ms);
        }).start();
    }

    /**
     * 发起图片请求
     *
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


    public static Bitmap getFitSampleBitmap(InputStream inputStream) throws Exception {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.ALPHA_8;
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
                        ISNav.getInstance().toListActivity(TopicEditActivity.this, config, TAG_ACTIVTY_RESULT_CODE);
                    } else {
                        ToastUtils.showToast(TopicEditActivity.this, "无相应权限");
                    }
                }
            }, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE});
        }
    }

    @Override
    protected void setListener() {

        mScrollView.setOnClickListener(this);

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

        et_new_content.getViewTreeObserver().addOnGlobalLayoutListener(() -> picWidth = et_new_content.getWidth());

        topBarView.setListener((v, action, extra) -> {
            if (action == TopBarView.ACTION_LEFT_BUTTON) {
                sureBackPopw = new NCommontPopw(TopicEditActivity.this, "确定放弃编辑吗?", v1 -> {
                    sureBackPopw.myDismiss();
                    TopicEditActivity.this.finish();
                });

            } else if (action == TopBarView.ACTION_RIGHT_TEXT) {
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
//                    mPresenter.sendUpdateTopic(topicId,newTitle,toHtml,topicAnonymity);
                mPresenter.commitTopicData(groupId, title, content, newTitle, "");

            }
        });

        et_update_topic_title.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            //获取当前界面可视部分
            TopicEditActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
            //获取屏幕的高度
            int screenHeight = TopicEditActivity.this.getWindow().getDecorView().getRootView().getHeight();
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

        }else if (v.getId() == R.id.mIvA){
            //点击 @
        } else  if (v.getId() == R.id.mIvPhotograph){
            //点击 拍照

        } else  if (v.getId() == R.id.mIvHyperLink){
            //点击 连接

        } else  if (v.getId() == R.id.mIvPicture){
            //点击 照片
            counter = 0;
            //通过判断字符串确定当前输入已经有几张图片
            String toHtml = Html.toHtml(et_new_content.getText()).replace(" dir=\"ltr\"", "").replace("\n", "<br>");
            toHtml = StringEscapeUtils.unescapeHtml4(toHtml);
            int imgNum = countStr(toHtml, "<img src=");
            showAlbue(9 - imgNum);
        } else  if (v.getId() == R.id.mIvFace){
            //点击 表情

        } else  if (v.getId() == R.id.mIvLineFeed){
            //点击 换行

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
    public void showLoadV(String s) {
    }

    @Override
    public void closeLoadV() {
    }

    @Override
    public void SuccessData(Object o) {
//        hidLoadDiaLog();
        List<NImageBean> imageBeans = (List<NImageBean>) o;
        for (int i = 0; i < imageBeans.size(); i++) {
            // TODO: 2019/2/22 图片等待添加到光标处
            int selectionStart = et_new_content.getSelectionStart();
            Editable text = et_new_content.getText();
            text.insert(selectionStart, "<br><img src=\"" + HttpConfig.newInstance().getmBaseUrl() + "/" + imageBeans.get(i).getPath() + "\"><br>");
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

    static class MyHandle extends Handler {
        WeakReference<TopicEditActivity> mActivity;

        public MyHandle(TopicEditActivity activity) {
            mActivity = new WeakReference<TopicEditActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TopicEditActivity editTopicActivity = mActivity.get();
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
     *
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
     * 判断str1中包含str2的个数t
     *
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

    public class DetailTagHandler implements Html.TagHandler {
        private Context context;
        private ArrayList<String> strings;

        public DetailTagHandler(Context context) {
            this.context = context;
            strings = new ArrayList<>();
        }

        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            // 处理标签<img>
            if ("img".equals(tag.toLowerCase(Locale.getDefault()))) {
                // 获取长度
                int len = output.length();
                // 获取图片地址
                ImageSpan[] images = output.getSpans(len - 1, len, ImageSpan.class);
                String imgURL = images[0].getSource();
                // 记录所有图片地址
                strings.add(imgURL);
                // 记录是第几张图片
                int position = strings.size()-1;
                // 使图片可点击并监听点击事件
                output.setSpan(new ClickableImage(context, position), len - 1, len,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        private class ClickableImage extends ClickableSpan {
            private Context context;
            private int position;

            public ClickableImage(Context context, int position) {
                this.context = context;
                this.position = position;
            }

            @Override
            public void onClick(View widget) {
                LogUtil.e("onClick ----- " + "onClick");
            }
        }
    }

}
