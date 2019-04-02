package com.example.module_employees_world.ui;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.adapter.MyImageAdapter;
import com.example.module_employees_world.view.PhotoViewPager;
import com.wb.baselib.base.activity.BaseActivity;
import com.wb.baselib.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class PictuirePreviewActivity extends BaseActivity {
    private List<String> urls;
    private ImageView ivExit;
    private TextView tvPage;
    private PhotoViewPager vp;
    private int currentPosition;
    private MyImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusLayout(this, Color.parseColor("#000000"));
        StatusBarUtil.StatusBarDarkMode(this, StatusBarUtil.StatusBarLightMode(this));
        setContentView(R.layout.activity_pictuire_preview);
        initView(savedInstanceState);
        setListener();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvPage = findViewById(R.id.tvPage);
        vp = findViewById(R.id.vp);
        ivExit = findViewById(R.id.ivExit);
        initData();
    }

    @Override
    protected void setListener() {
        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    private void initData() {
        //Urls = getIntent().getStringArrayListExtra("urls");u
        urls = new ArrayList<>();
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196978103&di=8860c3e3faadf3870542e4f72b0f9871&imgtype=0&src=http%3A%2F%2Fs9.rr.itc.cn%2Fr%2FwapChange%2F20165_6_11%2Fa0teml39607703025596.png");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196978103&di=07b378759a215729c08a68c3bfe14caa&imgtype=0&src=http%3A%2F%2Fp2.gexing.com%2Fshaitu%2F20121030%2F1949%2F508fbed1c6ff7.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196978102&di=9fb8f90ced1ccc36b49da20cb5dc3f88&imgtype=0&src=http%3A%2F%2Fwww.wydog.com%2FupLoad%2Fimage%2F20171027%2F15090927218191343.jpg");
        adapter = new MyImageAdapter(urls, this);
        vp.setAdapter(adapter);
        vp.setCurrentItem(currentPosition, false);
        tvPage.setText(currentPosition+1 + "/" + urls.size());
        vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                tvPage.setText(currentPosition + 1 + "/" + urls.size());
            }
        });
    }

}
