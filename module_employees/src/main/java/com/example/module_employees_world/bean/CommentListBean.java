package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * author:LIENLIN
 * date:2019/3/25
 */
public class CommentListBean implements Serializable {

    @SerializedName("total")
    public int total;
    @SerializedName("current_page")
    public int currentPage;
    @SerializedName("list")
    public List<ListBean> list;

    protected CommentListBean(Parcel in) {
        total = in.readInt();
        currentPage = in.readInt();
    }

    public static class ListBean implements Serializable{
        /**
         * id : 429
         * question_id : 212
         * parent_id : 0
         * content : dvvvccvv
         * comment_rule : /429
         * like_count : 0
         * comment_picture : null
         * comment_face : null
         * reply_count : 1
         * is_anonymity : 0
         * created_id : 31192
         * created_at : 2019-03-23
         * is_del : 0
         * delete_at : null
         * solve_status : 0
         * department_name : 研发组
         * user_id : 31192
         * user_name : 王晨
         * avatar : http://htwuhan.oss-cn-beijing.aliyuncs.com/9255f7f0e86a49d19be19820920b0a62
         * allow_del : 1
         * count : 2
         * parent : [{"id":437,"question_id":212,"parent_id":436,"content":"2222","comment_rule":"/429/436/437","like_count":0,"comment_picture":null,"comment_face":null,"reply_count":0,"is_anonymity":0,"created_id":31192,"created_at":"2019-03-26","is_del":0,"delete_at":null,"solve_status":0,"department_name":"研发组","user_id":31192,"user_name":"王晨","avatar":"http://htwuhan.oss-cn-beijing.aliyuncs.com/9255f7f0e86a49d19be19820920b0a62"},{"id":436,"question_id":212,"parent_id":429,"content":"111","comment_rule":"/429/436","like_count":0,"comment_picture":null,"comment_face":null,"reply_count":1,"is_anonymity":0,"created_id":31192,"created_at":"2019-03-26","is_del":0,"delete_at":null,"solve_status":0,"department_name":"研发组","user_id":31192,"user_name":"王晨","avatar":"http://htwuhan.oss-cn-beijing.aliyuncs.com/9255f7f0e86a49d19be19820920b0a62"}]
         */

        @SerializedName("id")
        public int id;
        @SerializedName("question_id")
        public int questionId;
        @SerializedName("parent_id")
        public int parentId;
        @SerializedName("content")
        public String content;
        @SerializedName("comment_rule")
        public String commentRule;
        @SerializedName("like_count")
        public int likeCount;
        @SerializedName("comment_picture")
        public String commentPicture;
        @SerializedName("comment_face")
        public String commentFace;
        @SerializedName("reply_count")
        public int replyCount;
        @SerializedName("is_anonymity")
        public int isAnonymity;
        @SerializedName("created_id")
        public int createdId;
        @SerializedName("created_at")
        public String createdAt;
        @SerializedName("is_del")
        public int isDel;
        @SerializedName("delete_at")
        public Object deleteAt;
        @SerializedName("solve_status")
        public int solveStatus;
        @SerializedName("department_name")
        public String departmentName;
        @SerializedName("user_id")
        public int userId;
        @SerializedName("user_name")
        public String userName;
        @SerializedName("avatar")
        public String avatar;
        @SerializedName("allow_del")
        public int allowDel;//1 可以删除 0不可以
        @SerializedName("type")
        public int type;
        @SerializedName("count")
        public int count;
        @SerializedName("comment_like")
        public int comment_like;
        @SerializedName("parent")
        public List<ParentBean> parent;



       }
}
