package com.zhiyun88.www.module_main.information.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.thefinestartist.finestwebview.FinestWebView;
import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.fragment.MvpFragment;
import com.wb.baselib.phone.PhoneUtils;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.rxbus.taskBean.RxBus;
import com.wb.rxbus.taskBean.RxMessageBean;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.information.adapter.InformationListAdapter;
import com.zhiyun88.www.module_main.information.bean.InformationDataListBean;
import com.zhiyun88.www.module_main.information.mvp.contranct.InformationFragmentContranct;
import com.zhiyun88.www.module_main.information.mvp.presenter.InformationFragmentPresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.functions.Consumer;


public class InformationFragment extends MvpFragment<InformationFragmentPresenter> implements InformationFragmentContranct.InformationFragmentView{

    private MultipleStatusView multipleStatusView;
    private SmartRefreshLayout smartRefreshLayout;
    private ListView listView;
    private InformationListAdapter informationListAdapter;
    private List<InformationDataListBean> dataListBeans;
    private String classify_id;
    private int page = 1;

    public static Fragment newInstance(String id) {
        InformationFragment informationFragment = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("classify_id", id);
        informationFragment.setArguments(bundle);
        return informationFragment;
    }

    @Override
    protected InformationFragmentPresenter onCreatePresenter() {
        return new InformationFragmentPresenter(this);
    }

    @Override
    public boolean isLazyFragment() {
        return true;
    }


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.main_fragment_information);
        classify_id = getArguments().getString("classify_id");
        multipleStatusView = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        listView = getViewById(R.id.p_lv);
        dataListBeans = new ArrayList<>();
        informationListAdapter = new InformationListAdapter(getActivity(), dataListBeans);
        listView.setAdapter(informationListAdapter);
        mPresenter.getInformationData(classify_id, page);
        RefreshUtils.getInstance(smartRefreshLayout,getActivity() ).defaultRefreSh();
        setListener();
    }

    @Override
    protected void setListener() {
        super.setListener();
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleStatusView.showLoading();
                page = 1;
                mPresenter.getInformationData(classify_id, page);
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getInformationData(classify_id, page);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getInformationData(classify_id, page);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dataListBeans == null) return;
                InformationDataListBean dataListBean = dataListBeans.get(position);
                int clickCount = Integer.parseInt(dataListBean.getClick_rate())+1;
                dataListBean.setClick_rate(clickCount+"");
                informationListAdapter.notifyDataSetChanged();
                new FinestWebView.Builder(parent.getContext())
                        .titleDefault("资讯详情")
                        .updateTitleFromHtml(false)
                        .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
                        .iconDefaultColorRes(R.color.main_live_3c)
                        .showIconMenu(false)
                        .titleSizeRes(R.dimen.title2)
                        .webViewJavaScriptEnabled(true)
                        .progressBarHeight(PhoneUtils.newInstance().dip2px(parent.getContext(), 3))
                        .progressBarColorRes(R.color.main_live_3c)
                        .titleColorRes(R.color.main_live_3c)
                        .toolbarColorRes(R.color.statusbar_color)
                        .statusBarColorRes(R.color.statusbar_color)
                        .backPressToClose(false)
                        .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
                        .showUrl(false)
                        .show(dataListBeans.get(position).getH5_detail());

            }
        });
    }

    @Override
    public void ShowLoadView() {
        multipleStatusView.showLoading();
    }

    @Override
    public void NoNetWork() {
        multipleStatusView.showNoNetwork();
    }

    @Override
    public void NoData() {
        multipleStatusView.showEmpty();
    }

    @Override
    public void ErrorData() {
        multipleStatusView.showError();
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
        if (page == 1) {
            dataListBeans.clear();
        }
        dataListBeans.addAll((Collection<? extends InformationDataListBean>) o);
        multipleStatusView.showContent();
        informationListAdapter.notifyDataSetChanged();
        page++;
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void isLoadMore(boolean isLoadMore) {
        RefreshUtils.getInstance(smartRefreshLayout,getActivity() ).isLoadData(isLoadMore);
    }
}
