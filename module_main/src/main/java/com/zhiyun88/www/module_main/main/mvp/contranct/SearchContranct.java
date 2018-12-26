package com.zhiyun88.www.module_main.main.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.main.bean.MyTrainBean;
import com.zhiyun88.www.module_main.main.bean.SearchBean;

import io.reactivex.Observable;

public interface SearchContranct {
    interface SearchView extends MvpView{
        void loadMore(boolean isLoadMore);
    }
    interface SearchModel extends BaseModel{
        Observable<Result<SearchBean>> getSearchData(String words,int page);
    }
    abstract class SearchPresenter extends BasePreaenter<SearchView,SearchModel>{
            public abstract void getSearchData(String words,int page);
    }
}
