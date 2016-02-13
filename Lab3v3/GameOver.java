import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameOver here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameOver extends World
{
    private player Player;

    /**
     * Constructor for objects of class GameOver.
     * 
     */
    public GameOver(player Player)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(PlatformWorld.sWidth, PlatformWorld.sHeight, 1); 
        this.Player = Player;
        prepare();
        
        Player.dead = false;
        Player.HP = Checkpoint.HP;
        Player.maxHP = Checkpoint.maxHP;
        Player.fury = Checkpoint.fury;
        Player.kills = Checkpoint.kills;
        Player.lives = 5;
        Player.setAngry(Checkpoint.angry);
        
        MusicManager.pause();
    }
    
    private DialogManager dialogmanager;

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        DialogBox dialogbox = new DialogBox(null);
        addObject(dialogbox,540,421);

        dialogmanager = new DialogManager(dialogbox, null);
        addObject(dialogmanager,836,97);
        
        dialogmanager.addLine("Wait!");
        dialogmanager.addLine("Its not over yet!");
        dialogmanager.addLine("For the low price of                  ", CutscenePlayer.Expression.Happy, false);
        dialogmanager.addLine("Just kidding                  ", CutscenePlayer.Expression.Happy, false);
        dialogmanager.addLine("Press Z and Wilbert will forget                                ", CutscenePlayer.Expression.Happy, false);
        dialogmanager.addLine("this ever happened!                           ");
    
        dialogmanager.nextWorld = new PlatformWorld(Player, Checkpoint.level);
        dialogmanager.start();
    }
}
