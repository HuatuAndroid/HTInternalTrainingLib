package com.zhiyun88.www.module_main.task.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.task.api.TaskServiceApi;
import com.zhiyun88.www.module_main.task.bean.TaskInfoListBean;
import com.zhiyun88.www.module_main.task.mvp.contranct.TaskInfoContranct;

import io.reactivex.Observable;

public class TaskInfoModel implements TaskInfoContranct.TaskInfoModel {
    @Override
    public Observable<Result<TaskInfoListBean>> getTaskInfoList(String id) {
        return HttpManager.newInstance().getService(TaskServiceApi.class).getTaskInfoList(id);
    }
}
