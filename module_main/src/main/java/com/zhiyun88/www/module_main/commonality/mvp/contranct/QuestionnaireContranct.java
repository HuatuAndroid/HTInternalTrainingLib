package com.zhiyun88.www.module_main.commonality.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.commonality.bean.QuestionNaireBean;

import io.reactivex.Observable;

public interface QuestionnaireContranct {
    interface QuestionnaireView extends MvpView{
        void setSuccess(Result result);
    }
    interface QuestionnaireModel extends BaseModel{
        Observable<Result<QuestionNaireBean>> getQuestionData(int course_id);
        Observable<Result> setQuestionData(int basis_id, int chapter_id, int grade, String nice_comment, String negative_comments);
    }
    abstract class QuestionnairePresenter extends BasePreaenter<QuestionnaireView,QuestionnaireModel>{
            public abstract void getQuestionData(int course_id);
            public abstract void setQuestionData(int basis_id,int chapter_id,int grade,String nice_comment,String negative_comments);
    }
}
