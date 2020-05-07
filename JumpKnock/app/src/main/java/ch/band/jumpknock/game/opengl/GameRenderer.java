package ch.band.jumpknock.game.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLES32;
import android.opengl.GLSurfaceView;
import android.util.Log;

import androidx.annotation.RawRes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import ch.band.jumpknock.R;

public class GameRenderer implements GLSurfaceView.Renderer {
	private static final String TAG = GameRenderer.class.getSimpleName();
	private Context context;
	private int program;

	public GameRenderer(Context context){
		this.context = context;
	}

	/**
	 * Called when the surface is created or recreated.
	 * <p>
	 * Called when the rendering thread
	 * starts and whenever the EGL context is lost. The EGL context will typically
	 * be lost when the Android device awakes after going to sleep.
	 * <p>
	 * Since this method is called at the beginning of rendering, as well as
	 * every time the EGL context is lost, this method is a convenient place to put
	 * code to create resources that need to be created when the rendering
	 * starts, and that need to be recreated when the EGL context is lost.
	 * Textures are an example of a resource that you might want to create
	 * here.
	 * <p>
	 * Note that when the EGL context is lost, all OpenGL resources associated
	 * with that context will be automatically deleted. You do not need to call
	 * the corresponding "glDelete" methods such as glDeleteTextures to
	 * manually delete these lost resources.
	 * <p>
	 *
	 * @param gl     the GL interface. Use <code>instanceof</code> to
	 *               test if the interface supports GL11 or higher interfaces.
	 * @param config the EGLConfig of the created surface. Can be used
	 */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.d(TAG,"OpenGL version: "+ GLES30.glGetString(GLES30.GL_VERSION));
		program = createProgram();
		String vertex = loadFile(R.raw.vertex);
		String fragment = loadFile(R.raw.fragment);
		compileShader(vertex,GLES30.GL_VERTEX_SHADER,program);
		compileShader(fragment,GLES30.GL_FRAGMENT_SHADER,program);
		GLES30.glLinkProgram(program);
		GLES30.glValidateProgram(program);
		// Set the background frame color
		GLES30.glClearColor(41/255f, 182/255f, 246/255f, 1.0f);

		float[] positions = {
				0,1,
				1,0,
				-1,0,
		};
		VertexBuffer vbo = new VertexBuffer( FloatBuffer.wrap(positions),GLES30.GL_DYNAMIC_DRAW);
		int[] recindex = {
				0,
				1,
				2,
				3,
		};
		IndexBuffer ibo = new IndexBuffer(IntBuffer.wrap(recindex),GLES30.GL_DYNAMIC_DRAW);
		VertexArray vao = new VertexArray();
		VertexBufferLayout vbl = new VertexBufferLayout();
		vbl.Push(2,Float.TYPE,"position");
		vao.AddBuffer(vbo,vbl);
		ibo.bind();
	}

	/**
	 * Called when the surface changed size.
	 * <p>
	 * Called after the surface is created and whenever
	 * the OpenGL ES surface size changes.
	 * <p>
	 * Typically you will set your viewport here. If your camera
	 * is fixed then you could also set your projection matrix here:
	 * <pre class="prettyprint">
	 * void onSurfaceChanged(GL10 gl, int width, int height) {
	 *     gl.glViewport(0, 0, width, height);
	 *     // for a fixed camera, set the projection too
	 *     float ratio = (float) width / height;
	 *     gl.glMatrixMode(GL10.GL_PROJECTION);
	 *     gl.glLoadIdentity();
	 *     gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
	 * }
	 * </pre>
	 *
	 * @param gl     the GL interface. Use <code>instanceof</code> to
	 *               test if the interface supports GL11 or higher interfaces.
	 * @param width
	 * @param height
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES30.glViewport(0, 0, width, height);

	}

	/**
	 * Called to draw the current frame.
	 * <p>
	 * This method is responsible for drawing the current frame.
	 * <p>
	 * The implementation of this method typically looks like this:
	 * <pre class="prettyprint">
	 * void onDrawFrame(GL10 gl) {
	 *     gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	 *     //... other gl calls to render the scene ...
	 * }
	 * </pre>
	 *
	 * @param gl the GL interface. Use <code>instanceof</code> to
	 *           test if the interface supports GL11 or higher interfaces.
	 */
	@Override
	public void onDrawFrame(GL10 gl) {

		// Redraw background color
		GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
		GLES30.glUseProgram(program);
		int uniformColor = GLES30.glGetUniformLocation(program,"vColor");
		FloatBuffer color = FloatBuffer.wrap(new float[]{0,1,1,0});
		GLES30.glUniform4fv(uniformColor,1,color);
		GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP,0,4);
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
