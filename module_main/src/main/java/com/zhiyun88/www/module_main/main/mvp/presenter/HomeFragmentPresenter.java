package com.zhiyun88.www.module_main.main.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.main.bean.HomeBean;
import com.zhiyun88.www.module_main.main.mvp.contranct.HomeFragmentContranct;
import com.zhiyun88.www.module_main.main.mvp.model.HomeFragmentModel;

import io.reactivex.disposables.Disposable;


public class HomeFragmentPresenter extends HomeFragmentContranct.HomeFragmentPresenter {
    public HomeFragmentPresenter(HomeFragmentContranct.HomeFragmentView iView) {
        this.mView = iView;
        this.mModel = new HomeFragmentModel();
    }

    @Override
    public void getHomeData() {
        HttpManager.newInstance().commonRequest(mModel.getHomeData(), new BaseObserver<Result<HomeBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<HomeBean> homeBeanResult) {
                if(homeBeanResult.getData()==null){
                    mView.ErrorData();
                }else {
                    mView.SuccessData(homeBeanResult.getData());
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
