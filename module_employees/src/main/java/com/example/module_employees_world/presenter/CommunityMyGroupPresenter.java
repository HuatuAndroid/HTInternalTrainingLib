package com.example.module_employees_world.presenter;


import com.example.module_employees_world.bean.MyItemBean;
import com.example.module_employees_world.contranct.CommunityMyGroupContranct;
import com.example.module_employees_world.model.CommunityMyGroupModel;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;

import io.reactivex.disposables.Disposable;


public class CommunityMyGroupPresenter extends CommunityMyGroupContranct.CommunityMyGroupPresenter {
    public CommunityMyGroupPresenter(CommunityMyGroupContranct.CommunityMyGroupView iView) {
        this.mView = iView;
        this.mModel = new CommunityMyGroupModel();
    }

    @Override
    public void getMyGroupData(final int page) {
        HttpManager.newInstance().commonRequest(mModel.getMyGroupData(page), new BaseObserver<Result<MyItemBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<MyItemBean> myItemBeanResult) {
                if (myItemBeanResult.getData() == null) {
                    if (page == 1) {
                        mView.ErrorData();
                    } else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadMore(true);
                    }
                } else {
                    if (myItemBeanResult.getData().getList() == null || myItemBeanResult.getData().getList().size() == 0) {
                        if (page == 1) {
                            mView.NoData();
                        } else {
                            mView.showErrorMsg("已经没有数据了!");
                            mView.isLoadMore(false);
                        }
                    } else {
                        if (myItemBeanResult.getData().getList().size() < AppConfigManager.newInstance().getAppConfig().getMaxPage()) {
                            //已经没有下一页了
                            mView.isLoadMore(false);
                        } else {
                            //还有下一页
                            mView.isLoadMore(true);
                        }
                        mView.SuccessData(myItemBeanResult.getData().getList());
                    }
                }
            }

            @Override
            public void onFail(ApiException e) {
                if (page == 1) {
                    mView.ErrorData();
                } else {
                    mView.showErrorMsg(e.getMessage());
                    mView.isLoadMore(true);
                }
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        }, mView.binLifecycle());
    }

    @Override
    public void setGroup(String groupId, String states) {
        HttpManager.newInstance().commonRequest(mModel.setGroup(groupId, states), new BaseObserver<Result>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result result) {
                mView.joinGroup();
                mView.showErrorMsg(result.getMsg());
            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        }, mView.binLifecycle());
    }
}

