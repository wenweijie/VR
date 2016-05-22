package com.lingjing.quanjing;


        import com.lingjing.quanjing.R;
        import android.annotation.SuppressLint;
        import android.content.Context;
        import android.content.res.AssetFileDescriptor;
        import android.content.res.Resources;
        import android.hardware.Sensor;
        import android.hardware.SensorEvent;
        import android.hardware.SensorEventListener;
        import android.hardware.SensorManager;
        import android.media.MediaPlayer;
        import android.net.Uri;
        import android.opengl.GLSurfaceView;
        import android.util.AttributeSet;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.MotionEvent;

        import java.io.IOException;


@SuppressLint("ViewConstructor")
public class MyGLSurfaceView extends GLSurfaceView {
    protected Resources mResources;
    protected SphereVideoRenderer mRenderer;
    private MediaPlayer mMediaPlayer;
    protected final float TOUCH_SCALE_FACTOR = 180.0f / 320 / 3.8f;
    private float mPreviousX;
    private float mPreviousY;
    private float tPreviousX;
    private float tPreviousY;
    private static String videoString;
    //private SensorManager sensorManager;


    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Create an OpenGL ES 2.0 contextd
        setEGLContextClientVersion(2);
        mMediaPlayer = new MediaPlayer();
        mResources = context.getResources();
;

        try {
 /*       AssetFileDescriptor afd = mResources.openRawResourceFd(R.raw.boat);
        mMediaPlayer.setDataSource(
                afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        afd.close();*/  //这种方法用于raw资源

            //overwrite
            mMediaPlayer.setDataSource(videoString);//可以通过路径直接读取手机视频
        } catch (Exception e) {
            Log.e("false", e.getMessage(), e);
        }
        mRenderer = new SphereVideoRenderer(context, mMediaPlayer);
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
    }


    /*
    设置视频路径
     */
    public static void setMedia(String path) {
        videoString = path;
        //            mMediaPlayer.reset();
       /* try {
            mMediaPlayer.setDataSource(videoString);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mPreviousX;
                float dy = y - mPreviousY;
                mRenderer.setAngleX(
                        mRenderer.getAngleX() +
                                (dx * TOUCH_SCALE_FACTOR));
                mRenderer.setAngleY(
                        mRenderer.getAngleY() +
                                (dy * TOUCH_SCALE_FACTOR));
                requestRender();
        }
        mPreviousX = x;
        mPreviousY = y;
        if(mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
        }else {
            mMediaPlayer.start();
        }

        return true;
    }


    public void onResume() {
        queueEvent(new Runnable() {
            public void run() {
                mRenderer.setMediaPlayer(mMediaPlayer);
            }
        });

        super.onResume();
    }

    public  void onPause(){
        super.onPause();
        mMediaPlayer.release();

    }





}

