public class Project2Runner {
    
    /*
     * Name: CJ Mejia
     * Student ID: <Removed for obvious reasons>
     * 
     ******** Project Description ********
     * 
     * This program is a bossfighting videogame with gameplay similar to bossfights in touhou and terraria.
     * The player has 5 hp and is trying to survive waves of attacks until the boss's sheild goes down.
     * The player must then click on the boss in order to damage the boss.
     * The boss has 10 lives and has multiple different phases with different attack patterns.
     * If the player clicks on the boss 10 times before they die, then they win.
     * The player can move with "WASD" and/or Arrow Keys 
     * 
     * ***** This game is very difficult and will probably take many attempts before the player wins. *****
     * Movement: KeyListener; Attacking the boss: MouseListener
     ******** Swing Requirement ********
     * 
     * Describe in 1 paragraph how your program satisfies the requirement that
     * there is at least 3 unique components. Be clear to identify in what
     * files and the lines number (just the starting line is fine) that the
     * components are defined on.
     * 
     * JButton: StartMenu.java: Line(s): 11, 39
     * Once this button is pressed it will dispose of the current Jpanel and will allow the program to continue and start the main game window
     * 
     * JLabel: StartMenu.java: Line(s): 13, 32
     * Used to display text "WARNING: This game is very difficult. Expect to restart over and over"
     * 
     * JProgressBar: GameWindow.Java: Line(s): 18, 40
     * Used to display how much time has passed/is left before the boss phase switches
     * This is not told to the player but it simply there for them to find out
     * 
     * 
     * ImageIcon: StartMenu.java: Line(s): 16-17, 22-23
     * These Images are used to give basic instructions on how to play, NOTE: IMAGES MUST BE IN SRC
     * 
     * 
     ******** 2D Graphics Requirement ********
     *
     * Describe in 1 paragraph how your program satisfies the requirement that
     * there is at least 1 JPanel used for drawing something. Be clear to
     * identify in what files and the line numbers that this panel is defined on.
     * 
     * JPanel Class: GameWindow.java: Line(s): 5 (extends JPanel)
     * GameWindow is a JPanel that is used as the main Panel for the game
     * it is defined on line 76, 90 in Project2Runner and has many different graphics such as circles, arcs, etc
     * these graphics make up the boss fighting game
     * 
     * 
     * 
     ******** Event Listener Requirement ********
     *
     * Describe in 1 paragraph how your program satisfies the requirement that
     * there is at least one ActionListener, and there is additionally at least
     * one MouseListener or ActionListener. Be clear to identify in what file
     * and the line numbers that these listeners are defined in.
     * 
     * ActionListener: StartMenu.java: Line(s): 67 (class); 12, 36 (ButtonListener)
     * This action listener is used to check if the start button has been pressed
     * this button will close the starting menu
     * 
     * MouseListener: GameWindow.java: Line(s): 231 (class); 16, 47 (MouseTracker)
     * This mouse listener is used to check if the player has clicked on the boss while the bosses shield is down
     * it allows the player to damage the boss and also makes the boss skip to the next phase once it hits 5 hp
     * 
     * KeyListener: GameWindow.java: Line(s): 291 (class); 11, 59 (KeyTracker)
     * The key tracker is used to move the players character around the screen
     * the way the listener is set up gives the player smooth 8 directional movement 
     */

    public static void main(String[] args) {
        
        //Declare Objects
        GameWindow game;
        GameOverMenu endMenu;

        
        //create start menu
        StartMenu menu = new StartMenu();

        //loop game until player wants to stop using end menu quit button
        do{

            //pause until player input
            menu.pause();

            //play game
            game = new GameWindow();
            game.animate();

            //Pause before giving gameover menu
            try {
                Thread.sleep(500);
            } catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }

            //Gameover  menu
            endMenu = new GameOverMenu();

            //pause until player input
            endMenu.pause();

            //close current game
            game.close();
            

        //loop until user quits using the quit button
        }while(true);
    }
}
