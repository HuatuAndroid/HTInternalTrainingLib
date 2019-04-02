package com.example.module_employees_world.contranct;

import com.example.module_employees_world.bean.GuideBean;
import com.example.module_employees_world.bean.SearchCommenBean;
import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;
import com.wb.baselib.bean.Result;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author liuzhe
 * @date 2019/3/21
 */
public interface GuideContranct {

    interface View extends BaseView {
        void upData_mTvTime(long aLong);
    }
    interface Model extends BaseModel {
        Observable<Result<GuideBean>> getGuide();

    }
    abstract class Presenter extends BasePreaenter<View, Model> {
        public abstract void countDown();
        public abstract void onDestroy();
        public abstract void getData();
    }
}
