package com.zhiyun88.www.module_main.main.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.main.bean.SearchBean;
import com.zhiyun88.www.module_main.main.mvp.contranct.SearchContranct;
import com.zhiyun88.www.module_main.main.mvp.model.SearchModel;

import io.reactivex.disposables.Disposable;


public class SearchPresenter extends SearchContranct.SearchPresenter {
    public SearchPresenter(SearchContranct.SearchView iView) {
        this.mView = iView;
        this.mModel = new SearchModel();
    }

    @Override
    public void getSearchData(String words, final int page) {
        HttpManager.newInstance().commonRequest(mModel.getSearchData(words,page), new BaseObserver<Result<SearchBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<SearchBean> searchBeanResult) {
                if(searchBeanResult.getData()==null){
                    if (page == 1) {
                        mView.ErrorData();
                    }else {
                        mView.showErrorMsg(AppUtils.getString(R.string.network_error));
                        mView.loadMore(true);
                    }
                }else {
                    if (searchBeanResult.getData().getList() == null || searchBeanResult.getData().getList().size() == 0) {
                        if (page == 1) {
                            mView.NoData();
                        }else {
                            mView.showErrorMsg("已经没有数据了");
                            mView.loadMore(false);
                        }
                    }else {
                        if (searchBeanResult.getData().getList().size()<AppConfigManager.newInstance().getAppConfig().getMaxPage()) {
                            //已经没有下一页了
                            mView.loadMore(false);
                        } else {
                            //还有下一页
                            mView.loadMore(true);
                        }
                        mView.SuccessData(searchBeanResult.getData().getList());
                    }
                }

            }

            @Override
            public void onFail(ApiException e) {
                if (page == 1) {
                    mView.ErrorData();
                } else {
                    mView.showErrorMsg(AppUtils.getString(R.string.network_error));
                    mView.loadMore(true);
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

