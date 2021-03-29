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
/** 整个窗口 */
public class World extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400;  //窗口的宽
	public static final int HEIGHT = 700; //窗口的高
	public static final int START = 0;     //启动状态
	public static final int RUNNING = 1;   //运行状态
	public static final int PAUSE = 2;     //暂停状态
	public static final int GAME_OVER = 3; //游戏结束状态
	private int state = START; //当前状态(默认启动状态)
	private Hero hero = new Hero(); //英雄机
	private Sky sky = new Sky();    //天空
	private FlyingObject[] enemies = {}; //敌人(小敌机、大敌机、小蜜蜂)数组
	private Bullet[] bullets = {}; //子弹数组
	/** 生成敌人(小敌机、大敌机、小蜜蜂)对象 */
	public FlyingObject nextOne() {
		Random rand = new Random(); //随机数对象
		int type = rand.nextInt(20); //0到19之间的随机数
		if(type<5) { //0到4时，返回小蜜蜂对象
			return new Bee();
		}else if(type<14) { //5到13时，返回小敌机对象
			return new Airplane();
		}else { //14到19时，返回大敌机对象
			return new BigAirplane();
		}
	}
	private int enterIndex = 0; //敌人入场计数器
	/** 敌人(小敌机、大敌机、小蜜蜂)入场 */
	public void enterAction() { //每10毫秒走一次
		enterIndex++; //每10毫秒增1
		if(enterIndex%40==0) { //每400(40*10)毫秒走一次
			FlyingObject obj = nextOne(); //获取敌人对象
			enemies = Arrays.copyOf(enemies,enemies.length+1); //扩容
			enemies[enemies.length-1] = obj; //将敌人对象obj添加到enemies最后一个元素上
		}
	}
	
	private int shootIndex = 0; //子弹入场计数
	/** 子弹入场 */
	public void shootAction() { //每10毫秒走一次
		shootIndex++; //每10毫秒增1
		if(shootIndex%30==0) { //每300(30*10)毫秒走一次
			Bullet[] bs = hero.shoot(); //获取子弹数组对象
			bullets = Arrays.copyOf(bullets,bullets.length+bs.length); //扩容(bs有几个元素就扩大几个容量)
			System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length); //数组的追加
		}
	}
	/** 飞行物移动 */
	public void shootAction1() { //每10毫秒走一次
		shootIndex++; //每10毫秒增1
		if(shootIndex%3==0) { //每300(30*10)毫秒走一次
			Bullet[] bs = hero.shoot(); //获取子弹数组对象
			bullets = Arrays.copyOf(bullets,bullets.length+bs.length); //扩容(bs有几个元素就扩大几个容量)
			System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length); //数组的追加
		}
	}

	public void enterAction1() { //每10毫秒走一次
		enterIndex++; //每10毫秒增1
		if(enterIndex%5==0) { //每400(40*10)毫秒走一次
			FlyingObject obj = nextOne(); //获取敌人对象
			enemies = Arrays.copyOf(enemies,enemies.length+1); //扩容
			enemies[enemies.length-1] = obj; //将敌人对象obj添加到enemies最后一个元素上
		}
	}
	public void stepAction() { //每10毫秒走一次
		sky.step(); //天空动
		for(int i=0;i<enemies.length;i++) { //遍历所有敌人
			enemies[i].step(); //敌人动
		}
		for(int i=0;i<bullets.length;i++) { //遍历所有子弹
			bullets[i].step(); //子弹动
		}
	}
	
	/** 删除越界的敌人和子弹----避免内存泄漏 */
	public void outOfBoundsAction() { //每10毫秒走一次
		for(int i=0;i<enemies.length;i++) { //遍历所有敌人
			if(enemies[i].isOutOfBounds() || enemies[i].isRemove()) { //越界的或者REMOVE状态的
				enemies[i] = enemies[enemies.length-1]; //用最后一个敌人将越界敌人替换掉
				enemies = Arrays.copyOf(enemies,enemies.length-1); //缩容
			}
		}
		
		for(int i=0;i<bullets.length;i++) { //遍历所有子弹
			if(bullets[i].isOutOfBounds() || bullets[i].isRemove()) { //越界的或者REMOVE状态的
				bullets[i] = bullets[bullets.length-1]; //用最后一个子弹将越界子弹替换掉
				bullets = Arrays.copyOf(bullets,bullets.length-1); //缩容
			}
		}
	}
	
	private int score = 0; //玩家得分
	/** 子弹与敌人的碰撞 */
	public void bulletBangAction() { //每10毫秒走一次
		for(int i=0;i<bullets.length;i++) { //遍历所有子弹
			Bullet b = bullets[i]; //获取每一个子弹
			for(int j=0;j<enemies.length;j++) { //遍历所有敌人
				FlyingObject f = enemies[j]; //获取每一个敌人
				if(b.isLife() && f.isLife() && f.isHit(b)) { //若都活着，并且还撞上了
					b.goDead(); //子弹去死
					f.goDead(); //敌人去死
					
					if(f instanceof EnemyScore) { //若被撞敌人能得分
						EnemyScore es = (EnemyScore)f; //将被撞对象强转分得分接口
						score += es.getScore(); //玩家得分
					}
					if(f instanceof EnemyAward) { //若被撞敌人为奖励
						EnemyAward ea = (EnemyAward)f; //将被撞对象强转为奖励接口
						int type = ea.getAwardType(); //获取奖励类型
						switch(type) { //根据奖励类型的不同来获取不同的奖励
						case EnemyAward.FIRE: //若奖励类型为FIRE火力值
							hero.addFire();   //则英雄机增火力
							break;
						case EnemyAward.LIFE: //若奖励类型为LIFE命
							hero.addLife();   //则英雄机增命
							break;
						}
					}
				}
			}
		}
	}

	/** 英雄机与敌人的碰撞 */
	public void heroBangAction() { //每10毫秒走一次
		for(int i=0;i<enemies.length;i++) { //遍历所有敌人
			FlyingObject f = enemies[i]; //获取每一个敌人
			if(hero.isLife() && f.isLife() && f.isHit(hero)) { //若都活着，并且还撞上了
				f.goDead(); //敌人去死
				hero.subtractLife(); //英雄机减命
				hero.clearFire(); //英雄机清空火力值
			}
		}
	}
	
	/** 检测游戏结束 */
	public void checkGameOverAction() { //每10毫秒走一次
		if(hero.getLife()<=0) { //若英雄机命数<=0，表示游戏结束了
			state = GAME_OVER; //将当前状态修改为GAME_OVER游戏结束状态 
		}
	}
	
	/** 启动程序的执行 */
	public void action() {
		//侦听器对象
		MouseAdapter m = new MouseAdapter() {
			/** 重写mouseMoved()鼠标移动事件 */
			public void mouseMoved(MouseEvent e) {
				if(state==RUNNING) { //运行状态时执行
					int x = e.getX(); //获取鼠标的x坐标
					int y = e.getY(); //获取鼠标的y坐标
					hero.moveTo(x, y); //英雄机随着鼠标移动
				}
			}
			/** 重写mouseClicked()鼠标点击事件 */
			public void mouseClicked(MouseEvent e) {
				switch(state) { //根据当前状态做不同的处理
				case START:          //启动状态时
					state = RUNNING; //修改为运行状态
					break;
				case GAME_OVER:    //游戏结束状态时
					score = 0;     //清理现场(所有数据还原)
					hero = new Hero();
					sky = new Sky();
					enemies = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START; //修改为启动状态
					break;
				}
			}
			/** 重写mouseExited()鼠标移出事件 */
			public void mouseExited(MouseEvent e) {
				if(state==RUNNING) { //运行状态时
					state = PAUSE;   //修改为暂停状态
				}
			}
			/** 重写mouseEntered()鼠标移入事件 */
			public void mouseEntered(MouseEvent e) {
				if(state==PAUSE) {   //暂停状态时
					state = RUNNING; //修改为运行状态
				}
			}
			
		}; 
		this.addMouseListener(m); //处理鼠标操作事件
		this.addMouseMotionListener(m); //处理鼠标滑动事件
		
		Timer timer = new Timer(); //定时器对象
		int intervel = 10; //定时间隔(每10毫秒走一次)
		timer.schedule(new TimerTask() {
			public void run() { //定时干的事(每10毫秒走一次)
				if(state==RUNNING) { //运行状态时执行
					enterAction(); //敌人(小敌机、大敌机、小蜜蜂)入场
					shootAction(); //子弹入场
					stepAction();  //飞行物移动
					outOfBoundsAction();   //删除越界的敌人和子弹
					bulletBangAction();    //子弹与敌人的碰撞
					heroBangAction();      //英雄机与敌人的碰撞
					checkGameOverAction(); //检测游戏结束
				}
				repaint();     //重画(重新调用paint()方法)
			}
		},intervel,intervel); //定时计划表
		
		
	}
	
	/** 重写paint()画  g:画笔 */
	public void paint(Graphics g) { //每10 毫秒走一次
		g.drawImage(sky.getImage(),sky.x,sky.y,null);       //画天空
		g.drawImage(sky.getImage(),sky.x,sky.getY1(),null); //画天空的第2张图片
		g.drawImage(hero.getImage(),hero.x,hero.y,null);    //画英雄机
		for(int i=0;i<enemies.length;i++) { //遍历所有敌人
			FlyingObject f = enemies[i]; //获取每个敌人
			g.drawImage(f.getImage(),f.x,f.y,null); //画敌人
		}
		for(int i=0;i<bullets.length;i++) { //遍历所有子弹
			Bullet b = bullets[i]; //获取每个子弹
			g.drawImage(b.getImage(),b.x,b.y,null); //画子弹
		}
		
		g.drawString("SCORE: "+score,10,25);         //画分
		g.drawString("LIFE: "+hero.getLife(),10,45); //画命
		switch(state) { //在不同的状态下画不同的图
		case START: //启动状态时画启动图
			g.drawImage(Images.start,0,0,null);
			break;
		case PAUSE: //暂停状态时画暂停图
			g.drawImage(Images.pause,0,0,null);
			break;
		case GAME_OVER: //游戏结束状态时画游戏结束图
			g.drawImage(Images.gameover,0,0,null);
			break;
		}
	}  
	public static void main(String[] args) {
		
		
	    
	    
		final JFrame f = new JFrame();
    	f.setSize(400,700);
    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	//居中
    	f.setLocationRelativeTo(null);
    	
    	/*
    	 * 添加菜单
    	 */
    	JMenuBar bar = new JMenuBar();//菜单容器
    	//菜单
    	JMenu menu01 = new JMenu("存数据");
    	JMenu menu02 = new JMenu("地图切换");
    	JMenu menu03 = new JMenu("播放音乐");
    	JMenu menu04 = new JMenu("玩家列表");
    	//菜单项
    	JMenuItem itemDB = new JMenuItem("存档");
    	//JMenuItem itemFile = new JMenuItem("查询数据");
    	JMenuItem itemFile1 = new JMenuItem("地图1");
    	JMenuItem itemFile2 = new JMenuItem("地图2");
    	JMenuItem itemFile3 = new JMenuItem("地图3");
    	JMenuItem itemFile4 = new JMenuItem("播放");
    	JMenuItem itemFile5 = new JMenuItem("关闭");
    	JMenuItem itemFile6 = new JMenuItem("查看");
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
    	
    	//添加事件
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
//						System.out.println("单击了:"+content);
						if("存档".equals(content)){
//							JOptionPane.showMessageDialog(
//									f,"准备写入数据库","提示",JOptionPane.PLAIN_MESSAGE);
							String username = JOptionPane.showInputDialog(f, 
									"用户名","username",JOptionPane.PLAIN_MESSAGE);
							String score = JOptionPane.showInputDialog(f, 
									"分数","score",JOptionPane.PLAIN_MESSAGE);
							System.out.println(username+","+score);
							jdbc.insert(username, score);
							}
						if("地图1".equals(content)){
					Images.changemap1();
				
					
							}
						if("查询数据".equals(content)){
							jdbc.query();
							
									}
						if("地图2".equals(content)){
							Images.changemap();
							
									}
						if("地图3".equals(content)){
							Images.changemap2();
							
									}
						if("播放".equals(content)){
						music.play();
							
									}
						if("关闭".equals(content)){
						music.stop();
							
									}
						if("查看".equals(content)){
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
    	
       //面板
    	World w = new World();
    	//将自定义面板添加到窗口
    	f.add(w,BorderLayout.CENTER);
    	//窗口显示
    	f.setVisible(true);
        w.action();
    }
		
		}	
	/*
	 * 1)问:为什么要将引用设计在main方法的外面?
	 *   答:若将引用设计在main中，则意味着引用就只能在main中使用了，
	 *      而World类中后期我们会设计很多方法，方法中都得用到这些引用，
	 *      为了在所有方法中都能使用引用，所以要设计在main的外面
	 * 2)问:为什么要创建action方法来测试?
	 *   答:因为main方法是static的，所以main中无法访问那一堆引用，
	 *      那就只能单独创建一个非static的方法action来做测试
	 *      ----为何static方法中无法访问引用--面向对象第5天讲
	 * 3)问:为什么在main中要先创建World对象再调用action()方法
	 *   答:因为main方法是static的，所以main中无法直接调用action()方法，
	 *      那就只能先创建World对象再调用action了
	 *      ----为何static方法中无法直接调用action--面向对象第5天讲
	 */
	

