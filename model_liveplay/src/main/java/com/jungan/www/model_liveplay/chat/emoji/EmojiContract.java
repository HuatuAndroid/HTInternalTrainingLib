package com.jungan.www.model_liveplay.chat.emoji;

import com.baijiahulian.livecore.models.imodels.IExpressionModel;

import com.jungan.www.model_liveplay.base.BasePresenter;
import com.jungan.www.model_liveplay.base.BaseView;

/**
 * Created by Shubo on 2017/5/6.
 */

interface EmojiContract {

    interface View extends BaseView<Presenter> {
        // get item count per Row
        int getSpanCount();

        int getRowCount();

    }

    interface Presenter extends BasePresenter {

        IExpressionModel getItem(int page, int position);

        int getCount(int page);

        int getPageCount();

        void onSizeChanged();

        int getPageOfCurrentFirstItem();

        void onPageSelected(int page);
    }

}
