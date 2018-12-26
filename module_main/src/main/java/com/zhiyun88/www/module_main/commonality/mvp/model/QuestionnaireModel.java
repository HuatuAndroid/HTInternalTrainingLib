package com.zhiyun88.www.module_main.commonality.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.commonality.api.CommonalityServiceApi;
import com.zhiyun88.www.module_main.commonality.bean.QuestionNaireBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.QuestionnaireContranct;

import io.reactivex.Observable;

public class QuestionnaireModel implements QuestionnaireContranct.QuestionnaireModel {

    @Override
    public Observable<Result<QuestionNaireBean>> getQuestionData(int course_id) {
        return HttpManager.newInstance().getService(CommonalityServiceApi.class).getQuestionData(course_id);
    }

    @Override
    public Observable<Result> setQuestionData(int basis_id, int chapter_id, int grade, String nice_comment, String negative_comments) {
        return HttpManager.newInstance().getService(CommonalityServiceApi.class).setQuestionData(basis_id,chapter_id,grade,nice_comment,negative_comments);
    }
}
