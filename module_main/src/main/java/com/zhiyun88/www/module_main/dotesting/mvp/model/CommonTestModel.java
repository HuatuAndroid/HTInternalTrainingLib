package com.zhiyun88.www.module_main.dotesting.mvp.model;

import android.text.TextUtils;
import android.util.Log;

import com.jungan.www.common_dotest.bean.QuestionBankBean;
import com.jungan.www.common_dotest.bean.UserOptionBean;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.wb.baselib.prase.GsonUtils;
import com.zhiyun88.www.module_main.dotesting.api.DoTestApiService;
import com.zhiyun88.www.module_main.dotesting.bean.PaperListBean;
import com.zhiyun88.www.module_main.dotesting.bean.PaperModuleBean;
import com.zhiyun88.www.module_main.dotesting.bean.PaperModuleQuesBean;
import com.zhiyun88.www.module_main.dotesting.bean.PaperQuesOptionBean;
import com.zhiyun88.www.module_main.dotesting.bean.PaperTestBean;
import com.zhiyun88.www.module_main.dotesting.bean.QestionTestBean;
import com.zhiyun88.www.module_main.dotesting.bean.QuesOptionBean;
import com.zhiyun88.www.module_main.dotesting.bean.QuestionBean;
import com.zhiyun88.www.module_main.dotesting.bean.QuestionNaireBean;
import com.zhiyun88.www.module_main.dotesting.bean.SubmitBean;
import com.zhiyun88.www.module_main.dotesting.bean.SubmitTestBean;
import com.zhiyun88.www.module_main.dotesting.config.TestTypeConfig;
import com.zhiyun88.www.module_main.dotesting.mvp.contranct.CommonTestContranct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CommonTestModel implements CommonTestContranct.CommonTestModel {

    @Override
    public Observable<List<QuestionBankBean>> getCommonTest(final String id, final String taskId, final int testType) {
        Observable<List<QuestionBankBean>> observable = Observable.create(new ObservableOnSubscribe<List<QuestionBankBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<QuestionBankBean>> e) throws Exception {
                if (testType == TestTypeConfig.WJST) {
                    getQuestionData(id, taskId, e);
                } else if (testType == TestTypeConfig.SJST) {
                    getPaperData(id, taskId, e);
                }else if(testType == TestTypeConfig.ALLJX){
                    getAllJxQuestionData(id,e);
                }else if(testType == TestTypeConfig.ERRJX){
                    getErrorJxQuestionData(id,e);
                }
            }
        });
        return observable;
    }

    @Override
    public Observable<Result<SubmitBean>> submitTest(SubmitTestBean submitTestBean) {
        HashMap<String, String> map = new HashMap<>();
        map.put("report_id", submitTestBean.getReport_id());
        map.put("type", submitTestBean.getType());
        map.put("answer_time", submitTestBean.getAnswer_time());
        map.put("answer_data", GsonUtils.newInstance().listToJson(submitTestBean.getAnswer_data()));
        return HttpManager.newInstance().getService(DoTestApiService.class).submitTest(map);
    }

    private void getPaperData(String id, String taskId, final ObservableEmitter<List<QuestionBankBean>> es) {
        Map<String,String> map=new HashMap<>();
        map.put("paper_id",id);
        map.put("task_id",taskId);
        Log.e("---->>>",map.toString());
        final Observable<Result<PaperTestBean>> paperQuestion = HttpManager.newInstance().getService(DoTestApiService.class).getPaperQuestion(map);
        HttpManager.newInstance().commonRequest(paperQuestion, new BaseObserver<Result<PaperTestBean>>(AppUtils.getContext()) {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Result<PaperTestBean> paperTestBeanResult) {
                if(paperTestBeanResult.getStatus()==200){
                    if (paperTestBeanResult.getData() == null ) {
                    }else {
                        forPaperData(paperTestBeanResult.getData(), es);
                    }
                }else {
                    ApiException apiException=new ApiException(paperTestBeanResult.getStatus(),paperTestBeanResult.getMsg());
                    es.onError(apiException);
                }

            }

            @Override
            public void onFail(ApiException e) {
                es.onError(e);
            }
        });
    }

    private void forPaperData(PaperTestBean paperTestBean, ObservableEmitter<List<QuestionBankBean>> e) {
        List<PaperModuleBean> paperModuleBeans = paperTestBean.getModule();
        PaperListBean paperListBean = paperTestBean.getPaper_list();
        List<QuestionBankBean> mkdatas = new ArrayList<>();
        for (PaperModuleBean paperModuleBean : paperModuleBeans) {//2
            String moduleName = paperModuleBean.getName();
            for (PaperModuleQuesBean paperModuleQuesBean : paperModuleBean.getModule_question()) {
                QuestionBankBean questionBankBean = new QuestionBankBean();
                questionBankBean.setCorrect_rate(null);
                questionBankBean.setFallibility(null);
                questionBankBean.setIsCollect(0);
                questionBankBean.setKnows_name(null);
                questionBankBean.setNumber_ques(null);
                questionBankBean.setOption(null);
                questionBankBean.setQues_analysis("");
                questionBankBean.setQuestionModel(0);
                questionBankBean.setQuestionNum(Long.parseLong(paperModuleQuesBean.getQues_number()));
                questionBankBean.setUser_answer(null);
                questionBankBean.setUser_dotime(paperListBean.getQuestion_time());
                questionBankBean.setQuestId(paperModuleQuesBean.getId());
                questionBankBean.setQuestionIssue(null);
                questionBankBean.setQuestionStem(paperModuleQuesBean.getQues_stem());
                Log.d("kaelli", "setQuestionModuleName :"+moduleName);
                questionBankBean.setQuestionModuleName(moduleName);
                questionBankBean.setQuestionType(Integer.parseInt(paperModuleQuesBean.getQues_type()));
                questionBankBean.setReport_id(paperModuleQuesBean.getReport_id());
                questionBankBean.setRight_answer(paperModuleQuesBean.getRight_answer());
                questionBankBean.setUser_answer(paperModuleQuesBean.getUser_answer());
                questionBankBean.setAnswer_difficulty(paperModuleQuesBean.getQues_difficulty());
                questionBankBean.setQues_analysis(paperModuleQuesBean.getQues_analysis());
                List<UserOptionBean> userOptionBeans = new ArrayList<>();
                if (paperModuleQuesBean.getQues_option() == null || paperModuleQuesBean.getQues_option().size() == 0) {
                } else {
                    for (PaperQuesOptionBean paperQuesOptionBean : paperModuleQuesBean.getQues_option()) {
                        UserOptionBean userOptionBean = new UserOptionBean();
                        userOptionBean.setOptionName(paperQuesOptionBean.getAnswer());
                        userOptionBean.setOptionContext(paperQuesOptionBean.getContent());
                        userOptionBeans.add(userOptionBean);
                    }
                    questionBankBean.setUserOption(userOptionBeans);
                }
                mkdatas.add(questionBankBean);
            }
        }
        e.onNext(mkdatas);
    }

    private void getQuestionData(String id, String taskId, final ObservableEmitter<List<QuestionBankBean>> e) {
        Observable<Result<QestionTestBean>> questionNaire = HttpManager.newInstance().getService(DoTestApiService.class).getQuestionNaire(id, taskId);
        HttpManager.newInstance().commonRequest(questionNaire, new BaseObserver<Result<QestionTestBean>>(AppUtils.getContext()) {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Result<QestionTestBean> qestionTestBeanResult) {
                if (qestionTestBeanResult.getData() == null) {
                }else {
                    forQuestionData(qestionTestBeanResult.getData(), e);
                }
            }

            @Override
            public void onFail(ApiException e) {
                Log.e("onFail: ", e.getMessage());
            }
        });
    }


    private void getAllJxQuestionData(String id, final ObservableEmitter<List<QuestionBankBean>> e) {
        Observable<Result<PaperTestBean>> questionNaire = HttpManager.newInstance().getService(DoTestApiService.class).getAllJxQuestionNaire(id);
        HttpManager.newInstance().commonRequest(questionNaire, new BaseObserver<Result<PaperTestBean>>(AppUtils.getContext()) {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Result<PaperTestBean> qestionTestBeanResult) {
                if (qestionTestBeanResult.getData() == null) {
                }else {
                    forPaperData(qestionTestBeanResult.getData(), e);
                }
            }

            @Override
            public void onFail(ApiException e) {
                Log.e("onFail: ", e.getMessage());
            }
        });
    }

    private void getErrorJxQuestionData(String id, final ObservableEmitter<List<QuestionBankBean>> e) {
        Observable<Result<PaperTestBean>> questionNaire = HttpManager.newInstance().getService(DoTestApiService.class).getErrorJxData(id);
        HttpManager.newInstance().commonRequest(questionNaire, new BaseObserver<Result<PaperTestBean>>(AppUtils.getContext()) {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Result<PaperTestBean> qestionTestBeanResult) {
                if (qestionTestBeanResult.getData() == null) {
                }else {
                    forPaperData(qestionTestBeanResult.getData(), e);
                }
            }

            @Override
            public void onFail(ApiException e) {
                Log.e("onFail: ", e.getMessage());
            }
        });
    }
    private void forQuestionData(QestionTestBean qestionTestBean, ObservableEmitter<List<QuestionBankBean>> e) {
        List<QuestionBean> question = qestionTestBean.getQuestion();
        QuestionNaireBean questionNaireBean = qestionTestBean.getQuestion_naire();
        List<QuestionBankBean> questionBankBeans = new ArrayList<>();
        for (QuestionBean questionBean : question) {
            QuestionBankBean questionBankBean = new QuestionBankBean();
            questionBankBean.setAnswer_difficulty(null);
            questionBankBean.setCorrect_rate(null);
            questionBankBean.setFallibility(null);
            questionBankBean.setIsCollect(0);
            questionBankBean.setKnows_name(null);
            questionBankBean.setNumber_ques(null);
            questionBankBean.setOption(null);
            questionBankBean.setQues_analysis("");
            questionBankBean.setQuestId(questionBean.getId());
            questionBankBean.setQuestionIssue(null);
            questionBankBean.setQuestionModel(0);
            questionBankBean.setQuestionNum(Long.parseLong(questionBean.getQues_number()));
            questionBankBean.setQuestionStem(questionBean.getQues_stem());
            if (!TextUtils.isEmpty(questionBean.getQues_stem()) && questionBean.getQues_stem().contains("甲公司违约")) {
                Log.d("kaelli", "forQuestionData questionBean.getQues_type():"+questionBean.getQues_type());
            }
            questionBankBean.setQuestionType(Integer.parseInt(questionBean.getQues_type()));
            questionBankBean.setReport_id(questionBean.getReport_id());
            questionBankBean.setRight_answer(questionBean.getRight_answer());
            questionBankBean.setUser_answer(null);
            questionBankBean.setUser_dotime(questionNaireBean.getQuestion_time());
            List<UserOptionBean> userOptionBeans = new ArrayList<>();
            if (questionBean.getQues_option() == null || questionBean.getQues_option().size() == 0) {
            } else {
                for (QuesOptionBean quesOptionBean : questionBean.getQues_option()) {
                    UserOptionBean userOptionBean = new UserOptionBean();
                    userOptionBean.setOptionName(quesOptionBean.getAnswer());
                    userOptionBean.setOptionContext(quesOptionBean.getContent());
                    userOptionBeans.add(userOptionBean);
                }
            }
            questionBankBean.setUserOption(userOptionBeans);
            questionBankBeans.add(questionBankBean);
        }
        e.onNext(questionBankBeans);
    }
}
