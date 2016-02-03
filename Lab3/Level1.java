import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Level1 extends World
{
    public static int PlatPos;
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public Level1()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1200, 720, 1); 
        prepare();
    }  

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        platform platform = new platform();
        addObject(platform,729,609);
        slime slime = new slime();
        addObject(slime,843,299);
        slime.setLocation(623,463);
        platform.setLocation(760,672);
    }
}
