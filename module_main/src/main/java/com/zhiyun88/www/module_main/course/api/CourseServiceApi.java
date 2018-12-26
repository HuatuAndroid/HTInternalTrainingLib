package com.zhiyun88.www.module_main.course.api;

import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.course.bean.BjyTokenBean;
import com.zhiyun88.www.module_main.course.bean.CommentListBean;
import com.zhiyun88.www.module_main.course.bean.CourseInfoBean;
import com.zhiyun88.www.module_main.course.bean.CourseMainBean;
import com.zhiyun88.www.module_main.course.bean.CourseMainClassflyBean;
import com.zhiyun88.www.module_main.course.config.CourseHttpConfig;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CourseServiceApi {
    @GET(CourseHttpConfig.COURSEINFO_URL)
    Observable<Result<CourseInfoBean>> getCourseInfo(@Path("basis_id") String basis_id);
    @POST(CourseHttpConfig.COURSEINFO_COMMENTLIST)
    @FormUrlEncoded
    Observable<Result<CommentListBean>> getCourseInfoCommentList(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST(CourseHttpConfig.COURSEMAIN_URL)
    Observable<Result<CourseMainBean>> getCourseMainListData(@FieldMap Map<String,String> map);

    @GET(CourseHttpConfig.COURSEMAINCLASSFLY_URL)
    Observable<Result<CourseMainClassflyBean>> getClassFlyData(@Path("type") String basis_id);

    @GET(CourseHttpConfig.JOINCOURSE_URL)
    Observable<Result> joinCourse(@Path("basis_id") String basis_id);

    @GET(CourseHttpConfig.GETBJYTOKEN_URL)
    Observable<Result<BjyTokenBean>> getBjyToken(@Path("video_id") String video_id);

    @POST(CourseHttpConfig.UPLOAD_WATCH_TIME_URL)
    @FormUrlEncoded
    Observable<Result> uploadWatchVideoTime(@FieldMap Map<String, String> map);
}
