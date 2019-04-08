package com.example.module_employees_world;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.module_employees_world.common.StartActivityCommon;
import com.example.module_employees_world.ui.home.CommunityActivity;
import com.wb.baselib.base.activity.BaseActivity;
import com.wb.baselib.utils.StatusBarUtil;

public class GuideH5Activity extends BaseActivity {
    private WebView wv;
    private TextView tvTitle;
    private ImageView ivGoMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusLayout(this, Color.parseColor("#007AFF"));
        StatusBarUtil.StatusBarDarkMode(this, StatusBarUtil.StatusBarLightMode(this));
        setContentView(R.layout.activity_guide_h5);
        initView(savedInstanceState);
        setListener();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ivGoMain = findViewById(R.id.ivGoMain);
        wv = findViewById(R.id.wv);
        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(getIntent().getStringExtra("title"));
        wv.setWebViewClient(new WebViewClient());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            wv.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        wv.getSettings().setJavaScriptEnabled(true);//启用js
        wv.getSettings().setBlockNetworkImage(false);//解决图片不显示
        wv.loadUrl(getIntent().getStringExtra("url"));
    }

    @Override
    protected void setListener() {
        ivGoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivityCommon.startActivity(GuideH5Activity.this, CommunityActivity.class);
                finish();
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
