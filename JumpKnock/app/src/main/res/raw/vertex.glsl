#version 300 es

layout(location = 0) in vec2 position;
out vec4 gl_position;
void main() {
    gl_position = vec4(position,0f,1f);
}