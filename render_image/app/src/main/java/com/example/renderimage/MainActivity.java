package com.example.renderimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private DisplaySurfaceView mDisplaySurfaceView;
    private ImageView mQuitButton;
    public static final int IMAGE_WIDTH = 1920;
    public static final int IMAGE_HEIGHT = 1080;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        renderNV21();
    }
    private void initView() {
        mDisplaySurfaceView = findViewById(R.id.surfaceView);   // 通过id找surfceview
        mQuitButton = findViewById(R.id.quit_btn);
        mQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // jump to ID4 Demo Car MainActivity
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }
    private void renderNV21() {
        final String testPhoto = "cms_1920x1080.NV21";
        Log.d(TAG, "test photo:" + testPhoto);
        InputStream is = null;
        try {
            is = this.getAssets().open(testPhoto);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
            return;
        }
        if (null == is) {
            Log.e(TAG, "fail to open test photo file:" + testPhoto);
            return;
        }
        Log.i(TAG, testPhoto + " open ok!");
        byte[] imageData = new byte[IMAGE_WIDTH * IMAGE_HEIGHT * 3 / 2];
        int byteCount = 0;
        try {
            byteCount = is.read(imageData, 0, imageData.length);
            is.close();
        }
        catch (Exception e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
            return;
        }
        if (byteCount != imageData.length) {
            Log.e(TAG, "read file length:" + byteCount + " error!");
            return;
        }
        Log.d(TAG, "read size:" + byteCount);
        mDisplaySurfaceView.mGlRender.mWidth = IMAGE_WIDTH;
        mDisplaySurfaceView.mGlRender.mHeight = IMAGE_HEIGHT;
        mDisplaySurfaceView.mGlRender.nv21 = imageData;
        mDisplaySurfaceView.requestRender();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mDisplaySurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDisplaySurfaceView.onPause();
    }
}