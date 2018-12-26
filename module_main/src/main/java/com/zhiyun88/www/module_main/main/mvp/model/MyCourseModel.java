package com.zhiyun88.www.module_main.main.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.main.api.MainServiceApi;
import com.zhiyun88.www.module_main.main.mvp.contranct.MyCourseContranct;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class MyCourseModel implements MyCourseContranct.MyCourseModel {

    @Override
    public Observable getMyCourseData(int type,int page) {
        return HttpManager.newInstance().getService(MainServiceApi.class).getMyCourseData(type,page);
    }

    @Override
    public Observable<Result> postUserComment(String courseId, String context,String grade) {
        Map<String,String> map=new HashMap<>();
        map.put("content",context);
        map.put("comment_shop_id",courseId);
        map.put("grade",grade);
        map.put("type","1");
        return HttpManager.newInstance().getService(MainServiceApi.class).userPostComment(map);
    }
}
