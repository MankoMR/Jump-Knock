package ch.band.jumpknock.game.opengl;

import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class IndexBuffer implements IBindable {
    protected int rendererID;
    protected int count;
    protected IntBuffer data;

    public IndexBuffer(IntBuffer data, int usage){
        IntBuffer id = IntBuffer.allocate(1);
        GLES30.glGenBuffers(1,id);
        rendererID = id.get(0);
        this.count = data.limit();
        this.data  = data;
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,rendererID);
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER,data.limit() * 4,data,usage);
    }
    public void bind(){
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,rendererID);
    }
    public void unBind(){
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,0);
    }

    @Override
    protected void finalize() throws Throwable {
        IntBuffer id = IntBuffer.wrap(new int[]{rendererID});
        GLES30.glDeleteBuffers(1,id);
        super.finalize();
    }
}
