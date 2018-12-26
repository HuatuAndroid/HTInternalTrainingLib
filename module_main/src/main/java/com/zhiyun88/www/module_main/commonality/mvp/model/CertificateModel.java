package com.zhiyun88.www.module_main.commonality.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.commonality.api.CommonalityServiceApi;
import com.zhiyun88.www.module_main.commonality.bean.CertificateBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.CertificateContranct;

import io.reactivex.Observable;

public class CertificateModel implements CertificateContranct.CertificateModel {

    @Override
    public Observable<Result<CertificateBean>> getCertificateData(int page) {
        return HttpManager.newInstance().getService(CommonalityServiceApi.class).getCertificateData(page);
    }

}
