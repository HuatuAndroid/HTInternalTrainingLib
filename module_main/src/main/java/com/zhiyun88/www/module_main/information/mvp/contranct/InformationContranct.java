package com.zhiyun88.www.module_main.information.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.information.bean.InformationTypeBean;

import io.reactivex.Observable;

public interface InformationContranct {
    interface InformationView extends BaseView{
    }
    interface InformationModel extends BaseModel{
        Observable<Result<InformationTypeBean>> getInformationType();
    }
    abstract class InformationPresenter extends BasePreaenter<InformationView,InformationModel>{
            public abstract void getInformationType();
    }
}
