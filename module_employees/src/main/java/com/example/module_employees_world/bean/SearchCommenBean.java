package com.example.module_employees_world.bean;

/**
 * 创建人：王晓凤
 * 创建时间：2019/3/28 10:20 AM
 */
public class SearchCommenBean {

    /**
     * id : 433
     * question_id : 298
     * parent_id : 0
     * content : 1123
     * comment_rule : /433
     * like_count : 0
     * comment_picture : null
     * comment_face : null
     * reply_count : 1
     * is_anonymity : 1
     * created_id : 31192
     * created_at : 2019-03-25
     * is_del : 0
     * delete_at : null
     * solve_status : 0
     * department_name :
     * user_id : 31192
     * user_name : 匿名
     * avatar : http://test-px.huatu.com//uploads/niming.jpeg
     * allow_del : 1
     */

    private String id;
    private String question_id;
    private String parent_id;
    private String content;
    private String comment_rule;
    private int like_count;
    private Object comment_picture;
    private Object comment_face;
    private int reply_count;
    private int is_anonymity;
    private String created_id;
    private String created_at;
    private String question_title;
    private int is_del;
    private Object delete_at;
    private int solve_status;
    private String department_name;
    private String user_id;
    private String user_name;
    private String avatar;
    private String content_text;
    private String content_img;
    private String text_img;
    private int allow_del;

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
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

    public String getComment_rule() {
        return comment_rule;
    }

    public void setComment_rule(String comment_rule) {
        this.comment_rule = comment_rule;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public Object getComment_picture() {
        return comment_picture;
    }

    public void setComment_picture(Object comment_picture) {
        this.comment_picture = comment_picture;
    }

    public Object getComment_face() {
        return comment_face;
    }

    public void setComment_face(Object comment_face) {
        this.comment_face = comment_face;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public int getIs_anonymity() {
        return is_anonymity;
    }

    public void setIs_anonymity(int is_anonymity) {
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

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public Object getDelete_at() {
        return delete_at;
    }

    public void setDelete_at(Object delete_at) {
        this.delete_at = delete_at;
    }

    public int getSolve_status() {
        return solve_status;
    }

    public void setSolve_status(int solve_status) {
        this.solve_status = solve_status;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
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

    public int getAllow_del() {
        return allow_del;
    }

    public void setAllow_del(int allow_del) {
        this.allow_del = allow_del;
    }
}
