package com.zhiyun88.www.module_main.library.api;

import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.library.bean.LibraryDataBean;
import com.zhiyun88.www.module_main.library.bean.LibraryDetailsBean;
import com.zhiyun88.www.module_main.library.bean.LibraryTypeBean;
import com.zhiyun88.www.module_main.library.config.LibraryHttpConfig;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LibraryServiceApi {

    @GET(LibraryHttpConfig.LIBRARYTYPE)
    Observable<Result<LibraryTypeBean>> getLibraryType(@Query("is_app") int is_app);

    @FormUrlEncoded
    @POST(LibraryHttpConfig.LIBRARYDATA)
    Observable<Result<LibraryDataBean>> getLibraryData(@Field("classify_id") String id, @Field("page") int page);

    @FormUrlEncoded
    @POST(LibraryHttpConfig.LIBRARYCOLLECTION)
    Observable<Result> setLibraryCollection(@Field("library_id") String libraryId, @Field("user_id") String userId, @Field("is_click") String isClick);

    @GET(LibraryHttpConfig.LIBRARY_DETAILS)
    Observable<Result<LibraryDetailsBean>> getLibraryDetails(@Path("library_id") String library_id);
}
