package com.example.jyace.xiangqi;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Jyace on 2016/5/12.
 */
public class XIActivity extends Activity {
    boolean isSound=true;
    MediaPlayer kaishi;
    MediaPlayer youxi;
    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            if(msg.what==1){initMenu();}
            if(msg.what==2){initGame();}
            if(msg.what==3){initHelp();}



        }



    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
kaishi=MediaPlayer.create(this,R.raw.kaisheng);
        kaishi.setLooping(true);
        youxi=MediaPlayer.create(this,R.raw.yousheng);
        youxi.setLooping(true);
this.initWelcome();

    }
    public void initWelcome(){

        this.setContentView(new welcome(this,this));
        if(isSound)kaishi.start();


    }
    public void initGame(){
        this.setContentView(new Game(this,this));




    }
    public void initMenu(){
        if(kaishi != null){
            kaishi.stop();//停止播放声音
            kaishi= null;
        }
        if(this.isSound){
            youxi.start();//播放声音
        }
        this.setContentView(new caidan(this,this));
        if(isSound)kaishi.start();



    }
    public void initHelp(){

        this.setContentView(new Help(this,this));



    }
}
