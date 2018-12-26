package com.zhiyun88.www.module_main.course.mvp.presenter;

import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.course.bean.BjyTokenBean;
import com.zhiyun88.www.module_main.course.bean.CourseChildBean;
import com.zhiyun88.www.module_main.course.mvp.contranct.BjyTokenContranct;
import com.zhiyun88.www.module_main.course.mvp.model.BjyTokenModel;

import io.reactivex.disposables.Disposable;

public class BjyTokenPresenter extends BjyTokenContranct.BjyTokenPresenter {
    public BjyTokenPresenter(BjyTokenContranct.BjyTokenView iView) {
        this.mView=iView;
        this.mModel=new BjyTokenModel();
    }

    @Override
    public void getBjyToken(String id, final boolean isDown, final CourseChildBean courseChildBean) {
        if(id==null||id.equals("")){
            mView.showErrorMsg("该课程出现异常，请联系管理员");
            return;
        }
        mView.showLoadV("请稍等....");
        HttpManager.newInstance().commonRequest(mModel.getBjyToken(id), new BaseObserver<Result<BjyTokenBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<BjyTokenBean> o) {
                mView.closeLoadV();
                if (o.getData()==null) {
                    mView.showErrorMsg(o.getMsg());
                } else {
                    if (o.getStatus()==200){
                        mView.SuccessBjyToken(o.getData(),isDown,courseChildBean);
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
        },mView.binLifecycle());
    }
}
