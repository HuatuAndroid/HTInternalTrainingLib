package com.zhiyun88.www.module_main.task.mvp.presenter;

import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.task.bean.TaskInfoListBean;
import com.zhiyun88.www.module_main.task.mvp.contranct.TaskInfoContranct;
import com.zhiyun88.www.module_main.task.mvp.model.TaskInfoModel;

import io.reactivex.disposables.Disposable;

public class TaskInfoPresenter extends TaskInfoContranct.TaskInfoPresenter {
    public TaskInfoPresenter(TaskInfoContranct.TaskInfoView iView) {
        this.mView=iView;
        this.mModel=new TaskInfoModel();
    }

    @Override
    public void getTaskInfoList(String id) {
        if(id==null||id.equals("")){
            mView.ErrorData();
            return;
        }
        HttpManager.newInstance().commonRequest(mModel.getTaskInfoList(id), new BaseObserver<Result<TaskInfoListBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<TaskInfoListBean> o) {
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
}
