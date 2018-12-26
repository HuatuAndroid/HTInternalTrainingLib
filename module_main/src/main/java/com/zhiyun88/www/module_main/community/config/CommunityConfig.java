package com.zhiyun88.www.module_main.community.config;


import android.widget.TextView;

import com.zhiyun88.www.module_main.task.bean.TaskData;

/**
 * 模块配置信息
 */
public interface CommunityConfig extends CommunityHttpConfig {
    interface OnItemJoinListener {
        void setJoinInfo(String id, String is_group, int position, TextView join);
    }
    interface OnItemOutListener {
        void setOutItem(String id, int position);
    }
    interface OnTypeClickListener {
        void setTypeClick(TaskData taskData);
    }
    interface OnReplyListener {
        void setReplyClick(int position);
    }
    interface OnEditChangeListener {
        void getEditChange(String content,boolean is_show);
    }
}
