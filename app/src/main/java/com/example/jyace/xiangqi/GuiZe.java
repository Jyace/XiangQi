package com.example.jyace.xiangqi;

import java.util.ArrayList;//����� 
import java.util.List;

public class GuiZe {
	boolean isRedGo = false;//�Ƿ�췽����
	public boolean canMove(int[][] qizi, int fromY, int fromX, int toY, int toX){
		int i = 0;
		int j = 0;
		int qiID;//��ʼλ�õ���������
		int muID;//Ŀ��λ��
		if(toX<0){//��߳���
			return false;
		}
		if(toX>8){//�ұ߳���
			return false;
		}
		if(toY<0){//�ϱ߳���
			return false;
		}
		if(toY>9){//�±߳���
			return false;
		}
		if(fromX==toX && fromY==toY){//Ŀ��λ������ʼλ����ͬ��
			return false;
		}
		qiID = qizi[fromY][fromX];//��ʼ����
		muID = qizi[toY][toX];
		if(isSameSide(qiID,muID)){//һ���
			return false;
		}
		switch(qiID){
			case 1://��˧
				if(toY>2||toX<3||toX>5){//���˾Ź���
					return false;
				}
				if((Math.abs(fromY-toY)+Math.abs(toX-fromX))>1){//ֻ����һ��
					return false;
				}
				break;
			case 5://��ʿ
				if(toY>2||toX<3||toX>5){//���˾Ź���
					return false;
				}
				if(Math.abs(fromY-toY) != 1 || Math.abs(toX-fromX) != 1){//��б��
					return false;
				}
				break;
			case 6://����
				if(toY>4){//������
					return false;
				}
				if(Math.abs(fromX-toX) != 2 || Math.abs(fromY-toY) != 2){//������
					return false;
				}
				if(qizi[(fromY+toY)/2][(fromX+toX)/2] != 0){
					return false;
				}
				break;
			case 7://�ڱ�
				if(toY < fromY){//����ͷ
					return false;
				}
				if(fromY<5 && fromY == toY){//����ǰֻ��ֱ��
					return false;
				}
				if(toY - fromY + Math.abs(toX-fromX) > 1){//ֻ��һ��ֱ�� 
					return false;
				}
				break;
			case 8://�콫
				if(toY<7||toX>5||toX<3){//���˾Ź���
					return false;
				}
				if((Math.abs(fromY-toY)+Math.abs(toX-fromX))>1){//ֻ��һ��
					return false;
				}
				break;
			case 2://�ڳ�
			case 9://�쳵
				if(fromY != toY && fromX != toX){//ֻ��ֱ��
					return false;
				}
				if(fromY == toY){//�ߺ���
					if(fromX < toX){//������
						for(i = fromX + 1; i < toX; i++){//ѭ��
							if(qizi[fromY][i] != 0){
								return false;//����false
							}
						}
					}
					else{//������
						for(i = toX + 1; i < fromX; i++){//ѭ��
							if(qizi[fromY][i] != 0){
								return false;//����false
							}
						}
					}
				}
				else{//�ߵ�������
					if(fromY < toY){//������
						for(j = fromY + 1; j < toY; j++){
							if(qizi[j][fromX] != 0)
							return false;//����false							
						}
					}
					else{//������
						for(j= toY + 1; j < fromY; j++){
							if(qizi[j][fromX] != 0)
							return false;//����false							
						}
					}
				}
				break;
			case 10://���� 
			case 3://����
				if(!((Math.abs(toX-fromX)==1 && Math.abs(toY-fromY)==2)
						|| (Math.abs(toX-fromX)==2 && Math.abs(toY-fromY)==1))){
					return false;//��������ʱ
				}
				if(toX-fromX==2){//����
					i=fromX+1;//�ƶ�
					j=fromY;
				}
				else if(fromX-toX==2){//����
					i=fromX-1;
					j=fromY;
				}
				else if(toY-fromY==2){//����
					i=fromX;
					j=fromY+1;
				}
				else if(fromY-toY==2){//����
					i=fromX;
					j=fromY-1;
				}
				if(qizi[j][i] != 0)
					return false;//������
				break;
			case 11://��h
			case 4://����
				if(fromY!=toY && fromX!=toX){//��ֱ��
					return false;
				}
				if(qizi[toY][toX] == 0){
					if(fromY == toY){//����
						if(fromX < toX){//������
							for(i = fromX + 1; i < toX; i++){
								if(qizi[fromY][i] != 0){
									return false;
								}
							}
						}
						else{//������
							for(i = toX + 1; i < fromX; i++){
								if(qizi[fromY][i]!=0){
									return false;
								}
							}
						}
					}
					else{//����
						if(fromY < toY){//������
							for(j = fromY + 1; j < toY; j++){
								if(qizi[j][fromX] != 0){
									return false;//����false
								}
							}
						}
						else{//������
							for(j = toY + 1; j < fromY; j++){
								if(qizi[j][fromX] != 0){
									return false;//����false
								}
							}
						}
					}
				}
				else{//����
					int count=0;
					if(fromY == toY){//�ߵĺ���
						if(fromX < toX){//����
							for(i=fromX+1;i<toX;i++){
								if(qizi[fromY][i]!=0){
									count++;
								}
							}
							if(count != 1){
								return false;//����false
							}
						}
						else{//������
							for(i=toX+1;i<fromX;i++){
								if(qizi[fromY][i] != 0){
									count++;
								}
							}
							if(count!=1){
								return false;//����false
							}
						}
					}
					else{//������
						if(fromY<toY){//������
							for(j=fromY+1;j<toY;j++){
								if(qizi[j][fromX]!=0){
									count++;
								}
							}	
							if(count!=1){
								return false;
							}
						}
						else{//������
							for(j=toY+1;j<fromY;j++){
								if(qizi[j][fromX] != 0){
									count++;//����false
								}
							}
							if(count!=1){
								return false;//����false
							}
						}
					}
				}
				break;
			case 12://����
				if(toY<7||toX>5||toX<3){//���˾Ź���
					return false;
				}
				if(Math.abs(fromY-toY) != 1 || Math.abs(toX-fromX) != 1){//��б��
					return false;
				}
				break;
			case 13://����
				if(toY<5){//���ܹ���
					return false;//����false
				}
				if(Math.abs(fromX-toX) != 2 || Math.abs(fromY-toY) != 2){//���ߡ����
					return false;//����false
				}
				if(qizi[(fromY+toY)/2][(fromX+toX)/2] != 0){
					return false;//���۴�������
				}
				break;
			case 14://����
				if(toY > fromY){//���ܻ�ͷ
					return false;
				}
				if(fromY > 4 && fromY == toY){
					return false;//������
				}
				if(fromY - toY + Math.abs(toX - fromX) > 1){//ֻ����һ����������ֱ�� 
					return false;//����false������
				}
				break;
			default:
				return false;
		}
		return true;
	}

	public Move searchAGoodMove(int[][] qizi){//��ѯһ���õ��߷�
		List<Move> ret = allPossibleMoves(qizi);//���������߷�
		try {
			Thread.sleep(4000);//˯�������ӣ��Ա����
		} catch (InterruptedException e) {//�����쳣
			e.printStackTrace();//��ӡ��ջ��Ϣ
		}
		return ret.get((int)(Math.random()*ret.size()));
	}
	public List<Move> allPossibleMoves(int qizi[][]){//�������п��ܵ��߷�
		 List<Move> ret = new ArrayList<Move>();//����װ���п��ܵ��߷� 
		 for (int x = 0; x < 10; x++){
			 for (int y = 0; y < 9; y++){//ѭ�����е�����λ��
				 int chessman = qizi[x][y];
				 if (chessman != 0){//����λ�ò�Ϊ��ʱ����������ʱ
					 if(chessman > 7){//�Ǻ췽�������������ʱ����
						 continue;
					 }
					 switch (chessman){
					 	case 1://��˧
					 		if(canMove(qizi, x, y, x, y+1)){//������һ��
	                    		ret.add(new Move(chessman, x, y, x, y+1, 0));
	                    	}
	                    	if(canMove(qizi, x, y, x, y-1)){//������һ��
	                    		ret.add(new Move(chessman, x, y, x, y-1, 0));
	                    	}
	                    	if(canMove(qizi, x, y, x+1, y)){//������һ��
	                    		ret.add(new Move(chessman, x, y, x+1, y, 0));
	                    	}
	                    	if(canMove(qizi, x, y, x-1, y)){//������һ��
	                    		ret.add(new Move(chessman, x, y, x-1, y, 0));
	                    	}
	                    	break;
						case 5://��ʿ
						case 12://����
							if(canMove(qizi, x, y, x-1, y+1)){//������
								ret.add(new Move(chessman, x, y, x-1, y+1, 1));
							}
							if(canMove(qizi, x, y, x-1, y-1)){//������
								ret.add(new Move(chessman, x, y, x-1, y-1, 1));
							}
							if(canMove(qizi, x, y, x+1, y+1)){//������
								ret.add(new Move(chessman, x, y, x+1, y+1, 1));
							}
							if(canMove(qizi, x, y, x+1, y-1)){//������
								ret.add(new Move(chessman, x, y, x+1, y-1, 1));
							}
							break;
	         			case 6://����
	         			case 13://����
							if(canMove(qizi, x, y, x-2, y+2)){//������
								ret.add(new Move(chessman, x, y, x-2, y+2, 1));
							}
							if(canMove(qizi, x, y, x-2, y-2)){//������
								ret.add(new Move(chessman, x, y, x-2, y-2, 1));
							}
							if(canMove(qizi, x, y, x+2, y+2)){//������
								ret.add(new Move(chessman, x, y, x+2, y+2, 1));
							}
							if(canMove(qizi, x, y, x+2, y-2)){//������
								ret.add(new Move(chessman, x, y, x+2, y-2, 1));
							}
	         				break;
	         			case 7://�ڱ�
         					if(canMove(qizi, x, y, x, y+1)){//ֱ��
         						ret.add(new Move(chessman, x, y, x, y+1, 2));
         					}
         					if(y >= 5){//������ 
                                if (canMove(qizi, x, y, x - 1, y)) {//���Ӻ�������
                                    ret.add(new Move(chessman, x, y, x - 1, y, 2));
                                }
                                if (canMove(qizi, x, y, x + 1, y)) {//������������
                                    ret.add(new Move(chessman, x, y, x + 1, y, 2));
                                }
         					}
         					break;
	         			case 14://���
         					if(canMove(qizi, x, y, x, y-1)){//��ǰ��
         						ret.add(new Move(chessman, x, y, x, y-1, 2));
         					}
         					if(y <=4 ){//������
                                if (canMove(qizi, x, y, x - 1, y)) {//���Ӻ�������
                                    ret.add(new Move(chessman, x, y, x - 1, y, 2));
                                }
                                if (canMove(qizi, x, y, x + 1, y)) {//������������
                                    ret.add(new Move(chessman, x, y, x + 1, y, 2));
                                }
         					}
	         				break;
	         			case 8://�콫
	                    	if(canMove(qizi, x, y, x, y+1)){//������һ��
	                    		ret.add(new Move(chessman, x, y, x, y+1, 0));
	                    	}
	                    	if(canMove(qizi, x, y, x, y-1)){//������һ��
	                    		ret.add(new Move(chessman, x, y, x, y-1, 0));
	                    	}
	                    	if(canMove(qizi, x, y, x+1, y)){//������һ��
	                    		ret.add(new Move(chessman, x, y, x+1, y, 0));
	                    	}
	                    	if(canMove(qizi, x, y, x-1, y)){//������һ��
	                    		ret.add(new Move(chessman, x, y, x-1, y, 0));
	                    	}
	                    	break;
	         			case 2://�ڳ�
	         			case 9://�쳵
	         				for(int i=y+1; i<10; i++){//������
	         					if(canMove(qizi, x, y, x, i)){ //������ʱ
	         						ret.add(new Move(chessman, x, y, x, i, 0));
	         					}else{//��������ʱֱ�� break
	         						break;
	         					}
	         				}
	         				for(int i=y-1; i>-1; i++){//������
	         					if(canMove(qizi, x, y, x, i)){//������ʱ
	         						ret.add(new Move(chessman, x, y, x, i, 0));
	         					}else{//��������ʱ
	         						break;
	         					}
	         				}
	         				for(int j=x-1; j>-1; j++){//������
	         					if(canMove(qizi, x, y, j, y)){//������ʱ 
	         						ret.add(new Move(chessman, x, y, j, y, 0));
	         					}else{//��������ʱ
	         						break;
	         					}
	         				}
	         				for(int j=x+1; j<9; j++){//������
	         					if(canMove(qizi, x, y, j, y)){//������ʱ
	         						ret.add(new Move(chessman, x, y, j, y, 0));
	         					}else{//��������ʱ
	         						break;
	         					}
	         				}
	         				break;
	         			case 10://���� 
	         			case 3://����
	                    	if(canMove(qizi, x, y, x-1, y-2)){//�������ߡ��ա���
	                    		ret.add(new Move(chessman, x, y, x-1, y-2, 0));
	                    	}
	                    	if(canMove(qizi, x, y, x-1, y+2)){//�����ߡ��ա���
	                    		ret.add(new Move(chessman, x, y, x-1, y+2, 0));
	                    	}
	                    	if(canMove(qizi, x, y, x+1, y-2)){//�������ߡ��ա���
	                    		ret.add(new Move(chessman, x, y, x+1, y-2, 0));
	                    	}
	                    	if(canMove(qizi, x, y, x+1, y+2)){//�������ߡ��ա���
	                    		ret.add(new Move(chessman, x, y, x+1, y+2, 0));
	                    	}
	                    	if(canMove(qizi, x, y, x-2, y-1)){//�������ߡ��ա���
	                    		ret.add(new Move(chessman, x, y, x-2, y-1, 0));
	                    	}
	                    	if(canMove(qizi, x, y, x-2, y+1)){//�������ߡ��ա���
	                    		ret.add(new Move(chessman, x, y, x-2, y+1, 0));
	                    	}
	                    	if(canMove(qizi, x, y, x+2, y-1)){//�������ߡ��ա���
	                    		ret.add(new Move(chessman, x, y, x+2, y-1, 0));
	                    	}
	                    	if(canMove(qizi, x, y, x+2, y+1)){//�������ߡ��ա���
	                    		ret.add(new Move(chessman, x, y, x+2, y+1, 0));
	                    	}
	         				break;
	         			case 11://��h
	         			case 4://����
	         				for(int i=y+1; i<10; i++){//������ʱ
	         					if(canMove(qizi, x, y, x, i)){//��������ʱ
	         						ret.add(new Move(chessman, x, y, x, i, 0));
	         					}
	         				}
	         				for(int i=y-1; i>-1; i--){//������ʱ
	         					if(canMove(qizi, x, y, x, i)){//��������ʱ
	         						ret.add(new Move(chessman, x, y, x, i, 0));
	         					}
	         				}
	         				for(int j=x-1; j>-1; j--){//������ʱ
	         					if(canMove(qizi, x, y, j, y)){//��������ʱ
	         						ret.add(new Move(chessman, x, y, j, y, 0));
	         					}
	         				}
	         				for(int j=x+1; j<9; j++){//������ʱ
	         					if(canMove(qizi, x, y, j, y)){//��������ʱ
	         						ret.add(new Move(chessman, x, y, j, y, 0));
	         					}
	         				}
	         				break;
					}
				 }
			 }
		 }
		 return ret.isEmpty() ? null : ret;//��ret��û���߷�ʱ�����ؿգ���ʱ����ret
	}
	public boolean isSameSide(int qiID, int muID){//�ж��������Ƿ�Ϊͬһ��Ӫ
		if(muID == 0){// ��Ŀ���λ�յ�ʱ
			return false;
		}
		if(qiID>7&&muID>7){//����Ϊ��ɫ����ʱ
			return true;
		}
		else if(qiID<=7&&muID<=7){//��Ϊ��ɫ����ʱ
			return true;
		}
		else{//�������
			return false;
		}
	}
}