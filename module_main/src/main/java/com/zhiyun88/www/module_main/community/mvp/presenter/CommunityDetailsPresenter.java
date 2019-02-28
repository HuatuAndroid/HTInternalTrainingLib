package com.zhiyun88.www.module_main.community.mvp.presenter;

import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.community.bean.CommunityDetailsBean;
import com.zhiyun88.www.module_main.community.bean.DetailsCommentBean;
import com.zhiyun88.www.module_main.community.bean.DetailsLikeBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.CommunityDetailsContranct;
import com.zhiyun88.www.module_main.community.mvp.model.CommunityDetailsModel;

import io.reactivex.disposables.Disposable;


public class CommunityDetailsPresenter extends CommunityDetailsContranct.CommunityDetailsPresenter {
    public CommunityDetailsPresenter(CommunityDetailsContranct.CommunityDetailsView iView) {
        this.mView = iView;
        this.mModel = new CommunityDetailsModel();
    }

    @Override
    public void getCommunityDetails(String question_id) {
        HttpManager.newInstance().commonRequest(mModel.getCommunityDetails(question_id), new BaseObserver<Result<CommunityDetailsBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<CommunityDetailsBean> communityDetailsBeanResult) {
                if (communityDetailsBeanResult.getData() == null) {
                    mView.NoData();
                } else if (communityDetailsBeanResult.getData().getQuestion_info() == null){
                    mView.NoData();
                }else {
                    mView.SuccessData(communityDetailsBeanResult.getData().getQuestion_info());
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
    public void getCommentList(String question_id, String st, final int page) {
        HttpManager.newInstance().commonRequest(mModel.getCommentList(question_id, st, page), new BaseObserver<Result<DetailsCommentBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<DetailsCommentBean> detailsCommentBeanResult) {
                if (detailsCommentBeanResult.getData() == null) {
                    /*if (page == 1) {
                        mView.ErrorData();
                    } else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadMore(true);
                    }*/
                } else {
                    if (detailsCommentBeanResult.getData().getList() == null || detailsCommentBeanResult.getData().getList().size() == 0) {
                        if (page == 1) {
                            // mView.NoData();
                            mView.isLoadMore(false);
                        } else {
                            mView.showErrorMsg("已经没有数据了!");
                            mView.isLoadMore(false);
                        }
                    } else {
                        if (detailsCommentBeanResult.getData().getList().size() < 6) {
                            //已经没有下一页了
                            mView.isLoadMore(false);
                        } else {
                            //还有下一页
                            mView.isLoadMore(true);
                        }
                        mView.CommentListData(detailsCommentBeanResult.getData().getTotal(),detailsCommentBeanResult.getData().getList());
                    }
                }
            }

            @Override
            public void onFail(ApiException e) {
                if (page == 1) {
                    //   mView.ErrorData();
                    //   mView.showErrorMsg(e.getMessage());
                } else {
                    mView.showErrorMsg(e.getMessage());
                    //  mView.isLoadMore(true);
                }
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
    public void sendComment(String question_id, String content, String is_anonymity, String comment_id) {
        HttpManager.newInstance().commonRequest(mModel.sendComment(question_id, content, is_anonymity, comment_id), new BaseObserver<Result>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result result) {
                mView.sendSuccess(result.getMsg());
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
    public void setLike(String question_id) {
        HttpManager.newInstance().commonRequest(mModel.setLike(question_id), new BaseObserver<Result<DetailsLikeBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<DetailsLikeBean> result) {
                mView.setLikeSuccess(result.getData());
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
    public void deleteTopic(String topicId) {
        HttpManager.newInstance().commonRequest(mModel.deleteTopic(topicId), new BaseObserver<Result>(AppUtils.getContext()) {
            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onSuccess(Result result) {
                mView.deleteTopicSuccess(result.getMsg());
            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(e.getMessage());
            }
        },mView.binLifecycle());
    }

    @Override
    public void deleteConmment(String conmmentId, final int position) {
        HttpManager.newInstance().commonRequest(mModel.deleteConmment(conmmentId), new BaseObserver<Result>(AppUtils.getContext()) {
            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onSuccess(Result result) {
                mView.deleteConmmentSuccess(result.getMsg(),position);
            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(e.getMessage());
            }
        },mView.binLifecycle());
    }
}

