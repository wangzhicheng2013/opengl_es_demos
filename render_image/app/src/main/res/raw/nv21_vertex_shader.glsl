#version 200 es
attribute vec4 vPosition;
attribute vec2 aTexCoord;
varying vec2 vTexCoord;
uniform mat4 mMat;
void main() {
     gl_Position  = vPosition;
     vTexCoord = aTexCoord;
}