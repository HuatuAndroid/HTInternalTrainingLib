package com.zhiyun88.www.module_main.commonality.config;

/**
 * 接口api
 */
public interface CommonalityHttpConfig {
    //我的反馈
    String FEEDBACK = "api/app/user/feedback";
    //我的积分
    String INTEGRAL = "api/app/webuser/integral/id={id}";
    //积分记录
    String INTEGRAL_RECORD = "api/app/webuser/integral_details/id={id}";
    //积分排行
    String INTEGRAL_RANKING = "api/app/user/ranking/id={id}";
    //个人信息
    String USERINFO = "api/app/webuser/index/id={id}";
    //我的消息
    String MESSAGE = "api/app/message";
    //消息状态
    String IS_READ = "api/app/signread";
    //获取调查问卷信息
    String GETQUESTIONNAIRE = "api/app/courseChapter/id={id}";
    //调查问卷
    String QUESTIONNAIRE = "api/app/questionnaire";
    //我的证书
    String MYCERTIFICATE = "api/app/MyCertificate";
    //我的文库
    String MYLIBRARY = "api/app/mylibrarylist";
}
