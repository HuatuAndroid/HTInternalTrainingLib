package com.zhiyun88.www.module_main.main.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.main.bean.HomeBean;

import io.reactivex.Observable;

public interface HomeFragmentContranct {
    interface HomeFragmentView extends MvpView{
    }
    interface HomeFragmentModel extends BaseModel{
        Observable<Result<HomeBean>> getHomeData();
    }
    abstract class HomeFragmentPresenter extends BasePreaenter<HomeFragmentView, HomeFragmentModel>{
        public abstract void getHomeData();

    }
}
