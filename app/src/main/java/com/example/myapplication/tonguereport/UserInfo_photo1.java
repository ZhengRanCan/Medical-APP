package com.example.myapplication.tonguereport;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.takephoto.Camera2ApiActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserInfo_photo1 extends AppCompatActivity {

    RecyclerView recyclerView;
   UserInfo_photo photoAdapter;
   List<Bitmap> photosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo_photo1);

        // 一，控件，显示照片的3个按钮和返回按钮
        // 加载文件夹中的照片，这步很重要！！！
        photosList = loadPhotosFromFolder();
        // 返回按钮    改成重拍
        Button btnTake3Paper = findViewById(R.id.btn_takeback);
        btnTake3Paper.setOnClickListener(v-> startActivityClass(Camera2ApiActivity.class));


        // 二，列表显示照片
        recyclerView = findViewById(R.id.recycler_view_photos);// 初始化RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1)); // 1列的网格布局
        photoAdapter = new UserInfo_photo(photosList);// 适配器，负责将照片数据绑定到RecyclerView中
        recyclerView.setAdapter(photoAdapter);
        // 点击事件的监听器，当用户点击列表中的某个照片时，会触发相应的操作
        photoAdapter.setOnItemClickListener(position -> {
            // 点击事件，您可以在这里添加代码以响应点击
            Toast.makeText(UserInfo_photo1.this, "拍摄的第 " + (position + 1) + " 张照片", Toast.LENGTH_SHORT).show();
        });
    }

    // 三，函数集合
    // 函数：从文件夹中加载照片列表，将所有照片弄成一个列表返回
    private List<Bitmap> loadPhotosFromFolder() {
        List<Bitmap> photosList = new ArrayList<>();
        // 获取指定文件夹中的文件列表
        File folder = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "photos_folder");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    // 检查文件是否是图像文件
                    if (isImageFile(file)) {
                        // 解码图像文件并添加到列表中
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        if (bitmap != null) {
                            photosList.add(bitmap);
                        }
                    }
                }
            }
        }
        return photosList;
    }
    // 函数：检查文件是否是图像文件
    private boolean isImageFile(File file) {
        String name = file.getName();
        // 判断文件名是否以常见的图像文件格式结尾
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif");
    }
    // 函数：换页
    private void startActivityClass(Class<?> cls) {
        Intent intent = new Intent(UserInfo_photo1.this, cls);
        startActivity(intent);
    }
    // 函数：生成数字列表，这里并没有用上，算是之前做的一个废案，但是先留着

}

