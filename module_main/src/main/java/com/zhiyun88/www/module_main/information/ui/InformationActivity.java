package com.zhiyun88.www.module_main.information.ui;

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
import com.zhiyun88.www.module_main.information.bean.InformationTypeListBean;
import com.zhiyun88.www.module_main.information.fragment.InformationFragment;
import com.zhiyun88.www.module_main.information.mvp.contranct.InformationContranct;
import com.zhiyun88.www.module_main.information.mvp.presenter.InformationPresenter;

import java.util.ArrayList;

public class InformationActivity extends MvpActivity<InformationPresenter> implements InformationContranct.InformationView{

    private TopBarView topBarView;
    private View view;
    private ViewPager mViewPager;
    private ScrollIndicatorView scrollIndicatorView;
    private ArrayList<InformationTypeListBean> typeListBeans;

    @Override
    protected InformationPresenter onCreatePresenter() {
        return new InformationPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_activity_information);
        topBarView = getViewById(R.id.topbarview);
        scrollIndicatorView = getViewById(R.id.spring_indicator);
        view = getViewById(R.id.view_line_xi);
        mViewPager = getViewById(R.id.viewpager);
        view.setVisibility(View.VISIBLE);
        topBarView.getCenterTextView().setText("资讯");
        typeListBeans = new ArrayList<>();
        mPresenter.getInformationType();
    }

    private void initView() {
        if (typeListBeans == null || typeListBeans.size() == 0)return;
        ArrayList<String> str = new ArrayList<>();
        ArrayList<Fragment> mFragments = new ArrayList<>();
        for (int i = 0; i < typeListBeans.size(); i++) {
            str.add(typeListBeans.get(i).getName());
            mFragments.add(InformationFragment.newInstance(typeListBeans.get(i).getId()));
        }
        scrollIndicatorView.setSplitAuto(true);
        scrollIndicatorView.setOnTransitionListener(new OnTransitionTextListener() {
            @Override
            public TextView getTextView(View tabItemView, int position) {
                return (TextView) tabItemView.findViewById(R.id.test_tv);
            }
        }.setColor(getResources().getColor(R.color.main_text_blue_458), Color.BLACK));
        ColorBar colorBar = new ColorBar(InformationActivity.this, getResources().getColor(R.color.main_text_blue_458), 4);
        scrollIndicatorView.setScrollBar(colorBar);
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, mViewPager);
        ViewPageTabAdapter viewPageTabAdapter= new ViewPageTabAdapter(getSupportFragmentManager(), InformationActivity.this, mFragments, str);
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
        typeListBeans = (ArrayList<InformationTypeListBean>) o;
        initView();
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }
}
