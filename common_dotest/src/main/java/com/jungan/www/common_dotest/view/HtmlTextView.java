package com.jungan.www.common_dotest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.solver.Cache;
import android.support.v4.content.ContextCompat;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baijiayun.glide.request.target.SimpleTarget;
import com.baijiayun.glide.request.transition.Transition;
import com.bumptech.glide.Glide;
import com.jungan.www.common_dotest.R;
import com.jungan.www.common_dotest.adapter.CommonQuestionOptionAdapter;
import com.jungan.www.common_dotest.call.HtmlTextViewCall;
import com.jungan.www.common_dotest.ds;
import com.jungan.www.common_dotest.utils.StrUtils;
import com.jungan.www.common_dotest.utils.UILKit;
import com.wngbo.www.common_postphoto.ISNav;
import com.wngbo.www.common_postphoto.config.ISLookConfig;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.LinkHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.Callback;
import com.zzhoujay.richtext.callback.LinkFixCallback;
import com.zzhoujay.richtext.callback.OnImageClickListener;
import com.zzhoujay.richtext.callback.SimpleImageFixCallback;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.droidlover.xrichtext.ImageLoader;
import cn.droidlover.xrichtext.XRichText;
import me.wcy.htmltext.HtmlImageLoader;
import me.wcy.htmltext.HtmlText;
import me.wcy.htmltext.OnTagClickListener;

public class HtmlTextView extends RelativeLayout {
    private boolean isOption;
    private int errorOption,noDoOption,rightOption,errorImage;
    private TextView option_tv;
    private HtmlTextViewCall htmlTextViewCall;
    private RelativeLayout main_rel;
//    private XRichText mRichTv;
    private TextView      mRichTv;
    private View mView;
    private Context mContext;
    public void setOption(boolean option) {
        isOption = option;
        initData();
    }
    private void initData(){
        if(isOption){
            Log.e("进来了","------");
            LayoutParams params = new LayoutParams(dip2px(getContext(),35),dip2px(getContext(),35));
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.setMargins(dip2px(getContext(),10),dip2px(getContext(),10),0,dip2px(getContext(),10));
            option_tv.setLayoutParams(params);
            LayoutParams params1 = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
            params1.addRule(RelativeLayout.CENTER_VERTICAL);
            params1.addRule(RelativeLayout.RIGHT_OF,R.id.option_tv);
            params1.setMargins(dip2px(getContext(),10),dip2px(getContext(),10),dip2px(getContext(),10),dip2px(getContext(),10));
//            txt_tv.setLayoutParams(params1);
        }
        option_tv.setVisibility(isOption?VISIBLE:GONE);
    }
    public void setHtmlTextViewCall(HtmlTextViewCall htmlTextViewCall) {
        this.htmlTextViewCall = htmlTextViewCall;
    }

    public HtmlTextView(Context context) {
        this(context,null);
    }

    public HtmlTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HtmlTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initView(context,attrs);
    }
    private void initView(Context context,AttributeSet attributeSet){
         UILKit.init(mContext);        //初始化UIL
        TypedArray array=getContext().obtainStyledAttributes(attributeSet, R.styleable.HtmlTextView);
        isOption=array.getBoolean(R.styleable.HtmlTextView_isOption,false);
        errorOption=array.getResourceId(R.styleable.HtmlTextView_errorOption,R.drawable.error_bg);
        rightOption=array.getResourceId(R.styleable.HtmlTextView_rightOption,R.drawable.right_bg);
        noDoOption=array.getResourceId(R.styleable.HtmlTextView_noDoOption,R.drawable.nodo_bg);
        errorImage=array.getResourceId(R.styleable.HtmlTextView_errorImage,0);
        mView= LayoutInflater.from(context).inflate(R.layout.layout_html,this);
        mRichTv=mView.findViewById(R.id.txt_tv);
//        txt_tv=mView.findViewById(R.id.txt_tv);
        option_tv=mView.findViewById(R.id.option_tv);
        main_rel=mView.findViewById(R.id.main_rel);
    }
    /**
     * 展示选项  正确选项
     */
    public void showRightOption(){
        option_tv.setBackgroundResource(rightOption);
        option_tv.setSelected(true);
    }
    /**
     * 展示选项  错误选项
     */
    public void showErrorOption(){
        option_tv.setBackgroundResource(errorOption);
        option_tv.setSelected(true);
    }
    /**
     * 展示选项  未选择选项
     */
    public void showNoDoOption(){
        option_tv.setBackgroundResource(noDoOption);
        option_tv.setSelected(false);
    }

    /**
     * 设置类容
     * @param txt
     */
    public void showTxt(String txt){
        if(txt==null||txt.equals(""))
            return;
        mRichTv.setMovementMethod(LinkMovementMethod.getInstance());
        String sample="<u>njnkjnsvdn</u>";//"<p><strong>字体测试字体测试字体测试字体测试字体测试</strong></p><p><em><strong>字体测试字体测试字体测试字体测试字体测试</strong></em></p><p><span style=\"text-decoration: underline;\">字体测试字体测试字体测试字体测试字体测试</span></p><p><span style=\"text-decoration: line-through; color: rgb(255, 0, 0);\">字体测试字体测试字体测试字体测试字体测试</span></p><p><span style=\"text-decoration: line-through; color: rgb(255, 0, 0);\"><img src=\"http://test-px.huatu.com//uploads/ueditor/image/20181122/1542888683164610.jpg\" title=\"1542888683164610.jpg\" _src=\"http://test-px.huatu.com//uploads/ueditor/image/20181122/1542888683164610.jpg\" alt=\"0-3.jpg\"></span></p>";
        HtmlText.from(txt)
                .setImageLoader(new HtmlImageLoader() {
                    @Override
                    public void loadImage(String url, final Callback callback) {
                        com.baijiayun.glide.Glide.with(mContext).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                callback.onLoadComplete(resource);
                            }
                        });
                    }

                    @Override
                    public Drawable getDefaultDrawable() {
                        return ContextCompat.getDrawable(mContext, R.drawable.test_qst);
                    }

                    @Override
                    public Drawable getErrorDrawable() {
                        return ContextCompat.getDrawable(mContext, R.drawable.test_qst);
                    }

                    @Override
                    public int getMaxWidth() {
                        return getTextWidth(mRichTv);
                    }

                    @Override
                    public boolean fitWidth() {
                        return false;
                    }
                })
                .setOnTagClickListener(new OnTagClickListener() {
                    @Override
                    public void onImageClick(Context context, List<String> imageUrlList, int position) {
                        // image click
                        if(imageUrlList==null){
                            return;
                        }
                        ISLookConfig isLookConfig=new ISLookConfig.Builder().setLock(false).setStartNum(0).setPhotoPaths(imageUrlList).build();
                        ISNav.getInstance().toLookPhotoActivity(mContext,isLookConfig);
                        if(htmlTextViewCall==null)
                            return;
                        htmlTextViewCall.imageClicked(imageUrlList, position);
                    }

                    @Override
                    public void onLinkClick(Context context, String url) {
                        // link click
                    }
                })
                .into(mRichTv);


    }
    private int getTextWidth(TextView textView) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        return dm.widthPixels - textView.getPaddingLeft() - textView.getPaddingRight();
    }
    /**
     * 根据数字自动设置 A-Z
     * @param num
     */
    public void setAuotOption(int num){
        option_tv.setText(StrUtils.Instance().numberToLetter(num));
    }

    /**
     * 用户设置选项
     * @param txt
     */
    public void setOption(String txt){
        option_tv.setText(txt);
    }

    /**
     * 根据选项 得到顺序  只有自动排序时生效
     * @param le
     * @return
     */
    public int getOptionNum(String le){
        return StrUtils.Instance().letterToNumber(le);
    }
    private   int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public void onClickOption(OnClickListener onClickListener){
        main_rel.setOnClickListener(onClickListener);
        option_tv.setOnClickListener(onClickListener);
        mRichTv.setOnClickListener(onClickListener);
    }
}
