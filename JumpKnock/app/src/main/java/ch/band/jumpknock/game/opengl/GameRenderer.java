package ch.band.jumpknock.game.opengl;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLES32;
import android.opengl.GLSurfaceView;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Objects;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class GameRenderer implements GLSurfaceView.Renderer {

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

		// Set the background frame color
		GLES30.glClearColor(41/255f, 182/255f, 246/255f, 1.0f);

		float[] positions = {
				0,1,
				1,0,
				-1,0,
		};
		VertexBuffer recVertices = new VertexBuffer( FloatBuffer.wrap(positions),float.class,GLES30.GL_DYNAMIC_DRAW);
		int[] recindex = {
				0,
				1,
				2,
				3,
		};
		IndexBuffer recIndexes = new IndexBuffer(IntBuffer.wrap(recindex),GLES30.GL_DYNAMIC_DRAW);
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
	}
	public static int loadShader(int type, String shaderCode){

		// create a vertex shader type (GLES30.GL_VERTEX_SHADER)
		// or a fragment shader type (GLES30.GL_FRAGMENT_SHADER)
		int shader = GLES30.glCreateShader(type);

		// add the source code to the shader and compile it
		GLES30.glShaderSource(shader, shaderCode);
		GLES30.glCompileShader(shader);

		return shader;
	}

}
