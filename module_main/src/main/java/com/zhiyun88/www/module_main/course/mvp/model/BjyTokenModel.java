package com.zhiyun88.www.module_main.course.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.course.api.CourseServiceApi;
import com.zhiyun88.www.module_main.course.bean.BjyTokenBean;
import com.zhiyun88.www.module_main.course.mvp.contranct.BjyTokenContranct;

import io.reactivex.Observable;

public class BjyTokenModel implements BjyTokenContranct.BjyTokenModel {
    @Override
    public Observable<Result<BjyTokenBean>> getBjyToken(String id) {
        return HttpManager.newInstance().getService(CourseServiceApi.class).getBjyToken(id);
    }
}
