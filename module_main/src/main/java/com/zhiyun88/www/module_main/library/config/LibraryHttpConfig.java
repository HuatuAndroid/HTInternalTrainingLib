package com.zhiyun88.www.module_main.library.config;


/**
 * 接口api
 */
public interface LibraryHttpConfig {
    //文库分类
    String LIBRARYTYPE = "api/app/library/classify";
    //文库列表
    String LIBRARYDATA = "api/app/library";
    //取消,收藏
    String LIBRARYCOLLECTION = "api/app/librarycollection";
    //资讯列表
   // String INFORMATIONINFO = "api/app/informationInfo";

    // 详情（为了获取文件地址）
    String LIBRARY_DETAILS = "api/app/libraryInfo/library_id={library_id}";
}
