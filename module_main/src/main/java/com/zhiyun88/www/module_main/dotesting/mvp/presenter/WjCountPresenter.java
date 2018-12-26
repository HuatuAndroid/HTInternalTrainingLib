package com.zhiyun88.www.module_main.dotesting.mvp.presenter;

import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.dotesting.bean.WjBean;
import com.zhiyun88.www.module_main.dotesting.mvp.contranct.WjCountContranct;
import com.zhiyun88.www.module_main.dotesting.mvp.model.WjCountModel;

import io.reactivex.disposables.Disposable;

public class WjCountPresenter extends WjCountContranct.WjCountPresenter {
    public WjCountPresenter(WjCountContranct.WjCountView iView) {
        this.mView=iView;
        this.mModel=new WjCountModel();
    }

    @Override
    public void getWjCountData(String id) {
        HttpManager.newInstance().commonRequest(mModel.getWjCountData(id), new BaseObserver<Result<WjBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<WjBean> o) {
                if(o.getStatus()==200){
                    if(o==null){
                        mView.ErrorData();
                    }else {
                        mView.SuccessData(o.getData());

                    }
                }else {
                    mView.ErrorData();
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
