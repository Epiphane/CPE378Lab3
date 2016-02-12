import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;


public class PlatformWorld extends World
{
    /** width and height for all actors to use up (static)*/
    static int sWidth = 1000;
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
    
    /** player movement deceleration */
    public double decel = 0.8;
    
    /** level no. */
    static int currentLevel = 0;
    
    /** start position of player given by the world */
    static public int startx, starty;
    
    /** position detecter variable of the player, used by other parties in the world*/
    static public int playerx, playery;
    
    /** player's remaining lifes*/
    static public int loseLifeDelay = 0;
    boolean gameIsOver = false;
    
    public Hearts hearts = new Hearts();
    public Lives lives = new Lives();
    public Fury fury = new Fury();
    
    player currentPlayer;
    
    /**
     * generates 
     */
    public PlatformWorld()
    {    
        this(new player(), 0);
    }
    
    public PlatformWorld(player Player, int level)
    {
        /**initialize*/
        super(sWidth, sHeight, 1); 
        setMaskCodes(); 
        
        currentPlayer = Player;
        addObject(hearts, 50, 60); //health
        addObject(lives, 40, 25); //lives
        addObject(fury, 600, 40);
        fury.set(currentPlayer.fury);
        if (currentPlayer.numInjuries() > 0)
            currentPlayer.firstInjury = false;
        
        /**Set order of appearance of actor classes.*/
        /**Set from front-most to back-most.*/
        /*||||***Make sure don't forget this while creating new kind of object!!!***|||||*/
        setPaintOrder(game_over.class, player_death.class, player.class, enemy1.class, Hearts.class);
        
        /**Start the game*/
        currentLevel = level;
        setLevel(currentLevel);
    }
    
    public void stopped() {
         CutsceneWorld.backgroundMusic.pause(); 
    }
     
    public void started() {
        CutsceneWorld.backgroundMusic.playLoop();
    }
        
    /**
     * what the world wants to do rapidly
     */
    public void act()
    {
        /**player's life needs to be updated here*/
        manageLife();
        hearts.adjustHearts(currentPlayer.HP);
        lives.adjustLives(currentPlayer.lives);
        
    }
    
    /**
     * take care of life system, if life runs out, calls game over
     */
    public void manageLife(){
        if (currentPlayer.dead && currentPlayer.lives >= 0){
            loseLifeDelay++;
            if (loseLifeDelay >= 50){
                resetLevel();
                loseLifeDelay = 0;
            }
        }
        if (currentPlayer.dead && currentPlayer.lives < 0 && !gameIsOver){
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
     */
    public void clearLevel(){
        this.removeObjects(this.getObjects(player.class));
        this.removeObjects(this.getObjects(AnimatedActor.class));
        this.removeObjects(this.getObjects(player_death.class));
        this.removeObjects(this.getObjects(game_over.class));
        this.removeObjects(this.getObjects(exit_right.class));
    }
    
    /**
     * set one of the specific levels
     */
    public void setLevel(int x){
        /**change current level from the number inside ()*/
        clearLevel();
        currentLevel = x;
        switch(x) {
            case 0: setLevel_1(); break;
            case 1: setLevel_2(); break;
            case 2: setLevel_3(); break;
            case 3: setLevel_4(); break;
            case 4: setLevel_5(); break;
            case 5: setLevel_6(); break;
            case 6: setLevel_7(); break;
            case 7: setLevel_ending(); break;
        }
    }
    
    /**
     * go to next level
     */
    public void nextLevel(){
        Greenfoot.setWorld(new CutsceneWorld(currentPlayer, currentLevel + 1)); 
    }
    
    /**
     * reset level
     */
    public void resetLevel(){
        currentPlayer.reset(startx, starty);
        setLevel(currentLevel);
    }
    
    /**
     * set the title level
     */
    public void setLevel_1(){
        
        /**set art*/
        background = new GreenfootImage("Level1_art.gif");
        setBackground(background);
        /**set platforms*/
        bgmask = new GreenfootImage("Level1.gif");
        /**set sliding */
        decel = 0.95;
        /**set player in a fixed spot*/
        startx = 40;
        starty = 150;
        addObject(currentPlayer, startx, starty);
        addObject(new exit_right(), 970, 480);
        addObject(new enemy1(), 737, 140); //top
        addObject(new enemy1(), 247, 270); //middle top
        addObject(new enemy2(), 730, 395); //middle bot
        addObject(new enemy1(), 508, 505); //bot
    }
    
    /**
     * set another level
     */
    public void setLevel_2(){
        
        /**set art*/
        background = new GreenfootImage("Level2_art.gif");
        setBackground(background);
        /**set platforms*/
        bgmask = new GreenfootImage("Level2.gif");
        
        /**set player in a fixed spot*/
        startx = 85;
        starty = 500;
        addObject(currentPlayer, startx, starty);
        addObject(new enemy1(), 583, 442);
        addObject(new enemy2(), 121, 163);
        addObject(new enemy2(), 184, 339);
        addObject(new enemy1(), 541, 242);
        addObject(new exit_right(), 970, 195);
    }
    
    /**
     * set another level
     */
    public void setLevel_3(){
        
        /**set art*/
        background = new GreenfootImage("Level3_art.gif");
        setBackground(background);
        /**set platforms*/
        bgmask = new GreenfootImage("Level3.gif");     
        
        /**set player in a fixed spot*/
        startx = 40;
        starty = 520;
        addObject(currentPlayer, startx, starty);
        addObject(new enemy1(), 882, 539); //bot right
        addObject(new enemy2(), 800, 399); //lower mid right
        addObject(new enemy1(), 231, 406); //lower mid left
        addObject(new enemy2(), 516, 265); //middle
        addObject(new enemy2(), 276, 141);
        addObject(new exit_right(), 900, 0);
    }
    
     /**
     * set another level
     */
    public void setLevel_4(){
        
        /**set art*/
        background = new GreenfootImage("Level4_art.gif");
        setBackground(background);
        /**set platforms*/
        bgmask = new GreenfootImage("Level4.gif");     
        
        /**set player in a fixed spot*/
        startx = 56;
        starty = 500;
        addObject(currentPlayer, startx, starty);
 
        addObject(new exit_right(), 926, 460);
    }
    
    public void setLevel_5(){
        
        /**set art*/
        background = new GreenfootImage("Level5_art.gif");
        setBackground(background);
        /**set platforms*/
        bgmask = new GreenfootImage("Level5.gif");     
        
        /**set player in a fixed spot*/
        startx = 56;
        starty = 500;
        addObject(currentPlayer, startx, starty);
        addObject(new enemy2(), 164, 199); //left
        addObject(new enemy1(), 543, 155); //mid
        addObject(new exit_right(), 970, 485);
    }
    
     /**
     * set another level
     */
    public void setLevel_6(){
        
        /**set art*/
        background = new GreenfootImage("Level6_art.gif");
        setBackground(background);
        /**set platforms*/
        bgmask = new GreenfootImage("Level6.gif");     
        
        /**set player in a fixed spot*/
        startx = 103;
        starty = 520;
        addObject(currentPlayer, startx, starty);
        addObject(new enemy2(), 81, 425); //leftbot
        addObject(new enemy2(), 99, 215); //lefttop
        addObject(new enemy1(), 420, 504); //mid
        addObject(new exit_right(), 950, 337);
    }
    
     /**
     * set another level
     */
    public void setLevel_7(){
        
        /**set art*/
        background = new GreenfootImage("Level7_art.gif");
        setBackground(background);
        /**set platforms*/
        bgmask = new GreenfootImage("Level7.gif");     
        
        /**set player in a fixed spot*/
        startx = 56;
        starty = 500;
        addObject(currentPlayer, startx, starty);
 
        addObject(new exit_right(), 926, 462);
    }
    
    /**
     * ending
     */
    public void setLevel_ending(){
        /**basic, set background*/
        removeObject(hearts);
        removeObject(lives);
        background = new GreenfootImage("ending.png");
        setBackground(background);
        /**also set mask*/
        bgmask = new GreenfootImage("ending_mask.gif");
        
    }
    
}
