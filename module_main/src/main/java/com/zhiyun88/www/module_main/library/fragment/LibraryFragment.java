package com.zhiyun88.www.module_main.library.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.fragment.MvpFragment;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.utils.ToastUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.library.adapter.LibraryListAdapter;
import com.zhiyun88.www.module_main.library.bean.LibraryDataListBean;
import com.zhiyun88.www.module_main.library.bean.LibraryDetailsBean;
import com.zhiyun88.www.module_main.library.config.LibraryConfig;
import com.zhiyun88.www.module_main.library.mvp.contranct.LibraryFragmentContranct;
import com.zhiyun88.www.module_main.library.mvp.presenter.LibraryFragmentPresenter;
import com.zhiyun88.www.module_main.library.ui.LibraryShowActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class LibraryFragment extends MvpFragment<LibraryFragmentPresenter> implements LibraryFragmentContranct.LibraryFragmentView{

    private MultipleStatusView multipleStatusView;
    private SmartRefreshLayout smartRefreshLayout;
    private ListView listView;
    private LibraryListAdapter libraryListAdapter;
    private List<LibraryDataListBean> libraryDataListBeans;
    private String classify_id;
    private int page = 1;
    private int index;
    private String isCollected;
    private boolean lockClick = false;

    public static Fragment newInstance(String id) {
        LibraryFragment libraryFragment = new LibraryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("classify_id", id);
        libraryFragment.setArguments(bundle);
        return libraryFragment;
    }

    @Override
    protected LibraryFragmentPresenter onCreatePresenter() {
        return new LibraryFragmentPresenter(this);
    }

    @Override
    public boolean isLazyFragment() {
        return true;
    }


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_group);
        classify_id = getArguments().getString("classify_id");
        multipleStatusView = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        listView = getViewById(R.id.p_lv);
        libraryDataListBeans = new ArrayList<>();
        libraryListAdapter = new LibraryListAdapter(getActivity(), libraryDataListBeans);
        listView.setAdapter(libraryListAdapter);
        mPresenter.getLibraryData(classify_id, page);
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
                mPresenter.getLibraryData(classify_id, page);
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getLibraryData(classify_id, page);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getLibraryData(classify_id, page);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!lockClick) {
                    lockClick = true;
                    mPresenter.getLibraryDetails(libraryDataListBeans.get(position).getId());
                    return;
                }
//                new FinestWebView.Builder(getActivity())
//                        .titleDefault("文库详情")
//                        .updateTitleFromHtml(false)
//                        .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
//                        .iconDefaultColorRes(R.color.main_live_3c)
//                        .showIconMenu(false)
//                        .titleSizeRes(R.dimen.title2)
//                        .webViewJavaScriptEnabled(true)
//                        .progressBarHeight(PhoneUtils.newInstance().dip2px(getActivity(), 3))
//                        .progressBarColorRes(R.color.main_live_3c)
//                        .titleColorRes(R.color.main_live_3c)
//                        .toolbarColorRes(R.color.statusbar_color)
//                        .statusBarColorRes(R.color.statusbar_color)
//                        .backPressToClose(false)
//                        .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
//                        .showUrl(false)
//                        .show(libraryDataListBeans.get(position).getH5_detail());

            }
        });
        libraryListAdapter.setOnClickCollection(new LibraryConfig.OnClickCollected() {
            @Override
            public void setCollection(String libraryId, String userId, String is_click, int position) {
                index = position;
                isCollected = is_click;
                mPresenter.setLibraryCollection(libraryId,userId ,is_click );
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
            libraryDataListBeans.clear();
        }
        libraryDataListBeans.addAll((Collection<? extends LibraryDataListBean>) o);
        multipleStatusView.showContent();
        libraryListAdapter.notifyDataSetChanged();
        page++;
    }

    @Override
    public void bindDetailsData(LibraryDetailsBean bean) {
        lockClick = false;
        if (bean != null && bean.list != null && !TextUtils.isEmpty(bean.list.file_name)) {
            if (bean.list.file_name.endsWith(".pdf") || bean.list.file_name.endsWith(".PDF")) {
                LibraryShowActivity.newInstance(getContext(), bean.list.file_path, LibraryShowActivity.LIBRARY_TYPE_PDF);
            } else if (bean.list.file_name.endsWith(".doc") || bean.list.file_name.endsWith(".DOC")
                    || bean.list.file_name.endsWith(".docx") || bean.list.file_name.endsWith(".DOCX")) {
                LibraryShowActivity.newInstance(getContext(), bean.list.file_path, LibraryShowActivity.LIBRARY_TYPE_DOCX);
            } else if (bean.list.file_name.endsWith(".xlsx") || bean.list.file_name.endsWith(".XLSX")
                    || bean.list.file_name.endsWith(".xls") || bean.list.file_name.endsWith(".XLS")) {
                LibraryShowActivity.newInstance(getContext(), bean.list.file_path, LibraryShowActivity.LIBRARY_TYPE_XLSX);
            } else if (bean.list.file_name.endsWith(".ppt") || bean.list.file_name.endsWith(".PPT")) {
                LibraryShowActivity.newInstance(getContext(), bean.list.file_path, LibraryShowActivity.LIBRARY_TYPE_PPT);
            }
        }
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void isLoadMore(boolean isLoadMore) {
        RefreshUtils.getInstance(smartRefreshLayout,getActivity() ).isLoadData(isLoadMore);
    }

    @Override
    public void setCollectedSuccess(String isClick) {
        if ("0".equals(isClick)){
            ToastUtils.showToast(getContext(),"成功收藏");
        }else {
            ToastUtils.showToast(getContext(),"取消收藏");
        }
        libraryListAdapter.updateItem(index,listView,isCollected);
    }
}
