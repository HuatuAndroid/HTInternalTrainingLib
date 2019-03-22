package com.example.module_employees_world.common;

import android.content.Context;
import android.content.Intent;


/**
 * @author liuzhe
 * @date 2019/3/21
 */
public class StartActivityCommon {

    public static void startActivity(Context mContext, Class object){
        mContext.startActivity(new Intent(mContext, object));
    }

}
