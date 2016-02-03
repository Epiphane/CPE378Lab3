import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class slime extends Actor
{
    private GreenfootImage left = new GreenfootImage("slime_idle_left.png");
    private GreenfootImage right = new GreenfootImage("slime_idle_right.png");
    private int ySpeed;
        
    public void act() 
    {
        if (Greenfoot.isKeyDown("left")){
            move(-3);
            setImage(left);
        }
        if (Greenfoot.isKeyDown("right")){
            move(3);
            setImage(right);
        }
        
        int groundLevel = getWorld().getHeight() - Level1.PlatPos;
        boolean onGround = (getY() == groundLevel);
        if (!onGround) // in air
        {
            ySpeed++; // gravity
            setLocation(getX(), getY()+ySpeed); // fall 
            if (getY()>=groundLevel) // has landed 
            {
                setLocation(getX(), groundLevel); // set on ground
           }
        }
        else // on ground
        {
            if(Greenfoot.isKeyDown("space")){
                ySpeed = -15; // jump speed
                setLocation(getX(), getY()+ySpeed); // leave ground
                Greenfoot.playSound("jump.mp3");
            }
        }
    }    
}
