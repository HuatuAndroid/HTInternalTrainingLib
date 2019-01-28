package com.zhiyun88.www.module_main.course.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TeacherData implements Parcelable {

    /**
     * id : 18
     * name : 江军
     * avatar : http://htwuhan.oss-cn-beijing.aliyuncs.com/aadb5a89e142468db307f52ec0a0dd96.jpg
     * teacher_info : 江军的简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介v
     简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介
     * user_id : 30860
     */

    private String id;
    private String name;
    private String avatar;
    private String teacher_info;
    private String user_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTeacher_info() {
        return teacher_info;
    }

    public void setTeacher_info(String teacher_info) {
        this.teacher_info = teacher_info;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeString(this.teacher_info);
        dest.writeString(this.user_id);
    }

    public TeacherData() {
    }

    protected TeacherData(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.avatar = in.readString();
        this.teacher_info = in.readString();
        this.user_id = in.readString();
    }

    public static final Parcelable.Creator<TeacherData> CREATOR = new Parcelable.Creator<TeacherData>() {
        @Override
        public TeacherData createFromParcel(Parcel source) {
            return new TeacherData(source);
        }

        @Override
        public TeacherData[] newArray(int size) {
            return new TeacherData[size];
        }
    };

    @Override
    public String toString() {
        return "TeacherData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", teacher_info='" + teacher_info + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
