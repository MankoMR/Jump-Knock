package ch.band.jumpknock.opengl;

import android.opengl.GLES30;

import java.nio.IntBuffer;

public class VertexArray implements IBindable, IDisposable {
    private final int rendererID;

    public VertexArray(){
        IntBuffer buffer = IntBuffer.allocate(1);
        GLES30.glGenVertexArrays(1,buffer);
        rendererID = buffer.get(0);
        GLES30.glBindVertexArray(rendererID);
    }

    public void AddBuffer(VertexBuffer buffer, VertexBufferLayout layout){
        bind();
        buffer.bind();
        int offset = 0;
        for(int i = 0; i < layout.getElements().size();i++)
        {
            VertexBufferLayout.VertexBufferElement element = layout.getElements().get(i);
            GLES30.glEnableVertexAttribArray(i);
            GLES30.glVertexAttribPointer(i,element.count,element.type.GLID,element.normalized,layout.getStride(),offset);
            GLES30.glVertexAttribIPointer(i,element.count,element.type.GLID,layout.getStride(),offset);
            offset += element.count * element.type.BYTES;
        }
    }

    @Override
    public void bind() {
        GLES30.glBindVertexArray(rendererID);
    }
    public void bind(int textureSlot){
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0+textureSlot);
        bind();
    }
    @Override
    public void unbind() {
        GLES30.glBindVertexArray(0);
    }
    @Override
    public void dispose() {
        GLES30.glDeleteVertexArrays(1,IntBuffer.wrap(new int[]{rendererID}));
    }
}
