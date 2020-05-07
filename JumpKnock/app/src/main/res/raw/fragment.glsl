#version 300 es
precision mediump float;
uniform vec4 vColor;
layout(location = 0) out vec4 gl_FragColor;

void main() {
    gl_FragColor = vColor;
}
