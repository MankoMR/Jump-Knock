package ch.band.jumpknock.game.opengl;

import android.opengl.GLES30;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class VertexBuffer implements IBindable {
    protected int rendererID;
    protected Buffer data;

    public VertexBuffer(Buffer data, Class dataType, int usage){
        IntBuffer id = IntBuffer.allocate(1);
        GLES30.glGenBuffers(1,id);
        rendererID = id.get(0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,rendererID);
        Object array = data.array();
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,data.limit() * IBindable.sizeof(dataType),data,usage);
    }
    public void bind(){
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,rendererID);
    }
    public void unBind(){
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,0);
    }

    @Override
    protected void finalize() throws Throwable {
        IntBuffer id = IntBuffer.wrap(new int[]{rendererID});
        GLES30.glDeleteBuffers(1,id);
        super.finalize();
    }
}
