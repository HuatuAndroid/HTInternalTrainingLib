package com.example.module_employees_world.common.config;


import android.widget.TextView;

import com.example.module_employees_world.bean.TaskData;

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

        void setDeleteConmment(String commentId, int position);
    }
    interface OnEditChangeListener {
        void getEditChange(String content, boolean is_show);
    }
}
