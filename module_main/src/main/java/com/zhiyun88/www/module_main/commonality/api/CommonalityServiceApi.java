package com.zhiyun88.www.module_main.commonality.api;


import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.commonality.bean.CertificateBean;
import com.zhiyun88.www.module_main.commonality.bean.IntegralBean;
import com.zhiyun88.www.module_main.commonality.bean.MessageBean;
import com.zhiyun88.www.module_main.commonality.bean.MyLibraryBean;
import com.zhiyun88.www.module_main.commonality.bean.QuestionNaireBean;
import com.zhiyun88.www.module_main.commonality.bean.RankingBean;
import com.zhiyun88.www.module_main.commonality.bean.RecordBean;
import com.zhiyun88.www.module_main.commonality.bean.UserDataBean;
import com.zhiyun88.www.module_main.commonality.config.CommonalityHttpConfig;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommonalityServiceApi {

    @FormUrlEncoded
    @POST(CommonalityHttpConfig.FEEDBACK)
    Observable<Result> getFeedback(@Field("feedback_info") String msg);

    @GET(CommonalityHttpConfig.INTEGRAL)
    Observable<Result<IntegralBean>> getIntegral(@Path("id") String id);

    @GET(CommonalityHttpConfig.INTEGRAL_RECORD)
    Observable<Result<RecordBean>> getRecord(@Path("id") String id, @Query("page") int page);

    @GET(CommonalityHttpConfig.INTEGRAL_RANKING)
    Observable<Result<RankingBean>> getRanking(@Path("id") String id);

    @GET(CommonalityHttpConfig.USERINFO)
    Observable<Result<UserDataBean>> getUserData(@Path("id") String id);

    @GET(CommonalityHttpConfig.MESSAGE)
    Observable<Result<MessageBean>> getMessageData(@Query("user_id") String user_id, @Query("message_type") int message_type, @Query("is_app") int is_app, @Query("page") int page);

    @FormUrlEncoded
    @POST(CommonalityHttpConfig.IS_READ)
    Observable<Result> setMessageState(@Field("user_id") String msg, @Field("message_id") String message_id);

    @GET(CommonalityHttpConfig.GETQUESTIONNAIRE)
    Observable<Result<QuestionNaireBean>> getQuestionData(@Path("id") int id);

    @FormUrlEncoded
    @POST(CommonalityHttpConfig.QUESTIONNAIRE)
    Observable<Result> setQuestionData(@Field("basis_id") int basis_id, @Field("chapter_id") int chapter_id, @Field("grade") int grade, @Field("nice_comment") String nice_comment, @Field("negative_comments") String negative_comments);
    @GET(CommonalityHttpConfig.MYCERTIFICATE)
    Observable<Result<CertificateBean>> getCertificateData(@Query("page") int page);

    @FormUrlEncoded
    @POST(CommonalityHttpConfig.MYLIBRARY)
    Observable<Result<MyLibraryBean>> getLibraryData(@Field("page") int page);
}
