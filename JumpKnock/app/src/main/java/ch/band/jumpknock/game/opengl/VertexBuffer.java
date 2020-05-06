package ch.band.jumpknock.game.opengl;

import android.opengl.GLES30;

import java.nio.Buffer;
import java.nio.IntBuffer;

public class VertexBuffer implements IBindable, IDisposable {
    protected int rendererID;
    protected Buffer data;

    public VertexBuffer(Buffer data, int usage){
        IntBuffer id = IntBuffer.allocate(1);
        GLES30.glGenBuffers(1,id);
        rendererID = id.get(0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,rendererID);
        Object array = data.array();
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,data.limit() * Util.GetBufferItemType(data).BYTES,data,usage);
    }
    public void bind(){
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,rendererID);
    }
    public void unbind(){
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,0);
    }

    @Override
    public void dispose() {
        IntBuffer id = IntBuffer.wrap(new int[]{rendererID});
        GLES30.glDeleteBuffers(1,id);
    }
}
