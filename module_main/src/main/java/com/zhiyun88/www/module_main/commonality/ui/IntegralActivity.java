package com.zhiyun88.www.module_main.commonality.ui;

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
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.bean.IntegralBean;
import com.zhiyun88.www.module_main.commonality.fragment.IntegralFragment;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.IntegralContranct;
import com.zhiyun88.www.module_main.commonality.mvp.presenter.IntegralPresenter;

import java.util.ArrayList;

public class IntegralActivity extends MvpActivity<IntegralPresenter> implements IntegralContranct.IntegralView{

    private View line;
    private TopBarView topBarView;
    private ScrollIndicatorView scrollIndicatorView;
    private ViewPager mViewPager;
    private TextView aggregate,number,year,quarter;

    @Override
    protected IntegralPresenter onCreatePresenter() {
        return new IntegralPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_activity_integral);
        topBarView = getViewById(R.id.topbarview);
        aggregate = getViewById(R.id.aggregate_score);
        number = getViewById(R.id.total_number);
        year = getViewById(R.id.intrgral_year);
        quarter = getViewById(R.id.intrgral_quarter);
        scrollIndicatorView = getViewById(R.id.spring_indicator);
        line = getViewById(R.id.view_line_xi);
        mViewPager = getViewById(R.id.viewpager);
        mPresenter.getIntegral(HttpManager.newInstance().getHttpConfig().getmMapHeader().get("uid"));
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
        line.setVisibility(View.VISIBLE);
        topBarView.getCenterTextView().setText(R.string.main_my_integral);

        ArrayList<String> str = new ArrayList<>();
        ArrayList<Fragment> mFragments = new ArrayList<>();
        str.add(getString(R.string.main_Integral_record));
        str.add(getString(R.string.main_League_table));
        mFragments.add(IntegralFragment.newInstance(HttpManager.newInstance().getHttpConfig().getmMapHeader().get("uid"),0));
        mFragments.add(IntegralFragment.newInstance(HttpManager.newInstance().getHttpConfig().getmMapHeader().get("uid"),1));
        scrollIndicatorView.setSplitAuto(true);
        scrollIndicatorView.setOnTransitionListener(new OnTransitionTextListener() {
            @Override
            public TextView getTextView(View tabItemView, int position) {
                return (TextView) tabItemView.findViewById(R.id.test_tv);
            }
        }.setColor(getResources().getColor(R.color.main_text_blue_458), Color.BLACK));
        ColorBar colorBar = new ColorBar(IntegralActivity.this, getResources().getColor(R.color.main_text_blue_458), 4);
        scrollIndicatorView.setScrollBar(colorBar);
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, mViewPager);
        ViewPageTabAdapter viewPageTabAdapter= new ViewPageTabAdapter(getSupportFragmentManager(), IntegralActivity.this, mFragments, str);
        indicatorViewPager.setAdapter(viewPageTabAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.size());
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
        IntegralBean integralBean = (IntegralBean) o;

        aggregate.setText(integralBean.getIntegral());
        number.setText(integralBean.getRanking());
        year.setText(getString(R.string.main_this_year_awards)+integralBean.getN_get_integral());
        quarter.setText(getString(R.string.main_Obtained_this_quarter)+integralBean.getY_get_integral());
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }
}
