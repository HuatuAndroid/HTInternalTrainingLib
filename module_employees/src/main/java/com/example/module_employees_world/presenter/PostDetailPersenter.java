package com.example.module_employees_world.presenter;

import com.example.module_employees_world.bean.CommentListBean;
import com.example.module_employees_world.bean.PostDetailBean;
import com.example.module_employees_world.contranct.PostsDetailContranct;
import com.example.module_employees_world.model.PostDetailModel;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;

import java.util.List;

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
    public void getCommentList(String question_id, String st, final String page, final String limit) {
        HttpManager.newInstance().commonRequest(mModel.getCommentList(question_id, st,page,limit), new BaseObserver<Result<CommentListBean>>(AppUtils.getContext()) {
            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {}

            @Override
            public void onSuccess(Result<CommentListBean> commentListBeanResult) {
                if (commentListBeanResult.getData()!=null){
                    if (commentListBeanResult.getData().list == null || commentListBeanResult.getData().list.size() == 0) {
                        if (page.equals("1")) {
                            mView.NoData();
                        } else {
                            mView.showErrorMsg("没有更多话题了");
                            mView.isLoadMore(false);
                        }
                    } else {
                        if (commentListBeanResult.getData().list.size() < Integer.valueOf(limit)) {
                            //已经没有下一页了
                            mView.isLoadMore(false);
                        } else {
                            //还有下一页
                            mView.isLoadMore(true);
                        }
                        mView.SuccessData(commentListBeanResult.getData().list);
                    }

                }else {
                    if (page.equals("1")) {
                        mView.ErrorData();
                    } else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadMore(true);
                    }
                }
            }

            @Override
            public void onFail(ApiException e) {
                if (page.equals("1")) {
                    mView.ErrorData();
                } else {
                    mView.showErrorMsg(e.getMessage());
                    mView.isLoadMore(true);
                }
            }
        }, mView.binLifecycle());
    }

    @Override
    public void getPostDetail(String question_id, String st) {
        HttpManager.newInstance().commonRequest(mModel.getPostDetail(question_id, st), new BaseObserver<Result<PostDetailBean>>(AppUtils.getContext()) {

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onSuccess(Result<PostDetailBean> postDetailBeanResult) {
                if (postDetailBeanResult.getData()!=null){
                    mView.getPostDetail(postDetailBeanResult.getData());
                }
            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(e.getMessage());
            }
        });
    }
}
