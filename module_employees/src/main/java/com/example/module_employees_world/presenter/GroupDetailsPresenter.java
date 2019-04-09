package com.example.module_employees_world.presenter;


import com.example.module_employees_world.bean.GroupDetailsBean;
import com.example.module_employees_world.bean.IsBannedBean;
import com.example.module_employees_world.contranct.GroupDetailsContranct;
import com.example.module_employees_world.model.GroupDetailsModel;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;

import io.reactivex.disposables.Disposable;


public class GroupDetailsPresenter extends GroupDetailsContranct.GroupDetailsPresenter {
    public GroupDetailsPresenter(GroupDetailsContranct.GroupDetailsView iView) {
        this.mView = iView;
        this.mModel = new GroupDetailsModel();
    }

    /**
     * app-判断是否可以发表评论
     */
    @Override
    public void getIsBanned(){

        HttpManager.newInstance().commonRequest(mModel.getIsBanned(), new BaseObserver<Result<IsBannedBean>>(AppUtils.getContext()) {
            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Result<IsBannedBean> guideBeanResult) {

                IsBannedBean data = guideBeanResult.getData();
                if (data != null) {
                    AppUtils.is_banned = data.isBanned;
                }
                mView.getIsBanned(data);

            }

            @Override
            public void onFail(ApiException e) {

            }
        });


    }

    @Override
    public void getGroupDetails(String group_id,String st) {
        HttpManager.newInstance().commonRequest(mModel.getGroupDetails(group_id, st), new BaseObserver<Result<GroupDetailsBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<GroupDetailsBean> groupDetailsBeanResult) {
                if (groupDetailsBeanResult.getData() == null) {}else {
                    mView.SuccessData(groupDetailsBeanResult.getData().getGroup_info());
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

