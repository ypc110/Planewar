package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** ��л�:�Ƿ����Ҳ�ܵ÷� */
public class BigAirplane extends FlyingObject implements EnemyScore {
	private int speed; //�ƶ��ٶ�
	/** ���췽�� */
	public BigAirplane(){
		super(66,89);
		speed = 2;
	}                            					  		                                                                                                                                                                                                                                                                                                         																																																																																																																																																																																																																																																																																								
	/** ��дstep()�ƶ� */
	public void step() {
		y+=speed; //y+(����) 
	}
	
	private int index = 1; //����ͼ�±�
	/** ��дgetImage()��ȡͼƬ */
	public BufferedImage getImage() { //ÿ10������һ��
		if(isLife()) { //�����ŵ�
			return Images.bairs[0]; //���ص�1��ͼƬ����
		}else if(isDead()) { //�����˵�
			BufferedImage img = Images.bairs[index++]; //��ȡ����ͼ
			if(index==Images.bairs.length) { //�������һ��ͼ��
				state = REMOVE;             //��״̬�޸�ΪREMOVEɾ����
			}
			return img; //���ر���ͼ
		}
		return null; //ɾ��״̬ʱ��������ͼƬ
	}
	/** ��дgetScore()�÷� */
	public int getScore() {
		return 3; //�����л�����3��
	}
	
}

