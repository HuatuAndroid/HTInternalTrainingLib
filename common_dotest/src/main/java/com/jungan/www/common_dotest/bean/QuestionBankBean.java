package com.jungan.www.common_dotest.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 这个是题型的公共模块，用于配置题相关的信息
 */
public class QuestionBankBean implements Parcelable {
    //报告ID
    private String report_id;
    //题Id
    private String questId;
    //题的类型
    private  int questionType;
    //题号
    private long questionNum;
    //题模块
    private int questionModel;
    //问题
    private String questionIssue;
    //题干
    private String questionStem;
    //选项
    private List<String> option;
    //用户定义的选项
    private List<UserOptionBean> userOption;
    //是否收藏
    private int isCollect;
    //正确答案
    private String right_answer;
    //用户选择的答案
    private String  user_answer;
    //问题难度
    private String answer_difficulty;
    //用户做题统计
    private String number_ques;
    //用户答题时间
    private String user_dotime;
    //正确率
    private String correct_rate;
    //易错项
    private String fallibility;
    //参考解析
    private String ques_analysis;
    //知识点
    private List<String> knows_name;
    private List<WdBean> wd;
    private int material_type;
    private String parent_stem;

    public String getParent_stem() {
        return parent_stem;
    }

    public void setParent_stem(String parent_stem) {
        this.parent_stem = parent_stem;
    }

    public List<WdBean> getWd() {
        return wd;
    }

    public void setWd(List<WdBean> wd) {
        this.wd = wd;
    }

    public String getQuestId() {
        return questId;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public String getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(String right_answer) {
        this.right_answer = right_answer;
    }

    public String getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }

    public String getAnswer_difficulty() {
        return answer_difficulty;
    }

    public void setAnswer_difficulty(String answer_difficulty) {
        this.answer_difficulty = answer_difficulty;
    }

    public String getNumber_ques() {
        return number_ques;
    }

    public void setNumber_ques(String number_ques) {
        this.number_ques = number_ques;
    }

    public String getUser_dotime() {
        return user_dotime;
    }

    public void setUser_dotime(String user_dotime) {
        this.user_dotime = user_dotime;
    }

    public String getCorrect_rate() {
        return correct_rate;
    }

    public void setCorrect_rate(String correct_rate) {
        this.correct_rate = correct_rate;
    }

    public String getFallibility() {
        return fallibility;
    }

    public void setFallibility(String fallibility) {
        this.fallibility = fallibility;
    }

    public String getQues_analysis() {
        return ques_analysis;
    }

    public void setQues_analysis(String ques_analysis) {
        this.ques_analysis = ques_analysis;
    }

    public List<String> getKnows_name() {
        return knows_name;
    }

    public void setKnows_name(List<String> knows_name) {
        this.knows_name = knows_name;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public long getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(long questionNum) {
        this.questionNum = questionNum;
    }

    public int getQuestionModel() {
        return questionModel;
    }

    public void setQuestionModel(int questionModel) {
        this.questionModel = questionModel;
    }

    public String getQuestionIssue() {
        return questionIssue;
    }

    public void setQuestionIssue(String questionIssue) {
        this.questionIssue = questionIssue;
    }

    public String getQuestionStem() {
        return questionStem;
    }

    public void setQuestionStem(String questionStem) {
        this.questionStem = questionStem;
    }

    public List<String> getOption() {
        return option;
    }

    public void setOption(List<String> option) {
        this.option = option;
    }

    public List<UserOptionBean> getUserOption() {
        return userOption;
    }

    public void setUserOption(List<UserOptionBean> userOption) {
        this.userOption = userOption;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public QuestionBankBean() {
    }

    public int getMaterial_type() {
        return material_type;
    }

    public void setMaterial_type(int material_type) {
        this.material_type = material_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.report_id);
        dest.writeString(this.questId);
        dest.writeInt(this.questionType);
        dest.writeLong(this.questionNum);
        dest.writeInt(this.questionModel);
        dest.writeString(this.questionIssue);
        dest.writeString(this.questionStem);
        dest.writeStringList(this.option);
        dest.writeTypedList(this.userOption);
        dest.writeInt(this.isCollect);
        dest.writeString(this.right_answer);
        dest.writeString(this.user_answer);
        dest.writeString(this.answer_difficulty);
        dest.writeString(this.number_ques);
        dest.writeString(this.user_dotime);
        dest.writeString(this.correct_rate);
        dest.writeString(this.fallibility);
        dest.writeString(this.ques_analysis);
        dest.writeStringList(this.knows_name);
        dest.writeTypedList(this.wd);
        dest.writeInt(this.material_type);
        dest.writeString(this.parent_stem);
    }

    protected QuestionBankBean(Parcel in) {
        this.report_id = in.readString();
        this.questId = in.readString();
        this.questionType = in.readInt();
        this.questionNum = in.readLong();
        this.questionModel = in.readInt();
        this.questionIssue = in.readString();
        this.questionStem = in.readString();
        this.option = in.createStringArrayList();
        this.userOption = in.createTypedArrayList(UserOptionBean.CREATOR);
        this.isCollect = in.readInt();
        this.right_answer = in.readString();
        this.user_answer = in.readString();
        this.answer_difficulty = in.readString();
        this.number_ques = in.readString();
        this.user_dotime = in.readString();
        this.correct_rate = in.readString();
        this.fallibility = in.readString();
        this.ques_analysis = in.readString();
        this.knows_name = in.createStringArrayList();
        this.wd = in.createTypedArrayList(WdBean.CREATOR);
        this.material_type = in.readInt();
        this.parent_stem = in.readString();
    }

    public static final Creator<QuestionBankBean> CREATOR = new Creator<QuestionBankBean>() {
        @Override
        public QuestionBankBean createFromParcel(Parcel source) {
            return new QuestionBankBean(source);
        }

        @Override
        public QuestionBankBean[] newArray(int size) {
            return new QuestionBankBean[size];
        }
    };
}
