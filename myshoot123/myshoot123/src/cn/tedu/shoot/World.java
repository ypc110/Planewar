package cn.tedu.shoot;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/** �������� */
public class World extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400;  //���ڵĿ�
	public static final int HEIGHT = 700; //���ڵĸ�
	public static final int START = 0;     //����״̬
	public static final int RUNNING = 1;   //����״̬
	public static final int PAUSE = 2;     //��ͣ״̬
	public static final int GAME_OVER = 3; //��Ϸ����״̬
	private int state = START; //��ǰ״̬(Ĭ������״̬)
	private Hero hero = new Hero(); //Ӣ�ۻ�
	private Sky sky = new Sky();    //���
	private FlyingObject[] enemies = {}; //����(С�л�����л���С�۷�)����
	private Bullet[] bullets = {}; //�ӵ�����
	/** ���ɵ���(С�л�����л���С�۷�)���� */
	public FlyingObject nextOne() {
		Random rand = new Random(); //���������
		int type = rand.nextInt(20); //0��19֮��������
		if(type<5) { //0��4ʱ������С�۷����
			return new Bee();
		}else if(type<14) { //5��13ʱ������С�л�����
			return new Airplane();
		}else { //14��19ʱ�����ش�л�����
			return new BigAirplane();
		}
	}
	private int enterIndex = 0; //�����볡������
	/** ����(С�л�����л���С�۷�)�볡 */
	public void enterAction() { //ÿ10������һ��
		enterIndex++; //ÿ10������1
		if(enterIndex%40==0) { //ÿ400(40*10)������һ��
			FlyingObject obj = nextOne(); //��ȡ���˶���
			enemies = Arrays.copyOf(enemies,enemies.length+1); //����
			enemies[enemies.length-1] = obj; //�����˶���obj��ӵ�enemies���һ��Ԫ����
		}
	}
	
	private int shootIndex = 0; //�ӵ��볡����
	/** �ӵ��볡 */
	public void shootAction() { //ÿ10������һ��
		shootIndex++; //ÿ10������1
		if(shootIndex%30==0) { //ÿ300(30*10)������һ��
			Bullet[] bs = hero.shoot(); //��ȡ�ӵ��������
			bullets = Arrays.copyOf(bullets,bullets.length+bs.length); //����(bs�м���Ԫ�ؾ����󼸸�����)
			System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length); //�����׷��
		}
	}
	/** �������ƶ� */
	public void shootAction1() { //ÿ10������һ��
		shootIndex++; //ÿ10������1
		if(shootIndex%3==0) { //ÿ300(30*10)������һ��
			Bullet[] bs = hero.shoot(); //��ȡ�ӵ��������
			bullets = Arrays.copyOf(bullets,bullets.length+bs.length); //����(bs�м���Ԫ�ؾ����󼸸�����)
			System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length); //�����׷��
		}
	}

	public void enterAction1() { //ÿ10������һ��
		enterIndex++; //ÿ10������1
		if(enterIndex%5==0) { //ÿ400(40*10)������һ��
			FlyingObject obj = nextOne(); //��ȡ���˶���
			enemies = Arrays.copyOf(enemies,enemies.length+1); //����
			enemies[enemies.length-1] = obj; //�����˶���obj��ӵ�enemies���һ��Ԫ����
		}
	}
	public void stepAction() { //ÿ10������һ��
		sky.step(); //��ն�
		for(int i=0;i<enemies.length;i++) { //�������е���
			enemies[i].step(); //���˶�
		}
		for(int i=0;i<bullets.length;i++) { //���������ӵ�
			bullets[i].step(); //�ӵ���
		}
	}
	
	/** ɾ��Խ��ĵ��˺��ӵ�----�����ڴ�й© */
	public void outOfBoundsAction() { //ÿ10������һ��
		for(int i=0;i<enemies.length;i++) { //�������е���
			if(enemies[i].isOutOfBounds() || enemies[i].isRemove()) { //Խ��Ļ���REMOVE״̬��
				enemies[i] = enemies[enemies.length-1]; //�����һ�����˽�Խ������滻��
				enemies = Arrays.copyOf(enemies,enemies.length-1); //����
			}
		}
		
		for(int i=0;i<bullets.length;i++) { //���������ӵ�
			if(bullets[i].isOutOfBounds() || bullets[i].isRemove()) { //Խ��Ļ���REMOVE״̬��
				bullets[i] = bullets[bullets.length-1]; //�����һ���ӵ���Խ���ӵ��滻��
				bullets = Arrays.copyOf(bullets,bullets.length-1); //����
			}
		}
	}
	
	private int score = 0; //��ҵ÷�
	/** �ӵ�����˵���ײ */
	public void bulletBangAction() { //ÿ10������һ��
		for(int i=0;i<bullets.length;i++) { //���������ӵ�
			Bullet b = bullets[i]; //��ȡÿһ���ӵ�
			for(int j=0;j<enemies.length;j++) { //�������е���
				FlyingObject f = enemies[j]; //��ȡÿһ������
				if(b.isLife() && f.isLife() && f.isHit(b)) { //�������ţ����һ�ײ����
					b.goDead(); //�ӵ�ȥ��
					f.goDead(); //����ȥ��
					
					if(f instanceof EnemyScore) { //����ײ�����ܵ÷�
						EnemyScore es = (EnemyScore)f; //����ײ����ǿת�ֵ÷ֽӿ�
						score += es.getScore(); //��ҵ÷�
					}
					if(f instanceof EnemyAward) { //����ײ����Ϊ����
						EnemyAward ea = (EnemyAward)f; //����ײ����ǿתΪ�����ӿ�
						int type = ea.getAwardType(); //��ȡ��������
						switch(type) { //���ݽ������͵Ĳ�ͬ����ȡ��ͬ�Ľ���
						case EnemyAward.FIRE: //����������ΪFIRE����ֵ
							hero.addFire();   //��Ӣ�ۻ�������
							break;
						case EnemyAward.LIFE: //����������ΪLIFE��
							hero.addLife();   //��Ӣ�ۻ�����
							break;
						}
					}
				}
			}
		}
	}

	/** Ӣ�ۻ�����˵���ײ */
	public void heroBangAction() { //ÿ10������һ��
		for(int i=0;i<enemies.length;i++) { //�������е���
			FlyingObject f = enemies[i]; //��ȡÿһ������
			if(hero.isLife() && f.isLife() && f.isHit(hero)) { //�������ţ����һ�ײ����
				f.goDead(); //����ȥ��
				hero.subtractLife(); //Ӣ�ۻ�����
				hero.clearFire(); //Ӣ�ۻ���ջ���ֵ
			}
		}
	}
	
	/** �����Ϸ���� */
	public void checkGameOverAction() { //ÿ10������һ��
		if(hero.getLife()<=0) { //��Ӣ�ۻ�����<=0����ʾ��Ϸ������
			state = GAME_OVER; //����ǰ״̬�޸�ΪGAME_OVER��Ϸ����״̬ 
		}
	}
	
	/** ���������ִ�� */
	public void action() {
		//����������
		MouseAdapter m = new MouseAdapter() {
			/** ��дmouseMoved()����ƶ��¼� */
			public void mouseMoved(MouseEvent e) {
				if(state==RUNNING) { //����״̬ʱִ��
					int x = e.getX(); //��ȡ����x����
					int y = e.getY(); //��ȡ����y����
					hero.moveTo(x, y); //Ӣ�ۻ���������ƶ�
				}
			}
			/** ��дmouseClicked()������¼� */
			public void mouseClicked(MouseEvent e) {
				switch(state) { //���ݵ�ǰ״̬����ͬ�Ĵ���
				case START:          //����״̬ʱ
					state = RUNNING; //�޸�Ϊ����״̬
					break;
				case GAME_OVER:    //��Ϸ����״̬ʱ
					score = 0;     //�����ֳ�(�������ݻ�ԭ)
					hero = new Hero();
					sky = new Sky();
					enemies = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START; //�޸�Ϊ����״̬
					break;
				}
			}
			/** ��дmouseExited()����Ƴ��¼� */
			public void mouseExited(MouseEvent e) {
				if(state==RUNNING) { //����״̬ʱ
					state = PAUSE;   //�޸�Ϊ��ͣ״̬
				}
			}
			/** ��дmouseEntered()��������¼� */
			public void mouseEntered(MouseEvent e) {
				if(state==PAUSE) {   //��ͣ״̬ʱ
					state = RUNNING; //�޸�Ϊ����״̬
				}
			}
			
		}; 
		this.addMouseListener(m); //�����������¼�
		this.addMouseMotionListener(m); //������껬���¼�
		
		Timer timer = new Timer(); //��ʱ������
		int intervel = 10; //��ʱ���(ÿ10������һ��)
		timer.schedule(new TimerTask() {
			public void run() { //��ʱ�ɵ���(ÿ10������һ��)
				if(state==RUNNING) { //����״̬ʱִ��
					enterAction(); //����(С�л�����л���С�۷�)�볡
					shootAction(); //�ӵ��볡
					stepAction();  //�������ƶ�
					outOfBoundsAction();   //ɾ��Խ��ĵ��˺��ӵ�
					bulletBangAction();    //�ӵ�����˵���ײ
					heroBangAction();      //Ӣ�ۻ�����˵���ײ
					checkGameOverAction(); //�����Ϸ����
				}
				repaint();     //�ػ�(���µ���paint()����)
			}
		},intervel,intervel); //��ʱ�ƻ���
		
		
	}
	
	/** ��дpaint()��  g:���� */
	public void paint(Graphics g) { //ÿ10 ������һ��
		g.drawImage(sky.getImage(),sky.x,sky.y,null);       //�����
		g.drawImage(sky.getImage(),sky.x,sky.getY1(),null); //����յĵ�2��ͼƬ
		g.drawImage(hero.getImage(),hero.x,hero.y,null);    //��Ӣ�ۻ�
		for(int i=0;i<enemies.length;i++) { //�������е���
			FlyingObject f = enemies[i]; //��ȡÿ������
			g.drawImage(f.getImage(),f.x,f.y,null); //������
		}
		for(int i=0;i<bullets.length;i++) { //���������ӵ�
			Bullet b = bullets[i]; //��ȡÿ���ӵ�
			g.drawImage(b.getImage(),b.x,b.y,null); //���ӵ�
		}
		
		g.drawString("SCORE: "+score,10,25);         //����
		g.drawString("LIFE: "+hero.getLife(),10,45); //����
		switch(state) { //�ڲ�ͬ��״̬�»���ͬ��ͼ
		case START: //����״̬ʱ������ͼ
			g.drawImage(Images.start,0,0,null);
			break;
		case PAUSE: //��ͣ״̬ʱ����ͣͼ
			g.drawImage(Images.pause,0,0,null);
			break;
		case GAME_OVER: //��Ϸ����״̬ʱ����Ϸ����ͼ
			g.drawImage(Images.gameover,0,0,null);
			break;
		}
	}  
	public static void main(String[] args) {
		
		
	    
	    
		final JFrame f = new JFrame();
    	f.setSize(400,700);
    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	//����
    	f.setLocationRelativeTo(null);
    	
    	/*
    	 * ��Ӳ˵�
    	 */
    	JMenuBar bar = new JMenuBar();//�˵�����
    	//�˵�
    	JMenu menu01 = new JMenu("������");
    	JMenu menu02 = new JMenu("��ͼ�л�");
    	JMenu menu03 = new JMenu("��������");
    	JMenu menu04 = new JMenu("����б�");
    	//�˵���
    	JMenuItem itemDB = new JMenuItem("�浵");
    	//JMenuItem itemFile = new JMenuItem("��ѯ����");
    	JMenuItem itemFile1 = new JMenuItem("��ͼ1");
    	JMenuItem itemFile2 = new JMenuItem("��ͼ2");
    	JMenuItem itemFile3 = new JMenuItem("��ͼ3");
    	JMenuItem itemFile4 = new JMenuItem("����");
    	JMenuItem itemFile5 = new JMenuItem("�ر�");
    	JMenuItem itemFile6 = new JMenuItem("�鿴");
    	bar.add(menu01);
    	bar.add(menu02);
    	bar.add(menu03);
    	bar.add(menu04);
    	menu01.add(itemDB);
    	//menu01.add(itemFile);
    	menu02.add(itemFile1);
    	menu02.add(itemFile2);
    	menu02.add(itemFile3);
    	menu03.add(itemFile4);
    	menu03.add(itemFile5);
    	menu04.add(itemFile6);
    	f.add(bar,BorderLayout.NORTH);
    	
    	//����¼�
    	ActionListener al = 
    			new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
//						int id = e.getID();
						Object obj = e.getSource();
//						System.out.println(obj.getClass().getName());
						JMenuItem item = (JMenuItem)obj;
						String content = 
						item.getText();
//						System.out.println("������:"+content);
						if("�浵".equals(content)){
//							JOptionPane.showMessageDialog(
//									f,"׼��д�����ݿ�","��ʾ",JOptionPane.PLAIN_MESSAGE);
							String username = JOptionPane.showInputDialog(f, 
									"�û���","username",JOptionPane.PLAIN_MESSAGE);
							String score = JOptionPane.showInputDialog(f, 
									"����","score",JOptionPane.PLAIN_MESSAGE);
							System.out.println(username+","+score);
							jdbc.insert(username, score);
							}
						if("��ͼ1".equals(content)){
					Images.changemap1();
				
					
							}
						if("��ѯ����".equals(content)){
							jdbc.query();
							
									}
						if("��ͼ2".equals(content)){
							Images.changemap();
							
									}
						if("��ͼ3".equals(content)){
							Images.changemap2();
							
									}
						if("����".equals(content)){
						music.play();
							
									}
						if("�ر�".equals(content)){
						music.stop();
							
									}
						if("�鿴".equals(content)){
							jdbc.query();
						}
					}
				};
			itemDB.addActionListener(al);
	//		itemFile.addActionListener(al);
			itemFile1.addActionListener(al);
			itemFile2.addActionListener(al);
			itemFile3.addActionListener(al);
			itemFile4.addActionListener(al);
			itemFile5.addActionListener(al);
			itemFile6.addActionListener(al);
    	
       //���
    	World w = new World();
    	//���Զ��������ӵ�����
    	f.add(w,BorderLayout.CENTER);
    	//������ʾ
    	f.setVisible(true);
        w.action();
    }
		
		}	
	/*
	 * 1)��:ΪʲôҪ�����������main����������?
	 *   ��:�������������main�У�����ζ�����þ�ֻ����main��ʹ���ˣ�
	 *      ��World���к������ǻ���ƺܶ෽���������ж����õ���Щ���ã�
	 *      Ϊ�������з����ж���ʹ�����ã�����Ҫ�����main������
	 * 2)��:ΪʲôҪ����action����������?
	 *   ��:��Ϊmain������static�ģ�����main���޷�������һ�����ã�
	 *      �Ǿ�ֻ�ܵ�������һ����static�ķ���action��������
	 *      ----Ϊ��static�������޷���������--��������5�콲
	 * 3)��:Ϊʲô��main��Ҫ�ȴ���World�����ٵ���action()����
	 *   ��:��Ϊmain������static�ģ�����main���޷�ֱ�ӵ���action()������
	 *      �Ǿ�ֻ���ȴ���World�����ٵ���action��
	 *      ----Ϊ��static�������޷�ֱ�ӵ���action--��������5�콲
	 */
	

