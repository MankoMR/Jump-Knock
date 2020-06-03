#version 300 es
// Set the default precision to medium. We don't need as high of a
// precision in the coloredvertexfrag shader.
precision mediump float;
// This is the color from the coloredvertexvert shader interpolated across the
// triangle per coloredvertexfrag.
in vec4 v_Color;
// The entry point for our coloredvertexfrag shader.
out vec4 fragColor;
void main(){
   // Pass the color directly through the pipeline.
   fragColor = v_Color;
}