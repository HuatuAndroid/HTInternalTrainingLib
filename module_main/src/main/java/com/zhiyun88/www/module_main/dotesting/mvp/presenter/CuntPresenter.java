package com.zhiyun88.www.module_main.dotesting.mvp.presenter;

import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.dotesting.bean.CountBean;
import com.zhiyun88.www.module_main.dotesting.mvp.contranct.CuntContranct;
import com.zhiyun88.www.module_main.dotesting.mvp.model.CuntModel;

import io.reactivex.disposables.Disposable;

public class CuntPresenter extends CuntContranct.CuntPresenter {
    public CuntPresenter(CuntContranct.CuntView iView) {
        this.mView=iView;
        this.mModel=new CuntModel();
    }

    @Override
    public void getCuntData(String id) {
        HttpManager.newInstance().commonRequest(mModel.getCuntData(id), new BaseObserver<Result<CountBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<CountBean> o) {
                if(o.getData()==null){
                    mView.ErrorData();
                }else {
                    mView.SuccessData(o.getData());
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
        },mView.binLifecycle());
    }
}
