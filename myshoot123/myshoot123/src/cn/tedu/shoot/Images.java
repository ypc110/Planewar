package cn.tedu.shoot;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
/** 图片工具类 */
public class Images {
//  公开的   静态的     图片数据类型   变量名(引用)
	public static BufferedImage sky;     //天空图片
	public static BufferedImage bullet;  //子弹图片
	public static BufferedImage[] heros;  //英雄机图片数组
	public static BufferedImage[] airs;  //小敌机图片数组 
	public static BufferedImage[] bairs; //大敌机图片数组
	public static BufferedImage[] bees;  //小蜜蜂图片数组
	
	public static BufferedImage start;    //启动图
	public static BufferedImage pause;    //暂停图
	public static BufferedImage gameover; //游戏结束图
	
	static { //初始化静态资源
		start = readImage("start.png");
		pause = readImage("pause.png");
		gameover = readImage("gameover.png");
		
		sky = readImage("background.png");
		bullet = readImage("bullet.png");
		
		heros = new BufferedImage[2]; //2张图片
		heros[0] = readImage("hero0.png");
		heros[1] = readImage("hero1.png");
		
		airs = new BufferedImage[5];  //5张图片
		bairs = new BufferedImage[5]; //5张图片
		bees = new BufferedImage[5];  //5张图片
		airs[0] = readImage("airplane.png");
		bairs[0] = readImage("bigairplane.png");
		bees[0] = readImage("bee.png");
		for(int i=1;i<airs.length;i++) { //给爆破图赋值
			airs[i] = readImage("bom"+i+".png");
			bairs[i] = readImage("bom"+i+".png");
			bees[i] = readImage("bom"+i+".png");
		}
		
	}
	public static void changemap() {
		
	
		
		
			start = readImage("start.png");
			pause = readImage("pause.png");
			gameover = readImage("gameover.png");
			
			sky = readImage("background1.png");
			bullet = readImage("bullet1.png");
			
			heros = new BufferedImage[2]; //2张图片
			heros[0] = readImage("hero0.png");
			heros[1] = readImage("hero1.png");
			
			airs = new BufferedImage[5];  //5张图片
			bairs = new BufferedImage[5]; //5张图片
			bees = new BufferedImage[5];  //5张图片
			airs[0] = readImage("airplane.png");
			bairs[0] = readImage("bigairplane.png");
			bees[0] = readImage("bee.png");
			
			
		
		}
	public static void changemap1() {
		
		
		
		
		start = readImage("start.png");
		pause = readImage("pause.png");
		gameover = readImage("gameover.png");
		
		sky = readImage("background2.png");
		bullet = readImage("bullet2.png");
		
		heros = new BufferedImage[2]; //2张图片
		heros[0] = readImage("hero0.png");
		heros[1] = readImage("hero1.png");
		
		airs = new BufferedImage[5];  //5张图片
		bairs = new BufferedImage[5]; //5张图片
		bees = new BufferedImage[5];  //5张图片
		airs[0] = readImage("airplane.png");
		bairs[0] = readImage("bigairplane.png");
		bees[0] = readImage("bee.png");
		
		
	
	}
	public static void changemap2() {
		
		
		
		
		start = readImage("start.png");
		pause = readImage("pause.png");
		gameover = readImage("gameover.png");
		
		sky = readImage("background3.png");
		bullet = readImage("bullet3.png");
		
		heros = new BufferedImage[2]; //2张图片
		heros[0] = readImage("hero0.png");
		heros[1] = readImage("hero1.png");
		
		airs = new BufferedImage[5];  //5张图片
		bairs = new BufferedImage[5]; //5张图片
		bees = new BufferedImage[5];  //5张图片
		airs[0] = readImage("airplane.png");
		bairs[0] = readImage("bigairplane.png");
		bees[0] = readImage("bee.png");
		
		
	
	}
	/** 读取图片 fileName:图片名称 */
	public static BufferedImage readImage(String fileName){
		try{
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName)); //读取与FlyingObject类在同一包中的图片
			return img;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
