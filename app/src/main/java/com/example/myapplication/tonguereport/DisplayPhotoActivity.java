package com.example.myapplication.tonguereport;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.io.ByteArrayInputStream;

public class DisplayPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_photo);

        Button btnTake3Paper = findViewById(R.id.btn_takeback);
        btnTake3Paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 返回到上一个页面
                finish();
            }
        });

        // 获取传递过来的字节数组
        byte[] byteArray = getIntent().getByteArrayExtra("bitmap");

        if (byteArray != null) {
            // 将字节数组转换为 Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(byteArray));

            // 在 ImageView 中显示 Bitmap
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
        } else {
            // 如果字节数组为空，显示错误提示
            Toast.makeText(this, "Failed to get bitmap data", Toast.LENGTH_SHORT).show();
        }
    }
}
