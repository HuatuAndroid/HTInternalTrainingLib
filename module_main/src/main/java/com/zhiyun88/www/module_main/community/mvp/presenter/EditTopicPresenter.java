package com.zhiyun88.www.module_main.community.mvp.presenter;

import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.community.bean.ImageListBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.UpdateTopicContranct;
import com.zhiyun88.www.module_main.community.mvp.model.UpdateTopicModel;

import java.util.Map;

import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * author:LIENLIN
 * date:2019/2/25
 */
public class EditTopicPresenter extends UpdateTopicContranct.UpdateTopicPresenter {

    public EditTopicPresenter(UpdateTopicContranct.UpdateTopicView mView) {
        this.mView = mView;
        this.mModel = new UpdateTopicModel();
    }

    @Override
    public void sendUpdateTopic(String topicId, String title, String content, String is_anonymity) {
        HttpManager.newInstance().commonRequest(mModel.sendUpdateTopic(topicId,title,content,is_anonymity), new BaseObserver<Result>(AppUtils.getContext()) {
            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onSuccess(Result result) {
                mView.updateTopicSuccess(result.getMsg());
            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(e.getMessage());
            }
        });
    }

    @Override
    public void commitImage(Map<String, RequestBody> map) {
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
