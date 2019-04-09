package com.example.module_employees_world.presenter;

import android.app.Activity;

import com.example.module_employees_world.bean.GuideBean;
import com.example.module_employees_world.bean.IsBannedBean;
import com.example.module_employees_world.common.StartActivityCommon;
import com.example.module_employees_world.contranct.CommunityContract;
import com.example.module_employees_world.contranct.GuideContranct;
import com.example.module_employees_world.model.CommunityModel;
import com.example.module_employees_world.model.GuideModel;
import com.example.module_employees_world.ui.home.CommunityActivity;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author liuzhe
 * @date 2019/3/21
 */
public class CommunityPresenter extends CommunityContract.Presenter {

    public CommunityPresenter(CommunityContract.View iView) {
        this.mView = iView;
        this.mModel = new CommunityModel();

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
                mView.SuccessData(guideBeanResult.getData());

            }

            @Override
            public void onFail(ApiException e) {

            }
        });


    }
}
