package com.example.module_employees_world.presenter;

import android.app.Activity;

import com.example.module_employees_world.bean.GuideBean;
import com.example.module_employees_world.bean.IsBannedBean;
import com.example.module_employees_world.bean.SearchPostBean;
import com.example.module_employees_world.common.StartActivityCommon;
import com.example.module_employees_world.contranct.GuideContranct;
import com.example.module_employees_world.model.GuideModel;
import com.example.module_employees_world.ui.home.CommunityActivity;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author liuzhe
 * @date 2019/3/21
 */
public class GuidePresenter extends GuideContranct.Presenter {

    private Activity mActivity;
    public Disposable subscribe;

    private int times = 3;   //倒计时的时间

    public GuidePresenter(GuideContranct.View iView, Activity mActivity) {

        this.mActivity = mActivity;
        this.mView = iView;
        this.mModel = new GuideModel();

    }

    /**
     * 倒计时
     */
    @Override
    public void countDown() {
        subscribe = Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        if (times - aLong == 0){
                            StartActivityCommon.startActivity(mActivity, CommunityActivity.class);
                            mActivity.finish();
                        }else{
                           mView.upData_mTvTime(times - aLong);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {

                    }
                });

    }

    /**
     * 当生命周期结束时，被调用
     */
    @Override
    public void onDestroy() {
        if (subscribe != null){
            subscribe.dispose();
            subscribe = null;
        }
    }

    @Override
    public void getData() {
        HttpManager.newInstance().commonRequest(mModel.getGuide(), new BaseObserver<Result<GuideBean>>(AppUtils.getContext()) {
            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Result<GuideBean> guideBeanResult) {
                if (guideBeanResult.getData() != null) {
                    mView.SuccessData(guideBeanResult.getData());
                }
            }

            @Override
            public void onFail(ApiException e) {

            }
        });

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
            }

            @Override
            public void onFail(ApiException e) {

            }
        });


    }
}
