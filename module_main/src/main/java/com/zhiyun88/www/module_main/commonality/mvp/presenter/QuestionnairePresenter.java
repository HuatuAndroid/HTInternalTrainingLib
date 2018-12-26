package com.zhiyun88.www.module_main.commonality.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.bean.QuestionNaireBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.QuestionnaireContranct;
import com.zhiyun88.www.module_main.commonality.mvp.model.QuestionnaireModel;

import io.reactivex.disposables.Disposable;


public class QuestionnairePresenter extends QuestionnaireContranct.QuestionnairePresenter {
    public QuestionnairePresenter(QuestionnaireContranct.QuestionnaireView iView) {
        this.mView = iView;
        this.mModel = new QuestionnaireModel();
    }

    @Override
    public void getQuestionData(int course_id) {
        if(course_id==-1){
            mView.ErrorData();
            return;
        }
        HttpManager.newInstance().commonRequest(mModel.getQuestionData(course_id), new BaseObserver<Result<QuestionNaireBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<QuestionNaireBean> questionNaireBeanResult) {
                if(questionNaireBeanResult.getData().getInfo()==null){
                    mView.ErrorData();
                }else {
                    mView.SuccessData(questionNaireBeanResult.getData().getInfo());
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

    @Override
    public void setQuestionData(int basis_id, int chapter_id, int grade, String nice_comment, String negative_comments) {
        HttpManager.newInstance().commonRequest(mModel.setQuestionData(basis_id,chapter_id,grade,nice_comment,negative_comments), new BaseObserver<Result>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result result) {
                if(result.getData()==null){
                    mView.showErrorMsg(AppUtils.getString(R.string.network_error));
                }else {
                    mView.setSuccess(result);
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

