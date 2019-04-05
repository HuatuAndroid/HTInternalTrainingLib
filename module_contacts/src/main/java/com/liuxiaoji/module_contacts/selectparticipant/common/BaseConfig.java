package com.liuxiaoji.module_contacts.selectparticipant.common;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class BaseConfig {

    // EHR baseurl
    public static String EHR_URL = "http://oa.huatu.com";
    public static String EHR_URL_PRODUCE = "http://oa.huatu.com";
    public static String EHR_URL_TEST = "http://192.168.12.174:8180";

    public static String REIMBURSE_URL = "https://exp.huatu.com";
    public static String REIMBURSE_URL_PRODUCE = "https://exp.huatu.com";
    public static String REIMBURSE_URL_TEST = "https://dev-exp.huatu.com";
    //https://dev-exp.huatu.com
    // http://10.2.46.174:8082

    public static String MONITOR_APPKEY = "yOUCIQDsw05S+DnWlNHuLc";
    public static String MONITOR_URL = "http://jk.htznbm.com";
    public static String MONITOR_URL_PRODUCE = "http://jk.htznbm.com";
    public static String MONITOR_URL_TEST = "http://jk-test.htznbm.com";

    public static boolean isProduceUrl = true;


    public static String NEIXUN_URL = "http://peixun.huatu.com/";
    public static String NEIXUN_URL_PRODUCE = "http://peixun.huatu.com/";
    public static String NEIXUN_URL_TEST = "http://test-px.huatu.com/";

    public static void setProductUrl(boolean isProduceUrl1) {
        isProduceUrl = isProduceUrl1;
        if (!isProduceUrl) {
            EHR_URL = EHR_URL_TEST;
            REIMBURSE_URL = REIMBURSE_URL_TEST;
            MONITOR_URL = MONITOR_URL_TEST;
            NEIXUN_URL = NEIXUN_URL_TEST;
        } else {
            EHR_URL = EHR_URL_PRODUCE;
            REIMBURSE_URL = REIMBURSE_URL_PRODUCE;
//            MONITOR_URL = MONITOR_URL_PRODUCE;
            MONITOR_URL = MONITOR_URL_TEST;
            NEIXUN_URL = NEIXUN_URL_PRODUCE;
        }
    }
}
