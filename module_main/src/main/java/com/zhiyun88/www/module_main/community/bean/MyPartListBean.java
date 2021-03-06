package com.zhiyun88.www.module_main.community.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class MyPartListBean implements Parcelable {

    private String id;
    private String group_id;
    private String title;
    private String content;
    private String content_picture;
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
    private String h5_detail;

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

    public String getContent_picture() {
        return content_picture;
    }

    public void setContent_picture(String content_picture) {
        this.content_picture = content_picture;
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

    public String getH5_detail() {
        return h5_detail;
    }

    public void setH5_detail(String h5_detail) {
        this.h5_detail = h5_detail;
    }

    public MyPartListBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.group_id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.content_picture);
        dest.writeString(this.like_count);
        dest.writeString(this.read_count);
        dest.writeString(this.comment_count);
        dest.writeString(this.is_essence);
        dest.writeString(this.is_top);
        dest.writeString(this.is_recommend);
        dest.writeString(this.is_anonymity);
        dest.writeString(this.created_id);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeString(this.h5_detail);
    }

    protected MyPartListBean(Parcel in) {
        this.id = in.readString();
        this.group_id = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.content_picture = in.readString();
        this.like_count = in.readString();
        this.read_count = in.readString();
        this.comment_count = in.readString();
        this.is_essence = in.readString();
        this.is_top = in.readString();
        this.is_recommend = in.readString();
        this.is_anonymity = in.readString();
        this.created_id = in.readString();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.h5_detail = in.readString();
    }

    public static final Creator<MyPartListBean> CREATOR = new Creator<MyPartListBean>() {
        @Override
        public MyPartListBean createFromParcel(Parcel source) {
            return new MyPartListBean(source);
        }

        @Override
        public MyPartListBean[] newArray(int size) {
            return new MyPartListBean[size];
        }
    };
}
