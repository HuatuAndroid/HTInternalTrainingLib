package com.zhiyun88.www.module_main.commonality.fragment;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.fragment.MvpFragment;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.utils.ToActivityUtil;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.rxbus.taskBean.RxBus;
import com.wb.rxbus.taskBean.RxTaskBean;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.adapter.MessageAdapter;
import com.zhiyun88.www.module_main.commonality.bean.MessageDetailsBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.MessageFragmentContranct;
import com.zhiyun88.www.module_main.commonality.mvp.presenter.MessageFragmentPresenter;
import com.zhiyun88.www.module_main.commonality.ui.MessageDetailActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.functions.Consumer;

public class MessageFragment extends MvpFragment<MessageFragmentPresenter> implements MessageFragmentContranct.MessageFragmentView {

    private static final int MESSAGE_READ = 666;
    private ListView listView;
    private SmartRefreshLayout smartRefreshLayout;
    private MultipleStatusView multipleStatusView;
    private MessageAdapter messageAdapter;
    private int page = 1;
    private List<MessageDetailsBean> messageDetailsBeans;
    private int type;
    private MessageDetailsBean messageDetailsBean;

    public static MessageFragment newInstance(int type) {
        MessageFragment messageFragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        messageFragment.setArguments(bundle);
        return messageFragment;
    }

    @Override
    protected MessageFragmentPresenter onCreatePresenter() {
        return new MessageFragmentPresenter(this);
    }

    @Override
    public boolean isLazyFragment() {
        return true;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.main_fragment_message);
        type = getArguments().getInt("type", -1);
        multipleStatusView = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        RefreshUtils.getInstance(smartRefreshLayout, getActivity()).defaultRefreSh();
        listView = getViewById(R.id.p_lv);
        if (type == -1) {
            multipleStatusView.showError();
            return;
        }
        multipleStatusView.showLoading();
        messageDetailsBeans = new ArrayList<>();
        mPresenter.getMessageData(HttpManager.newInstance().getHttpConfig().getmMapHeader().get("uid"), type, 1, page);
        messageAdapter = new MessageAdapter(getActivity(), messageDetailsBeans);
        listView.setAdapter(messageAdapter);
        setListener();
        RxBus.getIntanceBus().registerRxBus(RxTaskBean.class, new Consumer<RxTaskBean>() {
            @Override
            public void accept(RxTaskBean rxTaskBean) throws Exception {
                if (rxTaskBean.getTaskType() == 1) {
                    page = 1;
                    mPresenter.getMessageData(HttpManager.newInstance().getHttpConfig().getmMapHeader().get("uid"), type, 1, page);
                }
            }
        });
    }

    @Override
    protected void setListener() {
        super.setListener();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                messageDetailsBean = messageDetailsBeans.get(position);
                messageAdapter.updateItem(position,listView);
//                if (messageDetailsBean.getIs_read() == 0 ) {
                    mPresenter.setMessageState(HttpManager.newInstance().getHttpConfig().getmMapHeader().get("uid"), messageDetailsBean.getId());
//                }else {
//                    Intent intent = new Intent(getActivity(), MessageDetailActivity.class);
//                    intent.putExtra("MessageBean", messageDetailsBean);
//                    startActivity(intent);
//                }
            }
        });
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleStatusView.showLoading();
                page = 1;
                mPresenter.getMessageData(HttpManager.newInstance().getHttpConfig().getmMapHeader().get("uid"), type, 1, page);
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getMessageData(HttpManager.newInstance().getHttpConfig().getmMapHeader().get("uid"), type, 1, page);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getMessageData(HttpManager.newInstance().getHttpConfig().getmMapHeader().get("uid"), type, 1, page);
            }
        });
    }

    @Override
    public void loadMore(boolean isLoadMore) {
        RefreshUtils.getInstance(smartRefreshLayout, getActivity()).isLoadData(isLoadMore);
    }

    @Override
    public void setIsRead() {
        Intent intent = new Intent(getActivity(), MessageDetailActivity.class);
        intent.putExtra("MessageBean", messageDetailsBean);
        startActivity(intent);
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
            messageDetailsBeans.clear();
        }
        messageDetailsBeans.addAll((Collection<? extends MessageDetailsBean>) o);
        multipleStatusView.showContent();
        messageAdapter.notifyDataSetChanged();
        page++;
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MESSAGE_READ) {
            page = 1;
            mPresenter.getMessageData(HttpManager.newInstance().getHttpConfig().getmMapHeader().get("uid"), type, 1, page);
        }
    }
}
