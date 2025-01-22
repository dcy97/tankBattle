package TankGame;

import javax.swing.*;

/**
 * JFrame 窗口
 * gameMain 窗口的构造器
 */
public class GameMain extends JFrame {
    MyPanel mp = null;
    public static void main(String[] args){
        GameMain gameMain = new GameMain();
    }
    // 窗口的构造器
    public GameMain(){
        mp = new MyPanel();// 创建面板
        // 将mp 放入到Thread 中，并启动
        new Thread(mp).start();
        this.add(mp);// 把面板放入窗口
        // 接口的引用可以指向实现了接口类的对象
        this.addKeyListener(mp);// 把面板的键盘监听器放入窗口
        this.setSize(1000,750);// 设置窗口大小
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置关闭方式
        this.setVisible(true);// 设置可见
    }
}
