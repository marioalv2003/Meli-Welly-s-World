import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AlvaradoMarioGame extends ScrollingGame{


//A Simple version of the scrolling game, featuring Avoids, Gets, and RareGets
//Players must reach a score threshold to win
//If player runs out of HP (via too many Avoid collisions) they lose
    
    
    //Starting Player coordinates
    protected static final int STARTING_PLAYER_X = 0;
    protected static final int STARTING_PLAYER_Y = 100;
    
    //Score needed to win the game
    protected static final int SCORE_TO_WIN = 500;
    
    //Maximum that the game speed can be increased to
    //(a percentage, ex: a value of 300 = 300% speed, or 3x regular speed)
    protected static final int MAX_GAME_SPEED = 300;
    //Interval that the speed changes when pressing speed up/down keys
    protected static final int SPEED_CHANGE_INTERVAL = 20;    
    
    public static final String INTRO_SPLASH_FILE = "game_graphics/melistart.gif";  
    public static final String BACKGROUND_SPLASH_FILE = "game_graphics/citystroll.gif";  
    public static final String PAUSE_SPLASH_FILE = "game_graphics/pause.gif";
    public static final String WINNER_SPLASH_FILE = "game_graphics/winner.gif";
    public static final String LOSER_SPLASH_FILE = "game_graphics/loser.gif";
    //Key pressed to advance past the splash screen
    public static final String INSTRUCT_SPLASH_FILE = "game_graphics/instructions.gif";
    public static final int ADVANCE_SPLASH_KEY = KeyEvent.VK_ENTER;
    
    //Interval that Entities get spawned in the game window
    //ie: once every how many ticks does the game attempt to spawn new Entities
    protected static final int SPAWN_INTERVAL = 45;

    
    //A Random object for all your random number generation needs!
    public static final Random rand = new Random();
    
    //Player's current score
    protected int score;
    
    
    //Stores a reference to game's Player object for quick reference
    //(This Player will also be in the displayList)
    protected Player player;
    
    
    public AlvaradoMarioGame(){
        super();
    }
    
    public AlvaradoMarioGame(int gameWidth, int gameHeight){
        super(gameWidth, gameHeight);
    }
    
    
    //Performs all of the initialization operations that need to be done before the game starts
    protected void pregame(){
        setSplashImage(INTRO_SPLASH_FILE);
        this.setBackgroundImage(BACKGROUND_SPLASH_FILE);
        player = new Player(STARTING_PLAYER_X, STARTING_PLAYER_Y);
        displayList.add(player); 
        score = 0;
    }
    
    //Called on each game tick
    protected void updateGame(){
        if (isPaused){
            return;
        }
        //scroll all scrollable Entities on the game board
        scrollEntities();   
        //Spawn new entities only at a certain interval
        if (super.getTicksElapsed() % SPAWN_INTERVAL == 0){
            spawnEntities();
            spawnEntities();
        }
        for (int counter =0; counter< displayList.size(); counter++){
            Entity otherElem = displayList.get(counter);
            //spawnEntities();
            if (player != otherElem && player.isCollidingWith(otherElem)){
                handlePlayerCollision((Consumable)otherElem);
                displayList.remove(counter);
            }

        }
        //Update the title text on the top of the window
        setTitleText("HP: "+ player.getHP() + ", Score:"+ score); 
        if (isGameOver() == true){
            postgame();
        }
    }
    
    //Scroll all scrollable entities per their respective scroll speeds
    private void scrollEntities(){
/* For this function is just moves the blocks from the begining of the game board
 * to the end of the board but one of things i am confused about is if the block touches the 
 * end does it automically disappears or does it leave until the end has left.
 */
        for (int x =0; x<displayList.size(); x++){
            Entity element = displayList.get(x);
            if (element instanceof Scrollable){
                Scrollable scrollableEntity = (Scrollable)element;
                scrollableEntity.scroll();
            }
        }
        for (int y=0; y<displayList.size();y++){
            Entity element = displayList.get(y);
            if (element.getX()+ element.getWidth() < 0 || element.getX()> getWindowWidth() ||
            element.getY() +element.getHeight() < 0 || element.getY() >getWindowHeight()){
                displayList.remove(y);
                y--;
            }
        }
    }
    
    
    
    //Called whenever it has been determined that the Player collided with a consumable
    private void handlePlayerCollision(Consumable collidedWith){
/* Craig the TA helped me with this . I was a little bit confused with this 
 * but this function esesentially checks if the player collides with the block and if so 
 * it performs the action respectively so for avoid it removes a life from the player and for the others
 * etc..... 
 */
        if (collidedWith instanceof RareGet){
            System.out.println("just hit a rare");
            score += ((RareGet)collidedWith).getPoints();
            int restore = ((RareGet)collidedWith).getHP();
            player.modifyHP(restore);
        }
        else if (collidedWith instanceof Get){
            System.out.println("hit a get");
            score += ((Get)collidedWith).getPoints();
        }
        else if (collidedWith instanceof Avoid){
            System.out.println("just hit an avoid");
            int damage = (collidedWith).getDamage();
            player.modifyHP(damage);
        }
        else if (collidedWith instanceof PowerUp){
            System.out.println("Just hit a power up");
                int points = ((PowerUp)collidedWith).getPoints();
                score+= points;
                player.setHP(3);
        }
        else if (collidedWith instanceof DogBowl){
            System.out.println("hit a dog bowl ");
            int points = ((DogBowl)collidedWith).getPoints();
            score+= points;
            if ((this.getGameSpeed() < MAX_GAME_SPEED)&& ((SPEED_CHANGE_INTERVAL +this.getGameSpeed() <= MAX_GAME_SPEED))){
                this.setGameSpeed(SPEED_CHANGE_INTERVAL + this.getGameSpeed());
                System.out.println("game is speeding up");
            }
        }
    }
    
    
    //Spawn new Entities on the right edge of the game board
    private void spawnEntities(){ 
/* Spawns the blocks on the screen randomly in different places on the board and different blocks */
/*Worked with the TA on this function */
        int randomEntities = rand.nextInt(25);
        //int randomEntities = (15);
        int WindowHeight = getWindowHeight()-40;
        int randomLocation = rand.nextInt(WindowHeight);
       // int randomLocation2 = rand.nextInt(getWindowHeight()-50);
        if (randomEntities >= 0 && randomEntities < 15){
            Entity avoid = new Avoid ();
            avoid.setX(getWindowWidth());
            avoid.setY(randomLocation - (avoid.getHeight()-75));
            if (validPlacement(avoid)){
                displayList.add(avoid);
            }
        }
        else if (randomEntities>= 16 && randomEntities <=21){
            Entity get = new Get ();
            //get.setX(getWindowWidth()-50);
            get.setX(getWindowWidth());
            get.setY(randomLocation- (get.getHeight()-55));
            if (validPlacement(get)){
                displayList.add(get);
            }
        }
        else if (randomEntities == 22 || randomEntities ==23){
                Entity rare = new RareGet();
                rare.setX(getWindowWidth());
                rare.setY(randomLocation - (rare.getHeight()-55));
                if (validPlacement(rare)){
                    displayList.add(rare);
                }
            }
        else if (randomEntities == 24 || randomEntities == 25){
                Entity bowl = new DogBowl();
                bowl.setX(getWindowWidth());
                bowl.setY(randomLocation- (bowl.getHeight()-55));
                if (validPlacement(bowl)){
                    displayList.add(bowl);
                }
            }
        else if (player.getHP() ==1){
            Entity power = new PowerUp();
            power.setX(getWindowWidth());
            power.setY(randomLocation-(power.getHeight()-55));
            if (validPlacement(power)){
                displayList.add(power);
            }
        }
    }

   public boolean validPlacement(Entity block){
/* Check during office hours to see if this logic is correct but this function just checks 
 * whether or not there is a block already placed in the screen so there is no overlapping and 
 * if its a vali place then it returns true.
*/
        for (Entity otherBlock : displayList){
            if (block != otherBlock && block.isCollidingWith(otherBlock)){
                return false;
            }
        }
        return true;
   }
    
    //Called once the game is over, performs any end-of-game operations
    protected void postgame(){
/* Displays the title depending if the player has made more than the maximum number of points
 */
        if (SCORE_TO_WIN <= score){
            super.setTitleText("Winner winner chicken dinner! You win! "+ " SCORE:" +score);
            this.setSplashImage(WINNER_SPLASH_FILE);
        }
        else if (SCORE_TO_WIN > score){
            super.setTitleText("Game is over! You are a loser! "+ " SCORE:"+ score);
            this.setSplashImage(LOSER_SPLASH_FILE);
        }
    }
    
    //Determines if the game is over or not
    //Game can be over due to either a win or lose state
    protected boolean isGameOver(){
/* Determines whether the game is over or not. If it is it returns a boolean  */
        if (player.getHP()== 0 || score >= SCORE_TO_WIN){ // conditional checking players health and max points 
            return true;                    // to see if game should terminate or keep running 
        }
        return false; 
       
    }
    
    //Reacts to a single key press on the keyboard
    protected void reactToKey(int key){
/* This function essentially acts based upon the key that is pressed. It can move the player up/down/left/right
 * and can increase the game speed and lower it. 
 */
        //if a splash screen is up, only react to the advance splash key
        if (getSplashImage() != null){
            if (key == ADVANCE_SPLASH_KEY)
                super.setSplashImage(null);
            return;
        }
        if (key == GameEngine.KEY_PAUSE_GAME){
            if (isPaused){
                isPaused = true;
                System.out.print("this game is "+ isPaused);
                this.setSplashImage(PAUSE_SPLASH_FILE);
            }
            else{
                isPaused=false;
                super.setSplashImage(null);
            }
            return;
        }
        if(key == GameEngine.LEFT_KEY){
            if (player.x> 0){
                System.out.println("trying to move backward <---");
                player.x -= 10;
            }
        }
        if (key == GameEngine.RIGHT_KEY){
            if (player.x <getWindowWidth()- player.getWidth()){
                System.out.println("trying to move forward -->");
                player.x+= 10;
            }
        }
        if (key ==GameEngine.UP_KEY){
            if (player.y>0){
                System.out.println("trying to move up");
                player.y -=10;
            }
        }
        if (key == GameEngine.DOWN_KEY){
            if (player.y <getWindowHeight()- player.getHeight()){
                System.out.println("trying to move down");
                player.y += 10;
            }
        } // *** for my game the player cannot increase or decrease the speed an item will do that ****
        // if (key == GameEngine.SPEED_UP_KEY && this.getGameSpeed() > player.DEFAULT_MOVEMENT_SPEED){
        //     System.out.println("trying to increase the speed +");
        //     int speed = this.getGameSpeed() + SPEED_CHANGE_INTERVAL;
        //     this.setGameSpeed(Math.min (speed, MAX_GAME_SPEED));
        // }
        // else if (key == GameEngine.SPEED_DOWN_KEY && this.getGameSpeed() > player.DEFAULT_MOVEMENT_SPEED){
        //     System.out.println("triyng to decrease the speed -");
        //     int speed = this.getGameSpeed() - SPEED_CHANGE_INTERVAL;
        //     this.setGameSpeed(Math.max(speed, player.DEFAULT_MOVEMENT_SPEED));
        // }
        if (key == GameEngine.KEY_QUIT_GAME){
            System.exit(0); // I got this from google... it gets the job done
        }
  }    
  
    
    
    //Handles reacting to a single mouse click in the game window
    protected MouseEvent reactToMouseClick(MouseEvent click){ // worked with Lyboult on this
        if (click != null){
            int clickX = click.getX();
            int clickY = click.getY();
            System.out.println("This is click X: "+clickX +"This is click Y: "+ clickY);
        }
        return click;//returns the mouse event for any child classes overriding this method
    }    
}