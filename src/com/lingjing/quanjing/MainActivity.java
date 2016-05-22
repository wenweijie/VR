package com.lingjing.quanjing;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


public class MainActivity extends AppCompatActivity  implements SensorEventListener{


    private MyGLSurfaceView myGLSurfaceView;
    private String path;
    private SensorManager sensorManager;
    private Sensor sensor;

    private double timestamp;
    private static final float NS2S= 1.0f / 1000000000.0f;
    private static final float ANGLE_TO_PIXEL_SCALE_FACTOR_X = 15.2f;
    private static final float ANGLE_TO_PIXEL_SCALE_FACTOR_Y = 7.1f;
    float x,y,dAngleX,dAngleY;
    float[] preAngle={0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path=getIntent().getStringExtra("path");
        MyGLSurfaceView.setMedia(path);

        setContentView(R.layout.activity_main);
        myGLSurfaceView = (MyGLSurfaceView) findViewById(R.id.glSurfaceView);

        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }


    protected void onResume() {
        super.onResume();
        myGLSurfaceView.onResume();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_GYROSCOPE){
                if(timestamp!=0){
                   final float dT=(float) (event.timestamp -timestamp) * NS2S;
                    x+=event.values[0]*dT;
                    y+=event.values[1]*dT;

                    float angleX=(float)Math.toDegrees(y);
                    float angleY=(float)Math.toDegrees(x);
                    changeAngle(angleX,angleY);
                    preAngle[0] = angleX;
                    preAngle[1] = angleY;

                }
            timestamp=event.timestamp;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void changeAngle(float angleX,float angleY){
             dAngleX = angleX-preAngle[0];
             dAngleY = angleY-preAngle[1];

        float dx = -dAngleX*ANGLE_TO_PIXEL_SCALE_FACTOR_X;
        float dy=  dAngleY*ANGLE_TO_PIXEL_SCALE_FACTOR_Y;

            myGLSurfaceView.mRenderer.setAngleX(
            myGLSurfaceView.mRenderer.getAngleX()+dy*myGLSurfaceView.TOUCH_SCALE_FACTOR
            );
        myGLSurfaceView.mRenderer.setAngleY(
                myGLSurfaceView.mRenderer.getAngleY() +
                        (dx* myGLSurfaceView.TOUCH_SCALE_FACTOR));
        myGLSurfaceView.requestRender();

    }

    protected  void onDestroy(){
            super.onDestroy();
            sensorManager.unregisterListener(this,sensor);
    }

    protected void onPause(){
        super.onPause();
        myGLSurfaceView.onPause();
    }







}
