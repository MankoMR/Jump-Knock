package ch.band.jumpknock.opengl;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.util.Log;

import java.nio.IntBuffer;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

public class Shaderprogram implements IDisposable, IBindable {
    private static final String TAG = Shaderprogram.class.getCanonicalName();

    private final int mVertexShaderId;
    private final int mFragmentShaderId;
    private final int mId;
    private final HashMap<String,Integer> mUniformLocationCache = new HashMap<>();

    private Shaderprogram(int vertexShader, int fragmentShader, int program){
        mVertexShaderId = vertexShader;
        mFragmentShaderId = fragmentShader;
        mId = program;
    }

    public int getVertexShaderId() { return mVertexShaderId; }
    public int getFragmentShaderId() { return mFragmentShaderId; }
    public int getId() { return mId; }
    @Override
    public void dispose() {
        GLES30.glDeleteShader(mVertexShaderId);
        GLES30.glDeleteShader(mFragmentShaderId);
        GLES30.glDeleteProgram(mId);
    }
    public void validateProgram() {
        IntBuffer result = IntBuffer.allocate(1);
        GLES30.glValidateProgram(mId);
        GLES30.glGetProgramiv(mId, GLES20.GL_VALIDATE_STATUS, result);
        if (result.get(0) != GLES30.GL_TRUE) {
            String message = "Program is in an invalid state:\n" + GLES30.glGetProgramInfoLog(mId);
            Log.i(TAG,message);
            throw new RuntimeException(message);
        }
    }
    public int getUniformLocation(String name){
        Integer id = mUniformLocationCache.get(name);
        if(id == null){
            id = GLES30.glGetUniformLocation(mId,name);
            mUniformLocationCache.put(name,id);
        }
        if(id == -1){
            Log.w(TAG,name + " does not correspond to an active uniform variable in shaderprogram or  is associated with a named uniform block");
        }
        return id;
    }
    public void SetUniform(String name,float[] content){
        if (content == null || content.length <= 0 || content.length > 4)
            throw new IllegalArgumentException("content == null || content.length <= 0 || content.length > 4");
        int id = getUniformLocation(name);
        if(id == -1){
            throw new RuntimeException(name + " does not correspond to an active uniform variable in shaderprogram or  is associated with a named uniform block");
        } else {
            switch (content.length){
                case 1:
                    GLES30.glUniform1f(id,content[0]);
                    break;
                case 2:
                    GLES30.glUniform2f(id,content[0],content[1]);
                    break;
                case 3:
                    GLES30.glUniform3f(id,content[0],content[1],content[2]);
                    break;
                case 4:
                    GLES30.glUniform4f(id,content[0],content[1],content[2],content[3]);
                    break;
                default:
                    break;
            }
        }
    }
    public void SetUniform(String name, int[] content){
        if (content == null || content.length <= 0 || content.length > 4)
            throw new IllegalArgumentException("content == null || content.length <= 0 || content.length > 4");

        int id = getUniformLocation(name);
        if(id == -1){
            throw new RuntimeException(name + " does not correspond to an active uniform variable in shaderprogram or  is associated with a named uniform block");
        } else {
            switch (content.length){
                case 1:
                    GLES30.glUniform1i(id,content[0]);
                    break;
                case 2:
                    GLES30.glUniform2i(id,content[0],content[1]);
                    break;
                case 3:
                    GLES30.glUniform3i(id,content[0],content[1],content[2]);
                    break;
                case 4:
                    GLES30.glUniform4i(id,content[0],content[1],content[2],content[3]);
                    break;
                default:
                    break;
            }
        }
    }
    public void SetUniformMaxtrixSquare(String name, boolean transpose, float[] content){
        int id = getUniformLocation(name);
        if(id == -1){
            throw new RuntimeException(name + " does not correspond to an active uniform variable in shaderprogram or  is associated with a named uniform block");
        } else {
            switch (content.length){
            case  4:
                GLES30.glUniformMatrix2fv(id,1,transpose,content,0);
                break;
            case 9:
                GLES30.glUniformMatrix3fv(id,1,transpose,content,0);
                break;
            case 16:
                GLES30.glUniformMatrix4fv(id,1,transpose,content,0);
                break;
            default:
                throw new IllegalArgumentException("needs to be a square Matrix: 2x2, 3x3, 4x4");
            }
        }
    }
    public void Disable(int feature){
        GLES30.glGetError();
        GLES30.glDisable(feature);
        int error = GLES30.glGetError();
        if(error == GLES30.GL_INVALID_ENUM){
            throw new IllegalArgumentException();
        }
    }
    public void Enable(int feature){
        GLES30.glGetError();
        GLES30.glEnable(feature);
        int error = GLES30.glGetError();
        if(error == GLES30.GL_INVALID_ENUM){
            throw new IllegalArgumentException();
        }
    }
    public void BlendFunction(int sfactor, int dfactor){
        GLES30.glGetError();
        GLES30.glBlendFunc(sfactor,dfactor);
        int error = GLES30.glGetError();
        if(error == GLES30.GL_INVALID_ENUM){
            throw new IllegalArgumentException();
        }
    }
    @Override
    public void bind() {  GLES30.glUseProgram(mId); }
    @Override
    public void unbind() { GLES30.glUseProgram(0); }

    public static class ProgramBuilder{
        private final String TAG = ProgramBuilder.class.getCanonicalName();
        private int mFragmentShader;
        private int mVertexShader;
        private final int mProgram;
        private boolean used = false;

        public ProgramBuilder(){
            mProgram = GLES30.glCreateProgram();
            if(mProgram == 0){
                throw new RuntimeException("Error creating program.");
            }
        }

        public ProgramBuilder addFragmentShader(String shader){
            mFragmentShader = addShader(shader, GLES30.GL_FRAGMENT_SHADER);
            return this;
        }
        public ProgramBuilder addVertexShader(String shader){
            mVertexShader = addShader(shader, GLES30.GL_VERTEX_SHADER);
            return this;
        }
        public ProgramBuilder addBlendFunction(){
            GLES30.glEnable(GLES30.GL_BLEND);
            GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA,GLES20.GL_ONE_MINUS_SRC_ALPHA);
            return this;
        }
        public Shaderprogram build(){
            checkUsed();
            used = true;
            GLES30.glAttachShader(mProgram,mFragmentShader);
            GLES30.glAttachShader(mProgram,mVertexShader);
            GLES30.glLinkProgram(mProgram);
            IntBuffer result = IntBuffer.allocate(1);
            GLES30.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS,result);
            if(result.get(0) != GLES30.GL_TRUE){
                String message = GLES30.glGetProgramInfoLog(mProgram);
                throw new RuntimeException("Error linking Program:\n"+message);
            }
            return new Shaderprogram(mVertexShader,mFragmentShader,mProgram);
        }

        private int addShader(String shader, int type){
            checkUsed();
            int sid = GLES30.glCreateShader(type);
            if(sid == GLES30.GL_INVALID_ENUM){
                throw new RuntimeException("\""+type+"\" is not a supported type of shader.");
            }
            if(sid == 0){
                throw new RuntimeException("Error creating shader.");
            }
            GLES30.glShaderSource(sid,shader);
            GLES30.glCompileShader(sid);
            IntBuffer status = IntBuffer.allocate(1);
            GLES30.glGetShaderiv(sid,GLES30.GL_COMPILE_STATUS,status);
            if(status.get(0) != GL10.GL_TRUE){
                Log.e(TAG,"Error compiling shader:\n"+GLES30.glGetShaderInfoLog(sid));
                throw new RuntimeException("Error compiling shader.");
            }
            return sid;
        }
        private void checkUsed(){
            if(used){
                throw  new RuntimeException("ProgramBuilder can only be used once.");
            }
        }
    }
}
