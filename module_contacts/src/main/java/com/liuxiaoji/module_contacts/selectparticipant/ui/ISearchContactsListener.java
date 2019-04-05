package com.liuxiaoji.module_contacts.selectparticipant.ui;

import com.liuxiaoji.module_contacts.selectparticipant.bean.SearchContactsBean;
import com.liuxiaoji.module_contacts.selectparticipant.common.NetError;

/**
 * @author liuzhe
 * @date 2018/11/28
 */
public interface ISearchContactsListener {

    void success(SearchContactsBean searchContactsBean);

    void failure(NetError netError);

}
