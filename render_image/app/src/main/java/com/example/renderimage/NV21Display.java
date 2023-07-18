package com.example.renderimage;

import android.util.Log;
import android.content.Context;
import java.nio.FloatBuffer;

public class NV21Display extends YUV420Display {
    private final static String TAG = "NV21Display";
    public final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "attribute vec2 aTexCoord;" +
                    "varying vec2 vTexCoord;" +
                    "uniform mat4 mMat;" +
                    "void main() {" +
                    "   gl_Position = vPosition;" +
                    "   vTexCoord = aTexCoord;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "varying vec2 vTexCoord;" +
                    "uniform sampler2D YTexture;" +
                    "uniform sampler2D UVTexture;" +
                    "void main() {" +
                    "   float y = texture2D(YTexture,vTexCoord).r;" +  // 使用 GL_LUMINANCE 类型, r == g == b = Y, a = 1.0 ,任取r g b 中一个
                    // NV21 V在U前 使用 GL_LUMINANCE 类型, r == g == b = v, a = u ,任取r g b 中一个 和 a
                    "   float u = texture2D(UVTexture,vTexCoord).a - 0.5;" +
                    "   float v = texture2D(UVTexture,vTexCoord).r - 0.5;" +// 减 0.5 归一化到 [-0.5,0.5) 区间 为了计算公式的需要
                    "   float r = y + 1.370705*v;" +
                    "   float g = y - 0.337633*u - 0.698001*v;" +       // 看了几个转换公式 参数稍有差异
                    "   float b = y + 1.732446*u;" +
                    // "   float w=r*0.3+g*0.59+b*0.11;"+
                    // "   r=g=b=w;"+
                    "   gl_FragColor = vec4(r,g,b,1.0);" +
                    "}";
    public boolean init() {
        makeVertexBuffer();
        makeCoordBuffer();
        if (false == makeProgram(vertexShaderCode, fragmentShaderCode)) {
            return false;
        }
        makeTexture();
        Log.d(TAG, "NV21 Display init OK!");
        return true;
    }
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
        if (false == makeProgram(vertexShaderCode, fragmentShaderCode)) {
            return false;
        }
        makeTexture();
        Log.d(TAG, "NV21 Display init OK!");
        return true;
    }
}
