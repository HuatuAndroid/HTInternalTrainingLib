package com.zhiyun88.www.module_main.library.bean;

import java.io.Serializable;

/**
 * Created by KaelLi on 2018/12/19.
 */
public class LibraryDetailsBean implements Serializable {
//    "list": {
//        "id": 5,
//                "name": "自招真题试题版及参考答案-面试",
//                "created_at": "2018-10-17 11:15:13",
//                "browse_num": 89,
//                "img": "http://test-px.huatu.com/uploads/images/20180929/d6f319b37215c0b2c8bac6cc921a9def.png",
//                "file_type": 2,
//                "is_download": 0,
//                "file_path": "http://test-px.huatu.com/uploads/files/20180928/c80a5372f583a9a1d008d2b7dc07025e.pdf",
//                "file_name": "自招面试真题试题版及参考答案.pdf",
//                "is_collection": 0,
//                "app_created_at": 1539746113,
//                "app_created_time": "2018-10-17"
//    }
    public Bean list;

    public class Bean implements Serializable {
        public int id;
        public String name;
        public String created_at;
        public int browse_num;
        public String img;
        public int file_type;
        public int is_download;
        public String file_path;
        public String file_name;
        public int is_collection;
        public long app_created_at;
        public String app_created_time;

        @Override
        public String toString() {
            return "Bean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", created_at='" + created_at + '\'' +
                    ", browse_num=" + browse_num +
                    ", img='" + img + '\'' +
                    ", file_type=" + file_type +
                    ", is_download=" + is_download +
                    ", file_path='" + file_path + '\'' +
                    ", file_name='" + file_name + '\'' +
                    ", is_collection=" + is_collection +
                    ", app_created_at=" + app_created_at +
                    ", app_created_time='" + app_created_time + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LibraryDetailsBean{" +
                "list=" + list +
                '}';
    }
}
