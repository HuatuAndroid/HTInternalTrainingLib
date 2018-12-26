package com.zhiyun88.www.module_main.commonality.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.commonality.bean.RankingBean;
import com.zhiyun88.www.module_main.commonality.bean.RankingListBean;
import com.zhiyun88.www.module_main.commonality.bean.RecordBean;
import com.zhiyun88.www.module_main.commonality.bean.RecordListBean;

import java.util.List;

import io.reactivex.Observable;

public interface IntegralFragmentContranct {
    interface IntegralFragmentView extends MvpView {
        void loadMore(boolean isLoadMore);
        void SuccessRecordData(List<RecordListBean> recordListBeans);
        void SuccessRankingData(List<RankingListBean> rankingListBeans);

        void isLoadMore(boolean b);
    }
    interface IntegralFragmentModel extends BaseModel {
        Observable<Result<RecordBean>> getRecord(String id, int page);
        Observable<Result<RankingBean>> getRanking(String id);
    }
    abstract class IntegralFragmentPresenter extends BasePreaenter<IntegralFragmentView,IntegralFragmentModel> {
        public abstract void getRecord(String id,int page);
        public abstract void getRanking(String id);
    }
}
