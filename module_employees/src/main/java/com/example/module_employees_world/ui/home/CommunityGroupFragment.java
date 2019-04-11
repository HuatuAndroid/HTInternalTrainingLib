package com.example.module_employees_world.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.adapter.CommunityGroupAdapter;
import com.example.module_employees_world.bean.ListBean;
import com.example.module_employees_world.common.config.CommunityConfig;
import com.example.module_employees_world.contranct.CommunityGroupContranct;
import com.example.module_employees_world.presenter.CommunityGroupPresenter;
import com.example.module_employees_world.ui.group.GroupDetailsActivity;
import com.example.module_employees_world.utils.DialogUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.wb.baselib.base.fragment.MvpFragment;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.rxbus.taskBean.RxBus;
import com.wb.rxbus.taskBean.RxMessageBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.functions.Consumer;


public class CommunityGroupFragment extends MvpFragment<CommunityGroupPresenter> implements CommunityGroupContranct.CommunityGroupView {

    private MultipleStatusView multipleStatusView;
    private SmartRefreshLayout smartRefreshLayout;
    private ListView listView;
    private CommunityGroupAdapter communityGroupAdapter;
    private int index;
    private String state;
    private int page = 1;
    private List<ListBean> listBeans;


    @Override
    public boolean isLazyFragment() {
        return true;
    }


    @Override
    protected CommunityGroupPresenter onCreatePresenter() {
        return new CommunityGroupPresenter(this);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_group);
        multipleStatusView = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        listView = getViewById(R.id.p_lv);
        listBeans = new ArrayList<>();
        communityGroupAdapter = new CommunityGroupAdapter(getActivity(), listBeans);
        listView.setAdapter(communityGroupAdapter);
        RefreshUtils.getInstance(smartRefreshLayout, getActivity()).defaultRefreSh();
        multipleStatusView.showLoading();
        mPresenter.getGroupList(page,10);
        RxBus.getIntanceBus().registerRxBus(RxMessageBean.class, new Consumer<RxMessageBean>() {
            @Override
            public void accept(RxMessageBean rxMessageBean) throws Exception {
                if (rxMessageBean.getMessageType() == 592) {
                    for (int i = 0; i < listBeans.size(); i++) {
                        if (rxMessageBean.getMessage().equals(listBeans.get(i).getId())) {
                            listBeans.get(i).setIs_group(rxMessageBean.getExtras());
                            if (rxMessageBean.getExtras().equals("0")) {
                                int userCount = Integer.parseInt(listBeans.get(i).getUser_count()) - 1;
                                listBeans.get(i).setUser_count(userCount + "");
                            }else {
                                int userCount = Integer.parseInt(listBeans.get(i).getUser_count()) + 1;
                                listBeans.get(i).setUser_count(userCount + "");
                            }
                            communityGroupAdapter.notifyDataSetChanged();
                        }
                    }

                    }else if (rxMessageBean.getMessageType() == 593) {
                        for (int i =  0; i < listBeans.size(); i++) {
                            if (rxMessageBean.getMessage().equals(listBeans.get(i).getId())) {
                                listBeans.get(i).setIs_group("0");
                                communityGroupAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            });

            setListener();
        }

        @Override
        protected void setListener () {
            super.setListener();
            multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    multipleStatusView.showLoading();
                    page = 1;
                    mPresenter.getGroupList(page,10);
                }
            });
            smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    page = 1;
                    mPresenter.getGroupList(page,10);
                }
            });
            smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    mPresenter.getGroupList(page,10);
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //小组详情
                    Intent intent = new Intent(getActivity(), GroupDetailsActivity.class);
                    intent.putExtra("groupId", listBeans.get(position).getId());
                    startActivityForResult(intent, 555);
                }
            });
            communityGroupAdapter.setOnItemJoinListener(new CommunityConfig.OnItemJoinListener() {

                @Override
                public void setJoinInfo(final String id, final String is_group, int position, final TextView join) {
                    index = position;
                    state = is_group;
                    if (is_group.equals("1")) {
                        DialogUtils dialogUtils = new DialogUtils(getActivity());
                        dialogUtils.setTitle("提示")
                                .setContent("确定要退出小组?")
                                .setOnClickListenter(new DialogUtils.OnClickListener() {
                                    @Override
                                    public void setYesClickListener() {
                                        mPresenter.setGroup(id, "0");
                                        join.setEnabled(false);
                                    }

                                    @Override
                                    public void setNoClickListener() {

                                    }
                                });
                    } else {
                        mPresenter.setGroup(id, "1");
                        join.setEnabled(false);
                    }
                }
            });
        }


        @Override
        public void ShowLoadView () {
            multipleStatusView.showLoading();
        }

        @Override
        public void NoNetWork () {
            multipleStatusView.showNoNetwork();
        }

        @Override
        public void NoData () {
            multipleStatusView.showEmpty();
        }

        @Override
        public void ErrorData () {
            multipleStatusView.showError();
        }

        @Override
        public void showErrorMsg (String msg){
            showShortToast(msg);
        }

        @Override
        public void showLoadV (String msg){
            showLoadDiaLog(msg);
        }

        @Override
        public void closeLoadV () {
            hidLoadDiaLog();
        }

        @Override
        public void SuccessData (Object o){
            if (page == 1) {
                listBeans.clear();
            }
            listBeans.addAll((Collection<? extends ListBean>) o);
            communityGroupAdapter.notifyDataSetChanged();
            multipleStatusView.showContent();
            page++;
        }

        @Override
        public LifecycleTransformer binLifecycle () {
            return bindToLifecycle();
        }

        @Override
        public void joinGroup () {
            communityGroupAdapter.updataItem(index, listView, state);
        }

        @Override
        public void isLoadMore ( boolean isLoadMore){
            RefreshUtils.getInstance(smartRefreshLayout, getActivity()).isLoadData(isLoadMore);
        }

}
