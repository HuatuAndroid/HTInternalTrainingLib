package com.jungan.www.common_dotest.fragment;

import android.os.Bundle;

import com.jungan.www.common_dotest.R;
import com.jungan.www.common_dotest.base.LazyFragment;

/**
 * 错误的题类型
 */
public class ErrorQuestionTypeFragment extends LazyFragment {
    @Override
    public boolean isLazyFragment() {
        return false;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.errorquestion_layout);
    }
}
