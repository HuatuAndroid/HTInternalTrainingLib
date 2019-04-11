package com.example.module_employees_world.presenter;


import com.example.module_employees_world.bean.CommunityDiscussBean;
import com.example.module_employees_world.bean.SearchCommenBean;
import com.example.module_employees_world.bean.SearchPostBean;
import com.example.module_employees_world.contranct.SearchContranct;
import com.example.module_employees_world.model.CommunityDiscussModel;
import com.example.module_employees_world.model.SearchModel;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;

import java.util.List;

import io.reactivex.disposables.Disposable;


public class SearchPresenter extends SearchContranct.SearchPresenter {
    public SearchPresenter(SearchContranct.SearchView iView) {
        this.mView = iView;
        this.mModel = new SearchModel();
    }

    @Override
    public void getSearchCommnet(String type, String keyword, int page) {
        HttpManager.newInstance().commonRequest(mModel.getSearchCommnet(type,keyword,page), new BaseObserver<Result<List<SearchCommenBean>>>(AppUtils.getContext()) {
            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Result<List<SearchCommenBean>> listResult) {
                if (listResult.getData() == null) {
                    if (page == 1) {
                        mView.ErrorData();
                    } else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadMore(true);
                    }
                } else {
                    if (listResult.getData().size() == 0) {
                        if (page == 1) {
                            mView.NoData();
                        } else {
                            mView.showErrorMsg("没有更多评论了");
                            mView.isLoadMore(false);
                        }
                    } else {
                        if (listResult.getData().size() < AppConfigManager.newInstance().getAppConfig().getMaxPage()) {
                            //已经没有下一页了
                            mView.isLoadMore(false);
                        } else {
                            //还有下一页
                            mView.isLoadMore(true);
                        }
                        mView.SuccessData(listResult.getData());
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
        });
    }

    @Override
    public void getSerachPost(String type, String keyword, int page) {
        HttpManager.newInstance().commonRequest(mModel.getSerachPost(type,keyword,page), new BaseObserver<Result<List<SearchPostBean>>>(AppUtils.getContext()) {
            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Result<List<SearchPostBean>> listResult) {
                if (listResult.getData() == null) {
                    if (page == 1) {
                        mView.ErrorData();
                    } else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadMore(true);
                    }
                } else {
                    if (listResult.getData().size() == 0) {
                        if (page == 1) {
                            mView.NoData();
                        } else {
                            mView.showErrorMsg("没有更多帖子了");
                            mView.isLoadMore(false);
                        }
                    } else {
                        if (listResult.getData().size() < AppConfigManager.newInstance().getAppConfig().getMaxPage()) {
                            //已经没有下一页了
                            mView.isLoadMore(false);
                        } else {
                            //还有下一页
                            mView.isLoadMore(true);
                        }
                        mView.SuccessData(listResult.getData());
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
        });

    }
}

