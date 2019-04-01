package com.example.module_employees_world;

import com.example.module_employees_world.bean.CommentChildrenBean;
import com.example.module_employees_world.bean.CommentDetailBean;
import com.example.module_employees_world.bean.CommentLikeBean;
import com.example.module_employees_world.bean.CommentListBean;
import com.example.module_employees_world.bean.CommunityDiscussBean;
import com.example.module_employees_world.bean.CommunityGroupBean;
import com.example.module_employees_world.bean.PostDetailBean;
import com.example.module_employees_world.bean.NImageListsBean;
import com.example.module_employees_world.bean.SearchCommenBean;
import com.example.module_employees_world.bean.SearchPostBean;
import com.example.module_employees_world.common.config.CommunityHttpConfig;
import com.wb.baselib.bean.Result;

import java.util.List;
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
    Observable<Result> editTopic(@Field("id") String id, @Field("title") String title, @Field("content") String content, @Field("is_anonymity") String is_anonymity);

    /**
     * 发布帖子
     * @return
     */
    @FormUrlEncoded
    @POST(CommunityHttpConfig.RELEASETOPIC)
    Observable<Result> commitTopicData(@Field("group_id") String id, @Field("title") String title, @Field("content") String content,
                                       @Field("is_anonymity") String is_anonymity, @Field("type") String type);

    /**
     * 評論列表
     */
    @GET(CommunityHttpConfig.COMMENT_LIST)
    Observable<Result<CommentListBean>> getCommentList(@Path("question_id") String question_id, @Query("st") String st,@Query("page") String page,@Query("limit") String limit);

    /**
<<<<<<< HEAD
     * 搜索帖子
     */
    @GET(CommunityHttpConfig.SEARCH)
    Observable<Result<List<SearchPostBean>>> getSearchPost(@Path("type") String type, @Query("keyword") String keyword, @Query("page") int page);

    /**
     * 搜索评论
     */
    @GET(CommunityHttpConfig.SEARCH)
    Observable<Result<List<SearchCommenBean>>> getSearchComment(@Path("type") String type, @Query("keyword") String keyword, @Query("page") int page);

    /*
     * 帖子詳情
     */
    @GET(CommunityHttpConfig.POST_DETAILS)
    Observable<Result<PostDetailBean>> getPostDetail(@Path("question_id") String question_id, @Query("st") String st);

    /**
     * 评论点赞（适用于评论，子评论）
     */
    @GET(CommunityHttpConfig.COMMENT_LIKE)
    Observable<Result<CommentLikeBean>> commentLike(@Path("comment_id") String comment_id);

    /**
     * 帖子点赞
     */
    @GET(CommunityHttpConfig.DETAILS_LIKE)
    Observable<Result<CommentLikeBean>> postsLike(@Path("question_id") String question_id);

    /**
     * 删除帖子
     */
    @FormUrlEncoded
    @POST(CommunityHttpConfig.DELETE_POST)
    Observable<Result> deletePost(@Field("id") String id);

    /**
     * 删除评论及子评论
     */
    @FormUrlEncoded
    @POST(CommunityHttpConfig.DELETE_COMMENT)
    Observable<Result> deleteComment(@Field("id") String id);

    /**
     * 评论详情
     */
    @GET(CommunityHttpConfig.COMMENT_INFO)
    Observable<Result<CommentDetailBean>> comment_detail(@Path("comment_id") int comment_id);

    /**
     * 评论子列表
     */
    @GET(CommunityHttpConfig.COMMENT_CHILDREN_LIST)
    Observable<Result<List<CommentChildrenBean>>> commentChildrenList(@Path("comment_id") int comment_id,@Query("page") int page,@Query("limit") int limit,@Query("st") int st);


}
