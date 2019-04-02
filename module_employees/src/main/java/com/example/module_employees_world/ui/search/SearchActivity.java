package com.example.module_employees_world.ui.search;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.customview.ClearEditText;
import com.example.module_employees_world.ui.home.CommunityActivity;
import com.example.module_employees_world.ui.home.CommunityDiscussFragment;
import com.example.module_employees_world.ui.home.CommunityGroupFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.wb.baselib.adapter.ViewPageTabAdapter;
import com.wb.baselib.base.activity.BaseActivity;
import com.wb.baselib.utils.StatusBarUtil;

import java.util.ArrayList;

public class SearchActivity extends BaseActivity {

    private ImageView ivBack;
    private TextView tvCancel;
    private LinearLayout llTab;
    private ClearEditText etcSearch;
    private ScrollIndicatorView scorllIndicator;
    private ViewPager viewpager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusLayout(this, Color.parseColor("#007AFF"));
        StatusBarUtil.StatusBarDarkMode(this, StatusBarUtil.StatusBarLightMode(this));
        setContentView(R.layout.activity_search);
        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        etcSearch = findViewById(R.id.etcSearch);
        llTab = findViewById(R.id.llTab);
        ivBack = findViewById(R.id.ivBack);
        tvCancel = findViewById(R.id.tvCancel);
        scorllIndicator = findViewById(R.id.scorllIndicator);
        viewpager = getViewById(R.id.viewpager);
        setListener();
    }

    @Override
    protected void setListener() {
        etcSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (mFragments.size() == 0) {
                    llTab.setVisibility(View.VISIBLE);
                    ArrayList<String> str = new ArrayList<>();
                    str.add("帖子");
                    str.add("评论");
                    String keyword = v.getText().toString();
                    mFragments.add(PostMessageFragment.newInstance("1", keyword, true));
                    mFragments.add(CommentFragment.newInstance("2", keyword, true));
                    scorllIndicator.setSplitAuto(true);
                    scorllIndicator.setOnTransitionListener(new OnTransitionTextListener() {
                        @Override
                        public TextView getTextView(View tabItemView, int position) {
                            return (TextView) tabItemView.findViewById(R.id.test_tv);
                        }
                    }.setColor(getResources().getColor(R.color.main_text_blue_458), Color.BLACK));
                    ColorBar colorBar = new ColorBar(SearchActivity.this, getResources().getColor(R.color.main_text_blue_458), 8);
                    scorllIndicator.setScrollBar(colorBar);
                    IndicatorViewPager indicatorViewPager = new IndicatorViewPager(scorllIndicator, viewpager);
                    ViewPageTabAdapter viewPageTabAdapter = new ViewPageTabAdapter(getSupportFragmentManager(), SearchActivity.this, mFragments, str);
                    indicatorViewPager.setAdapter(viewPageTabAdapter);
                    viewpager.setOffscreenPageLimit(mFragments.size());
                    viewpager.setCurrentItem(0);
                }else {

                }
                return true;
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}