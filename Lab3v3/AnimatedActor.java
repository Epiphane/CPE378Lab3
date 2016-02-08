import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class AnimatedActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AnimatedActor extends Actor
{
    /**
     * Animation class
     */
    public static class Animation {
        String name;
        String extension = ".png";
        int[] sequence;
        float speed;
        
        GreenfootImage[] frames;
        
        Animation(String name, int[] seq) {
            this(name, seq, 0.2f);
        }
        
        Animation(String name, int[] seq, float speed) {
            this.name = name;
            this.sequence = seq;
            this.speed = speed;
            
            frames = new GreenfootImage[seq.length * 2];
        }
        
        public void load() {
            for (int i = 0; i < sequence.length; i ++) {
                frames[i] = new GreenfootImage(name + "_" + (sequence[i] + 1) + extension);
                frames[sequence.length + i] = new GreenfootImage(name + "_" + (sequence[i] + 1) + extension);
                frames[sequence.length + i].mirrorHorizontally();
            }
        }
        
        public GreenfootImage getFrame(int frame, boolean flip) {
            if (flip) return frames[sequence.length + frame];
            else return frames[frame];
        }
    }
    
    /**
     * Animation sequences follow the form [frame1, frame2, frame3, ...]
     */
    private Animation current_animation = null;
    private float current_frame = 0;
    private boolean isFacingRight = true;
    
    /**
     * Set whether this character is facing left or right
     */
    public void setFacingRight(boolean right) {
        isFacingRight = right;
    }
    
    /**
     * Create a typical animation with name name and sequence [0, 1, 2, ...]
     */
    protected static Animation generateSequence(String name, int length) {
        int[] seq = new int[length];
        
        for (int i = 0; i < length; i ++)
            seq[i] = i;
        
        return new Animation(name, seq);
    }
    
    protected void setAnimation(Animation anim) {
        if (current_animation == anim) return;
        
        current_animation = anim;
        current_frame = 0;
    }
    
    /**
     * Act - do whatever the AnimatedActor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (current_animation == null) {
            return;
        }
        
        /**sprite frame loop*/
        current_frame += current_animation.speed;
        if (current_frame >= current_animation.sequence.length){
            current_frame -= current_animation.sequence.length;
        }
        
        /**set a new image*/
        this.setImage(current_animation.getFrame((int)current_frame, isFacingRight));
    }
}
