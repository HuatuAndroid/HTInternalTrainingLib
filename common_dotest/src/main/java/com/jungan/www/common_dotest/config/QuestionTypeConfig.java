package com.jungan.www.common_dotest.config;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 这个是题库  题型的配置
 */
@IntDef({
        QuestionTypeConfig.RADIO_CHOICE,
        QuestionTypeConfig.MULITSELECT_CHOICE,
        QuestionTypeConfig.MULITSELECT_CHOICE_BDX,
        QuestionTypeConfig.MATERIAL_RADIO_CHOICE,
        QuestionTypeConfig.MATERIAL_MULITSELECT_CHOICE,
        QuestionTypeConfig.ESSAY_QUESTION,
        QuestionTypeConfig.TEST_PDT,
        QuestionTypeConfig.TEST_TKT
})
@Retention(RetentionPolicy.SOURCE)
public @interface  QuestionTypeConfig {
    //1:单选题 2:多选题 3:不定项选择 4:判断题 5:问答题 6:填空题 7:组合题
    //普通单选标识
    int RADIO_CHOICE=1;
    //普通多选标识
    int MULITSELECT_CHOICE=2;
    //不定项选择
    int MULITSELECT_CHOICE_BDX=3;
    //材料单选标识
    int MATERIAL_RADIO_CHOICE=7;
    //材料多选标识
    int MATERIAL_MULITSELECT_CHOICE=8;
    //问答标识
    int ESSAY_QUESTION=5;
    //判断题
    int TEST_PDT=4;
    //填空题
    int TEST_TKT=6;
}
