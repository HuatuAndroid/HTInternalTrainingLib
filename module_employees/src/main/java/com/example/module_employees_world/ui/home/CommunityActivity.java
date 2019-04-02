package com.example.module_employees_world.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.module_employees_world.R;
import com.example.module_employees_world.ui.group.MyItemActivity;
import com.example.module_employees_world.ui.search.SearchActivity;
import com.example.module_employees_world.ui.topic.TopicEditActivity;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.wb.baselib.adapter.ViewPageTabAdapter;
import com.wb.baselib.base.activity.BaseActivity;
import com.wb.baselib.utils.StatusBarUtil;

import java.util.ArrayList;

/**
 * 员工天地
 */
public class CommunityActivity extends BaseActivity {

    private View view;
    private ImageView ivPost,ivBack, ivContacts, ivSearch;
    private ViewPager mViewPager;
    private ScrollIndicatorView scrollIndicatorView;

    public static void startForResult(Activity activity) {
        Intent intent = new Intent(activity, CommunityActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusLayout(this,Color.parseColor("#007AFF"));
        StatusBarUtil.StatusBarDarkMode(this, StatusBarUtil.StatusBarLightMode(this));
        setContentView(R.layout.main_new);
        ivBack = findViewById(R.id.ivBack);
        ivContacts = findViewById(R.id.ivContacts);
        ivSearch = findViewById(R.id.ivSearch);
        scrollIndicatorView = getViewById(R.id.spring_indicator);
        view = getViewById(R.id.view_line_xi);
        mViewPager = getViewById(R.id.viewpager);
        ivPost = getViewById(R.id.ivPost);
        view.setVisibility(View.VISIBLE);
        initView(savedInstanceState);
        setListener();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ArrayList<String> str = new ArrayList<>();
        ArrayList<Fragment> mFragments = new ArrayList<>();
        str.add("小组");
        str.add("热门");
        str.add("最新");
        mFragments.add(new CommunityGroupFragment());
        mFragments.add(CommunityDiscussFragment.newInstance("1", "", true));
        mFragments.add(CommunityDiscussFragment.newInstance("2", "", true));
        scrollIndicatorView.setSplitAuto(true);
        scrollIndicatorView.setOnTransitionListener(new OnTransitionTextListener() {
            @Override
            public TextView getTextView(View tabItemView, int position) {
                return (TextView) tabItemView.findViewById(R.id.test_tv);
            }
        }.setColor(getResources().getColor(R.color.main_text_blue_458), Color.BLACK));
        ColorBar colorBar = new ColorBar(CommunityActivity.this, getResources().getColor(R.color.main_text_blue_458), 8);
        scrollIndicatorView.setScrollBar(colorBar);
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, mViewPager);
        ViewPageTabAdapter viewPageTabAdapter = new ViewPageTabAdapter(getSupportFragmentManager(), CommunityActivity.this, mFragments, str);
        indicatorViewPager.setAdapter(viewPageTabAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.setCurrentItem(1);

    }

    @Override
    protected void setListener() {
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommunityActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommunityActivity.this, TopicEditActivity.class);
                startActivity(intent);
             }
        });
        ivContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommunityActivity.this, MyItemActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
