package com.zhiyun88.www.module_main.commonality.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.bean.CertificateBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.CertificateContranct;
import com.zhiyun88.www.module_main.commonality.mvp.model.CertificateModel;

import io.reactivex.disposables.Disposable;


public class CertificatePresenter extends CertificateContranct.CertificatePresenter {
    public CertificatePresenter(CertificateContranct.CertificateView iView) {
        this.mView = iView;
        this.mModel = new CertificateModel();
    }

    @Override
    public void getCertificateData(final int page) {
        HttpManager.newInstance().commonRequest(mModel.getCertificateData(page), new BaseObserver<Result<CertificateBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<CertificateBean> certificateBeanResult) {
                if (certificateBeanResult.getData() == null) {
                    if (page == 1) {
                        mView.ErrorData();
                    }else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadMore(true);
                    }
                }else {
                    if (certificateBeanResult.getData().getList() == null || certificateBeanResult.getData().getList().size() == 0) {
                        if (page == 1) {
                            mView.NoData();
                        }else {
                            mView.showErrorMsg("已经没有数据了!");
                            mView.isLoadMore(false);
                        }
                    }else {
                        if (certificateBeanResult.getData().getList().size() < AppConfigManager.newInstance().getAppConfig().getMaxPage()) {
                            //已经没有下一页了
                            mView.isLoadMore(false);
                        } else {
                            //还有下一页
                            mView.isLoadMore(true);
                        }
                        mView.SuccessData(certificateBeanResult.getData().getList());
                    }
                }
            }

            @Override
            public void onFail(ApiException e) {
                if (page == 1) {
                    mView.ErrorData();
                }else {
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

