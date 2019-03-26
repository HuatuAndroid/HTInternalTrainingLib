package com.example.module_employees_world.common;

/**
 * @author liuzhe
 * @date 2019/3/22
 */
public class CommonUtils {

    public static boolean compareStr(String str1,String str2){
        if(str1 != null){
            return str1.equals(str2);
        }else{
            return str1 == str2;
        }
    }

}
