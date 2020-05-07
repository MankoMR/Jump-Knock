#version 300 es

layout(location = 0) in vec2 position;
layout(location = 0) out vec4 gl_position;

void main() {
    vec4 locPosition = vec4(position,0f,1f);
    gl_position = locPosition;
}