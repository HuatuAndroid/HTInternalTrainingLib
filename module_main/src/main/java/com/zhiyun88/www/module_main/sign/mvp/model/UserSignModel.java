package com.zhiyun88.www.module_main.sign.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.sign.api.SignServiceApi;
import com.zhiyun88.www.module_main.sign.bean.SignBean;
import com.zhiyun88.www.module_main.sign.mvp.contranct.UserSignContranct;

import io.reactivex.Observable;

public class UserSignModel implements UserSignContranct.UserSignModel {
    @Override
    public Observable<Result<SignBean>> getUserSign(String url) {
        return HttpManager.newInstance().getService(SignServiceApi.class).getUserSignData(url);
    }

    @Override
    public Observable<Result> userSign(String basis_id, String chapter_id) {
        return HttpManager.newInstance().getService(SignServiceApi.class).userSign(basis_id,chapter_id);
    }
}
