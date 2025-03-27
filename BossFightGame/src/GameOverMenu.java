import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOverMenu extends JPanel{

    private JFrame frame;
    private boolean pressed;
    private boolean quitProgram;
    private JButton playAgain;
    private JButton quitGame;
    private ReplayButtonListener replay;
    private QuitButtonListener quit;

    //constructor override
    public GameOverMenu(){
        replay = new ReplayButtonListener();
        quit  = new QuitButtonListener();

        playAgain = new JButton();
        quitGame = new JButton();

        playAgain.addActionListener(replay);
        quitGame.addActionListener(quit);

        pressed = false;

        playAgain.setText("Play Again?");
        quitGame.setText("Quit Game?");

        playAgain.setBounds(0, 50, 400, 150);
        quitGame.setBounds(0, 250, 400, 150);

        frame = new JFrame("Select an Option:");

        this.setBackground(new Color(76,190,228));
        this.setPreferredSize(new Dimension(400,450));

        frame.add(playAgain);
        frame.add(quitGame);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.add(this);
        frame.pack();
        frame.setLayout(null);
        frame.setVisible(true);
    }

    //Method that pauses program until a button is clicked
    public void pause(){
        //pause until user presses button
        while(!(pressed)){
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    //class for button listener that replays game
    private class ReplayButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            pressed = true;
            frame.setVisible(false);
            frame.dispose();

            
        }

    }

    //class for button listener
    private class QuitButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    }
}
