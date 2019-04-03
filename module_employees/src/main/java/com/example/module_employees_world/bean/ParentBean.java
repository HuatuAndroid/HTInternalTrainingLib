package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * author:LIENLIN
 * date:2019/3/29
 */
public class ParentBean implements Parcelable {
    /**
     * id : 437
     * question_id : 212
     * parent_id : 436
     * content : 2222
     * comment_rule : /429/436/437
     * like_count : 0
     * comment_picture : null
     * comment_face : null
     * reply_count : 0
     * is_anonymity : 0
     * created_id : 31192
     * created_at : 2019-03-26
     * is_del : 0
     * delete_at : null
     * solve_status : 0
     * department_name : 研发组
     * user_id : 31192
     * user_name : 王晨
     * avatar : http://htwuhan.oss-cn-beijing.aliyuncs.com/9255f7f0e86a49d19be19820920b0a62
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
    @SerializedName("parent_name")
    public String parent_name;
    @SerializedName("comment_like")
    public int comment_like;
    @SerializedName("allow_del")
    public int allowDel;//1 可以删除


    protected ParentBean(Parcel in) {
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
        departmentName = in.readString();
        userId = in.readInt();
        userName = in.readString();
        avatar = in.readString();
        parent_name = in.readString();
        comment_like = in.readInt();
        allowDel = in.readInt();
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
        dest.writeString(departmentName);
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeString(avatar);
        dest.writeString(parent_name);
        dest.writeInt(comment_like);
        dest.writeInt(allowDel);
    }
}
