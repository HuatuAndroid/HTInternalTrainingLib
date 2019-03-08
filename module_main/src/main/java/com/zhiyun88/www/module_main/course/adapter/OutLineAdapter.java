package com.zhiyun88.www.module_main.course.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.baijia.player.playback.downloader.PlaybackDownloader;
import com.baijiayun.download.DownloadManager;
import com.baijiayun.download.DownloadTask;
import com.baijiayun.download.constant.TaskStatus;
import com.jungan.www.common_down.BjyBackPlayDownManager;
import com.wb.baselib.log.LogTools;
import com.wb.baselib.permissions.PerMissionsManager;
import com.wb.baselib.permissions.interfaces.PerMissionCall;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.course.bean.ChapterBean;
import com.zhiyun88.www.module_main.course.bean.CourseChildBean;
import com.zhiyun88.www.module_main.course.call.VideoStatusCall;

import java.util.List;
public class OutLineAdapter implements ExpandableListAdapter {
    private List<ChapterBean> lists;
    private Context mContext;
    private boolean isTaskInfo;
    private int currentNum=1;
    private VideoStatusCall mCall;

    public void setmCall(VideoStatusCall mCall) {
        this.mCall = mCall;
    }

    public OutLineAdapter(List<ChapterBean> lists, Context mContext, boolean taskInfo) {
        this.lists = lists;
        this.mContext = mContext;
        this.isTaskInfo=taskInfo;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getGroupCount() {
        return lists.size();
    }

    @Override
    public int getChildrenCount(int i) {
        try {
            return lists.get(i).getChild().size();
        }catch (Exception e){
            return 0;
        }

    }

    @Override
    public Object getGroup(int i) {
        return lists.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return lists.get(i).getChild().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder holder=null;
        if(view==null){
            holder = new GroupViewHolder();
            view= LayoutInflater.from(mContext).inflate(R.layout.main_course_outgroup_item,null);
            holder.tv_group_time = (TextView) view.findViewById(R.id.tv_group_time);
            holder.num_tv=view.findViewById(R.id.num_tv);
            holder.ex_up_img=view.findViewById(R.id.ex_up_img);
            view.setTag(holder);
        }else {
            holder= (GroupViewHolder) view.getTag();
        }
        ChapterBean chapterBean= (ChapterBean) getGroup(i);
        holder.tv_group_time.setText(chapterBean.getTitle());
        holder.num_tv.setText((currentNum+i)+"");
        if(b){
            holder.ex_up_img.setImageResource(R.drawable.course_up);
        }else{
            holder.ex_up_img.setImageResource(R.drawable.course_down_img);
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
        ChildViewHolder holder = null;
        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_course_outchild_item, null);
            holder.time= (TextView) convertView.findViewById(R.id.time);
            holder.course_time = (TextView) convertView.findViewById(R.id.course_time);
            holder.downVideo= (ImageView) convertView.findViewById(R.id.downVideo);
            holder.time_tv=convertView.findViewById(R.id.time_tv);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        final CourseChildBean bean= (CourseChildBean) getChild(i,i1);
        setVideoState(holder,bean);
//        if(isTaskInfo){
//            //任务详情
//            holder.course_time.setVisibility(View.GONE);
//            holder.downVideo.setVisibility(View.GONE);
//            holder.time_tv.setVisibility(View.GONE);
//            holder.time.setText(bean.getPeriods_title());
//        }else {
//            if(isTaskInfo){
////                String play_type = bean.getPlay_type();
//                setVideoState(holder,bean);
//            }else {
//                String play_type = bean.getCourse_type();
//                setTaskState(play_type,holder,bean);
//            }
//            setVideoState(holder,bean);
//            try {

//            }catch (Exception e){
//                e.printStackTrace();
//            }
            holder.downVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if(mCall==null)
                    return;
                mCall.getVideoCall(bean);
                }
            });
//        }

        return  convertView;
    }
    private void setVideoState(ChildViewHolder holder,CourseChildBean courseChildBean){
        DownloadManager downloadManager= BjyBackPlayDownManager.Instance().getDownloadManager();
        List<DownloadTask> downloadTaskLists=downloadManager.getAllTasks();
        boolean isDown=false;
        try {
            for(DownloadTask downloadTask:downloadTaskLists){
                if(downloadTask.getVideoDownloadInfo().roomId==Long.parseLong(courseChildBean.getVideo_id())||downloadTask.getVideoDownloadInfo().videoId==Long.parseLong(courseChildBean.getVideo_id())){
                    isDown=true;
                    break;
                }
            }
        }catch (Exception e){
            isDown=false;
        }
        String stateDes="";
        if(courseChildBean.getCourse_type().equals("1")){
            //直播

            holder.downVideo.setVisibility(View.GONE);
            if(courseChildBean.getPlay_type().equals("1")){
                //未开始
                holder.course_time.setBackgroundResource(R.drawable.main_courseinfo_live_no_bg);
                holder.course_time.setTextColor(mContext.getResources().getColor(R.color.qhs));
                holder.course_time.setText("未开始");
                holder.downVideo.setVisibility(View.GONE);
                stateDes="[直播] ";
            }else if(courseChildBean.getPlay_type().equals("2")){
                //进行中
                holder.course_time.setBackgroundResource(R.drawable.main_courseinfo_live_bg);
                holder.course_time.setTextColor(mContext.getResources().getColor(R.color.red));
                holder.course_time.setText("正在直播");
                stateDes="[直播] ";
                holder.downVideo.setVisibility(View.GONE);
            }else if(courseChildBean.getPlay_type().equals("3")){
                //回放
                holder.course_time.setBackgroundResource(R.drawable.main_courseinfo_live_no_bg);
                holder.course_time.setTextColor(mContext.getResources().getColor(R.color.qhs));
                holder.course_time.setText("已生成");

                stateDes="[回放] ";
                if(!isDown){
                    holder.downVideo.setVisibility(View.VISIBLE);
                }else {
                    holder.downVideo.setVisibility(View.GONE);
                }
            }else if(courseChildBean.getPlay_type().equals("4")){
                //回放未生成
                holder.course_time.setBackgroundResource(R.drawable.main_courseinfo_live_no_bg);
                holder.course_time.setTextColor(mContext.getResources().getColor(R.color.qhs));
                holder.course_time.setText("未生成");
                holder.downVideo.setVisibility(View.GONE);
                stateDes="[回放] ";
            }
        }else if(courseChildBean.getCourse_type().equals("2")){
            //视频
            stateDes="[录播] ";
            holder.course_time.setBackgroundResource(R.drawable.main_courseinfo_live_no_bg);
            holder.course_time.setTextColor(mContext.getResources().getColor(R.color.qhs));
            holder.course_time.setVisibility(View.GONE);
            if(!isDown){
                holder.downVideo.setVisibility(View.VISIBLE);
            }else {
                holder.downVideo.setVisibility(View.GONE);
            }
        }else if(courseChildBean.getCourse_type().equals("3")){
            //音频
            stateDes="[音频] ";
            if(!isDown){
                holder.downVideo.setVisibility(View.VISIBLE);
            }else {
                holder.downVideo.setVisibility(View.GONE);
            }
            holder.course_time.setBackgroundResource(R.drawable.main_courseinfo_live_no_bg);
            holder.course_time.setTextColor(mContext.getResources().getColor(R.color.qhs));
            holder.course_time.setVisibility(View.GONE);
        }else if(courseChildBean.getCourse_type().equals("4")){
            //线下培训
            stateDes="[培训] ";
            holder.downVideo.setVisibility(View.GONE);
            holder.course_time.setBackgroundResource(R.drawable.main_courseinfo_live_no_bg);
            holder.course_time.setTextColor(mContext.getResources().getColor(R.color.qhs));
            holder.course_time.setVisibility(View.GONE);
        }
        if(courseChildBean.getStart_end_date()==null||courseChildBean.getStart_end_date().equals("")){
            holder.time_tv.setVisibility(View.GONE);
        }else {
            holder.time_tv.setVisibility(View.VISIBLE);
            holder.time_tv.setText(courseChildBean.getStart_end_date());
        }

        holder.time.setText(stateDes+courseChildBean.getPeriods_title());

//        if(videoState.equals("1")){
//            stateDes="[直播] ";
//            holder.course_time.setText("未开始");
//            holder.downVideo.setVisibility(View.GONE);
//            holder.course_time.setBackgroundResource(R.drawable.main_courseinfo_live_no_bg);
//            holder.course_time.setTextColor(mContext.getColor(R.color.qhs));
//        }else if(videoState.equals("2")){
//            stateDes="[直播] ";
//            holder.course_time.setText("正在直播");
//            holder.downVideo.setVisibility(View.GONE);
//            holder.course_time.setBackgroundResource(R.drawable.main_courseinfo_live_bg);
//            holder.course_time.setTextColor(mContext.getColor(R.color.red));
//        }else if(videoState.equals("3")){
//            holder.course_time.setText("回放");
//            holder.downVideo.setVisibility(View.VISIBLE);
//            holder.course_time.setBackgroundResource(R.drawable.main_courseinfo_live_no_bg);
//            stateDes="[回放] ";
//            holder.course_time.setTextColor(mContext.getColor(R.color.qhs));
//        }else if(videoState.equals("4")){
//            holder.course_time.setText("回放未生成");
//            holder.course_time.setBackgroundResource(R.drawable.main_courseinfo_live_no_bg);
//            stateDes="[回放] ";
//            holder.downVideo.setVisibility(View.VISIBLE);
//            holder.course_time.setTextColor(mContext.getColor(R.color.qhs));
//        }

    }

    private void setTaskState(String videoState,ChildViewHolder holder,CourseChildBean courseChildBean){
//        String stateDes="";
//        if(videoState.equals("1")){
//            stateDes="[直播] ";
//            holder.course_time.setText("未开始");
//            holder.downVideo.setVisibility(View.GONE);
//            holder.course_time.setBackgroundResource(R.drawable.main_courseinfo_live_no_bg);
//            holder.course_time.setTextColor(mContext.getColor(R.color.qhs));
//        }else if(videoState.equals("2")){
//            stateDes="[直播] ";
//            holder.course_time.setText("正在直播");
//            holder.downVideo.setVisibility(View.GONE);
//            holder.course_time.setBackgroundResource(R.drawable.main_courseinfo_live_bg);
//            holder.course_time.setTextColor(mContext.getColor(R.color.red));
//        }else if(videoState.equals("3")){
//            holder.course_time.setText("回放");
//            holder.downVideo.setVisibility(View.VISIBLE);
//            holder.course_time.setBackgroundResource(R.drawable.main_courseinfo_live_no_bg);
//            stateDes="[回放] ";
//            holder.course_time.setTextColor(mContext.getColor(R.color.qhs));
//        }else if(videoState.equals("4")){
//            holder.course_time.setText("回放未生成");
//            holder.course_time.setBackgroundResource(R.drawable.main_courseinfo_live_no_bg);
//            stateDes="[回放] ";
//            holder.downVideo.setVisibility(View.VISIBLE);
//            holder.course_time.setTextColor(mContext.getColor(R.color.qhs));
//        }
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int i) {

    }

    @Override
    public void onGroupCollapsed(int i) {

    }

    @Override
    public long getCombinedChildId(long l, long l1) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long l) {
        return 0;
    }
    class GroupViewHolder {
        TextView tv_group_time,num_tv;
        ImageView ex_up_img;
    }

    class ChildViewHolder {
        ImageView img,downVideo;
        TextView course_time,time,time_tv;
    }
}
