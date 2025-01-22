package TankGame;

/**
 * 射击子弹
 */
public class Shot implements Runnable {
    int x, y;
    int direct = 0;//方向
    int speed = 5;//速度
    boolean isLive = true;//是否存活

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {
        while (isLive) {
            try {
                //线程休眠50ms
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据方向移动
            switch (direct) {
                case 0://上
                    y -= speed;
                    break;
                case 1://右
                    x += speed;
                    break;
                case 2://下
                    y += speed;
                    break;
                case 3://左
                    x -= speed;
                    break;
            }
            //判断子弹是否到边界
            if (x < 0 || y < 0 || x > 1000 || y > 750|| !isLive) {
                isLive=false;
                break;
            }
        }
    }
}
