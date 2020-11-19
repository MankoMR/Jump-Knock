package ch.band.jumpknock.opengl;

import android.opengl.GLES30;

public class Model implements IBindable, IDisposable {
    private final VertexBuffer mVertexBuffer;
    private final VertexArray mVertexArray;
    private final IndexBuffer mIndexBuffer;
    private final Shaderprogram mShaderprogram;
    private Texture mTexture;
    private final OnDraw mDrawAction;

    public Model(VertexBuffer vertexBuffer, VertexArray vertexArray, IndexBuffer indexBuffer,
                 Shaderprogram shaderprogram, OnDraw drawAction){
        mVertexBuffer = vertexBuffer;
        mVertexArray = vertexArray;
        mIndexBuffer = indexBuffer;
        mShaderprogram = shaderprogram;
        mDrawAction = drawAction;
    }
    public Model(VertexBuffer vertexBuffer, VertexArray vertexArray, IndexBuffer indexBuffer, Texture texture, Shaderprogram shaderprogram, OnDraw drawAction){
        mVertexBuffer = vertexBuffer;
        mVertexArray = vertexArray;
        mIndexBuffer = indexBuffer;
        mTexture = texture;
        mShaderprogram = shaderprogram;
        mDrawAction = drawAction;
        }
    public void draw(){
        mDrawAction.doWith(mShaderprogram);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP,0,mIndexBuffer.count);
        unbind();
    }

    @Override
    public void dispose() {
        mIndexBuffer.dispose();
        mVertexBuffer.dispose();
        mVertexArray.dispose();
        mShaderprogram.dispose();
        if( mTexture != null)
            mTexture.dispose();
    }

    @Override
    public void bind() {
        mShaderprogram.bind();
        mVertexArray.bind();
        mIndexBuffer.bind();
        mVertexBuffer.bind();
        if( mTexture != null)
            mTexture.bind();
    }

    @Override
    public void unbind() {
        mVertexBuffer.unbind();
        mIndexBuffer.unbind();
        mVertexArray.unbind();
        mShaderprogram.unbind();
        if( mTexture != null)
            mTexture.unbind();
    }

    public interface OnDraw{
        void doWith(Shaderprogram shaderprogram);
    }
}
