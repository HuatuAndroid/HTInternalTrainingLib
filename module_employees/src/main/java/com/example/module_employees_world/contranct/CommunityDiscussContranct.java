package com.example.module_employees_world.contranct;

import com.example.module_employees_world.bean.CommunityDiscussBean;
import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import io.reactivex.Observable;


public interface CommunityDiscussContranct {
    interface CommunityDiscussView extends MvpView{
        void isLoadMore(boolean b);
    }
    interface CommunityDiscussModel extends BaseModel{
        Observable<Result<CommunityDiscussBean>> getDiscussData(String type, int page);
        Observable<Result<CommunityDiscussBean>> getGroupTypeData(String type, String group_id, int page);

    }
    abstract class CommunityDiscussPresenter extends BasePreaenter<CommunityDiscussView,CommunityDiscussModel>{
        public abstract void getDiscussData(String type, int page);
        public abstract void getGroupTypeData(String type,String group_id, int page);
    }
}
