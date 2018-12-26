package com.jungan.www.model_liveplay.ppt.quickswitchppt;

import com.baijiahulian.livecore.viewmodels.impl.LPDocListViewModel;

import java.util.List;

import com.jungan.www.model_liveplay.base.BasePresenter;
import com.jungan.www.model_liveplay.base.BaseView;

/**
 * Created by bjhl on 17/7/4.
 */
class SwitchPPTContract {
    interface View extends BaseView<Presenter> {
        void setIndex();

        void setMaxIndex(int maxIndex);

        void setType(boolean isStudent);

        void docListChanged(List<LPDocListViewModel.DocModel> docModelList);
    }

    interface Presenter extends BasePresenter {
        void setSwitchPosition(int position);
    }
}
