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
    
    public World nextWorld;
    
    DialogManager(DialogBox box, CutscenePlayer player) {
        this.dialogBox = box;
        this.player = player;
        
        lines = new ArrayList<String>();
        expressions = new ArrayList<CutscenePlayer.Expression>();
    }
    
    public void addLine(String line) {
        addLine(line, CutscenePlayer.Expression.Happy);
    }
    
    public void addLine(String line, CutscenePlayer.Expression expr) {
        lines.add(line);
        expressions.add(expr);
    }
    
    public void start() {
        ndx = 0;
        dialogBox.say(lines.get(ndx++));
    }
    
    /**
     * Act - do whatever the DialogManager wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (Greenfoot.isKeyDown("x")) {
            Greenfoot.setWorld(nextWorld);
            return;
        }
        if (Greenfoot.isKeyDown("z")) {
            if (dialogBox.isComplete()) {
                if (ndx == lines.size()) {
                    Greenfoot.setWorld(nextWorld);
                }
                else {
                    player.setExpression(expressions.get(ndx));
                    dialogBox.say(lines.get(ndx++));
                }
            }
        }
    }    
}
