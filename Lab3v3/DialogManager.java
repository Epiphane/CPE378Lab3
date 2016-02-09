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
    private ArrayList<String> lines;
    
    public World nextWorld;
    
    DialogManager(DialogBox box) {
        this.dialogBox = box;
        
        lines = new ArrayList<String>();
    }
    
    public void addLine(String line) {
        lines.add(line);
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
        if (Greenfoot.isKeyDown("z")) {
            if (dialogBox.isComplete()) {
                if (ndx == lines.size()) {
                    Greenfoot.setWorld(nextWorld);
                }
                else {
                    dialogBox.say(lines.get(ndx++));
                }
            }
        }
    }    
}
