import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CutscenePlayer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CutscenePlayer extends AnimatedActor
{
    private Animation player_talk = AnimatedActor.generateSequence("player_talk", 2);
    private Animation player_angry = AnimatedActor.generateSequence("player_talk_angry", 2);
    
    public CutscenePlayer() {
        player_angry.load();
        player_talk.load();
        
        player_angry.speed = player_talk.speed = 1.0f;
        
        setAnimation(player_talk);
        setFacingRight(false); // awk
    }
    
    /**
     * Act - do whatever the CutscenePlayer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    } 
    
    /**
     * Advance a talking animation
     */
    public void animate()
    {
        super.act();
    }
}
