package com.example.module_employees_world.contranct;

import com.example.module_employees_world.bean.NImageListsBean;
import com.example.module_employees_world.bean.TopicContentItem;
import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;
import com.wb.baselib.bean.Result;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author liuzhe
 * @date 2019/3/21
 */
public interface TopicEditContranct {

    interface View extends BaseView {

        void commitSuccess(String msg);
        void updateTopicSuccess(String msg);

        List<TopicContentItem> getData();
    }

    interface Model extends BaseModel {
        Observable<Result> sendUpdateTopic(String topicId, String title, String content, String is_anonymity);

        Observable<Result<NImageListsBean>> commitImage(Map<String, RequestBody> map);

        Observable<Result> commitTopicData(String group_id, String title, String content, String is_anonymity, String path);
    }

    abstract class Presenter extends BasePreaenter<View, Model> {
        public abstract void sendUpdateTopic(String topicId, String title, String content, String is_anonymity);

        public abstract void commitImage(Map<String, RequestBody> map);

        public abstract void commitTopicData(String group_id, String title, String content, String is_anonymity, String path);
    }
}
