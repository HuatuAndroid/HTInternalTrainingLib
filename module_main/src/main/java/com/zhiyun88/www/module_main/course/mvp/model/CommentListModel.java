package com.zhiyun88.www.module_main.course.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.course.api.CourseServiceApi;
import com.zhiyun88.www.module_main.course.bean.CommentListBean;
import com.zhiyun88.www.module_main.course.mvp.contranct.CommentListContranct;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class CommentListModel implements CommentListContranct.CommentListModel {
    @Override
    public Observable<Result<CommentListBean>> getCommentListData(String comment_shop_id, int page) {
        Map<String,String> map=new HashMap<>();
        map.put("comment_shop_id",comment_shop_id);
        map.put("page",page+"");
        return HttpManager.newInstance().getService(CourseServiceApi.class).getCourseInfoCommentList(map);
    }
}
