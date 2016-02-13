import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CutsceneWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CutsceneWorld extends World
{
    private DialogManager dialogmanager;
    private static player Player;
    
    public World skip = null;
    
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
         MusicManager.pause();
    }
     
    public void started() {
         MusicManager.play();
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
        if (Player.firstInjury && Player.numInjuries() > 0) {
            dialogmanager.addLine("Ow! Who was that strange man");
            dialogmanager.addLine("Who attacked me?");
            dialogmanager.addLine("...");
            dialogmanager.addLine("You dont think theyre all");
            dialogmanager.addLine("like that do you?");
        }
        
        // Prepare next level
        dialogmanager.nextWorld = new PlatformWorld(Player, level);
        
        if (level == 0)
            setupDialog_intro();
        else if (level == 1)
            setupDialog_field_1();
        else if (level == 2)
            setupDialog_field_2();
        else if (level == 5)
            setupDialog_forest_1(); // Right after the first transition
        else if (level == 8)
            setupDialog_cave_1(); // Right after the second transition
        else if (level == 12)
            setupDialog_mountain_1(); // Right after the tird transition
        else {
            skip = dialogmanager.nextWorld;
            Greenfoot.setWorld(dialogmanager.nextWorld);
        }
        
        dialogmanager.start();
    }
    
    private void setupDialog_intro() {    
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
        dialogmanager.addLine("...", CutscenePlayer.Expression.Concerned);
        dialogmanager.addLine("what do you mean?", CutscenePlayer.Expression.Concerned);
        dialogmanager.addLine("be careful?", CutscenePlayer.Expression.Concerned);
        dialogmanager.addLine("...");
        dialogmanager.addLine("Oh theres nothing");
        dialogmanager.addLine("to worry about!");
        dialogmanager.addLine("Everyone here is friendly!");
        dialogmanager.addLine("Here goes!");
        
        MusicManager.setMusic(MusicManager.fieldTheme); 
    }
    
    private void setupDialog_field_1() {
        Player.fury = Math.max(1, Player.fury);
        
        // Pacifist
        if (Player.kills == 0) {
            if (Player.numInjuries() == 0) {
                dialogmanager.addLine("Wow that was fun!");
                dialogmanager.addLine("Did you see all the");
                dialogmanager.addLine("friendly people waving at us?");
                dialogmanager.addLine("Lets keep going!");
                dialogmanager.addLine("...", CutscenePlayer.Expression.Concerned);
                dialogmanager.addLine("Why are you so worried?", CutscenePlayer.Expression.Concerned);
                dialogmanager.addLine("Have you been here before?", CutscenePlayer.Expression.Concerned);
                if (player.FURY_ENABLED) {
                    dialogmanager.addLine("why didnt you              ", CutscenePlayer.Expression.Irked, false);
                    dialogmanager.addLine("oops             ", CutscenePlayer.Expression.Concerned, false);
                }
                dialogmanager.addLine("you should have said so!");
                dialogmanager.addLine("itll be a breeze! Lets go!");
            }
            else {
                // Stuff is already added if there's an injury
                if (player.FURY_ENABLED) {
                    dialogmanager.addLine("You know...", CutscenePlayer.Expression.Concerned);
                    dialogmanager.addLine("When I got hurt", CutscenePlayer.Expression.Concerned);
                    dialogmanager.addLine("I felt really ....angry.", CutscenePlayer.Expression.Irked);
                    dialogmanager.addLine("But then it passed like that!");
                    dialogmanager.addLine("Oh well. Lets keep going!");
                }
            }
        }
        else if (player.FURY_ENABLED) {
            dialogmanager.addLine("Woah! That was interesting");
            dialogmanager.addLine("When I knocked out those");
            dialogmanager.addLine("adventurers, I felt...");
            dialogmanager.addLine("...", CutscenePlayer.Expression.Concerned);
            dialogmanager.addLine("Angry!", CutscenePlayer.Expression.Irked);
            dialogmanager.addLine("But it was... exciting too", CutscenePlayer.Expression.Irked);
            dialogmanager.addLine("...", CutscenePlayer.Expression.Concerned);
            dialogmanager.addLine("I dont know what this is", CutscenePlayer.Expression.Concerned);
            dialogmanager.addLine("This...feeling", CutscenePlayer.Expression.Concerned);
        }
    }
    
    private void setupDialog_field_2() {
        // Pacifist
        if (Player.kills == 0) {
            if (Player.numInjuries() <= 1) {
                dialogmanager.addLine("Wheeeeee!");
                dialogmanager.addLine("This world is so great!");
                dialogmanager.addLine("All those nice");
                dialogmanager.addLine("Adventurers keep smiling");
                dialogmanager.addLine("At us!");
            }
            else if (Player.numInjuries() < 3) {
                dialogmanager.addLine("Well..", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("Its not quite as carefree", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("as I expected...", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("But I am having a lot of fun");
                dialogmanager.addLine("This world is so beautiful!");
                dialogmanager.addLine("Whats not to love?", CutscenePlayer.Expression.Sad);
            }
            else {
                dialogmanager.addLine("I dont get it...", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("They all attack me!", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("Whats happening!?!", CutscenePlayer.Expression.Sad);
            }
        }
        else if (player.FURY_ENABLED) {
        }
    }
    
    private void setupDialog_forest_1() {
        // Pacifist
        if (Player.kills == 0) {
            if (Player.numInjuries() <= 2) {
                dialogmanager.addLine("What a beautiful forest!!");
                dialogmanager.addLine("The trees are so lovely");
                dialogmanager.addLine("And our friends are here too!");
                dialogmanager.addLine("Gosh Im so glad theyre so friendly...");
            }
            else if (Player.numInjuries() < 8) {
                dialogmanager.addLine("Ooh Im feeling a little sore", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("Why do you think theyre", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("attacking us?", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("I am having fun though.");
                dialogmanager.addLine("I really...   ", CutscenePlayer.Expression.Sad, false);
                dialogmanager.addLine("I really am.");
                dialogmanager.addLine("Come on now!");
            }
            else {
                dialogmanager.addLine("This is a lot to handle...", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("I dont know what to do", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("Do we keep going?", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("Well I guess we dont really", CutscenePlayer.Expression.Concerned);
                dialogmanager.addLine("Have a choice do we?", CutscenePlayer.Expression.Concerned);
                dialogmanager.addLine("After all theyre just waiting", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("For us to come back...", CutscenePlayer.Expression.Sad);
            }
        }
    }
    
    private void setupDialog_cave_1() {
        // Pacifist
        if (Player.kills == 0) {
            if (Player.numInjuries() <= 2) {
                dialogmanager.addLine("Oooooooh this cave is spooky");
                dialogmanager.addLine("But like.");
                dialogmanager.addLine("In a good way you know?");
                dialogmanager.addLine("I cant wait to take my friends here!");
                dialogmanager.addLine("....", CutscenePlayer.Expression.Concerned);
                dialogmanager.addLine("No silly! Youre my friend too!");
                dialogmanager.addLine("I just mean....");
                dialogmanager.addLine("I wonder where my other friends are?", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("Oh well. Well see them around.");
                dialogmanager.addLine("And then I can show them this awesome cave!");
            }
            else if (Player.numInjuries() < 9) {
                dialogmanager.addLine("Wow this cave is actually       ", CutscenePlayer.Expression.Concerned, false);
                dialogmanager.addLine("Kind of scary...", CutscenePlayer.Expression.Concerned);
                dialogmanager.addLine("Oh well. Well be out of there soon!");
                dialogmanager.addLine("...", CutscenePlayer.Expression.Concerned);
                dialogmanager.addLine("Come on! Chin up!");
                dialogmanager.addLine("We gotta see what else this world has");
                dialogmanager.addLine("in store for us!");
            }
            else {
                dialogmanager.addLine("Everything hurts...", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("They keep hitting me", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("and hitting me...", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("I just want to go home.", CutscenePlayer.Expression.Concerned);
                dialogmanager.addLine("...", CutscenePlayer.Expression.Concerned);
                dialogmanager.addLine("No Im not having fun.", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("Everybody keeps hurting me!", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("Just...lets go.", CutscenePlayer.Expression.Sad);
            }
        }
    }
    
    private void setupDialog_mountain_1() {
        // Pacifist
        if (Player.kills == 0) {
            if (Player.numInjuries() <= 3) {
                dialogmanager.addLine("It looks like we're almost done exploring!");
                dialogmanager.addLine("I can see my house from here!");
                dialogmanager.addLine("This was so great. Thank you!");
                dialogmanager.addLine("You know...");
                dialogmanager.addLine("I really like being your friend!");
                dialogmanager.addLine("Will you be my friend forever?");
                dialogmanager.addLine("We can go on trips all the time...");
                dialogmanager.addLine("Have hot chocolate in winter...");
                dialogmanager.addLine("Just stick around for a while, ok?");
            }
            else if (Player.numInjuries() < 12) {
                dialogmanager.addLine("Whew!");
                dialogmanager.addLine("Im tired!");
                dialogmanager.addLine("Fortunately I can see home from here.");
                dialogmanager.addLine("Just a little farther now!");
                dialogmanager.addLine("...", CutscenePlayer.Expression.Concerned);
                dialogmanager.addLine("you know...", CutscenePlayer.Expression.Concerned);
                dialogmanager.addLine("I kind of miss having people around", CutscenePlayer.Expression.Concerned);
                dialogmanager.addLine("...im sorry for asking but...", CutscenePlayer.Expression.Concerned);
                dialogmanager.addLine("would you stick around a while?", CutscenePlayer.Expression.Concerned);
                dialogmanager.addLine("actually.");
                dialogmanager.addLine("I bet you have important things to do.");
                dialogmanager.addLine("Dont worry about it!");
                dialogmanager.addLine("Ill be okay");
            }
            else {
                dialogmanager.addLine("I.....      ", CutscenePlayer.Expression.Concerned, false);
                dialogmanager.addLine("I.....      ", CutscenePlayer.Expression.Sad, false);
                dialogmanager.addLine("I dont know what id do without you", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("This place is so terrible...", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("Everything is awful", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("Please....", CutscenePlayer.Expression.Concerned, false);
                dialogmanager.addLine("Please. when we get back", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("dont leave for a while ok?", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("just think about it.", CutscenePlayer.Expression.Sad);
                dialogmanager.addLine("Thanks...im sorry...", CutscenePlayer.Expression.Concerned);
            }
        }
    }
}
