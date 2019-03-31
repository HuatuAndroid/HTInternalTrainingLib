package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * author:LIENLIN
 * date:2019/3/30
 */
public class CommentChildrenBean implements Parcelable{

    /**
     * id : 441
     * question_id : 147
     * parent_id : 436
     * content : dsfsdfsdfsdf
     * comment_rule : /429/436/441
     * like_count : 1
     * comment_picture : null
     * comment_face : null
     * reply_count : 0
     * is_anonymity : 1
     * created_id : 30860
     * created_at : 2019-03-28
     * is_del : 0
     * delete_at : null
     * solve_status : 0
     * parent_name : 王晨
     * department_name :
     * user_id : 30860
     * user_name : 匿名
     * avatar : http://test-px.huatu.com//uploads/niming.jpeg
     * allow_del : 0
     * comment_like : 0
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
    @SerializedName("parent_name")
    public String parentName;
    @SerializedName("department_name")
    public String departmentName;
    @SerializedName("user_id")
    public int userId;
    @SerializedName("user_name")
    public String userName;
    @SerializedName("avatar")
    public String avatar;
    @SerializedName("allow_del")
    public int allowDel;
    @SerializedName("comment_like")
    public int commentLike;

    protected CommentChildrenBean(Parcel in) {
        id = in.readInt();
        questionId = in.readInt();
        parentId = in.readInt();
        content = in.readString();
        commentRule = in.readString();
        likeCount = in.readInt();
        commentPicture = in.readString();
        commentFace = in.readString();
        replyCount = in.readInt();
        isAnonymity = in.readInt();
        createdId = in.readInt();
        createdAt = in.readString();
        isDel = in.readInt();
        solveStatus = in.readInt();
        parentName = in.readString();
        departmentName = in.readString();
        userId = in.readInt();
        userName = in.readString();
        avatar = in.readString();
        allowDel = in.readInt();
        commentLike = in.readInt();
    }

    public static final Creator<CommentChildrenBean> CREATOR = new Creator<CommentChildrenBean>() {
        @Override
        public CommentChildrenBean createFromParcel(Parcel in) {
            return new CommentChildrenBean(in);
        }

        @Override
        public CommentChildrenBean[] newArray(int size) {
            return new CommentChildrenBean[size];
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
        dest.writeString(commentRule);
        dest.writeInt(likeCount);
        dest.writeString(commentPicture);
        dest.writeString(commentFace);
        dest.writeInt(replyCount);
        dest.writeInt(isAnonymity);
        dest.writeInt(createdId);
        dest.writeString(createdAt);
        dest.writeInt(isDel);
        dest.writeInt(solveStatus);
        dest.writeString(parentName);
        dest.writeString(departmentName);
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeString(avatar);
        dest.writeInt(allowDel);
        dest.writeInt(commentLike);
    }
}
