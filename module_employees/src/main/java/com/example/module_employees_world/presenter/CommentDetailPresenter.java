package com.example.module_employees_world.presenter;

import android.widget.TextView;

import com.example.module_employees_world.bean.CommentChildrenBean;
import com.example.module_employees_world.bean.CommentDetailBean;
import com.example.module_employees_world.bean.CommentLikeBean;
import com.example.module_employees_world.bean.ParentBean;
import com.example.module_employees_world.contranct.CommentDetailContranct;
import com.example.module_employees_world.model.CommentDetailodel;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfig;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * author:LIENLIN
 * date:2019/3/30
 */
public class CommentDetailPresenter extends CommentDetailContranct.CommentDetailPresenter {

    public CommentDetailPresenter(CommentDetailContranct.CommentDetialView view) {
        this.mView=view;
        this.mModel=new CommentDetailodel();
    }

    @Override
    public void commentDetail(int commentId) {
        HttpManager.newInstance().commonRequest(mModel.commentDetail(commentId), new BaseObserver<Result<CommentDetailBean>>(AppUtils.getContext()) {

            @Override
            public void onSuccess(Result<CommentDetailBean> commentDetailBeanResult) {
                if (commentDetailBeanResult.getData()!=null){
                    mView.SuccessData(commentDetailBeanResult.getData());
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
        },mView.binLifecycle());
    }

    @Override
    public void commentChildrenList(int commentId, int page, int limit, int st) {
        HttpManager.newInstance().commonRequest(mModel.commentChildrenList(commentId,page,limit,st), new BaseObserver<Result<List<ParentBean>>>(AppUtils.getContext()) {

            @Override
            public void onSuccess(Result<List<ParentBean>> childrenList) {
                if (childrenList.getData()!=null){
                    if ( childrenList.getData().size() == 0) {
                        if (page==1) {
                            mView.NoData();
                        } else {
                            mView.showErrorMsg("没有更多话题了");
                            mView.isLoadMore(false);
                        }
                    } else {
                        if (childrenList.getData().size() < Integer.valueOf(limit)) {
                            //已经没有下一页了
                            mView.isLoadMore(false);
                        } else {
                            //还有下一页
                            mView.isLoadMore(true);
                        }
                        mView.commentChildrenList(childrenList.getData());
                    }
                }else {
                    if (page==1) {
                        mView.ErrorData();
                    } else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadMore(true);
                    }
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
        },mView.binLifecycle());
    }

    @Override
    public void commentLike(String comment_id, TextView tvZan) {
        HttpManager.newInstance().commonRequest(mModel.commentLike(comment_id), new BaseObserver<Result<CommentLikeBean>>(AppUtils.getContext()) {

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {}

            @Override
            public void onSuccess(Result<CommentLikeBean> commentLikeBeanResult) {
                if (commentLikeBeanResult.getData()!=null&& mView!=null){
                    mView.commentLike(commentLikeBeanResult.getData(),tvZan);
                }
            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(e.getMessage());
            }
        });
    }

    @Override
    public void deleteComment(String deleteComment, int position) {
        HttpManager.newInstance().commonRequest(mModel.deleteComment(deleteComment), new BaseObserver<Result>(AppUtils.getContext()) {

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {}

            @Override
            public void onSuccess(Result result) {
                mView.deleteComment(position);
            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(e.getMessage());
            }
        });
    }
}
