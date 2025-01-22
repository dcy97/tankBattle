package TankGame;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
    //定义敌人子弹集合
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;

    public EnemyTank(int x, int y, int direct, int speed) {
        super(x, y, direct, speed);
    }

    @Override
    public void run() {
        while (isLive) {

//            如果shots size（）=0 创建一颗子弹
            if (isLive&&shots.size() < 10){
                Shot shot = null;
                switch (getDirect()) {
                    case 0://向上
                        shot = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1://向右
                        shot = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2://向下
                        shot = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3://向左
                        shot = new Shot(getX(), getY() + 20, 3);
                        break;
                }
                shots.add(shot);
                new Thread(shot).start();
            }

//            敌人坦克自由移动
            switch (getDirect()) {
                case 0://向上
                    for (int i = 0; i < 30; i++) {
//                        这里是控制敌人坦克边界
                        if (getY() > 0){
                            moveUp();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                case 1://向右
                    for (int i = 0; i < 30; i++) {
                        if (getX()+60 < 1000){
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2://向下
                    for (int i = 0; i < 30; i++) {
                        if (getY()+60 < 750){
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3://向左
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0){
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setDirect((int) (Math.random() * 4));
        }
    }
}
