package com.example.renderimage;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRender implements GLSurfaceView.Renderer {
    private final static String TAG = "GLRender";
    public int mWidth = 0;
    public int mHeight = 0;
    public byte[] nv21 = null;
    private int mSurfaceWidth, mSurfaceHeight;
    private int mShowWidth, mShowHeight;
    private NV21Display mNV21Display;
    private Context mContext;
    public GLRender(Context context) {
        mNV21Display = new NV21Display();
        mContext = context;
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0, 0, 0.0f);
        if (false == mNV21Display.init(mContext)) {         // must set here
            Log.e(TAG, "NV21Display init failed!");
            throw new RuntimeException("NV21Display init failed!");
        }
    }
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        mSurfaceWidth = width;
        mSurfaceHeight = height;
    }
    @Override
    public void onDrawFrame(GL10 gl) {
        // 画背景颜色
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        if (0 == mWidth || 0 == mHeight || null == nv21) {
            Log.d(TAG, "LAILLL");
            return;
        }
        adjustShowScreen();
        mNV21Display.display(nv21, mWidth, mHeight);
    }
    public void adjustShowScreen() {
        // find the most suitable window size according to the aspect ratio
        if ((mWidth == mSurfaceWidth) && (mHeight == mSurfaceHeight)) {
            return;
        }
        if ((float)mWidth / (float)mHeight < (float)mSurfaceWidth / (float)mSurfaceHeight) {
            // Wider screen
            mShowWidth = (int)((float)mSurfaceHeight * (float)mWidth / (float)mHeight);
            mShowHeight = mSurfaceHeight;
        } else {
            // Higher screen
            mShowHeight = (int)((float)mSurfaceWidth * (float)mHeight / (float)mWidth);
            mShowWidth = mSurfaceWidth;
        }
        GLES20.glViewport(0, 0, mShowWidth, mShowHeight);
    }
}
