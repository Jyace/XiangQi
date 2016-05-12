package com.example.jyace.xiangqi;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class caidan extends SurfaceView implements SurfaceHolder.Callback {
	XIActivity activity;
	private TutorialThread thread;//ˢ֡�߳�
	Bitmap kai;//��ʼͼƬ
	Bitmap da;//������ͼƬ
	Bitmap guan;//�ر�������ͼƬ
	Bitmap help;//����ͼƬ
	Bitmap exit;//�˳�ͼƬ 
	public caidan(Context context,XIActivity activity) {
		super(context);
		this.activity = activity;
        getHolder().addCallback(this);
        this.thread = new TutorialThread(getHolder(), this);//����ˢ֡�߳�
        initBitmap();//ͼƬ��Դ��ʼ��
	}
	public void initBitmap(){//ͼƬ��ԴͼƬ��ʼ��
		kai = BitmapFactory.decodeResource(getResources(), R.drawable.kai);//��ʼ��ť
		da = BitmapFactory.decodeResource(getResources(), R.drawable.da);//��ʼ������ť
		guan = BitmapFactory.decodeResource(getResources(), R.drawable.guan);//�ر�������ť
		help = BitmapFactory.decodeResource(getResources(), R.drawable.help);//������ť
		exit = BitmapFactory.decodeResource(getResources(), R.drawable.exit);//�˳���ť
	}
	public void onDraw(Canvas canvas){//���Ʒ���
		canvas.drawColor(Color.BLACK);//����
		canvas.drawBitmap(kai, 50, 50, null);//����ͼƬ
		if(activity.isSound){//������ʱ���ر�����ͼƬ
			canvas.drawBitmap(guan, 50, 150, null);//�ر�����
		}else{//���ƴ�����ͼƬ
			canvas.drawBitmap(da, 50, 150, null);//��ʼ����
		}
		canvas.drawBitmap(help, 50, 250, null);//������ť
		canvas.drawBitmap(exit, 50, 350, null);//�˳���ť
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	public void surfaceCreated(SurfaceHolder holder) {//����ˢ֡
        this.thread.setFlag(true);//ѭ����־
        this.thread.start();//�����߳�
	}
	public void surfaceDestroyed(SurfaceHolder holder) {//�ͷ�ˢ֡�߳�
        boolean retry = true;//ѭ����־
        thread.setFlag(false);//ѭ����־
        while (retry) {//ѭ��
            try {
                thread.join();//�߳̽���
                retry = false;//ֹͣѭ��
            }catch (InterruptedException e){}//ѭ����ˢ֡�߳̽���
        }
	}
	public boolean onTouchEvent(MotionEvent event) {//������Ļ
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(event.getX()>105 && event.getX()<220
					&&event.getY()>60 && event.getY()<95){//��ʼ��Ϸ
				activity.handler.sendEmptyMessage(2);
			}else if(event.getX()>105 && event.getX()<220
					&&event.getY()>160 && event.getY()<195){//������ť
				activity.isSound = !activity.isSound;//ȡ����������
				if(!activity.isSound){
					if(activity.youxi != null){//�Ƿ����ڲ�������
						if(activity.youxi.isPlaying()){
							activity.youxi.pause();//ֹͣ����
						}	
					}
				}else{
					if(activity.youxi!= null){//������ʱ
						if(!activity.youxi.isPlaying()){//û�в��ŵ�����
							activity.youxi.start();//�򲥷���
						}	
					}
				}
			}else if(event.getX()>105 && event.getX()<220
					&&event.getY()>260 && event.getY()<295){//������ť
				activity.handler.sendEmptyMessage(3);
			}else if(event.getX()>105 && event.getX()<220
					&&event.getY()>360 && event.getY()<395){//�˳���Ϸ
				System.exit(0);//�˳�
			}
		}
		return super.onTouchEvent(event);
	}
	class TutorialThread extends Thread{//ˢ֡�߳�
		private int span = 500;//˯��500����
		private SurfaceHolder surfaceHolder;
		private caidan menuView;
		private boolean flag = false;//ѭ�����
        public TutorialThread(SurfaceHolder surfaceHolder, caidan menuView) {
            this.surfaceHolder = surfaceHolder;
            this.menuView = menuView;
        }
        public void setFlag(boolean flag) {//ѭ�����
        	this.flag = flag;
        }
		public void run() {//��д����
			Canvas c;//����
            while (this.flag) {//ѭ��
                c = null;
                try {
                	// ��������
                    c = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {//ͬ����
                    	menuView.onDraw(c);//���Ʒ���
                    }
                } finally {
                    if (c != null) {
                    	//������Ļ����
                        this.surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                try{
                	Thread.sleep(span);//˯��ʱ�䣬��λ����
                }catch(Exception e){//�����쳣
                	e.printStackTrace();//���ӡ�쳣��ջ��Ϣ
                }
            }
		}
	}
}