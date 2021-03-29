package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** 子弹:是飞行物 */
public class Bullet extends FlyingObject {
	private int speed; //移动速度
	/** 构造方法 */
	public Bullet(int x,int y){ //子弹构造带参是因为有很多发子弹，而每发子弹的位置要根据英雄机的位置来计算得到，而英雄机位置不固定，所以x/y不能写死
		super(800,2000,x,y);
		speed = 3;
	}
	
	/** 重写step()移动 */
	public void step() {
		y-=speed; //y-(向上)
	}
	
	/** 重写getImage()获取图片 */
	public BufferedImage getImage() { //每10后毫秒走一次
		if(isLife()) {            //若为活着的
			return Images.bullet; //则直接返回bullet图片
		}else if(isDead()) { //若为死了的
			state = REMOVE;  //则将状态修改为REMOVE删除的
		}
		return null; //死了的和删除的，都不返回图片
		/*
		 * 若活着的: 返回子弹图片
		 * 若死了的: 将状态修改为REMOVE，同时不返回图片
		 * 若删除的: 不返回图片
		 */
	}
	
	/** 重写isOutOfBounds()判断子弹是否越界 */
	public boolean isOutOfBounds() {
		return y<=-height; //子弹的y<=负的子弹的高，即为越界了
	}
	
}
