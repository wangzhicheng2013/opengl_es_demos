precision mediump float;
varying vec2 vTexCoord;
uniform sampler2D YTexture;
uniform sampler2D UVTexture;
void main() {
    float y = texture2D(YTexture,vTexCoord).r;
    float u = texture2D(UVTexture,vTexCoord).a - 0.5;
    float v = texture2D(UVTexture,vTexCoord).r - 0.5;
    float r = y + 1.370705*v;
    float g = y - 0.337633*u - 0.698001*v;
    float b = y + 1.732446*u;
    gl_FragColor = vec4(r,g,b,1.0);
}