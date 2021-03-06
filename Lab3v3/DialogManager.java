import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class DialogManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DialogManager extends Actor
{
    private int ndx;
    private DialogBox dialogBox;
    private CutscenePlayer player;
    private ArrayList<String> lines;
    private ArrayList<CutscenePlayer.Expression> expressions;
    private ArrayList<Boolean> pause;
    
    private boolean shouldPause = true;
    
    public boolean skip = false;
    public World nextWorld;
    
    DialogManager(DialogBox box, CutscenePlayer player) {
        this.dialogBox = box;
        this.player = player;
        
        lines = new ArrayList<String>();
        expressions = new ArrayList<CutscenePlayer.Expression>();
        pause = new ArrayList<Boolean>();
    }
    
    public void addLine(String line) {
        addLine(line, CutscenePlayer.Expression.Happy);
    }
    
    public void addLine(String line, CutscenePlayer.Expression expr) {
        addLine(line, expr, true);
    }
    
    public void addLine(String line, CutscenePlayer.Expression expr, boolean pauseAtEnd) {
        lines.add(line);
        expressions.add(expr);
        pause.add(pauseAtEnd);
    }
    
    public void start() {
        if (lines.size() == 0) return;
        
        ndx = 0;
        if (player != null) player.setExpression(expressions.get(ndx));
        shouldPause = pause.get(ndx);
        dialogBox.say(lines.get(ndx++));
        dialogBox.pausing = shouldPause;
    }
    
    /**
     * Act - do whatever the DialogManager wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private boolean xPress = false;
    private boolean zPress = false;
    public void act() 
    {
        if (xPress && !Greenfoot.isKeyDown("x") && nextWorld != null) {
            Greenfoot.setWorld(nextWorld);
            MusicManager.play(); // Just in case
            return;
        }
        xPress = Greenfoot.isKeyDown("x");
        if ((zPress && !Greenfoot.isKeyDown("z")) || (dialogBox.isComplete() && !shouldPause)) {
            if (dialogBox.isComplete()) {
                if (ndx == lines.size() && nextWorld != null) {
                    Greenfoot.setWorld(nextWorld);
                    MusicManager.play(); // Just in case
                }
                else if (ndx < lines.size()) {
                    if (player != null) player.setExpression(expressions.get(ndx));
                    shouldPause = pause.get(ndx);
                    dialogBox.say(lines.get(ndx++));
                    dialogBox.pausing = shouldPause;
                }
            }
            else if (shouldPause) {
                dialogBox.complete();
            }
        }
        zPress = Greenfoot.isKeyDown("z");
    }    
}
