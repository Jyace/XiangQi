package com.example.jyace.xiangqi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Jyace on 2016/5/12.
 */
public class welcome extends SurfaceView implements SurfaceHolder.Callback {

    XIActivity activity;
    private TutorialThread thread;//刷帧线程
    private huanyingThread moveThread;//移动物件线程
    Bitmap welcomebackage;//大背景
    Bitmap biao;
    Bitmap boy;//小孩图
    Bitmap oldboy;//老头图
    Bitmap bordbackground;//文字背景
    Bitmap biao2;
    Bitmap menu;//菜单按钮

    int biaoX = -120;//移动图片坐标初始化
    int boyX = -100;
    int oldboyX = -120;
    int biao2X = 320;
    int bordbackgroundY = -100;//背景y坐标
    int menuY = 520;//菜单y坐标
    public welcome(Context context, XIActivity activity) {
        super(context);
        this.activity = activity;//获取activity引用
        getHolder().addCallback(this);
        this.thread = new TutorialThread(getHolder(), this);//刷帧线程初始化
        this.moveThread = new huanyingThread(this);//移动图片线程初始化
        initBitmap();//初始化全部图片
    }
    public void initBitmap(){//初始化全部图片方法
        welcomebackage = BitmapFactory.decodeResource(getResources(), R.mipmap.huanying);
        biao = BitmapFactory.decodeResource(getResources(), R.mipmap.biao);
        boy = BitmapFactory.decodeResource(getResources(), R.mipmap.boy);
        oldboy = BitmapFactory.decodeResource(getResources(), R.mipmap.oldboy);
        bordbackground = BitmapFactory.decodeResource(getResources(), R.mipmap.bordbackground);
        biao2 = BitmapFactory.decodeResource(getResources(), R.mipmap.biao2);
        menu = BitmapFactory.decodeResource(getResources(), R.mipmap.menu);
    }

    public void onDraw(Canvas canvas){
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(welcomebackage, 0, 100, null);
        canvas.drawBitmap(biao, biaoX, 110, null);
        canvas.drawBitmap(boy, boyX, 210, null);
        canvas.drawBitmap(oldboy, oldboyX, 270, null);
        canvas.drawBitmap(bordbackground, 150, bordbackgroundY, null);
        canvas.drawBitmap(biao2, biao2X, 100, null);
        canvas.drawBitmap(menu, 200, menuY, null);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
    public void surfaceCreated(SurfaceHolder holder) {//创建启动进程
        this.thread.setFlag(true);//循环标志位
        this.thread.start();//启动线程

        this.moveThread.setFlag(true);//循环标志位
        this.moveThread.start();//启动线程
    }
    public void surfaceDestroyed(SurfaceHolder holder) {//撤销释放进程
        boolean retry = true;
        thread.setFlag(false);//循环标志位
        moveThread.setFlag(false);
        while (retry) {//循环
            try {
                thread.join();//线程结束
                moveThread.join();
                retry = false;//停止循环
            }
            catch (InterruptedException e) {//循环到刷帧线程结束
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {//监听屏幕
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(event.getX()>200 && event.getX()<200+menu.getWidth()
                    && event.getY()>355 && event.getY()<355+menu.getHeight()){//点击菜单按钮
                activity.handler.sendEmptyMessage(1);
            }
        }
        return super.onTouchEvent(event);
    }
    class TutorialThread extends Thread{//刷帧线程
        private int span = 100;//设置睡眠100毫秒数
        private SurfaceHolder surfaceHolder;//SurfaceHolder引用
        private welcome welcomeView;//WelcomeView引用
        private boolean flag = false;
        public TutorialThread(SurfaceHolder surfaceHolder, welcome welcomeView) {//构造器
            this.surfaceHolder = surfaceHolder;
            this.welcomeView = welcomeView;
        }
        public void setFlag(boolean flag) {//循环标记位
            this.flag = flag;
        }
        @Override
        public void run() {//重写方法
            Canvas c;//画布
            while (this.flag) {//循环
                c = null;
                try {
                    // 锁定画布
                    c = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {//同步
                        welcomeView.onDraw(c);//绘制
                    }
                } finally {
                    if (c != null) {
                        //更新屏幕显示内容
                        this.surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                try{
                    Thread.sleep(span);//睡眠时间
                }
                catch(Exception e){//捕获异常
                    e.printStackTrace();//输出堆栈信息
                }
            }
        }
    }
}