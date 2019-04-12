package com.example.module_employees_world.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author liuzhe
 * @date 2019/4/6
 */
public class IsBannedBean implements Serializable {

    /**
     * id : 31192
     * is_banned : 0
     */

    @SerializedName("id")
    public int id;
    @SerializedName("is_banned")
    public int isBanned;
}
