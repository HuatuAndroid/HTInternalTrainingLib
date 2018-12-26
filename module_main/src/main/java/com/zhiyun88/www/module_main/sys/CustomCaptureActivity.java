package com.zhiyun88.www.module_main.sys;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.thefinestartist.finestwebview.FinestWebView;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wb.baselib.base.activity.BaseActivity;
import com.wb.baselib.phone.PhoneUtils;
import com.wb.baselib.utils.ToActivityUtil;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.sign.ui.UserSignActivity;

public class CustomCaptureActivity extends BaseActivity {

    private CaptureFragment captureFragment;
    private TopBarView sys_tb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_custom_capture);
        initView(savedInstanceState);
        processLogic(savedInstanceState);
    }
    @Override
    protected void initView(Bundle bundle) {
        sys_tb=getViewById(R.id.sys_tb);
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.main_layout_custom_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }
    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle bundle) {
        sys_tb.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View view, int i, String s) {
                if(TopBarView.ACTION_LEFT_BUTTON==i){
                    finish();
                }
            }
        });
    }
    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Log.e("打开url",result);
            openUrl(result);
            finish();
        }

        @Override
        public void onAnalyzeFailed() {
            showLongToast("解析失败！");
        }
    };
    private void openUrl(String  url){
        try {
            ToActivityUtil.newInsance().toNextActivity(CustomCaptureActivity.this, UserSignActivity.class,new String[][]{{"signJson",url}});
//            new FinestWebView.Builder(CustomCaptureActivity.this)
//                    .titleDefault("正在加载...")
//                    .updateTitleFromHtml(true)
//                    .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
//                    .iconDefaultColorRes(R.color.main_text_blue_458)
//                    .showIconMenu(false)
//                    .titleSizeRes(R.dimen.title2)
//                    .webViewJavaScriptEnabled(true)
//                    .progressBarHeight(PhoneUtils.newInstance().dip2px(CustomCaptureActivity.this, 3))
//                    .progressBarColorRes(R.color.main_text_blue_458)
//                    .titleColorRes(R.color.main_text_blue_458)
//                    .toolbarColorRes(R.color.statusbar_color)
//                    .statusBarColorRes(R.color.statusbar_color)
//                    .backPressToClose(false)
//                    .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
//                    .showUrl(false)
//                    .show(url);
        }catch (Exception e){
            showLongToast("打开url错误！");
        }

    }

}