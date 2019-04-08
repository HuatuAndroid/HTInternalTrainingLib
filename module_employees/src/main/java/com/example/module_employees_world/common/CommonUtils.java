package com.example.module_employees_world.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liuzhe
 * @date 2019/3/22
 */
public class CommonUtils {

    public static final String BASE_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath();

    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_TAG_CROP_IMAGE = 20;

    /**
     * 请求相册
     */
    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0;

    public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;
    /**
     * 请求相机
     */
    public static final int REQUEST_CODE_GETIMAGE_BYCROP = 2;

    public static final int REQUEST_CODE_TAKE_PICTURE = 3;
    /**
     * 选择小组
     */
    public static final int SELECT_GROUP = 4;

    public static final String LOCAL_FOLDER_NAME = "local_folder_name";//跳转到相册页的文件夹名称

    public final static String base_path = BASE_PATH + "/dt";
    public final static String cache_path = BASE_PATH + "/cache_dt";

    public static void createFile() {
        if (CommonUtils.isExitsSdcard()) {
            File file = new File(CommonUtils.base_path);
            if (!file.exists()) {
                file.mkdir();
            }

            File file2 = new File(CommonUtils.cache_path);
            if (!file2.exists()) {
                file2.mkdir();
            }
        } else {

        }
    }

    public static boolean isExitsSdcard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    public static boolean compareStr(String str1, String str2) {
        if (str1 != null) {
            return str1.equals(str2);
        } else {
            return str1 == str2;
        }
    }

    /**
     * 显示软键盘，Dialog使用
     *
     * @param activity 当前Activity
     */
    public static void showSoftInput(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean checkDeviceHasNavigationBar(Context context,boolean isNavBarMini) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar && !isNavBarMini;
    }

    public static boolean checkDeviceHasStatusBar(Activity activity) {
        if ((activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * dp2px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px2dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public boolean isTopActivity(Activity activity) {

        ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);

        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

        return cn.getClassName().equals(activity.getClass().getName());

    }

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;

        Object obj = null;

        Field field = null;

        int x = 0, sbar = 0;

        try {

            c = Class.forName("com.android.internal.R$dimen");

            obj = c.newInstance();

            field = c.getField("status_bar_height");

            x = Integer.parseInt(field.get(obj).toString());

            sbar = context.getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {

            e1.printStackTrace();

        }

        return sbar;
    }

    public static String getTime() {

        StringBuffer time = new StringBuffer();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        String time2 = format.format(new Date());

        String[] spit = time2.split("\\-| |:");

        for (int i = 0; i < spit.length; i++) {
            time.append(spit[i]);
        }
        return time.toString();
    }

}
