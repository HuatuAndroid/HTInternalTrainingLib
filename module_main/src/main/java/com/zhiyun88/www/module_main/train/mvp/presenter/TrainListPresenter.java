package com.zhiyun88.www.module_main.train.mvp.presenter;

import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfig;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.train.bean.TrainListBean;
import com.zhiyun88.www.module_main.train.mvp.contranct.TrainListContranct;
import com.zhiyun88.www.module_main.train.mvp.model.TrainListModel;

import io.reactivex.disposables.Disposable;

public class TrainListPresenter extends TrainListContranct.TrainListPresenter {
    public TrainListPresenter(TrainListContranct.TrainListView iView) {
        this.mView=iView;
        this.mModel=new TrainListModel();
    }

    @Override
    public void getTrainListData(String st, final int page) {
        HttpManager.newInstance().commonRequest(mModel.getTrainListData(st, page), new BaseObserver<Result<TrainListBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<TrainListBean> o) {
                TrainListBean trainListBean=o.getData();
                if(trainListBean==null){
                    if(page==1){
                        mView.ErrorData();
                    }else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadMore(true);
                    }
                }else {
                    if(trainListBean.getList()==null||trainListBean.getList().size()==0){
                        //无数据
                        if(page==1){
                            mView.NoData();
                        }else {
                            mView.showErrorMsg("已经没有数据了！");
                            mView.isLoadMore(false);
                        }
                    }else {
                        if(trainListBean.getList().size()< AppConfigManager.newInstance().getAppConfig().getMaxPage()){
                            //没有分页
                            mView.isLoadMore(false);
                        }else {
                            mView.isLoadMore(true);
                        }
                        mView.SuccessData(trainListBean);
                    }
                }
            }

            @Override
            public void onFail(ApiException e) {
                if(page==1){
                    mView.ErrorData();
                }else {
                    mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                    mView.isLoadMore(true);
                }
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
