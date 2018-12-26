package com.jungan.www.common_dotest.bean;

import java.io.Serializable;
import java.util.List;

public class UserPostBean implements Serializable{
   private String report_id;
   private List<UserPostData> answer_data;
   private String uid;

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public List<UserPostData> getAnswer_data() {
        return answer_data;
    }

    public void setAnswer_data(List<UserPostData> answer_data) {
        this.answer_data = answer_data;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
