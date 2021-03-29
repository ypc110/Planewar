package cn.tedu.shoot;
import java.util.Random;
import java.awt.image.BufferedImage;
/** ������ */
public abstract class FlyingObject {
	public static final int LIFE = 0;   //���ŵ�
	public static final int DEAD = 1;   //���˵�
	public static final int REMOVE = 2; //ɾ����
	protected int state = LIFE; //��ǰ״̬(Ĭ��Ϊ���ŵ�)
	
	protected int width;  //��
	protected int height; //��
	protected int x;      //x����
	protected int y;      //y����
	
	/** ר�Ÿ�С�л�����л���С�۷��ṩ�� */
	//��Ϊ���ֵ��˵Ŀ�͸��ǲ�ͬ�ģ����Կ����д��(����)
	//��Ϊ���ֵ��˵�x��y����ͬ�ģ�����ֱ��д��
	public FlyingObject(int width,int height){
		this.width = width;
		this.height = height;
		Random rand = new Random(); //���������
		x = rand.nextInt(World.WIDTH-width); //x:0��(���ڿ�-���˿�)֮�ڵ������
		y = -height; //y:���ĵ��˵ĸ�
	}
	
	/** ר�Ÿ�Ӣ�ۻ�����ա��ӵ��ṩ�� */
	//��Ϊ���ַ�����Ŀ��xy���ǲ�ͬ�ģ����Զ���д��(����)
	public FlyingObject(int width,int height,int x,int y){
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	/** �������ƶ� */
	public abstract void step();
	
	/** ��ȡ�����ͼƬ */
	public abstract BufferedImage getImage();
	
	/** �ж��Ƿ��ǻ��ŵ� */
	public boolean isLife() {
		return state==LIFE; //����ǰ״̬ΪLIFE���򷵻�true��ʾΪ���ŵģ����򷵻�false
	}
	/** �ж��Ƿ������˵� */
	public boolean isDead() {
		return state==DEAD; //����ǰ״̬ΪDEAD���򷵻�true��ʾΪ���˵ģ����򷵻�false
	}
	/** �ж��Ƿ���ɾ���� */
	public boolean isRemove() {
		return state==REMOVE; //����ǰ״̬ΪREMOVE���򷵻�true��ʾΪɾ���ģ����򷵻�false
	}
	
	/** �жϵ����Ƿ�Խ�� */
	public boolean isOutOfBounds() {
		return y>=World.HEIGHT; //���˵�y>=���ڵĸߣ���ΪԽ����
	}
	
	/** �жϵ����Ƿ����ӵ�/Ӣ�ۻ���ײ this:����  other:�ӵ�/Ӣ�ۻ� */
	public boolean isHit(FlyingObject other) {
		int x1 = this.x-other.width;  //x1:���˵�x-�ӵ�/Ӣ�ۻ��Ŀ�
		int x2 = this.x+this.width;   //x2:���˵�x+���˵Ŀ�
		int y1 = this.y-other.height; //y1:���˵�y-�ӵ�/Ӣ�ۻ��ĸ�
		int y2 = this.y+this.height;  //y2:���˵�y+���˵ĸ�
		int x = other.x;              //x:�ӵ�/Ӣ�ۻ���x
		int y = other.y;              //y:�ӵ�/Ӣ�ۻ���y
		
		return x>=x1 && x<=x2 
		       && 
		       y>=y1 && y<=y2; //x��x1��x2֮�䣬���ң�y��y1��y2֮�䣬��Ϊײ����
	}
	
	/** ������ȥ�� */
	public void goDead() {
		state = DEAD; //������״̬�޸�ΪDEAD���˵�
	}
	
}
