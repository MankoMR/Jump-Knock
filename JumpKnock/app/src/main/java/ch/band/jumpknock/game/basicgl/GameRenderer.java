package ch.band.jumpknock.game.basicgl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import androidx.annotation.RawRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ch.band.jumpknock.R;

public class GameRenderer implements GLSurfaceView.Renderer {
	private static final String TAG = GameRenderer.class.getCanonicalName();
	private Context context;
	public GameRenderer(Context context){
		this.context = context;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.d(TAG,"OpenGL version: "+ GLES30.glGetString(GLES30.GL_VERSION));
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES30.glViewport(0, 0, width, height);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
	}

}
