package com.zhiyun88.www.module_main.dotesting.mvp.presenter;

import android.util.Log;

import com.jungan.www.common_dotest.bean.QuestionBankBean;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.dotesting.bean.SubmitBean;
import com.zhiyun88.www.module_main.dotesting.bean.SubmitTestBean;
import com.zhiyun88.www.module_main.dotesting.mvp.contranct.CommonTestContranct;
import com.zhiyun88.www.module_main.dotesting.mvp.model.CommonTestModel;


import java.util.List;

import io.reactivex.disposables.Disposable;

public class CommonTestPresenter extends CommonTestContranct.CommonTestPresenter {
    public CommonTestPresenter(CommonTestContranct.CommonTestView iView) {
        this.mView = iView;
        this.mModel = new CommonTestModel();
    }


    @Override
    public void getCommonTest(String id, String taskId, int testType) {
        HttpManager.newInstance().commonRequest(mModel.getCommonTest(id, taskId, testType), new BaseObserver<List<QuestionBankBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(List<QuestionBankBean> questionBankBeans) {
                if (questionBankBeans == null || questionBankBeans.size() == 0) {
                } else {
                    mView.SuccessData(questionBankBeans);
                }
            }

            @Override
            public void onFail(ApiException e) {
                Log.e("看看", e.getErrorCode() + "----");
                mView.showDidlogMsg(e.getMessage());
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
    public void submitTest(SubmitTestBean submitTestBean) {
        HttpManager.newInstance().commonRequest(mModel.submitTest(submitTestBean), new BaseObserver<Result<SubmitBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<SubmitBean> result) {
                if (result.getStatus() == 200) {
                    result.getData().setMsg(result.getMsg());
                    mView.submitSuccess(result.getData());
                } else {
                    mView.showErrorMsg(result.getMsg());

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
