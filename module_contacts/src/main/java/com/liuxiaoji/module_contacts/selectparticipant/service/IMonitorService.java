package com.liuxiaoji.module_contacts.selectparticipant.service;

import com.liuxiaoji.module_contacts.selectparticipant.bean.ContactsBean;
import com.liuxiaoji.module_contacts.selectparticipant.bean.SearchContactsBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public interface IMonitorService {

    /**
     * 场所预订-预订会议室时选择的联系人
     */
    @GET("api/v1/site/f_book/contacts")
    Call<ContactsBean> getContacts(@Query("nodes_id") int nodes_id,
                                   @Query("choice_date") String choice_date,
                                   @Query("start_time") String start_time,
                                   @Query("end_time") String end_time);

    /**
     * 选择常驻地后，保存到后端，后续会在token中返回
     *
     * @param keywords
     * @param choice_date
     * @param start_time
     * @param end_time
     * @param page
     * @return
     */
    @POST("api/v1/site/f_book/searchuser")
    Call<SearchContactsBean> searchuser(@Query("keywords") String keywords,
                                        @Query("choice_date") String choice_date,
                                        @Query("start_time") String start_time,
                                        @Query("end_time") String end_time,
                                        @Query("page") int page);

}
