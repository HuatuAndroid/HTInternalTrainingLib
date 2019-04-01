package com.example.module_employees_world.presenter;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;

import com.example.module_employees_world.bean.NImageListsBean;
import com.example.module_employees_world.bean.TopicContentItem;
import com.example.module_employees_world.contranct.TopicEditContranct;
import com.example.module_employees_world.model.TopicEditModel;
import com.thefinestartist.utils.log.LogUtil;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

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
        HttpManager.newInstance().commonRequest(mModel.sendUpdateTopic(topicId, title, content, is_anonymity), new BaseObserver<Result>(AppUtils.getContext()) {
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

                } else {
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

    /**
     * 提交帖子
     *
     * @param group_id
     * @param title
     * @param content
     * @param is_anonymity
     * @param type
     */
    @Override
    public void commitTopicData(String group_id, String title, String content, String is_anonymity, String type) {
        HttpManager.newInstance().commonRequest(mModel.commitTopicData(group_id, title, content, is_anonymity, type), new BaseObserver<Result>(AppUtils.getContext()) {

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

}
