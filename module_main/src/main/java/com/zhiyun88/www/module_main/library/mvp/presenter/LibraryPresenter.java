package com.zhiyun88.www.module_main.library.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.library.bean.LibraryTypeBean;
import com.zhiyun88.www.module_main.library.mvp.contranct.LibraryContranct;
import com.zhiyun88.www.module_main.library.mvp.model.LibraryModel;

import io.reactivex.disposables.Disposable;


public class LibraryPresenter extends LibraryContranct.LibraryPresenter {
    public LibraryPresenter(LibraryContranct.LibraryView iView) {
        this.mView = iView;
        this.mModel = new LibraryModel();
    }

    @Override
    public void getLibraryType() {
        HttpManager.newInstance().commonRequest(mModel.getLibraryType(), new BaseObserver<Result<LibraryTypeBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<LibraryTypeBean> libraryTypeBeanResult) {
                if (libraryTypeBeanResult.getData() == null || libraryTypeBeanResult.getData().getList().size() == 0) {
                }else {
                    mView.SuccessData(libraryTypeBeanResult.getData().getList());
                }
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
}

