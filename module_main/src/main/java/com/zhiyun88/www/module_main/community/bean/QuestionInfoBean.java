package com.zhiyun88.www.module_main.community.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionInfoBean implements Parcelable {

    private String id;
    private String group_id;
    private String title;
    private String content;
    private String like_count;
    private String read_count;
    private String comment_count;
    private String is_essence;
    private String is_top;
    private String is_recommend;
    private String is_anonymity;
    private String created_id;
    private String created_at;
    private String updated_at;
    private String group_name;
    private String user_name;
    private String avatar;
    private String is_collect;

    private int is_like;

    protected QuestionInfoBean(Parcel in) {
        id = in.readString();
        group_id = in.readString();
        title = in.readString();
        content = in.readString();
        like_count = in.readString();
        read_count = in.readString();
        comment_count = in.readString();
        is_essence = in.readString();
        is_top = in.readString();
        is_recommend = in.readString();
        is_anonymity = in.readString();
        created_id = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        group_name = in.readString();
        user_name = in.readString();
        avatar = in.readString();
        is_collect = in.readString();
        is_like = in.readInt();
    }

    public static final Creator<QuestionInfoBean> CREATOR = new Creator<QuestionInfoBean>() {
        @Override
        public QuestionInfoBean createFromParcel(Parcel in) {
            return new QuestionInfoBean(in);
        }

        @Override
        public QuestionInfoBean[] newArray(int size) {
            return new QuestionInfoBean[size];
        }
    };

    public void setIs_like(int is_like) {
        this.is_like = is_like;
    }

    public int getIs_like() {

        return is_like;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getRead_count() {
        return read_count;
    }

    public void setRead_count(String read_count) {
        this.read_count = read_count;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getIs_essence() {
        return is_essence;
    }

    public void setIs_essence(String is_essence) {
        this.is_essence = is_essence;
    }

    public String getIs_top() {
        return is_top;
    }

    public void setIs_top(String is_top) {
        this.is_top = is_top;
    }

    public String getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(String is_recommend) {
        this.is_recommend = is_recommend;
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

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
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

    public String getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(String is_collect) {
        this.is_collect = is_collect;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(group_id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(like_count);
        dest.writeString(read_count);
        dest.writeString(comment_count);
        dest.writeString(is_essence);
        dest.writeString(is_top);
        dest.writeString(is_recommend);
        dest.writeString(is_anonymity);
        dest.writeString(created_id);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeString(group_name);
        dest.writeString(user_name);
        dest.writeString(avatar);
        dest.writeString(is_collect);
        dest.writeInt(is_like);
    }
}
