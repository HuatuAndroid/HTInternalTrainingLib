package com.zhiyun88.www.module_main.main.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.main.bean.MyCourseBean;
import com.zhiyun88.www.module_main.main.mvp.contranct.MyCourseContranct;
import com.zhiyun88.www.module_main.main.mvp.model.MyCourseModel;

import io.reactivex.disposables.Disposable;


public class MyCoursePresenter extends MyCourseContranct.MyCoursePresenter {
    public MyCoursePresenter(MyCourseContranct.MyCourseView iView) {
        this.mView = iView;
        this.mModel = new MyCourseModel();
    }

    @Override
    public void getMyCourseData(int type, final int page) {
        HttpManager.newInstance().commonRequest(mModel.getMyCourseData(type,page), new BaseObserver<Result<MyCourseBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<MyCourseBean> myCourseBeanResult) {
                if(myCourseBeanResult.getData() == null){
                    if (page == 1) {
                        mView.ErrorData();
                        mView.loadMore(true);
                    }else {
                        mView.showErrorMsg(AppUtils.getString(R.string.network_error));
                        mView.loadMore(true);
                    }
                }else {
                    if (myCourseBeanResult.getData().getList() == null || myCourseBeanResult.getData().getList().size() == 0) {
                        if (page == 1) {
                            mView.NoData();
                            mView.loadMore(false);
                        }else {
                            mView.showErrorMsg("已经没有数据了");
                            mView.loadMore(false);
                        }
                    }else {
                        if (myCourseBeanResult.getData().getList().size()<AppConfigManager.newInstance().getAppConfig().getMaxPage()) {
                            //已经没有下一页了
                            mView.loadMore(false);
                        } else {
                            //还有下一页
                            mView.loadMore(true);
                        }
                        mView.SuccessData(myCourseBeanResult.getData().getList());
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

    @Override
    public void postUserComment(String courseId, String context, String grade, final int postion) {
        if(context==null||context.equals("")){
            mView.showErrorMsg("评价内容不能为空！");
            return;
        }
        mView.showLoadV("提交中....");
        HttpManager.newInstance().commonRequest(mModel.postUserComment(courseId, context, grade), new BaseObserver<Result>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result o) {
                mView.closeLoadV();
                if(o.getStatus()==200){
                    mView.successComment(o.getMsg(),true,postion);
                }else {
                    mView.successComment(o.getMsg(),false,postion);
                }
            }

            @Override
            public void onFail(ApiException e) {
                mView.closeLoadV();
                mView.successComment(e.getMessage(),true,postion);
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

