package com.example.module_employees_world.presenter;

import com.example.module_employees_world.bean.CommentInsertBean;
import com.example.module_employees_world.bean.NImageListsBean;
import com.example.module_employees_world.contranct.CommentSendDialogContranct;
import com.example.module_employees_world.contranct.EditPoatsContranct;
import com.example.module_employees_world.model.CommentSendDialogModel;
import com.example.module_employees_world.model.EditPostsModel;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;

import java.util.Map;

import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * author:LIENLIN
 * date:2019/4/6
 */
public class EditPostsPresenter extends EditPoatsContranct.EditPostsPresenter {

    public EditPostsPresenter(EditPoatsContranct.IEditPoatsView mView) {
        this.mView = mView;
        this.mModel = new EditPostsModel();
    }

    @Override
    public void commitImage(Map<String, RequestBody> map) {
        HttpManager.newInstance().commonRequest(mModel.commitImage(map), new BaseObserver<Result<NImageListsBean>>(AppUtils.getContext()) {

            @Override
            public void onSuccess(Result<NImageListsBean> result) {
                if (result.getData() == null || result.getData().getList().size() == 0) {
                    mView.showErrorMsg("无图片地址");
                }else {
                    mView.commitImage(result.getData().getList());
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
    public void commitTopicData(String comment_id, String title, String content, String is_anonymity, String path) {
        HttpManager.newInstance().commonRequest(mModel.commitTopicData(comment_id, title, content, is_anonymity, path), new BaseObserver<Result>(AppUtils.getContext()) {

            @Override
            public void onSuccess(Result result) {
                mView.closeLoadV();
                mView.commitTopicData(result.getMsg());
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
