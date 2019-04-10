package com.example.module_employees_world.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.thefinestartist.utils.log.LogUtil;
import com.wb.baselib.app.AppUtils;

/**
 * @author liuzhe
 * @date 2019/4/9
 */
public class CustomMovementMethod extends LinkMovementMethod {
    Context mContext;

    public CustomMovementMethod(Activity context) {
        this.mContext = context;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer,
                                MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP) {

            //长按之后松手是不会跳转到目标页面的，只有短暂点击才会
            if (event.getEventTime() - event.getDownTime() > ViewConfiguration
                    .getLongPressTimeout() - 10) {
                return true;
            }

            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            URLSpan[] link = buffer.getSpans(off, off, URLSpan.class);
            if (link.length != 0) {
                String url = link[0].getURL();


                LogUtil.e("URLSpanURLSpanURLSpan = " + url);

                startExplorer(url);

                return true;
            }
        }
        return super.onTouchEvent(widget, buffer, event);
    }

    public void startExplorer(String url) {

        if (!url.contains("http")) {
            url = "http://" + url;
        }

//        try {
//            Intent intent = new Intent();
//            intent.setAction("android.intent.action.VIEW");
//            Uri content_url = Uri.parse(url);//splitflowurl为分流地址
//            intent.setData(content_url);
//            if (!hasPreferredApplication(mContext, intent)) {
//                intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
//            }
//            mContext.startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            mContext.startActivity(intent);
        } catch (Exception e) {

            Intent shortcutIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (hasPreferredApplication(mContext, shortcutIntent)) {
                shortcutIntent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                mContext.startActivity(shortcutIntent);
            }
        }
    }

    //判断系统是否设置了默认浏览器
    public boolean hasPreferredApplication(Context mContext, Intent intent) {
        PackageManager pm = mContext.getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (info == null) {
            return false;
        }
        return "android".equals(info.activityInfo.packageName);
    }
}
