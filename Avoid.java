//Avoids are entities the player needs to avoid colliding with.
//If a player collides with an avoid, it reduces the players Hit Points (HP).
public class Avoid extends Entity implements Consumable, Scrollable {
    
    //Location of image file to be drawn for an Avoid
    public static final String AVOID_IMAGE_FILE = "game_graphics/chocolate.gif";
    //Dimensions of the Avoid    
    public static final int AVOID_WIDTH = 75;
    public static final int AVOID_HEIGHT = 75;
    //Speed that the avoid moves each time the game scrolls
    public static final int AVOID_DEFAULT_SCROLL_SPEED = 5;

    private int scrollSpeed = AVOID_DEFAULT_SCROLL_SPEED;
        
    public Avoid(){
        this(0, 0);        
    }
    
    public Avoid(int x, int y){
        super(x, y, AVOID_WIDTH, AVOID_HEIGHT, AVOID_IMAGE_FILE);  
    }
    
    
    public int getScrollSpeed(){
        return this.scrollSpeed;
    }
    
    //Sets the scroll speed to the argument amount
    public void setScrollSpeed(int newSpeed){
       this.scrollSpeed = newSpeed;
    }
    
    //Move the avoid left by the scroll speed
    public void scroll(){
        setX(getX() - this.scrollSpeed);
    }
    
    //Colliding with an Avoid does not affect the player's score
    public int getPoints(){
        return 0; // since it doesnt do anything this would be 0 right?
       //implement me!
      // throw new IllegalStateException("Hey 102 Student! You need to implement getPoints in Avoid.java!");
    }
    
    //Colliding with an Avoid Reduces players HP by 1
    public int getDamage(){
        return -1;
    }
    
}
