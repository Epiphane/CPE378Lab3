import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DialogueBox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DialogueBox extends Actor
{
    private float speed = 0.5f;
    private float ndx = 0;
    private String text = "";
    
    public static final int PADDING = 20;
    
    public void say(String text) {
        ndx = -1;
        this.text = text;
    }
    
    /**
     * Act - do whatever the DialogueBox wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (ndx < text.length() - 1) {
            ndx += speed;
            
            Letter newLetter = new Letter();
            newLetter.setImage(Text.instance().getChar(text.charAt((int) ndx)));
            
            int pos_x = (int) ndx;
            int pos_y = 0;
            
            getWorld().addObject(newLetter, pos_x * Text.LETTER_W + getX() - getImage().getWidth() / 2 + PADDING + Text.LETTER_W / 2, 
                pos_y * Text.LETTER_H + getY() - getImage().getHeight() / 2 + PADDING + Text.LETTER_H / 2);
        
            if (ndx >= text.length() - 1) {
                setImage(new GreenfootImage("dialogue_complete.png"));
            }
        }
    }
}
