package com.zhiyun88.www.module_main.commonality.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.bean.UserDataBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.UserInfoContranct;
import com.zhiyun88.www.module_main.commonality.mvp.model.UserInfoModel;

import io.reactivex.disposables.Disposable;


public class UserInfoPresenter extends UserInfoContranct.UserInfoPresenter {
    public UserInfoPresenter(UserInfoContranct.UserInfoView iView) {
        this.mView = iView;
        this.mModel = new UserInfoModel();
    }

    @Override
    public void getUserData(String id) {
        HttpManager.newInstance().commonRequest(mModel.getUserData(id), new BaseObserver<Result<UserDataBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<UserDataBean> userDataBeanResult) {
                if(userDataBeanResult.getData()==null){
                    mView.showErrorMsg(AppUtils.getString(R.string.network_error));
                }else {
                    mView.SuccessData(userDataBeanResult.getData().getUser_info());
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

