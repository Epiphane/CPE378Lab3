import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Text here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Text  
{
    private GreenfootImage[] characters;
    
    private static final int EXCLAMATION = 26;
    private static final int PERIOD = 27;
    private static final int QUESTION = 28;
    public static final int LETTER_W = 53;
    public static final int LETTER_H = 53;

    /**
     * Constructor for objects of class Text
     */
    public Text()
    {
        characters = new GreenfootImage[29];
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        
        for (int ch = 0; ch < 26; ch ++) {
            characters[ch] = new GreenfootImage("font/" + alphabet[ch] + "_inv.png");
        }
        
        // !
        characters[EXCLAMATION] = new GreenfootImage("font/exclamation_inv.png");
        characters[PERIOD] = new GreenfootImage("font/period_inv.png");
        characters[QUESTION] = new GreenfootImage("font/question_inv.png");
    }
    
    private static Text _instance = null;
    public static Text instance() {
        if (_instance == null) {
            _instance = new Text();
        }
        return _instance;
    }
    
    /**
     * Get the GreenfootImage corresponding to a specific character
     */
    public GreenfootImage getChar(char character) {
        if (character <= 'z' && character >= 'a') {
            character += 'A' - 'a';
        }
        else if (character == '!') {
            return characters[EXCLAMATION];
        }
        else if (character == '.') {
            return characters[PERIOD];
        }
        else if (character == '?') {
            return characters[QUESTION];
        }
        else if (character == ' ') {
            return null;
        }
        
        return characters[(int) character - 'A'];
    }
}
