package com.zhiyun88.www.module_main.main.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.main.api.MainServiceApi;
import com.zhiyun88.www.module_main.main.bean.SearchBean;
import com.zhiyun88.www.module_main.main.mvp.contranct.SearchContranct;

import io.reactivex.Observable;

public class SearchModel implements SearchContranct.SearchModel {

    @Override
    public Observable<Result<SearchBean>> getSearchData(String words,int page) {
        return HttpManager.newInstance().getService(MainServiceApi.class).getSearchData(words,page);
    }
}
