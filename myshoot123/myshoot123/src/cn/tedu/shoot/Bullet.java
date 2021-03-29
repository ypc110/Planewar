package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** �ӵ�:�Ƿ����� */
public class Bullet extends FlyingObject {
	private int speed; //�ƶ��ٶ�
	/** ���췽�� */
	public Bullet(int x,int y){ //�ӵ������������Ϊ�кܶ෢�ӵ�����ÿ���ӵ���λ��Ҫ����Ӣ�ۻ���λ��������õ�����Ӣ�ۻ�λ�ò��̶�������x/y����д��
		super(800,2000,x,y);
		speed = 3;
	}
	
	/** ��дstep()�ƶ� */
	public void step() {
		y-=speed; //y-(����)
	}
	
	/** ��дgetImage()��ȡͼƬ */
	public BufferedImage getImage() { //ÿ10�������һ��
		if(isLife()) {            //��Ϊ���ŵ�
			return Images.bullet; //��ֱ�ӷ���bulletͼƬ
		}else if(isDead()) { //��Ϊ���˵�
			state = REMOVE;  //��״̬�޸�ΪREMOVEɾ����
		}
		return null; //���˵ĺ�ɾ���ģ���������ͼƬ
		/*
		 * �����ŵ�: �����ӵ�ͼƬ
		 * �����˵�: ��״̬�޸�ΪREMOVE��ͬʱ������ͼƬ
		 * ��ɾ����: ������ͼƬ
		 */
	}
	
	/** ��дisOutOfBounds()�ж��ӵ��Ƿ�Խ�� */
	public boolean isOutOfBounds() {
		return y<=-height; //�ӵ���y<=�����ӵ��ĸߣ���ΪԽ����
	}
	
}
