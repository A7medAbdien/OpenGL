# Learning OpenGL

</br>
</br>

# Base app - Full red screen

## Why Do We Need to Continually Clear the Screen?

Back in the days when everything was rendered in software, it usually was wasteful
to clear the screen. Developers would optimize by assuming that everything would
get painted over, so there would be no need to wipe away stuff from the previous
frame. They did this to save processing time that would otherwise have been wasted.
This sometimes led to the famous “hall of mirrors” effect, as seen in games such as
Doom: the resulting visual effect was like being in the middle of a hall of mirrors,
with old content repeated over and over.

This optimization is no longer useful today. The latest GPUs work differently, and
they use special rendering techniques that can actually work faster if the screen is
cleared. By telling the GPU to clear the screen, we save time that would have been
wasted on copying over the previous frame. Because of the way that GPUs work today,
clearing the screen also helps to avoid problems like flickering or stuff not getting
drawn. Preserving old content can lead to unexpected and undesirable results.

</br>
</br>

## TextureViews

Behind the scenes, a GLSurfaceView actually creates its own window and punches a
“hole” in the view hierarchy to allow the underlying OpenGL surface to be displayed.
For many uses, this is good enough; however, since the GLSurfaceView is part of a separate window,
 it doesn’t animate or transform as well as a regular view.

Starting with Android 4.0 Ice Cream Sandwich, Android provides a TextureView that
can be used to render OpenGL without having a separate window or hole punching,
which means that the view can be manipulated, animated, and transformed as well
as any regular Android view. Since there’s no OpenGL initialization built into the
TextureView class, one way of using a TextureView is by performing your own OpenGL
initialization and running that on top of a TextureView; another is to grab the source
code of GLSurfaceView and adapt it onto a TextureView.

</br>
</br>

## Renderer Class

Now we’re going to define a Renderer so that we can start clearing the screen.
Let’s take a quick overview of the methods defined by the Renderer interface:

### onSurfaceCreated(GL10 glUnused, EGLConfig config): 
  
GLSurfaceView calls this when the surface is created. This happens the first
time our application is run, and it may also be called when the device
wakes up or when the user switches back to our activity. In practice, this
means that this method may be called multiple times while our application
is running. 

### onSurfaceChanged(GL10 glUnused, int width, int height):

GLSurfaceView calls this after the surface is created and whenever the size
has changed. A size change can occur when switching from portrait to
landscape and vice versa.

### onDrawFrame(GL10 glUnused):

GLSurfaceView calls this when it’s time to draw a frame. We must draw
something, even if it’s only to clear the screen. The rendering buffer will
be swapped and displayed on the screen after this method returns, so if
we don’t draw anything, we’ll probably get a bad flickering effect.

### unused arguments:

What’s going on with those unused arguments of type GL10? This is a vestige
of the OpenGL ES 1.0 API. We would use this parameter if we were writing
an OpenGL ES 1.0 renderer, but for OpenGL ES 2.0, we call static methods
on the GLES20 class instead.

</br>
</br>

## Rendering in a Background Thread

The renderer methods will be called on a separate thread by the GLSurfaceView. The
GLSurfaceView will render continuously by default, usually at the display’s refresh rate,
but we can also configure the surface view to render only on request by calling
GLSurfaceView.setRenderMode(), with GLSurfaceView.RENDERMODE_WHEN_DIRTY as the argument.
Since Android’s GLSurfaceView does rendering in a background thread, we must be
careful to call OpenGL only within the rendering thread, and Android UI calls only
within Android’s main thread. We can call queueEvent() on our instance of GLSurfaceView
to post a Runnable on the background rendering thread. From within the rendering
thread, we can call runOnUIThread() on our activity to post events on the main thread.

</br>
</br>


# Defining Vertices and Shaders 

build objects by using a set of independent points known as **vertices**,

draw these objects by using **shaders**, small programs that tell OpenGL how to draw an object. 