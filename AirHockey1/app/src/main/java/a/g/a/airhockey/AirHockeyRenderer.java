package a.g.a.airhockey;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class AirHockeyRenderer implements Renderer {

  private static final int POSITION_COMPONENT_COUNT = 2;

  @Override
  public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
    glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
  }

  @Override
  public void onSurfaceChanged(GL10 glUnused, int width, int height) {
// Set the OpenGL viewport to fill the entire surface.
    glViewport(0, 0, width, height);
  }

  @Override
  public void onDrawFrame(GL10 glUnused) {
  // Clear the rendering surface.
    glClear(GL_COLOR_BUFFER_BIT);
  }
}
