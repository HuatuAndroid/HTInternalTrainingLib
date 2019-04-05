package com.liuxiaoji.module_contacts.selectparticipant.ui;

import com.liuxiaoji.module_contacts.selectparticipant.bean.ContactsBean;
import com.liuxiaoji.module_contacts.selectparticipant.common.NetError;

/**
 * @author liuzhe
 * @date 2018/11/28
 */
public interface IContactsListener {

    void success(ContactsBean followDataItemList);

    void failure(NetError netError);

}
