package com.example.jyace.xiangqi;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Game extends SurfaceView implements SurfaceHolder.Callback{
	private TutorialThread thread;//ˢ֡�߳�
	shijianThread timeThread ;
	XIActivity activity;
	Bitmap qiPan;//����
	Bitmap qibei;//���ӱ���ͼ
	Bitmap win;//ʤ��ͼ
	Bitmap bai;//ʧ��ͼ
	Bitmap ok;//ȷ����ť
	Bitmap sheng;//�ڷ��췽sheng��ͼ
	Bitmap right;//����ָ��
	Bitmap left;//����ָ�� 
	Bitmap current;//����ǰ������
	Bitmap exit2;//�˳�ͼ
	Bitmap sound2;//����ͼ
	Bitmap sound3;//�Ƿ񲥷�����
	Bitmap time;//ð��
	Bitmap redtime;//��ð��
	Bitmap background;//����ͼ
	MediaPlayer go;//������	
	Paint paint;//����
	boolean caiPan = true;//�������?
	boolean de = false;//�Ƿ�ѡ������
	int selectqizi = 0; //����ѡ�е�����

	int startI, startJ;//��ǰ���ӿ�ʼλ��
	int endI, endJ;//��ǰ����Ŀ��λ��
	Bitmap[] heiZi = new Bitmap[7];//����ͼƬ����
	Bitmap[] hongZi = new Bitmap[7];//����ͼƬ����
	Bitmap[] number = new Bitmap[10];//����ͼƬ������ʾʱ�� 
	Bitmap[] redNumber = new Bitmap[10];//������ͼƬ��ʾʱ�� 
	
	GuiZe guiZe;//������

	int status = 0;//Ϸ״̬��0��Ϸ�У�1ʤ��, 2ʧ��
	int heiTime = 0;//�ڷ�˼��ʱ��
	int hongTime = 0;//�췽��˼��ʱ�� 

	int[][] qizi = new int[][]{//����
		{2,3,6,5,1,5,6,3,2},
		{0,0,0,0,0,0,0,0,0},
		{0,4,0,0,0,0,0,4,0},
		{7,0,7,0,7,0,7,0,7},
		{0,0,0,0,0,0,0,0,0},

		{0,0,0,0,0,0,0,0,0},
		{14,0,14,0,14,0,14,0,14},
		{0,11,0,0,0,0,0,11,0},
		{0,0,0,0,0,0,0,0,0},
		{9,10,13,12,8,12,13,10,9},
	};
	
	public Game(Context context,XIActivity activity) {//������
		super(context);
		this.activity = activity;//�õ�Activity������
		getHolder().addCallback(this);
		go  = MediaPlayer.create(this.getContext(), R.raw.go);//��������
		this.thread = new TutorialThread(getHolder(), this);//ˢ֡�̳߳�ʼ��
		this.timeThread = new shijianThread(this);//��ʼ��˼��ʱ����߳�
		init();//��Դ��ʼ��
		guiZe = new GuiZe();//�������ʼ��
	}
	
	public void init(){//��ʼ��
		paint = new Paint();//���ʳ�ʼ��
		qiPan = BitmapFactory.decodeResource(getResources(), R.drawable.qipan);//����ͼ
		qibei = BitmapFactory.decodeResource(getResources(), R.drawable.qizi);//���ӱ���
		win = BitmapFactory.decodeResource(getResources(), R.drawable.win);//ʤ��ͼ
		bai = BitmapFactory.decodeResource(getResources(), R.drawable.bai);//ʧ��ͼ
		ok = BitmapFactory.decodeResource(getResources(), R.drawable.ok);//ȷ����ťͼ
		sheng = BitmapFactory.decodeResource(getResources(), R.drawable.sheng);//ʤ��ͼ
		right = BitmapFactory.decodeResource(getResources(), R.drawable.right);//����ָ��
		left = BitmapFactory.decodeResource(getResources(), R.drawable.left);//����ָ��
		current = BitmapFactory.decodeResource(getResources(), R.drawable.current);
		exit2 = BitmapFactory.decodeResource(getResources(), R.drawable.exit2);//�˳�ͼ
		sound2 = BitmapFactory.decodeResource(getResources(), R.drawable.sound2);//����ͼ
		time = BitmapFactory.decodeResource(getResources(), R.drawable.time);//��ð��
		redtime = BitmapFactory.decodeResource(getResources(), R.drawable.redtime);//��ð��
		sound3 = BitmapFactory.decodeResource(getResources(), R.drawable.sound3);
		
		heiZi[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heishuai);//��˧
		heiZi[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heiju);//�ڳ�
		heiZi[2] = BitmapFactory.decodeResource(getResources(), R.drawable.heima);//����
		heiZi[3] = BitmapFactory.decodeResource(getResources(), R.drawable.heipao);//����
		heiZi[4] = BitmapFactory.decodeResource(getResources(), R.drawable.heishi);//��ʿ
		heiZi[5] = BitmapFactory.decodeResource(getResources(), R.drawable.heixiang);//����
		heiZi[6] = BitmapFactory.decodeResource(getResources(), R.drawable.heibing);//�ڱ�
		
		hongZi[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hongjiang);//�콫
		hongZi[1] = BitmapFactory.decodeResource(getResources(), R.drawable.hongju);//�쳵
		hongZi[2] = BitmapFactory.decodeResource(getResources(), R.drawable.hongma);//����
		hongZi[3] = BitmapFactory.decodeResource(getResources(), R.drawable.hongpao);//��h
		hongZi[4] = BitmapFactory.decodeResource(getResources(), R.drawable.hongshi);//����
		hongZi[5] = BitmapFactory.decodeResource(getResources(), R.drawable.hongxiang);//����
		hongZi[6] = BitmapFactory.decodeResource(getResources(), R.drawable.hongzu);//����
		
		number[0] = BitmapFactory.decodeResource(getResources(), R.drawable.number0);//��ɫ����0
		number[1] = BitmapFactory.decodeResource(getResources(), R.drawable.number1);//��ɫ����1
		number[2] = BitmapFactory.decodeResource(getResources(), R.drawable.number2);//��ɫ����2
		number[3] = BitmapFactory.decodeResource(getResources(), R.drawable.number3);//��ɫ����3
		number[4] = BitmapFactory.decodeResource(getResources(), R.drawable.number4);//��ɫ����4
		number[5] = BitmapFactory.decodeResource(getResources(), R.drawable.number5);//��ɫ����5
		number[6] = BitmapFactory.decodeResource(getResources(), R.drawable.number6);//��ɫ����6
		number[7] = BitmapFactory.decodeResource(getResources(), R.drawable.number7);//��ɫ����7
		number[8] = BitmapFactory.decodeResource(getResources(), R.drawable.number8);//��ɫ����8
		number[9] = BitmapFactory.decodeResource(getResources(), R.drawable.number9);//��ɫ����9
		
		redNumber[0] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber0);//��ɫ����0
		redNumber[1] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber1);//��ɫ����1
		redNumber[2] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber2);//��ɫ����2
		redNumber[3] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber3);//��ɫ����3
		redNumber[4] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber4);//��ɫ����4
		redNumber[5] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber5);//��ɫ����5
		redNumber[6] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber6);//��ɫ����6
		redNumber[7] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber7);//��ɫ����7
		redNumber[8] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber8);//��ɫ����8
		redNumber[9] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber9);//��ɫ����9
		
		background = BitmapFactory.decodeResource(getResources(), R.drawable.bei);
		
		
	}

	public void onDraw(Canvas canvas){//�������ݻ�����Ļ����
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(background, 0,0, null);//�屳��
		canvas.drawBitmap(qiPan, 10, 10, null);//������	
		for(int i=0; i<qizi.length; i++){
			for(int j=0; j<qizi[i].length; j++){//������
				if(qizi[i][j] != 0){
					canvas.drawBitmap(qibei, 9+j*34, 10+i*35, null);//�����ӱ���					
					if(qizi[i][j] == 1){//��˧ʱ
						canvas.drawBitmap(heiZi[0], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 2){//�ڳ�ʱ
						canvas.drawBitmap(heiZi[1], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 3){//����ʱ
						canvas.drawBitmap(heiZi[2], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 4){//����ʱ
						canvas.drawBitmap(heiZi[3], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 5){//��ʿʱ
						canvas.drawBitmap(heiZi[4], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 6){//����ʱ
						canvas.drawBitmap(heiZi[5], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 7){//�ڱ�ʱ
						canvas.drawBitmap(heiZi[6], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 8){//Ϊ�콫ʱ
						canvas.drawBitmap(hongZi[0], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 9){//�쳵ʱ
						canvas.drawBitmap(hongZi[1], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 10){//����ʱ
						canvas.drawBitmap(hongZi[2], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 11){//��hʱ
						canvas.drawBitmap(hongZi[3], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 12){//����ʱ
						canvas.drawBitmap(hongZi[4], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 13){//����ʱ
						canvas.drawBitmap(hongZi[5], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 14){//����ʱ
						canvas.drawBitmap(hongZi[6], 12+j*34, 13+i*35, paint);
					}
				}
			}
		}
		canvas.drawBitmap(sheng, 10, 360, paint);//������������ͼ
		//���ƺڷ���ʱ��
		canvas.drawBitmap(time, 81, 411, paint);//ð��
		int temp = this.heiTime/60;//ʱ�任��
		String timeStr = temp+"";//תΪ�ַ���
		if(timeStr.length()<2){//������λʱǰ����0
			timeStr = "0" + timeStr;
		}
    	for(int i=0;i<2;i++){//ѭ������ʱ��
    		int tempScore=timeStr.charAt(i)-'0';
    		canvas.drawBitmap(number[tempScore], 65+i*7, 412, paint);
    	}
    	//���Ʒ���
    	temp = this.heiTime%60;
		timeStr = temp+"";//ת���ַ���
		if(timeStr.length()<2){   
			timeStr = "0" + timeStr;//����С��2ʱ��ǰ�����һ��0
		}
    	for(int i=0;i<2;i++){//ѭ��
    		int tempScore=timeStr.charAt(i)-'0';
    		canvas.drawBitmap(number[tempScore], 85+i*7, 412, paint);//����
    	}
    	//���ƺ췽ʱ��
		canvas.drawBitmap(this.redtime, 262, 410, paint);//��ð��
		int temp2 = this.hongTime/60;//����ʱ��
		String timeStr2 = temp2+"";//ת���ַ���
		if(timeStr2.length()<2){//������λʱǰ����0
			timeStr2 = "0" + timeStr2;
		}
    	for(int i=0;i<2;i++){//ѭ������ʱ��
    		int tempScore=timeStr2.charAt(i)-'0';
    		canvas.drawBitmap(redNumber[tempScore], 247+i*7, 411, paint);//����
    	}
    	//���Ʒ���
    	temp2 = this.hongTime%60;//��ǰ����
		timeStr2 = temp2+"";//ת�����ַ���
		if(timeStr2.length()<2){//������λʱǰ����0��
			timeStr2 = "0" + timeStr2;
		}
    	for(int i=0;i<2;i++){//ѭ������
    		int tempScore=timeStr2.charAt(i)-'0';
    		canvas.drawBitmap(redNumber[tempScore], 267+i*7, 411, paint);//����ʱ������
    	}
		if(caiPan == true){
			canvas.drawBitmap(right, 155, 420, paint);//����ָ��
		}
		else{//�ڷ����壬����������ʱ
			canvas.drawBitmap(left, 120, 420, paint);//����ָ��
		}
		
		canvas.drawBitmap(current, 138, 445, paint);//��ǰ����
		canvas.drawBitmap(sound2, 10, 440, paint);//��������
		if(activity.isSound){//������ڲ������������
			canvas.drawBitmap(sound3, 80, 452, paint);
		}
		
		canvas.drawBitmap(exit2, 250, 440, paint);//�����˳���ť
		if(status == 1){//ʤ��ʱ
			canvas.drawBitmap(win, 85, 150, paint);//����ʤ��ͼƬ
			canvas.drawBitmap(ok, 113, 240, paint);
		}
		if(status == 2){//ʧ��
			canvas.drawBitmap(bai, 85, 150, paint);//����ʧ�ܽ���
			canvas.drawBitmap(ok, 113, 236, paint);	
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {//��Ļ������д
		if(event.getAction() == MotionEvent.ACTION_DOWN){//��갴���¼�
			if(event.getX()>10&&event.getX()<10+sound2.getWidth()
					&& event.getY()>440 && event.getY()<440+sound2.getHeight()){//����������ť
				activity.isSound = !activity.isSound;
				if(activity.isSound){//����������ʱ
					if(activity.youxi != null){
						if(!activity.youxi.isPlaying()){
			    			activity.youxi.start();//��������
			    		}
					}
				}
				else{
					if(activity.youxi != null){
						if(activity.youxi.isPlaying()){
							activity.youxi.pause();//ֹͣ����
						}
					} 
				}
			}
			if(event.getX()>250&&event.getX()<250+exit2.getWidth()
					&& event.getY()>440 && event.getY()<440+exit2.getHeight()){//�����˳���ť
				activity.handler.sendEmptyMessage(1);
			}
			if(status == 1){//ʤ��
				if(event.getX()>135&&event.getX()<190
						&& event.getY()>249 && event.getY()<269){
					activity.handler.sendEmptyMessage(1);//������Ϣ
				}
			}
			else if(status == 2){//ʧ�ܺ�
				if(event.getX()>135&&event.getX()<190
						&& event.getY()>245 && event.getY()<265){//�����ȷ����ť
					activity.handler.sendEmptyMessage(1);//������Ϣ���л���MenuView
				}
			}

	
			else if(status == 0){//��Ϸ��ʱ
				if(event.getX()>10&&event.getX()<310
						&& event.getY()>10 && event.getY()<360){//���λ����������
						if(caiPan == true){//����Ǹ��������
							int i = -1, j = -1;
							int[] pos = getPos(event);//�������껻������ڵ��к���
							i = pos[0];
							j = pos[1];
							if(de == false){//ǰ��û��ѡ�е�����
								if(qizi[i][j] != 0){//�����λ��������
									if(qizi[i][j] > 7){//��������Լ�������
										selectqizi = qizi[i][j];//��Ϊѡ�е�����
										de = true;//�����ѡ�е�����
										startI = i;
										startJ = j;
									}
								}
							}
							else{//֮ǰѡ�й�����
								if(qizi[i][j] != 0){//�����λ��������
									if(qizi[i][j] > 7){//������Լ�������.
										selectqizi = qizi[i][j];//����������Ϊѡ�е�����
										startI = i;
										startJ = j;
									}
									else{//����ǶԷ�������
										endI = i;
										endJ = j;//�����
										boolean canMove = guiZe.canMove(qizi, startI, startJ, endI, endJ);
										if(canMove){//�����ƶ���ȥ
											caiPan = false;//���������
											if(qizi[endI][endJ] == 1 || qizi[endI][endJ] == 8){//�ǡ�˧���򡰽���
												this.success();//ʤ��
											}
											else{
												if(activity.isSound){
													go.start();//����������
												}
												qizi[endI][endJ] = qizi[startI][startJ];//�ƶ�����
												qizi[startI][startJ] = 0;
												startI = -1;
												startJ = -1;
												endI = -1;
												endJ = -1;//��ԭ�����
												de = false;//��ǰû��ѡ������
												
												Move cm = guiZe.searchAGoodMove(qizi);
												if(activity.isSound){
													go.start();//����������
												}
												qizi[cm.toX][cm.toY] = qizi[cm.qiX][cm.qiY];//�ƶ�����
												qizi[cm.qiX][cm.qiY] = 0;
												caiPan = true;
											}
										}
									}
								}
								else{//���û������
									endI = i;
									endJ = j;							
									boolean canMove = guiZe.canMove(qizi, startI, startJ, endI, endJ);//�鿴�Ƿ����
									if(canMove){//��������ƶ�
										caiPan = false;//���������
										if(activity.isSound){
											go.start();//����������
										}
										qizi[endI][endJ] = qizi[startI][startJ];//������
										qizi[startI][startJ] = 0;//�ÿ�ԭ��λ��
										startI = -1;
										startJ = -1;
										endI = -1;
										endJ = -1;//��ԭ�����
										de = false;//��־Ϊfalse

										Move cm = guiZe.searchAGoodMove(qizi);//�õ��߷� 
										if(qizi[cm.toX][cm.toY] == 8){//���˽�
											status = 2;//ʧ��
										}
										if(activity.isSound){
											go.start();
										}
										qizi[cm.toX][cm.toY] = qizi[cm.qiX][cm.qiY];//�ƶ�����
										qizi[cm.qiX][cm.qiY] = 0;
										caiPan = true;
									}
								}
							}
						}
					}
			}
		}
		return super.onTouchEvent(event);
	}
	
	public int[] getPos(MotionEvent e){//����ת��������ά��
		int[] pos = new int[2];
		double x = e.getX();//���λ��x����
		double y = e.getY();//���λ��y����
		if(x>10 && y>10 && x<10+qiPan.getWidth() && y<10+qiPan.getHeight()){//�������ʱ
			pos[0] = Math.round((float)((y-21)/36));//��ȡ������
			pos[1] = Math.round((float)((x-21)/35));//��ȡ������
		}
		else{//����Ĳ�������ʱ
			pos[0] = -1;//����λ�ò�����
			pos[1] = -1;
		}
		return pos;//������������
	}
	
	public void success(){//ʤ��
		status = 1;//����ʤ��״̬
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {//��д
        this.thread.setFlag(true);
        this.thread.start();//ˢ֡�߳�
        timeThread.setFlag(true);
        timeThread.start();//˼��ʱ����߳�
	}

	public void surfaceDestroyed(SurfaceHolder holder) {//view���ͷ�ʱ���õ�
        boolean retry = true;
        thread.setFlag(false);//ֹͣˢ֡�߳�
        timeThread.setFlag(false);//ֹͣ˼��ʱ���߳�
        while (retry) {
            try {
                thread.join();
                timeThread.join();//�ȴ��߳̽���
                retry = false;//����ѭ����־λΪfalse
            } 
            catch (InterruptedException e) {//ѭ�����߳̽���
            }
        }
	}
	class TutorialThread extends Thread{//ˢ֡�߳�
		private int span = 300;//˯��300������
		private SurfaceHolder surfaceHolder;
		private Game gameView;
		private boolean flag = false;//ѭ����־
        public TutorialThread(SurfaceHolder surfaceHolder, Game gameView) {
            this.surfaceHolder = surfaceHolder;
            this.gameView = gameView;
        }
        public void setFlag(boolean flag) {//ѭ�����
        	this.flag = flag;
        }
		public void run() {//��д�ķ���
			Canvas c;//����
            while (this.flag) {//ѭ������
                c = null;
                try {
                    c = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {
                    	gameView.onDraw(c);//���Ʒ���
                    }
                } finally {
                    if (c != null) {
                    	//������Ļ����
                        this.surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                try{
                	Thread.sleep(span);//˯��ʱ�䣬��λ�Ǻ���
                }catch(Exception e){
                	e.printStackTrace();//����쳣��ջ��Ϣ
                }
            }
		}
	}
}