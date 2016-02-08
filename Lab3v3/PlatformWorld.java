import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;

/**
 * a platform game in my style
 * 
 * @author softwhizjx
 * @version 1.0
 * 
 * Thanks to the BioChicken Project for this to exist!!!
 */
public class PlatformWorld extends World
{
    /** width and height for all actors to use up (static)*/
    static int sWidth = 1024;
    static int sHeight = 600;
    
    /** background and masking colors for each levels */
    static GreenfootImage background;
    static GreenfootImage bgmask;
   
    /** mask coding image*/
    static GreenfootImage codes = new GreenfootImage("codes.gif");
    
    /** mask colors*/
    static Color solidColor;
    static Color deathColor;
    static Color aiColor;
    static Color hurtColor;
    static Color upColor;
    static Color bounceColor;
    
    /** level no. */
    static int currentLevel = 0;
    
    /** start position of player given by the world */
    static public int startx, starty;
    
    /** position detecter variable of the player, used by other parties in the world*/
    static public int playerx, playery;
    
    /** player's remaining lifes*/
    static public int life = 4;
    static public int loseLifeDelay = 0;
    boolean gameIsOver = false;
    
    /**
     * generates 
     */
    public PlatformWorld()
    {    
        /**initialize*/
        super(sWidth, sHeight, 1); 
        setMaskCodes(); 
        
        /**Set order of appearance of actor classes.*/
        /**Set from front-most to back-most.*/
        /*||||***Make sure don't forget this while creating new kind of object!!!***|||||*/
        setPaintOrder(game_over.class, player_death.class, player.class, enemy.class);
        
        /**Start the game*/
        restartGame();
    }
        
    /**
     * what the world wants to do rapidly
     */
    public void act()
    {
        /**player's life needs to be updated here*/
        manageLife();
    }
    
    
    /**
     * show the remaining lifes of the player
     */
    public int getLife(){
        return life;
    }
    
    /**
     * 1-UP
     */
    public void extraLife(){
        life++;
    }
    
    /**
     * 1-DOWN
     */
    public void loseLife(){
        life--;
    }
    
    /**
     * take care of life system, if life runs out, calls game over
     */
    public void manageLife(){
        if (player.dead && life >= 0){
            loseLifeDelay++;
            if (loseLifeDelay >= 50){
                resetLevel();
                loseLifeDelay = 0;
            }
        }
        if (player.dead && life < 0 && !gameIsOver){
            addObject(new game_over(), sWidth/2, sHeight/2);
            gameIsOver = true;
        }
    }
    
    /**
     * set mask colors
     */
    public void setMaskCodes(){
        solidColor = codes.getColorAt(1,1); //black -- Solid
        deathColor = codes.getColorAt(22,1); //red -- Instant Death
        aiColor = codes.getColorAt(40,1); //green -- AI Objects Patrol Limit Zone
        hurtColor = codes.getColorAt(57,1); //yellow -- Minus HP
        upColor = codes.getColorAt(68,1); //blue -- Elevating Platform
        bounceColor = codes.getColorAt(88,1); //purple -- Bouncing Platform
    }

    /**
     * detect the black solid mask color in the given (x, y) spot in the world.
     */
    public boolean isSolid(int x, int y){
        if (x > sWidth-1 || x < 0 || y > sHeight-1 || y < 0) {
            /**if it's outside of the screen, it's not solid*/
            return false;
        }
        
        /**mask color detecter, "maskGet" will detect the color from the mask "bgmask"
           in the given (x, y) spot in the world*/
        Color maskGet = bgmask.getColorAt(x,y);
        if (solidColor.equals(maskGet)){
            /**if "maskGet" dectects black color, then it's solid, it will return this method as true*/
            return true;
        }
        
        /**otherwise not solid*/
        return false;
    }
    
    /**
     * detect the red instant death zone mask color in the given (x, y) spot in the world.
     */
    public boolean isDeath(int x, int y){
        if (x > sWidth-1 || x < 0 || y > sHeight-1 || y < 0) {
            /**if it's outside of the screen, it's not instant death zone*/
            return false;
        }
        
        /**mask color detecter, "maskGet" will detect the color from the mask "bgmask"
           in the given (x, y) spot in the world*/
        Color maskGet = bgmask.getColorAt(x,y);
        if (deathColor.equals(maskGet)){
            /**if "maskGet" dectects black color, then it's instant death zone*/
            return true;
        }
        //it's not just falling is instant death!!
        /**otherwise not an instant death zone*/
        return false;
    }
    
     /**
     * detect the green patrol limit zone mask color in the given (x, y) spot in the world.
     */
    public boolean isPatrol(int x, int y){
        if (x > sWidth-1 || x < 0 || y > sHeight-1 || y < 0) {
            /**if it's outside of the screen, it's not patrol limit zone*/
            return false;
        }
        
        /**mask color detecter, "maskGet" will detect the color from the mask "bgmask"
           in the given (x, y) spot in the world*/
        Color maskGet = bgmask.getColorAt(x,y);
        if (aiColor.equals(maskGet)){
            /**if "maskGet" dectects green color, then it's the patrol limit zone*/
            return true;
        }
        
        /**otherwise not a patrol limit zone*/
        return false;
    }
    
    /**
     * detect the yellow hurt zone mask color in the given (x, y) spot in the world.
     */
    public boolean isHurt(int x, int y){
        if (x > sWidth-1 || x < 0 || y > sHeight-1 || y < 0) {
            /**if it's outside of the screen, it's not hurt zone*/
            return false;
        }
        
        /**mask color detecter, "maskGet" will detect the color from the mask "bgmask"
           in the given (x, y) spot in the world*/
        Color maskGet = bgmask.getColorAt(x,y);
        if (hurtColor.equals(maskGet)){
            /**if "maskGet" dectects black color, then it's hurt zone*/
            return true;
        }
        //useful for some spikes in the background.
        /**otherwise not a hurt zone*/
        return false;
    }
    
    /**
     * detect the blue up platform mask color in the given (x, y) spot in the world.
     */
    public boolean isUpPlatform(int x, int y){
        if (x > sWidth-1 || x < 0 || y > sHeight-1 || y < 0) {
            /**if it's outside of the screen, it's not up platform*/
            return false;
        }
        
        /**mask color detecter, "maskGet" will detect the color from the mask "bgmask"
           in the given (x, y) spot in the world*/
        Color maskGet = bgmask.getColorAt(x,y);
        if (upColor.equals(maskGet)){
            /**if "maskGet" dectects green color, then it's the up platform*/
            return true;
        }
        
        /**otherwise not an up platform*/
        return false;
    }
    
    /**
     * detect the purple bounce platform mask color in the given (x, y) spot in the world.
     */
    public boolean isBounce(int x, int y){
        if (x > sWidth-1 || x < 0 || y > sHeight-1 || y < 0) {
            /**if it's outside of the screen, it's not bounce platform*/
            return false;
        }
        
        /**mask color detecter, "maskGet" will detect the color from the mask "bgmask"
           in the given (x, y) spot in the world*/
        Color maskGet = bgmask.getColorAt(x,y);
        if (bounceColor.equals(maskGet)){
            /**if "maskGet" dectects green color, then it's the bounce platform*/
            return true;
        }
        
        /**otherwise not a bounce platform*/
        return false;
    }
    
    /**
     * the players coordinates are sent to world
     * so enemies etc. can read it from here
     */
    public void setPlayerCoords(int px, int py)
    {
        playerx = px;
        playery = py;
     
    }
    
    /**
     * ending events (still not used)
     */
    private void lastlevel()
    {
        
    }
    
    /**
     * clean up the levels
     * ||||***Make sure don't forget this while creating new kind of object!!!***|||||
     */
    public void clearLevel(){
        this.removeObjects(this.getObjects(player.class));
        this.removeObjects(this.getObjects(enemy.class));
        this.removeObjects(this.getObjects(player_death.class));
        this.removeObjects(this.getObjects(game_over.class));
        this.removeObjects(this.getObjects(exit_right.class));
    }
    
    /**
     * start or restart the game
     */
    public void restartGame(){
        /** back to the start*/
        currentLevel = 0;
        life = 4;
        gameIsOver = false;
        loseLifeDelay = 0;
        setLevel(currentLevel);
    }
    
    /**
     * set one of the specific levels
     */
    public void setLevel(int x){
        /**change current level from the number inside ()*/
        clearLevel();
        currentLevel = x;
        switch(x) {
            case 0: setLevel_title(); break;
            case 1: setLevel_one(); break;
            case 2: setLevel_two(); break;
            case 3: setLevel_ending(); break;
        }
    }
    
    /**
     * go to next level
     */
    public void nextLevel(){
        currentLevel++;
        setLevel(currentLevel);
    }
    
    /**
     * reset level
     */
    public void resetLevel(){
        setLevel(currentLevel);
        player.dead = false;
    }
    
    /**
     * set the title level
     */
    public void setLevel_title(){
        /**basic, set background*/
        background = new GreenfootImage("BGtest2.gif");
        setBackground(background);
        /**also set mask*/
        bgmask = new GreenfootImage("BGtestmask2.gif");
       
        /**set player in a fixed spot*/
        startx = 40;
        starty = 150;
        addObject(new player(), startx, starty);
        addObject(new exit_right(), 970, 480);
        addObject(new enemy(), 737, 170);
        addObject(new enemy(), 247, 300);
    }
    
    /**
     * set another level
     */
    public void setLevel_one(){
        /**basic, set background*/
        background = new GreenfootImage("BGtest3.gif");
        setBackground(background);
        /**also set mask*/
        bgmask = new GreenfootImage("BGtestmask3.gif");
        
        /**set player in a fixed spot*/
        startx = 85;
        starty = 500;
        addObject(new player(), startx, starty);
        addObject(new enemy(), 583, 442);
        addObject(new enemy(), 121, 163);
        addObject(new enemy(), 184, 339);
        addObject(new enemy(), 541, 242);
        addObject(new exit_right(), 970, 195);
    }
    
    /**
     * set another level
     */
    public void setLevel_two(){
        /**basic, set background*/
        background = new GreenfootImage("BGtest4.gif");
        setBackground(background);
        /**also set mask*/
        bgmask = new GreenfootImage("BGtestmask4.gif");
        
        /**set player in a fixed spot*/
        startx = 34;
        starty = 108;
        addObject(new player(), startx, starty);
        addObject(new enemy(), 179, 76);
        addObject(new enemy(), 373, 98);
        addObject(new enemy("left"), 97, 272);
        addObject(new enemy(), 246, 276);
        addObject(new enemy("left"), 159, 401);
        addObject(new exit_right(), 609, 413);
    }
    
    /**
     * ending
     */
    public void setLevel_ending(){
        /**basic, set background*/
        background = new GreenfootImage("ending.gif");
        setBackground(background);
        /**also set mask*/
        bgmask = new GreenfootImage("ending_mask.gif");
        
    }
    
}
