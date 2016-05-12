package com.example.jyace.xiangqi;

public class Move {
	int ChessID;//ʲô����
	int qiX;//��ʼ����
	int qiY;
	int toX;//Ŀ������
	int toY;
	int score;//�÷�
	public Move(int ChessID, int qiX,int qiY,int toX,int toY,int score){//������
		this.ChessID = ChessID;//��������
		this.qiX = qiX;//��ʼ����
		this.qiY = qiY;
		this.toX = toX;//Ŀ��x����
		this.toY = toY;//Ŀ��y����
		this.score = score;
	}
}