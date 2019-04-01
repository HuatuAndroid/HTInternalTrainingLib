package com.example.module_employees_world.presenter;

import android.app.Activity;

import com.example.module_employees_world.bean.NImageListsBean;
import com.example.module_employees_world.bean.TopicContentItem;
import com.example.module_employees_world.contranct.TopicEditContranct;
import com.example.module_employees_world.model.TopicEditModel;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * @author liuzhe
 * @date 2019/3/21
 */
public class TopicEditPresenter extends TopicEditContranct.Presenter {

    private Activity mActivity;
    public Disposable subscribe;

    public TopicEditPresenter(TopicEditContranct.View iView, Activity mActivity) {

        this.mActivity = mActivity;
        this.mView = iView;
        this.mModel = new TopicEditModel();

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
        HttpManager.newInstance().commonRequest(mModel.commitImage(map), new BaseObserver<Result<NImageListsBean>>(AppUtils.getContext()) {

            @Override
            public void onSuccess(Result<NImageListsBean> result) {
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


    public void disposeImages(TopicContentItem[] topicContentItems){

        Map<String, File> map = new HashMap<>();
        for (int i = 0; i < topicContentItems.length; i++) {
            File file = new File(topicContentItems[i].localUrl);
            map.put("file" + i, file);
        }

    }

}
