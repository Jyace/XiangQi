package com.example.jyace.xiangqi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Help extends SurfaceView implements SurfaceHolder.Callback {
	XIActivity activity;
	private TutorialThread thread;//ˢ֡�߳�
	Bitmap back;//����ͼ��ť
	Bitmap bei;//����ͼ
	public Help(Context context,XIActivity activity) {
		super(context);
		this.activity = activity;//�õ�activity����
        getHolder().addCallback(this);
        this.thread = new TutorialThread(getHolder(), this);//�ػ��̳߳�ʼ��
        initBitmap();//ͼƬ��Դ��ʼ��
	}
	public void initBitmap(){//��ʼ�����õ���ͼƬ
		back = BitmapFactory.decodeResource(getResources(), R.mipmap.back);//���ذ�ťͼ
		bei = BitmapFactory.decodeResource(
						getResources(), 
						R.mipmap.bei);//����ͼƬ��ʼ��
	}
	public void onDraw(Canvas canvas){//���Ʒ���
		canvas.drawBitmap(bei, 0, 90, new Paint());//���Ʊ���ͼ
		canvas.drawBitmap(back, 200, 370, new Paint());//���ư�ť
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
	public void surfaceCreated(SurfaceHolder holder) {//����ʱ����ˢ֡�߳�
        this.thread.setFlag(true);//ѭ����־
        this.thread.start();//ˢ֡�߳�
	}
	public void surfaceDestroyed(SurfaceHolder holder) {//ֹͣˢ֡�߳�
        boolean retry = true;//ѭ����־
        thread.setFlag(false);//ѭ����־λ
        while (retry) {
            try {
                thread.join();//�߳̽���
                retry = false;//ֹͣѭ��
            }catch (InterruptedException e){}//ѭ����ˢ֡�߳̽���
        }
	}
	public boolean onTouchEvent(MotionEvent event) {//������Ļ
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(event.getX()>200 && event.getX()<200+back.getWidth()
					&& event.getY()>370 && event.getY()<370+back.getHeight()){
				activity.handler.sendEmptyMessage(1);
			}
		}
		return super.onTouchEvent(event);
	} 
	class TutorialThread extends Thread{//ˢ֡�߳�
		private int span = 1000;//˯��1000������
		private SurfaceHolder surfaceHolder;
		private Help helpView;//���ø���
		private boolean flag = false;//ѭ�����? 
        public TutorialThread(SurfaceHolder surfaceHolder, Help helpView) {
            this.surfaceHolder = surfaceHolder;
            this.helpView = helpView;
        }
        public void setFlag(boolean flag) {//ѭ�����?
        	this.flag = flag;
        }
		public void run() {//��д����
			Canvas c;//����
            while (this.flag) {//ѭ��
                c = null;
                try {
                    c = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {//ͬ��
                    	helpView.onDraw(c);//���Ʒ���
                    }
                } finally {
                    if (c != null) {//������Ļ��ʾ
                        this.surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                try{
                	Thread.sleep(span);//����˯��ʱ�䣬��λ����
                }catch(Exception e){//�����쳣
                	e.printStackTrace();//����쳣��ջ���?
                }
            }
		}
	}
}