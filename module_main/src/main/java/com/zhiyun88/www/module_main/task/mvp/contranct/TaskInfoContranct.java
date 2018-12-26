package com.zhiyun88.www.module_main.task.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.task.bean.TaskInfoListBean;

import io.reactivex.Observable;

public interface TaskInfoContranct {
    interface TaskInfoView extends MvpView{

    }
    interface TaskInfoModel extends BaseModel{
        Observable<Result<TaskInfoListBean>> getTaskInfoList(String  id);
    }
    abstract class TaskInfoPresenter extends BasePreaenter<TaskInfoView,TaskInfoModel>{
            public abstract void getTaskInfoList(String  id);
    }
}
