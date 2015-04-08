package munchkin.com.game_views;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by darkbobo on 4/8/15.
 */
public class MyGLSurfaceView extends GLSurfaceView {
    private MyGLRenderer renderer;
    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        renderer = new MyGLRenderer();
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
