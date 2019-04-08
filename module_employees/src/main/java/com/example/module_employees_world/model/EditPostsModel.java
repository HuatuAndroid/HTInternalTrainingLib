package com.example.module_employees_world.model;

import com.example.module_employees_world.CommunityServiceApi;
import com.example.module_employees_world.bean.CommentInsertBean;
import com.example.module_employees_world.bean.NImageListsBean;
import com.example.module_employees_world.contranct.CommentSendDialogContranct;
import com.example.module_employees_world.contranct.EditPoatsContranct;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * author:LIENLIN
 * date:2019/4/6
 */
public class EditPostsModel implements EditPoatsContranct.IEditPoatsModel {
    @Override
    public Observable<Result<NImageListsBean>> commitImage(Map<String, RequestBody> map) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).commitImage(map);
    }

    @Override
    public Observable<Result> commitTopicData(String comment_id, String title, String content, String is_anonymity, String type) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).editPost(comment_id, title, content, is_anonymity, type);
    }
}
