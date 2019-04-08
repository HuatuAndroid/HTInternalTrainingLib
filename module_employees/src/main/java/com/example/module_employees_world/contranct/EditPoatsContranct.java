package com.example.module_employees_world.contranct;

import com.example.module_employees_world.bean.CommentInsertBean;
import com.example.module_employees_world.bean.NImageBean;
import com.example.module_employees_world.bean.NImageListsBean;
import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * author:LIENLIN
 * date:2019/4/6
 */
public interface EditPoatsContranct {

    interface IEditPoatsView extends MvpView{
        void commitImage(List<NImageBean> pathList);
        void commitTopicData(String content);
    }
    interface IEditPoatsModel extends BaseModel{
        Observable<Result<NImageListsBean>> commitImage(Map<String, RequestBody> map);
        Observable<Result> commitTopicData(String group_id, String title, String content, String is_anonymity, String path);
    }
    abstract class EditPostsPresenter extends BasePreaenter<IEditPoatsView,IEditPoatsModel>{
        public abstract void commitImage(Map<String, RequestBody> map);
        public abstract void commitTopicData(String group_id, String title, String content, String is_anonymity, String path);
    }
}
