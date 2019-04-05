package com.liuxiaoji.module_contacts.selectparticipant.base;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class BaseResponse implements Serializable {

    @SerializedName("code")
    public int code;
    @SerializedName("msg")
    public String msg;
}
