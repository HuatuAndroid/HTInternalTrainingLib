package com.zhiyun88.www.module_main.main.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.main.bean.MyTrainBean;

import io.reactivex.Observable;

public interface MyTaskContranct {
    interface MyTaskView extends MvpView{
        void loadMore(boolean isLoadMore);
    }
    interface MyTaskModel extends BaseModel{
        Observable<Result<MyTrainBean>> getMyTaskData(String complete_type,String type,int page);
    }
    abstract class MyTaskPresenter extends BasePreaenter<MyTaskView,MyTaskModel>{
            public abstract void getMyTaskData(String complete_type,String type,int page);
    }
}
