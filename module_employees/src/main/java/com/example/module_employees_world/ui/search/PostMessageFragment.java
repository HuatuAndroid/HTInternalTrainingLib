package com.example.module_employees_world.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.adapter.CommunityDiscussAdapter;
import com.example.module_employees_world.adapter.SearchPostAdapter;
import com.example.module_employees_world.bean.DiscussListBean;
import com.example.module_employees_world.bean.SearchPostBean;
import com.example.module_employees_world.contranct.SearchContranct;
import com.example.module_employees_world.presenter.CommunityDiscussPresenter;
import com.example.module_employees_world.presenter.SearchPresenter;
import com.example.module_employees_world.ui.PostsDetailActivity;
import com.example.module_employees_world.ui.home.CommunityDiscussFragment;
import com.example.module_employees_world.utils.RxBusMessageBean;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.wb.baselib.base.fragment.MvpFragment;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.MyListView;
import com.wb.rxbus.taskBean.RxBus;
import com.wb.rxbus.taskBean.RxMessageBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.functions.Consumer;


public class PostMessageFragment extends MvpFragment<SearchPresenter> implements SearchContranct.SearchView {

    private MultipleStatusView multipleStatusView;
    private SmartRefreshLayout smartRefreshLayout;
    private MyListView listView;
    private String type;
    private String keyword;
    private int page = 1;
    private List<SearchPostBean> searchPostBeans;
    private boolean isRefresh;
    private int index;
    private SearchPostAdapter searchPostAdapter;

    public static Fragment newInstance(String type, String keyword, boolean isRefresh) {
        PostMessageFragment postMessageFragment = new PostMessageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("keyword", keyword);
        bundle.putBoolean("isRefresh", isRefresh);
        postMessageFragment.setArguments(bundle);
        return postMessageFragment;
    }


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_post_message);
        type = getArguments().getString("type");
        keyword = getArguments().getString("keyword");
        isRefresh = getArguments().getBoolean("isRefresh");
        multipleStatusView = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        listView = getViewById(R.id.p_lv);
        searchPostBeans = new ArrayList<>();
        searchPostAdapter = new SearchPostAdapter(getActivity(), keyword, searchPostBeans);
        listView.setAdapter(searchPostAdapter);
        RefreshUtils.getInstance(smartRefreshLayout, getActivity()).defaultRefreSh();
        smartRefreshLayout.setEnableRefresh(isRefresh);
        smartRefreshLayout.setEnableLoadMore(true);
        multipleStatusView.showLoading();
        mPresenter.getSerachPost(type, keyword, page);
        RxBus.getIntanceBus().registerRxBus(RxBusMessageBean.class, new Consumer<RxBusMessageBean>() {
            @Override
            public void accept(RxBusMessageBean rxMessageBean) throws Exception {
                if (rxMessageBean.getMessageCode() == RxBusMessageBean.MessageType.SEARCH_POST_DELETE) {
                    page = 1;
                    multipleStatusView.showLoading();
                    mPresenter.getSerachPost(type, keyword, page);
                } else if (rxMessageBean.getMessageCode() == RxBusMessageBean.MessageType.SEARCH_POST_COMMENT) {
                    String total = (String) rxMessageBean.getMessage();
                    searchPostBeans.get(index).setComment_count(new Integer(total));
                    searchPostAdapter.notifyDataSetChanged();
                } else if (rxMessageBean.getMessageCode() == RxBusMessageBean.MessageType.SEARCH_POST_LIKE) {
                    String likeCount = (String) rxMessageBean.getMessage();
                    searchPostBeans.get(index).setLike_count(new Integer(likeCount));
                    searchPostAdapter.notifyDataSetChanged();
                } else if (rxMessageBean.getMessageCode() == RxBusMessageBean.MessageType.SEARCH_CHANGE_KEYWORD) {
                    page = 1;
                    keyword = (String) rxMessageBean.getMessage();
                    multipleStatusView.showLoading();
                    mPresenter.getSerachPost(type, keyword, page);
                }
            }
        });
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
                mPresenter.getSerachPost(type, keyword, page);
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getSerachPost(type, keyword, page);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getSerachPost(type, keyword, page);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                //社区详情
                SearchPostBean searchPostBean = searchPostBeans.get(position);
                int readCount = searchPostBean.getRead_count() + 1;
                searchPostBean.setRead_count(readCount);
                searchPostAdapter.notifyDataSetChanged();
                Intent intent = new Intent(getActivity(), PostsDetailActivity.class);
                intent.putExtra("question_id", searchPostBeans.get(position).getId());
                startActivity(intent);
            }
        });
    }


    @Override
    public void isLoadMore(boolean b) {
        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishRefresh();
            smartRefreshLayout.finishLoadMore();

        }
    }

    @Override
    protected SearchPresenter onCreatePresenter() {
        return new SearchPresenter(this);
    }

    @Override
    public boolean isLazyFragment() {
        return true;
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
        if (multipleStatusView!=null){
            multipleStatusView.showEmpty();
        }
    }

    @Override
    public void ErrorData() {
        if (multipleStatusView!=null){
            multipleStatusView.showError();
        }
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
        multipleStatusView.showContent();
        if (page == 1) {
            searchPostBeans.clear();
        }
        searchPostBeans.addAll((Collection<? extends SearchPostBean>) o);
        searchPostAdapter.setKeyword(keyword);
        searchPostAdapter.notifyDataSetChanged();
        multipleStatusView.showContent();
        page++;
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }
}
