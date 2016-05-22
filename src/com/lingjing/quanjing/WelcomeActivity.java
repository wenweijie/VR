package com.lingjing.quanjing;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import static java.lang.Thread.sleep;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //写在了一个线程里面
        Thread startThread =new Thread(){
            public void run(){
                try{
                    sleep(2500);
                }catch(Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent=new Intent(WelcomeActivity.this,Mainchoice.class);
                    startActivity(intent);
                    WelcomeActivity.this.finish();
                }
            }

        };//自定义了一个Thread

        startThread.start();

    }

}
