package com.zhiyun88.www.module_main.library.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wb.baselib.base.activity.BaseActivity;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;

/**
 * Created by KaelLi on 2018/12/19.
 */
public class LibraryShowActivity extends BaseActivity {
    public final static int LIBRARY_TYPE_PDF = 0;
    public final static int LIBRARY_TYPE_DOCX = 1;
    public final static int LIBRARY_TYPE_XLSX = 2;
    public final static int LIBRARY_TYPE_PPT = 3;
    private final static String OFFICE_PREFIX = "https://view.officeapps.live.com/op/view.aspx?src=";
    private final static String PDF_PREFIX = "file:///android_asset/pdfjs/web/viewer.html?file=";
    private final static String PREFIX_BATE = "http://ow365.cn/?i=17755&furl=";
    private final static String PREFIX_TEXT = "http://ow365.cn/?i=17217&furl=";
    private final static String PREFIX_PEIXUN = "http://ow365.cn/?i=17754&furl=";
    private TopBarView topBarView;
    private WebView webView;
    private String mUrl;
    private int mType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        setListener();
    }

    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.activity_library_show);
        topBarView = getViewById(R.id.topbarview);
        topBarView.getCenterTextView().setText("文库详情");
        webView = getViewById(R.id.webView);
        mUrl = getIntent().getStringExtra("url");
        mType = getIntent().getIntExtra("type", LIBRARY_TYPE_PDF);
        initWebView();
//        if (mType == LIBRARY_TYPE_PDF) {
//            mUrl = PDF_PREFIX + mUrl;
//        } else {
//            mUrl = OFFICE_PREFIX + mUrl;
//        }
        if (mUrl.contains("beta-px")){
            webView.loadUrl(PREFIX_BATE + mUrl);
        }else if (mUrl.contains("test-px")){
            webView.loadUrl(PREFIX_TEXT + mUrl);
        }else if (mUrl.contains("peixun")){
            webView.loadUrl(PREFIX_PEIXUN + mUrl);
        }

    }

    @Override
    protected void setListener() {
        topBarView.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == TopBarView.ACTION_LEFT_BUTTON) {
                    finish();
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle bundle) {

    }

    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setBuiltInZoomControls(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient());
    }

    public static void newInstance(Context context, String url, int type) {
        if (context == null || TextUtils.isEmpty(url)) return;
        if (type < LIBRARY_TYPE_PDF || type > LIBRARY_TYPE_PPT) return;
        Intent intent = new Intent(context, LibraryShowActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }
}
