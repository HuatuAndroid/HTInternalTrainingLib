package com.example.module_employees_world.ui.topic;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.example.module_employees_world.CommunityServiceApi;
import com.example.module_employees_world.R;
import com.example.module_employees_world.adapter.CommunityGroupAdapter;
import com.example.module_employees_world.adapter.SelectGroupAdapter;
import com.example.module_employees_world.bean.CommunityGroupBean;
import com.example.module_employees_world.bean.ListBean;
import com.example.module_employees_world.common.CommonUtils;
import com.example.module_employees_world.contranct.CommunityGroupContranct;
import com.hss01248.dialog.StyledDialog;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.base.activity.BaseActivity;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author liuzhe
 * @date 2019/3/27
 */
public class SelectGroupActivity extends BaseActivity implements CommunityGroupContranct.CommunityGroupView, SelectGroupAdapter.OnItemJoinListener {

    private TopBarView mTopBarView;
    private MultipleStatusView multipleStatusView;
    private SmartRefreshLayout smartRefreshLayout;
    private ListView listView;
    private SelectGroupAdapter communityGroupAdapter;
    private int index;
    private String state;
    private int page = 1;
    private List<ListBean> listBeans;

    private String groupId = "";

    private Dialog mDiaLog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_group);

        groupId = getIntent().getStringExtra("groupId");

        mTopBarView = getViewById(R.id.mTopBarView);
        multipleStatusView = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        listView = getViewById(R.id.p_lv);

        RefreshUtils.getInstance(smartRefreshLayout,this).defaultRefreSh();

        if (TextUtils.isEmpty(groupId) || "".equals(groupId)){
            groupId = "";
        }

        listBeans = new ArrayList<>();
        communityGroupAdapter = new SelectGroupAdapter(this, listBeans, groupId, this);
        listView.setAdapter(communityGroupAdapter);

        processLogic(savedInstanceState);
        setListener();

    }

    @Override
    protected void setListener() {

        mTopBarView.setListener((v, action, extra) -> {
            //点击back键退出时，按键响应
            if (action == TopBarView.ACTION_LEFT_BUTTON) {
                finish();
            }
        });

        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleStatusView.showLoading();
                page = 1;
                getGroupList(page,30);
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getGroupList(page,30);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getGroupList(page,30);
            }
        });

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

        showLoadV("加载中...");
        getGroupList(page, 10);

    }

    public void getGroupList(int page, int sizeCount){
        HttpManager.newInstance().commonRequest(HttpManager.newInstance().getService(CommunityServiceApi.class).getGroupList(page,sizeCount),
                new BaseObserver<Result<CommunityGroupBean>>(AppUtils.getContext()) {
                    @Override
                    public void onSuccess(Result<CommunityGroupBean> communityGroupBeanResult) {
                        if (communityGroupBeanResult.getData().getGroup_list() == null) {
                            if (page == 1) {
                                ErrorData();
                            } else {
                                showErrorMsg("服务器繁忙，请稍后尝试！");
                                isLoadMore(true);
                            }
                        } else {
                            if (communityGroupBeanResult.getData().getGroup_list().getList() == null || communityGroupBeanResult.getData().getGroup_list().getList().size() == 0) {
                                if (page == 1) {
                                    NoData();
                                } else {
                                    showErrorMsg("已经没有数据了!");
                                    isLoadMore(false);
                                }
                            } else {
                                if (communityGroupBeanResult.getData().getGroup_list().getList().size() < 6) {
                                    //已经没有下一页了
                                    isLoadMore(false);
                                } else {
                                    //还有下一页
                                    isLoadMore(true);
                                }
                                SuccessData(communityGroupBeanResult.getData().getGroup_list().getList());
                            }
                        }
                        closeLoadV();

                    }

                    @Override
                    public void onFail(ApiException e) {
                        if (page == 1) {
                            ErrorData();
                        } else {
                            showErrorMsg(e.getMessage());
                            isLoadMore(true);
                        }
                        closeLoadV();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
//                addSubscribe(d);
                    }

                    @Override
                    public void onComplete() {

                    }
                }, binLifecycle());
    }

    @Override
    public void ShowLoadView() {

    }

    @Override
    public void NoNetWork() {

    }

    @Override
    public void NoData() {

    }

    @Override
    public void ErrorData() {

    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void showLoadV(String msg) {
        mDiaLog = StyledDialog.buildLoading(msg).show();
    }

    @Override
    public void closeLoadV() {
        if (mDiaLog == null)
            return;
        if (mDiaLog.isShowing()) {
            mDiaLog.dismiss();
        }
    }

    @Override
    public void SuccessData(Object o) {
        if (page == 1) {
            listBeans.clear();
        }
        listBeans.addAll((Collection<? extends ListBean>) o);
        communityGroupAdapter.notifyDataSetChanged();
        multipleStatusView.showContent();
        page++;
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void joinGroup() {

    }

    @Override
    public void isLoadMore(boolean isLoadMore) {
        RefreshUtils.getInstance(smartRefreshLayout, this).isLoadData(isLoadMore);
    }

    @Override
    public void onItemJoinListener(ListBean listBean) {

        Intent intent = new Intent();
        intent.putExtra("group_id", listBean.getId());
        intent.putExtra("groupName", listBean.getName());
        this.setResult(CommonUtils.SELECT_GROUP, intent);

        finish();

    }
}
