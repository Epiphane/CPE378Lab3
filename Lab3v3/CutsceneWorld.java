import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CutsceneWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CutsceneWorld extends World
{
    public static final GreenfootSound backgroundMusic = new GreenfootSound("The Lake.wav");

    private DialogManager dialogmanager;
    private static player Player;
    
    /**
     * Constructor for objects of class CutsceneWorld.
     * 
     */
    public CutsceneWorld()
    {     
        this(new player(), 0);
    }
    
    /**
     * Constructor for objects of class CutsceneWorld.
     * 
     */
    public CutsceneWorld(player Player, int level)
    {     
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(PlatformWorld.sWidth, PlatformWorld.sHeight, 1); 
        prepare();
        
        this.Player = Player;
        
        setupDialog(level);
    }
    
    public void stopped() {
         backgroundMusic.pause(); 
    }
     
    public void started() {
        backgroundMusic.playLoop();
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        CutscenePlayer cutsceneplayer = new CutscenePlayer();
        addObject(cutsceneplayer,262,313);
        DialogBox dialogbox = new DialogBox(cutsceneplayer);
        addObject(dialogbox,617,472);
        cutsceneplayer.setLocation(124,479);
        cutsceneplayer.setLocation(527,182);
        dialogbox.setLocation(540,421);

        dialogmanager = new DialogManager(dialogbox, cutsceneplayer);
        addObject(dialogmanager,836,97);
    }
        
    private void setupDialog(int level) {
        if (level == 0)
            setupDialog_0();
        else if (level == 1)
            setupDialog_1();
        else {
            dialogmanager.addLine("Uh oh...");
            dialogmanager.addLine("Theres no dialog");
            dialogmanager.addLine("for this level!");
            dialogmanager.addLine("well........");
            dialogmanager.addLine("I guess that means you win!");
        }
        
        dialogmanager.nextWorld = new PlatformWorld(Player, level);
        dialogmanager.start();
    }
    
    private void setupDialog_0() {    
        dialogmanager.addLine("Hello there!");
        dialogmanager.addLine("My name is Wilbert!");
        dialogmanager.addLine("Welcome to my home!");
        dialogmanager.addLine("I have lots of friends");
        dialogmanager.addLine("here but it gets a little");
        dialogmanager.addLine("lonely sometimes.");
        dialogmanager.addLine("Hey!");
        dialogmanager.addLine("I have an idea!");
        dialogmanager.addLine("Lets go on a trip!");
        dialogmanager.addLine("See the rest of this");
        dialogmanager.addLine("beautiful world!");
        dialogmanager.addLine("Come on! Lets go!");
        dialogmanager.addLine("...");
        dialogmanager.addLine("what do you mean?", CutscenePlayer.Expression.Concerned);
        dialogmanager.addLine("be careful?", CutscenePlayer.Expression.Concerned);
        dialogmanager.addLine("...");
        dialogmanager.addLine("Oh theres nothing");
        dialogmanager.addLine("to worry about!");
        dialogmanager.addLine("Everyone here is friendly!");
        dialogmanager.addLine("Here goes!");
        
        backgroundMusic.playLoop();
        backgroundMusic.pause(); 
    }
    
    private void setupDialog_1() {    
        dialogmanager.addLine("Hedfssdfllo there!");
        dialogmanager.addLine("My name is Wilbert!");
        dialogmanager.addLine("Welcome to my home!");
        dialogmanager.addLine("I have lots of friends");
        dialogmanager.addLine("here but it gets a little");
        dialogmanager.addLine("lonely sometimes.");
        dialogmanager.addLine("Hey!");
        dialogmanager.addLine("I have an idea!");
        dialogmanager.addLine("Lets go on a trip!");
        dialogmanager.addLine("See the rest of this");
        dialogmanager.addLine("beautiful world!");
        dialogmanager.addLine("Come on! Lets go!");
        dialogmanager.addLine("...");
        dialogmanager.addLine("what do you mean?", CutscenePlayer.Expression.Concerned);
        dialogmanager.addLine("be careful?", CutscenePlayer.Expression.Concerned);
        dialogmanager.addLine("...");
        dialogmanager.addLine("Oh theres nothing");
        dialogmanager.addLine("to worry about!");
        dialogmanager.addLine("Everyone here is friendly!");
        dialogmanager.addLine("Here goes!");
    }
}
