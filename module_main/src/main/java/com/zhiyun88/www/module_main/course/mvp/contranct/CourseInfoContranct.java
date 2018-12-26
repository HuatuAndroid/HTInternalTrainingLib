package com.zhiyun88.www.module_main.course.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.course.bean.CourseInfoBean;

import io.reactivex.Observable;
import io.reactivex.Observer;

public interface CourseInfoContranct {
    interface CourseInfoView extends MvpView{
        void initCoorLayout(CourseInfoBean courseInfoBean);
        void joinSuccess(String msg);
    }
    interface CourseInfoModel extends BaseModel{
        Observable<Result<CourseInfoBean>> getCourseInfoData(String basis_id);
        Observable<Result> buyCourse(String basis_id);
        Observable<Result> uploadWatchVideoTime(long video_id, String start_time, String end_time, int watch_time, int abort_time);
    }
    abstract class CourseInfoPresenter extends BasePreaenter<CourseInfoView,CourseInfoModel>{
            public abstract void getCourseInfoData(String basis_id);
            public abstract void buyCourse(String basis_id);
            public abstract void uploadWatchVideoTime(long video_id, String start_time, String end_time, int watch_time, int abort_time);
    }
}
