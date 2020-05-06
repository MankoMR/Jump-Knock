package ch.band.jumpknock.game.opengl;

import android.opengl.GLES30;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

final class Util {

    public static final GlType GetGlType(Class dataType)
    {
        if (dataType == null) throw new NullPointerException();

        if (dataType == float.class  || dataType == Float.class)     return GlType.FLOAT;
        if (dataType == int.class    || dataType == Integer.class)   return GlType.INT;
        if (dataType == short.class  || dataType == Short.class)     return GlType.SHORT;
        if (dataType == byte.class   || dataType == Byte.class)      return GlType.BYTE;
        if (dataType == boolean.class  || dataType == Boolean.class) return GlType.BOOL;
        throw new AssertionError("OpenGL ES doesn't support this Datatype");
    }
    public static final GlType GetBufferItemType(Buffer buffer){
        if( buffer instanceof IntBuffer )  return  GlType.INT;
        if( buffer instanceof ShortBuffer)  return  GlType.SHORT;
        if( buffer instanceof FloatBuffer)  return  GlType.FLOAT;
        if( buffer instanceof ByteBuffer)  return GlType.BYTE;
        throw new AssertionError("Type not recognized");
    }

    public enum GlType {
        FLOAT(GLES30.GL_FLOAT,4),
        INT(GLES30.GL_INT,4),
        UINT(GLES30.GL_UNSIGNED_INT,4),
        SHORT(GLES30.GL_SHORT,2),
        USHORT(GLES30.GL_UNSIGNED_SHORT,2),
        BYTE(GLES30.GL_BYTE,1),
        UBYTE(GLES30.GL_UNSIGNED_BYTE,1),
        BOOL(GLES30.GL_BOOL,1);

        public final int GLID;
        public final int BYTES;

        GlType(int glid, int bytes){
            this.BYTES = bytes;
            this.GLID = glid;
        }
    }
}
