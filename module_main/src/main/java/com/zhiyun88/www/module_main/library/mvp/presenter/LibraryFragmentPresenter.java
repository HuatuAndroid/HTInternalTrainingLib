package com.zhiyun88.www.module_main.library.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.library.bean.LibraryDataBean;
import com.zhiyun88.www.module_main.library.bean.LibraryDetailsBean;
import com.zhiyun88.www.module_main.library.mvp.contranct.LibraryFragmentContranct;
import com.zhiyun88.www.module_main.library.mvp.model.LibraryFragmentModel;
import com.zhiyun88.www.module_main.main.bean.MyCourseBean;

import io.reactivex.disposables.Disposable;


public class LibraryFragmentPresenter extends LibraryFragmentContranct.LibraryFragmentPresenter {
    public LibraryFragmentPresenter(LibraryFragmentContranct.LibraryFragmentView iView) {
        this.mView = iView;
        this.mModel = new LibraryFragmentModel();
    }

    @Override
    public void getLibraryData(String id, final int page) {
        HttpManager.newInstance().commonRequest(mModel.getLibraryData(id, page), new BaseObserver<Result<LibraryDataBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<LibraryDataBean> libraryDataBeanResult) {
                if (libraryDataBeanResult.getData() == null) {
                    if (page == 1) {
                        mView.ErrorData();
                    } else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadMore(true);
                    }
                } else {
                    if (libraryDataBeanResult.getData().getList() == null || libraryDataBeanResult.getData().getList().size() == 0) {
                        if (page == 1) {
                            mView.NoData();
                        } else {
                            mView.showErrorMsg("已经没有数据了!");
                            mView.isLoadMore(false);
                        }
                    } else {
                        if (libraryDataBeanResult.getData().getList().size() < AppConfigManager.newInstance().getAppConfig().getMaxPage()) {
                            //已经没有下一页了
                            mView.isLoadMore(false);
                        } else {
                            //还有下一页
                            mView.isLoadMore(true);
                        }
                        mView.SuccessData(libraryDataBeanResult.getData().getList());
                    }
                }
            }

            @Override
            public void onFail(ApiException e) {
                if (page == 1) {
                    mView.ErrorData();
                } else {
                    mView.showErrorMsg(e.getMessage());
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
        }, mView.binLifecycle());
    }

    @Override
    public void setLibraryCollection(String libraryId, String userId, String isClick) {
        HttpManager.newInstance().commonRequest(mModel.setLibraryCollection(libraryId, userId, isClick), new BaseObserver<Result>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result result) {
                mView.showErrorMsg(result.getMsg());
                mView.setCollectedSuccess();
            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(e.getMessage());
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
    public void getLibraryDetails(String id) {
        HttpManager.newInstance().commonRequest(mModel.requestLibraryDetails(id), new BaseObserver<Result<LibraryDetailsBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<LibraryDetailsBean> libraryDetailsBeanResult) {
                mView.bindDetailsData(libraryDetailsBeanResult.getData());
            }

            @Override
            public void onFail(ApiException e) {
                mView.bindDetailsData(null);
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

