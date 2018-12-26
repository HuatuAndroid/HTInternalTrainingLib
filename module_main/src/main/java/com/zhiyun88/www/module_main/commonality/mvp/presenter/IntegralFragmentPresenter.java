package com.zhiyun88.www.module_main.commonality.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.bean.RankingBean;
import com.zhiyun88.www.module_main.commonality.bean.RecordBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.IntegralFragmentContranct;
import com.zhiyun88.www.module_main.commonality.mvp.model.IntegralFragmentModel;

import io.reactivex.disposables.Disposable;


public class IntegralFragmentPresenter extends IntegralFragmentContranct.IntegralFragmentPresenter {
    public IntegralFragmentPresenter(IntegralFragmentContranct.IntegralFragmentView iView) {
        this.mView = iView;
        this.mModel = new IntegralFragmentModel();
    }

    @Override
    public void getRecord(String id, final int page) {
        HttpManager.newInstance().commonRequest(mModel.getRecord(id, page), new BaseObserver<Result<RecordBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<RecordBean> recordBeanResult) {
                if (recordBeanResult.getData() == null) {
                    if (page == 1) {
                        mView.ErrorData();
                    } else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadMore(true);
                    }
                } else {
                    if (recordBeanResult.getData().getList() == null || recordBeanResult.getData().getList().size() == 0) {
                        if (page == 1) {
                            mView.NoData();
                        } else {
                            mView.showErrorMsg("已经没有数据了!");
                            mView.isLoadMore(false);
                        }
                    } else {
                        if (recordBeanResult.getData().getList().size() < AppConfigManager.newInstance().getAppConfig().getMaxPage()) {
                            //已经没有下一页了
                            mView.isLoadMore(false);
                        } else {
                            //还有下一页
                            mView.isLoadMore(true);
                        }
                        mView.SuccessRecordData(recordBeanResult.getData().getList());
                    }
                }
            }
            @Override
            public void onFail (ApiException e){
                if (page == 1) {
                    mView.ErrorData();
                } else {
                    mView.showErrorMsg(AppUtils.getString(R.string.network_error));
                    mView.loadMore(true);
                }
            }

            @Override
            public void onSubscribe (Disposable d){
                addSubscribe(d);
            }

            @Override
            public void onComplete () {

            }
        },mView.binLifecycle());
    }

    @Override
    public void getRanking (String id){
        HttpManager.newInstance().commonRequest(mModel.getRanking(id), new BaseObserver<Result<RankingBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<RankingBean> rankingBeanResult) {
                if (rankingBeanResult.getData() == null) {
                    mView.ErrorData();
                } else {
                    if (rankingBeanResult.getData().getList() == null || rankingBeanResult.getData().getList().size() == 0) {
                        mView.NoData();
                    } else {
                        mView.SuccessRankingData(rankingBeanResult.getData().getList());
                    }
                }

            }

            @Override
            public void onFail(ApiException e) {
                mView.ErrorData();
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

