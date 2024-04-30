package com.example.myapplication.takephoto;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.TextureView;

import androidx.annotation.NonNull;

public class Camera2ApiActivity1 extends TextureView implements TextureView.SurfaceTextureListener {
    Paint paint, maskPaint;
    private int width, height;

    public Camera2ApiActivity1(Context context) {
        this(context, null);
    }

    public Camera2ApiActivity1(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSurfaceTextureListener(this);
        init();
    }

    private void init() {
        paint = new Paint();
        maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    private void drawMask(Canvas canvas) {
        // 首先绘制一个覆盖全屏的半透明遮罩
        canvas.drawColor(0x88000000);
        // 再绘制一个透明的圆形
        canvas.drawCircle(width / 2, height / 2, Math.min(width, height) / 2, maskPaint);
    }

    @Override
    public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
        this.width = width;
        this.height = height;
        updateTextureView();
    }

    @Override
    public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {
        this.width = width;
        this.height = height;
        updateTextureView();
    }

    private void updateTextureView() {
        Canvas canvas = lockCanvas();
        if (canvas != null) {
            drawMask(canvas);
            unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {
        // 可以在这里处理实时帧数据更新后的处理
    }
}

