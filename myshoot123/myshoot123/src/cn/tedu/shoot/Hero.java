package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** Ӣ�ۻ�: �Ƿ����� */
public class Hero extends FlyingObject {
	private int life;   //��
	private int fire;   //����ֵ
	/** ���췽�� */ 
	public Hero(){
		super(171,139,140,400);
		life = 3;
		fire = 0;
	}
	
	/** ��дstep()�ƶ� */
	public void step() {
		
	}
	
	private int index = 0; //�±�
	/** ��дgetImage()��ȡͼƬ */
	public BufferedImage getImage() { //ÿ10������һ��
		return Images.heros[index++%Images.heros.length]; //����heros[0]��heros[1]�������л�
		/*
		 *                    index=0
		 * 10M  ����heros[0]  index=1
		 * 20M  ����heros[1]  index=2p
		 * 30M  ����heros[0]  index=3
		 * 40M  ����heros[1]  index=4
		 * 50M  ����heros[0]  index=5
		 * ...
		 */
	}
	
	/** Ӣ�ۻ������ӵ�(�����ӵ�����) */
	public Bullet[] shoot() {
		int xStep = this.width/4; //xStep:1/4Ӣ�ۻ��Ŀ�
		int yStep = 20;           //yStep:�̶���20
		if(fire>0) { //˫
			Bullet[] bs = new Bullet[1]; //2���ӵ�
			bs[0] = new Bullet(this.x+1*xStep,this.y-yStep); //x:Ӣ�ۻ���x+1/4Ӣ�ۻ��Ŀ�   y:Ӣ�ۻ���y-�̶���20
			//bs[1] = new Bullet(this.x+3*xStep,this.y-yStep); //x:Ӣ�ۻ���x+3/4Ӣ�ۻ��Ŀ�   y:Ӣ�ۻ���y-�̶���20
						
		
			
			fire-=2; //����һ��˫�������������ֵ��2
			return bs;
		}else { //��
			Bullet[] bs = new Bullet[2]; //1���ӵ�
			//bs[0] = new Bullet(this.x+2*xStep,this.y-yStep); 
			bs[0] = new Bullet(this.x+1*xStep,this.y-yStep); //x:Ӣ�ۻ���x+1/4Ӣ�ۻ��Ŀ�   y:Ӣ�ۻ���y-�̶���20
			bs[1] = new Bullet(this.x+3*xStep,this.y-yStep); //x:Ӣ�ۻ���x+3/4Ӣ�ۻ��Ŀ�   y:Ӣ�ۻ���y-�̶���20
		//	bs[2] = new Bullet(this.x+5*xStep,this.y-yStep);
			
			return bs;
		}
	}
	
	/** Ӣ�ۻ���������ƶ�  x/y:����x����/y���� */
	public void moveTo(int x,int y) {
		this.x = x-this.width/2;  //Ӣ�ۻ���x=����x-1/2Ӣ�ۻ��Ŀ�
		this.y = y-this.height/2; //Ӣ�ۻ���y=����y-1/2Ӣ�ۻ��ĸ�
	}
	
	/** Ӣ�ۻ����� */
	public void addLife() {
		life++; //������1
	}
	/** ��ȡӢ�ۻ����� */
	public int getLife() {
		return life; //��������
	}
	/** Ӣ�ۻ����� */
	public void subtractLife() {
		life--; //������1
	}
	
	/** Ӣ�ۻ������� */
	public void addFire() {
		fire+=40; //����ֵ��40
	}
	/** ���Ӣ�ۻ�����ֵ */
	public void clearFire() {
		fire=0; //����ֵ����
	}
	
}















