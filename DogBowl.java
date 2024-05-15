public class DogBowl extends Entity implements Consumable, Scrollable{
    public static final String POWERUP_IMAGE_FILE = "game_graphics/dogBowl.gif";
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int SPEED = 5;
    public static final int POINTS = 10;
    private int scrollspeed = SPEED;
    
    public DogBowl (){
        this(0, 0);
    }
    public DogBowl(int x, int y){
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
    
    
    
    
    
