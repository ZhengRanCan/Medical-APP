package com.example.myapplication.takephoto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class photo_album extends AppCompatActivity {

    //初始化控件
    ImageButton button;
    Button button1;
    Button button2;
    Button button3;
    Bitmap bitmap;

    //级联器
    CascadeClassifier cascadeClassifier;

    File mCascadeFile;


    private ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_album);

        //加载opencv库
        OpenCVLoader.initLocal();

        try{
            InputStream is = getResources().openRawResource(R.raw.haarcascade_smile);
            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE); //创建目录
            mCascadeFile = new File(cascadeDir, "cascade.xml");
            FileOutputStream os = new FileOutputStream(mCascadeFile);


            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) != -1)
            {
                os.write(buffer, 0, bytesRead);
                Log.d("photo_album", "buffer: " + buffer.toString());
            }
            is.close();
            os.close();
            // Load the cascade classifier
            cascadeClassifier = new CascadeClassifier(mCascadeFile.getAbsolutePath());
            cascadeClassifier.load(mCascadeFile.getAbsolutePath());
            if (cascadeClassifier.empty()) {
                Log.e("photo_album", "Failed to load cascade classifier");
                cascadeClassifier = null;
            }
        }catch (Exception e) {
            Log.e("OpenCVActivity", "Error loading cascade", e);
        }



        //控件处理
        button = findViewById(R.id.imageButton);
        button1 = findViewById(R.id.button8);
        button2 = findViewById(R.id.button7);
        button3 = findViewById(R.id.button6);

        //选取图片后，对图片进行处理
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                final Uri imageUri =result.getData().getData();
                try {
                    //获取相册图片
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                    // Convert Bitmap to Mat
                    Mat matImage = new Mat();
                    Utils.bitmapToMat(bitmap, matImage);
                    Log.d("photo_album","调试");
                    //存储检测到舌头的位置信息
                    MatOfRect tongueRect = new MatOfRect();
                    cascadeClassifier.detectMultiScale(matImage, tongueRect, 1.1, 2, 2, new Size(100, 100), new Size());
                    Toast.makeText(photo_album.this, "Toast调试", Toast.LENGTH_SHORT).show();
                    // Draw rectangle around detected tongue
                    // 遍历检测到的舌头位置，并在图像上绘制出来，以可视化舌头的位置
                    for (Rect rect : tongueRect.toArray()) {
                        Imgproc.rectangle(matImage, rect.tl(), rect.br(), new Scalar(255, 0, 0), 2);
                    }
                    // Enlarge detected tongue
                    // 再次遍历检测到的舌头位置，并将检测到舌头区域放大，这里使用imgproc.resize来调整舌头区域大小。
                    for (Rect rect : tongueRect.toArray()) {
                        Mat tongueROI = matImage.submat(rect);
                        Imgproc.resize(tongueROI, tongueROI, new Size(tongueROI.width() * 2, tongueROI.height() * 2));
                    }
                    // Convert Mat back to Bitmap
                    Bitmap outputImage = Bitmap.createBitmap(matImage.cols(), matImage.rows(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(matImage, outputImage);
                    button.setImageBitmap(outputImage);
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
//        button3.setOnClickListener(v->{
//            Intent intent = new Intent(photo_album.this, Camera2ApiActivity.class);
//            startActivity(intent);
//        });
    }

    //打开相册
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }



}