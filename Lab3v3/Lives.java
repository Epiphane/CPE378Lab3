import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Lives here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lives extends Actor
{
    GreenfootImage[] lives = { new GreenfootImage("lives0.png"),
                            new GreenfootImage("lives1.png"),
                            new GreenfootImage("lives2.png"),
                            new GreenfootImage("lives3.png"),
                            new GreenfootImage("lives4.png"),
                            new GreenfootImage("lives5.png") };
    int life = 5;
    public Lives()
    {
        updateImage();
    }
    
    private void updateImage()
    {
        setImage(lives[life]);
    }
    /**
     * Act - do whatever the Lives wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }
    public void adjustLives(int adjustmentValue)
    {
        life = adjustmentValue;
        if (life < 0) life = 0;
        if (life > 5) life = 5;
        
        else updateImage();
    }
}
