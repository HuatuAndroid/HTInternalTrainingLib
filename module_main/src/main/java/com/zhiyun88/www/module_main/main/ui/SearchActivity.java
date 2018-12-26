package com.zhiyun88.www.module_main.main.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.utils.SharedPrefsUtil;
import com.wb.baselib.view.MultipleStatusView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.main.adapter.SearchAdapter;
import com.zhiyun88.www.module_main.main.adapter.UseringHistoryAdapter;
import com.zhiyun88.www.module_main.main.bean.SearchListBean;
import com.zhiyun88.www.module_main.main.mvp.contranct.SearchContranct;
import com.zhiyun88.www.module_main.main.mvp.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SearchActivity extends MvpActivity<SearchPresenter> implements SearchContranct.SearchView {
    private ListView mListView;
    private int page = 1;
    private EditText ss_et;
    private String currentT;
    private ImageView back;
    private TextView search_tv, search_clear;
    private String value;
    private MultipleStatusView multiplestatusview;
    private SmartRefreshLayout smartRefreshLayout;
    private List<SearchListBean> searchListBeans = new ArrayList<>();
    private SearchAdapter searchAdapter;
    private UseringHistoryAdapter useringHistoryAdapter;
    private List<String> list;
    private ImageView search_del;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_activity_search);
        mListView = getViewById(R.id.p_lv);
        search_tv = getViewById(R.id.seacher_tv);
        multiplestatusview = getViewById(R.id.multiplestatusview);
        ss_et = getViewById(R.id.ss_et);
        back = getViewById(R.id.back_img);
        search_del = getViewById(R.id.search_del);
        search_clear = getViewById(R.id.search_clear);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        processLogic(savedInstanceState);
    }

    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
    @Override
    protected void setListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        search_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Search();
                finish();
            }


        });
        search_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ss_et.setText("");
                try {
                    searchListBeans.clear();
                    searchAdapter.notifyDataSetChanged();
                    page=1;
                    NoData();
                }catch (Exception e){
                }
            }
        });
        ss_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                hintKeyBoard();
                    Search();
                return true;
            }
        });

        ss_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    search_del.setVisibility(View.GONE);
                    useringHistoryAdapter.setNewData(list);
                }else {
                    search_del.setVisibility(View.VISIBLE);
                    ArrayList<String> newList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).contains(s)) {
                            newList.add(list.get(i));
                        }
                    }
                    Collections.sort(newList);
                    useringHistoryAdapter.setNewData(newList);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ss_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ss_et.setHint("");
            }
        });

        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getSearchData(currentT, page);
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getSearchData(currentT, page);
            }
        });

        search_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefsUtil.clearName(AppUtils.getContext(), "history");
                history();
            }
        });
        multiplestatusview.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                multiplestatusview.showLoading();
                mPresenter.getSearchData(currentT, page);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        search_tv.setText("取消");
        RefreshUtils.getInstance(smartRefreshLayout, SearchActivity.this).defaultRefreSh();
        history();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("点击的",list.get(i)+"****"+list.size());
                ss_et.requestFocus();
                ss_et.setText(list.get(i));
                ss_et.setSelection(list.get(i).length());
                Search();
            }
        });

    }

    private void history() {
        smartRefreshLayout.setEnabled(false);
        value = SharedPrefsUtil.getValue(AppUtils.getContext(), "history", "text", "");
        if (value == null || "".equals(value)) {
            search_clear.setVisibility(View.GONE);
            list=new ArrayList<>();
        } else {
            String[] split = value.trim().split(",");
            list = Arrays.asList(split);
            search_clear.setVisibility(View.VISIBLE);
        }

        useringHistoryAdapter = new UseringHistoryAdapter(SearchActivity.this, list);
        mListView.setAdapter(useringHistoryAdapter);
    }

    private void Search() {
        currentT = ss_et.getText().toString().trim();
        if ("".equals(currentT)) {
            showErrorMsg("请输入要搜索的关键字");
        } else {
            page = 1;
            search_clear.setVisibility(View.GONE);
            mPresenter.getSearchData(currentT, page);
            ShowLoadView();
            saveHistory();
            searchAdapter = new SearchAdapter(SearchActivity.this, searchListBeans);
            mListView.setAdapter(searchAdapter);
        }
    }

    /**
     * 保存历史记录
     * 判断数据是否存在于字符串
     * 已存在,去除旧数据中已存在的数据,重新添加,将存在的数据放在首位,并保存数据
     * 不存在,将新数据添加到首位
     * 保存的字段不多于5条
     **/

    private void saveHistory() {
        value = SharedPrefsUtil.getValue(AppUtils.getContext(), "history", "text", "");
        String[] split = value.split(",");

        LinkedList<String> linkedList = new LinkedList<>();
        for (int i = 0; i < split.length; i++) {
            linkedList.addLast(split[i]);
            if (currentT.equals(split[i])) {
                linkedList.remove(i);
            }
        }
        linkedList.addFirst(currentT);
        if (linkedList.size() == 6) {
            linkedList.removeLast();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : linkedList) {
            if (stringBuilder.length() == 0) {
                stringBuilder = stringBuilder.append(word);
            } else {
                stringBuilder = stringBuilder.append(",").append(word);
            }
        }
        SharedPrefsUtil.putValue(AppUtils.getContext(), "history", "text", stringBuilder.toString());
    }

    @Override
    protected SearchPresenter onCreatePresenter() {
        return new SearchPresenter(this);
    }

    @Override
    public void loadMore(boolean isLoadMore) {
        RefreshUtils.getInstance(smartRefreshLayout, this).isLoadData(isLoadMore);
    }

    @Override
    public void SuccessData(Object o) {
        smartRefreshLayout.setEnabled(true);
        if (page == 1) {
            searchListBeans.clear();
        }
        searchListBeans.addAll((Collection<? extends SearchListBean>) o);
        searchAdapter.notifyDataSetChanged();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        multiplestatusview.showContent();
        page++;
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }


    @Override
    public void showErrorMsg(String msg) {
        showShortToast(msg);
    }

    @Override
    public void showLoadV(String msg) {
        showLoadDiaLog(msg);
    }

    @Override
    public void closeLoadV() {
        hidLoadDiaLog();
    }

    @Override
    public void ShowLoadView() {
        multiplestatusview.showLoading();
    }

    @Override
    public void NoNetWork() {
        multiplestatusview.showNoNetwork();
    }

    @Override
    public void NoData() {
        multiplestatusview.showEmpty();
    }

    @Override
    public void ErrorData() {
        multiplestatusview.showError();
    }

}
