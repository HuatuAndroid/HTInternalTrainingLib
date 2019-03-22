package com.example.module_employees_world;

import com.example.module_employees_world.bean.CommunityDiscussBean;
import com.example.module_employees_world.bean.CommunityGroupBean;
import com.example.module_employees_world.common.config.CommunityHttpConfig;
import com.wb.baselib.bean.Result;
import java.util.Map;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommunityServiceApi {

    @GET(CommunityHttpConfig.GROUPLIST)
    Observable<Result<CommunityGroupBean>> getGroupList(@Query("page") int page);

    @FormUrlEncoded
    @POST(CommunityHttpConfig.JOINGROUP)
    Observable<Result> setGroup(@Field("group_id") String groupId, @Field("states") String states);

    @GET(CommunityHttpConfig.DISCUSS)
    Observable<Result<CommunityDiscussBean>> getDiscussData(@Path("type") String type, @Query("page") int page);

    @GET(CommunityHttpConfig.GROUPDETAILSTYPE)
    Observable<Result<CommunityDiscussBean>> getGroupTypeData(@Path("type") String type, @Query("group_id") String group_id, @Query("page") int page);

}
