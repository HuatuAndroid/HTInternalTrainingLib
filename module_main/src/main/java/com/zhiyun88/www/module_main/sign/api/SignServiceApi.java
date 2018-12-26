package com.zhiyun88.www.module_main.sign.api;

import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.sign.bean.SignBean;
import com.zhiyun88.www.module_main.sign.config.SignHttpConfig;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SignServiceApi {
    @GET(SignHttpConfig.GETUSERSIGN)
    Observable<Result<SignBean>> getUserSignData(@Path("type") String basis_id);

    @GET(SignHttpConfig.USERSIGN)
    Observable<Result> userSign(@Path("basis_id") String basis_id,@Path("chapter_id") String chapter_id);
}
