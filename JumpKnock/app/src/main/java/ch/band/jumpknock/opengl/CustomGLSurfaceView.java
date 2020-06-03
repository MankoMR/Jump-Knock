package ch.band.manko.glrenderer.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class CustomGLSurfaceView extends GLSurfaceView {

	private final ch.band.manko.glrenderer.opengl.Renderer renderer;

	/**
	 * Standard View constructor. In order to render something, you
	 * must call {@link #setRenderer} to register a renderer.
	 *
	 * @param context
	 * @param attrs
	 */
	public CustomGLSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setEGLContextClientVersion(2);
		renderer = new ch.band.manko.glrenderer.opengl.Renderer(this.getContext());
		setRenderer(renderer);
	}


}

