package com.zhiyun88.www.module_main.community.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.wb.baselib.adapter.ViewPageTabAdapter;
import com.wb.baselib.base.activity.BaseActivity;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.fragment.CommunityMyGroupFragment;
import com.zhiyun88.www.module_main.community.fragment.CommunityMyJoinFragment;

import java.util.ArrayList;

/**
 * 我的小组
 */
public class MyItemActivity extends BaseActivity {

    private TopBarView topBarView;
    private View view;
    private ViewPager mViewPager;
    private ScrollIndicatorView scrollIndicatorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_community);
        topBarView = getViewById(R.id.topbarview);
        scrollIndicatorView = getViewById(R.id.spring_indicator);
        view = getViewById(R.id.view_line_xi);
        mViewPager = getViewById(R.id.viewpager);
        view.setVisibility(View.VISIBLE);
        topBarView.getCenterTextView().setText("我的小组");
        topBarView.getRightImageButton().setVisibility(View.GONE);
        initView(savedInstanceState);
        setListener();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ArrayList<String> str = new ArrayList<>();
        ArrayList<Fragment> mFragments = new ArrayList<>();
        str.add("我的小组");
        str.add("我的参与");
        mFragments.add(new CommunityMyGroupFragment());
        mFragments.add(new CommunityMyJoinFragment());
        scrollIndicatorView.setSplitAuto(true);
        scrollIndicatorView.setOnTransitionListener(new OnTransitionTextListener() {
            @Override
            public TextView getTextView(View tabItemView, int position) {
                return (TextView) tabItemView.findViewById(R.id.test_tv);
            }
        }.setColor(getResources().getColor(R.color.main_text_blue_458), Color.BLACK));
        ColorBar colorBar = new ColorBar(MyItemActivity.this, getResources().getColor(R.color.main_text_blue_458), 4);
        scrollIndicatorView.setScrollBar(colorBar);
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, mViewPager);
        ViewPageTabAdapter viewPageTabAdapter= new ViewPageTabAdapter(getSupportFragmentManager(), MyItemActivity.this, mFragments, str);
        indicatorViewPager.setAdapter(viewPageTabAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.size());
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
    protected void processLogic(Bundle savedInstanceState) {

    }
}
