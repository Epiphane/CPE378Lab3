import greenfoot.*; 

import java.awt.Color;

public class player extends AnimatedActor
{
    public static final boolean FURY_ENABLED = true;
    public static final int GENOCIDE_KILLS = 12;
    
    /** Input */
    public static final String JUMP_KEY = "z";
    public static final String ANGRY_KEY = "r";
    
    /**
     * Player's animation variables
     */
    private Animation player_walk_loop = AnimatedActor.generateSequence("player_walk", 6);
    private Animation player_walk_angry_loop = AnimatedActor.generateSequence("player_walk_angry", 6);
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
    private Animation player_idle_angry = new Animation("player_idle_angry", new int[] {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        1, 1, 1, 1, 1, 1, 1, 1, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        1, 1, 1, 1, 1, 1, 1, 1, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        1, 1, 1, 1, 1, 1, 1, 1, 
    }, 0.15f);
    private Animation player_jump_loop = new Animation("player_jump", new int[] { 3 }, 1);
    private Animation player_jump_angry_loop = new Animation("player_jump_angry", new int[] { 3 }, 1);
    private Animation player_hurt = AnimatedActor.generateSequence("player_hurt", 2);
    
    private Transition player_walk = AnimatedActor.generateTransition(player_walk_loop, "player_walk_transition", 1);
    private Transition player_jump = AnimatedActor.generateTransition(player_jump_loop, "player_jump", 3, 0.5f);
    private Transition player_jump_angry = AnimatedActor.generateTransition(player_jump_angry_loop, "player_jump_angry", 3, 0.5f);
    
    /**
     * Player sounds
     */
    public static final GreenfootSound hurt = new GreenfootSound("Hurt.wav");
    public static final GreenfootSound scream = new GreenfootSound("scream.wav");
    
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
    public boolean dead = false;
    boolean fall = false;
    
    /** health value, if it's 0, die*/
    int HP = 3;
    int maxHP = 3;
    int lives = 5;
    int fury = 0;
    boolean angry = false;
    int kills = 0;
    int newKills = 0;
    public boolean firstInjury = true;
    
    public int numInjuries() {
        return (maxHP - HP) + maxHP * (5 - lives);
    }
    
    /**
     * Player's Sprites Variables
     */
  
    public int angry_delay = 0;
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
        player_hurt.load();
        
        player_walk_angry_loop.load();
        player_idle_angry.load();
        player_jump_angry.load();
        player_jump_angry_loop.load();
        
        setAnimation(angry ? player_idle_angry : player_idle);
        
        return;
    }
    
    /**
     * Reset the player back to full health
     */
    public void reset(int x, int y)
    {
        HP = maxHP;
        this.x = x;
        this.y = y;
        fall = false;
        dead = false;
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
    private boolean zPress = false;
    public void act() 
    {
        // Run animations
        super.act();
        
        /**move coding*/
        moveControl();
        
        
        /**auto detect masks*/
        maskTouch();
        
        // Become mad if ya want
        if (zPress && !Greenfoot.isKeyDown(ANGRY_KEY) && angry_delay-- <= 0) {
            setAngry(!angry);
        }
        zPress = Greenfoot.isKeyDown(ANGRY_KEY);

        
        /**disallow HP go over maxHP*/
        if (HP > maxHP){
            HP = maxHP;
        }
        
        /**enable taking damage*/
        getHurt();
        
        /**death case*/
        if (HP <= 0 && !fall){
            dead = true;
            lives --;
            platformer.addObject(new player_death(), getX(), getY());
            platformer.removeObject(this);
        }
        /**disappear and die if fall of the screen*/
        if (HP <= 0 && fall){
            dead = true;
            lives --;
            platformer.removeObject(this);
        }
    }    
    
    public void setAnimation(Animation anim) {
        if (hurt_delay > 0 && anim != player_hurt) return;
        
        super.setAnimation(anim);
    }
    
    public void setAngry(boolean angry) {
        if (fury == 0) angry = false;
        if (fury > GENOCIDE_KILLS) angry = true;
        
        if (!FURY_ENABLED)
            angry = false; // Avoid fury mechanic completely
        
        this.angry = angry;
        
        if (angry) {
            if (current_animation == player_walk) current_animation = player_walk_angry_loop;
            if (current_animation == player_walk_loop) current_animation = player_walk_angry_loop;
            if (current_animation == player_jump) current_animation = player_jump_angry;
            if (current_animation == player_jump_loop) current_animation = player_jump_angry_loop;
            if (current_animation == player_idle) current_animation = player_idle_angry;
        }
        else {
            if (current_animation == player_walk_angry_loop) current_animation = player_walk_loop;
            if (current_animation == player_jump_angry) current_animation = player_jump;
            if (current_animation == player_jump_angry_loop) current_animation = player_jump_loop;
            if (current_animation == player_idle_angry) current_animation = player_idle;
        }
    }
    
    public void getMad() {
        setFury(fury + 1);
    }
    
    public void setFury(int fury) {
        this.fury = fury;
        if (platformer != null) {
            platformer.fury.set(fury);
        }
        
        setAngry(this.angry);
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
                    setAnimation(angry ? player_walk_angry_loop : player_walk);
            }
        }
        /**right key trigger*/
        else if(Greenfoot.isKeyDown("right")) {
             setFacingRight(true);
            if (speedX < maxSpeed && !Greenfoot.isKeyDown("down")){
                speedX += 2;
                
                if (canJump)
                    setAnimation(angry ? player_walk_angry_loop : player_walk);
            }
        }
         /**set the stand still animation while stopping x axis movement*/
        else if(speedY == 0) {
            setAnimation(angry ? player_idle_angry : player_idle);
        }
                
        /** nullifies speed horizontally */
        speedX *= platformer.decel;
        
        /** speed changer*/
        x += speedX;
        y += speedY;
        
        /**jump up key trigger*/
        if (Greenfoot.isKeyDown(JUMP_KEY) && canJump == true){
            /**speedY goes negative (up) and disable jump to prevent multi-jump*/
            speedY = -9;
            canJump = false;
            //set jumping animation
            setAnimation(angry ? player_jump_angry : player_jump);
        }
        
        /**duck key trigger*/
        if (Greenfoot.isKeyDown("down") && canJump == true){
            //setAnim_land();
            if (Greenfoot.isKeyDown("left")){setFacingRight(false);} //face left
            if (Greenfoot.isKeyDown("right")){setFacingRight(true);} //face right
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
            
            if (!Greenfoot.isKeyDown(JUMP_KEY)){
                
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
            setAnimation(angry ? player_jump_angry : player_jump);
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
            hurt_delay = 60; //reset hurt delay
            if (angry) {
                getWorld().removeObject(Enemy);
                
                kills ++;
                newKills ++;
                getMad();
                hurt.play();
            }
            else {
                if (getX() < Enemy.getX()) {speedX = -20; setFacingRight(true);}
                if (getX() > Enemy.getX()) {speedX = 20; setFacingRight(false);}
                if (getX() == Enemy.getX()) {speedX = 0;} //bounce reactions from the enemy
                speedY = 0; //stop falling for a while
                //setAnim_hurt(); //set hurt pose
                HP--; //do damage to the player
                speedY = -5;
                y -= 4;
                if (fury < 2) getMad();
                setAnimation(player_hurt);
                
                hurt.play();
            }
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
            speedY = -20;
            //setAnim_hurt();
            HP -= dmg;
            hurt_delay = 60;
            setAnimation(player_hurt);
            
            hurt.play();
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
