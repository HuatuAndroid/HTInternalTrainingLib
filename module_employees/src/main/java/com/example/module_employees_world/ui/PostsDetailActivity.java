package com.example.module_employees_world.ui;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.adapter.ImgAdapter;
import com.example.module_employees_world.adapter.PostDetailAdapter;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.utils.StatusBarUtil;
import com.wb.baselib.utils.ToastUtils;
import com.wb.baselib.view.TopBarView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * 帖子详情
 */
public class PostsDetailActivity extends MvpActivity {

    private TopBarView topBarView;
    private RecyclerView mRvPost;
    private PostDetailAdapter postDetailAdapter;

    private RecyclerView tvImg;
    private RelativeLayout rlOpen;
    private TextView tvDetailText,tvName,tvOpen,tvHtml,tvClose,tvTitle;
    private LinearLayout llDev1,llDev2,llContainerFab;
    private ArrayList imgList;
    private NestedScrollView scvPost;
    private FloatingActionButton fabAll,fabEdit,fabTop;
    private boolean fabEnable;

    @Override
    protected BasePreaenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.setStatusLayout(this,Color.parseColor("#007AFF"));
        StatusBarUtil.StatusBarDarkMode(this, StatusBarUtil.StatusBarLightMode(this));
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_posts_detail);
        topBarView = findViewById(R.id.topbarview_post_detail);
        mRvPost = findViewById(R.id.rv_post_detail);
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

        imgList = new ArrayList<>();
        imgList.add("");
        imgList.add("");
        imgList.add("");
        imgList.add("");
        imgList.add("");
        // TODO: 2019/3/22 数据暂写死
        topBarView.getCenterTextView().setText("欢迎搭建来到这个瓶体多提宝贵意见");
        TextView centerTextView = topBarView.getCenterTextView();
        centerTextView.setVisibility(View.INVISIBLE);
        tvTitle.setText("欢迎搭建来到这个瓶体多提宝贵意见");
        tvDetailText.setText("        "+"halsdnfasfnlk哈快递费哈利的法拉发哪里上课积分拉克丝剪短发哪里看法呢时间段内发了看不见halsdnfasfnlk哈快递费哈利的法拉发哪里上课积分拉克丝剪短发哪里看法呢时间段内发了看不见halsdnfasfnlk哈快递费哈利的法拉发哪里上课积分拉克丝剪短发哪里看法呢时间段内发了看不见halsdnfasfnlk哈快递费哈利的法拉发哪里上课积分拉克丝剪短发哪里看法呢时间段内发了看不见"+"         ");
        tvImg.setLayoutManager(new GridLayoutManager(this,4));
        tvImg.setAdapter(new ImgAdapter(this,imgList,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(PostsDetailActivity.this,"点击看大图");
            }
        }));

        //显示“展开全部”条件：文本等于五行或者有图片
        tvDetailText.post(new Runnable() {
            @Override
            public void run() {
                if (tvDetailText.getLineCount()==5||imgList.size()>0){
                    tvOpen.setVisibility(View.VISIBLE);
                }else {
                    tvOpen.setVisibility(View.INVISIBLE);
                }
            }
        });

        // TODO: 2019/3/21 数据暂写死
        setActivityContent("<br\\/><img src='http:\\/\\/peixun.huatu.com\\/uploads\\/images\\/20190304\\/1de6158aab21bf817d82da7c59c3f872.jpg' width='100%' _src='http:\\/\\/peixun.huatu.com\\/uploads\\/images\\/20190304\\/1de6158aab21bf817d82da7c59c3f872.jpg'\\/>",tvHtml);

        postDetailAdapter = new PostDetailAdapter(this);
        mRvPost.setLayoutManager(new LinearLayoutManager(this));
        mRvPost.setAdapter(postDetailAdapter);
        mRvPost.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
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

    }

    @Override
    protected void setListener() {
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
                    animationSet.setInterpolator(new SpringInterpolator(0.5f));
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

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

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
    public  static  Bitmap  getFitSampleBitmap(InputStream  inputStream) throws Exception{
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

    public static class SpringInterpolator implements Interpolator {

        private float factor;

        public SpringInterpolator(float factor) {
            this.factor = factor;
        }

        @Override
        public float getInterpolation(float input) {
            return (float) (Math.pow(2, -10 * input) * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1);
        }
    }

}
