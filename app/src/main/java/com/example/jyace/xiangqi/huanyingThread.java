package com.example.jyace.xiangqi;
public class huanyingThread extends Thread{
	private boolean flag = true;//ѭ����־
	welcome welcomeView;
	public huanyingThread(welcome welcomeView){//������
		this.welcomeView = welcomeView;
	}
	public void setFlag(boolean flag){//����ѭ����־
		this.flag = flag;
	}
	public void run(){//��д����
    	try{
    		Thread.sleep(300);//����˯��300����
    	}
    	catch(Exception e){//�����쳣
    		e.printStackTrace();//����쳣��Ϣ
    	}
		while(flag){
			welcomeView.biaoX += 10;//��̬��ӭ����
			if(welcomeView.biaoX>0){//ֹͣ�ƶ�
				welcomeView.biaoX = 0;
			}
			welcomeView.boyX += 20;//�ƶ��к�ͼƬ����λ�ú�ֹͣ�ƶ�
			if(welcomeView.boyX>70){
				welcomeView.boyX = 70;
			}
			welcomeView.oldboyX += 15;//�ƶ���ͷ����λ��ֹͣ�ƶ�
			if(welcomeView.oldboyX>0){
				welcomeView.oldboyX = 0;
			}
			welcomeView.bordbackgroundY += 50;//�ƶ����ֱ���
			if(welcomeView.bordbackgroundY>240){
				welcomeView.bordbackgroundY = 240;
			}
			welcomeView.biao2X -= 30;//����ͼƬ����
			if(welcomeView.biao2X<150){
				welcomeView.biao2X = 150; //ֹͣ
			}
			if(welcomeView.biao2X == 150){
				welcomeView.menuY -= 30;
				if(welcomeView.menuY<355){
					welcomeView.menuY = 355;
				}
			} 
        	try{ 
        		Thread.sleep(100);//����˯��100����
        	}catch(Exception e){//�����쳣
        		e.printStackTrace();//����쳣��Ϣ
        	}
		}
	}
}