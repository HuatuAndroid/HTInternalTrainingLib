package com.zhiyun88.www.module_main.course.mvp.presenter;

import android.util.Log;

import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.course.bean.CourseInfoBean;
import com.zhiyun88.www.module_main.course.mvp.contranct.CourseInfoContranct;
import com.zhiyun88.www.module_main.course.mvp.model.CourseInfoModel;

import io.reactivex.disposables.Disposable;

public class CourseInfoPresenter extends CourseInfoContranct.CourseInfoPresenter {
    public CourseInfoPresenter(CourseInfoContranct.CourseInfoView iView) {
        this.mView = iView;
        this.mModel = new CourseInfoModel();
    }

    @Override
    public void getCourseInfoData(String basis_id) {
        if(basis_id==null||basis_id.equals("")){
            mView.ErrorData();
            return;
        }
        HttpManager.newInstance().commonRequest(mModel.getCourseInfoData(basis_id), new BaseObserver<Result<CourseInfoBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<CourseInfoBean> o) {
                if(o.getData()==null){
                    mView.ErrorData();
                }else {
                    mView.SuccessData(o.getData());
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

    @Override
    public void buyCourse(String basis_id) {
        mView.showLoadV("请稍等...");
        HttpManager.newInstance().commonRequest(mModel.buyCourse(basis_id), new BaseObserver<Result>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result o) {
                mView.closeLoadV();
                if(o.getData()==null){
                    mView.showErrorMsg(o.getMsg());
                }else {
                    if(o.getStatus()==200){
                        mView.joinSuccess(o.getMsg());
                    }else {
                        mView.showErrorMsg(o.getMsg());
                    }

                }
            }

            @Override
            public void onFail(ApiException e) {
                mView.closeLoadV();
                mView.showErrorMsg(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void uploadWatchVideoTime(long video_id, String start_time, String end_time, int watch_time, int abort_time) {
        HttpManager.newInstance().commonRequest(mModel.uploadWatchVideoTime(video_id, start_time, end_time, watch_time,abort_time), new BaseObserver<Result>(AppUtils.getContext()){
            @Override
            public void onSuccess(Result result) {

            }

            @Override
            public void onFail(ApiException e) {

            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}

