package com.lingjing.quanjing;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Mainchoice extends AppCompatActivity {

    private Button choice;
    private long time=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainchoice);

        choice=(Button)findViewById(R.id.button);

        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent();
                intent.setType("image*//*;video*//*");//选择图片是image*//*  选择视频是video*//*  选择音频是audio*//* 都不用大写的！！！
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);*/
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                Intent wrapIntent=Intent.createChooser(intent,null);
                startActivityForResult(wrapIntent,1);

            }
        });
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String videoPath = uri.getPath();//path（在content下结果为/external/video/media/123241）不是真正的地址。。。


                if(uri.getScheme().toString().equalsIgnoreCase("content")) {
                    //这里我们要得到content的真正地址
                    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                    cursor.moveToFirst();//Content文件指针
                    videoPath = cursor.getString(1);//文件地址
                }

                Intent t = new Intent(Mainchoice.this, MainActivity.class);
                t.putExtra("path", videoPath);
                startActivity(t);

            }
        }

    }



    public boolean onKeyDown(int KeyCode,KeyEvent event){
        if(KeyCode== KeyEvent.KEYCODE_BACK){
                exit();
                return false;
        }
        return super.onKeyDown(KeyCode,event);


    }


    public void exit(){
        if((System.currentTimeMillis()-time)>2000){
            Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
            time=System.currentTimeMillis();
        }
        else{
            finish();
            System.exit(0);
        }


    }



}
