package com.zhiyun88.www.module_main.main.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.main.bean.MyTaskBean;
import com.zhiyun88.www.module_main.main.mvp.contranct.MyTaskContranct;
import com.zhiyun88.www.module_main.main.mvp.model.MyTaskModel;

import io.reactivex.disposables.Disposable;


public class MyTaskPresenter extends MyTaskContranct.MyTaskPresenter {
    public MyTaskPresenter(MyTaskContranct.MyTaskView iView) {
        this.mView = iView;
        this.mModel = new MyTaskModel();
    }

    @Override
    public void getMyTaskData(String complete_type, String type, final int page) {
        HttpManager.newInstance().commonRequest(mModel.getMyTaskData(complete_type,type,page), new BaseObserver<Result<MyTaskBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<MyTaskBean> myTaskBeanResult) {
                if(myTaskBeanResult.getData()==null){
                    if (page == 1) {
                        mView.ErrorData();
                        mView.loadMore(true);
                    }else {
                        mView.showErrorMsg(AppUtils.getString(R.string.network_error));
                        mView.loadMore(true);
                    }
                }else {
                    if (myTaskBeanResult.getData().getList()== null || myTaskBeanResult.getData().getList().size() == 0) {
                        if (page == 1) {
                            mView.NoData();
                            mView.loadMore(false);
                        }else {
                            mView.showErrorMsg("已经没有数据了");
                            mView.loadMore(false);
                        }
                    }else {
                        if (myTaskBeanResult.getData().getList().size()<AppConfigManager.newInstance().getAppConfig().getMaxPage()) {
                            //已经没有下一页了
                            mView.loadMore(false);
                        } else {
                            //还有下一页
                            mView.loadMore(true);
                        }
                        mView.SuccessData(myTaskBeanResult.getData().getList());
                    }
                }

            }

            @Override
            public void onFail(ApiException e) {
                if (page == 1) {
                    mView.ErrorData();
                    mView.loadMore(true);
                } else {
                    mView.showErrorMsg(AppUtils.getString(R.string.network_error));
                    mView.loadMore(true);
                }
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

