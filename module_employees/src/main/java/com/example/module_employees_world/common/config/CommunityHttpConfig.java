package com.example.module_employees_world.common.config;


/**
 * 接口api
 */
public interface CommunityHttpConfig {
    //小组列表
    String GROUPLIST = "api/app/discuss/getMoreGroup";
    //我的小组
    String MYGROUP = "api/app/discuss/myGroup";
    //我的参与
    String MYPART = "api/app/discuss/myPart";
    //取消,加入
    String JOINGROUP = "api/app/discuss/userGroup";
    //话题热门,最新列表
    String DISCUSS = "api/app/discuss/getQuestion/type={type}";
    //小组详情
    String GROUPDETAILS = "api/app/discuss/group/group_id={group_id}";
    //小组列表详情
    String GROUPDETAILSTYPE = "api/app/discuss/getQuestion/type={type}";
    //详情话题点赞
    String DETAILS_LIKE = "api/app/discuss/questionLike/question_id={question_id}";
    //详情话题收藏
    String DETAILS_COLLECT = "api/app/discuss/userCollect";
    //话题详情
    String COMMUNITY_DETAILS= "api/app/discuss/question/question_id={question_id}";
    //发布话题
    String RELEASETOPIC= "api/app/discuss/createQuestion";
    //公共图片
    String PUBLICIMAGE= "api/app/public/images";
    //获取评论列表
    String COMMENT_LIST= "api/app/discuss/comment";
    //发表评论
    String SENDCOMMENT= "api/app/discuss/createComment";
    /**删除帖子*/
    String DELETE_TOPIC="api/app/discuss/delQuestion";
    /**删除评论*/
    String DELETE_COMMENT="api/app/discuss/delComment";
    /**编辑帖子*/
    String EDIT_TOPIC="api/app/discuss/editQuestion";
}
