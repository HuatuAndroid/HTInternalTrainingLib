package com.zhiyun88.www.module_main.train.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.train.bean.TrainListBean;

import io.reactivex.Observable;

public interface TrainListContranct {
    interface TrainListView extends MvpView{
            void isLoadMore(boolean b);
    }
    interface TrainListModel extends BaseModel{
        Observable<Result<TrainListBean>> getTrainListData(String st,int page);
    }
    abstract class TrainListPresenter extends BasePreaenter<TrainListView,TrainListModel>{
        public abstract void getTrainListData(String st,int page);
    }
}
