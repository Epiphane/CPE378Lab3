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
    private Animation player_concerned = AnimatedActor.generateSequence("player_talk_concerned", 2);
    private Animation player_angry = AnimatedActor.generateSequence("player_talk_angry", 2);
    private Animation player_irked = AnimatedActor.generateSequence("player_talk_irked", 2);
    private Animation player_sad = AnimatedActor.generateSequence("player_talk_sad", 2);
    
    public enum Expression {
        Happy, Concerned, Angry, Irked, Sad
    };
    
    int baseX, baseY;
    boolean shaking = false;
    
    public CutscenePlayer() {
        player_angry.load();
        player_talk.load();
        player_concerned.load();
        player_sad.load();
        player_irked.load();
        
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
        if (shaking) {
            setLocation(baseX + (int) (Math.random() * 4 - 2), baseY + (int) (Math.random() * 4 - 2));
        }
    } 
    
    /**
     * Advance a talking animation
     */
    public void animate()
    {
        super.act();
    }
    
    /** 
     * Set the expression/talking animation for the character
     */
    public void setExpression(Expression expr) {
        Animation anim = player_talk;
        shaking = false;
        switch (expr) {
        case Happy:
            anim = player_talk;
            break;
        case Sad:
            anim = player_sad;
            break;
        case Concerned:
            anim = player_concerned;
            break;
        case Angry:
            anim = player_angry;
            
            baseX = getX();
            baseY = getY();
            shaking = true;
            break;
        case Irked:
            anim = player_irked;
            
            baseX = getX();
            baseY = getY();
            shaking = true;
            break;
        }
        
        setAnimation(anim);
    }
}
