package com.example.jyace.xiangqi;

 
public class shijianThread extends Thread{
	private boolean flag = true;//ѭ����־
	Game gameView;
	public shijianThread(Game gameView){
		this.gameView = gameView;
	}
	public void setFlag(boolean flag){//����ѭ�����
		this.flag = flag;
	}
	@Override
	public void run(){//��д����
		while(flag){//ѭ��
			if(gameView.caiPan == false){//�Զ��Ӻڷ�ʱ��
			}
			else if(gameView.caiPan == true){//Ϊ�췽���塢˼��
				gameView.hongTime++;//�Զ��Ӻ췽ʱ��
			}
			try{
				Thread.sleep(1000);//˯��1000���룬��1����
			}
			catch(Exception e){//�����쳣
				e.printStackTrace();//����쳣��Ϣ
			}
		}
	}
}