package com.example.module_employees_world.presenter;

import android.widget.TextView;

import com.example.module_employees_world.bean.CommentLikeBean;
import com.example.module_employees_world.bean.CommentListBean;
import com.example.module_employees_world.bean.ParentBean;
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
        }, mView.binLifecycle());
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
        }, mView.binLifecycle());
    }

    @Override
    public void postsLike(String question_id) {
        HttpManager.newInstance().commonRequest(mModel.postsLike(question_id), new BaseObserver<Result<CommentLikeBean>>(AppUtils.getContext()) {

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {}

            @Override
            public void onSuccess(Result<CommentLikeBean> commentLikeBeanResult) {
                if (commentLikeBeanResult.getData()!=null){
                    mView.postsLike(commentLikeBeanResult.getData());
                }
            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(e.getMessage());
            }
        }, mView.binLifecycle());
    }

    @Override
    public void deletePost(String postId) {
        HttpManager.newInstance().commonRequest(mModel.deletePost(postId), new BaseObserver<Result>(AppUtils.getContext()) {

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {}

            @Override
            public void onSuccess(Result result) {
                mView.deletePost();
            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(e.getMessage());
            }
        }, mView.binLifecycle());
    }

    @Override
    public void deleteComment(String deleteComment, int position, int partenPosition) {
        HttpManager.newInstance().commonRequest(mModel.deleteComment(deleteComment), new BaseObserver<Result>(AppUtils.getContext()) {

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {}

            @Override
            public void onSuccess(Result result) {
                mView.deleteComment(position,partenPosition);
            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(e.getMessage());
            }
        }, mView.binLifecycle());
    }

    @Override
    public void editQuestion(String type, String id) {
        HttpManager.newInstance().commonRequest(mModel.editQuestion(type,id), new BaseObserver<Result>(AppUtils.getContext()) {

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {}

            @Override
            public void onSuccess(Result result) {
                mView.editQuestion();
            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(e.getMessage());
            }
        }, mView.binLifecycle());
    }

    @Override
    public void commentChildrenList(int partenPosition, int commentId, int page, int limit, int st) {
        HttpManager.newInstance().commonRequest(mModel.commentChildrenList(commentId,page,limit,st), new BaseObserver<Result<List<ParentBean>>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<List<ParentBean>> childrenList) {
                if (childrenList.getData()!=null){
                    mView.commentChildrenList(childrenList.getData(),partenPosition);
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
    public void acceptPosts(String id, String solve_status) {
        HttpManager.newInstance().commonRequest(mModel.acceptPosts(id,solve_status), new BaseObserver<Result>(AppUtils.getContext()) {

            @Override
            public void onSuccess(Result result) {
                mView.acceptPosts();
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
    public void acceptComment(String comment_id) {
        HttpManager.newInstance().commonRequest(mModel.acceptComment(comment_id), new BaseObserver<Result>(AppUtils.getContext()) {

            @Override
            public void onSuccess(Result result) {
                mView.acceptComment();
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
    public void invitationUser(String cover_user_id, String question_id) {
        HttpManager.newInstance().commonRequest(mModel.invitationUser(cover_user_id,question_id), new BaseObserver<Result>(AppUtils.getContext()) {

            @Override
            public void onSuccess(Result result) {
                mView.invitationUser();
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
}
