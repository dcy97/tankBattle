### JPanel类
1. JPanel类是JFrame类的子类，是JFrame类的容器，可以放置多个组件，可以设置背景颜色，可以设置布局管理器。
2. paint方法是绘图方法，里面参数Graphics g，g是画笔，g.drawString("Hello World", 100, 100);
3. paint方法调用时机有3种
+ 窗体创建时调用初始化会调用
+ 窗体显示时调用
+ 窗体大小改变时调用
+ 窗体关闭时调用