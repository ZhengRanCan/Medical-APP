package com.example.myapplication.takephoto;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.inital.MainActivity;
import com.example.myapplication.tonguereport.UserInfo_photo1;
//import com.example.myapplication.tonguereport.UserInfo_photo1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Camera2ApiActivity extends AppCompatActivity {
    // 一，视图组件
    TextureView textureView;
    TextureView.SurfaceTextureListener surfaceTextureListener;
    CameraManager cameraManager;
    CameraDevice.StateCallback cam_stateCallback;
    CameraDevice opened_camera;
    Surface texture_surface;
    CameraCaptureSession.StateCallback cam_capture_session_stateCallback;
    CameraCaptureSession cameraCaptureSession;
    CaptureRequest.Builder requestBuilder;
    CaptureRequest.Builder requestBuilder_image_reader;
    ImageReader imageReader;
    Surface imageReaderSurface;
    CaptureRequest request;
    Button takephoto_btn;
    Button backButton;
    Button turnButton;
    ImageView takephoto_imageView;
    //权限申请
    private static final int REQUEST_CAMERA_PERMISSION = 12;


    //相机硬件配置
    String[] cameraIdList = new String[0];
    String CameraId;
    String frontCameraId;
    String backCameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2_api);

        // 控件
        textureView=findViewById(R.id.texture_view_camera2);

        takephoto_btn=findViewById(R.id.btn_camera2_takephoto);
       takephoto_imageView= findViewById(R.id.image_view_preview_image);

        // btn1,换页按钮触发事件，返回首页
        backButton = findViewById(R.id.btn_takeback);
        backButton.setOnClickListener(v-> startActivityClass(MainActivity.class));

        //btn2摄像头反转按钮
        turnButton = findViewById(R.id.btn_camera2_turnphoto);
        turnButton.setOnClickListener(v-> switchCamera());
        //设置相机的初始摄像头，设为后摄
        try {
            cameraManager= (CameraManager) getSystemService(Context.CAMERA_SERVICE);  // 初始化CameraManager
            cameraIdList = cameraManager.getCameraIdList();//获取所有相机的id
            backCameraId = cameraIdList[0];
            frontCameraId = cameraIdList[1];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
            CameraId = backCameraId;

        // 二，监听器的函数体，此处为实时预览监视器
        surfaceTextureListener=new TextureView.SurfaceTextureListener() {
            @Override
            // 当TextureView可用并且准备好显示内容时调用
            public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
                // 创建一个Surface，这个Surface随后被用于相机预览
                texture_surface=new Surface(textureView.getSurfaceTexture());
                openCamera(); // 打开相机设备
            }
            @Override
            public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {
            }
            @Override
            public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) { return false; }
            @Override
            public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {
            }
        };
        // 将这个监听器设置给TextureView，以便在相应的事件发生时能够执行上述定义的操作
        textureView.setSurfaceTextureListener(surfaceTextureListener);

        // 三，静态监视器，B1到B4为执行拍照按钮后的一系列操作
        //B1. 准备工作：初始化ImageReader
        imageReader = ImageReader.newInstance(1000  ,1000, ImageFormat.JPEG,2);
        //B2. 准备工作：设置ImageReader收到图片后的回调函数，也就是监视器的函数体，触发监视器会怎么样
        imageReader.setOnImageAvailableListener(reader -> {
            Image image= reader.acquireLatestImage();
            Bitmap bitmap;
            if (image != null) {
                ByteBuffer buffer= image.getPlanes()[0].getBuffer();
                int length= buffer.remaining();
                byte[] bytes= new byte[length];
                buffer.get(bytes);
                image.close();
                bitmap = BitmapFactory.decodeByteArray(bytes,0,length);
                String photoPath = savePhotoToFolder(bitmap);
                takephoto_imageView.setImageBitmap(bitmap);
                onPhotoCaptured();
                navigateToPhotoGallery(photoPath);
            } else {
                Toast.makeText(Camera2ApiActivity.this, "Failed to acquire the image", Toast.LENGTH_SHORT).show();
            }
        }, null);
        //B3 配置：获取ImageReader的Surface
        imageReaderSurface = imageReader.getSurface();
        //B4. btn2，相机点击事件
        takephoto_btn.setOnClickListener(v-> {
                //B4.1 配置request的参数 拍照模式(这行代码要调用已启动的相机 opened_camera，所以不能放在外面
                try {
                    requestBuilder_image_reader = opened_camera.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                requestBuilder_image_reader.set(CaptureRequest.JPEG_ORIENTATION,90);
                requestBuilder_image_reader.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_START);
                //B4.2 配置request的参数的目标对象（ImageReader的Surface），这意味着拍摄的照片会被发送到ImageReader中，若可用则执行imageReader.setOnImageAvailableListener
                requestBuilder_image_reader.addTarget(imageReaderSurface );
                try {
                    //B4.3 触发拍照，在这里捕获照片
                    cameraCaptureSession.capture(requestBuilder_image_reader.build(),null,null);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
        });
    }

    private void switchCamera() {
        try {
            if (CameraId.equals(backCameraId)){
                opened_camera.close();
                CameraId = frontCameraId;
                openCamera();
            }else {
                opened_camera.close();
                CameraId = backCameraId;
                openCamera();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 如果 textureView可用，就直接打开相机
        if(textureView.isAvailable()){
            openCamera();
        }else{
            // 否则，就开启它的可用时监听。
            textureView.setSurfaceTextureListener(surfaceTextureListener);
        }
    }
    @Override
    protected void onPause() {
        // 先把相机的session关掉
        if(cameraCaptureSession!=null){
            cameraCaptureSession.close();
        }
        // 再关闭相机
        if(null!=opened_camera){
            opened_camera.close();
        }
        // 最后关闭ImageReader
        if(null!=imageReader){
            imageReader.close();
        }
        // 最后交给父View去处理
        super.onPause();
    }

    // 四，函数集合
    // 函数：创建文件夹并保存照片，返回照片路径
    private String savePhotoToFolder(Bitmap bitmap) {
        String filename = "photo_" + System.currentTimeMillis() + ".jpg";
        File folder = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "photos_folder");
        if (!folder.exists()) {
            folder.mkdirs(); // 如果文件夹不存在，则创建文件夹
        }
        File file = new File(folder, filename);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
    // 函数：传递照片路径，并换页到PhotoGalleryActivity
    private void navigateToPhotoGallery(String photoPath) {
        Intent intent = new Intent(this, UserInfo_photo1.class);
        intent.putExtra("photo_path", photoPath); // 传递照片路径
        startActivity(intent);
    }
    // 函数：拍照成功弹出提示框
    private void onPhotoCaptured() {
        Toast.makeText(this, "照片已拍摄", Toast.LENGTH_SHORT).show();
    }


    // 函数：拍照
    private void openCamera() {
        checkPermission();//检查权限
        cam_stateCallback=new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice camera) {
                opened_camera=camera;
                try {
                    requestBuilder = opened_camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                    //开启3A模式
                    requestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
                    requestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                    requestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON);
                    requestBuilder.set(CaptureRequest.CONTROL_AWB_MODE, CaptureRequest.CONTROL_AWB_MODE_AUTO);

                    requestBuilder.addTarget(texture_surface);
                    request = requestBuilder.build();
                    cam_capture_session_stateCallback=new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession session) {
                            cameraCaptureSession=session;
                            try {
                                session.setRepeatingRequest(request,null,null);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                        }
                    };
                    opened_camera.createCaptureSession( Arrays.asList(texture_surface,imageReaderSurface), cam_capture_session_stateCallback,null);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onDisconnected(@NonNull CameraDevice camera) {
            }
            @Override
            public void onError(@NonNull CameraDevice camera, int error) {
            }
        };
        //启动相机
        try {
                cameraManager.openCamera(CameraId, cam_stateCallback,null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    //函数：检查相机权限
    //函数一
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 权限未被授予，需要请求权限
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, REQUEST_CAMERA_PERMISSION);
        }
    }
    //请求函数回调一
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean permissionDenied = false;
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                permissionDenied = true;
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

    //相机权限对话框
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

    // 函数：用于换页
    private void startActivityClass(Class<?> cls) {
        Intent intent = new Intent(Camera2ApiActivity.this, cls);
        startActivity(intent);
    }
}
