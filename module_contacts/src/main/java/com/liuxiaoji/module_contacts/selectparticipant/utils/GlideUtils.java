package com.liuxiaoji.module_contacts.selectparticipant.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
//        if ((url == null || "".equals(url)) && !"".equals(ThemeConfig.INSTANCE.getAvatarIcon())) {
//            setNetImage(context, ThemeConfig.INSTANCE.getAvatarIcon(), imageView, R.mipmap.portairt);
//        } else {
            setNetImage(context, url, imageView, R.mipmap.portairt);
//        }
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
        requestOptions.placeholder(palceholder).dontAnimate();
        Glide.with(context).load(url).apply(requestOptions).into(imageView);
    }

    private static void setNetImageWithOutPlaceHolder(Context context, String url, ImageView imageView) {
        if (context == null || imageView == null) {
            return;
        }
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(context).load(url).apply(requestOptions).into(imageView);
    }
}