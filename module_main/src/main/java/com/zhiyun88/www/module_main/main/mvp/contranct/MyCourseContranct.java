package com.zhiyun88.www.module_main.main.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.main.bean.MyCourseBean;

import io.reactivex.Observable;

public interface MyCourseContranct {
    interface MyCourseView extends MvpView{
        void loadMore(boolean isLoadMore);
        void successComment(String msg,boolean isPostint ,int postion);
    }
    interface MyCourseModel extends BaseModel{
        Observable<Result<MyCourseBean>> getMyCourseData(int type, int page);
        Observable<Result> postUserComment(String courseId,String context,String grade);
    }
    abstract class MyCoursePresenter extends BasePreaenter<MyCourseView,MyCourseModel>{
            public abstract void getMyCourseData(int type,int page);
            public abstract void postUserComment(String courseId,String context,String grade,int postion);
    }
}
