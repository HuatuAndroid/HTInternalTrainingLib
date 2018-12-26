package com.zhiyun88.www.module_main.course.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.course.api.CourseServiceApi;
import com.zhiyun88.www.module_main.course.bean.CourseMainBean;
import com.zhiyun88.www.module_main.course.mvp.contranct.CourseMainContranct;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class CourseMainModel implements CourseMainContranct.CourseMainModel {
    @Override
    public Observable<Result<CourseMainBean>> getItemData(String title, String classify_id1, String classify_id2, String is_hot, String is_new, String is_free, String is_price, int page) {
        Map<String,String> map=new HashMap<>();
        map.put("classify_id1",classify_id1);
        map.put("classify_id2",classify_id2);
        map.put("type",is_free);
        map.put("is_hot",is_hot);
        map.put("is_new",is_new);
        map.put("keywords",title);
        map.put("page",page+"");
        return HttpManager.newInstance().getService(CourseServiceApi.class).getCourseMainListData(map);
    }
}
