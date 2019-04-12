package com.example.module_employees_world.bean;

import java.io.Serializable;

/**
 * @author liuzhe
 * @date 2019/3/28
 */
public class TutuIconBean implements Serializable {

    public int TutuId;
    public String key;
    public int type;

    public TutuIconBean(int TutuId, String key, int type) {
        this.TutuId = TutuId;
        this.key = key;
        this.type = type;
    }

}
