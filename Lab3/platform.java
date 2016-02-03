import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class platform here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class platform extends Actor
{
    public void act() 
    {
       setLocation(getX(), getWorld().getHeight() - getImage().getHeight()/2);
       Level1.PlatPos = getImage().getHeight()+15;
    }    
}
