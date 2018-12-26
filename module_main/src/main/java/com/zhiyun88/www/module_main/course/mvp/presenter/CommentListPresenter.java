package com.zhiyun88.www.module_main.course.mvp.presenter;

import android.util.Log;

import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfig;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpConfig;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.wb.baselib.log.LogTools;
import com.zhiyun88.www.module_main.course.bean.CommentListBean;
import com.zhiyun88.www.module_main.course.mvp.contranct.CommentListContranct;
import com.zhiyun88.www.module_main.course.mvp.model.CommentListModel;

import io.reactivex.disposables.Disposable;

public class CommentListPresenter extends CommentListContranct.CommentListPresenter {
    public CommentListPresenter(CommentListContranct.CommentListView iView) {
        this.mView=iView;
        this.mModel=new CommentListModel();
    }

    @Override
    public void getCommentListData(String comment_shop_id, final int page) {
        HttpManager.newInstance().commonRequest(mModel.getCommentListData(comment_shop_id, page), new BaseObserver<Result<CommentListBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<CommentListBean> o) {
                CommentListBean commentListBean=o.getData();
                if(commentListBean==null){
                    if(page==1){
                        mView.ErrorData();
                    }else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadMore(true);
                    }
                }else {
                    if(commentListBean.getList()==null||commentListBean.getList().size()==0){
                        if(page==1){
                            mView.NoData();
                        }else {
                            mView.showErrorMsg("已经没有数据了！");
                            mView.isLoadMore(false);
                        }
                    }else {
                        if(commentListBean.getList().size()< AppConfigManager.newInstance().getAppConfig().getMaxPage()){
                            //没有下一页了
                            mView.isLoadMore(false);
                        }else {
                            mView.isLoadMore(true);
                        }
                        mView.SuccessData(commentListBean);
                    }

                }
            }

            @Override
            public void onFail(ApiException e) {
                if(page==1){
                    mView.ErrorData();
                }else {
                    mView.showErrorMsg("服务器繁忙，请稍后尝试！");
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
        },mView.binLifecycle());
    }
}
