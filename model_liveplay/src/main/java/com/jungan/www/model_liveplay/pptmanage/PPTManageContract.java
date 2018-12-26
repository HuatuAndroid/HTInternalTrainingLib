package com.jungan.www.model_liveplay.pptmanage;


import java.util.List;

import com.jungan.www.model_liveplay.base.BasePresenter;
import com.jungan.www.model_liveplay.base.BaseView;

/**
 * Created by Shubo on 2017/4/26.
 */

interface PPTManageContract {

    interface View extends BaseView<Presenter> {
        void showPPTEmpty();

        void showPPTNotEmpty();

        void notifyDataChange();

        void notifyItemRemoved(int position);

        void notifyItemChanged(int position);

        void notifyItemInserted(int position);

        void showRemoveBtnEnable();

        void showRemoveBtnDisable();
    }

    interface Presenter extends BasePresenter {
        int getCount();

        IDocumentModel getItem(int position);

        void uploadNewPics(List<String> picsPath);

        void selectItem(int position);

        void deselectItem(int position);

        boolean isItemSelected(int position);

        void removeSelectedItems();

        boolean isDocumentAdded(int position);

        void attachView(View view);

        void detachView();
    }
}
