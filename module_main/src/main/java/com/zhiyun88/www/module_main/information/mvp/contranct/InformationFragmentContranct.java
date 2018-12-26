package com.zhiyun88.www.module_main.information.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.information.bean.InformationDataBean;

import io.reactivex.Observable;

public interface InformationFragmentContranct {
    interface InformationFragmentView extends MvpView{
        void isLoadMore(boolean isLoadMore);
    }
    interface InformationFragmentModel extends BaseModel{
        Observable<Result<InformationDataBean>> getInformationData(String id, int page);
    }
    abstract class InformationFragmentPresenter extends BasePreaenter<InformationFragmentView,InformationFragmentModel>{
            public abstract void getInformationData(String id,int page);
    }
}
