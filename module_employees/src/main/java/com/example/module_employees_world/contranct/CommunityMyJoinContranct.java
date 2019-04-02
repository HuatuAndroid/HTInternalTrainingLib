package com.example.module_employees_world.contranct;

import com.example.module_employees_world.bean.MyPartBean;
import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;

import io.reactivex.Observable;

public interface CommunityMyJoinContranct {
    interface CommunityMyJoinView extends MvpView {
        void isLoadMore(boolean isLoadMore);
    }
    interface CommunityMyJoinModel extends BaseModel {
        Observable<Result<MyPartBean>> getMyPartData(int page);
    }
    abstract class CommunityMyJoinPresenter extends BasePreaenter<CommunityMyJoinView,CommunityMyJoinModel> {
        public abstract void getMyPartData(int page);
    }
}
