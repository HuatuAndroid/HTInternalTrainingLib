package com.zhiyun88.www.module_main.main.api;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.main.bean.MyCourseBean;
import com.zhiyun88.www.module_main.main.bean.HomeBean;
import com.zhiyun88.www.module_main.main.bean.SearchBean;
import com.zhiyun88.www.module_main.main.bean.UserMessageCount;
import com.zhiyun88.www.module_main.main.config.MainConfig;
import com.zhiyun88.www.module_main.main.bean.MyTaskBean;
import com.zhiyun88.www.module_main.main.bean.MyTrainBean;


import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MainServiceApi {

    @GET(MainConfig.HOME_URL)
    Observable<Result<HomeBean>> getHomeData();

    @GET(MainConfig.MYCOURSE_URL)
    Observable<Result<MyCourseBean>> getMyCourseData(@Path("type") int type, @Query("page") int page);

    @FormUrlEncoded
    @POST(MainConfig.MYTASK_URL)
    Observable<Result<MyTaskBean>> getMyTaskData(@FieldMap Map<String,String > map);

    @GET(MainConfig.MYTRAIN_URL)
    Observable<Result<MyTrainBean>> getMyTrainData(@Path("type") int type, @Query("page") int page);

    @FormUrlEncoded
    @POST(MainConfig.SEARCH_URL)
    Observable<Result<SearchBean>> getSearchData(@Field("keywords") String words,@Field("page") int page);

    @FormUrlEncoded
    @POST(MainConfig.USERPOSTCOMMENT)
    Observable<Result> userPostComment(@FieldMap Map<String,String> map);

    @GET(MainConfig.GETNEWMESSAGE)
    Observable<Result<UserMessageCount>> getNewMessage();

}
