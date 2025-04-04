import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//class for start menu 
public class StartMenu extends JPanel{

    //fields
    private JFrame frame;
    private boolean pressed=false;
    private JButton start;
    private ButtonListener b1;
    private JLabel label;
    private JLabel image1;
    private JLabel image2;
    private ImageIcon i1;
    private ImageIcon i2;

    //override constructor
    public StartMenu(){
        //icons for images
        i1 = new ImageIcon("src/HowToPlay1.png");
        i2 = new ImageIcon("src/HowToPlay2.png");

        //images
        image1 = new JLabel(i1);
        image2 = new JLabel(i2);
        image1.setBounds(0, 20, 400, 500);
        image2.setBounds(300, 20, 550, 500);

        //labels
        label = new JLabel("WARNING: This game is very difficult. Expect to restart over and over");
        label.setBounds(5, 5, 400, 50);

        //button listener
        b1 = new ButtonListener();
        
        //button
        start = new JButton();
        start.setText("Start Game");
        start.setBounds(0,450,800,50);
        start.addActionListener(b1);
        
        //jframe
        frame = new JFrame();
        frame.add(label);
        frame.add(start);

        frame.add(image1);
        frame.add(image2);
        
        //jpanel
        this.setBackground(new Color(76,190,228));
        this.setPreferredSize(new Dimension(800,500));

        //jframe
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        frame.add(this);
        frame.pack();
        frame.setLayout(null);
        frame.setVisible(true);
        
    }

    //class for button listener
    private class ButtonListener implements ActionListener{

        //set pressed to true, hide & dispose frame
        @Override
        public void actionPerformed(ActionEvent e) {
            pressed = true;
            frame.setVisible(false);
            frame.dispose();

            
        }

    }

    //method that pauses program until user presses a button
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
}
