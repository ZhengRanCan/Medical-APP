package com.example.myapplication.takephoto;

import android.content.res.AssetManager;
import android.graphics.Bitmap;

public class YoloAPI {
    public native boolean Init(AssetManager mgr,int modelid, int cpugpu);

    public class Obj
    {
        public float x;
        public float y;
        public float w;
        public float h;
        public int label;
        public float prob;
    }

    public native Obj[] Detect(Bitmap bitmap, boolean use_gpu);

    static {
        System.loadLibrary("yolov8ncnn");
    }
}
