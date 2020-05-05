package ch.band.jumpknock.game.opengl;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

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
		setEGLContextClientVersion(3);
		renderer = new GameRenderer();
		setRenderer(renderer);
	}


}

