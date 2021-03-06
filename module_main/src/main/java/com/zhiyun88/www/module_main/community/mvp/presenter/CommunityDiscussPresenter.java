package com.zhiyun88.www.module_main.community.mvp.presenter;


import android.util.Log;

import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.community.bean.CommunityDiscussBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.CommunityDiscussContranct;
import com.zhiyun88.www.module_main.community.mvp.model.CommunityDiscussModel;

import io.reactivex.disposables.Disposable;


public class CommunityDiscussPresenter extends CommunityDiscussContranct.CommunityDiscussPresenter {
    public CommunityDiscussPresenter(CommunityDiscussContranct.CommunityDiscussView iView) {
        this.mView = iView;
        this.mModel = new CommunityDiscussModel();
    }

    @Override
    public void getDiscussData(String type, final int page) {
        HttpManager.newInstance().commonRequest(mModel.getDiscussData(type, page), new BaseObserver<Result<CommunityDiscussBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<CommunityDiscussBean> communityDiscussBeanResult) {
                if (communityDiscussBeanResult.getData() == null) {
                    if (page == 1) {
                        mView.ErrorData();
                    } else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadMore(true);
                    }
                } else {
                    if (communityDiscussBeanResult.getData().getList() == null || communityDiscussBeanResult.getData().getList().size() == 0) {
                        if (page == 1) {
                            mView.NoData();
                        } else {
                            mView.showErrorMsg("已经没有数据了!");
                            mView.isLoadMore(false);
                        }
                    } else {
                        if (communityDiscussBeanResult.getData().getList().size() < 6) {
                            //已经没有下一页了
                            mView.isLoadMore(false);
                        } else {
                            //还有下一页
                            mView.isLoadMore(true);
                        }
                        mView.SuccessData(communityDiscussBeanResult.getData().getList());
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
    public void getGroupTypeData(String type, String group_id, final int page) {
        HttpManager.newInstance().commonRequest(mModel.getGroupTypeData(type, group_id, page), new BaseObserver<Result<CommunityDiscussBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<CommunityDiscussBean> communityDiscussBeanResult) {
                if (communityDiscussBeanResult.getData() == null) {
                    if (page == 1) {
                        mView.ErrorData();
                    } else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadMore(true);
                    }
                } else {
                    if (communityDiscussBeanResult.getData().getList() == null || communityDiscussBeanResult.getData().getList().size() == 0) {
                        if (page == 1) {
                            mView.NoData();
                        } else {
                            mView.showErrorMsg("已经没有数据了!");
                            mView.isLoadMore(false);
                        }
                    } else {
                        if (communityDiscussBeanResult.getData().getList().size() < AppConfigManager.newInstance().getAppConfig().getMaxPage()) {
                            //已经没有下一页了
                            mView.isLoadMore(false);
                        } else {
                            //还有下一页
                            mView.isLoadMore(true);
                        }
                        mView.SuccessData(communityDiscussBeanResult.getData().getList());
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
}

