package com.liuxiaoji.module_contacts.selectparticipant.ui;

import android.support.annotation.NonNull;

import com.liuxiaoji.module_contacts.selectparticipant.base.BaseResponse;
import com.liuxiaoji.module_contacts.selectparticipant.bean.ContactsBean;
import com.liuxiaoji.module_contacts.selectparticipant.bean.SearchContactsBean;
import com.liuxiaoji.module_contacts.selectparticipant.common.NetError;
import com.liuxiaoji.module_contacts.selectparticipant.listener.IOAServiceListener;
import com.liuxiaoji.module_contacts.selectparticipant.service.ServiceFactory;

import retrofit2.Call;

/**
 * @author liuzhe
 * @date 2018/11/28
 */
public class SelectParticipantService {

    public synchronized void getContacts(@NonNull int nodeId, String data, String startTime, String endTime, @NonNull final IContactsListener iContactsListener) {

        Call<ContactsBean> contactsResponseCall = ServiceFactory.getInstance().getiMonitorService().getContacts(nodeId, data,startTime, endTime);
        ServiceFactory.getInstance().httpRequest(contactsResponseCall, new IOAServiceListener() {

            @Override
            public <T extends BaseResponse> void onSuccess(T t, Call apiCall) {

                ContactsBean contactsBeans = (ContactsBean) t;
                iContactsListener.success(contactsBeans);
            }

            @Override
            public void onFailure(NetError netError, Call apiCall) {
                iContactsListener.failure(netError);
            }
        });


    }

    public synchronized void searchuser(String keywords, String choice_date, String startTime, String endTime, int page, @NonNull final ISearchContactsListener iSearchContactsListener) {

        Call<SearchContactsBean> searchContactsBeanCall = ServiceFactory.getInstance().getiMonitorService().searchuser(keywords, choice_date,startTime, endTime,page);
        ServiceFactory.getInstance().httpRequest(searchContactsBeanCall, new IOAServiceListener() {

            @Override
            public <T extends BaseResponse> void onSuccess(T t, Call apiCall) {

                SearchContactsBean staffsBean = (SearchContactsBean) t;
                iSearchContactsListener.success(staffsBean);
            }

            @Override
            public void onFailure(NetError netError, Call apiCall) {
                iSearchContactsListener.failure(netError);
            }
        });


    }

}
