import greenfoot.*;

/**
 * Write a description of class MusicManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MusicManager  
{
    public static final GreenfootSound theLake = new GreenfootSound("The Lake.wav");
    public static final GreenfootSound caveTheme = new GreenfootSound("Cave theme.mp3");
    public static final GreenfootSound fieldTheme = new GreenfootSound("Field theme.mp3");
    public static final GreenfootSound forestTheme = new GreenfootSound("Forest theme.mp3");
    
    private static GreenfootSound currentMusic = theLake;
    
    /**
     * Constructor for objects of class MusicManager
     */
    public MusicManager() {
        currentMusic = theLake;
    }
    
    public static void setMusic(GreenfootSound snd) {
        if (currentMusic != null) currentMusic.pause();
        currentMusic = snd;
        if (snd != null) snd.playLoop();
    }
    
    public static void play() {
        if (currentMusic != null)
            currentMusic.playLoop();
    }
    
    public static void pause() {
        if (currentMusic != null)
            currentMusic.pause();
    }
}
