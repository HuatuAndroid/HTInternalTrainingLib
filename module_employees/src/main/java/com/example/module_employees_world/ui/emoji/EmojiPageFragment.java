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
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.module_employees_world.adapter.EmojiGridAdapter;
import com.example.module_employees_world.bean.EmojiconBean;
import com.example.module_employees_world.common.EmojiInit;
import com.example.module_employees_world.common.config.EmojiConfig;

import java.util.List;

/**
 * @author liuzhe
 * @date 2019/3/28
 */
@SuppressLint("ValidFragment")
public class EmojiPageFragment extends Fragment {
    private List<EmojiconBean> datas;
    private GridView sGrid;
    private EmojiGridAdapter adapter;
    private EmojiItemClickListener itemClickListener;

    public static EmojiPageFragment newInstance(int type, EmojiItemClickListener itemClickListener) {
        EmojiPageFragment f = new EmojiPageFragment();
        f.initData(type);
        f.itemClickListener = itemClickListener;
        return f;
    }

    private void initData(int type) {
        datas = EmojiInit.getByType(type);
        if(datas.size() > EmojiConfig.EMOJI_PER_PAGE_MAX_COUNT){
            datas = datas.subList(0,EmojiConfig.EMOJI_PER_PAGE_MAX_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        sGrid = new GridView(getActivity());
        sGrid.setNumColumns(EmojiConfig.COLUMNS);
        sGrid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        sGrid.setPadding(0,0,0,0);
        sGrid.setHorizontalSpacing(0);
        sGrid.setVerticalSpacing(0);
        sGrid.setGravity(Gravity.CENTER);
        adapter = new EmojiGridAdapter(getActivity(), datas);
        sGrid.setAdapter(adapter);

        sGrid.setOnItemClickListener((parent, view, position, id) -> {
            if (itemClickListener != null) {
                if(position == adapter.getCount()-1){
                    itemClickListener.onDeleteClick();
                }else{
                    itemClickListener.onItemClick(adapter.getItem(position));
                }
            }
        });
        sGrid.setSelector(new ColorDrawable(Color.WHITE));
        return sGrid;
    }

    public GridView getRootView() {
        return sGrid;
    }
}