package com.zhiyun88.www.module_main.community.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.community.bean.MyPartBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.CommunityMyJoinContranct;
import com.zhiyun88.www.module_main.community.mvp.model.CommunityMyJoinModel;

import io.reactivex.disposables.Disposable;


public class CommunityMyJoinPresenter extends CommunityMyJoinContranct.CommunityMyJoinPresenter {
    public CommunityMyJoinPresenter(CommunityMyJoinContranct.CommunityMyJoinView iView) {
        this.mView = iView;
        this.mModel = new CommunityMyJoinModel();
    }

    @Override
    public void getMyPartData(final int page) {
        HttpManager.newInstance().commonRequest(mModel.getMyPartData(page), new BaseObserver<Result<MyPartBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<MyPartBean> myPartBeanResult) {
                if (myPartBeanResult.getData() == null) {
                    if (page == 1) {
                        mView.ErrorData();
                    } else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadMore(true);
                    }
                } else {
                    if (myPartBeanResult.getData().getList() == null || myPartBeanResult.getData().getList().size() == 0) {
                        if (page == 1) {
                            mView.NoData();
                        } else {
                            mView.showErrorMsg("已经没有数据了!");
                            mView.isLoadMore(false);
                        }
                    } else {
                        if (myPartBeanResult.getData().getList().size() < AppConfigManager.newInstance().getAppConfig().getMaxPage()) {
                            //已经没有下一页了
                            mView.isLoadMore(false);
                        } else {
                            //还有下一页
                            mView.isLoadMore(true);
                        }
                        mView.SuccessData(myPartBeanResult.getData().getList());
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
}

