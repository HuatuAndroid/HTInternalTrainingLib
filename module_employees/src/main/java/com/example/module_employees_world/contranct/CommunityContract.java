package com.example.module_employees_world.contranct;

import com.example.module_employees_world.bean.GuideBean;
import com.example.module_employees_world.bean.IsBannedBean;
import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;
import com.wb.baselib.bean.Result;

import io.reactivex.Observable;

/**
 * @author 王晓凤
 * @date 2019/4/9
 */
public interface CommunityContract {

    interface View extends BaseView {

    }
    interface Model extends BaseModel {
        Observable<Result<IsBannedBean>> getIsBanned();

    }
    abstract class Presenter extends BasePreaenter<View, Model> {
        public abstract void getIsBanned();
    }
}
