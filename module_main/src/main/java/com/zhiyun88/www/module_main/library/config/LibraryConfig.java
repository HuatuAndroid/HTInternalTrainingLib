package com.zhiyun88.www.module_main.library.config;


/**
 * 模块配置信息
 */
public interface LibraryConfig extends LibraryHttpConfig {
    interface OnClickCollected {
        void setCollection(String libraryId, String userId, String is_click, int position);
    }
}
