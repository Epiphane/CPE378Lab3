import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Fury here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Fury extends Actor
{
    /**
     * Act - do whatever the Fury wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }    
    
    public void set(int fury) {
        if (!player.FURY_ENABLED)
            fury = 0;
        fury = Math.min(6, fury);
        setImage("fury_" + fury + ".png");
    }
}
