package com.example.myapplication.tonguedetection;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myapplication.R;
import com.example.myapplication.takephoto.Camera2ApiActivity;
import com.example.myapplication.takephoto.photo_album;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class UserInfo1 extends AppCompatActivity {


    // 获取当前时间
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.getDefault());

    UserInfo userInfo = new UserInfo();
    String input1;
    String input2;
    String input3;
    String input4= simpleDateFormat.format(new Date());
    String input5;

    Spinner spinner;
    ArrayAdapter<String> collegeAdapter;
    String[] collegeList = new String[]{"内科", "外科", "儿科", "妇产科", "眼科", "耳鼻喉科", "口腔科"};
    ArrayList<UserInfo> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo1);
        spinner = findViewById(R.id.input5);
        setSpinner();
        RadioButton s1 = findViewById(R.id.input3_1);
        RadioButton s2 = findViewById(R.id.input3_2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //点击按钮之后将信息传入
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {

            input1 = ((EditText) findViewById(R.id.input1)).getText().toString();
            input2 = ((EditText) findViewById(R.id.input2)).getText().toString();


            if (s1.isChecked()) {
                input3 = s1.getText().toString();
            } else if (s2.isChecked()) {
                input3 = s2.getText().toString();
            }

            if (!"".equals(input1) && !"".equals(input2)&&input5 != null && !"".equals(input5)) {
                userInfo.setInput1(input1);
                userInfo.setInput2(input2);
                userInfo.setInput3(input3);
                userInfo.setInput4(input4);
                userInfo.setInput5(input5);

                Serializable serializable = getIntent().getSerializableExtra("list");
                if (serializable instanceof ArrayList<?>) {
                    list = (ArrayList<UserInfo>) serializable;
                }
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(userInfo);

                //发送广播，同步数据
                Intent intent = new Intent("custom-event-name");
                intent.putExtra("list", (Serializable)list);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

                //切换到拍照界面
                Intent intent1 = new Intent(UserInfo1.this, Camera2ApiActivity.class);
                startActivity(intent1);
            } else {
                Toast.makeText(UserInfo1.this, "请填写完整！！！", Toast.LENGTH_SHORT).show();
            }



        });

        Button button_photo = findViewById(R.id.button3);
        button_photo.setOnClickListener(v->{
            input1 = ((EditText) findViewById(R.id.input1)).getText().toString();
            input2 = ((EditText) findViewById(R.id.input2)).getText().toString();


            if (s1.isChecked()) {
                input3 = s1.getText().toString();
            } else if (s2.isChecked()) {
                input3 = s2.getText().toString();
            }

            if (!"".equals(input1) && !"".equals(input2)&&input5 != null && !"".equals(input5)) {
                userInfo.setInput1(input1);
                userInfo.setInput2(input2);
                userInfo.setInput3(input3);
                userInfo.setInput4(input4);
                userInfo.setInput5(input5);

                Serializable serializable = getIntent().getSerializableExtra("list");
                if (serializable instanceof ArrayList<?>) {
                    list = (ArrayList<UserInfo>) serializable;
                }
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(userInfo);

                //发送广播，同步数据
                Intent intent2 = new Intent("custom-event-name");
                intent2.putExtra("list", (Serializable)list);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent2);

                //切换到拍照界面
                Intent intent3 = new Intent(UserInfo1.this, photo_album.class);
                startActivity(intent3);
            } else {
                Toast.makeText(UserInfo1.this, "请填写完整！！！", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void setSpinner() {
        // 创建适配器并设置数据源
        collegeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, collegeList);
        spinner.setAdapter(collegeAdapter);
        // 设置默认选项
        spinner.setSelection(0);
        // 设置选项选择监听器
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // 获取选中项的值
                input5 = adapterView.getItemAtPosition(i).toString();
                // 这里可以根据选中的值做一些其他操作
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // 未选择任何项时的操作，可以为空
            }
        });
    }

}