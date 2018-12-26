package com.zhiyun88.www.module_main.library.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.adapter.ViewPageTabAdapter;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.library.bean.LibraryTypeListBean;
import com.zhiyun88.www.module_main.library.fragment.LibraryFragment;
import com.zhiyun88.www.module_main.library.mvp.contranct.LibraryContranct;
import com.zhiyun88.www.module_main.library.mvp.presenter.LibraryPresenter;

import java.util.ArrayList;

public class LibraryActivity extends MvpActivity<LibraryPresenter> implements LibraryContranct.LibraryView{

    private TopBarView topBarView;
    private View view;
    private ViewPager mViewPager;
    private ScrollIndicatorView scrollIndicatorView;
    private ArrayList<LibraryTypeListBean> libraryTypeListBeans;

    @Override
    protected LibraryPresenter onCreatePresenter() {
        return new LibraryPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_activity_information);
        topBarView = getViewById(R.id.topbarview);
        scrollIndicatorView = getViewById(R.id.spring_indicator);
        view = getViewById(R.id.view_line_xi);
        mViewPager = getViewById(R.id.viewpager);
        view.setVisibility(View.VISIBLE);
        topBarView.getCenterTextView().setText("文库");
        libraryTypeListBeans = new ArrayList<>();
        mPresenter.getLibraryType();
    }

    private void initView() {
        if (libraryTypeListBeans == null || libraryTypeListBeans.size() == 0)return;
        ArrayList<String> str = new ArrayList<>();
        ArrayList<Fragment> mFragments = new ArrayList<>();
        for (int i = 0; i < libraryTypeListBeans.size(); i++) {
            str.add(libraryTypeListBeans.get(i).getName());
            mFragments.add(LibraryFragment.newInstance(libraryTypeListBeans.get(i).getId()));
        }
        scrollIndicatorView.setSplitAuto(true);
        scrollIndicatorView.setOnTransitionListener(new OnTransitionTextListener() {
            @Override
            public TextView getTextView(View tabItemView, int position) {
                return (TextView) tabItemView.findViewById(R.id.test_tv);
            }
        }.setColor(getResources().getColor(R.color.main_text_blue_458), Color.BLACK));
        ColorBar colorBar = new ColorBar(LibraryActivity.this, getResources().getColor(R.color.main_text_blue_458), 4);
        scrollIndicatorView.setScrollBar(colorBar);
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, mViewPager);
        ViewPageTabAdapter viewPageTabAdapter= new ViewPageTabAdapter(getSupportFragmentManager(), LibraryActivity.this, mFragments, str);
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

    @Override
    public void showErrorMsg(String msg) {
        showShortToast(msg);
    }

    @Override
    public void showLoadV(String msg) {
        showLoadDiaLog(msg);
    }

    @Override
    public void closeLoadV() {
        hidLoadDiaLog();
    }

    @Override
    public void SuccessData(Object o) {
        libraryTypeListBeans = (ArrayList<LibraryTypeListBean>) o;
        initView();
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }
}
