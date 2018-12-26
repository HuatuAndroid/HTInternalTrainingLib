package com.zhiyun88.www.module_main.course.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CommentListData implements Parcelable {

    /**
     * id : 3
     * content : 我是评论内容,字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下字体撑一下
     * comment_shop_id : 30
     * grade : 5
     * type : 1
     * created_name : 山余
     * avatar : http://htwuhan.oss-cn-beijing.aliyuncs.com/2f30cc80263b4dbd9aec036498dda310.jpg
     * created_at : 2018-09-26 10:06:47
     * comment_shop_name : 线下培训1
     */

    private String id;
    private String content;
    private String comment_shop_id;
    private String grade;
    private String type;
    private String created_name;
    private String avatar;
    private String created_at;
    private String comment_shop_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComment_shop_id() {
        return comment_shop_id;
    }

    public void setComment_shop_id(String comment_shop_id) {
        this.comment_shop_id = comment_shop_id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated_name() {
        return created_name;
    }

    public void setCreated_name(String created_name) {
        this.created_name = created_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getComment_shop_name() {
        return comment_shop_name;
    }

    public void setComment_shop_name(String comment_shop_name) {
        this.comment_shop_name = comment_shop_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.content);
        dest.writeString(this.comment_shop_id);
        dest.writeString(this.grade);
        dest.writeString(this.type);
        dest.writeString(this.created_name);
        dest.writeString(this.avatar);
        dest.writeString(this.created_at);
        dest.writeString(this.comment_shop_name);
    }

    public CommentListData() {
    }

    protected CommentListData(Parcel in) {
        this.id = in.readString();
        this.content = in.readString();
        this.comment_shop_id = in.readString();
        this.grade = in.readString();
        this.type = in.readString();
        this.created_name = in.readString();
        this.avatar = in.readString();
        this.created_at = in.readString();
        this.comment_shop_name = in.readString();
    }

    public static final Creator<CommentListData> CREATOR = new Creator<CommentListData>() {
        @Override
        public CommentListData createFromParcel(Parcel source) {
            return new CommentListData(source);
        }

        @Override
        public CommentListData[] newArray(int size) {
            return new CommentListData[size];
        }
    };
}
