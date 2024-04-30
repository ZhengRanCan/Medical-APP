package com.example.myapplication.inital;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import android.widget.Button;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.opencv.android.OpenCVLoader;

import com.example.myapplication.R;
import com.example.myapplication.tonguedetection.UserInfo0;
import com.example.myapplication.tonguereport.External_PhotoGalleryActivity;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 12;

    Button btn_tongue;
    Button btn_report;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化按钮
        setContentView(R.layout.activity_main);
        btn_tongue = findViewById(R.id.tongue);
        btn_report = findViewById(R.id.report);

        //初始化opencv
        if (!OpenCVLoader.initLocal()) {
            Log.e("OpenCV", "Unable to load OpenCV!");
        } else {
            Log.d("OpenCV", "OpenCV loaded successfully!");
        }

        //切换智能舌诊界面
        btn_tongue.setOnClickListener(v->{
            //动态获取权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // 权限未被授予，需要请求权限
                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, REQUEST_CAMERA_PERMISSION);
            } else {
                // 权限已经被授予，可以执行相应的操作
                startActivityClass(UserInfo0.class);
            }
        });

        //切换舌诊报告
        btn_report.setOnClickListener(v->startActivityClass(External_PhotoGalleryActivity.class));

    }


    /*
    方法
     */

    //权限申请返回结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean permissionDenied = false;
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                permissionDenied = true;
                startActivityClass(UserInfo0.class);
                break;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            // 权限被拒绝
            if (permissionDenied &&!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                // 用户勾选 "不再询问" 选项，向用户解释权限的重要性并再次请求权限
                showPermissionSettingsDialog();
            }
        }
    }


    private void showPermissionSettingsDialog() {
        // 显示一个对话框，引导用户前往应用设置页面手动开启权限
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您已经拒绝了相机权限，您可以在应用设置中手动开启权限。");
        builder.setPositiveButton("去设置", (dialog,which)-> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void startActivityClass(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }




}