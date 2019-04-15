package com.liuxiaoji.module_contacts.selectparticipant.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.liuxiaoji.module_contacts.R;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class GlideUtils {

    /**
     * 简单封装一下glide 加载图片的方法
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void setNetImage(Context context, String url, ImageView imageView) {
        setNetImage(context, url, imageView, R.mipmap.portairt);
    }

    public static void setNetImage(Context context, String url, ImageView imageView, boolean isSetPlaceholder) {
        if (isSetPlaceholder) {
            setNetImage(context, url, imageView);
        } else {
            setNetImageWithOutPlaceHolder(context, url, imageView);
        }
    }

    /**
     * 简单封装一下glide 加载图片的方法
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void setNetImage(Context context, String url, ImageView imageView, int palceholder) {
        if (context == null || imageView == null) {
            return;
        }
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        requestOptions.placeholder(palceholder).dontAnimate();
        if (!TextUtils.isEmpty(url) && url.contains(".gif")) {
            Glide.with(context)
                    .load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target,
                                                    boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                       DataSource dataSource, boolean isFirstResource) {
                            GifDrawable gifDrawable;
                            if (resource instanceof GifDrawable) {
                                gifDrawable = (GifDrawable) resource;
                            } else {
                                gifDrawable = null;
                            }
                            return false;
                        }
                    })
                    .into(imageView);
        } else {
            Glide.with(context).load(url).apply(requestOptions).into(imageView);
        }
    }

    private static void setNetImageWithOutPlaceHolder(Context context, String url, ImageView imageView) {
        if (context == null || imageView == null) {
            return;
        }
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        if (!TextUtils.isEmpty(url) && url.contains(".gif")) {
            Glide.with(context)
                    .load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target,
                                                    boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                       DataSource dataSource, boolean isFirstResource) {
                            GifDrawable gifDrawable;
                            if (resource instanceof GifDrawable) {
                                gifDrawable = (GifDrawable) resource;
                            } else {
                                gifDrawable = null;
                            }
                            return false;
                        }
                    })
                    .into(imageView);
        } else {
            Glide.with(context).load(url).apply(requestOptions).into(imageView);
        }
    }

}