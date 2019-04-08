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
    //帖子点赞
    String DETAILS_LIKE = "api/app/discuss/questionLike/question_id={question_id}";
    //评论点赞（适用于评论，子评论）
    String COMMENT_LIKE = "api/app/discuss/commentLike/comment_id={comment_id}";
    //详情话题收藏
    String DETAILS_COLLECT = "api/app/discuss/userCollect";
    //话题详情
    String POST_DETAILS= "api/app/discuss/question/question_id={question_id}";
    //发布话题
    String RELEASETOPIC = "api/app/discuss/createQuestion";
    //公共图片
    String PUBLICIMAGE = "api/app/public/images";
    //获取评论列表
    String COMMENT_LIST= "api/app/discuss/appComment/question_id={question_id}";
    //发表评论
    String SENDCOMMENT= "api/app/discuss/createComment";
    /**删除帖子*/
    String DELETE_POST="api/app/discuss/delQuestion";
    /**删除评论*/
    String DELETE_COMMENT="api/app/discuss/delComment";
    /**编辑帖子*/
    String EDIT_TOPIC="api/app/discuss/editQuestion";
    /** 搜索*/
    String SEARCH = "api/app/discuss/search/type={type}";
    /** 评论详情*/
    String COMMENT_INFO = "api/app/discuss/appCommentInfo/comment_id={comment_id}";
    /**评论子列表*/
    /**发布评论*/
    String COMMENT_CHILDREN_LIST = "api/app/discuss/appCommentSonInfo/comment_id={comment_id}";
    /**评论发布*/
    String COMMENT_SEND = "api/app/discuss/createComment";
    /** 广告*/
    String GUIDE = "api/app/discuss/getBanner";
    /**修改帖子类型*/
    String editQuestion = "api/app/discuss/editQuestionType";
    /**采纳帖子*/
    String acceptPosts="api/app/discuss/cancelAdoption";
    /**采纳评论*/
    String acceptComment="api/app/discuss/comment/comment_id={comment_id}";
    /**邀请回答*/
    String invitationUser="api/app/discuss/invitation";
    /**编辑帖子*/
    String editPost="api/app/discuss/editQuestion";
    /**app-判断是否可以发表评论*/
    String isBanned="api/app/discuss/isBanned";
}
