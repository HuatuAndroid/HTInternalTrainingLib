package com.liuxiaoji.module_contacts.selectparticipant.ui;

import com.liuxiaoji.module_contacts.selectparticipant.bean.ContactsBean;

/**
 * @author liuzhe
 * @date 2018/11/20
 *
 * 我的预定
 */
public class SelectParticipantContract {
    interface View {
        void getDataSuccess(ContactsBean contactsBean, int nodeId);

        void getDataFailure(String errorMsg);

    }

    interface Presenter {

        void getContacts(int nodes_id, String choice_date, String startTime, String endTime);
    }
}
