package com.example.module_employees_world.ui.emoji;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.module_employees_world.adapter.TutuGridAdapter;
import com.example.module_employees_world.bean.TutuIconBean;
import com.example.module_employees_world.common.TutuPicInit;
import com.example.module_employees_world.common.config.EmojiConfig;

import java.util.List;

/**
 * @author liuzhe
 * @date 2019/3/28
 */
@SuppressLint("ValidFragment")
public class TutuPageFragment extends Fragment {
    private List<TutuIconBean> datas;
    private GridView sGrid;
    private TutuGridAdapter adapter;
    private EmojiItemClickListener itemClickListener;

    public static TutuPageFragment newInstance(int type, EmojiItemClickListener itemClickListener) {
        TutuPageFragment f = new TutuPageFragment();
        f.initData(type);
        f.itemClickListener = itemClickListener;
        return f;
    }

    private void initData(int type) {
        datas = TutuPicInit.getData(type);
        if (datas.size() > 10) {
            datas = datas.subList(0, 10);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        sGrid = new GridView(getActivity());
        sGrid.setNumColumns(5);
        sGrid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        sGrid.setPadding(0, 0, 0, 0);
        sGrid.setHorizontalSpacing(0);
        sGrid.setVerticalSpacing(0);
        sGrid.setGravity(Gravity.CENTER);
        adapter = new TutuGridAdapter(getActivity(), datas);
        sGrid.setAdapter(adapter);

        sGrid.setOnItemClickListener((parent, view, position, id) -> {
            if (itemClickListener != null) {
                itemClickListener.onDeleteClick();
            }
        });
        sGrid.setSelector(new ColorDrawable(Color.WHITE));
        return sGrid;
    }

    public GridView getRootView() {
        return sGrid;
    }
}