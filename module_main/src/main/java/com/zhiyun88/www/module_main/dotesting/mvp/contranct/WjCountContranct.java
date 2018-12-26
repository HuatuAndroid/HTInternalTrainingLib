package com.zhiyun88.www.module_main.dotesting.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.dotesting.bean.WjBean;

import io.reactivex.Observable;

public interface WjCountContranct {
    interface  WjCountView extends MvpView{

    }
    interface WjCountModel extends BaseModel{
            Observable<Result<WjBean>> getWjCountData(String id);
    }
   abstract class WjCountPresenter extends BasePreaenter<WjCountView,WjCountModel>{
       public abstract void getWjCountData(String id);
   }
}
