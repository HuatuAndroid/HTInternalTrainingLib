package com.example.module_employees_world.bean;

import java.io.Serializable;

/**
 * @author liuzhe
 * @date 2019/3/28
 */
public class EmojiconBean implements Serializable {

    public String emojiChart;
    public int type;

    public EmojiconBean(String emojiChart, int type) {
        this.emojiChart = emojiChart;
        this.type = type;
    }

}
