import greenfoot.*; 

import java.awt.Color;

public class player extends AnimatedActor
{
    /**
     * Player's animation variables
     */
    private Animation player_walk_loop = AnimatedActor.generateSequence("player_walk", 6);
    private Animation player_idle = new Animation("player_idle", new int[] {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        1, 1, 1, 1, 1, 1, 1, 1, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        1, 1, 1, 1, 1, 1, 1, 1, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        1, 1, 1, 1, 1, 1, 1, 1, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        2, 3, 4, 5, 6, 6, 6, 6,
        6, 6, 6, 7, 8, 8, 8, 8, 
        8, 8, 8, 9, 10, 11, 12, 13
    }, 0.15f);
    private Animation player_jump_loop = new Animation("player_jump", new int[] { 3 }, 1);
    
    private Transition player_walk = AnimatedActor.generateTransition(player_walk_loop, "player_walk_transition", 1);
    private Transition player_jump = AnimatedActor.generateTransition(player_jump_loop, "player_jump", 3, 0.5f);
    
    /**
     * Player sounds
     */
    public static final GreenfootSound hurt = new GreenfootSound("Hurt.wav");
    
    /**
     * Player's Movements Variables
     */
    
    /** main world's variable */
    private static PlatformWorld platformer;
    
    /**player's positioning*/
    float x = 160;
    float y = 100;
    /**set to 160 or 100 is for in the screen at first anywhere, when setLevel() runs
       these numbers are not much of an effect*/
    
    /**player's movement speed*/
    float speedX = 0;
    float speedY = 0;
    
    /** MAX speed horizonally */
    float maxSpeed = 6;  
    
    /** check can jump or not */
    boolean canJump = false;
    
    /** Offsets of the main sprite images
     * (uses for prevent the player looks like passing the floor or wall while touching the mask)
     * [offset = distance how far from the very center of the image]
     */
    int X_offset = 20;
    int Y_offset = 20;
    
    /** screen length*/
    static float minX = 14;
    static float maxX = platformer.sWidth-14;
    static float minY = 20;
    static float maxY = platformer.sHeight-20;
    
    /**
     * Player's Life Variables
     */
    
    /** check still alive or not */
    static boolean dead = false;
    boolean fall = false;
    
    /** health value, if it's 0, die*/
    int HP = 3;
    int maxHP = 3;
    
    /**
     * Player's Sprites Variables
     */
  
    
    float current_frame = 0; //current image value (from the number of arrays "[i]")
    float anim_speed = 0.1f; //how fast the image change
    float start_frame = 0; //first image of the animation
    float end_frame = 0; //last image of the animation
    int anim_no = 0; //the number of each animations
    int dir = 0; //value for mirrored sprites (half of the total images)
    int hurt_delay = 0;
    
    /**
     *initialize sprites
     */
    public player(){
        player_walk.load();
        player_walk_loop.load();
        player_idle.load();
        player_jump.load();
        player_jump_loop.load();
        
        setAnimation(player_idle);
        
        return;
    }
    
    /**
     * set coordinate from the world (if not using this, the player disappear.)
     */
    public void addedToWorld(World world) {
        platformer = (PlatformWorld) world;
        x = getX();
        y = getY();
    }
    
  
    /**
     * Gears up the player!
     */
    public void act() 
    {
        // Run animations
        super.act();
        
        /**move coding*/
        moveControl();
        
        
        /**auto detect masks*/
        maskTouch();
        
        
        /**disallow HP go over maxHP*/
        if (HP > maxHP){
            HP = maxHP;
        }
        
        /**enable taking damage*/
        getHurt();
        
        /**death case*/
        if (HP <= 0 && !fall){
            dead = true;
            platformer.loseLife();
            platformer.addObject(new player_death(), getX(), getY());
            platformer.removeObject(this);
        }
        /**disappear and die if fall of the screen*/
        if (HP <= 0 && fall){
            dead = true;
            platformer.loseLife();
            platformer.removeObject(this);
        }
    }    
    
    /** 
     * Control to walk around the world.
     */
    private void moveControl(){
        /**left key trigger*/
        if(Greenfoot.isKeyDown("left")) {
            setFacingRight(false);
            if (speedX > -maxSpeed && !Greenfoot.isKeyDown("down")){
                speedX -= 2;
                
                if (canJump)
                    setAnimation(player_walk);
            }
        }
        /**right key trigger*/
        else if(Greenfoot.isKeyDown("right")) {
             setFacingRight(true);
            if (speedX < maxSpeed && !Greenfoot.isKeyDown("down")){
                speedX += 2;
                
                if (canJump)
                    setAnimation(player_walk);
            }
        }
         /**set the stand still animation while stopping x axis movement*/
        else if(speedY == 0) {
            setAnimation(player_idle);
        }
                
        /** nullifies speed horizontally */
        speedX *= 0.8;
        
        /** speed changer*/
        x += speedX;
        y += speedY;
        
        /**jump up key trigger*/
        if (Greenfoot.isKeyDown("space") && canJump == true){
            /**speedY goes negative (up) and disable jump to prevent multi-jump*/
            speedY = -9;
            canJump = false;
            //set jumping animation
            setAnimation(player_jump);
        }
        
        /**duck key trigger*/
        if (Greenfoot.isKeyDown("down") && canJump == true){
            //setAnim_land();
            if (Greenfoot.isKeyDown("left")){dir = 12;} //face left
            if (Greenfoot.isKeyDown("right")){dir = 0;} //face right
        }
        
        /**set when falling, also disable jump in case of falling without jumping*/
        if (speedY > 2 ) {
            /*in some games, it still can jump,
             * but in this case, the animation will glitched if changed*/
            canJump = false;
            //set falling animation
            //setAnim_fall();
        }
        
        /** jumping speed, includes gravity*/
        if (speedY < 10) {
            speedY += 0.3;//gravity
        } 
        
         /**prevent going further than the top of the screen*/
        if (y < minY) {
            y = minY;
            speedY = 0;
        }
        
        /**prevent going further than the screen in X axis*/
        if (x > maxX) {
             x = maxX; 
        }
        if (x < minX) {
            x = minX;
        }
        
        /**fall into the bottom of the screen to instantly die*/
        if (y > maxY){
            HP = 0;
            fall = true;
        }
        
        
        /**send the info of player's current position to the world*/
        platformer.setPlayerCoords(getX(),getY()); 
        
        /**core of the movement function*/
        setLocation((int)x,(int)y-(int)speedY); 
       
    }
    
    /**
     * events when player detect masks
     */
    private void maskTouch(){
        /**
         * BLACK MASK
         */
        
        /**landing strike to the ground in a positive Y-offset diatance plus a few more*/
      
        if (platformer.isSolid(getX(), getY()+(Y_offset+2) )){
            /**nullifies falling speed, (landing) allow player to jump again*/
            canJump = true;
            
            if (!Greenfoot.isKeyDown("space")){
                
              speedY = 0;
           }
        }
      
        /**when stomping into the black ground mask in a positive Y-offset diatance,
         * prevent going further downwards*/
        while (platformer.isSolid(getX(), (int)y+Y_offset)){  
            /**y coorninate go upwards to prevent going downwards*/
            y -= 1; 
            if (speedY > 0){
               //setAnim_land(); //this can have a sprite problem on slopes
            }
        } 
        
        /**
         * IMPORTANT NOTE!!!!
         * the "if" condition at the top should always has Y-offset's value
         * more than the bottom one.
         * Or else the player will shake on the floor.
         * 
         * Find the Y-offset value that fit's the player sprite's height the most!!
         * 
         */
        
        /**when head butted into the black ceiling in a negative Y-offset diatance,
         * prevent going further upwards*/
         if (platformer.isSolid(getX(), getY()-Y_offset) ){
            /**y coorninate go downwards to prevent going upwards*/
            y += 9; 
            if (speedY < 0){
                /**if speed is currently going up, nullifies this speed*/
                speedY = 0;
            }
        }
        
        /**when running into the black right wall mask in a positive X-offset diatance,
         * prevent going further rightwards*/
        if (platformer.isSolid(getX()+X_offset, getY()) ){ 
            /**x coorninate go leftwards to prevent going rightwards*/
           x -= 2;
          
          if (speedX > 0) {
              /**if speed is currently going right, nullifies this speed*/
              speedX = 0;
            }
        } 
        
        /**when running into the black left wall mask for a negative X-offset diatance,
         * prevent going further leftwards*/
         if (platformer.isSolid(getX()-X_offset, getY()) ){ 
            /**x coorninate go rightwards to prevent going leftwards*/
            x += 2;

          if (speedX < 0) {
              /**if speed is currently going left, nullifies this speed*/
              speedX = 0;
            }
        } 
        
        /**
         * RED MASK
         */
        
        if (platformer.isDeath(getX(), getY()+Y_offset)){  
            killPlayer();
        } 
        
         if (platformer.isDeath(getX(), getY()-Y_offset) ){
            killPlayer();
        }
        
        if (platformer.isDeath(getX()+X_offset, getY()) ){ 
            killPlayer();
        } 
        
         if (platformer.isDeath(getX()-X_offset, getY()) ){ 
            killPlayer();
        } 
        
        /**
         * YELLOW MASK
         */
        
        if (platformer.isHurt(getX(), getY()+Y_offset)){  
            hurtPlayer();
        } 
        
        if (platformer.isHurt(getX(), getY()-Y_offset) ){
            hurtPlayer();
        }
        
        if (platformer.isHurt(getX()+X_offset, getY()) ){ 
            hurtPlayer();
        } 
        
        if (platformer.isHurt(getX()-X_offset, getY()) ){ 
            hurtPlayer();
        } 
        
        /**
         * BLUE MASK
         */
        
        /**same as the BLACK, but only for landing*/
      
        if (platformer.isUpPlatform(getX(), getY()+(Y_offset+2)) && speedY > -1){
            /**nullifies falling speed, (landing) allow player to jump again*/
            canJump = true;
            
            if (!Greenfoot.isKeyDown("up")){
                
              speedY = 0;
           }
        }
      
        if (platformer.isUpPlatform(getX(), getY()+Y_offset) && speedY > -1){  
            /**y coorninate go upwards to prevent going downwards*/
            y -= 1;
            if (speedY > 0){
                 //setAnim_land();
            }
        } 
        
        /**
         * PURPLE MASK
         */
        if (platformer.isBounce(getX(), getY()+Y_offset)){  
            speedY = -12; //Bounce up high
            canJump = false;
            setAnimation(player_jump);
        } 
        
    }
    
    /**
     * Get hurt.
     */
    private void getHurt(){
        hurt_delay--;
        
        /**set enemy variable everytime the player meets the enemy*/
        AnimatedActor Enemy = (AnimatedActor) getOneIntersectingObject(AnimatedActor.class);
        
        //Conditions Requirements
        if (Enemy != null  //The enemy needs to meet the player on contact.
        && hurt_delay <= 0 //The delay of getting hurt should be over
        && getY()+34 >= Enemy.getY()
        ){
            if (getX() < Enemy.getX()) {speedX = -5; dir = 0;}
            if (getX() > Enemy.getX()) {speedX = 5; dir = 12;}
            if (getX() == Enemy.getX()) {speedX = 0;} //bounce reactions from the enemy
            speedY = 0; //stop falling for a while
            //setAnim_hurt(); //set hurt pose
            HP--; //do damage to the player
            hurt_delay = 60; //reset hurt delay
            
            hurt.play();
        }
    }
    
    /**
     * Get hurt, for this one, even the developer can hurt the player
     */
    public void hurtPlayer(){
        damagePlayer(1);
    }
    
    /**
     * Now hurt in any value.
     */
    public void damagePlayer(int dmg){
        if (hurt_delay <= 0){
            speedY = -5;
            //setAnim_hurt();
            HP -= dmg;
            hurt_delay = 60;
        }
    }
    
    public void killPlayer(){
        HP = 0;
    }

    public void healPlayer(int heal){
        if (HP >= maxHP){
            HP = maxHP;
            return;
        }
        HP += heal;
    }

    public void regenPlayer(){
        if (HP >= maxHP){
            HP = maxHP;
            return;
        }
        HP++;
    }
    
}
