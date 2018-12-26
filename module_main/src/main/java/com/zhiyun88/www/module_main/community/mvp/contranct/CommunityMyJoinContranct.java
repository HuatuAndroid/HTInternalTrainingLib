package com.zhiyun88.www.module_main.community.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.community.bean.MyPartBean;

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
