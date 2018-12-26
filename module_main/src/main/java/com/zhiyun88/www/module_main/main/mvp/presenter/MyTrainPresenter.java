package com.zhiyun88.www.module_main.main.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.main.bean.MyTrainBean;
import com.zhiyun88.www.module_main.main.mvp.contranct.MyTrainContranct;
import com.zhiyun88.www.module_main.main.mvp.model.MyTrainModel;

import io.reactivex.disposables.Disposable;


public class MyTrainPresenter extends MyTrainContranct.MyTrainPresenter {
    public MyTrainPresenter(MyTrainContranct.MyTrainView iView) {
        this.mView = iView;
        this.mModel = new MyTrainModel();
    }

    @Override
    public void getMyTrainData(int type, final int page) {
        HttpManager.newInstance().commonRequest(mModel.getMyTrainData(type,page), new BaseObserver<Result<MyTrainBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<MyTrainBean> myTrainBeanResult) {
                if(myTrainBeanResult.getData()==null){
                    if (page == 1) {
                        mView.ErrorData();
                        mView.loadMore(true);
                    }else {
                        mView.showErrorMsg(AppUtils.getString(R.string.network_error));
                        mView.loadMore(true);
                    }
                }else {
                    if (myTrainBeanResult.getData() == null || myTrainBeanResult.getData().getList().size() == 0) {
                        if (page == 1) {
                            mView.NoData();
                            mView.loadMore(false);
                        }else {
                            mView.showErrorMsg("已经没有数据了");
                            mView.loadMore(false);
                        }
                    }else {
                        if (myTrainBeanResult.getData().getList().size()<AppConfigManager.newInstance().getAppConfig().getMaxPage()) {
                            //已经没有下一页了
                            mView.loadMore(false);
                        } else {
                            //还有下一页
                            mView.loadMore(true);
                        }
                        mView.SuccessData(myTrainBeanResult.getData().getList());
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

