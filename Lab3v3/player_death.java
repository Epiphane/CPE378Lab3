import greenfoot.*; 

/**
 * death animation
 * can be more than 3 main types:
 * 1) falling down the screen
 * 2) lie down the platform
 * 3) explode self
 * etc...
 * In this case, it's explosion. Cuz it's easy to make than anything else.
 * 
 * @author softwhizjx
 * @version 1.0
 */
public class player_death extends Actor
{
    /** main world's variable */
    private static PlatformWorld platformer;
    
    /**death images*/
    private GreenfootImage frames[] = new GreenfootImage[6];
    
    /**explosion state value*/
    int state = 0;
    
    /**help slow the animation down*/
    int explodeDelay = 0;
    
    /**
     * load images
     */
    public player_death(){
        if (frames[0] == null){
            frames[0] = new GreenfootImage("player_explosion_a.gif");
            frames[1] = new GreenfootImage("player_explosion_b.gif");
            frames[2] = new GreenfootImage("player_explosion_c.gif");
            frames[3] = new GreenfootImage("player_explosion_d.gif");
            frames[4] = new GreenfootImage("player_explosion_e.gif");
            frames[5] = new GreenfootImage("player_explosion_f.gif");
        }
        setImage(frames[0]);
    }
    
    /**
     * set animation and disappear
     */
    public void act() {
        explodeDelay++;
        if (state < 5 && explodeDelay == 10){
            state++;
            explodeDelay = 0;
        }
        setImage(frames[state]);
        
        if (state == 5){
            
            getWorld().removeObject(this);
            
            
        }
    }    
}
