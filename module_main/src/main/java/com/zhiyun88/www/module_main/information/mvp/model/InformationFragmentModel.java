package com.zhiyun88.www.module_main.information.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.information.api.InformationServiceApi;
import com.zhiyun88.www.module_main.information.bean.InformationDataBean;
import com.zhiyun88.www.module_main.information.mvp.contranct.InformationFragmentContranct;

import io.reactivex.Observable;

public class InformationFragmentModel implements InformationFragmentContranct.InformationFragmentModel {

    @Override
    public Observable<Result<InformationDataBean>> getInformationData(String id, int page) {
        return HttpManager.newInstance().getService(InformationServiceApi.class).getInformationData(id,page);
    }
}
