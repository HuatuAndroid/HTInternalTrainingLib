package com.zhiyun88.www.module_main.community.mvp.presenter;

import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.community.bean.ImageListBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.ReleaseTopicContranct;
import com.zhiyun88.www.module_main.community.mvp.model.ReleaseTopicModel;

import java.util.Map;

import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;


public class ReleaseTopicPresenter extends ReleaseTopicContranct.ReleaseTopicPresenter {
    public ReleaseTopicPresenter(ReleaseTopicContranct.ReleaseTopicView iView) {
        this.mView = iView;
        this.mModel = new ReleaseTopicModel();
    }


    @Override
    public void commitTopicData(String group_id, String title, String content, String is_anonymity, String path) {
        HttpManager.newInstance().commonRequest(mModel.commitTopicData(group_id, title, content, is_anonymity, path), new BaseObserver<Result>(AppUtils.getContext()) {

            @Override
            public void onSuccess(Result result) {
                mView.commitSuccess(result.getMsg());
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
        }, mView.binLifecycle());
    }

    @Override
    public void commitImage(Map<String,RequestBody> map) {
        HttpManager.newInstance().commonRequest(mModel.commitImage(map), new BaseObserver<Result<ImageListBean>>(AppUtils.getContext()) {

            @Override
            public void onSuccess(Result<ImageListBean> result) {
                if (result.getData() == null || result.getData().getList().size() == 0) {

                }else {
                    mView.SuccessData(result.getData().getList());
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

