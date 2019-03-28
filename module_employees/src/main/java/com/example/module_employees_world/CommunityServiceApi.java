package com.example.module_employees_world;

import com.example.module_employees_world.bean.CommentListBean;
import com.example.module_employees_world.bean.CommunityDiscussBean;
import com.example.module_employees_world.bean.CommunityGroupBean;
import com.example.module_employees_world.bean.PostDetailBean;
import com.example.module_employees_world.bean.NImageListsBean;
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

    /**
     * 获取更多小组接口
     *
     * @param page
     * @return
     */
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
     * 上传图片
     * @param map
     * @return
     */
    @Multipart
    @POST(CommunityHttpConfig.PUBLICIMAGE)
    Observable<Result<NImageListsBean>> commitImage(@PartMap Map<String, RequestBody> map);

    @FormUrlEncoded
    @POST(CommunityHttpConfig.EDIT_TOPIC)
    Observable<Result> editTopic(@Field("id") String id,@Field("title") String title,@Field("content") String content,@Field("is_anonymity") String is_anonymity);

    /**
     * 发布帖子
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(CommunityHttpConfig.RELEASETOPIC)
    Observable<Result> commitTopicData(@FieldMap() Map<String, String> map);

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
