package com.zhiyun88.www.module_main.information.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.information.bean.InformationDataBean;
import com.zhiyun88.www.module_main.information.mvp.contranct.InformationFragmentContranct;
import com.zhiyun88.www.module_main.information.mvp.model.InformationFragmentModel;
import com.zhiyun88.www.module_main.main.bean.MyCourseBean;

import io.reactivex.disposables.Disposable;


public class InformationFragmentPresenter extends InformationFragmentContranct.InformationFragmentPresenter {
    public InformationFragmentPresenter(InformationFragmentContranct.InformationFragmentView iView) {
        this.mView = iView;
        this.mModel = new InformationFragmentModel();
    }

    @Override
    public void getInformationData(String id, final int page) {
        HttpManager.newInstance().commonRequest(mModel.getInformationData(id,page ), new BaseObserver<Result<InformationDataBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<InformationDataBean> informationDataBeanResult) {
                if (informationDataBeanResult.getData() == null) {
                    if (page == 1) {
                        mView.ErrorData();
                    } else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadMore(true);
                    }
                } else {
                    if (informationDataBeanResult.getData().getList() == null || informationDataBeanResult.getData().getList().size() == 0) {
                        if (page == 1) {
                            mView.NoData();
                        } else {
                            mView.showErrorMsg("已经没有数据了!");
                            mView.isLoadMore(false);
                        }
                    } else {
                        if (informationDataBeanResult.getData().getList().size() < AppConfigManager.newInstance().getAppConfig().getMaxPage()) {
                            //已经没有下一页了
                            mView.isLoadMore(false);
                        } else {
                            //还有下一页
                            mView.isLoadMore(true);
                        }
                        mView.SuccessData(informationDataBeanResult.getData().getList());
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

