package com.example.module_employees_world.contranct;

import com.example.module_employees_world.bean.MyItemBean;
import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import io.reactivex.Observable;

public interface CommunityMyGroupContranct {
    interface CommunityMyGroupView extends MvpView {
        void joinGroup();
        void isLoadMore(boolean isLoadMore);
    }
    interface CommunityMyGroupModel extends BaseModel {
        Observable<Result<MyItemBean>> getMyGroupData(int page);
        Observable<Result> setGroup(String groupId, String states);
    }
    abstract class CommunityMyGroupPresenter extends BasePreaenter<CommunityMyGroupView,CommunityMyGroupModel> {
        public abstract void getMyGroupData(int page);
        public abstract void setGroup(String groupId,String states);
    }
}
