package com.zhiyun88.www.module_main.community.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ParentCommentBean implements Parcelable {

    private String id;
    private String question_id;
    private String parent_id;
    private String content;
    private String reply_count;
    private String is_anonymity;
    private String created_id;
    private String created_at;
    private String user_id;
    private String user_name;
    private String avatar;

    @Override
    public String toString() {
        return "ParentCommentBean{" +
                "id='" + id + '\'' +
                ", question_id='" + question_id + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", content='" + content + '\'' +
                ", reply_count='" + reply_count + '\'' +
                ", is_anonymity='" + is_anonymity + '\'' +
                ", created_id='" + created_id + '\'' +
                ", created_at='" + created_at + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply_count() {
        return reply_count;
    }

    public void setReply_count(String reply_count) {
        this.reply_count = reply_count;
    }

    public String getIs_anonymity() {
        return is_anonymity;
    }

    public void setIs_anonymity(String is_anonymity) {
        this.is_anonymity = is_anonymity;
    }

    public String getCreated_id() {
        return created_id;
    }

    public void setCreated_id(String created_id) {
        this.created_id = created_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.question_id);
        dest.writeString(this.parent_id);
        dest.writeString(this.content);
        dest.writeString(this.reply_count);
        dest.writeString(this.is_anonymity);
        dest.writeString(this.created_id);
        dest.writeString(this.created_at);
        dest.writeString(this.user_id);
        dest.writeString(this.user_name);
        dest.writeString(this.avatar);
    }

    public ParentCommentBean() {
    }

    protected ParentCommentBean(Parcel in) {
        this.id = in.readString();
        this.question_id = in.readString();
        this.parent_id = in.readString();
        this.content = in.readString();
        this.reply_count = in.readString();
        this.is_anonymity = in.readString();
        this.created_id = in.readString();
        this.created_at = in.readString();
        this.user_id = in.readString();
        this.user_name = in.readString();
        this.avatar = in.readString();
    }

    public static final Creator<ParentCommentBean> CREATOR = new Creator<ParentCommentBean>() {
        @Override
        public ParentCommentBean createFromParcel(Parcel source) {
            return new ParentCommentBean(source);
        }

        @Override
        public ParentCommentBean[] newArray(int size) {
            return new ParentCommentBean[size];
        }
    };
}
