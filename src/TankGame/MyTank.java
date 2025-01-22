package TankGame;

import java.util.Vector;

public class MyTank extends Tank {
    //定义一个shot 对象
    Shot shot = null;

    //可以发射多个子弹
    Vector<Shot> shots = new Vector<>();

    public MyTank(int x, int y, int direct, int speed) {
        super(x, y, direct, speed);
    }

    //射击
    public void shotEnemyTank() {
//        控制最多只有5颗子弹
        if (shots.size() == 5){
            return;
        }

         //创建shot 对象 根据当前坦克的坐标和方向来创建
        switch (getDirect()) {
            case 0://上
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1://右
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2://下
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3://左
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }
        //把刚刚创建的shot对象放入到shots集合中
        shots.add(shot);
        //启动我们的射击线程
        new Thread(shot).start();
    }
}
