package com.liuxiaoji.module_contacts.selectparticipant.ui;

import com.liuxiaoji.module_contacts.selectparticipant.bean.ContactsBean;

/**
 * @author liuzhe
 * @date 2018/11/29
 */
public class ContactContract {

    interface View {
        void getDataSuccess(ContactsBean contactsBean, int nodeId);

        void getDataFailure(String errorMsg);

    }

    interface Presenter {

        void getContacts(int nodes_id, String choice_date);
    }

}
