package com.zhiyun88.www.module_main.course.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.baijiahulian.live.ui.LiveSDKWithUI;
import com.baijiayun.download.DownloadManager;
import com.baijiayun.download.DownloadTask;
import com.baijiayun.livecore.context.LPConstants;
import com.baijiayun.videoplayer.ui.activity.PBRoomActivity;
import com.baijiayun.videoplayer.ui.activity.VideoPlayActivity;
import com.jungan.www.common_down.BjyBackPlayDownManager;
import com.jungan.www.common_down.BjyPlayDownManager;
import com.jungan.www.common_down.bean.PlayDownConfig;
import com.jungan.www.common_down.config.BjyPlayDownConfig;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.fragment.MvpFragment;
import com.wb.baselib.log.LogTools;
import com.wb.baselib.permissions.PerMissionsManager;
import com.wb.baselib.permissions.interfaces.PerMissionCall;
import com.wb.baselib.user.AppLoginUserInfoUtils;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.course.adapter.OutLineAdapter;
import com.zhiyun88.www.module_main.course.bean.BjyTokenBean;
import com.zhiyun88.www.module_main.course.bean.ChapterBean;
import com.zhiyun88.www.module_main.course.bean.CourseChildBean;
import com.zhiyun88.www.module_main.course.call.VideoStatusCall;
import com.zhiyun88.www.module_main.course.mvp.contranct.BjyTokenContranct;
import com.zhiyun88.www.module_main.course.mvp.presenter.BjyTokenPresenter;
import com.zhiyun88.www.module_main.course.view.MyExpandableListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CourseOutFragment extends MvpFragment<BjyTokenPresenter> implements BjyTokenContranct.BjyTokenView{
    private MyExpandableListView myExpandableListView;
    private List<ChapterBean> courseInfoChildData;
    private OutLineAdapter mAdapter;
    private boolean isTaskInfo;
    private String  courseName;
    private boolean isBuy;
    public static CourseOutFragment newInstcace(List<ChapterBean> courseInfoChildData,boolean isTaskInfo,String courseName,boolean isbu){
        CourseOutFragment courseOutFragment=new CourseOutFragment();
        Bundle bundle=new Bundle();
        bundle.putString("courseName",courseName);
        bundle.putParcelableArrayList("courseInfoChildData", (ArrayList<? extends Parcelable>) courseInfoChildData);
        bundle.putBoolean("isBuy",isbu);
        bundle.putBoolean("isTaskInfo",isTaskInfo);
        courseOutFragment.setArguments(bundle);
        return courseOutFragment;
    }
    @Override
    public boolean isLazyFragment() {
        return false;
    }

    @Override
    protected BjyTokenPresenter onCreatePresenter() {
        return new BjyTokenPresenter(this);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.main_course_courseout_layout);
        new BjyPlayDownConfig.Builder().with(getActivity()).setBjyId(33177992).setFilePath(getVideoDownLoadPath()).bulider();
        courseInfoChildData=getArguments().getParcelableArrayList("courseInfoChildData");
        courseName=getArguments().getString("courseName");
        isBuy=getArguments().getBoolean("isBuy",false);
        isTaskInfo=getArguments().getBoolean("isTaskInfo",false);
        myExpandableListView=getViewById(R.id.course_exl);
        mAdapter=new OutLineAdapter(courseInfoChildData,getActivity(),isTaskInfo);
        myExpandableListView.setAdapter(mAdapter);
//        myExpandableListView.expandGroup(0);
        myExpandableListView.setIndicatorBounds(myExpandableListView.getWidth()-140, myExpandableListView.getWidth()-10);
        setListener();
    }

    @Override
    protected void setListener() {
        super.setListener();
        myExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                if(isTaskInfo){
                       // showErrorMsg("暂无请求");

                    }else {
                    ChapterBean chapterBean = courseInfoChildData.get(i);
                    CourseChildBean chapterBean1 = chapterBean.getChild().get(i1);
                    if (isBuy) {
                       boolean isDown=false;
                        DownloadManager downloadManager= BjyBackPlayDownManager.Instance().getDownloadManager();
                        List<DownloadTask> downloadTaskLists=downloadManager.getAllTasks();
                        DownloadTask downloadTask=null;
                        try {
                            for(DownloadTask downloadTask1:downloadTaskLists){
                                if(downloadTask1.getVideoDownloadInfo().roomId==Long.parseLong(chapterBean1.getVideo_id())){
                                    isDown=true;
                                    downloadTask=downloadTask1;
                                    break;
                                }
                            }
                            if(isDown){
                                //已下载
                                if(chapterBean1.getCourse_type().equals("1")){
                                    //直播

                                    if(chapterBean1.getPlay_type().equals("1")){
                                        //未开始
                                    }else if(chapterBean1.getPlay_type().equals("2")){
                                        //进行中
                                    }else if(chapterBean1.getPlay_type().equals("3")){
                                        //回放
                                        String sign= downloadTask.getVideoDownloadInfo().targetFolder+"/"+BjyBackPlayDownManager.Instance().getPlayBackSig(downloadTask.getVideoDownloadInfo().roomId,downloadTask.getVideoDownloadInfo().sessionId);
//                                        Intent intent=new Intent(getActivity(), PBRoomActivity.class);
                                        Intent intent=new Intent(getActivity(), PBRoomActivity.class);
                                        intent.putExtra("pb_room_id",downloadTask.getVideoDownloadInfo().roomId+"");
                                        intent.putExtra("pb_room_video_file_path",downloadTask.getVideoDownloadInfo().targetFolder+"/"+downloadTask.getVideoDownloadInfo().targetName);
                                        intent.putExtra("pb_room_signal_file_path",sign);
                                        startActivity(intent);
                                    }else if(chapterBean1.getPlay_type().equals("4")){
                                        //回放未生成
                                    }
                                }else if(chapterBean1.getCourse_type().equals("2")){
                                    //视频
                                    Intent intent=new Intent(getActivity(), VideoPlayActivity.class);
                                    intent.putExtra("isOnLine","2");
                                    intent.putExtra("Filepath",downloadTask.getVideoDownloadInfo().targetFolder+downloadTask.getVideoDownloadInfo().targetName);
                                    intent.putExtra("Token",downloadTask.getVideoDownloadInfo().targetName);
                                    intent.putExtra("bjyId","33197");
                                    startActivity(intent);
                                }else if(chapterBean1.getCourse_type().equals("3")){
                                    //音频
                                    Intent intent=new Intent(getActivity(), VideoPlayActivity.class);
                                    intent.putExtra("isOnLine","2");
                                    intent.putExtra("Filepath",downloadTask.getVideoDownloadInfo().targetFolder+downloadTask.getVideoDownloadInfo().targetName);
                                    intent.putExtra("Token",downloadTask.getVideoDownloadInfo().targetName);
                                    intent.putExtra("bjyId","33197");
                                    startActivity(intent);
                                }else if(chapterBean1.getCourse_type().equals("4")){
                                    //线下培训

                                }


                            }else {
                                //未下载
                                mPresenter.getBjyToken(chapterBean1.getVideo_id(), false, chapterBean1);
                            }
                        }catch (Exception e){
                            mPresenter.getBjyToken(chapterBean1.getVideo_id(), false, chapterBean1);
                        }



                    } else {
                        showErrorMsg("请先加入学习");
                    }
                }
                return true;
            }
        });
        mAdapter.setmCall(new VideoStatusCall() {
            @Override
            public void getVideoCall(CourseChildBean courseInfoChildData) {
                if(isTaskInfo){
                    // showErrorMsg("暂无请求");
                }else {
                    if(isBuy){
                        mPresenter.getBjyToken(courseInfoChildData.getVideo_id(),true,courseInfoChildData);
                    }else {
                        showErrorMsg("请先加入学习");
                    }
                }
            }
        });
    }
    public  String getVideoDownLoadPath(){
        String mCacheDirPath=null;
        File cacheDir = getActivity().getExternalFilesDir("VideoFiles");
        if(null != cacheDir){
            mCacheDirPath = cacheDir.getAbsolutePath() ;
        }
        if(TextUtils.isEmpty(mCacheDirPath)){
            if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
                mCacheDirPath = Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.zhiyun88.www/cache/VideoFiles/";
            }
        }
        return mCacheDirPath;
    }
    @Override
    public void showErrorMsg(String msg) {
            showLongToast(msg);
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
    public void SuccessData(Object o) {

    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }
    @Override
    public void SuccessBjyToken(final BjyTokenBean bjyTokenData, boolean isDown, final CourseChildBean courseInfoChildData) {
        if(isDown){
            if(bjyTokenData.getType().equals("1")){
                showErrorMsg("直播课程，不支持下载");
            }else if(bjyTokenData.getType().equals("2")||bjyTokenData.getType().equals("3")){
            //视频

                PerMissionsManager.newInstance().getUserPerMissions(getActivity(), new PerMissionCall() {
                    @Override
                    public void userPerMissionStatus(boolean is) {
                        PlayDownConfig playDownConfig=new PlayDownConfig.Bulider()
                                .setCourerName(courseName)
                                .setEncryptType(0)
                                .setFileName(courseInfoChildData.getPeriods_title())
                                .setOccName("华图视频")
                                .setSectionName(courseInfoChildData.getChapter_title())
                                .setSessionId("0")
                                .setToken(bjyTokenData.getToken())
                                .setuId(Long.parseLong(AppLoginUserInfoUtils.getInstance().getUserLoginInfo().getUid()))
                                .setVideoId(Long.parseLong(bjyTokenData.getVideo_id()))
                                .builder();
                        try {
                            BjyPlayDownManager.Instance().startDown(playDownConfig);
                            Toast.makeText(getActivity(),"已加入课程缓存中....",Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        PlayDownConfig playDownConfig=new PlayDownConfig.Bulider()
//                                .setCourerName(courseName)
//                                .setEncryptType(0)
//                                .setFileName(courseInfoChildData.getPeriods_title())
//                                .setOccName("下载完成")
//                                .setSectionName(courseInfoChildData.getChapter_title())
//                                .setSessionId("0")
//                                .setToken(bjyTokenData.getToken())
//                                .setuId(Long.parseLong(AppLoginUserInfoUtils.getInstance().getUserLoginInfo().getUid()))
//                                .setVideoId(Long.parseLong(bjyTokenData.getVideo_id()))
//                                .builder();
//                        try {
//                            BjyBackPlayDownManager.Instance().startDown(playDownConfig);
//                            Toast.makeText(getActivity(),"已加入课程缓存中....",Toast.LENGTH_LONG).show();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }
                },new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE});
            }else if(bjyTokenData.getType().equals("4")){
                //回放
                PlayDownConfig playDownConfig=new PlayDownConfig.Bulider()
                        .setCourerName(courseName)
                        .setEncryptType(0)
                        .setFileName(courseInfoChildData.getPeriods_title())
                        .setOccName("华图视频")
                        .setSectionName(courseInfoChildData.getChapter_title())
                        .setSessionId("0")
                        .setToken(bjyTokenData.getToken())
                        .setuId(Long.parseLong(AppLoginUserInfoUtils.getInstance().getUserLoginInfo().getUid()))
                        .setVideoId(Long.parseLong(bjyTokenData.getVideo_id()))
                        .builder();
                try {
                    BjyBackPlayDownManager.Instance().startDown(playDownConfig);
                    Toast.makeText(getActivity(),"已加入课程缓存中....",Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }else {
            if(bjyTokenData.getType().equals("1")){
                //直播
                LiveSDKWithUI.LiveRoomUserModel liveRoomUserModel=new LiveSDKWithUI.LiveRoomUserModel(bjyTokenData.getName(),bjyTokenData.getAvatar(),bjyTokenData.getUser_id(), LPConstants.LPUserType.Student);
                LiveSDKWithUI.enterRoom(getActivity(), Long.parseLong(bjyTokenData.getVideo_id()), bjyTokenData.getToken(), liveRoomUserModel, new LiveSDKWithUI.LiveSDKEnterRoomListener() {
                    @Override
                    public void onError(String s) {
                        
                    }
                });
            }else if(bjyTokenData.getType().equals("2")){
                //点播
//                ARouter.getInstance().build("/backplay/play").with(bundle).navigation();
                Intent intent=new Intent(getActivity(), VideoPlayActivity.class);
                intent.putExtra("videoId",Long.parseLong(bjyTokenData.getVideo_id()));
                intent.putExtra("Token",bjyTokenData.getToken());
                intent.putExtra("isOnLine","1");
                intent.putExtra("bjyId",bjyTokenData.getUser_id());
                startActivity(intent);
            }else if(bjyTokenData.getType().equals("3")){
                //音频
                Intent intent=new Intent(getActivity(), VideoPlayActivity.class);
                intent.putExtra("videoId",Long.parseLong(bjyTokenData.getVideo_id()));
                intent.putExtra("Token",bjyTokenData.getToken());
                intent.putExtra("isOnLine","1");
                intent.putExtra("bjyId",bjyTokenData.getUser_id());
                startActivity(intent);
            }else {
                //回放
                Intent intent=new Intent(getActivity(),PBRoomActivity.class);
                intent.putExtra("pb_room_id",bjyTokenData.getVideo_id()+"");
                intent.putExtra("pb_room_token",bjyTokenData.getToken());
                intent.putExtra("pb_room_session_id","-1");
                intent.putExtra("pb_room_deploy",2);
                startActivity(intent);
            }
        }
    }
    public void setisBuy(boolean is){
        LogTools.e("购买是否成功"+is);
        this.isBuy=is;
    }
}
