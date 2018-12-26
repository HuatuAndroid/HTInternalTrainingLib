package com.zhiyun88.www.module_main.api;

import com.wb.baselib.bean.Result;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AppServiceApi {
    @POST("api/app/getAppToken")
    @FormUrlEncoded
    Observable<Result<AppBean>> getLoginInfo(@FieldMap Map<String,String> map);
}
