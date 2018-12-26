package com.zhiyun88.www.module_main.dotesting.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.dotesting.bean.CountBean;

import io.reactivex.Observable;

public interface CuntContranct {
    interface CuntView extends MvpView{
    }
    interface CuntModel extends BaseModel{
            Observable<Result<CountBean>> getCuntData(String id);
    }
    abstract class CuntPresenter extends BasePreaenter<CuntView,CuntModel>{
            public abstract void getCuntData(String id);
    }
}
