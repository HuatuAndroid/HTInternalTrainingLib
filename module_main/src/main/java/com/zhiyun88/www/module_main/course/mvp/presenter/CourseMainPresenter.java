package com.zhiyun88.www.module_main.course.mvp.presenter;

import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.course.bean.CourseMainBean;
import com.zhiyun88.www.module_main.course.mvp.contranct.CourseMainContranct;
import com.zhiyun88.www.module_main.course.mvp.model.CourseMainModel;
import com.zhiyun88.www.module_main.train.bean.TrainListBean;

import io.reactivex.disposables.Disposable;

public class CourseMainPresenter extends CourseMainContranct.CourseMainPresenter {
    public CourseMainPresenter(CourseMainContranct.CourseMainView iView) {
        this.mView=iView;
        this.mModel=new CourseMainModel();
    }

    @Override
    public void getItemData(String title, String classify_id1, String classify_id2, String is_hot, String is_new, String is_free, String is_price, final int page) {
        HttpManager.newInstance().commonRequest(mModel.getItemData(title, classify_id1, classify_id2, is_hot, is_new, is_free, is_price, page), new BaseObserver<Result<CourseMainBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<CourseMainBean> o) {
                CourseMainBean trainListBean=o.getData();
                if(trainListBean==null){
                    if(page==1){
                        mView.ErrorData();
                    }else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadData(true);
                    }

                }else {
                    if(trainListBean.getList()==null||trainListBean.getList().size()==0){
                        //无数据
                        if(page==1){
                            mView.NoData();
                        }else {
                            mView.showErrorMsg("已经没有数据了");
                            mView.isLoadData(false);
                        }

                    }else {
                        if(trainListBean.getList().size()< AppConfigManager.newInstance().getAppConfig().getMaxPage()){
                            //没有分页
                            mView.isLoadData(false);
                        }else {
                            mView.isLoadData(true);
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
                    mView.isLoadData(true);
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
