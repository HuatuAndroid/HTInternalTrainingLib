package com.jungan.www.common_dotest;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichTextConfig;
import com.zzhoujay.richtext.callback.DrawableGetter;

public class ds implements DrawableGetter {
    private Context mContext;

    public ds(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Drawable getDrawable(ImageHolder holder, RichTextConfig config, TextView textView) {
        return ContextCompat.getDrawable(mContext,  R.drawable.public_difficult);
    }
}
