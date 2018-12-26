package com.zhiyun88.www.module_main.community.api;

import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.community.bean.CommunityDetailsBean;
import com.zhiyun88.www.module_main.community.bean.CommunityDiscussBean;
import com.zhiyun88.www.module_main.community.bean.CommunityGroupBean;
import com.zhiyun88.www.module_main.community.bean.DetailsCommentBean;
import com.zhiyun88.www.module_main.community.bean.GroupDetailsBean;
import com.zhiyun88.www.module_main.community.bean.ImageListBean;
import com.zhiyun88.www.module_main.community.bean.MyItemBean;
import com.zhiyun88.www.module_main.community.bean.MyPartBean;
import com.zhiyun88.www.module_main.community.config.CommunityHttpConfig;

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

    @GET(CommunityHttpConfig.MYGROUP)
    Observable<Result<MyItemBean>> getMyGroupData(@Query("page") int page);

    @GET(CommunityHttpConfig.MYPART)
    Observable<Result<MyPartBean>> getMyPartData(@Query("page") int page);

    @FormUrlEncoded
    @POST(CommunityHttpConfig.JOINGROUP)
    Observable<Result> setGroup(@Field("group_id") String groupId, @Field("states") String states);

    @GET(CommunityHttpConfig.DISCUSS)
    Observable<Result<CommunityDiscussBean>> getDiscussData(@Path("type") String type, @Query("page") int page);

    @GET(CommunityHttpConfig.GROUPDETAILS)
    Observable<Result<GroupDetailsBean>> getGroupDetails(@Path("group_id") String group_id, @Query("st") String st);

    @GET(CommunityHttpConfig.GROUPDETAILSTYPE)
    Observable<Result<CommunityDiscussBean>> getGroupTypeData(@Path("type") String type, @Query("group_id") String group_id, @Query("page") int page);

    @GET(CommunityHttpConfig.DETAILS_LIKE)
    Observable<Result> setDetailsLike(@Path("question_id") String question_id);
 /*
    @FormUrlEncoded
    @POST(CommunityHttpConfig.DETAILS_COLLECT)
    Observable<Result> setDetailsCollect(@Field("question_id") String question_id,@Field("states") String states);*/

    @GET(CommunityHttpConfig.COMMUNITY_DETAILS)
    Observable<Result<CommunityDetailsBean>> getCommunityDetails(@Path("question_id") String question_id, @Query("st") String st);

    @FormUrlEncoded
    @POST(CommunityHttpConfig.RELEASETOPIC)
    Observable<Result> commitTopicData(@FieldMap() Map<String, String> map);

    @Multipart
    @POST(CommunityHttpConfig.PUBLICIMAGE)
    Observable<Result<ImageListBean>> commitImage(@PartMap Map<String, RequestBody> map);

    @GET(CommunityHttpConfig.COMMENT)
    Observable<Result<DetailsCommentBean>> getCommentList(@Path("question_id") String question_id, @Query("st") String st, @Query("page") int page);

    @FormUrlEncoded
    @POST(CommunityHttpConfig.SENDCOMMENT)
    Observable<Result> sendComment(@FieldMap Map<String, String> map);
}
