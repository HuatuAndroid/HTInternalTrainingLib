package com.zhiyun88.www.module_main.main.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.fragment.MvpFragment;
import com.wb.baselib.permissions.PerMissionsManager;
import com.wb.baselib.permissions.interfaces.PerMissionCall;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.utils.ToActivityUtil;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.wb.rxbus.taskBean.RxBus;
import com.wb.rxbus.taskBean.RxMessageBean;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.main.adapter.HomeAdapter;
import com.zhiyun88.www.module_main.main.bean.HomeBean;
import com.zhiyun88.www.module_main.main.mvp.contranct.HomeFragmentContranct;
import com.zhiyun88.www.module_main.main.mvp.presenter.HomeFragmentPresenter;
import com.zhiyun88.www.module_main.main.ui.SearchActivity;
import com.zhiyun88.www.module_main.sys.CustomCaptureActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;


public class HomeFragment extends MvpFragment<HomeFragmentPresenter> implements HomeFragmentContranct.HomeFragmentView {

    private TopBarView topBarView;
    private ListView listView;
    private MultipleStatusView multiplestatusview;
    private List<HomeBean> homeBeanList;
    private SmartRefreshLayout smartRefreshLayout;
    private HomeAdapter homeAdapter;

    @Override
    protected HomeFragmentPresenter onCreatePresenter() {
        return new HomeFragmentPresenter(this);
    }


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.main_fragment_home);
        topBarView = getViewById(R.id.topbarview);
        multiplestatusview = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        RefreshUtils.getInstance(smartRefreshLayout,getActivity()).defaultRefreSh();
        smartRefreshLayout.setEnableLoadMore(false);
        multiplestatusview.showLoading();
        listView = getViewById(R.id.p_lv);
        listView.setVerticalScrollBarEnabled(false);
//        topBarView.getLeftImageButton().setVisibility(View.GONE);
        mPresenter.getHomeData();
        homeBeanList = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(), homeBeanList);
        listView.setAdapter(homeAdapter);
        setListener();
    }


    @Override
    protected void setListener() {
        super.setListener();
        topBarView.getCenterSearchEditText().setHint("请输入关键字");
        topBarView.getCenterSearchEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToActivityUtil.newInsance().toNextActivity(getActivity(),SearchActivity.class );
            }
        });
        topBarView.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == TopBarView.ACTION_RIGHT_BUTTON) {
                    PerMissionsManager.newInstance().getUserPerMissions(getActivity(), new PerMissionCall() {
                        @Override
                        public void userPerMissionStatus(boolean b) {
                            ToActivityUtil.newInsance().toNextActivity(getActivity(),CustomCaptureActivity.class );
                        }
                    },new String[]{Manifest.permission.CAMERA});

                }else if(action==TopBarView.ACTION_LEFT_BUTTON){
                            getActivity().finish();
                }
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getHomeData();
            }
        });
        multiplestatusview.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiplestatusview.showLoading();
                mPresenter.getHomeData();
            }
        });
    }

    @Override
    public boolean isLazyFragment() {
        return true;
    }


    @Override
    public void ShowLoadView() {
        multiplestatusview.showLoading();
    }

    @Override
    public void NoNetWork() {
        multiplestatusview.showNoNetwork();
    }

    @Override
    public void NoData() {
        multiplestatusview.showEmpty();
    }

    @Override
    public void ErrorData() {
        multiplestatusview.showError();
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
        smartRefreshLayout.finishRefresh();
        homeBeanList.clear();
        homeBeanList.add((HomeBean) o);
        multiplestatusview.showContent();
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }
}
