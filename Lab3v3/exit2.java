import greenfoot.*; 


public class exit2 extends Actor
{
    /** main world's variable */
    private static PlatformWorld platformer;
    
    
    public exit2(){
        GreenfootImage pic = new GreenfootImage("right_exit.png");
        pic.clear();
        setImage(pic);
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
