package com.zhiyun88.www.module_main.community.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.community.bean.CommunityGroupBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.CommunityGroupContranct;
import com.zhiyun88.www.module_main.community.mvp.model.CommunityGroupModel;

import io.reactivex.disposables.Disposable;


public class CommunityGroupPresenter extends CommunityGroupContranct.CommunityGroupPresenter {
    public CommunityGroupPresenter(CommunityGroupContranct.CommunityGroupView iView) {
        this.mView = iView;
        this.mModel = new CommunityGroupModel();
    }

    @Override
    public void getGroupList(final int page) {
        HttpManager.newInstance().commonRequest(mModel.getGroupList(page), new BaseObserver<Result<CommunityGroupBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<CommunityGroupBean> communityGroupBeanResult) {
                if (communityGroupBeanResult.getData().getGroup_list() == null) {
                    if (page == 1) {
                        mView.ErrorData();
                    } else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadMore(true);
                    }
                } else {
                    if (communityGroupBeanResult.getData().getGroup_list().getList() == null || communityGroupBeanResult.getData().getGroup_list().getList().size() == 0) {
                        if (page == 1) {
                            mView.NoData();
                        } else {
                            mView.showErrorMsg("已经没有数据了!");
                            mView.isLoadMore(false);
                        }
                    } else {
                        if (communityGroupBeanResult.getData().getGroup_list().getList().size() < 6) {
                            //已经没有下一页了
                            mView.isLoadMore(false);
                        } else {
                            //还有下一页
                            mView.isLoadMore(true);
                        }
                        mView.SuccessData(communityGroupBeanResult.getData().getGroup_list().getList());
                    }
                }
            }

            @Override
            public void onFail(ApiException e) {
                if (page == 1) {
                    mView.ErrorData();
                } else {
                    mView.showErrorMsg(e.getMessage());
                    mView.isLoadMore(true);
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
    public void setGroup(String groupId, String states) {
        HttpManager.newInstance().commonRequest(mModel.setGroup(groupId, states), new BaseObserver<Result>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result result) {
                mView.joinGroup();
                mView.showErrorMsg(result.getMsg());
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

