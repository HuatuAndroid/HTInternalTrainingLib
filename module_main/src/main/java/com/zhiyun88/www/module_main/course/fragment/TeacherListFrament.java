package com.zhiyun88.www.module_main.course.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;

import com.wb.baselib.base.fragment.LazyFragment;
import com.wb.baselib.view.MyListView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.course.adapter.TeacherListAdapter;
import com.zhiyun88.www.module_main.course.bean.TeacherData;

import java.util.ArrayList;
import java.util.List;

public class TeacherListFrament extends LazyFragment {
    @Override
    public boolean isLazyFragment() {
        return true;
    }
    private MyListView course_lm;
    private TeacherListAdapter adapter;
    private List<TeacherData> teacherDataLists;
    public static TeacherListFrament newInstance(List<TeacherData> teacherData){
        Bundle bundle=new Bundle();
        bundle.putParcelableArrayList("teacherdata", (ArrayList<? extends Parcelable>) teacherData);
        TeacherListFrament teacherListFrament=new TeacherListFrament();
        teacherListFrament.setArguments(bundle);
        return teacherListFrament;
    }
    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.main_course_teacher_layout);
        course_lm=getViewById(R.id.course_lm);
        teacherDataLists=new ArrayList<>();
        teacherDataLists=getArguments().getParcelableArrayList("teacherdata");
        if(teacherDataLists==null){
            teacherDataLists=new ArrayList<>();
        }
        adapter=new TeacherListAdapter(teacherDataLists,getActivity());
        course_lm.setAdapter(adapter);
    }
}
