package com.example.module_employees_world.presenter;

import com.example.module_employees_world.bean.NImageBean;
import com.example.module_employees_world.bean.NImageListsBean;
import com.example.module_employees_world.contranct.CommentSendDialogContranct;
import com.example.module_employees_world.model.CommentSendDialogModel;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;

import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * author:LIENLIN
 * date:2019/4/1
 */
public class CommentSendDialogPresenter extends CommentSendDialogContranct.CommentSendDialogPresenter {

    public CommentSendDialogPresenter(CommentSendDialogContranct.ICommentSendDialogView view) {
        this.mModel=new CommentSendDialogModel();
        this.mView=view;
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
    public void sendComment(String question_id, String content, String comment_picture, String comment_face, String is_anonymity, String comment_id) {
        HttpManager.newInstance().commonRequest(mModel.sendComment(question_id, content, comment_picture, comment_face, is_anonymity, comment_id), new BaseObserver<Result>(AppUtils.getContext()) {

            @Override
            public void onSuccess(Result result) {
                mView.sendCommment();
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
        });
    }
}
