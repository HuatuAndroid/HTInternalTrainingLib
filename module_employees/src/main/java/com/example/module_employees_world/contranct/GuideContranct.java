package com.example.module_employees_world.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;

/**
 * @author liuzhe
 * @date 2019/3/21
 */
public interface GuideContranct {

    interface View extends BaseView {
        void upData_mTvTime(long aLong);
    }
    interface Model extends BaseModel {
    }
    abstract class Presenter extends BasePreaenter<View, Model> {

        public abstract void countDown();
        public abstract void onDestroy();

    }
}
