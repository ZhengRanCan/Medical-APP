package com.example.myapplication.tonguereport;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.inital.MainActivity;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
//import org.opencv.highgui.HighGui;


public class External_PhotoGalleryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    private List<Bitmap> photosList;
    private RecyclerView recyclerView1;
    private List<Bitmap> photosList1;
    private RecyclerView recyclerView2;
    private List<Bitmap> photosList2;
    private RecyclerView recyclerView3;
    private List<Bitmap> photosList3;
    private TextView RGBTextView;
    private TextView redTextView;
    private TextView greenTextView;
    private TextView blueTextView;
    private List<Float> RGBList;
    private TextView number_photo_TextView;
    private TextView Brightness_TextView;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_photo_gallery);

        // 一，控件，显示照片的3个按钮和返回按钮
        // 加载文件夹中的照片，这步很重要！！！
        photosList = loadPhotosFromFolder();
        // 返回按钮
        Button btnTake3Paper = findViewById(R.id.btn_takeback);
        btnTake3Paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityClass(MainActivity.class);
            }
        });
//        // 显示第一张照片，第一种处理结果
//        Button btnShowFirstPhoto = findViewById(R.id.btn_show_first_photo);
//        btnShowFirstPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (photosList.size() >= 1) {
//                    // showPhotoBitmap(photosList.get(0));
//                    Bitmap bitmap = photosList.get(0); // 获取Bitmap图像
//
//                    Mat resultImage = detectTongueCoatingFromBitmap(bitmap);
//                    Bitmap resultBitmap = Bitmap.createBitmap(resultImage.cols(), resultImage.rows(), Bitmap.Config.ARGB_8888);
//                    Utils.matToBitmap(resultImage, resultBitmap); // 将Mat转换回Bitmap
//                    ImageView imageView1 = findViewById(R.id.one_imageView);
//                    imageView1.setImageBitmap(resultBitmap); // 显示结果图像
//                    resultImage.release(); // 释放Mat资源
//                }
//            }
//        });
//        // 显示第二张照片，第二种处理结果
//        Button btnShowSecondPhoto = findViewById(R.id.btn_show_second_photo);
//        btnShowSecondPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (photosList.size() >= 1) {
//                    // showPhotoBitmap(photosList.get(1));
//                    Bitmap bitmap = photosList.get(0); // 获取Bitmap图像
//
//                    Bitmap processedBitmap = detectTongueCoating(bitmap);
//                    ImageView imageView2 = findViewById(R.id.two_imageView);
//                    imageView2.setImageBitmap(processedBitmap); // 显示结果图像
//                }
//            }
//        });
//        // 显示第三张照片，第三种处理结果
//        Button btnShowThirdPhoto = findViewById(R.id.btn_show_third_photo);
//        btnShowThirdPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (photosList.size() >= 1) {
//                    // showPhotoBitmap(photosList.get(2));
//                    Bitmap bitmap = photosList.get(0); // 获取Bitmap图像
//                    Bitmap processedBitmap = extractTongueContour(bitmap);
//                    ImageView imageView = findViewById(R.id.three_imageView);
//                    imageView.setImageBitmap(processedBitmap); // 显示结果图像
//                }
//            }
//        });
        // 显示第三张照片，第四种处理结果，颜色整体框
        Button btnShowThirdPhoto = findViewById(R.id.btn_show_third_photo);
        btnShowThirdPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photosList.size() >= 1) {
                    // showPhotoBitmap(photosList.get(2));
                    float get = 0;
                    Bitmap bitmap = photosList.get((int) get); // 获取Bitmap图像

//                    Bitmap processedBitmap = detectTongueCoatingCombined2(bitmap);// 处理图像
//                    ImageView imageView = findViewById(R.id.three_imageView);
//                    imageView.setImageBitmap(processedBitmap); // 显示结果图像
//                    MatOfPoint processedMatOfPoint = detectTongueCoatingCombined(bitmap);
//                    float number = detectTongueColorTrend(bitmap,processedMatOfPoint);
//                    showTongueTypeToast(number);
//                    number_photo_TextView = findViewById(R.id.number_photo_TextView);
//                    redTextView = findViewById(R.id.red_TextView);
//                    greenTextView = findViewById(R.id.green_TextView);
//                    blueTextView = findViewById(R.id.blue_TextView);
//                    RGBList = showRGB(bitmap,processedMatOfPoint);
//                    Float red = RGBList.get(0);
//                    Float green = RGBList.get(1);
//                    Float blue = RGBList.get(2);
//                    updateRGBUI(number, (int) get);
//                    updateRGBUI1(red,green,blue);
                }
            }
        });

        // 二，列表显示照片
        recyclerView = findViewById(R.id.recycler_view_photos);// 初始化RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1)); // 1列的网格布局
        photoAdapter = new PhotoAdapter(photosList);// 适配器，负责将照片数据绑定到RecyclerView中
        recyclerView.setAdapter(photoAdapter);

        // 点击事件的监听器，当用户点击列表中的某个照片时，会触发相应的操作
        photoAdapter.setOnItemClickListener(new PhotoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bitmap bitmap = photosList.get(position);
                // 点击事件，您可以在这里添加代码以响应点击
//                Toast.makeText(External_PhotoGalleryActivity.this, "拍摄的第 " + (position + 1) + " 张照片", Toast.LENGTH_SHORT).show();
                MatOfPoint processedMatOfPoint = detectTongueCoatingCombined(bitmap);
                float number = detectTongueColorTrend(bitmap,processedMatOfPoint);
//                showTongueTypeToast(number);
                number_photo_TextView = findViewById(R.id.number_photo_TextView);
                RGBTextView = findViewById(R.id.RGB_TextView);
                redTextView = findViewById(R.id.red_TextView);
                greenTextView = findViewById(R.id.green_TextView);
                blueTextView = findViewById(R.id.blue_TextView);
                Brightness_TextView = findViewById(R.id.Brightness_TextView);
                RGBList = showRGB(bitmap,processedMatOfPoint);
                Float red = RGBList.get(0);
                Float green = RGBList.get(1);
                Float blue = RGBList.get(2);
                float Brightness = calculateBrightnessEstimate(bitmap);
                updateRGBUI(number,position,Brightness);
                updateRGBUI1(red,green,blue);
            }
        });

        photosList1 = loadPhotosFromFolder1();
        recyclerView1 = findViewById(R.id.recycler_view_photos_one);// 初始化RecyclerView
        recyclerView1.setLayoutManager(new GridLayoutManager(this, 1)); // 1列的网格布局
        photoAdapter = new PhotoAdapter(photosList1);// 适配器，负责将照片数据绑定到RecyclerView中
        recyclerView1.setAdapter(photoAdapter);

        photosList2 = loadPhotosFromFolder2();
        recyclerView2 = findViewById(R.id.recycler_view_photos_two);// 初始化RecyclerView
        recyclerView2.setLayoutManager(new GridLayoutManager(this, 1)); // 1列的网格布局
        photoAdapter = new PhotoAdapter(photosList2);// 适配器，负责将照片数据绑定到RecyclerView中
        recyclerView2.setAdapter(photoAdapter);

        photosList3 = loadPhotosFromFolder3();
        recyclerView3 = findViewById(R.id.recycler_view_photos_three);// 初始化RecyclerView
        recyclerView3.setLayoutManager(new GridLayoutManager(this, 1)); // 1列的网格布局
        photoAdapter = new PhotoAdapter(photosList3);// 适配器，负责将照片数据绑定到RecyclerView中
        recyclerView3.setAdapter(photoAdapter);
    }

    // 三，函数集合
    // 函数：按钮点击，进行照片传递，开启新页面显示照片
    private void showPhotoBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            // 创建一个新的Intent对象，用于启动DisplayPhotoActivity
            Intent intent = new Intent(External_PhotoGalleryActivity.this, DisplayPhotoActivity.class);
            // 将位图数据转换为字节数组进行传递
            ByteArrayOutputStream stream = new ByteArrayOutputStream();// 创建一个新的"字节数组输出流"对象，用于将位图数据写入到字节数组中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);// 调用位图的compress方法，将位图压缩为PNG格式，并将压缩后的数据写入到"字节数组输出流"中
            byte[] byteArray = stream.toByteArray();// 调用toByteArray()方法，将"字节数组输出流"中的数据转换为字节数组
            // 将位图的字节数组作为额外的数据放入Intent中
            intent.putExtra("bitmap", byteArray);
            // 启动DisplayPhotoActivity，并传递Intent到目标Activity中
            startActivity(intent);
        } else {
            Toast.makeText(External_PhotoGalleryActivity.this, "Failed to decode photo", Toast.LENGTH_SHORT).show();
        }
    }

    // 函数：从文件夹中加载照片列表，将所有照片弄成一个列表返回
    private List<Bitmap> loadPhotosFromFolder() {
        List<Bitmap> photosList = new ArrayList<>();
        // 获取指定文件夹中的文件列表
        File folder = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "External_folders");
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
        Intent intent = new Intent(External_PhotoGalleryActivity.this, cls);
        startActivity(intent);
    }

    // 函数：生成数字列表，这里并没有用上，算是之前做的一个废案，但是先留着
    private List<Integer> generateNumbersList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            list.add(i);
        }
        return list;
    }

    // 函数：轮廓，也就是第一个处理
    public static Mat detectTongueCoatingFromBitmap(Bitmap bitmap) {
        // 将Bitmap转换为OpenCV的Mat对象
        Mat image = new Mat(bitmap.getWidth(), bitmap.getHeight(), CvType.CV_8UC4);
        Utils.bitmapToMat(bitmap, image);
        // 将彩色图像转换为灰度图像
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_RGBA2GRAY);
        // 使用Canny算法检测边缘
        Mat edges = new Mat();
        Imgproc.Canny(grayImage, edges, 30, 100);
        // 查找轮廓
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        // 在原图上绘制轮廓
        Imgproc.drawContours(image, contours, -1, new Scalar(0, 255, 0), 2);
        // 清理
        grayImage.release();
        edges.release();
        hierarchy.release();
        return image; // 返回修改后的Mat对象，包含轮廓
    }

    // 函数：颜色分割，红色区域划出来，原第二个处理，现已更新使用最大算法
    public Bitmap detectTongueCoating(Bitmap bitmap) {
        Mat image = new Mat();
        Utils.bitmapToMat(bitmap, image);

        Mat hsvImage = new Mat();
        Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV);

        Scalar lowerRed = new Scalar(0, 100, 100);
        Scalar upperRed = new Scalar(10, 255, 255);
        Scalar lowerWhite = new Scalar(0, 0, 200);
        Scalar upperWhite = new Scalar(180, 30, 255);
        Scalar lowerPurple = new Scalar(120, 50, 50);
        Scalar upperPurple = new Scalar(160, 255, 255);

        Mat maskRed = new Mat();
        Core.inRange(hsvImage, lowerRed, upperRed, maskRed);
        Mat maskWhite = new Mat();
        Core.inRange(hsvImage, lowerWhite, upperWhite, maskWhite);
        Mat maskPurple = new Mat();
        Core.inRange(hsvImage, lowerPurple, upperPurple, maskPurple);

        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5));
        Imgproc.morphologyEx(maskRed, maskRed, Imgproc.MORPH_OPEN, kernel);
        Imgproc.morphologyEx(maskWhite, maskWhite, Imgproc.MORPH_OPEN, kernel);
        Imgproc.morphologyEx(maskPurple, maskPurple, Imgproc.MORPH_OPEN, kernel);

        List<MatOfPoint> contoursRed = new ArrayList<>();
        Mat hierarchyRed = new Mat();
        Imgproc.findContours(maskRed, contoursRed, hierarchyRed, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.drawContours(image, contoursRed, -1, new Scalar(0, 0, 255), 2);

        List<MatOfPoint> contoursWhite = new ArrayList<>();
        Mat hierarchyWhite = new Mat();
        Imgproc.findContours(maskWhite, contoursWhite, hierarchyWhite, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.drawContours(image, contoursWhite, -1, new Scalar(255, 255, 255), 2);

        List<MatOfPoint> contoursPurple = new ArrayList<>();
        Mat hierarchyPurple = new Mat();
        Imgproc.findContours(maskPurple, contoursPurple, hierarchyPurple, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.drawContours(image, contoursPurple, -1, new Scalar(128, 0, 128), 2);

        Bitmap resultBitmap = Bitmap.createBitmap(image.cols(), image.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(image, resultBitmap);

        return resultBitmap;
    }

    // 函数：显示轮廓，黑绿色的那个
    // 第一部分可选：高斯模糊，自适应阈值，最大轮廓提取
//    public Bitmap extractTongueContour(Bitmap bitmap) {
//        // Convert Bitmap to Mat
//        Mat image = new Mat();
//        Utils.bitmapToMat(bitmap, image);
//
//        // Convert the image to grayscale
//        Mat grayImage = new Mat();
//        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
//
//        // Apply Gaussian blur to reduce noise
//        Mat blurredImage = new Mat();
//        Imgproc.GaussianBlur(grayImage, blurredImage, new Size(5, 5), 0);
//
//        // Adaptive thresholding to handle varying illumination
//        Mat binaryImage = new Mat();
//        Imgproc.adaptiveThreshold(blurredImage, binaryImage, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 11, 2);
//
//        // Find contours
//        List<MatOfPoint> contours = new ArrayList<>();
//        Mat hierarchy = new Mat();
//        Imgproc.findContours(binaryImage, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
//
//        // Get the largest contour
//        MatOfPoint largestContour = contours.stream()
//                .max((c1, c2) -> Double.compare(Imgproc.contourArea(c1), Imgproc.contourArea(c2)))
//                .orElse(null);
//
//        if (largestContour != null) {
//            // Draw the largest contour on a blank canvas
//            Mat contourImage = Mat.zeros(image.size(), CvType.CV_8UC3);
//            Imgproc.drawContours(contourImage, List.of(largestContour), -1, new Scalar(0, 255, 0), 2);
//
//            // Convert Mat back to Bitmap
//            Bitmap resultBitmap = Bitmap.createBitmap(contourImage.cols(), contourImage.rows(), Bitmap.Config.ARGB_8888);
//            Utils.matToBitmap(contourImage, resultBitmap);
//
//            return resultBitmap;
//        } else {
//            // If no contour is found, return original bitmap
//            return bitmap;
//        }
    //第二部分可选：无高斯模糊，有自适应阈值，使用 Imgproc.findContours() 函数来检测图像中的轮廓
    public Bitmap extractTongueContour(Bitmap inputBitmap) {
        // 将输入的 Bitmap 转换为 Mat
        Mat inputImage = new Mat();
        Utils.bitmapToMat(inputBitmap, inputImage);

        // 转换为灰度图像
        Mat grayImage = new Mat();
        Imgproc.cvtColor(inputImage, grayImage, Imgproc.COLOR_RGB2GRAY);

        // 自适应阈值处理
        Mat binaryImage = new Mat();
        Imgproc.adaptiveThreshold(grayImage, binaryImage, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 15, 10);

        // 查找轮廓
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(binaryImage, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // 创建空白图像
        Mat contourImage = Mat.zeros(inputImage.size(), CvType.CV_8UC3);

        // 绘制轮廓
        Imgproc.drawContours(contourImage, contours, -1, new Scalar(0, 255, 0), 2);

        // 将处理好的图像转换为 Bitmap
        Bitmap outputBitmap = Bitmap.createBitmap(contourImage.cols(), contourImage.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(contourImage, outputBitmap);

        return outputBitmap;
    }

    // 函数：列表显示处理好的图片1
    private List<Bitmap> loadPhotosFromFolder1() {
        List<Bitmap> photosList = new ArrayList<>();
        // 获取指定文件夹中的文件列表
        File folder = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "External_folders");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    // 检查文件是否是图像文件
                    if (isImageFile(file)) {
                        // 解码图像文件并添加到列表中
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        Mat resultImage = detectTongueCoatingFromBitmap(bitmap);
                        Bitmap resultBitmap = Bitmap.createBitmap(resultImage.cols(), resultImage.rows(), Bitmap.Config.ARGB_8888);
                        Utils.matToBitmap(resultImage, resultBitmap); // 将Mat转换回Bitmap
                        resultImage.release(); // 释放Mat资源
                        if (bitmap != null) {
                            photosList.add(resultBitmap);
                        }
                    }
                }
            }
        }
        return photosList;
    }

    // 函数：列表显示处理好的图片2
    private List<Bitmap> loadPhotosFromFolder2() {
        List<Bitmap> photosList = new ArrayList<>();
        // 获取指定文件夹中的文件列表
        File folder = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "External_folders");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    // 检查文件是否是图像文件
                    if (isImageFile(file)) {
                        // 解码图像文件并添加到列表中
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        Bitmap processedBitmap = detectTongueCoatingCombined2(bitmap);
                        if (bitmap != null) {
                            photosList.add(processedBitmap);
                        }
                    }
                }
            }
        }
        return photosList;
    }

    // 函数：列表显示处理好的图片3
    private List<Bitmap> loadPhotosFromFolder3() {
        List<Bitmap> photosList = new ArrayList<>();
        // 获取指定文件夹中的文件列表
        File folder = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "External_folders");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    // 检查文件是否是图像文件
                    if (isImageFile(file)) {
                        // 解码图像文件并添加到列表中
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        Bitmap processedBitmap = extractTongueContour(bitmap);
                        if (bitmap != null) {
                            photosList.add(processedBitmap);
                        }
                    }
                }
            }
        }
        return photosList;
    }

    //往下是4.15晚更新
    // 函数：最大轮廓，这里得到轮廓
    public MatOfPoint detectTongueCoatingCombined(Bitmap bitmap) {
        // 将Bitmap转换为Mat
        Mat image = new Mat();
        Utils.bitmapToMat(bitmap, image);

        // 将图像转换到HSV色彩空间
        Mat hsvImage = new Mat();
        Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV);

        // 设定HSV色彩空间中红色、白色和紫色的范围
        Scalar lowerRed = new Scalar(0, 100, 100);
        Scalar upperRed = new Scalar(10, 255, 255);
        Scalar lowerWhite = new Scalar(0, 0, 200);
        Scalar upperWhite = new Scalar(180, 30, 255);
        Scalar lowerPurple = new Scalar(120, 50, 50);
        Scalar upperPurple = new Scalar(160, 255, 255);

        // 创建各颜色的掩膜
        Mat maskRed = new Mat();
        Core.inRange(hsvImage, lowerRed, upperRed, maskRed);
        Mat maskWhite = new Mat();
        Core.inRange(hsvImage, lowerWhite, upperWhite, maskWhite);
        Mat maskPurple = new Mat();
        Core.inRange(hsvImage, lowerPurple, upperPurple, maskPurple);

        // 合并红色、白色和紫色掩膜
        Mat combinedMask = new Mat();
        Core.bitwise_or(maskRed, maskWhite, combinedMask);
        Core.bitwise_or(combinedMask, maskPurple, combinedMask);

        // 使用形态学操作去除噪点
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5));
        Imgproc.morphologyEx(combinedMask, combinedMask, Imgproc.MORPH_OPEN, kernel);

        // 寻找掩膜中的轮廓
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(combinedMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // 寻找最大轮廓
        double maxArea = 0;
        MatOfPoint maxContour = null;
        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > maxArea) {
                maxArea = area;
                maxContour = contour;
            }
        }

        // 在原始图像上绘制最大轮廓
        if (maxContour != null) {
            Imgproc.drawContours(image, List.of(maxContour), -1, new Scalar(0, 255, 0), 2);  // 使用绿色线条标记
        }
        // 将处理后的Mat转换回Bitmap
        Bitmap resultBitmap = Bitmap.createBitmap(image.cols(), image.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(image, resultBitmap);
        return maxContour;
    }

    // 函数：得到含最大轮廓的图像，也就是上面说更新过的那个
    public Bitmap detectTongueCoatingCombined2(Bitmap bitmap) {
        // 将Bitmap转换为Mat
        Mat image = new Mat();
        Utils.bitmapToMat(bitmap, image);

        // 将图像转换到HSV色彩空间
        Mat hsvImage = new Mat();
        Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV);

        // 设定HSV色彩空间中红色、白色和紫色的范围
        Scalar lowerRed = new Scalar(0, 100, 100);
        Scalar upperRed = new Scalar(10, 255, 255);
        Scalar lowerWhite = new Scalar(0, 0, 200);
        Scalar upperWhite = new Scalar(180, 30, 255);
        Scalar lowerPurple = new Scalar(120, 50, 50);
        Scalar upperPurple = new Scalar(160, 255, 255);

        // 创建各颜色的掩膜
        Mat maskRed = new Mat();
        Core.inRange(hsvImage, lowerRed, upperRed, maskRed);
        Mat maskWhite = new Mat();
        Core.inRange(hsvImage, lowerWhite, upperWhite, maskWhite);
        Mat maskPurple = new Mat();
        Core.inRange(hsvImage, lowerPurple, upperPurple, maskPurple);

        // 合并红色、白色和紫色掩膜
        Mat combinedMask = new Mat();
        Core.bitwise_or(maskRed, maskWhite, combinedMask);
        Core.bitwise_or(combinedMask, maskPurple, combinedMask);

        // 使用形态学操作去除噪点
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5));
        Imgproc.morphologyEx(combinedMask, combinedMask, Imgproc.MORPH_OPEN, kernel);

        // 寻找掩膜中的轮廓
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(combinedMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // 寻找最大轮廓
        double maxArea = 0;
        MatOfPoint maxContour = null;
        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > maxArea) {
                maxArea = area;
                maxContour = contour;
            }
        }

        // 在原始图像上绘制最大轮廓
        if (maxContour != null) {
            Imgproc.drawContours(image, List.of(maxContour), -1, new Scalar(0, 255, 0), 3);  // 使用深绿色加粗线条标记
        }
        // 将处理后的Mat转换回Bitmap
        Bitmap resultBitmap = Bitmap.createBitmap(image.cols(), image.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(image, resultBitmap);
        return resultBitmap;
    }

    // 函数：返回数值
    // 第一部分：HSV算法，三通道没搞出来
//    public float detectTongueColorTrend(Bitmap bitmap, MatOfPoint contour) {
//        // 将Bitmap转换为Mat对象
//        Mat image = new Mat();
//        Utils.bitmapToMat(bitmap, image);
//
//        // 将图像转换到HSV色彩空间
//        Mat hsvImage = new Mat();
//        Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV);
//
//        // 创建一个掩膜，只包含指定的轮廓区域
//        Mat mask = Mat.zeros(image.size(), CvType.CV_8UC1);
//        Imgproc.drawContours(mask, Collections.singletonList(contour), -1, new Scalar(255), -1);
//        // 使得掩膜和图片尺寸相同
//        Mat resizedMask = new Mat();
//        Imgproc.resize(mask, resizedMask, hsvImage.size());
//        // 在HSV图像中使用掩膜，仅获取轮廓区域的颜色数据
//        Mat hsvRoi = new Mat();
//        hsvImage.copyTo(hsvRoi, resizedMask);
//
//        // 提取H通道
//        Mat hueChannel = new Mat();
//        Core.extractChannel(hsvImage, hueChannel, 0); // 0表示H通道
//        // 使用掩膜提取感兴趣区域的像素值
//        Mat roiHue = new Mat();
//        hueChannel.copyTo(roiHue, resizedMask);
//        // 计算提取区域的H通道均值
//        Scalar meanHue = Core.mean(roiHue);
//        // 获取H通道均值的第一个通道值，即H值
//        float hue = (float) meanHue.val[0];
//
//        // 对HSV区域的颜色进行统计，就是这里出来问题，一直闪退！！！！！！！！！！！！！！！
//        Core.MinMaxLocResult mmrHue = Core.minMaxLoc(hsvRoi.reshape(1, (int) resizedMask.total()), resizedMask.reshape(1, (int) resizedMask.total()));
//        float hue = (float) mmrHue.minVal;
//        // 判断舌头的颜色趋势
//        // 淡白舌: H值分布较低，S值低，V值高
//        // 淡红舌: H值约为0-10或350-360，S值中等，V值中等
//        // 深红舌: H值约为0-10或350-360，S值高，V值低
//        // 青紫舌: H值约为260-280，S值高，V值中等到低
//
//        float hue = (float) mmrHue.minVal;
//        double saturation = hsvRoi.get(0, 1)[0];
//        double value = hsvRoi.get(0, 2)[0];
//
////         根据HSV值返回对应的舌头类型
//        if (saturation < 100 && value > 150) {
//            return 1; // 淡白舌
//        } else if ((hue < 50 || hue > 350) && saturation > 50 && saturation < 200 && value > 50 && value < 150) {
//            return 2; // 淡红舌
//        } else if ((hue < 50 || hue > 350) && saturation > 150 && value < 50) {
//            return 3; // 深红舌
//        } else if (hue >= 260 && hue <= 280 && saturation > 50 && value < 150) {
//            return 4; // 青紫舌
//        }
//        return 0; // 如果没有符合的分类
//    }
//
    // 第二部分：也是HSV算法，搞出来了，但是只返回0
//    public int detectTongueColorTrend(Bitmap bitmap, MatOfPoint contour) {
//        // 将Bitmap转换为Mat对象
//        Mat image = new Mat();
//        Utils.bitmapToMat(bitmap, image);
//
//        // 将图像转换到HSV色彩空间
//        Mat hsvImage = new Mat();
//        Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV);
//
//        // 创建一个掩膜，只包含指定的轮廓区域
//        Mat mask = Mat.zeros(image.size(), CvType.CV_8UC1);
//        Imgproc.drawContours(mask, Collections.singletonList(contour), -1, new Scalar(255), -1);
//
//        // 在HSV图像中使用掩膜，仅获取轮廓区域的颜色数据
//        Mat hsvRoi = new Mat();
//        hsvImage.copyTo(hsvRoi, mask);
//
//        // 提取HSV图像的H、S、V通道
//        List<Mat> channels = new ArrayList<>();
//        Core.split(hsvRoi, channels);
//        Mat hueChannel = channels.get(0);
//        Mat saturationChannel = channels.get(1);
//        Mat valueChannel = channels.get(2);
//
//        // 计算H、S、V通道的平均值
//        Scalar meanHue = Core.mean(hueChannel);
//        Scalar meanSaturation = Core.mean(saturationChannel);
//        Scalar meanValue = Core.mean(valueChannel);
//
//        // 根据平均值判断舌头类型
//        double hue = meanHue.val[0];
//        double saturation = meanSaturation.val[0];
//        double value = meanValue.val[0];
//
//        if (saturation < 100 && value > 150) {
//            return 1; // 淡白舌
//        } else if ((hue < 50 || hue > 350) && saturation > 50 && saturation < 200 && value > 50 && value < 150) {
//            return 2; // 淡红舌
//        } else if ((hue < 50 || hue > 350) && saturation > 150 && value < 50) {
//            return 3; // 深红舌
//        } else if (hue >= 260 && hue <= 280 && saturation > 50 && value < 150) {
//            return 4; // 青紫舌
//        } else if ((hue < 50 || hue > 350) && saturation > 80 && value > 150) {
//            return 5; // 红舌
//        } else if (hue >= 30 && hue <= 60 && saturation > 80 && value > 150) {
//            return 6; // 淡黄舌
//        } else if (saturation < 80 && value < 80) {
//            return 7; // 灰黑舌
//        } else if (hue >= 20 && hue <= 40 && saturation > 150 && value >= 50 && value <= 120) {
//            return 8; // 焦黄舌
//        }
//
//        return 0; // 如果没有符合的分类
//    }
    // 第三部分：用RGB算法搞出来了
    public float detectTongueColorTrend(Bitmap bitmap, MatOfPoint contour) {
        // 将Bitmap转换为Mat对象
        Mat image = new Mat();
        Utils.bitmapToMat(bitmap, image);

        // 将图像转换到RGB色彩空间
        Mat rgbImage = new Mat();
        Imgproc.cvtColor(image, rgbImage, Imgproc.COLOR_BGR2RGB);

        // 创建一个掩膜，只包含指定的轮廓区域
        Mat mask = Mat.zeros(image.size(), CvType.CV_8UC1);
        Imgproc.drawContours(mask, Collections.singletonList(contour), -1, new Scalar(255), -1);

        // 在RGB图像中使用掩膜，仅获取轮廓区域的颜色数据
        Mat rgbRoi = new Mat();
        rgbImage.copyTo(rgbRoi, mask);

        // 提取RGB图像的R、G、B通道
        List<Mat> channels = new ArrayList<>();
        Core.split(rgbRoi, channels);
        Mat redChannel = channels.get(0);
        Mat greenChannel = channels.get(1);
        Mat blueChannel = channels.get(2);

        // 计算R、G、B通道的平均值
        Scalar meanRed = Core.mean(redChannel);
        Scalar meanGreen = Core.mean(greenChannel);
        Scalar meanBlue = Core.mean(blueChannel);

        // 根据平均值判断舌头类型
        double red = meanRed.val[0];
        double green = meanGreen.val[0];
        double blue = meanBlue.val[0];

        if (red > 80 && red < 200 && blue > 100 && blue < 180) {
            return 1; // 淡红舌
        } else if (red > 230) {
            return 2; // 深红舌
        } else if (red > 150 && red < 230 && blue > 180) {
            return 3; // 红紫舌
        } else if (red < 80){
            return 4; // 淡白舌
        }

        return 0; // 如果没有符合的分类
    }
    // 函数：点击按钮显示舌头颜色的总体结果
    private void showTongueTypeToast(float tongueType) {
        String message;
        switch ((int) tongueType) {
            case 1:
                message = "淡红舌";
                break;
            case 2:
                message = "深红舌";
                break;
            case 3:
                message = "紫红舌";
                break;
            case 4:
                message = "淡白舌";
                break;
            default:
                message = "未检测出结果";
                break;
        }
        Toast.makeText(External_PhotoGalleryActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    // 函数：更新UI显示光照强度
    private void updateRGBUI(float tongueType,int position,float Brightness) {
        // 在UI上显示光照强度，例如更新TextView的文本
        String message;
        int position1 = position + 1;
        switch ((int) tongueType) {
            case 1:
                message = "淡红舌";
                break;
            case 2:
                message = "深红舌";
                break;
            case 3:
                message = "紫红舌";
                break;
            case 4:
                message = "淡白舌";
                break;
            default:
                message = "未检测出结果";
                break;
        }
        if (RGBTextView != null) {
            RGBTextView.setText("舌头颜色为：" + message);
        }
        if (number_photo_TextView != null) {
            number_photo_TextView.setText("拍摄的第 " + position1 + " 张");
        }
        if (Brightness_TextView != null) {
            Brightness_TextView.setText("光照强度：" + Brightness);
        }
    }
    // 函数：RGB颜色的值为多少
    public List<Float> showRGB(Bitmap bitmap, MatOfPoint contour) {
        // 将Bitmap转换为Mat对象
        Mat image = new Mat();
        Utils.bitmapToMat(bitmap, image);

        // 将图像转换到RGB色彩空间
        Mat rgbImage = new Mat();
        Imgproc.cvtColor(image, rgbImage, Imgproc.COLOR_BGR2RGB);

        // 创建一个掩膜，只包含指定的轮廓区域
        Mat mask = Mat.zeros(image.size(), CvType.CV_8UC1);
        Imgproc.drawContours(mask, Collections.singletonList(contour), -1, new Scalar(255), -1);

        // 在RGB图像中使用掩膜，仅获取轮廓区域的颜色数据
        Mat rgbRoi = new Mat();
        rgbImage.copyTo(rgbRoi, mask);

        // 提取RGB图像的R、G、B通道
        List<Mat> channels = new ArrayList<>();
        Core.split(rgbRoi, channels);
        Mat redChannel = channels.get(0);
        Mat greenChannel = channels.get(1);
        Mat blueChannel = channels.get(2);

        // 计算R、G、B通道的平均值
        Scalar meanRed = Core.mean(redChannel);
        Scalar meanGreen = Core.mean(greenChannel);
        Scalar meanBlue = Core.mean(blueChannel);

        // 创建包含平均值的列表
        List<Float> rgbValues = new ArrayList<>();
        rgbValues.add((float) meanRed.val[0]); // 添加红色通道平均值
        rgbValues.add((float) meanGreen.val[0]); // 添加绿色通道平均值
        rgbValues.add((float) meanBlue.val[0]); // 添加蓝色通道平均值

        return rgbValues;
    }
    // 函数：UI上显示颜色强度
    private void updateRGBUI1(float red,float green,float blue) {
        // 在UI上显示颜色强度，例如更新TextView的文本
        if (redTextView != null) {
            redTextView.setText("红色R：" + red);
        }
        if (greenTextView != null) {
            greenTextView.setText("绿色G：" + green);
        }
        if (blueTextView != null) {
            blueTextView.setText("蓝色B：" + blue);
        }
    }
    // 函数：光照强度
    public float calculateBrightnessEstimate(Bitmap bitmap) {
        long totalBrightness = 0;
        int brightness;
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int stride = 3; // 采样间隔，处理9宫格的每隔3个像素（可以根据需求调整）
        Random random = new Random();

        for (int y = 0; y < height - stride; y += stride) {
            for (int x = 0; x < width - stride; x += stride) {
                // 随机选择九宫格内的一个像素
                int randomY = y + random.nextInt(stride);
                int randomX = x + random.nextInt(stride);
                int index = randomY * width + randomX;
                int color = pixels[index];
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = color & 0xFF;
                brightness = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                totalBrightness += brightness;
            }
        }

        // 计算平均亮度，需要确保除数不为0
        int sampleCount = ((width / stride) * (height / stride));
        return sampleCount > 0 ? totalBrightness / sampleCount : 0;
    }

}

