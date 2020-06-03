#version 300 es
// Set the default precision to medium. We don't need as high of a
// precision in the coloredvertexfrag shader.
precision mediump float;

uniform sampler2D u_Texture;
// This is the color from the coloredvertexvert shader interpolated across the
// triangle per coloredvertexfrag.
in vec2 v_TextCoord;

// The entry point for our coloredvertexfrag shader.
out vec4 fragColor;
void main(){
    vec4 texColor = texture(u_Texture,v_TextCoord);
    // Pass the color directly through the pipeline.
    fragColor = texColor;
}