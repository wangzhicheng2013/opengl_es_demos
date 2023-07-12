package com.example.drawtriangle;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class TriangleGLSurfaceView extends GLSurfaceView {
    public TriangleGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(3);
    }

    public TriangleGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
    }
}