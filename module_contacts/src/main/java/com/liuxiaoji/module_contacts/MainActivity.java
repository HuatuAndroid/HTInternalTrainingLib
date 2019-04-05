package com.liuxiaoji.module_contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.liuxiaoji.module_contacts.selectparticipant.bean.ContactsBean;
import com.liuxiaoji.module_contacts.selectparticipant.service.ServiceFactory;
import com.liuxiaoji.module_contacts.selectparticipant.ui.SelectParticipantActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView holloword = findViewById(R.id.holloword);

        //需要先初始化
        ServiceFactory.getInstance().createMonitorService(this);
        ServiceFactory.getInstance().createIEHRService();
        ServiceFactory.getInstance().createIReimburseService();

        holloword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectParticipantActivity.class);
                startActivityForResult(intent, SelectParticipantActivity.intentCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SelectParticipantActivity.intentCode){

            ContactsBean.DataBean.StaffsBean staffsBean = (ContactsBean.DataBean.StaffsBean) data.getSerializableExtra("staffsBean");

            Log.i("onActivityResult", "onActivityResult = " + SelectParticipantActivity.intentCode + " -- " + staffsBean.name);

        }

    }
}
