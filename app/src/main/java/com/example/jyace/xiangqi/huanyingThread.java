package com.example.jyace.xiangqi;
public class huanyingThread extends Thread{
	private boolean flag = true;//循环标志
	welcome welcomeView;
	public huanyingThread(welcome welcomeView){//构造器
		this.welcomeView = welcomeView;
	}
	public void setFlag(boolean flag){//设置循环标志
		this.flag = flag;
	}
	public void run(){//重写方法
    	try{
    		Thread.sleep(300);//设置睡眠300毫秒
    	}
    	catch(Exception e){//捕获异常
    		e.printStackTrace();//输出异常信息
    	}
		while(flag){
			welcomeView.biaoX += 10;//动态欢迎界面
			if(welcomeView.biaoX>0){//停止移动
				welcomeView.biaoX = 0;
			}
			welcomeView.boyX += 20;//移动男孩图片，到位置后停止移动
			if(welcomeView.boyX>70){
				welcomeView.boyX = 70;
			}
			welcomeView.oldboyX += 15;//移动老头，到位后停止移动
			if(welcomeView.oldboyX>0){
				welcomeView.oldboyX = 0;
			}
			welcomeView.bordbackgroundY += 50;//移动文字背景
			if(welcomeView.bordbackgroundY>240){
				welcomeView.bordbackgroundY = 240;
			}
			welcomeView.biao2X -= 30;//更改图片坐标
			if(welcomeView.biao2X<150){
				welcomeView.biao2X = 150; //停止
			}
			if(welcomeView.biao2X == 150){
				welcomeView.menuY -= 30;
				if(welcomeView.menuY<355){
					welcomeView.menuY = 355;
				}
			} 
        	try{ 
        		Thread.sleep(100);//设置睡眠100毫秒
        	}catch(Exception e){//捕获异常
        		e.printStackTrace();//输出异常信息
        	}
		}
	}
}