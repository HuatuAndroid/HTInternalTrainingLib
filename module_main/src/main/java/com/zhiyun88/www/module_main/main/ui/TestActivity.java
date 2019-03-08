package com.zhiyun88.www.module_main.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

//import com.jungan.www.module_blackplay.activity.PBRoomActivity;
import com.baijiayun.videoplayer.ui.activity.PBRoomActivity;
import com.baijiayun.videoplayer.ui.activity.VideoPlayActivity;
//import com.jungan.www.module_playvideo.ui.PlayVodActivity;
import com.zhiyun88.www.module_main.R;

public class TestActivity extends AppCompatActivity {
    private Button play_bt,live_bt,vice_bt,blackplay_bt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_maintest);
        play_bt=this.findViewById(R.id.play_bt);
        live_bt=this.findViewById(R.id.live_bt);
        vice_bt=this.findViewById(R.id.vice_bt);
        blackplay_bt=this.findViewById(R.id.blackplay_bt);
        play_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TestActivity.this, VideoPlayActivity.class);
                intent.putExtra("videoId",10193855L);
                intent.putExtra("token","of0OZ9zayew3S3PxCaNttQRZ3VeWzuNKDubaI3DZtppAEox40_tqYA");
                intent.putExtra("isOnLine","1");
                intent.putExtra("bjyId","33975");
                startActivity(intent);
            }
        });
        live_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        vice_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        blackplay_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TestActivity.this,PBRoomActivity.class);
                intent.putExtra("pb_room_id","17081868624899");
                intent.putExtra("pb_room_token","GqWb2ZbzIEWNcup-z1nfawQswJsIPG12KGoegVdZxYNGXzXK0HAAYw");
                intent.putExtra("pb_room_session_id","-1");
                intent.putExtra("pb_room_deploy",2);
                startActivity(intent);
            }
        });
    }
}
