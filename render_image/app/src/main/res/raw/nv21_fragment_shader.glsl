#version 200 es
precision mediump float;
varying vec2 vTexCoord;
uniform sampler2D YTexture;
uniform sampler2D UVTexture;
void main() {
     float y = texture2D(YTexture,vTexCoord).r;					// 使用 GL_LUMINANCE 类型, r == g == b = Y, a = 1.0 ,任取r g b 中一个
	 float u = texture2D(UVTexture,vTexCoord).a - 0.5;
	 float v = texture2D(UVTexture,vTexCoord).r - 0.5; 			// 减 0.5 归一化到 [-0.5,0.5) 区间 为了计算公式的需要
	 float r = y + 1.370705*v;
	 float g = y - 0.337633*u - 0.698001*v;"
	 float b = y + 1.732446*u;
	 gl_FragColor = vec4(r,g,b,1.0);
}