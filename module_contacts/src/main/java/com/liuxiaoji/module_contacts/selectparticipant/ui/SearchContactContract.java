package com.liuxiaoji.module_contacts.selectparticipant.ui;

import com.liuxiaoji.module_contacts.selectparticipant.bean.SearchContactsBean;

/**
 * @author liuzhe
 * @date 2018/12/3
 *
 * 搜索联系人
 */
public class SearchContactContract {
    interface View {
        void getDataSuccess(SearchContactsBean searchContactsBean);

        void getDataFailure(String errorMsg);

    }

    interface Presenter {

        void searchuser(String keywords, String choice_date, String startTime, String endTime, int page);
    }
}
