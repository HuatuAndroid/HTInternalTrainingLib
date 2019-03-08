package com.baijiahulian.live.ui.answersheet;

import com.baijiahulian.live.ui.base.BasePresenter;
import com.baijiahulian.live.ui.base.BaseView;
import com.baijiayun.livecore.models.LPAnswerSheetOptionModel;

import java.util.List;


/**
 * Created by yangjingming on 2018/6/5.
 */

public interface QuestionToolContract {
    interface View extends BaseView<Presenter>{
        void timeDown(String down);
    }

    interface Presenter extends BasePresenter{

        List<LPAnswerSheetOptionModel> getOptions();

        void addCheckedOption(int index);

        void deleteCheckedOption(int index);

        boolean submitAnswers();

        void removeQuestionTool(boolean isEnded);
    }
}
