package ch.band.jumpknock.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import androidx.annotation.RawRes;

import java.nio.IntBuffer;

public class Texture implements IBindable, IDisposable {
    private int mId;
    private int mWidth;
    private int mHeight;
    private int mInternalFormat;
    private final int mType;

    public Texture(@RawRes int drawable, Context context){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap tex = BitmapFactory.decodeResource(context.getResources(),drawable,options);
        IntBuffer ids = IntBuffer.allocate(1);
        GLES30.glGenTextures(1,ids);
        mId = ids.get(0);
        mWidth = tex.getWidth();
        mHeight = tex.getHeight();
        mInternalFormat = GLUtils.getInternalFormat(tex);
        mType = GLUtils.getType(tex);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,mId);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR_MIPMAP_LINEAR);

        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);

        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0,tex,0);
        GLES30.glGenerateMipmap(GLES30.GL_TEXTURE_2D); // Unavailable in OpenGL 2.1, use gluBuild2DMipmaps() insteads.

        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);
        tex.recycle();
    }
    public int getmId() { return mId; }
    public int getmWidth() { return mWidth; }
    public int getmHeight() { return mHeight; }
    public int getInternalFormat() { return mInternalFormat; }
    public int getType() { return mType; }

    @Override
    public void bind() {
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,mId);
    }

    @Override
    public void unbind() {
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);
    }

    @Override
    public void dispose() {
        GLES30.glDeleteTextures(1,IntBuffer.wrap(new int[]{mId}));
    }
}
