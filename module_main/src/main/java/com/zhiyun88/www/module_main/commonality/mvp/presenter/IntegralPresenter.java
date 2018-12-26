package com.zhiyun88.www.module_main.commonality.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.bean.IntegralBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.IntegralContranct;
import com.zhiyun88.www.module_main.commonality.mvp.model.IntegralModel;

import io.reactivex.disposables.Disposable;


public class IntegralPresenter extends IntegralContranct.IntegralPresenter {
    public IntegralPresenter(IntegralContranct.IntegralView iView) {
        this.mView = iView;
        this.mModel = new IntegralModel();
    }

    @Override
    public void getIntegral(String id) {
        HttpManager.newInstance().commonRequest(mModel.getIntegral(id), new BaseObserver<Result<IntegralBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<IntegralBean> integralBeanResult) {
                if(integralBeanResult.getData()==null){
                    mView.showErrorMsg(AppUtils.getString(R.string.network_error));
                }else {
                    mView.SuccessData(integralBeanResult.getData());
                }

            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(AppUtils.getString(R.string.network_error));
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

