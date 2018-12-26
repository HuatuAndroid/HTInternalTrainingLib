package com.zhiyun88.www.module_main.information.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.information.api.InformationServiceApi;
import com.zhiyun88.www.module_main.information.bean.InformationTypeBean;
import com.zhiyun88.www.module_main.information.mvp.contranct.InformationContranct;

import io.reactivex.Observable;

public class InformationModel implements InformationContranct.InformationModel {

    @Override
    public Observable<Result<InformationTypeBean>> getInformationType() {
        return HttpManager.newInstance().getService(InformationServiceApi.class).getInformationType(1);
    }
}
