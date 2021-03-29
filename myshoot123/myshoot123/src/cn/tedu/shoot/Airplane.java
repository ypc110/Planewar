package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** С�л�:�Ƿ����Ҳ�ܵ÷� */
public class Airplane extends FlyingObject implements EnemyScore {
	private int speed; //�ƶ��ٶ�
	/** ���췽�� */
	public Airplane(){
		super(48,50);
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
			return Images.airs[0]; //���ص�1��ͼƬ����
		}else if(isDead()) { //�����˵�
			BufferedImage img = Images.airs[index++]; //��ȡ����ͼ
			if(index==Images.airs.length) { //�������һ��ͼ��
				state = REMOVE;             //��״̬�޸�ΪREMOVEɾ����
			}
			return img; //���ر���ͼ
		}                 
		return null; //ɾ��״̬ʱ��������ͼƬ
		/* 
		 *                  index=1
		 * 10M  img=airs[1] index=2                  ����airs[1]
		 * 20M  img=airs[2] index=3                  ����airs[2]
		 * 30M  img=airs[3] index=4                  ����airs[3]
		 * 40M  img=airs[4] index=5(REMOVE) ����airs[4]
		 * 50M  ����null(������ͼƬ)
		 */
	}
	
	/** ��дgetScore()�÷� */
	public int getScore() {
		return 1; //���С�л�����1��
	}
	
}
















