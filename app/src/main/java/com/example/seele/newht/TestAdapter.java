package com.example.seele.newht;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baijiayun.glide.Glide;
import com.baijiayun.glide.request.target.SimpleTarget;
import com.baijiayun.glide.request.transition.Transition;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.Callback;
import com.zzhoujay.richtext.callback.OnImageClickListener;
import com.zzhoujay.richtext.callback.SimpleImageFixCallback;

import java.util.List;

import cn.droidlover.xrichtext.XRichText;
import me.wcy.htmltext.HtmlImageLoader;
import me.wcy.htmltext.HtmlText;
import me.wcy.htmltext.OnTagClickListener;

public class TestAdapter extends BaseAdapter {
    private Context mContext;

    public TestAdapter( Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TestHolder holder=null;
        if(convertView==null){
            holder=new TestHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.test_item,null);
            holder.tv_tv=convertView.findViewById(R.id.tv_tv);
            convertView.setTag(holder);
        }else {
            holder= (TestHolder) convertView.getTag();
        }
        holder.tv_tv.setMovementMethod(LinkMovementMethod.getInstance());
        String sample="<p><strong>字体测试字体测试字体测试字体测试字体测试</strong></p><p><em><strong>字体测试字体测试字体测试字体测试字体测试</strong></em></p><p><span style=\"text-decoration: underline;\">字体测试字体测试字体测试字体测试字体测试</span></p><p><span style=\"text-decoration: line-through; color: rgb(255, 0, 0);\">字体测试字体测试字体测试字体测试字体测试</span></p><p><span style=\"text-decoration: line-through; color: rgb(255, 0, 0);\"><img src=\"http://test-px.huatu.com//uploads/ueditor/image/20181122/1542888683164610.jpg\" title=\"1542888683164610.jpg\" _src=\"http://test-px.huatu.com//uploads/ueditor/image/20181122/1542888683164610.jpg\" alt=\"0-3.jpg\"></span></p>";
        final TestHolder finalHolder = holder;
        HtmlText.from(sample)
                .setImageLoader(new HtmlImageLoader() {
                    @Override
                    public void loadImage(String url, final Callback callback) {
                        Glide.with(mContext).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                callback.onLoadComplete(resource);
                            }
                        });


                        // Glide sample, you can also use other image loader
//                        Glide.with(mContext)
//                                .load(url)
//                                .asBitmap()
//                                .into(new SimpleTarget<Bitmap>() {
//                                    @Override
//                                    public void onResourceReady(Bitmap resource,
//                                                                GlideAnimation<? super Bitmap> glideAnimation) {
//                                        callback.onLoadComplete(resource);
//                                    }
//
//                                    @Override
//                                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                                        callback.onLoadFailed();
//                                    }
//                                });
                    }

                    @Override
                    public Drawable getDefaultDrawable() {
                        return ContextCompat.getDrawable(mContext, R.drawable.public_difficult);
                    }

                    @Override
                    public Drawable getErrorDrawable() {
                        return ContextCompat.getDrawable(mContext, R.drawable.public_difficult);
                    }

                    @Override
                    public int getMaxWidth() {
                        return getTextWidth(finalHolder.tv_tv);
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
                    }

                    @Override
                    public void onLinkClick(Context context, String url) {
                        // link click
                    }
                })
                .into(holder.tv_tv);


//        holder.tv_tv.callback(new XRichText.Callback() {
//            @Override
//            public void onImageClick(List<String> urlList, int position) {
//            }
//
//            @Override
//            public boolean onLinkClick(String url) {
//                return true;
//            }
//
//            @Override
//            public void onFix(XRichText.ImageHolder holder) {
//            }
//        }).text("<p><strong>字体测试字体测试字体测试字体测试字体测试</strong></p><p><em><strong>字体测试字体测试字体测试字体测试字体测试</strong></em></p><p><span style=\"text-decoration: underline;\">字体测试字体测试字体测试字体测试字体测试</span></p><p><span style=\"text-decoration: line-through; color: rgb(255, 0, 0);\">字体测试字体测试字体测试字体测试字体测试</span></p><p><span style=\"text-decoration: line-through; color: rgb(255, 0, 0);\"><img src=\"http://test-px.huatu.com//uploads/ueditor/image/20181122/1542888683164610.jpg\" title=\"1542888683164610.jpg\" _src=\"http://test-px.huatu.com//uploads/ueditor/image/20181122/1542888683164610.jpg\" alt=\"0-3.jpg\"></span></p>");




//        holder.tv_tv.setText("fsfsdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
//        RichText
//                .from("<p>我是文本内容 <img src='http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg' /> 下一个\" +\n" +
//                        "                \"<img src='http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg' />似懂非懂撒范德萨咖啡机盛大开放惊世毒妃</p><p>我是文本内容 <img src='http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg' /> 下一个\" +\n" +
//                        "                \"<img src='http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg' />似懂非懂撒范德萨咖啡机盛大开放惊世毒妃</p><p>我是文本内容 <img src='http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg' /> 下一个\" +\n" +
//                        "                \"<img src='http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg' />似懂非懂撒范德萨咖啡机盛大开放惊世毒妃</p><p>我是文本内容 <img src='http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg' /> 下一个\" +\n" +
//                        "                \"<img src='http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg' />似懂非懂撒范德萨咖啡机盛大开放惊世毒妃</p><img src='http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg' />似懂非懂撒范德萨咖啡机盛大开放惊世毒妃")
////                .imageClick(new OnImageClickListener() {
////                    @Override
////                    public void imageClicked(List<String> imageUrls, int position) {
////                        Log.e("----->>>",imageUrls.toString());
////                    }
////                })
////// .autoFix(true) // 是否自动修复，默认true
//////                .autoPlay(true) // gif图片是否自动播放
//////                .showBorder(true) // 是否显示图片边框
//////                .borderColor(Color.RED) // 图片边框颜色
//////                .borderSize(10) // 边框尺寸
//////                .borderRadius(50) // 图片边框圆角弧度
//////                .singleLoad(false)
//                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT) // 图片占位区域的宽高
//////                .noImage(false) // 不显示并且不加载图片
//////                .resetSize(false) // 默认false，是否忽略img标签中的宽高尺寸（只在img标签中存在宽高时才有效），true：忽略标签中的尺寸并触发SIZE_READY回调，false：使用img标签中的宽高尺寸，不触发SIZE_READY回调
//////                .clickable(true) // 是否可点击，默认只有设置了点击监听才可点击
////                .into(holder.tv_tv); // 设置目标TextView
//                .showBorder(true)
//                .singleLoad(false)
//                .fix(new SimpleImageFixCallback() {
//
//                    @Override
//                    public void onFailure(ImageHolder holder, Exception e) {
//                        super.onFailure(holder, e);
//                        e.printStackTrace();
//                    }
//                })
//                .done(new Callback() {
//                    @Override
//                    public void done(boolean imageLoadDone) {
//                        Log.e("eee", "imageDownloadFinish() called with: imageLoadDone = [" + imageLoadDone + "]");
//                    }
//                })
//                .into(holder.tv_tv);


        return convertView;
    }
    class TestHolder{
        TextView tv_tv;
    }
    private int getTextWidth(TextView textView) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        return dm.widthPixels - textView.getPaddingLeft() - textView.getPaddingRight();
    }
}
