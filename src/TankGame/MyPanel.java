package TankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * 坦克大战绘图区域
 * keyListener监听键盘事件
 */
//为了让Panel 不停的重绘，需要myPanel实现runnable接口，当做一个线程使用
public class MyPanel extends JPanel implements KeyListener, Runnable {
    // 定义我的坦克
    MyTank myTank = null;
    // 定义敌人坦克，放到Vector中
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义一个Vector，用于存储炸弹
    //当子弹击中坦克时，就加入一个Bomb对象，加入Vector
    Vector<Bomb> bombs = new Vector<>();
    int enemyTankSize = 3;

    //定义三张图片，用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public MyPanel() {
        //初始化我的坦克
        myTank = new MyTank(100, 100, 0, 10);
        //初始化敌人坦克
        for (int i = 0; i < enemyTankSize; i++) {
            //创建一个敌人坦克
            EnemyTank enemyTank = new EnemyTank((i + 1) * 100, 0, 2, 1);
            //给enemyTank加入子弹
            Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
            enemyTank.shots.add(shot);
            new Thread(enemyTank).start();
            // 启动线程
            new Thread(shot).start();
            //放入Vector中
            enemyTanks.add(enemyTank);
        }
        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
    }

    // 重写paint方法 画出坦克
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形，默认是黑色
        //画出自己坦克
        if (myTank != null && myTank.isLive) {
            drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), 0);
        }
        //如果Bomb集合中有炸弹，就画出
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //判断当前炸弹是否还存活
            if (bomb.life > 6) {
                //画出炸弹
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                //画出爆炸
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                //画出爆炸
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            //让这个炸弹的生命值减1
            bomb.lifeDown();
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }
        //画出子弹射击
//        if (myTank.shot != null && myTank.shot.isLive) {
//            g.draw3DRect(myTank.shot.x, myTank.shot.y, 1, 1, false);
//        }
        for (int i = 0; i < myTank.shots.size(); i++) {
            Shot shot = myTank.shots.get(i);
            if (shot != null && shot.isLive) {
                g.draw3DRect(shot.x, shot.y, 1, 1, false);
            } else {
                //移除
                myTank.shots.remove(shot);
            }
        }
        //画出敌人的坦克，遍历enemyTanks
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
//            判断当前坦克是否还存活
//            if (!enemyTank.isLive) {
//                enemyTanks.remove(enemyTank);
//            }
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTanks.get(i).shots.get(j);
                    if (shot.isLive) {
                        g.draw3DRect(shot.x, shot.y, 1, 1, false);
                    } else {
                        //移除
                        enemyTanks.get(i).shots.remove(shot);
                    }
                }
            }
        }
    }

    /**
     * 画出坦克
     *
     * @param x      坦克坐标x
     * @param y      坦克坐标y
     * @param g      画笔
     * @param direct 坦克方向
     * @param type   坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        //设置坦克颜色
        switch (type) {
            case 0://我的坦克
                g.setColor(Color.cyan);
                break;
            case 1://敌人的坦克
                g.setColor(Color.yellow);
                break;
        }
        //根据坦克方向绘制坦克
        switch (direct) {
            case 0://上
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克中间的矩形
                g.fillOval(x + 10, y + 20, 20, 20);//画出坦克圆盖子
                g.drawLine(x + 20, y + 30, x + 20, y);//画出坦克炮口
                break;
            case 1://右
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
            case 2://下
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 3://左
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
        }
    }

    //有字符输出时，该方法就会被触发
    @Override
    public void keyTyped(KeyEvent e) {

    }

    //    处理键盘按下事件
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            //向上移动
            case KeyEvent.VK_UP:
                if (myTank.getY() > 0) {
                    myTank.moveUp();
                    myTank.setDirect(0);
                    this.repaint();
                }
                break;


            case KeyEvent.VK_RIGHT:
                if (myTank.getX() + 60 < 1000) {
                    myTank.moveRight();
                    myTank.setDirect(1);
                    this.repaint();
                }
                break;
            case KeyEvent.VK_DOWN:
                if (myTank.getY() + 60 < 750) {
                    myTank.moveDown();
                    myTank.setDirect(2);
                    this.repaint();
                }
                break;
            case KeyEvent.VK_LEFT:
                if (myTank.getX() > 0) {
                    myTank.moveLeft();
                    myTank.setDirect(3);
                    this.repaint();
                }
                break;
        }
        //用户按下的键是control键，就发射子弹
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
//            判断子弹是否销毁,发射一颗子弹
//            if (myTank.shot == null|| !myTank.shot.isLive){
//                myTank.shotEnemyTank();
//            }
            //发射多颗子弹
            myTank.shotEnemyTank();

        }
    }

    // 判断我方子弹是否击中敌人坦克
    public void hitTank(Shot s, Tank tank) {
        // 判断击中
        switch (tank.getDirect()) {
            case 0://上
            case 2:
                if (s.x >= tank.getX() && s.x <= tank.getX() + 40 && s.y >= tank.getY() && s.y <= tank.getY() + 60) {
                    s.isLive = false;
                    tank.isLive = false;
                    enemyTanks.remove(tank);
                    //创建Bomb对象
                    bombs.add(new Bomb(tank.getX(), tank.getY()));
                }
                break;
            case 1://右
            case 3:
                if (s.x >= tank.getX() && s.x <= tank.getX() + 60 && s.y >= tank.getY() && s.y <= tank.getY() + 40) {
                    s.isLive = false;
                    tank.isLive = false;
                    enemyTanks.remove(tank);
                    bombs.add(new Bomb(tank.getX(), tank.getY()));
                }
                break;
        }
    }

    // 判断敌人坦克子弹是否击中我方坦克
    public void hitMyTank() {
        //遍历所有敌人坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            //遍历所有子弹
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                //判断子弹是否击中坦克
                if (myTank.isLive && shot.isLive){
                    hitTank(shot, myTank);
                }
            }
        }
    }

    public void hitEnemyTank() {
        for (int i = 0; i < myTank.shots.size(); i++) {
            Shot s = myTank.shots.get(i);
            // 单个子弹判断是否击中敌人坦克
            if (s != null && s.isLive) {
//                增强for底层使用迭代器，如果在遍历的时候修改集合，会报异常
//                for (EnemyTank enemyTank : enemyTanks) {
//                    hitEnemyTank(myTank.shot, enemyTank);
//                }
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    hitTank(s, enemyTank);
                }
            }
        }
    }

    //    处理键盘释放事件
    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {// 每隔100ms重绘一次
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 击中敌方
            hitEnemyTank();
            // 击中我方
            hitMyTank();
            this.repaint();
        }
    }
}
