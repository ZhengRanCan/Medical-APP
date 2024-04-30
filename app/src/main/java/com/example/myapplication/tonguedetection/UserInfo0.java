package com.example.myapplication.tonguedetection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myapplication.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfo0 extends AppCompatActivity {
    ArrayList<UserInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo0);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));

        Button button = findViewById(R.id.button2);
        button.setOnClickListener(v->{
                Intent intent1 = new Intent();
                intent1.setClass(UserInfo0.this, UserInfo1.class);
                intent1.putExtra("list", list);
                startActivity(intent1);
            });
    }

    // 注册广播接收器

    private final  BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction() != null && intent.getAction().equals("custom-event-name")) {
                Serializable serializable = intent.getSerializableExtra("list");
                if (serializable instanceof ArrayList<?>) {
                    list = (ArrayList<UserInfo>) serializable;

                    ListView listview = findViewById(R.id.listview); // 获取列表视图
                    List<Map<String, Object>> listItems = new ArrayList<>();// 创建一个list集合
                    if(list!=null){
                        for(int i=0;i<list.size();i++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("编号",  list.get(i).getInput1() );
                            map.put("姓名",  list.get(i).getInput2() );
                            map.put("性别", list.get(i).getInput3() );
                            map.put("就诊时间",  list.get(i).getInput4() );
                            map.put("就诊科室",  list.get(i).getInput5() );

                            listItems.add(map);
                        }
                    }
                    SimpleAdapter adapter = new SimpleAdapter(context, listItems,
                            R.layout.activity_userinfo, new String[]{"编号", "姓名", "性别", "就诊时间", "就诊科室"},
                            new int[]{R.id.output1, R.id.output2, R.id.output3, R.id.output4, R.id.output5});
                    listview.setAdapter(adapter);// 将适配器与ListView关联
                }
            }
        }
    };

}