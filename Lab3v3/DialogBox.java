import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DialogBox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DialogBox extends Actor
{
    private float speed = 0.5f;
    private float ndx = 0;
    private String text = "";
    
    public static final int LINE_WIDTH = 14;
    
    private Actor[] letters;
    private CutscenePlayer player;
    
    public static final int PADDING = 20;
    
    public static final GreenfootSound speech = new GreenfootSound("Player_talk.wav");
    
    DialogBox(CutscenePlayer player) {
        this.player = player;
        letters = new Actor[0];
        
        speech.setVolume(70);
    }
    
    private boolean complete = false;
    public boolean isComplete() { return complete; }
    
    public void say(String text) {
        World world = getWorld();
        for (int i = 0; i < letters.length; i ++) {
            if (letters[i] != null) {
                world.removeObject(letters[i]);
            }
        }
        
        ndx = -1;
        // Fix text so that it wraps nicely
        int lastSpace = -1;
        int originalLength = text.length();
        for (int i = 0; i < text.length() && i < originalLength; i ++) {
            if (text.charAt(i) == ' ') {
                lastSpace = i;
                
                if (i % LINE_WIDTH == 0) {
                    text = text.substring(0, i) + text.substring(i + 1);
                }
            }
            if (i % LINE_WIDTH == 0 && lastSpace > 0) {
                // Add spaces to text
                String spaces = "";
                for (int j = lastSpace; j % LINE_WIDTH < LINE_WIDTH - 1; j ++) {
                    spaces += " ";
                }
                if (spaces.length() != 13) {
                    text = text.substring(0, lastSpace) + spaces + text.substring(lastSpace);
                    lastSpace = -1; 
                }
            }
        }
        
        this.text = text;
        letters = new Actor[text.length()];
    }
    
    /**
     * Act - do whatever the DialogBox wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (ndx < text.length() - 1) {
            ndx += speed;
            complete = false;
            
            if (Math.floor(ndx) != Math.floor(ndx - speed)) {
                if (player != null &Math.floor(ndx) % 2 == 1 && ndx < 8)
                    player.animate();
                speech.play();
            
                Letter newLetter = new Letter();
                newLetter.setImage(Text.instance().getChar(text.charAt((int) ndx)));
                
                int pos_x = ((int) ndx) % LINE_WIDTH;
                int pos_y = ((int) ndx) / LINE_WIDTH;
                
                getWorld().addObject(newLetter, pos_x * Text.LETTER_W + getX() - getImage().getWidth() / 2 + PADDING + Text.LETTER_W / 2, 
                    pos_y * Text.LETTER_H + getY() - getImage().getHeight() / 2 + PADDING + Text.LETTER_H / 2);
                    
                letters[(int) ndx] = newLetter;
            
                if (ndx >= text.length() - 1) {
                    setImage(new GreenfootImage("dialogue_complete.png"));
                    complete = true;
                }
                else {
                    setImage(new GreenfootImage("dialogue.png"));
                }
            }
        }
    }
}
