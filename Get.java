//Gets are entities that the player wants to collide with, as they increase
//their score.
public class Get extends Entity implements Consumable, Scrollable {
    
    //Location of image file to be drawn for a Get
    public static final String GET_IMAGE_FILE = "game_graphics/dogtreat.gif";
    //Dimensions of the Get  
    public static final int GET_WIDTH = 60;
    public static final int GET_HEIGHT = 60;
    //Speed that the Get moves (in pixels) each time the game scrolls
    public static final int GET_DEFAULT_SCROLL_SPEED = 5;
    //Amount of points received when player collides with a Get
    public static final int GET_POINT_VALUE = 20;
    
    private int scrollSpeed = GET_DEFAULT_SCROLL_SPEED;
    
    public Get(){
        this(0, 0);        
    }
    
    public Get(int x, int y){
        super(x, y, GET_WIDTH, GET_HEIGHT, GET_IMAGE_FILE);  
    }
    
    public Get(int x, int y, String imageFileName){
        super(x, y, GET_WIDTH, GET_HEIGHT, imageFileName);
    }
    
    public int getScrollSpeed(){
        return this.scrollSpeed;
    }
    
    //Sets the scroll speed to the argument amount
    public void setScrollSpeed(int newSpeed){
       this.scrollSpeed = newSpeed;
    }
    
    //Move the Get left by its scroll speed
    public void scroll(){
        this.setX(getX() - this.scrollSpeed); // copied this from avoid
       //throw new IllegalStateException("Hey 102 Student! You need to implement scroll in Get.java!");
    }
    
    //Colliding with a Get increases the player's score by the specified amount
    public int getPoints(){
        return GET_POINT_VALUE;
    }
    
    //Colliding with a Get does not affect the player's HP
    public int getDamage(){
        return 0;
    }
    
}
