package com.example.seele.newht;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.baijiahulian.livecore.context.LPConstants;
import com.jungan.www.module_blackplay.activity.PBRoomActivity;
import com.wb.baselib.permissions.PerMissionsManager;
import com.wb.baselib.permissions.interfaces.PerMissionCall;
import com.wb.baselib.view.MyListView;
import com.zhiyun88.www.module_main.call.LoginStatusCall;
import com.zhiyun88.www.module_main.hApp;
import com.zzhoujay.richtext.RichText;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button toact1,toact2,toact3,toact4,toact5;
    private ListView mlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mlist=this.findViewById(R.id.mlist);
//        RichText.initCacheDir(this);
//        RichText.debugMode = true;


//        mlist.setAdapter(new Test1Adapter(MainActivity.this));
//
        toact4=this.findViewById(R.id.toact4);

        toact4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hApp.newInstance().toMainActivity(MainActivity.this, "31192", "dfsfsfds", new LoginStatusCall() {
                    @Override
                    public void LoginError(String msg, int code) {
                        Log.e("---->>",msg+code);
                    }
                });
            }
        });
//        setListViewHeightBasedOnChildren(mlist);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        try{
            // 获取ListView对应的Adapter
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }

            int totalHeight = 0;
            for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
                // listAdapter.getCount()返回数据项的数目
                View listItem = listAdapter.getView(i, null, listView);
                // 计算子项View 的宽高
                listItem.measure(0, 0);
                // 统计所有子项的总高度
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            int h=totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
                    Log.e("hhhh",h+"");
            params.height = h;
            // listView.getDividerHeight()获取子项间分隔符占用的高度
            // params.height最后得到整个ListView完整显示需要的高度
            listView.setLayoutParams(params);
        }catch (Exception e){
        }
    }
////        CrashHandler.getInstance().CheckAppCarchLog();
////        PerMissionsManager.newInstance().getUserPerMissions(MainActivity.this, new PerMissionCall() {
////            @Override
////            public void userPerMissionStatus(boolean b) {
////
////            }
////        },new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE});
//        toact1=this.findViewById(R.id.toact1);
//        toact2=this.findViewById(R.id.toact2);
//        toact3=this.findViewById(R.id.toact3);
//        toact4=this.findViewById(R.id.toact4);
//        toact5=this.findViewById(R.id.toact5);
//        toact1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                hApp.newInstance().toSchemme("htnx://course/666",MainActivity.this);
//                hApp.newInstance().toMainActivity("31192", "fdsfs", new LoginStatusCall() {
//                    @Override
//                    public void LoginError(String msg, int code) {
//
//                    }
//                },"htnx://course/186",MainActivity.this);
////                hApp.newInstance().toSchemme("htnx://course?uid=31192&id=186&token=fsafdsafsafsafas",MainActivity.this);
//            }
//        });
//        toact2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hApp.newInstance().toMainActivity("31192", "fdsfs", new LoginStatusCall() {
//                    @Override
//                    public void LoginError(String msg, int code) {
//
//                    }
//                },"htnx://peixun/180",MainActivity.this);
////                hApp.newInstance().toSchemme("htnx://peixun?uid=31192&id=180&token=fsafdsafsafsafas",MainActivity.this);
//            }
//        });
//        toact3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hApp.newInstance().toMainActivity("31192", "fdsfs", new LoginStatusCall() {
//                    @Override
//                    public void LoginError(String msg, int code) {
//
//                    }
//                },"htnx://investigation/463",MainActivity.this);
////                hApp.newInstance().toSchemme("htnx://investigation?uid=31192&id=463&token=fsafdsafsafsafas",MainActivity.this);
////                hApp.newInstance().toMainActivity(MainActivity.this, "1", "dfsfsfds", new LoginStatusCall() {
////                    @Override
////                    public void LoginError(String msg, int code) {
////                        Log.e("---->>",msg+code);
////                    }
////                });
//
//
//
//
//            }
//        });
//        toact5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                hApp.newInstance().toSchemme("htnx://task?uid=31192&id=60&token=fsafdsafsafsafas",MainActivity.this);
//                hApp.newInstance().toMainActivity("31192", "fdsfs", new LoginStatusCall() {
//                    @Override
//                    public void LoginError(String msg, int code) {
//
//                    }
//                },"htnx://task/60",MainActivity.this);
//            }
//        });
//        toact4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                initPermission();
////                Intent intent=new Intent(MainActivity.this,PBRoomActivity.class);
////                intent.putExtra("pb_room_id","18103168755745");
////                intent.putExtra("pb_room_token","PdNXy8ukfyR71zkgu7jxxR8R8F60dK4enJ6wEBmGL4z85rTfBbiskDTSEzZrILF4");
////                intent.putExtra("pb_room_session_id","-1");
////                intent.putExtra("pb_room_deploy",2);
////                startActivity(intent);
////                for (Map.Entry<String, String> entry :  HttpConfig.newInstance().getmMapHeader().entrySet()) {
////                    Log.e("baotou",entry.getKey()+"---"+entry.getValue());
////                }
//////
//                hApp.newInstance().toMainActivity(MainActivity.this, "31192", "dfsfsfds", new LoginStatusCall() {
//                    @Override
//                    public void LoginError(String msg, int code) {
//                        Log.e("---->>",msg+code);
//                    }
//                });
////                LiveSDKWithUI.LiveRoomUserModel liveRoomUserModel=new LiveSDKWithUI.LiveRoomUserModel("ffsfsf","http://www.baidu.com","88", LPConstants.LPUserType.Student);
////               LiveSDKWithUI.LiveRoomUserModel liveRoomUserModel=new LiveSDKWithUI.LiveRoomUserModel("dfsfsd","dfssfsdf","888",1);
////                LiveSDKWithUI.enterRoom(MainActivity.this, Long.parseLong("465456456456456"), "dsdaDAdADAdA", liveRoomUserModel, new LiveSDKWithUI.LiveSDKEnterRoomListener() {
////                    @Override
////                    public void onError(String s) {
////
////                    }
////                });
//            }
//        });
//    }
////    /**
////     * 初始化权限事件
////     */
////    private void initPermission() {
////        PerMissionsManager.newInstance().getUserPerMissions(this, new PerMissionCall() {
////            @Override
////            public void userPerMissionStatus(boolean b) {
////                if(b){
////                    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
////                    startActivityForResult(intent, 222);
////                }
////            }
////        },new String[]{Manifest.permission.CAMERA});
////    }
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        if (requestCode == 222) {
////            //处理扫描结果（在界面上显示）
////            if (null != data) {
////                Bundle bundle = data.getExtras();
////                if (bundle == null) {
////                    return;
////                }
////                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
////                    String result = bundle.getString(CodeUtils.RESULT_STRING);
////                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
////                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
////                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
////                }
////            }
////        }
////    }
}
