package cn.tedu.shoot;
import java.awt.image.BufferedImage;
import java.util.Random;
/** 小蜜蜂:是飞行物，也是奖励 */
public class Bee extends FlyingObject implements EnemyAward {
	private int xSpeed; //x坐标移动速度
	private int ySpeed; //y坐标移动速度
	private int awardType; //奖励类型
	/** 构造方法 */
	public Bee(){
		super(60,51);
		xSpeed = 1;
		ySpeed = 2;
		Random rand = new Random(); //随机数对象
		awardType = rand.nextInt(2); //0到1之内的随机数
	}
	
	/** 重写step()移动 */
	public void step() {
		x+=xSpeed; //x+(向左或向右)
		y+=ySpeed; //y+(向下)
		if(x<=0 || x>=World.WIDTH-width) { //若x<=0或x>=窗口宽-蜜蜂宽，表示到两头了
			xSpeed*=-1; //切换方向(正变负、负变正)
		}
	}
	
	private int index = 1; //爆破图下标
	/** 重写getImage()获取图片 */
	public BufferedImage getImage() { //每10毫秒走一次
		if(isLife()) { //若活着的
			return Images.bees[0]; //返回第1张图片即可
		}else if(isDead()) { //若死了的
			BufferedImage img = Images.bees[index++]; //获取爆破图
			if(index==Images.bees.length) { //若到最后一张图了
				state = REMOVE;             //则将状态修改为REMOVE删除的
			}
			return img; //返回爆破图
		}
		return null; //删除状态时，不返回图片
	}
	
	/** 重写getAwardType()获取奖励类型 */
	public int getAwardType() {
		return awardType; //返回奖励类型
	}
}







