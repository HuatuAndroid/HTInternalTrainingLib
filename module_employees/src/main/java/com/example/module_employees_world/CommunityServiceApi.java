package com.example.module_employees_world;

import com.example.module_employees_world.bean.CommentListBean;
import com.example.module_employees_world.bean.CommunityDiscussBean;
import com.example.module_employees_world.bean.CommunityGroupBean;
import com.example.module_employees_world.bean.PostDetailBean;
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

    /**
     * 評論列表
     */
    @GET(CommunityHttpConfig.COMMENT_LIST)
    Observable<Result<CommentListBean>> getCommentList(@Path("question_id") String question_id, @Query("st") String st,@Query("page") String page,@Query("limit") String limit);

    /**
     * 帖子詳情
     */
    @GET(CommunityHttpConfig.POST_DETAILS)
    Observable<Result<PostDetailBean>> getPostDetail(@Path("question_id") String question_id, @Query("st") String st);


}
