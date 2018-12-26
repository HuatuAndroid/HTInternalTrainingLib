package com.zhiyun88.www.module_main.information.api;

import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.information.bean.InformationDataBean;
import com.zhiyun88.www.module_main.information.bean.InformationTypeBean;
import com.zhiyun88.www.module_main.information.config.InformationHttpConfig;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InformationServiceApi {

    @GET(InformationHttpConfig.INFORMATIONTYPE)
    Observable<Result<InformationTypeBean>> getInformationType(@Query("is_app") int is_app);

    @FormUrlEncoded
    @POST(InformationHttpConfig.INFORMATION)
    Observable<Result<InformationDataBean>> getInformationData(@Field("classify_id") String id, @Field("page") int page);

}
