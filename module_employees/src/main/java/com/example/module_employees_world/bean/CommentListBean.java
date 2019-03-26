package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * author:LIENLIN
 * date:2019/3/25
 */
public class CommentListBean implements Parcelable{

    /**
     * total : 4
     * current_page : 1
     * list : [{"id":435,"question_id":298,"parent_id":433,"content":"111","like_count":0,"reply_count":0,"is_anonymity":0,"created_id":31192,"created_at":"刚刚","is_del":0,"delete_at":null,"department_name":"研发组","user_id":31192,"user_name":"王晨","avatar":"http://htwuhan.oss-cn-beijing.aliyuncs.com/9255f7f0e86a49d19be19820920b0a62","parent":{"id":433,"question_id":298,"parent_id":0,"content":"1123","like_count":0,"reply_count":1,"is_anonymity":1,"created_id":31192,"created_at":"30分钟前","is_del":0,"delete_at":null,"department_name":"","user_id":31192,"user_name":"匿名","avatar":"http://test-px.huatu.com//uploads/niming.jpeg"},"allow_del":1},{"id":434,"question_id":298,"parent_id":0,"content":"111","like_count":0,"reply_count":0,"is_anonymity":0,"created_id":31192,"created_at":"30分钟前","is_del":0,"delete_at":null,"department_name":"研发组","user_id":31192,"user_name":"王晨","avatar":"http://htwuhan.oss-cn-beijing.aliyuncs.com/9255f7f0e86a49d19be19820920b0a62","parent":null,"allow_del":1},{"id":433,"question_id":298,"parent_id":0,"content":"1123","like_count":0,"reply_count":1,"is_anonymity":1,"created_id":31192,"created_at":"30分钟前","is_del":0,"delete_at":null,"department_name":"","user_id":31192,"user_name":"匿名","avatar":"http://test-px.huatu.com//uploads/niming.jpeg","parent":null,"allow_del":1},{"id":432,"question_id":298,"parent_id":0,"content":"123456","like_count":0,"reply_count":0,"is_anonymity":1,"created_id":31192,"created_at":"30分钟前","is_del":0,"delete_at":null,"department_name":"","user_id":31192,"user_name":"匿名","avatar":"http://test-px.huatu.com//uploads/niming.jpeg","parent":null,"allow_del":1}]
     */

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

    public static final Creator<CommentListBean> CREATOR = new Creator<CommentListBean>() {
        @Override
        public CommentListBean createFromParcel(Parcel in) {
            return new CommentListBean(in);
        }

        @Override
        public CommentListBean[] newArray(int size) {
            return new CommentListBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(total);
        dest.writeInt(currentPage);
    }

    public static class ListBean {
        /**
         * id : 435
         * question_id : 298
         * parent_id : 433
         * content : 111
         * like_count : 0
         * reply_count : 0
         * is_anonymity : 0
         * created_id : 31192
         * created_at : 刚刚
         * is_del : 0
         * delete_at : null
         * department_name : 研发组
         * user_id : 31192
         * user_name : 王晨
         * avatar : http://htwuhan.oss-cn-beijing.aliyuncs.com/9255f7f0e86a49d19be19820920b0a62
         * parent : {"id":433,"question_id":298,"parent_id":0,"content":"1123","like_count":0,"reply_count":1,"is_anonymity":1,"created_id":31192,"created_at":"30分钟前","is_del":0,"delete_at":null,"department_name":"","user_id":31192,"user_name":"匿名","avatar":"http://test-px.huatu.com//uploads/niming.jpeg"}
         * allow_del : 1
         */

        @SerializedName("id")
        public int id;
        @SerializedName("question_id")
        public int questionId;
        @SerializedName("parent_id")
        public int parentId;
        @SerializedName("content")
        public String content;
        @SerializedName("like_count")
        public int likeCount;
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
        @SerializedName("department_name")
        public String departmentName;
        @SerializedName("user_id")
        public int userId;
        @SerializedName("user_name")
        public String userName;
        @SerializedName("avatar")
        public String avatar;
        @SerializedName("parent")
        public ParentBean parent;
        @SerializedName("allow_del")
        public int allowDel;

        public static class ParentBean implements Parcelable{
            /**
             * id : 433
             * question_id : 298
             * parent_id : 0
             * content : 1123
             * like_count : 0
             * reply_count : 1
             * is_anonymity : 1
             * created_id : 31192
             * created_at : 30分钟前
             * is_del : 0
             * delete_at : null
             * department_name :
             * user_id : 31192
             * user_name : 匿名
             * avatar : http://test-px.huatu.com//uploads/niming.jpeg
             */

            @SerializedName("id")
            public int id;
            @SerializedName("question_id")
            public int questionId;
            @SerializedName("parent_id")
            public int parentId;
            @SerializedName("content")
            public String content;
            @SerializedName("like_count")
            public int likeCount;
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
            @SerializedName("department_name")
            public String departmentName;
            @SerializedName("user_id")
            public int userId;
            @SerializedName("user_name")
            public String userName;
            @SerializedName("avatar")
            public String avatar;

            protected ParentBean(Parcel in) {
                id = in.readInt();
                questionId = in.readInt();
                parentId = in.readInt();
                content = in.readString();
                likeCount = in.readInt();
                replyCount = in.readInt();
                isAnonymity = in.readInt();
                createdId = in.readInt();
                createdAt = in.readString();
                isDel = in.readInt();
                departmentName = in.readString();
                userId = in.readInt();
                userName = in.readString();
                avatar = in.readString();
            }

            public static final Creator<ParentBean> CREATOR = new Creator<ParentBean>() {
                @Override
                public ParentBean createFromParcel(Parcel in) {
                    return new ParentBean(in);
                }

                @Override
                public ParentBean[] newArray(int size) {
                    return new ParentBean[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(id);
                dest.writeInt(questionId);
                dest.writeInt(parentId);
                dest.writeString(content);
                dest.writeInt(likeCount);
                dest.writeInt(replyCount);
                dest.writeInt(isAnonymity);
                dest.writeInt(createdId);
                dest.writeString(createdAt);
                dest.writeInt(isDel);
                dest.writeString(departmentName);
                dest.writeInt(userId);
                dest.writeString(userName);
                dest.writeString(avatar);
            }
        }
    }
}
