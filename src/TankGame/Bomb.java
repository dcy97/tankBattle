package TankGame;

public class Bomb {
    int x,y;//炸弹的坐标
    int life = 9;//炸弹的生命周期
    boolean isLive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void lifeDown(){
        if(life > 0){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            life--;
        }else{
            isLive = false;
        }
    }
}
