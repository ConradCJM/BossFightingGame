import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class GameWindow extends JPanel{

    private JFrame frame;
    private PlayerHealth hpBar;
    private PlayerCharacter pc;
    private KeyTracker kt;
    private BossCharacter bc;
    private int bossPhase;
    private ArrayList<Projectile> projectiles;
    private int phaseTimer;
    private MouseTracker m1;
    private boolean runGame;
    private JProgressBar pg;
    private BossBar bb;
    private JLabel gameOverMessage;


    public void close(){
        frame.setVisible(false);
        frame.dispose();
    }


    //default constructor override
    public GameWindow(){
        gameOverMessage = new JLabel();
        bb = new BossBar();
        pg = new JProgressBar();
        pg.setMaximum(1000);
        pg.setValue(0);
        pg.setBounds(10,10,100,40);
        runGame = true;
        this.add(pg);
        
        m1 = new MouseTracker();

        phaseTimer = 0;

        projectiles = new ArrayList<Projectile>();

        bossPhase = 0;

        kt = new KeyTracker();

        frame = new JFrame("Good Luck!");
        
        
        this.setBackground(new Color(76,190,228));
        this.setPreferredSize(new Dimension(630,960));
        
        frame.add(this);
        
        
        bc = new BossCharacter();
        hpBar = new PlayerHealth();
        pc = new PlayerCharacter();

        frame.addKeyListener(kt);
        frame.addMouseListener(m1);
        
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.pack();
        frame.setLayout(null);
        frame.setVisible(true);

    }
    //Update gameover message based on if player wins or loses
    public void updateGameOverMessage(){
        if (hpBar.hp<=0){
            gameOverMessage = new JLabel("YOU LOSE!");
        }
        else{
            gameOverMessage = new JLabel("YOU WIN!");
        }
        gameOverMessage.setBounds(265,60,500,500);
        gameOverMessage.setFont(new Font("Impact",Font.BOLD,30));
        
        this.add(gameOverMessage);

    }

    //Method to loop game animation and call all necessary methods
    public void animate(){

        //loop while the game should be running
        while(runGame){
            sleep(10);
            phaseTimer++;

            bossAttack();
            bossPhase();
            pg.setValue(phaseTimer);
            
            checkGameState();

            //repaint JPanel
            this.repaint();
            
        }
    }

    //Method that updates runGame; checks if game should still be running by checking if either boss hp or player hp is greater than 0
    //also calls a method that updates window to tell player that they won or lost once runGame = false
    public void checkGameState(){
        runGame = hpBar.hp>0 && bc.hp>0;

        if (!runGame){
            updateGameOverMessage();
        }
    }

    //method to pause for an int amount of time
    public void sleep(int i){
        try{
            Thread.sleep(i);
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }


    //Method to paint Components
    protected void paintComponent(Graphics g){

        //draw non projectile components
        super.paintComponent(g);
        pc.draw(g);
        bc.draw(g);
        hpBar.draw(g);
        bb.draw(g);

        //draw projectiles
        for (Projectile p:projectiles){
            p.draw(g);
        }

        //check if a projectile left game window or hit the player
        for (int i = 0; i<projectiles.size();i++){

            //check if projectile leaves game
            if (projectiles.get(i).getY()>=GameWindow.this.getHeight()){
                projectiles.remove(i);
            }

            //check if player was hit
            //check if player top is between projectiles top and bottom
            if (pc.y>=projectiles.get(i).getY()&&pc.y<=projectiles.get(i).getY()+projectiles.get(i).getHeight()){
                // Debug message
                // System.out.println("Character top y value is inside projectile");
            
            //check if right side of player is between a projectile
                if (pc.x>=projectiles.get(i).getX()&&pc.x<=projectiles.get(i).getX()+projectiles.get(i).getWidth()){
                    // Debug message
                    // System.out.println("right of character is lined with a projectile");

                    
                    projectiles.remove(i);
                    hpBar.takeDamage();
                }
                //check if players left side is between a projectile
                else if (pc.x+21>=projectiles.get(i).getX()&&pc.x+21<=projectiles.get(i).getX()+projectiles.get(i).getWidth()){
                    // Debug message
                    // System.out.println("left of character is lined with a projectile");

                    
                    projectiles.remove(i);
                    hpBar.takeDamage();
                }
                
            }//check if bottom of character is between projectiles top and bottom
            else if (pc.y+35>=projectiles.get(i).getY()&&pc.y+35<=projectiles.get(i).getY()+projectiles.get(i).getHeight()){
                // Debug message
                // System.out.println("Character bottom y value is inside projectile");

                //check if right side of player is between a projectile
                if (pc.x>=projectiles.get(i).getX()&&pc.x<=projectiles.get(i).getX()+projectiles.get(i).getWidth()){
                    // Debug message
                    // System.out.println("right of character is lined with a projectile");

                    
                    projectiles.remove(i);
                    hpBar.takeDamage();
                }
                //check if players left side is between a projectile
                else if (pc.x+21>=projectiles.get(i).getX()&&pc.x+21<=projectiles.get(i).getX()+projectiles.get(i).getWidth()){
                    // Debug message
                    // System.out.println("left of character is lined with a projectile");

                    
                    projectiles.remove(i);
                    hpBar.takeDamage();
                }
            }
        }
        
    }
    //Mousetracker class
    private class MouseTracker implements MouseListener{

        //method that checks if player clicked
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            if (e.getButton() == 1){

                //check if x is within boss character
                if (x>=bc.getX()-3 && x<=bc.getX()+32){

                    //check if y is within boss character
                    if (y>=bc.getY()+13 && y<=bc.getY()+68){

                        // Debug print message
                        // System.out.println("Clicked boss");

                        //check if boss doesn't have shield
                        if (!(bc.hasShield)){
                            bc.hp--;
                            
                        }
                    }
                }
            }
            // Debug print message
            // System.out.println("Boss: x:<"+(bc.getX()-3)+", "+(bc.getX()+32)+">");
            // System.out.println("Boss: y:<"+(bc.getY()+13)+", "+(bc.getY()+68)+">");
            // System.out.println("Clicked: <"+x+", "+y+">\n\n\n");
            // System.out.println(bc.hp);

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

    //KeyTracker class
    private class KeyTracker implements KeyListener{
        
        //key typed method
        @Override
        public void keyTyped(KeyEvent e) {}

        //key pressed method
        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();

            //check if user inputted movement [w,a,s,d] or arrow keys; also allows for double inputs to allow quicker movement
            if (code == KeyEvent.VK_W){
                pc = new PlayerCharacter(pc.x,pc.y-10);
            }
            else if (code == KeyEvent.VK_S){
                pc = new PlayerCharacter(pc.x,pc.y+10);
            }
            else if (code == KeyEvent.VK_A){
                pc = new PlayerCharacter(pc.x-10,pc.y);
            }
            else if (code == KeyEvent.VK_D){
                pc = new PlayerCharacter(pc.x+10,pc.y);
            }
            else if (code == KeyEvent.VK_UP){
                pc = new PlayerCharacter(pc.x,pc.y-10);
            }
            else if (code == KeyEvent.VK_DOWN){
                pc = new PlayerCharacter(pc.x,pc.y+10);
            }
            else if (code == KeyEvent.VK_LEFT){
                pc = new PlayerCharacter(pc.x-10,pc.y);
            }
            else if (code == KeyEvent.VK_RIGHT){
                pc = new PlayerCharacter(pc.x+10,pc.y);
            }
            

            
        }

        //key released method
        @Override
        public void keyReleased(KeyEvent e) {}

    }
    //method to change phase of boss and resets phase timer, some phases last longer which is why sometimes phasetimer will be set to an int other than 0
    public void bossPhase(){
        //check if boss phase is 1 or 0, increment phase
        if (phaseTimer/1000==1 && bossPhase<2){
            bossPhase++;
            phaseTimer = 0;
        }
        //check if boss phase ==2, set phase to phase 3
        else if (phaseTimer/1000==1 && bossPhase==2){
            bossPhase++;
            phaseTimer = 600;
        }
        //check if boss phase ==3, set phase to phase 4
        else if (phaseTimer/1000==1 && bossPhase ==3){
            bossPhase++;
            phaseTimer = 0;
        }
        //default to phase 0 once every other phase is done;
        else if(phaseTimer/1000==1){
            bossPhase=0;
            phaseTimer = 500;
        }
        
        
            
    }

    //method for boss attack
    public void bossAttack(){


        if (bossPhase == 1){
            bc.hasShield = true;

            //teleport boss randomly accross the top every so often and teleports rapidly when phase starts
            if (phaseTimer%150==0||(phaseTimer<30&&phaseTimer%5==0)){
                bc = new BossCharacter((int)(Math.random()*608),40 + (int) (Math.random()*250),true,bc.hp);
            }

            //check when to spawn projectiles
            if (phaseTimer%30==0){

                //spawn projectiles
                projectiles.add(new CircleProjectile(Color.RED, bc.x-10, bc.y+1, 7, 4, 25, 25));
                projectiles.add(new CircleProjectile(Color.BLUE, bc.x-10, bc.y+1, -7, 4, 25, 25));
                projectiles.add(new CircleProjectile(Color.MAGENTA, bc.x-10, bc.y+1, 0, 4, 25, 25));
            }
            
        }
        //check if boss is in "mirror user movement" phase [phase = 2]
        else if (bossPhase ==2){
            bc.hasShield = true;

            //check when to spawn projectile
            if (phaseTimer%50==0){

                
                //chase player by teleporting (because it looks cooler)
                bc = new BossCharacter(pc.x,pc.y-600,true,bc.hp);

                //spawn projectile
                projectiles.add(new ArcProjectile(Color.MAGENTA,bc.x-28,bc.y+2,-2,6,65,50,180,180));
                projectiles.add(new ArcProjectile(Color.MAGENTA,bc.x-28,bc.y+2,0,6,65,50,180,180));
                projectiles.add(new ArcProjectile(Color.MAGENTA,bc.x-28,bc.y+2,2,6,65,50,180,180));

            }
        } //check if boss is in phase 3 
        else if (bossPhase == 3){
            bc.hasShield = true;

            //check when to spawn projectile
            if (phaseTimer%100 == 0){

                //move boss character 
                bc = new BossCharacter(140,40,true,bc.hp);

                //spawn projectile
                projectiles.add(new ArcProjectile(Color.BLACK,0,40,0,4,315,50,180,180));

            }
            else if (phaseTimer%50 == 0){

                //move boss character 
                bc = new BossCharacter(455,40,true,bc.hp);

                //spawn projectile
                projectiles.add(new ArcProjectile(Color.BLACK,315,40,0,4,315,50,180,180));

            }
        }
        //check if boss is in phase 4
        else if (bossPhase == 4){
            bc.hasShield = true;

            //move boss to set position according to timer
            if (phaseTimer < 10){
                bc = new BossCharacter(50,50,true,bc.hp);

            }//move boss and attack
            else if (phaseTimer < 500 && phaseTimer%25 == 0){
                bc = new BossCharacter(bc.x+20,bc.y+4,true,bc.hp);
                projectiles.add(new CircleProjectile(Color.RED, bc.x-10, bc.y+1, 7, 6, 30, 30));
                projectiles.add(new CircleProjectile(Color.BLUE, bc.x-10, bc.y+1, -7, 3, 30, 30));
            }//move boss to set poition according to timer
            else if (phaseTimer == 500){
                bc = new BossCharacter(580,50,true,bc.hp);

            }//move boss and attack
            else if(phaseTimer > 500 && phaseTimer%25 ==0){
                bc = new BossCharacter(bc.x-20,bc.y+4,true,bc.hp);
                projectiles.add(new CircleProjectile(Color.RED, bc.x-10, bc.y+1, 7, 6, 30, 30));
                projectiles.add(new CircleProjectile(Color.BLUE, bc.x-10, bc.y+1, -7, 3, 30, 30));

            }

        }
        //check if boss loses shield
        else if (bossPhase == 0){

            //make boss lose shield
            bc.hasShield =false;

            // move boss randomly on screen to make it hard to click on them
            if (phaseTimer%40==0){
                bc = new BossCharacter((int)(Math.random()*608),40 + (int) (Math.random()*770),false,bc.hp);
            }

        }
        else{
            System.out.println("broken phase");
        }
    }

    //class for arc projectiles
    private class ArcProjectile extends Projectile{
        private int dx,dy,startAngle,arcAngle;
        private Color colour;

        //override constructor
        public ArcProjectile(){
            this.setWidth(10);
            this.setHeight(10);
            this.setX(0);
            this.setY(0);
            this.dx = 0;
            this.dy = 1;
            this.colour = Color.MAGENTA;
            this.startAngle = 1;
            this.arcAngle = 10;
        }

        //overload constructor
        public ArcProjectile(Color colour, int x, int y, int dx, int dy, int width, int height,int startAngle,int arcAngle){
            this.setWidth(width);
            this.setHeight(height);
            this.setX(x);
            this.setY(y);
            this.dx = dx;
            this.dy = dy;
            this.colour = colour;
            this.startAngle = startAngle;
            this.arcAngle = arcAngle;
        }

        //draw projectile
        public void draw(Graphics g){
            Graphics2D g2d = (Graphics2D) g;   
            g2d.setColor(colour);
            g2d.fillArc(this.getX(), this.getY(), this.getWidth(), this.getHeight(),this.startAngle,this.arcAngle);
            this.setX(this.getX()+dx);
            this.setY(this.getY()+dy);

            //print test
            // System.out.println("Moved Wave");
            
        }


    }

    //class for circle projectiles
    private class CircleProjectile extends Projectile{
        private int dx,dy;
        private Color colour;
        
        //Override constructor
        public CircleProjectile(){
            this.setWidth(10);
            this.setHeight(10);
            this.setX(0);
            this.setY(0);
            this.dx = 0;
            this.dy = 1;
            this.colour = Color.MAGENTA;
        }

        //overload constructor
        public CircleProjectile(Color colour, int x, int y, int dx, int dy, int width, int height){
            this.setWidth(width);
            this.setHeight(height);
            this.setX(x);
            this.setY(y);
            this.dx = dx;
            this.dy = dy;
            this.colour = colour;
        }

        //draw projectile
        public void draw(Graphics g){
            Graphics2D g2d = (Graphics2D) g;   
            g2d.setColor(colour);
            g2d.fillOval(this.getX(), this.getY(), this.getWidth(), this.getHeight());
            this.setX(this.getX()+dx);
            this.setY(this.getY()+dy);

            if (this.getX()+10<0) dx = 7;
            if (this.getX()>=GameWindow.this.getWidth()-this.getWidth()) dx = -7;

            //Test print
            // System.out.println("Moved Ball");
        }
    }
    //abstract class for Projectiles
    private abstract class Projectile{
        private int y;
        private int x;
        private int width;
        private int height;

        //method to get width;
        public int getWidth(){
            return width;
        }

        //method to set width;
        public void setWidth(int w){
            width = w;
        }

        //method to get height;
        public int getHeight(){
            return height;
        }

        //method to set height;
        public void setHeight(int h){
            height  = h;
        }

        //method to get y
        public int getY(){
            return y;
        }

        //method to set y;
        public void setY(int y){
            this.y=y;
        }

        //method to get x;
        public int getX(){
            return x;
        }
        

        //method to set x
        public void setX(int x){
            this.x =x;
        }

        //abstract method to draw
        abstract void draw(Graphics g);
        
    }

    //abstract class for Shapes
    private abstract class Shape{

        //abstract method to draw
        abstract void draw(Graphics g);
        
    }

    // class for boss character
    private class BossCharacter extends Shape{

        //fields
        private int x,y;
        private boolean hasShield;
        private int hp;

        //override constructor
        public BossCharacter(){
            this.y = 200;
            this.x = 300;
            this.hasShield = true;
            this.hp = 10;
            
        }

        //overload constructor
        public BossCharacter(int x,int y){
            this.hasShield = true;
            if(x>=608){
                this.x=608;
            }
            else if(x<=0){
                this.x=0;
            }
            else{
                this.x=x;
            }

            if (y>=810){
                this.y = 810;
            }
            else if (y<=40){
                this.y = 45;
            }
            else{
                this.y =y;
            }
        }
        //overload constructor
        public BossCharacter(int x,int y,boolean shield,int hp){
            this.hasShield = shield;
            this.hp = hp;
            if(x>=608){
                this.x=608;
            }
            else if(x<=0){
                this.x=0;
            }
            else{
                this.x=x;
            }

            if (y>=810){
                this.y = 810;
            }
            else if (y<=40){
                this.y = 40;
            }
            else{
                this.y =y;
            }
        }

        //get x
        public int getX(){
            return this.x;
        }

        //get y
        public int getY(){
            return this.y;
        }

        //set x
        public void setX(int x){
            if (x>0 && y<=608){
                this.x = x;
            }
        }

        //set y
        public void setY(int y){
            if (y<=810 && y>=40){
                this.y = y;
            }
        }
        //method to print game character
        @Override
        public void draw(Graphics g){
            Graphics2D g2d = (Graphics2D) g;
            Graphics2D gd = (Graphics2D) g;
            Graphics2D gr2d = (Graphics2D) g;

            g2d.setColor(Color.RED);
            g2d.fillRoundRect(this.x, this.y, 21, 35, 10, 10);

            gd.setColor(Color.CYAN);
            gd.fillRoundRect(this.x+3, this.y+5, 15, 8, 10, 10);

            if (hasShield){
                gr2d.setColor((Color.CYAN));
                gr2d.setStroke(new BasicStroke(2));
                gr2d.drawOval(x-14, y-9, 50, 50);
            }
        }


    }

    //class for player character
    private class PlayerCharacter extends Shape{

        //fields
        private int x,y;

        //override constructor
        public PlayerCharacter(){
            this.x=300;
            this.y=800;
        }

        //overload constructor
        public PlayerCharacter(int x,int y){
            if(x>=608){
                this.x=608;
            }
            else if(x<=0){
                this.x=0;
            }
            else{
                this.x=x;
            }

            if (y>=810){
                this.y = 810;
            }
            else if (y<=40){
                this.y = 40;
            }
            else{
                this.y =y;
            }
        }

        //get x
        public int getX(){
            return this.x;
        }

        //get y
        public int getY(){
            return this.y;
        }

        //set x
        public void setX(int x){
            if (x>0 && y<=608){
                this.x = x;
            }
        }

        //set y
        public void setY(int y){
            if (y<=810 && y>=40){
                this.y = y;
            }
        }


        //method to print game character
        @Override
        public void draw(Graphics g){
            Graphics2D g2d = (Graphics2D) g;
            Graphics2D gd = (Graphics2D) g;

            g2d.setColor(Color.BLACK);
            g2d.fillRoundRect(this.x, this.y, 21, 35, 10, 10);

            gd.setColor(Color.CYAN);
            gd.fillRoundRect(this.x+3, this.y+5, 15, 8, 10, 10);
        }
    }
    //class for boss hp bar
    private class BossBar extends Shape{

        //fields

        private int x;
        private int y;

        //Playerhealth constructor
        public BossBar(){
            x=10;
            y=9;
            
        }

        //get x
        public int getX(){
            return x;
        }

        //get y
        public int getY(){
            return y;
        }

        //set x
        public void setX(int x){
            this.x = x;
        }

        //set y
        public void setY(int y){
            this.y = y;
        }
    
        //Method to draw boss hp
        @Override
        public void draw(Graphics g){
            Graphics2D g2d = (Graphics2D) g;

            //Draw hp
            g2d.setColor(Color.GRAY);
            g2d.fillRoundRect(this.getX(),this.getY(),18*bc.hp,25,15,15);

            //draw hp border
            if (bc.hasShield){
                g2d.setColor(Color.CYAN);
            }
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRoundRect(this.getX(),this.getY(),180,25,15,15);
        }
    }




    //class for players hp bar/health
    private class PlayerHealth extends Shape{

        //fields
        private int hp;
        private int x;
        private int y;

        //Playerhealth constructor
        public PlayerHealth(){
            x=435;
            y=9;
            hp = 5;
        }

        //get x
        public int getX(){
            return x;
        }

        //get y
        public int getY(){
            return y;
        }

        //set x
        public void setX(int x){
            this.x = x;
        }

        //set y
        public void setY(int y){
            this.y = y;
        }
    
        //get hp
        public int getHp(){
            return hp;
        }
    
        //set hp
        public void setHp(int hp){
            this.hp = hp;
        }
    
        //method that decrements hp when the player takes damage
        public void takeDamage(){
            this.hp--;
        }


        //Method to draw players hp
        @Override
        public void draw(Graphics g){
            Graphics2D g2d = (Graphics2D) g;



            //Draw header
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0,0,630,40);
            
            //Draw hp
            g2d.setColor(Color.RED);
            g2d.fillRoundRect(this.getX(),this.getY(),36*this.hp,25,15,15);

            //draw hp border
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRoundRect(this.getX(),this.getY(),180,25,15,15);
        }
    }
}
