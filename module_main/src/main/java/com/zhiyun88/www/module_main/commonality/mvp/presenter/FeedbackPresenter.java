package com.zhiyun88.www.module_main.commonality.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.FeedbackContranct;
import com.zhiyun88.www.module_main.commonality.mvp.model.FeedbackModel;

import io.reactivex.disposables.Disposable;


public class FeedbackPresenter extends FeedbackContranct.FeedbackPresenter {
    public FeedbackPresenter(FeedbackContranct.FeedbackView iView) {
        this.mView = iView;
        this.mModel = new FeedbackModel();
    }

    @Override
    public void getFeedback(String msg) {
        HttpManager.newInstance().commonRequest(mModel.getFeedback(msg), new BaseObserver<Result>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result result) {
                mView.SuccessData(result.getMsg());
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

