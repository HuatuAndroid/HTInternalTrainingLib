package com.zhiyun88.www.module_main.course.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.course.bean.BjyTokenBean;
import com.zhiyun88.www.module_main.course.bean.CourseChildBean;

import io.reactivex.Observable;

public interface BjyTokenContranct {
    interface BjyTokenView extends BaseView{
//        void SuccessBjyToken(BjyTokenBean bjyTokenBean);
        void SuccessBjyToken( BjyTokenBean bjyTokenData, boolean isDown,  CourseChildBean courseInfoChildData);
    }
    interface BjyTokenModel extends BaseModel{
        Observable<Result<BjyTokenBean>> getBjyToken(String id);
    }
    abstract class BjyTokenPresenter extends BasePreaenter<BjyTokenView,BjyTokenModel>{
        public abstract void getBjyToken(String id, final boolean isDown, final CourseChildBean courseChildBean);
    }
}
