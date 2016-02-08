import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;
/**
 * enemy object for my custom made platform game
 * 
 * @author softwhizjx
 * @version 1.0
 */
public class enemy extends Actor
{
    /**
     * Enemy's Variables
     */

    /** main world's variable */
    private static PlatformWorld platformer;
    
    /** enemy's position */
    float x, y;
    
    /** enemy's speed */
    float speedX = 0;
    float speedY = 0;
    
    /** enemy's acceleration (only in horizontal) */
    float accelX = 2;
    
    /** enemy's max horizontal speed */
    float maxSpeedX = 3;
    
    /** movement delay, stop moving while delay number not yet reach the target number*/
    float moveDelay = 0;
    
    /** offset of enemy's image*/
    int X_offset = 22;
    int Y_offset = 9;
    
    /** screen length*/
    static float minX = 14;
    static float maxX = platformer.sWidth-14;
    static float minY = 20;
    static float maxY = platformer.sHeight-20;
    
    /** enemy's health*/
    int HP = 1;
    boolean fall = false;
    
    float deathDelay = 0;
    
    /** check if the enemy can be damaged by the player from the head*/
    boolean canBeAttackedFromAbove = true;
    
    boolean canHurtPlayer = true;
    
    /** enemy images array*/
    private static GreenfootImage frames[] = new GreenfootImage[10];
    
    /** images variables*/
    int dir = 0;
    float current_frame = 0;
    float anim_speed = 0.0f;
    float start_frame = 0;
    float end_frame = 0;
    float anim_no = 0;
    float anim_delay = 0;
    
    
    /**
     * initialize enemy
     */
    public enemy() {
        /**set image sprites ready!!*/
        setSprites();
    }  
    
    /**
     * initialize with direction string
     */
    public enemy(String direction) {
        /**set image sprites ready!!*/
        setSprites();
        if (direction.equals("left")){
            dir = 5;
        } else if (direction.equals("right")){
            dir = 0;
        }
    }  
    
    /**
     * set enemy images
     */
    private void setSprites(){
        if(frames[0] == null){ //put this on so we dont reload images every time we make an enemy
            /**load right facing frames*/
            frames[0] = new GreenfootImage("blob_a.gif");
            frames[1] = new GreenfootImage("blob_b.gif");
            frames[2] = new GreenfootImage("blob_c.gif");
            frames[3] = new GreenfootImage("blob_d.gif");
            frames[4] = new GreenfootImage("blob_e.gif");
            
            /**load left facing frames*/
            frames[0+5] = new GreenfootImage("blob_a_dir.gif");
            frames[1+5] = new GreenfootImage("blob_b_dir.gif");
            frames[2+5] = new GreenfootImage("blob_c_dir.gif");
            frames[3+5] = new GreenfootImage("blob_d_dir.gif");
            frames[4+5] = new GreenfootImage("blob_e_dir.gif");
        }
    }
    
    /**
     * enemy has just been added to world
     */
    public void addedToWorld(World world){
        platformer = (PlatformWorld) world;
        x = getX();
        y = getY();
    }
    
    /**
     * animation
     */
    private void animate(){
        /**check animation delay*/
        if (anim_delay > 0){
            return; // cancel if delaying
        }
        
          current_frame += anim_speed;
         if (current_frame > end_frame && anim_no != 1){
             current_frame = start_frame; //frame loop
         }
         if (anim_no == 1 && current_frame >= end_frame){
             anim_speed = 0;
             current_frame = 2; //frame non-loop for no.1
         }

        this.setImage(frames[(int)current_frame + dir]);
    }
    
    private void setAnim_moveDelay(){
        if (anim_no == 0||anim_delay > 0){
            return; //cancel if using is animation, or animation delaying
        }
        anim_no = 0; //this is animation no.0
        
        anim_speed = 0; //how fast the image change
        current_frame = 0; //current image shot
        start_frame = 0; //first image of the animation
        end_frame = 0; //last image of the animation
    }
    
    private void setAnim_move(){
        if (anim_no == 1||anim_delay > 0){
            return; //cancel if using is animation, or animation delaying
        }
        anim_no = 1; //this is animation no.1
        
        anim_speed = 0.1f; //how fast the image change
        current_frame = 1; //current image shot
        start_frame = 1; //first image of the animation
        end_frame = 2; //last image of the animation
        
    }
    
    private void setAnim_death(){
        if (anim_no == 2||anim_delay > 0){
            return; //cancel if using is animation, or animation delaying
        }
        anim_no = 2; //this is animation no.2
        
        anim_speed = 0; //how fast the image change
        current_frame = 3; //current image shot
        start_frame = 3; //first image of the animation
        end_frame = 3; //last image of the animation
        
    }
    
    private void setAnim_deathFlow(){
        if (anim_no == 3||anim_delay > 0){
            return; //cancel if using is animation, or animation delaying
        }
        anim_no = 3; //this is animation no.3
        
        anim_speed = 0; //how fast the image change
        current_frame = 4; //current image shot
        start_frame = 4; //first image of the animation
        end_frame = 4; //last image of the animation
        
    }
    
    /**
     * Gears up the enemy!!
     */
    public void act(){
        /**move around in it's own way*/
        movement();
        
        /**auto detect masks*/
        maskTouch();
        
        /**animations*/
        animate();
        
        /**death*/
        if (HP <= 0 && !fall){         
            deathDelay++;
            canBeAttackedFromAbove = false;
            canHurtPlayer = false;
            if (deathDelay < 30){
                setAnim_death();
            }
            if (deathDelay >= 30){
                setAnim_deathFlow();
            }
            if (deathDelay >= 50){
                platformer.removeObject(this);
            }
        }
        /**disappear and die if fall of the screen*/
        if (HP <= 0 && fall){
            canBeAttackedFromAbove = false;
            canHurtPlayer = false;
            platformer.removeObject(this);
        }
    }
    
    /**
     * movements : when enemy moves (moves as a blob)
     */
    private void movement(){
        /**set movement*/
        setLocation((int)x, (int)y);
        
        /**movement changes by speed*/
        x += speedX;
        y += speedY;
        
        /**gravity*/
        if (speedY < 5){
            speedY += 0.3;
        }
        
        /**deceleration*/
        speedX *= 0.9;
        
        /**enemy's ai movement method*/
        ai_patrol();
        
        /**movement delay, the enemy is crawling*/
        moveDelay++;
        
        /**movement animation*/
        if (Math.abs(speedX) < 0.1 && HP > 0){
            current_frame = 1;
            setAnim_moveDelay();
        }
        
        /**fall then die*/
        if (getY() > platformer.getHeight()-Y_offset){
            HP = 0;
            fall = true;
        }
    }
    
    /**
     * movements : staying in the patrol zone (green mask), or in the bound
     */
    private void ai_patrol(){
        /**
         * ENEMY MOVING, ALSO AUTO-DETECT BLACK & GREEN MASK.
         */
        /**left*/
        if (dir == 5 && speedX > -maxSpeedX && HP > 0){
            if (moveDelay >= 85){
              speedX -= accelX;
              moveDelay = 0;
              current_frame = 1;
              setAnim_move();
           }
        }
        /**right*/
        if (dir == 0 && speedX < maxSpeedX && HP > 0){
            if (moveDelay >= 85){
              speedX += accelX;
              moveDelay = 0;
              current_frame = 1;
              setAnim_move();
           }
        }
        
        /**reverse direction once hit black mask, green mask, or the edge of the screen*/
        if (getX() > maxX || platformer.isSolid(getX()+X_offset, getY())
            || platformer.isPatrol(getX()+X_offset, getY())){
                dir = 5; //turn left
                speedX -= accelX/4;
            }
        if (getX() < minX || platformer.isSolid(getX()-X_offset, getY())
            || platformer.isPatrol(getX()-X_offset, getY())){
                dir = 0; //turn right
                speedX += accelX/4;
            }
            
        
    }
    
    /**
     * events when enemy detect masks
     */
    private void maskTouch(){
        /**
         * BLACK MASK
         */
        
        if (platformer.isSolid(getX(), getY()+(Y_offset+2) )){
            speedY = 0;
        } 
        
        if (platformer.isSolid(getX(), getY()+Y_offset)){  
            y -= 1; 
        } 
        
         if (platformer.isSolid(getX(), getY()-Y_offset) ){
            y += 9; 
            if (speedY < 0){
                speedY = 0;
            }
        }
        
        if (platformer.isSolid(getX()+X_offset, getY()) ){ 
           x -= 2;   
          if (speedX > 0) {
              speedX = 0;
            }
        } 
        
         if (platformer.isSolid(getX()-X_offset, getY()) ){ 
            x += 2;
          if (speedX < 0) {
              speedX = 0;
            }
        } 
        
        /**
         * RED MASK
         */
        if (platformer.isDeath(getX(), getY()+Y_offset)){  
            HP = 0;
        } 
        
         if (platformer.isDeath(getX(), getY()-Y_offset) ){
            HP = 0;
        }
        
        if (platformer.isDeath(getX()+X_offset, getY()) ){ 
            HP = 0;
        } 
        
         if (platformer.isDeath(getX()-X_offset, getY()) ){ 
            HP = 0;
        } 
        
        /**
         * YELLOW MASK
         */
        if (platformer.isHurt(getX(), getY()+Y_offset)){  
            damage(1);
        } 
        
         if (platformer.isHurt(getX(), getY()-Y_offset) ){
            damage(1);
        }
        
        if (platformer.isHurt(getX()+X_offset, getY()) ){ 
            damage(1);
        } 
        
         if (platformer.isHurt(getX()-X_offset, getY()) ){ 
            damage(1);
        } 
        
        /**
         * BLUE MASK
         */
        if (platformer.isUpPlatform(getX(), getY()+(Y_offset+2) )){
            speedY = 0;
        } 
        
        if (platformer.isUpPlatform(getX(), getY()+Y_offset)){  
            y -= 1; 
        } 
        
        /**
         * PURPLE MASK
         */
        if (platformer.isBounce(getX(), getY()+Y_offset)){  
            speedY = -7;
        } 
    }
    
    /**
     * do damage to enemy, decrease health
     */
    public void damage(int dmg){
        HP -= dmg;
        
    }
}
