package com.example.module_employees_world.bean;

import java.io.Serializable;

/**
 * 创建人：王晓凤
 * 创建时间：2019/4/2 3:49 PM
 */
public class GuideBean implements Serializable {

    /**
     * id : 4
     * img : http://test-px.huatu.com/uploads/images/20181129/9552a2232a66c9e4aecc0495b2a8ce08.jpg
     * created_at : 2019-03-25 18:07:55
     * updated_at : null
     * title : 测试广告02
     * content : 测试广告
     * link : https://www.baidu.com
     * status : 0
     * show_type : 1
     * show_data : 10
     */

    private int id;
    private String img;
    private String created_at;
    private Object updated_at;
    private String title;
    private String content;
    private String link;
    private int status;
    private int show_type;
    private int show_data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Object getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Object updated_at) {
        this.updated_at = updated_at;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getShow_type() {
        return show_type;
    }

    public void setShow_type(int show_type) {
        this.show_type = show_type;
    }

    public int getShow_data() {
        return show_data;
    }

    public void setShow_data(int show_data) {
        this.show_data = show_data;
    }
}
