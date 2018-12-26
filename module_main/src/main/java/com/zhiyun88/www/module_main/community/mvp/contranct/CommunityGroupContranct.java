package com.zhiyun88.www.module_main.community.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.community.bean.CommunityGroupBean;

import io.reactivex.Observable;

public interface CommunityGroupContranct {
    interface CommunityGroupView extends MvpView {
        void joinGroup();

        void isLoadMore(boolean isLoadMore);
    }
    interface CommunityGroupModel extends BaseModel {
        Observable<Result<CommunityGroupBean>> getGroupList(int page);
        Observable<Result> setGroup(String groupId, String states);
    }
    abstract class CommunityGroupPresenter extends BasePreaenter<CommunityGroupView,CommunityGroupModel> {
        public abstract void getGroupList(int page);
        public abstract void setGroup(String groupId,String states);
    }
}
