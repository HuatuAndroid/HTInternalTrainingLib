package com.example.module_employees_world.model;


import com.example.module_employees_world.CommunityServiceApi;
import com.example.module_employees_world.bean.CommunityDiscussBean;
import com.example.module_employees_world.bean.SearchCommenBean;
import com.example.module_employees_world.bean.SearchPostBean;
import com.example.module_employees_world.contranct.CommunityDiscussContranct;
import com.example.module_employees_world.contranct.SearchContranct;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;

import java.util.List;

import io.reactivex.Observable;

public class SearchModel implements SearchContranct.SearchModel {

    @Override
    public Observable<Result<List<SearchCommenBean>>> getSearchCommnet(String type, String keyword, int page) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getSearchComment(type,keyword,page);
    }

    @Override
    public Observable<Result<List<SearchPostBean>>> getSerachPost(String type, String keyword, int page) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getSearchPost(type,keyword,page);
    }
}
