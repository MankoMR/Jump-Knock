#version 300 es

// A constant representing the combined model/view/projection matrix.
uniform mat4 u_MVPMatrix;
// Per-coloredvertexvert position information we will pass in.
layout(location = 0) in vec4 a_Position;
// Per-coloredvertexvert color information we will pass in.
layout(location = 1) in vec4 a_Color;
// This will be passed into the coloredvertexfrag shader.
out vec4 v_Color;

// The entry point for our coloredvertexvert shader.
void main(){
    // Pass the color through to the coloredvertexfrag shader.
    // It will be interpolated across the triangle.
    v_Color = a_Color;
    // gl_Position is a special variable used to store the final position.
    // Multiply the coloredvertexvert by the matrix to get the final point in
    // normalized screen coordinates.
    gl_Position = u_MVPMatrix
    * a_Position;
}