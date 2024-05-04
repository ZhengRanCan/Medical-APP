package com.example.myapplication.takephoto;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import java.io.IOException;


public class photo_album extends AppCompatActivity {

    //初始化控件
    ImageView button;
    Button button1;
    Button button2;
    Button button3;
    TextView textView;
    Bitmap bitmap;
    Bitmap croppedBitmap;

    //ActivityAPI
    private ActivityResultLauncher<Intent> galleryLauncher;
    //调用JNI
    private YoloAPI yolo = new YoloAPI();
    private int current_model = 0;
    private int current_cpugpu = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_album);
        //初始化控件
        button = findViewById(R.id.imageButton);
        button1 = findViewById(R.id.button8);
        button2 = findViewById(R.id.button7);
        button3 = findViewById(R.id.button6);
        textView = findViewById(R.id.textView6);
        //选取图片后，对图片进行处理
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                final Uri imageUri =result.getData().getData();
                try {
        //获取相册图片
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                    boolean ret_init = yolo.Init(getAssets(),current_model,current_cpugpu);
                    if(!ret_init) {
                        Log.e("MainActivity","yololoadModel failed");
                    }else{
                        drawRect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(photo_album.this, "出现错误", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //打开相册
        button.setOnClickListener(v->openGallery());

        //跳转到相机页面
        button1.setOnClickListener(v->{
            Intent intent = new Intent(photo_album.this, Camera2ApiActivity.class);
            startActivity(intent);
        });

        //打开相册
        button2.setOnClickListener(v->openGallery());
        //跳转到报告部分
        // button3.setOnClickListener(v->{
        // Intent intent = new Intent(photo_album.this, Camera2ApiActivity.class);
        // startActivity(intent);
        // });

    }

    //打开相册
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    //图像处理
    private void drawRect(){

        YoloAPI.Obj[] objArray = yolo.Detect(bitmap, true);
        YoloAPI.Obj obj = objArray[0];
        String text = "x: " + obj.x + "\n" +
                "y: " + obj.y + "\n" +
                "w: " + obj.w + "\n" +
                "h: " + obj.h + "\n" +
                "label: " + obj.label + "\n" +
                "prob: " + obj.prob;
        textView.setText(text);
        //截取图片
        float x = obj.x;
        float y = obj.y;
        float w = obj.w;
        float h = obj.h;
//
//        croppedBitmap = Bitmap.createBitmap(bitmap, (int)x, (int)y, (int)w, (int)h);
        // 将opencv的Mat对象转换成Bitmap对象，并将其显示到Button对象中。
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.RED); // 设置框的颜色
        paint.setStyle(Paint.Style.STROKE); // 设置画笔样式为描边
        paint.setStrokeWidth(10); // 设置描边的宽度

        // 画框
        // 注意：这里假设 x, y, w, h 是以像素为单位的坐标和尺寸
        RectF rect = new RectF(x, y, x+w, y+h);
        canvas.drawRect(rect, paint);
        button.setImageBitmap(mutableBitmap);

    }
}