package com.example.module_employees_world.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.module_employees_world.ui.topic.LocalAlbumDetailActicity;


/**
 * @author liuzhe
 * @date 2019/3/21
 */
public class StartActivityCommon {

    public static void startActivity(Context mContext, Class object){
        mContext.startActivity(new Intent(mContext, object));
    }

    public static void startActivityForResult(Activity mContext, Class object, int state){
        Intent intent = new Intent(mContext, object);
        mContext.startActivityForResult(intent, state);
    }

    public static void startActivity(Context mContext, Intent intent){
        mContext.startActivity(intent);
    }

}
