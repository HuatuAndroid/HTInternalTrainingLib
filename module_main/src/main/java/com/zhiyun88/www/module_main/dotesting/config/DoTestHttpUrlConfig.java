package com.zhiyun88.www.module_main.dotesting.config;

public interface DoTestHttpUrlConfig {
    // 获取问卷试题
    String QUESTIONNAIRE="api/app/question_naire";

    // 获取试卷试题
    String PAPERQUESTION="api/app/paper_question";

    // 试题提交
    String SUBMIT="api/app/question/submit";

    // 试题报告
    String REPORT="api/app/question/report/id={id}";

    // 试题解析
    String ANALYSIS="api/app/question/analysis/id={id}";

    // 问卷报告
    String SURVEYREPORT="api/app/question/ques_naire_report/id={id}";

    // 错题解析
    String ERRORSPARSE="api/app/question/error_analysis/id={id}";

    // 错题解析
    String GETCUNTDATA="/api/app/question/report/id={id}";

    // 获取全部解析
    String ALLJXDATA="api/app/question/analysis/id={id}";
    // 获取错题解析
    String ERRORJXDATA="api/app/question/error_analysis/id={id}";

    // 获取问卷调查的报告
    String GETWJDCCOUNT="api/app/question/ques_naire_report/id={id}";

}
