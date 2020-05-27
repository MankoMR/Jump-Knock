package ch.band.jumpknock.game.basicgl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;


public class GameSurfaceView extends GLSurfaceView {

	private final GameRenderer renderer;

	/**
	 * Standard View constructor. In order to render something, you
	 * must call {@link #setRenderer} to register a renderer.
	 *
	 * @param context
	 * @param attrs
	 */
	public GameSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setEGLContextFactory(new ContextFactory());
		setEGLContextClientVersion(3);
		renderer = new GameRenderer(this.getContext());
		setRenderer(renderer);
		// Render the view only when there is a change in the drawing data
		//setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

	}
	private static double glVersion = 3.0;

	private static class ContextFactory implements GLSurfaceView.EGLContextFactory {
		private static final String TAG = ContextFactory.class.getCanonicalName();
		private static int EGL_CONTEXT_CLIENT_VERSION = 0x3098;

		@Override
		public EGLContext createContext(
				EGL10 egl, EGLDisplay display, EGLConfig eglConfig) {

			Log.w(TAG, "creating OpenGL ES " + glVersion + " context");
			int[] attrib_list = {EGL_CONTEXT_CLIENT_VERSION, (int) glVersion,
					EGL10.EGL_NONE };
			// attempt to create a OpenGL ES 3.0 context
			EGLContext context = egl.eglCreateContext(
					display, eglConfig, EGL10.EGL_NO_CONTEXT, attrib_list);
			return context; // returns null if 3.0 is not supported;
		}

		@Override
		public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {

		}
	}


}

