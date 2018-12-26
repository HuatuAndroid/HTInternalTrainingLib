package com.jungan.www.common_dotest.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class WdBean implements Parcelable {

    /**
     * wd_id : 8
     * problem : ssssss
     * answer : ddddddd
     * created_at : 2018-08-29 20:02:53
     * updated_at : 2018-08-29 20:02:53
     * user_name : 爱臣
     * user_avatar : http://zacs.zhiyun88.com/uploads/20180719/65a9c730a6409af6941a7376786d5bc6.png
     * name : 测试T老师
     * t_avatar : http://zacs.zhiyun88.com/uploads/images/20180817/fe73a4ac7bc801ba7fc26825a9b5ab93.jpg
     * praise_num : 0
     * is_praise : 0
     * is_collection : 0
     */

    private String wd_id;
    private String problem;
    private String answer;
    private String created_at;
    private String updated_at;
    private String user_name;
    private String user_avatar;
    private String name;
    private String t_avatar;
    private String praise_num;
    private String is_praise;
    private String is_collection;

    public String getWd_id() {
        return wd_id;
    }

    public void setWd_id(String wd_id) {
        this.wd_id = wd_id;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getT_avatar() {
        return t_avatar;
    }

    public void setT_avatar(String t_avatar) {
        this.t_avatar = t_avatar;
    }

    public String getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(String praise_num) {
        this.praise_num = praise_num;
    }

    public String getIs_praise() {
        return is_praise;
    }

    public void setIs_praise(String is_praise) {
        this.is_praise = is_praise;
    }

    public String getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(String is_collection) {
        this.is_collection = is_collection;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.wd_id);
        dest.writeString(this.problem);
        dest.writeString(this.answer);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeString(this.user_name);
        dest.writeString(this.user_avatar);
        dest.writeString(this.name);
        dest.writeString(this.t_avatar);
        dest.writeString(this.praise_num);
        dest.writeString(this.is_praise);
        dest.writeString(this.is_collection);
    }

    public WdBean() {
    }

    protected WdBean(Parcel in) {
        this.wd_id = in.readString();
        this.problem = in.readString();
        this.answer = in.readString();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.user_name = in.readString();
        this.user_avatar = in.readString();
        this.name = in.readString();
        this.t_avatar = in.readString();
        this.praise_num = in.readString();
        this.is_praise = in.readString();
        this.is_collection = in.readString();
    }

    public static final Parcelable.Creator<WdBean> CREATOR = new Parcelable.Creator<WdBean>() {
        @Override
        public WdBean createFromParcel(Parcel source) {
            return new WdBean(source);
        }

        @Override
        public WdBean[] newArray(int size) {
            return new WdBean[size];
        }
    };
}
