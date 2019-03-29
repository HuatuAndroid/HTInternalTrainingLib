package com.example.module_employees_world.contranct;

import com.example.module_employees_world.bean.CommunityDiscussBean;
import com.example.module_employees_world.bean.SearchCommenBean;
import com.example.module_employees_world.bean.SearchPostBean;
import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;

import java.util.List;

import io.reactivex.Observable;


public interface SearchContranct {
    interface SearchView extends MvpView{
        void isLoadMore(boolean b);
    }
    interface SearchModel extends BaseModel{
        Observable<Result<List<SearchCommenBean>>> getSearchCommnet(String type, String keyword, int page);
        Observable<Result<List<SearchPostBean>>> getSerachPost(String type, String keyword, int page);

    }
    abstract class SearchPresenter extends BasePreaenter<SearchView,SearchModel>{
        public abstract void getSearchCommnet(String type, String keyword, int page);
        public abstract void getSerachPost(String type, String keyword, int page);
    }
}
