public class PowerUp extends Entity implements Consumable, Scrollable {
    public static final String POWERUP_IMAGE_FILE = "game_graphics/powerUp.gif";
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int SPEED = 8;
    public static final int POINTS = 40;
    private int scrollspeed = SPEED;

    public PowerUp (){
        this(0, 0);
    }
    public PowerUp(int x, int y){
        super(x,y, WIDTH, HEIGHT, POWERUP_IMAGE_FILE);
    }
    public int getScrollSpeed (){
        return this.SPEED;
    }
    public void setScrollSpeed(int newSpeed){
        this.scrollspeed =newSpeed;
    }
    public void scroll (){
        this.setX(getX()- this.scrollspeed);
    }
    public int getPoints(){
        return POINTS;
    }
    public int getDamage(){
        return 0;
    }
    public int getHP(){
        return +3;
    }
}




