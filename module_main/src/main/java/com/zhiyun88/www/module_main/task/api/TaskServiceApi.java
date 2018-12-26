package com.zhiyun88.www.module_main.task.api;


import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.task.bean.TaskInfoListBean;
import com.zhiyun88.www.module_main.task.config.TaskConfig;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TaskServiceApi {
    @GET(TaskConfig.TASKINFO_URL)
    Observable<Result<TaskInfoListBean>> getTaskInfoList(@Path("id") String id);
}
