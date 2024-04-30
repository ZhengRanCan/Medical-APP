package com.example.myapplication.tonguereport;

//import android.content.Context;
//import android.graphics.Bitmap;
//import android.os.Environment;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class PhotoManager {
//    private Context context;
//
//    public PhotoManager(Context context) {
//        this.context = context;  // 传入的上下文用于访问文件系统
//    }
//
//    public void saveImage(Bitmap bitmap, String folderName, String fileName) {
//        File folder = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), folderName);  // 定位图片存储的目标文件夹
//        if (!folder.exists()) {
//            folder.mkdirs();  // 如果文件夹不存在，则创建
//        }
//        File file = new File(folder, fileName);  // 创建文件实例
//        try (FileOutputStream out = new FileOutputStream(file)) {
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);  // 压缩并保存图片
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PhotoManager {
    private Context context;

    public PhotoManager(Context context) {
        this.context = context;  // 使用传入的上下文来访问文件系统
    }

    public String saveImage(Bitmap bitmap, String folderName, String fileName) {
        File folder = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), folderName);  // 定位到图片存储的目标文件夹
        if (!folder.exists()) {
            folder.mkdirs();  // 如果文件夹不存在，则创建
        }
        File file = new File(folder, fileName);  // 创建文件实例
        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);  // 压缩并保存图片
            return file.getAbsolutePath();  // 返回保存文件的绝对路径
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;  // 如果保存失败，则返回null
    }
}
