package com.liuxiaoji.module_contacts.selectparticipant.ui;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.liuxiaoji.module_contacts.selectparticipant.base.BasePresenter;
import com.liuxiaoji.module_contacts.selectparticipant.bean.SearchContactsBean;
import com.liuxiaoji.module_contacts.selectparticipant.common.NetError;


/**
 * @author liuzhe
 * @date 2018/12/3
 *
 * 搜索联系人
 */
public class SearchContactContractPresenter extends BasePresenter<SearchContactContract.View> implements SearchContactContract.Presenter {

    private SelectParticipantService selectParticipantService;

    public SearchContactContractPresenter(@NonNull Activity activity) {
        super(activity);

        selectParticipantService = new SelectParticipantService();
    }

    @Override
    public void searchuser(String keywords, String choice_date, String startTime, String endTime, int page) {
        if (selectParticipantService != null) {

            selectParticipantService.searchuser(keywords, choice_date, startTime, endTime, page, new ISearchContactsListener() {
                @Override
                public void success(SearchContactsBean searchContactsBean) {
                    mView.getDataSuccess(searchContactsBean);
                }

                @Override
                public void failure(NetError netError) {
                    mView.getDataFailure(netError.toString());
                }
            });
        }
    }
}
