package com.zhiyun88.www.module_main.course.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.course.api.CourseServiceApi;
import com.zhiyun88.www.module_main.course.bean.CourseInfoBean;
import com.zhiyun88.www.module_main.course.mvp.contranct.CourseInfoContranct;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class CourseInfoModel implements CourseInfoContranct.CourseInfoModel {
    @Override
    public Observable<Result<CourseInfoBean>> getCourseInfoData(String basis_id) {
        return HttpManager.newInstance().getService(CourseServiceApi.class).getCourseInfo(basis_id);
    }

    @Override
    public Observable<Result> buyCourse(String basis_id) {
        return HttpManager.newInstance().getService(CourseServiceApi.class).joinCourse(basis_id);
    }

    @Override
    public Observable<Result> uploadWatchVideoTime(long video_id, String start_time, String end_time, int watch_time, int abort_time) {
        Map<String, String> map = new HashMap<>();
        map.put("video_id", video_id + "");
        map.put("start_time", start_time);
        map.put("end_time", end_time);
        map.put("watch_time", watch_time + "");
        map.put("abort_time", abort_time + "");
        return HttpManager.newInstance().getService(CourseServiceApi.class).uploadWatchVideoTime(map);
    }
}
