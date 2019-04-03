package com.example.module_employees_world.common;

import com.example.module_employees_world.R;
import com.example.module_employees_world.bean.TutuIconBean;
import com.thefinestartist.utils.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * @author liuzhe
 * @date 2019/3/28
 */
public class TutuPicInit {

    public static final List<TutuIconBean> EMOJICONS = new ArrayList<>();

    public static int pics[] = {R.drawable.pic1_01
                                ,R.drawable.pic1_02
                                ,R.drawable.pic1_03
                                ,R.drawable.pic1_04
                                ,R.drawable.pic1_05
                                ,R.drawable.pic1_06
                                ,R.drawable.pic1_07
                                ,R.drawable.pic1_08
                                ,R.drawable.pic1_09
                                ,R.drawable.pic1_10
                                ,R.drawable.pic1_11
                                ,R.drawable.pic1_12
                                ,R.drawable.pic1_13
                                ,R.drawable.pic1_14
                                ,R.drawable.pic1_15
                                ,R.drawable.pic1_16};

     static {

        try {

            EMOJICONS.add(new TutuIconBean(R.drawable.pic1_01, "[爱情]", 0));
            EMOJICONS.add(new TutuIconBean(R.drawable.pic1_02, "[比心]", 0));
            EMOJICONS.add(new TutuIconBean(R.drawable.pic1_03, "[爱情]", 0));
            EMOJICONS.add(new TutuIconBean(R.drawable.pic1_04, "[额]", 0));
            EMOJICONS.add(new TutuIconBean(R.drawable.pic1_05, "[哼]", 0));
            EMOJICONS.add(new TutuIconBean(R.drawable.pic1_06, "[惊讶]", 0));

            EMOJICONS.add(new TutuIconBean(R.drawable.pic1_07, "[萌]", 0));
            EMOJICONS.add(new TutuIconBean(R.drawable.pic1_08, "[你好]", 0));
            EMOJICONS.add(new TutuIconBean(R.drawable.pic1_09, "[投降]", 0));

            EMOJICONS.add(new TutuIconBean(R.drawable.pic1_10, "[晚安]", 0));
            EMOJICONS.add(new TutuIconBean(R.drawable.pic1_11, "[委屈]", 0));
            EMOJICONS.add(new TutuIconBean(R.drawable.pic1_12, "[压力]", 0));
            EMOJICONS.add(new TutuIconBean(R.drawable.pic1_13, "[疑问]", 0));
            EMOJICONS.add(new TutuIconBean(R.drawable.pic1_14, "[可怜]", 0));
            EMOJICONS.add(new TutuIconBean(R.drawable.pic1_15, "[重要通知]", 0));
            EMOJICONS.add(new TutuIconBean(R.drawable.pic1_16, "[佛系]", 0));

            for (int i = 0; i < pics.length; i++){
                EMOJICONS.get(i).type = i/10;
            }

        } catch (Exception e) {
            LogUtil.e(e.toString());
        }
    }

    /**
     * 所有key的集合
     * @return
     */
    public String getToStringNames(){

         String names = "";

         for (TutuIconBean tutuIconBean : EMOJICONS){
             names += tutuIconBean.key;
         }

         return names;

    }

    public static List<TutuIconBean> getData(int type){

        final List<TutuIconBean> tutuIconBeans = new ArrayList<>();

        Observable.from(EMOJICONS)
                .filter(emojicon -> type == emojicon.type).subscribe(emojicon -> tutuIconBeans.add(emojicon));

        return tutuIconBeans;
    }

    public static int getResFromEmojicList(String key){
        for (int i = 0; i < EMOJICONS.size(); i++) {
            if (EMOJICONS.get(i).key.equals(key)){
                return EMOJICONS.get(i).TutuId;
            }
        }
        return 0;
    }
}
