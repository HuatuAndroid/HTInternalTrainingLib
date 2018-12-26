package com.zhiyun88.www.module_main.main.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.main.bean.MyTrainBean;

import io.reactivex.Observable;

public interface MyTrainContranct {
    interface MyTrainView extends MvpView{
        void loadMore(boolean isLoadMore);
    }
    interface MyTrainModel extends BaseModel{
        Observable<Result<MyTrainBean>> getMyTrainData(int type, int page);
    }
    abstract class MyTrainPresenter extends BasePreaenter<MyTrainView,MyTrainModel>{
            public abstract void getMyTrainData(int type,int page);
    }
}
