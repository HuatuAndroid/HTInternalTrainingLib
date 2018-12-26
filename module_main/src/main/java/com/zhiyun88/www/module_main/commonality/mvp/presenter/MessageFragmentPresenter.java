package com.zhiyun88.www.module_main.commonality.mvp.presenter;

import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.bean.MessageBean;
import com.zhiyun88.www.module_main.commonality.bean.RecordBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.MessageFragmentContranct;
import com.zhiyun88.www.module_main.commonality.mvp.model.MessageFragmentModel;

import io.reactivex.disposables.Disposable;


public class MessageFragmentPresenter extends MessageFragmentContranct.MessageFragmentPresenter {
    public MessageFragmentPresenter(MessageFragmentContranct.MessageFragmentView iView) {
        this.mView = iView;
        this.mModel = new MessageFragmentModel();
    }

    @Override
    public void getMessageData(String user_id, int message_type, int is_app, final int page) {
        HttpManager.newInstance().commonRequest(mModel.getMessageData(user_id,message_type,is_app,page), new BaseObserver<Result<MessageBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<MessageBean> messageBeanResult) {
                if(messageBeanResult.getData()==null||messageBeanResult.getData().getList()==null){
                    if (page == 1) {
                        mView.ErrorData();
                    } else {
                        mView.showErrorMsg(AppUtils.getString(R.string.network_error));
                        mView.loadMore(true);
                    }
                }else {
                    if (messageBeanResult.getData() == null || messageBeanResult.getData().getList().size() == 0) {
                        if (page == 1) {
                            mView.NoData();
                        }else {
                            mView.showErrorMsg("已经没有数据了!");
                            mView.loadMore(true);
                        }
                    }else {
                        if (messageBeanResult.getData().getList().size()<AppConfigManager.newInstance().getAppConfig().getMaxPage()) {
                            //已经没有下一页了
                            mView.loadMore(false);
                        } else {
                            //还有下一页
                            mView.loadMore(true);
                        }
                        mView.SuccessData(messageBeanResult.getData().getList());
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

    @Override
    public void setMessageState(String user_id, String message_id) {
        HttpManager.newInstance().commonRequest(mModel.setMessageState(user_id, message_id), new BaseObserver<Result>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result result) {
                if(result.getData()==null){
                    mView.showErrorMsg(AppUtils.getString(R.string.network_error));
                }else {
                    mView.setIsRead();
                }

            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(AppUtils.getString(R.string.network_error));
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

