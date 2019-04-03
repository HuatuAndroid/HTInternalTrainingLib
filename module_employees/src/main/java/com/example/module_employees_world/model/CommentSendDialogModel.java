package com.example.module_employees_world.model;

import com.example.module_employees_world.CommunityServiceApi;
import com.example.module_employees_world.bean.CommentInsertBean;
import com.example.module_employees_world.bean.NImageListsBean;
import com.example.module_employees_world.contranct.CommentSendDialogContranct;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * author:LIENLIN
 * date:2019/4/1
 */
public class CommentSendDialogModel implements CommentSendDialogContranct.ICommentSendDialogModel {
    @Override
    public Observable<Result<NImageListsBean>> commitImage(Map<String, RequestBody> map) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).commitImage(map);
    }

    @Override
    public Observable<Result<CommentInsertBean>> sendComment(String question_id, String content, String comment_picture, String comment_face, String is_anonymity, String comment_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("question_id",question_id);
        map.put("content",content);
        map.put("comment_picture",comment_picture);
        map.put("comment_face",comment_face);
        map.put("is_anonymity",is_anonymity);
        map.put("comment_id",comment_id);
        return HttpManager.newInstance().getService(CommunityServiceApi.class).commentSend(map);
    }
}
