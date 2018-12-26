package com.zhiyun88.www.module_main.main;

import android.content.Context;
import android.widget.ImageView;

import com.wb.baselib.image.GlideManager;
import com.youth.banner.loader.ImageLoader;
import com.zhiyun88.www.module_main.R;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        try {
            GlideManager.getInstance().setCommonPhoto(imageView, R.drawable.course_image,context, (String) path,false);
        }catch (Exception e){
            GlideManager.getInstance().setCommonPhoto(imageView, R.drawable.course_image,context, "http://www.baidu.com",false);
        }

    }
}
