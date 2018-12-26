package com.zhiyun88.www.module_main.information.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.information.bean.InformationTypeBean;
import com.zhiyun88.www.module_main.information.mvp.contranct.InformationContranct;
import com.zhiyun88.www.module_main.information.mvp.model.InformationModel;

import io.reactivex.disposables.Disposable;


public class InformationPresenter extends InformationContranct.InformationPresenter {
    public InformationPresenter(InformationContranct.InformationView iView) {
        this.mView = iView;
        this.mModel = new InformationModel();
    }

    @Override
    public void getInformationType() {
        HttpManager.newInstance().commonRequest(mModel.getInformationType(), new BaseObserver<Result<InformationTypeBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<InformationTypeBean> informationTypeBeanResult) {
                if (informationTypeBeanResult.getData() == null || informationTypeBeanResult.getData().getList().size() == 0) {
                }else {
                    mView.SuccessData(informationTypeBeanResult.getData().getList());
                }
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

