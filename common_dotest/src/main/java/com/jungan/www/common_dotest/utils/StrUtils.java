package com.jungan.www.common_dotest.utils;

import android.util.Log;

import com.jungan.www.common_dotest.config.QuestionTypeConfig;

import java.util.Set;

public class StrUtils {
    private static StrUtils strUtils;
    public static StrUtils Instance(){
        synchronized (StrUtils.class){
            if(strUtils==null){
                return strUtils=new StrUtils();
            }
        }
        return strUtils;
    }

    /**
     * 将数字转化为字母
     * @param num
     * @return
     */
    public String numberToLetter(int num) {
        if (num <= 0) {
            return null;
        }
        String letter = "";
        num--;
        do {
            if (letter.length() > 0) {
                num--;
            }
            letter = ((char) (num % 26 + (int) 'A')) + letter;
            num = (int) ((num - num % 26) / 26);
        } while (num > 0);
        return letter;
    }
    //将字母转化为数字
    public int letterToNumber(String letter) {
        int length = letter.length();
        int num = 0;
        int number = 0;
        for(int i = 0; i < length; i++) {
            char ch = letter.charAt(length - i - 1);
            num = (int)(ch - 'A' + 1) ;
            num *= Math.pow(26, i);
            number += num;
        }
        return number;
    }
    public String SetToString(Set<String> set){
        StringBuffer stringBuffer=new StringBuffer();
        for (String str : set) {
           stringBuffer.append(str+",");
        }
        return stringBuffer.deleteCharAt(stringBuffer.length()-1).toString();
    }
    public String getTestTypeStr(int type){
        //单选题 2:多选题 3:不定项选择 4:判断题 5:问答题 6:填空题 7:组合题
        switch (type){
            case QuestionTypeConfig.RADIO_CHOICE:
                return "单选题";
            case QuestionTypeConfig.MULITSELECT_CHOICE:
                return "多选题";
            case QuestionTypeConfig.MULITSELECT_CHOICE_BDX:
                return "不定项选择";
            case QuestionTypeConfig.TEST_PDT:
                return "判断题";
            case QuestionTypeConfig.ESSAY_QUESTION:
                return "问答题";
            case QuestionTypeConfig.TEST_TKT:
                return "填空题";
            case QuestionTypeConfig.MATERIAL_RADIO_CHOICE:
                return "材料题";
            case QuestionTypeConfig.MATERIAL_MULITSELECT_CHOICE:
                return "材料题";
        }
        return "未知题型";
    }
}
