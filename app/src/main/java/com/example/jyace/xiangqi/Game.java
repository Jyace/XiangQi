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
	private TutorialThread thread;//刷帧线程
	shijianThread timeThread ;
	XIActivity activity;
	Bitmap qiPan;//棋盘
	Bitmap qibei;//棋子背景图
	Bitmap win;//胜利图
	Bitmap bai;//失败图
	Bitmap ok;//确定按钮
	Bitmap sheng;//黑方红方sheng的图
	Bitmap right;//向右指针
	Bitmap left;//向左指针 
	Bitmap current;//“当前”文字
	Bitmap exit2;//退出图
	Bitmap sound2;//声音图
	Bitmap sound3;//是否播放声音
	Bitmap time;//冒号
	Bitmap redtime;//红冒号
	Bitmap background;//背景图
	MediaPlayer go;//下棋声	
	Paint paint;//画笔
	boolean caiPan = true;//玩家走棋?
	boolean de = false;//是否选中棋子
	int selectqizi = 0; //现在选中的棋子

	int startI, startJ;//当前棋子开始位置
	int endI, endJ;//当前棋子目标位置
	Bitmap[] heiZi = new Bitmap[7];//黑子图片数组
	Bitmap[] hongZi = new Bitmap[7];//红子图片数组
	Bitmap[] number = new Bitmap[10];//数字图片数组显示时间 
	Bitmap[] redNumber = new Bitmap[10];//红数字图片显示时间 
	
	GuiZe guiZe;//规则类

	int status = 0;//戏状态，0游戏中，1胜利, 2失败
	int heiTime = 0;//黑方思考时间
	int hongTime = 0;//红方共思考时间 

	int[][] qizi = new int[][]{//棋盘
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
	
	public Game(Context context,XIActivity activity) {//构造器
		super(context);
		this.activity = activity;//得到Activity的引用
		getHolder().addCallback(this);
		go  = MediaPlayer.create(this.getContext(), R.raw.go);//下棋声音
		this.thread = new TutorialThread(getHolder(), this);//刷帧线程初始化
		this.timeThread = new shijianThread(this);//初始化思考时间的线程
		init();//资源初始化
		guiZe = new GuiZe();//规则类初始化
	}
	
	public void init(){//初始化
		paint = new Paint();//画笔初始化
		qiPan = BitmapFactory.decodeResource(getResources(), R.drawable.qipan);//棋盘图
		qibei = BitmapFactory.decodeResource(getResources(), R.drawable.qizi);//棋子背景
		win = BitmapFactory.decodeResource(getResources(), R.drawable.win);//胜利图
		bai = BitmapFactory.decodeResource(getResources(), R.drawable.bai);//失败图
		ok = BitmapFactory.decodeResource(getResources(), R.drawable.ok);//确定按钮图
		sheng = BitmapFactory.decodeResource(getResources(), R.drawable.sheng);//胜利图
		right = BitmapFactory.decodeResource(getResources(), R.drawable.right);//向右指针
		left = BitmapFactory.decodeResource(getResources(), R.drawable.left);//向左指针
		current = BitmapFactory.decodeResource(getResources(), R.drawable.current);
		exit2 = BitmapFactory.decodeResource(getResources(), R.drawable.exit2);//退出图
		sound2 = BitmapFactory.decodeResource(getResources(), R.drawable.sound2);//声音图
		time = BitmapFactory.decodeResource(getResources(), R.drawable.time);//黑冒号
		redtime = BitmapFactory.decodeResource(getResources(), R.drawable.redtime);//红冒号
		sound3 = BitmapFactory.decodeResource(getResources(), R.drawable.sound3);
		
		heiZi[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heishuai);//黑帅
		heiZi[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heiju);//黑车
		heiZi[2] = BitmapFactory.decodeResource(getResources(), R.drawable.heima);//黑马
		heiZi[3] = BitmapFactory.decodeResource(getResources(), R.drawable.heipao);//黑炮
		heiZi[4] = BitmapFactory.decodeResource(getResources(), R.drawable.heishi);//黑士
		heiZi[5] = BitmapFactory.decodeResource(getResources(), R.drawable.heixiang);//黑象
		heiZi[6] = BitmapFactory.decodeResource(getResources(), R.drawable.heibing);//黑兵
		
		hongZi[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hongjiang);//红将
		hongZi[1] = BitmapFactory.decodeResource(getResources(), R.drawable.hongju);//红车
		hongZi[2] = BitmapFactory.decodeResource(getResources(), R.drawable.hongma);//红马
		hongZi[3] = BitmapFactory.decodeResource(getResources(), R.drawable.hongpao);//红砲
		hongZi[4] = BitmapFactory.decodeResource(getResources(), R.drawable.hongshi);//红仕
		hongZi[5] = BitmapFactory.decodeResource(getResources(), R.drawable.hongxiang);//红相
		hongZi[6] = BitmapFactory.decodeResource(getResources(), R.drawable.hongzu);//红卒
		
		number[0] = BitmapFactory.decodeResource(getResources(), R.drawable.number0);//黑色数字0
		number[1] = BitmapFactory.decodeResource(getResources(), R.drawable.number1);//黑色数字1
		number[2] = BitmapFactory.decodeResource(getResources(), R.drawable.number2);//黑色数字2
		number[3] = BitmapFactory.decodeResource(getResources(), R.drawable.number3);//黑色数字3
		number[4] = BitmapFactory.decodeResource(getResources(), R.drawable.number4);//黑色数字4
		number[5] = BitmapFactory.decodeResource(getResources(), R.drawable.number5);//黑色数字5
		number[6] = BitmapFactory.decodeResource(getResources(), R.drawable.number6);//黑色数字6
		number[7] = BitmapFactory.decodeResource(getResources(), R.drawable.number7);//黑色数字7
		number[8] = BitmapFactory.decodeResource(getResources(), R.drawable.number8);//黑色数字8
		number[9] = BitmapFactory.decodeResource(getResources(), R.drawable.number9);//黑色数字9
		
		redNumber[0] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber0);//红色数字0
		redNumber[1] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber1);//红色数字1
		redNumber[2] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber2);//红色数字2
		redNumber[3] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber3);//红色数字3
		redNumber[4] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber4);//红色数字4
		redNumber[5] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber5);//红色数字5
		redNumber[6] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber6);//红色数字6
		redNumber[7] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber7);//红色数字7
		redNumber[8] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber8);//红色数字8
		redNumber[9] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber9);//红色数字9
		
		background = BitmapFactory.decodeResource(getResources(), R.drawable.bei);
		
		
	}

	public void onDraw(Canvas canvas){//根据数据绘制屏幕方法
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(background, 0,0, null);//清背景
		canvas.drawBitmap(qiPan, 10, 10, null);//绘棋盘	
		for(int i=0; i<qizi.length; i++){
			for(int j=0; j<qizi[i].length; j++){//绘棋子
				if(qizi[i][j] != 0){
					canvas.drawBitmap(qibei, 9+j*34, 10+i*35, null);//绘棋子背景					
					if(qizi[i][j] == 1){//黑帅时
						canvas.drawBitmap(heiZi[0], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 2){//黑车时
						canvas.drawBitmap(heiZi[1], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 3){//黑马时
						canvas.drawBitmap(heiZi[2], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 4){//黑炮时
						canvas.drawBitmap(heiZi[3], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 5){//黑士时
						canvas.drawBitmap(heiZi[4], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 6){//黑象时
						canvas.drawBitmap(heiZi[5], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 7){//黑兵时
						canvas.drawBitmap(heiZi[6], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 8){//为红将时
						canvas.drawBitmap(hongZi[0], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 9){//红车时
						canvas.drawBitmap(hongZi[1], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 10){//红马时
						canvas.drawBitmap(hongZi[2], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 11){//红砲时
						canvas.drawBitmap(hongZi[3], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 12){//红仕时
						canvas.drawBitmap(hongZi[4], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 13){//红相时
						canvas.drawBitmap(hongZi[5], 12+j*34, 13+i*35, paint);
					}
					else if(qizi[i][j] == 14){//红卒时
						canvas.drawBitmap(hongZi[6], 12+j*34, 13+i*35, paint);
					}
				}
			}
		}
		canvas.drawBitmap(sheng, 10, 360, paint);//绘制声音背景图
		//绘制黑方的时间
		canvas.drawBitmap(time, 81, 411, paint);//冒号
		int temp = this.heiTime/60;//时间换算
		String timeStr = temp+"";//转为字符串
		if(timeStr.length()<2){//不足两位时前面填0
			timeStr = "0" + timeStr;
		}
    	for(int i=0;i<2;i++){//循环绘制时间
    		int tempScore=timeStr.charAt(i)-'0';
    		canvas.drawBitmap(number[tempScore], 65+i*7, 412, paint);
    	}
    	//绘制分钟
    	temp = this.heiTime%60;
		timeStr = temp+"";//转成字符串
		if(timeStr.length()<2){   
			timeStr = "0" + timeStr;//长度小于2时在前面添加一个0
		}
    	for(int i=0;i<2;i++){//循环
    		int tempScore=timeStr.charAt(i)-'0';
    		canvas.drawBitmap(number[tempScore], 85+i*7, 412, paint);//绘制
    	}
    	//绘制红方时间
		canvas.drawBitmap(this.redtime, 262, 410, paint);//红冒号
		int temp2 = this.hongTime/60;//换算时间
		String timeStr2 = temp2+"";//转成字符串
		if(timeStr2.length()<2){//不足两位时前面填0
			timeStr2 = "0" + timeStr2;
		}
    	for(int i=0;i<2;i++){//循环绘制时间
    		int tempScore=timeStr2.charAt(i)-'0';
    		canvas.drawBitmap(redNumber[tempScore], 247+i*7, 411, paint);//绘制
    	}
    	//绘制分钟
    	temp2 = this.hongTime%60;//当前秒数
		timeStr2 = temp2+"";//转换成字符串
		if(timeStr2.length()<2){//不足两位时前面用0补
			timeStr2 = "0" + timeStr2;
		}
    	for(int i=0;i<2;i++){//循环绘制
    		int tempScore=timeStr2.charAt(i)-'0';
    		canvas.drawBitmap(redNumber[tempScore], 267+i*7, 411, paint);//绘制时间数字
    	}
		if(caiPan == true){
			canvas.drawBitmap(right, 155, 420, paint);//向右指针
		}
		else{//黑方走棋，即电脑走棋时
			canvas.drawBitmap(left, 120, 420, paint);//向左指针
		}
		
		canvas.drawBitmap(current, 138, 445, paint);//当前文字
		canvas.drawBitmap(sound2, 10, 440, paint);//绘制声音
		if(activity.isSound){//如果正在播放声音则绘制
			canvas.drawBitmap(sound3, 80, 452, paint);
		}
		
		canvas.drawBitmap(exit2, 250, 440, paint);//绘制退出按钮
		if(status == 1){//胜利时
			canvas.drawBitmap(win, 85, 150, paint);//绘制胜利图片
			canvas.drawBitmap(ok, 113, 240, paint);
		}
		if(status == 2){//失败
			canvas.drawBitmap(bai, 85, 150, paint);//绘制失败界面
			canvas.drawBitmap(ok, 113, 236, paint);	
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {//屏幕监听重写
		if(event.getAction() == MotionEvent.ACTION_DOWN){//鼠标按下事件
			if(event.getX()>10&&event.getX()<10+sound2.getWidth()
					&& event.getY()>440 && event.getY()<440+sound2.getHeight()){//按下声音按钮
				activity.isSound = !activity.isSound;
				if(activity.isSound){//当播放声音时
					if(activity.youxi != null){
						if(!activity.youxi.isPlaying()){
			    			activity.youxi.start();//播放音乐
			    		}
					}
				}
				else{
					if(activity.youxi != null){
						if(activity.youxi.isPlaying()){
							activity.youxi.pause();//停止音乐
						}
					} 
				}
			}
			if(event.getX()>250&&event.getX()<250+exit2.getWidth()
					&& event.getY()>440 && event.getY()<440+exit2.getHeight()){//按下退出按钮
				activity.handler.sendEmptyMessage(1);
			}
			if(status == 1){//胜利
				if(event.getX()>135&&event.getX()<190
						&& event.getY()>249 && event.getY()<269){
					activity.handler.sendEmptyMessage(1);//发送消息
				}
			}
			else if(status == 2){//失败后
				if(event.getX()>135&&event.getX()<190
						&& event.getY()>245 && event.getY()<265){//点击了确定按钮
					activity.handler.sendEmptyMessage(1);//发送消息，切换到MenuView
				}
			}

	
			else if(status == 0){//游戏中时
				if(event.getX()>10&&event.getX()<310
						&& event.getY()>10 && event.getY()<360){//点击位置在棋盘内
						if(caiPan == true){//如果是该玩家走棋
							int i = -1, j = -1;
							int[] pos = getPos(event);//根据坐标换算成所在的行和列
							i = pos[0];
							j = pos[1];
							if(de == false){//前面没有选中的棋子
								if(qizi[i][j] != 0){//点击的位置有棋子
									if(qizi[i][j] > 7){//点击的是自己的棋子
										selectqizi = qizi[i][j];//设为选中的棋子
										de = true;//标记有选中的棋子
										startI = i;
										startJ = j;
									}
								}
							}
							else{//之前选中过棋子
								if(qizi[i][j] != 0){//点击的位置有棋子
									if(qizi[i][j] > 7){//如果是自己的棋子.
										selectqizi = qizi[i][j];//将该棋子设为选中的棋子
										startI = i;
										startJ = j;
									}
									else{//如果是对方的棋子
										endI = i;
										endJ = j;//保存点
										boolean canMove = guiZe.canMove(qizi, startI, startJ, endI, endJ);
										if(canMove){//可以移动过去
											caiPan = false;//不让玩家走
											if(qizi[endI][endJ] == 1 || qizi[endI][endJ] == 8){//是“帅”或“将”
												this.success();//胜利
											}
											else{
												if(activity.isSound){
													go.start();//播放下棋声
												}
												qizi[endI][endJ] = qizi[startI][startJ];//移动棋子
												qizi[startI][startJ] = 0;
												startI = -1;
												startJ = -1;
												endI = -1;
												endJ = -1;//还原保存点
												de = false;//当前没有选中棋子
												
												Move cm = guiZe.searchAGoodMove(qizi);
												if(activity.isSound){
													go.start();//播放下棋声
												}
												qizi[cm.toX][cm.toY] = qizi[cm.qiX][cm.qiY];//移动棋子
												qizi[cm.qiX][cm.qiY] = 0;
												caiPan = true;
											}
										}
									}
								}
								else{//如果没有棋子
									endI = i;
									endJ = j;							
									boolean canMove = guiZe.canMove(qizi, startI, startJ, endI, endJ);//查看是否可走
									if(canMove){//如果可以移动
										caiPan = false;//不让玩家走
										if(activity.isSound){
											go.start();//播放下棋声
										}
										qizi[endI][endJ] = qizi[startI][startJ];//动棋子
										qizi[startI][startJ] = 0;//置空原来位置
										startI = -1;
										startJ = -1;
										endI = -1;
										endJ = -1;//还原保存点
										de = false;//标志为false

										Move cm = guiZe.searchAGoodMove(qizi);//得到走法 
										if(qizi[cm.toX][cm.toY] == 8){//吃了将
											status = 2;//失败
										}
										if(activity.isSound){
											go.start();
										}
										qizi[cm.toX][cm.toY] = qizi[cm.qiX][cm.qiY];//移动棋子
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
	
	public int[] getPos(MotionEvent e){//坐标转换成数组维数
		int[] pos = new int[2];
		double x = e.getX();//点击位置x坐标
		double y = e.getY();//点击位置y坐标
		if(x>10 && y>10 && x<10+qiPan.getWidth() && y<10+qiPan.getHeight()){//点击棋盘时
			pos[0] = Math.round((float)((y-21)/36));//获取所在行
			pos[1] = Math.round((float)((x-21)/35));//获取所在列
		}
		else{//点击的不是棋盘时
			pos[0] = -1;//设置位置不可用
			pos[1] = -1;
		}
		return pos;//返回坐标数组
	}
	
	public void success(){//胜利
		status = 1;//来到胜利状态
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {//重写
        this.thread.setFlag(true);
        this.thread.start();//刷帧线程
        timeThread.setFlag(true);
        timeThread.start();//思考时间的线程
	}

	public void surfaceDestroyed(SurfaceHolder holder) {//view被释放时调用的
        boolean retry = true;
        thread.setFlag(false);//停止刷帧线程
        timeThread.setFlag(false);//停止思考时间线程
        while (retry) {
            try {
                thread.join();
                timeThread.join();//等待线程结束
                retry = false;//设置循环标志位为false
            } 
            catch (InterruptedException e) {//循环到线程结束
            }
        }
	}
	class TutorialThread extends Thread{//刷帧线程
		private int span = 300;//睡眠300毫秒数
		private SurfaceHolder surfaceHolder;
		private Game gameView;
		private boolean flag = false;//循环标志
        public TutorialThread(SurfaceHolder surfaceHolder, Game gameView) {
            this.surfaceHolder = surfaceHolder;
            this.gameView = gameView;
        }
        public void setFlag(boolean flag) {//循环标记
        	this.flag = flag;
        }
		public void run() {//重写的方法
			Canvas c;//画布
            while (this.flag) {//循环绘制
                c = null;
                try {
                    c = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {
                    	gameView.onDraw(c);//绘制方法
                    }
                } finally {
                    if (c != null) {
                    	//更新屏幕内容
                        this.surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                try{
                	Thread.sleep(span);//睡眠时间，单位是毫秒
                }catch(Exception e){
                	e.printStackTrace();//输出异常堆栈信息
                }
            }
		}
	}
}