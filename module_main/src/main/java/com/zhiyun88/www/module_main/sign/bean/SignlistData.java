package com.zhiyun88.www.module_main.sign.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class SignlistData implements Parcelable {

    /**
     * basis_id : 559
     * basis_title : 向下培训--测试二维码
     * cover : http://test-px.huatu.com/uploads/images/20181128/508f1b7e8d468f3593198cb0827d7514.jpg
     * chapter_id : 1182
     * chapter_title : 第一节
     * start_date : 2018-11-28 15:30:00
     * end_date : 2018-11-28 17:00:00
     * teacher : 赵同鑫
     * start_end_date : 2018-11-28 15:30-17:00
     */

    private String basis_id;
    private String basis_title;
    private String cover;
    private String chapter_id;
    private String chapter_title;
    private String start_date;
    private String end_date;
    private String teacher;
    private String start_end_date;
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBasis_id() {
        return basis_id;
    }

    public void setBasis_id(String basis_id) {
        this.basis_id = basis_id;
    }

    public String getBasis_title() {
        return basis_title;
    }

    public void setBasis_title(String basis_title) {
        this.basis_title = basis_title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getChapter_title() {
        return chapter_title;
    }

    public void setChapter_title(String chapter_title) {
        this.chapter_title = chapter_title;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getStart_end_date() {
        return start_end_date;
    }

    public void setStart_end_date(String start_end_date) {
        this.start_end_date = start_end_date;
    }

    public SignlistData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.basis_id);
        dest.writeString(this.basis_title);
        dest.writeString(this.cover);
        dest.writeString(this.chapter_id);
        dest.writeString(this.chapter_title);
        dest.writeString(this.start_date);
        dest.writeString(this.end_date);
        dest.writeString(this.teacher);
        dest.writeString(this.start_end_date);
        dest.writeInt(this.code);
        dest.writeString(this.msg);
    }

    protected SignlistData(Parcel in) {
        this.basis_id = in.readString();
        this.basis_title = in.readString();
        this.cover = in.readString();
        this.chapter_id = in.readString();
        this.chapter_title = in.readString();
        this.start_date = in.readString();
        this.end_date = in.readString();
        this.teacher = in.readString();
        this.start_end_date = in.readString();
        this.code = in.readInt();
        this.msg = in.readString();
    }

    public static final Creator<SignlistData> CREATOR = new Creator<SignlistData>() {
        @Override
        public SignlistData createFromParcel(Parcel source) {
            return new SignlistData(source);
        }

        @Override
        public SignlistData[] newArray(int size) {
            return new SignlistData[size];
        }
    };
}
