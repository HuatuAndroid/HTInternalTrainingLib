package com.zhiyun88.www.module_main.course.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.course.bean.CourseMainBean;

import io.reactivex.Observable;

public interface CourseMainContranct {
    interface CourseMainView extends MvpView{
        void isLoadData(boolean is);
    }
    interface CourseMainModel extends BaseModel{
        Observable<Result<CourseMainBean>> getItemData(String title, String classify_id1, String classify_id2, String is_hot, String is_new, String is_free, String is_price, int page);
    }
    abstract class CourseMainPresenter extends BasePreaenter<CourseMainView,CourseMainModel>{
        public abstract void  getItemData(String title,String classify_id1,String classify_id2,String is_hot,String is_new,String is_free,String is_price, int page);
    }
}
