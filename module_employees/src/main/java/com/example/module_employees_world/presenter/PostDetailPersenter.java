package com.example.module_employees_world.presenter;

import com.example.module_employees_world.bean.CommentListBean;
import com.example.module_employees_world.contranct.PostsDetailContranct;
import com.example.module_employees_world.model.PostDetailModel;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;

import io.reactivex.disposables.Disposable;

/**
 * author:LIENLIN
 * date:2019/3/25
 */
public class PostDetailPersenter extends PostsDetailContranct.PostDetailPresenter {

    public PostDetailPersenter(PostsDetailContranct.PostsDetailView view) {
        this.mView=view;
        this.mModel=new PostDetailModel();
    }

    @Override
    public void getCommentList(String question_id, String st) {
        HttpManager.newInstance().commonRequest(mModel.getCommentList(question_id, st), new BaseObserver<Result<CommentListBean>>(AppUtils.getContext()) {

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Result<CommentListBean> commentListBeanResult) {
                if (commentListBeanResult.getData()!=null){
                    mView.getCommentList(commentListBeanResult.getData());
                }
            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(e.getMessage());
            }
        }, mView.binLifecycle());
    }
}
