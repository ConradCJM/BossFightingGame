public class Project2Runner {
    
    /*
     * Name: CJ Mejia
     * 
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
