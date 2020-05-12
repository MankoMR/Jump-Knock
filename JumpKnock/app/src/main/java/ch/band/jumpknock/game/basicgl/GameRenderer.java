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
import ch.band.jumpknock.game.opengl.Util;

public class GameRenderer implements GLSurfaceView.Renderer {
	private static final String TAG = GameRenderer.class.getCanonicalName();
	private Context context;
	private int program;
	private int colorcounter = 0;
	private int sign  = +1;

	private float[] color;
	private ByteBuffer posBuffer;
	private int cordsPerVertex = 3;
	private ByteBuffer indBuffer;
	private ByteBuffer drawBuffer;
	public GameRenderer(Context context){
		this.context = context;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.d(TAG,"OpenGL version: "+ GLES30.glGetString(GLES30.GL_VERSION));

		program = createProgram();
		String vertex = loadFile(R.raw.vertex);
		String fragment = loadFile(R.raw.fragment);
		compileShader(vertex,GLES30.GL_VERTEX_SHADER,program);
		compileShader(fragment,GLES30.GL_FRAGMENT_SHADER,program);
		GLES30.glLinkProgram(program);
		//GLES30.glValidateProgram(program);
		GLES30.glClearColor(41/255f, 182/255f, 246/255f, 1.0f);
		// Set the background frame color
		float[] positions = {
				-0.5f,  0.5f, 0.0f,   // top left
				-0.5f, -0.5f, 0.0f,   // bottom left
				0.5f, -0.5f, 0.0f,   // bottom right
				0.5f,  0.5f, 0.0f }; // top right
		int[] recindex = {
				0,
				1,
				2,
		};
		short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

		float[] color = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
		posBuffer = Util.createBuffer(positions);
		indBuffer = Util.createBuffer(recindex);
		drawBuffer = Util.createBuffer(drawOrder);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES30.glViewport(0, 0, width, height);

	}

	@Override
	public void onDrawFrame(GL10 gl) {
		if(colorcounter == 255)
			sign = -1;
		if(colorcounter == 0)
			sign = +1;

		colorcounter += sign;
		//GLES30.glClearColor(colorcounter/255f, colorcounter/255f, colorcounter/255f, 1.0f);
		// Redraw background color
		GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

		GLES30.glUseProgram(program);
		int positionH = GLES30.glGetAttribLocation(program,"vPosition");
		GLES30.glEnableVertexAttribArray(positionH);
		Log.d(TAG,"Handle"+positionH+" dslfkjsölfkjasdöl söflkjds flkdsjfla sda ldskfjdsalkfjdsölfd saökd födslkjfad ö");
		GLES30.glVertexAttribPointer(positionH,cordsPerVertex,GLES30.GL_FLOAT,false,cordsPerVertex*4,posBuffer);
		int uniformColor = GLES30.glGetUniformLocation(program,"vColor");
		GLES30.glUniform4fv(uniformColor,1, FloatBuffer.wrap(color));
		GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,posBuffer.asFloatBuffer().limit() / cordsPerVertex);
		GLES30.glDisableVertexAttribArray(positionH);
	}

	public int compileShader(String shader,int shaderType,int program){
		int sid = GLES30.glCreateShader(shaderType);
		GLES30.glShaderSource(sid,shader);
		GLES30.glCompileShader(sid);
		GLES30.glAttachShader(program,sid);
		String info = GLES30.glGetShaderInfoLog(sid);
		Log.d(TAG,(shaderType == GLES30.GL_FRAGMENT_SHADER ? "Fragment": "Vertex") +"shader information: \n"+info);
		return sid;
	}
	public int createProgram(){
		int pid = GLES30.glCreateProgram();
		return pid;
	}
	public String loadFile(@RawRes int id) {
		InputStream stream = context.getResources().openRawResource(id);
		BufferedReader reader = new  BufferedReader( new InputStreamReader(stream));
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while(( line = reader.readLine()) != null ) {
				sb.append( line );
				sb.append( '\n' );
			}
		} catch (IOException e) {
			Log.e(TAG,"Could not open Ressource "+context.getResources().getResourceName(id)+".",e);
		}
		return sb.toString();
	}

}
