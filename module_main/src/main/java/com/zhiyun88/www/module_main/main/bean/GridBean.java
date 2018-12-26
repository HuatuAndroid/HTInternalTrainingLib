package com.zhiyun88.www.module_main.main.bean;

public class GridBean {
    private int imagePath;
    private String text;

    public GridBean(int imagePath, String text) {
        this.imagePath = imagePath;
        this.text = text;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
