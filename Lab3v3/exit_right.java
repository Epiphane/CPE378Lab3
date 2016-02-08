import greenfoot.*; 

/**
 * Exit sign (this one is invisible in the game, check the code out!)
 * 
 * @author softwhizjx
 * @version 1.0
 */
public class exit_right extends Actor
{
    /** main world's variable */
    private static PlatformWorld platformer;
    
    /**
     * set invisible
     */
    public exit_right(){
        GreenfootImage clear = new GreenfootImage("right_exit.png");
        //clear.clear();
        setImage(clear);
    }
    
    /**
     * just wait for the player to come for exit
     */
    public void act() {
        playerExit();
    }    
    
    /**
     * get the player
     */
    private void playerExit(){
        player P1 = (player) getOneObjectAtOffset(0, 50, player.class);
        if (P1 != null && Greenfoot.isKeyDown("up")){
            platformer.nextLevel();
        }
    }
    
    public void addedToWorld(World world) {
        platformer = (PlatformWorld) world; 
    }
}
