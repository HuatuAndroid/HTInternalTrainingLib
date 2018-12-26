package com.zhiyun88.www.module_main.course.config;

/**
 * 接口api
 */
public interface CourseHttpConfig {
     //课程详情接口
     String COURSEINFO_URL="api/app/courseInfo/basis_id={basis_id}/st=1";
     //我的课程
     String MYCOURSE_URL="api/app/course/myCourse/type={type}&page={page}";
     //课程详情 评论接口
     String COURSEINFO_COMMENTLIST="api/app/commentlist";
     //课程筛选接口
     String COURSEMAIN_URL="api/app/courseBasis";

     //课程筛选接口
     String COURSEMAINCLASSFLY_URL="api/app/courseClassify/{type}";

     //加入课程
     String JOINCOURSE_URL="api/app/course/study/basis_id={basis_id}";

     String GETBJYTOKEN_URL="api/app/course/getPlayToken/video_id={video_id}";

     String UPLOAD_WATCH_TIME_URL = "api/app/setWatchChapter";

     String GET_WATCH_TIME_URL = "api/app/getWatchChapter/video_id={video_id}";
}
