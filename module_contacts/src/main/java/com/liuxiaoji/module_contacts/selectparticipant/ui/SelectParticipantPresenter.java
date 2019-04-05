package com.liuxiaoji.module_contacts.selectparticipant.ui;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.liuxiaoji.module_contacts.selectparticipant.base.BasePresenter;
import com.liuxiaoji.module_contacts.selectparticipant.bean.ContactsBean;
import com.liuxiaoji.module_contacts.selectparticipant.common.NetError;

/**
 * @author liuzhe
 * @date 2018/11/20
 *
 * 我的预定
 */
public class SelectParticipantPresenter extends BasePresenter<SelectParticipantContract.View> implements SelectParticipantContract.Presenter {


    private SelectParticipantService selectParticipantService;

    public SelectParticipantPresenter(@NonNull Activity activity) {
        super(activity);

        selectParticipantService = new SelectParticipantService();
    }

    @Override
    public void getContacts(final int nodes_id, String choice_date, String startTime, String endTime){

        if (selectParticipantService != null){

            selectParticipantService.getContacts(nodes_id, choice_date, startTime, endTime, new IContactsListener() {
                @Override
                public void success(ContactsBean followDataItemList) {
                    mView.getDataSuccess(followDataItemList, nodes_id);
                }

                @Override
                public void failure(NetError netError) {
                    Log.i("failure", netError.toString());
                }
            });
        }

    }
}
