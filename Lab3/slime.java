import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class slime extends Actor
{
    private int ySpeed = 0;
    private int jumpHeight = 15;
    private int gravity = 1;
    private boolean direction = true; //true = right, false = left
    private boolean airborne;
    private int groundOffset = 20; //offset for ground so standing on platform looks natural
   
    private GreenfootImage left = new GreenfootImage("slime_idle_left.png");
    private GreenfootImage right = new GreenfootImage("slime_idle_right.png");
    
        
    public void act() 
    {
        checkKey();
        checkRightWall();
        checkLeftWall();
        checkFall();
    }
    
    public void checkKey()
    {
        if (Greenfoot.isKeyDown("left"))
        {
            move(-3);
            direction = false;
        }
        if (Greenfoot.isKeyDown("right"))
        {
            move(3);
            direction = true;
        }
        if(Greenfoot.isKeyDown("space") && airborne == false)
        {
            jump();
        }
    }
    
    public void fall()
    {
        setLocation(getX(), getY()+ySpeed);
        if (ySpeed <= 9) 
        {
            ySpeed = ySpeed+gravity;
        }
        airborne = true;
    }

    public boolean onGround()
    {
        int spriteHeight = getImage().getHeight();
        Actor groundCenter = getOneObjectAtOffset(0, getImage().getHeight()/2, platform.class);
        Actor groundLeft = getOneObjectAtOffset((getImage().getWidth())/2*(-1), getImage().getHeight()/2, platform.class);
        Actor groundRight = getOneObjectAtOffset(getImage().getWidth()/2, getImage().getHeight()/2, platform.class);
        
        if(groundLeft != null)
        {
            moveToGround(groundLeft);
            return true;
        }
        else if(groundRight != null){
            moveToGround(groundRight);
            return true;
        }
        else if(groundCenter != null)
        {
            moveToGround(groundCenter);
            return true;
        }        
        else        
        {
            airborne = true;
            return false;
        }
    }

    public void moveToGround(Actor ground)
    {
        int groundHeight = ground.getImage().getHeight() - groundOffset;
        int newY = ground.getY()-(groundHeight+getImage().getHeight())/2;
        setLocation(getX(), newY);
        airborne = false;
    }

    public void checkFall()
    {
        if (onGround()) 
        {
            ySpeed = 0; 
        }
        else 
        {
            fall();
        }
    }

    public void jump()
    {
        ySpeed = ySpeed-jumpHeight;
        airborne = true;
        fall();
    }
    
    public boolean checkRightWall()
    {
        int spriteWidth = getImage().getWidth();
        int xDistance = (int)(spriteWidth/2);
        Actor rightWall = getOneObjectAtOffset(xDistance, 0, platform.class);
        if(rightWall == null)
        {
            return false;
        }
        else
        {
            stopByRightWall(rightWall);
            return true;
        }
    }
    public void stopByRightWall(Actor rightWall)
    {
        int wallWidth = rightWall.getImage().getWidth();
        int newX = rightWall.getX() - (wallWidth + getImage().getWidth())/2;
        setLocation(newX, getY());

    }

    public boolean checkLeftWall()
    {
        int spriteWidth = getImage().getWidth();
        int xDistance = (int)(spriteWidth/-2);
        Actor leftWall = getOneObjectAtOffset(xDistance, 0, platform.class);
        if(leftWall == null)
        {
            return false;
        }
        else
        {
            stopByLeftWall(leftWall);
            return true;
        }
    }

    public void stopByLeftWall(Actor leftWall)
    {
        int wallWidth = leftWall.getImage().getWidth();
        int newX = leftWall.getX() + (wallWidth + getImage().getWidth())/2;
        setLocation(newX, getY());
    }
}
