package ch.band.jumpknock.opengl;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.RawRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ch.band.jumpknock.R;


public class Renderer implements GLSurfaceView.Renderer {
	private static final String TAG = Renderer.class.getSimpleName();
	private Context context;
	private Shaderprogram program;
	Texture texture;
	/**
	 * Store the view matrix. This can be thought of as our camera. This matrix transforms world space to eye space;
	 * it positions things relative to our eye.
	 */
    private float[] mViewMatrix = new float[16];
	private float[] mProjectionMatrix = new float[16];
	private float[] mModelMatrix = new float[16];
    /** Allocate storage for the final combined matrix. This will be passed into the shader program. */
    private float[] mMVPMatrix = new float[16];
    private Model mTriangle;
    private Model mQuad;
	private IndexBuffer ibo;
	private VertexArray vao;

	public Renderer(Context context){
		this.context = context;
	}

	public Model createTriangle(){
		String vertex = loadFile(R.raw.coloredvertexvert);
		String fragment = loadFile(R.raw.coloredvertexfrag);
		program = new Shaderprogram.ProgramBuilder()
				.addFragmentShader(fragment)
				.addVertexShader(vertex)
				.build();
		final float[] triangle1 = {
				// X, Y, Z,
				// R, G, B, A
				-0.5f, -0.25f, 0.0f,
				1.0f, 0.0f, 0.0f, 1.0f,

				0.5f, -0.25f, 0.0f,
				0.0f, 0.0f, 1.0f, 1.0f,

				0.0f, 0.559016994f, 0.0f,
				0.0f, 1.0f, 0.0f, 1.0f};
		VertexBuffer vbo = new VertexBuffer( Util.createBuffer(triangle1),GLES30.GL_DYNAMIC_DRAW);
		int[] recindex = {
				3,
				2,
				1,
		};
		ibo = new IndexBuffer(IntBuffer.wrap(recindex), GLES30.GL_DYNAMIC_DRAW);
		vao = new VertexArray();
		VertexBufferLayout vbl = new VertexBufferLayout();
		vbl.Push(3,Float.TYPE,"v_Position");
		vbl.Push(4,Float.TYPE,"v_Color");
		vao.AddBuffer(vbo,vbl);
		Model triangle = new Model(vbo, vao, ibo, program, new Model.OnDraw() {
			@Override
			public void doWith(Shaderprogram shaderprogram) {
				// Do a complete rotation every 10 seconds.
				long time = SystemClock.uptimeMillis() % 10000L;
				float angleInDegrees = (360.0f / 10000.0f) * ((int) time);
				// Draw the triangle facing straight on.
				Matrix.setIdentityM(mModelMatrix, 0);
				Matrix.rotateM(mModelMatrix, 0, angleInDegrees, 0.0f, 0.0f, 1.0f);
				calculateModelViewProjectionMatrix();
			}
		});
		return triangle;
	}
	public Model createQuad(){
		String vertex = loadFile(R.raw.texturedquadvert);
		String fragment = loadFile(R.raw.texturedquadfrag);
		program = new Shaderprogram.ProgramBuilder()
				.addFragmentShader(fragment)
				.addVertexShader(vertex)
				.build();
		program.Disable(GLES30.GL_CULL_FACE);
		program.Disable(GLES30.GL_DEPTH_TEST);
		program.Enable(GLES30.GL_BLEND);
		program.BlendFunction(GLES30.GL_SRC_ALPHA,GLES30.GL_ONE_MINUS_SRC_ALPHA);

		final float[] triangle1 = {
				// pos X, Y, Z,
				// tex X, Y
				-0.5f, 0.5f, 0.0f,
				0.0f, 0.0f,

				-0.5f, -0.5f, 0.0f,
				0.0f, 1.0f,

				0.5f, 0.5f, 0.0f,
				1.0f, 0.0f,

				0.5f, -0.5f, 0.0f,
				1.0f, 1.0f,};
		VertexBuffer vbo = new VertexBuffer( Util.createBuffer(triangle1),GLES30.GL_DYNAMIC_DRAW);
		int[] recindex = {
				1,
				2,
				3,
				4,

		};
		ibo = new IndexBuffer(IntBuffer.wrap(recindex), GLES30.GL_DYNAMIC_DRAW);
		vao = new VertexArray();
		VertexBufferLayout vbl = new VertexBufferLayout();
		vbl.Push(3,Float.TYPE,"v_Position");
		vbl.Push(2,Float.TYPE,"a_TextCoord");
		vao.AddBuffer(vbo,vbl);
		Texture texture = new Texture(R.raw.thecherno,this.context);
		Model quad = new Model(vbo, vao, ibo,texture, program, new Model.OnDraw() {
			@Override
			public void doWith(Shaderprogram shaderprogram) {
				// Do a complete rotation every 10 seconds.
				long time = SystemClock.uptimeMillis() % 10000L;
				float angleInDegrees = (360.0f / 10000.0f) * ((int) time)* 5;
				// Draw the triangle facing straight on.
				Matrix.setIdentityM(mModelMatrix, 0);
				Matrix.translateM(mModelMatrix,0,0,1,0);
				//Matrix.rotateM(mModelMatrix, 0, -angleInDegrees, 0.0f, 0.0f, 1.0f);
				calculateModelViewProjectionMatrix();
			}
		});
		return quad;
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

		// Position the eye behind the origin.
		final float eyeX = 0.0f;
		final float eyeY = 0.0f;
		final float eyeZ = 1.5f;

		// We are looking toward the distance
		final float lookX = 0.0f;
		final float lookY = 0.0f;
		final float lookZ = -5.0f;

		// Set our up vector. This is where our head would be pointing were we holding the camera.
		final float upX = 0.0f;
		final float upY = 1.0f;
		final float upZ = 0.0f;

		// Set the view matrix. This matrix can be said to represent the camera position.
		// NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
		// view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
		Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
		mTriangle = createTriangle();
		mQuad = createQuad();

		// Set the background frame color
		GLES30.glClearColor(41/255f, 182/255f, 246/255f, 1.0f);


		final float[] rectangle ={
			// X, Y , Z, TexX, TexY
		};

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

		// Create a new perspective projection matrix. The height will stay the same
		// while the width will vary as per aspect ratio.
		final float ratio = (float) width / height;
		final float left = -ratio;
		final float right = ratio;
		final float bottom = -1.0f;
		final float top = 1.0f;
		final float near = 1.0f;
		final float far = 10.0f;

		Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);

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

		mQuad.bind();
		mQuad.draw();
		mTriangle.bind();
		mTriangle.draw();
	}
	private void calculateModelViewProjectionMatrix(){
		// This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        // (which currently contains model * view).
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        // (which now contains model * view * projection).
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
        program.SetUniformMaxtrixSquare("u_MVPMatrix",false,mMVPMatrix);
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
