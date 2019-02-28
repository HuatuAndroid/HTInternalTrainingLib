package com.zhiyun88.www.module_main.community.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.community.bean.ImageBean;
import com.zhiyun88.www.module_main.community.bean.ImageListBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * author:LIENLIN
 * date:2019/2/25
 * 编辑帖子
 */
public interface UpdateTopicContranct {
    interface UpdateTopicView extends BaseView{
        void updateTopicSuccess(String msg);
    }
    interface UpdateTopicModel extends BaseModel{
        Observable<Result> sendUpdateTopic(String topicId,String title,String content,String is_anonymity);
        Observable<Result<ImageListBean>> commitImage(Map<String, RequestBody> map);
    }
    abstract class UpdateTopicPresenter extends BasePreaenter<UpdateTopicView,UpdateTopicModel>{
        public abstract void sendUpdateTopic(String topicId,String title,String content,String is_anonymity);
        public abstract void commitImage(Map<String, RequestBody> map);
    }
}
