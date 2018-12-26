package com.zhiyun88.www.module_main.sign.mvp.presenter;

import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.sign.bean.SignBean;
import com.zhiyun88.www.module_main.sign.mvp.contranct.UserSignContranct;
import com.zhiyun88.www.module_main.sign.mvp.model.UserSignModel;

import io.reactivex.disposables.Disposable;

public class UserSignPresenter extends UserSignContranct.UserSignPresenter {
    public UserSignPresenter(UserSignContranct.UserSignView iView) {
        this.mView=iView;
        this.mModel=new UserSignModel();
    }

    @Override
    public void userSign(String basis_id, String chapter_id) {
        mView.showLoadV("签到中...");
        HttpManager.newInstance().commonRequest(mModel.userSign(basis_id, chapter_id), new BaseObserver<Result>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result o) {
                mView.closeLoadV();
                if(o.getStatus()==200){
                    mView.showErrorMsg(o.getMsg(),false,true);
                }else {
                    mView.showErrorMsg(o.getMsg(),false,false);
                }

            }

            @Override
            public void onFail(ApiException e) {
                mView.closeLoadV();
                mView.showErrorMsg(e.getMessage(),false,false);
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

    @Override
    public void getUserSign(String url) {
        HttpManager.newInstance().commonRequest(mModel.getUserSign(url), new BaseObserver<Result<SignBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<SignBean> o) {
                if(o.getStatus()==200){
                    if(o.getData()==null){
                        mView.ErrorData();
                    }else {
                        if(o.getData().getList().getCode()==0){
                            mView.SuccessData(o.getData());
                        }else {
                            if(o.getData().getList().getCode()==200){
                                mView.SuccessData(o.getData());
                            }else {
                                mView.ErrorData();
                                mView.showErrorMsg(o.getData().getList().getMsg(),true,false);
                            }
                        }


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
