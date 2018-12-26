package com.zhiyun88.www.module_main.main.config;


/**
 * 接口api
 */
public interface MainHttpConfig {
    //首页
    String HOME_URL = "api/app/home";
    //我的课程
    String MYCOURSE_URL="api/app/course/myCourse/type={type}";
    //我的任务
    String MYTASK_URL = "api/app/task/index";
    //我的培训
    String MYTRAIN_URL = "api/app/course/myOfflineTraining/type={type}";
    //搜索
    String SEARCH_URL = "api/app/courseBasis";

    //评论
    String USERPOSTCOMMENT = "api/app/comment";
    //获取是否有新的消息
    String GETNEWMESSAGE = "api/app/user_message_count";
}
