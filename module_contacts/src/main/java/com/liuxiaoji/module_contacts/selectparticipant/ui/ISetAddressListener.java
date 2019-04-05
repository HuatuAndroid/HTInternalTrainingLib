package com.liuxiaoji.module_contacts.selectparticipant.ui;

import com.liuxiaoji.module_contacts.selectparticipant.base.BaseResponse;
import com.liuxiaoji.module_contacts.selectparticipant.common.NetError;

/**
 * @author liuzhe
 * @date 2018/11/28
 */
public interface ISetAddressListener {

    void success(BaseResponse baseResponse);

    void failure(NetError netError);

}
