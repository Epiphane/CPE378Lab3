import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Hearts here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hearts extends Actor
{
    GreenfootImage[] hearts = { new GreenfootImage("no_hearts.png"),
                            new GreenfootImage("one_heart.png"),
                            new GreenfootImage("two_hearts.png"),
                            new GreenfootImage("three_hearts.png") };
  
    private int health = 3;
    public Hearts()
    {
        updateImage();
    }
    
    private void updateImage()
    {
        setImage(hearts[health]);
    }      
    /**
     * Act - do whatever the Hearts wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }
    public void adjustHearts(int adjustmentValue)
    {
        health = adjustmentValue;
        if (health < 0) health = 0;
        if (health > 3) health = 3;
        
        else updateImage();
    }
}
