package com.example.renderimage;

import android.util.Log;
import android.content.Context;
import java.nio.FloatBuffer;

public class NV21Display extends YUV420Display {
    private final static String TAG = "NV21Display";
    public boolean init(Context context) {
        String vertexShaderCode = readShaderFromResource(context, R.raw.nv21_vertex_shader);
        if (true == vertexShaderCode.isEmpty()) {
            Log.e(TAG, "get nv21 vertex shader string failed!");
            return false;
        }
        String fragmentShaderCode = readShaderFromResource(context, R.raw.nv21_fragment_shader);
        if (true == fragmentShaderCode.isEmpty()) {
            Log.e(TAG, "get nv21 fragment shader string failed!");
            return false;
        }
        makeVertexBuffer();
        makeCoordBuffer();
        Log.d(TAG, "now to make program!");
        if (false == makeProgram(vertexShaderCode, fragmentShaderCode)) {
            return false;
        }
        Log.d(TAG, "make program ok!");
        makeTexture();
        Log.d(TAG, "NV21 Display init OK!");
        return true;
    }
}
