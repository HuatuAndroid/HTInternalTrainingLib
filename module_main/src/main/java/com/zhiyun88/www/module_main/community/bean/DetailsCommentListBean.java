package com.zhiyun88.www.module_main.community.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailsCommentListBean implements Parcelable {

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
    private ParentCommentBean parent;
    private int allow_del;

    public void setId(String id) {
        this.id = id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setReply_count(String reply_count) {
        this.reply_count = reply_count;
    }

    public void setIs_anonymity(String is_anonymity) {
        this.is_anonymity = is_anonymity;
    }

    public void setCreated_id(String created_id) {
        this.created_id = created_id;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setParent(ParentCommentBean parent) {
        this.parent = parent;
    }

    public void setAllow_del(int allow_del) {
        this.allow_del = allow_del;
    }

    public String getId() {
        return id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public String getContent() {
        return content;
    }

    public String getReply_count() {
        return reply_count;
    }

    public String getIs_anonymity() {
        return is_anonymity;
    }

    public String getCreated_id() {
        return created_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public ParentCommentBean getParent() {
        return parent;
    }

    public int getAllow_del() {
        return allow_del;
    }

    protected DetailsCommentListBean(Parcel in) {
        id = in.readString();
        question_id = in.readString();
        parent_id = in.readString();
        content = in.readString();
        reply_count = in.readString();
        is_anonymity = in.readString();
        created_id = in.readString();
        created_at = in.readString();
        user_id = in.readString();
        user_name = in.readString();
        avatar = in.readString();
        parent = in.readParcelable(ParentCommentBean.class.getClassLoader());
        allow_del = in.readInt();
    }

    public static final Creator<DetailsCommentListBean> CREATOR = new Creator<DetailsCommentListBean>() {
        @Override
        public DetailsCommentListBean createFromParcel(Parcel in) {
            return new DetailsCommentListBean(in);
        }

        @Override
        public DetailsCommentListBean[] newArray(int size) {
            return new DetailsCommentListBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(question_id);
        dest.writeString(parent_id);
        dest.writeString(content);
        dest.writeString(reply_count);
        dest.writeString(is_anonymity);
        dest.writeString(created_id);
        dest.writeString(created_at);
        dest.writeString(user_id);
        dest.writeString(user_name);
        dest.writeString(avatar);
        dest.writeParcelable(parent, flags);
        dest.writeInt(allow_del);
    }
}
