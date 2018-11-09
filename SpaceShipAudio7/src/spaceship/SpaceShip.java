/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceship;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.*;


public class SpaceShip extends JFrame implements Runnable {

    boolean animateFirstTime = true;
    Image image;
    Graphics2D g;

    Image outerSpaceImage;

//variables for rocket.
    Rocket rocket;
 
    boolean gameOver;
    int timeCount;
    double frameRate = 25;
    
    sound missileSound = null;
    sound bgSound = null;
    
    
    static SpaceShip frame;
    public static void main(String[] args) {
        frame = new SpaceShip();
        frame.setSize(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public SpaceShip() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON1 == e.getButton()) {
                    //left button

// location of the cursor.
                    int xpos = e.getX();
                    int ypos = e.getY();

                }
                if (e.BUTTON3 == e.getButton()) {
                    //right button
                    reset();
                }
                repaint();
            }
        });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        repaint();
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {

        repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                
                if (gameOver)
                    return;
                
                if (e.VK_UP == e.getKeyCode()) {
                    rocket.incYSpeed(1);
                } else if (e.VK_DOWN == e.getKeyCode()) {
                    rocket.incYSpeed(-1);
                } else if (e.VK_LEFT == e.getKeyCode()) {
                    rocket.incXSpeed(-1);
                } else if (e.VK_RIGHT == e.getKeyCode()) {
                    rocket.incXSpeed(1);
                } else if (e.VK_SPACE == e.getKeyCode()) {
                    missileSound = new sound("missile.wav");
                    Missile.Add(rocket);
                }

                repaint();
            }
        });
        init();
        start();
    }
    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }



////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || Window.xsize != getSize().width || Window.ysize != getSize().height) {
            Window.xsize = getSize().width;
            Window.ysize = getSize().height;
            image = createImage(Window.xsize, Window.ysize);
            g = (Graphics2D) image.getGraphics();
            Drawing.setDrawingInfo(g,this);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
//fill background
        g.setColor(Color.cyan);
        g.fillRect(0, 0, Window.xsize, Window.ysize);

        int x[] = {Window.getX(0), Window.getX(Window.getWidth2()), Window.getX(Window.getWidth2()), Window.getX(0), Window.getX(0)};
        int y[] = {Window.getY(0), Window.getY(0), Window.getY(Window.getHeight2()), Window.getY(Window.getHeight2()), Window.getY(0)};
//fill border
        g.setColor(Color.black);
        g.fillPolygon(x, y, 4);
// draw border
        g.setColor(Color.red);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }

        g.drawImage(outerSpaceImage,Window.getX(0),Window.getY(0),
                Window.getWidth2(),Window.getHeight2(),this);

        rocket.draw();
   
        Star.Draw();
        Missile.Draw();
        
        if (gameOver)
        {
            g.setColor(Color.white);
            g.setFont(new Font("Arial",Font.PLAIN,50));
            g.drawString("Game Over", 60, 360);        
        }            
        gOld.drawImage(image, 0, 0, null);
    }

////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
//            double seconds = .04;    //time that 1 frame takes.
            double seconds = 1/frameRate;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
/////////////////////////////////////////////////////////////////////////
    public void reset() {
        timeCount = 0;
        gameOver = false;
        rocket = new Rocket();
        Star.Init();
        Missile.Init();

    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {
        if (animateFirstTime) {
            animateFirstTime = false;
            if (Window.xsize != getSize().width || Window.ysize != getSize().height) {
                Window.xsize = getSize().width;
                Window.ysize = getSize().height;
            }
            outerSpaceImage = Toolkit.getDefaultToolkit().getImage("./outerSpace.jpg");
//            rocketImage = Toolkit.getDefaultToolkit().getImage("./rocket.GIF");
//            rocketImage = Toolkit.getDefaultToolkit().getImage("./animRocket.GIF");
            reset();    
            bgSound = new sound("starwars.wav");
            
        }
        if (gameOver)
            return;
        
        if (bgSound.donePlaying)       
            bgSound = new sound("starwars.wav");
        
        
        Missile.CheckHit();
        
        if (Star.Move(rocket))
        {
            gameOver = true;                
        }
        Missile.Move();
        rocket.move();

        if (timeCount % frameRate == frameRate-1)
            Star.Add();
        
        timeCount++;
    }

////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }

}
////////////////////////////////////////////////////////////////////////////

class Window {
    private static final int XBORDER = 20;
    
//    private static final int YBORDER = 20;
    
    private static final int TOP_BORDER = 40;
    private static final int BOTTOM_BORDER = 20;
    
    private static final int YTITLE = 30;
    private static final int WINDOW_BORDER = 8;
    static final int WINDOW_WIDTH = 2*(WINDOW_BORDER + XBORDER) + 600;
    static final int WINDOW_HEIGHT = YTITLE + WINDOW_BORDER + 600;
    static int xsize = -1;
    static int ysize = -1;


/////////////////////////////////////////////////////////////////////////
    public static int getX(int x) {
        return (x + XBORDER + WINDOW_BORDER);
    }

    public static int getY(int y) {
//        return (y + YBORDER + YTITLE );
        return (y + TOP_BORDER + YTITLE );
        
    }

    public static int getYNormal(int y) {
//          return (-y + YBORDER + YTITLE + getHeight2());
      return (-y + TOP_BORDER + YTITLE + getHeight2());
        
    }
    
    public static int getWidth2() {
        return (xsize - 2 * (XBORDER + WINDOW_BORDER));
    }

    public static int getHeight2() {
//        return (ysize - 2 * YBORDER - WINDOW_BORDER - YTITLE);
        return (ysize - (BOTTOM_BORDER + TOP_BORDER) - WINDOW_BORDER - YTITLE);
    }    
}

class Rocket {
    private static Image image = Toolkit.getDefaultToolkit().getImage("./rocket.GIF");    
    
    private int xPos;
    private int yPos;
    private int xSpeed;
    private int ySpeed;
   
    Rocket()
    {
        xPos = (int)(Window.getWidth2()/2);
        yPos = (int)(Window.getHeight2()/2);  
        xSpeed = 1;
        ySpeed = 0;
    }
    public int getXPos() {
        return (xPos);
    }
    public int getYPos() {
        return (yPos);
    }
    public int getXSpeed() {
        return (xSpeed);
    }
    
    public void draw()
    {
        Drawing.drawImage(image,Window.getX(xPos),Window.getYNormal(yPos),0.0,1.0,1.0 );
    }
    public void move()
    { 
        
        yPos += ySpeed;
        
//Stop the rocket when it gets to the top or bottom of the window.        
        if (yPos < 0)
        {
            yPos = 0;
            ySpeed = 0;
        }
        if (yPos > Window.getHeight2())
        {
            yPos = Window.getHeight2();
            ySpeed = 0;
        }
                
    }   
    public void incYSpeed(int speed) {
        ySpeed += speed;
    }
    public void incXSpeed(int speed) {
        xSpeed += speed;
        if (xSpeed <= 0)
            xSpeed = 1;        
    }    
}

class Star { 
    private static Image image = Toolkit.getDefaultToolkit().getImage("./starAnim.GIF");    
    private static ArrayList<Star> stars = new ArrayList<Star>();     
    
    private int xPos;
    private int yPos;
    private static sound hitSound = null;

    public static void Init() {
        stars.clear();      
    }    
    public static void Add() {
        Star star = new Star();
        stars.add(star);
        star.xPos = Window.getWidth2();
    }
    public static void Draw()
    {
//        for (Star obj : stars)
//            obj.draw();

        for (int i=0;i<stars.size();i++)
            stars.get(i).draw();
        
        
    }
    public static boolean Move(Rocket rocket) {
        for (int i=0;i < stars.size();i++) {
            if (stars.get(i).collide(rocket.getXPos(),rocket.getYPos()))
            {
                return (true);                
            }
            stars.get(i).move(rocket.getXSpeed());
            if (stars.get(i).checkRemove())
                i--;
        }           
        
        return (false);
    }    
    public static int GetNumStars()
    {
        return stars.size();
    }
    public static boolean CheckHit(int i,int objXPos,int objYPos)
    {
        if (stars.get(i).collide(objXPos,objYPos))
        {
            hitSound = new sound("hit.wav");

            stars.remove(i);
            return true;
        }
        return false;
    }
    
    Star()
    {
        xPos = (int)(Math.random()* Window.getWidth2());
        yPos = (int)(Math.random()* Window.getHeight2());        
    }
    private void draw()
    {
        Drawing.drawImage(image,Window.getX(xPos),Window.getYNormal(yPos),0.0,1.0,1.0);
    }
    private void move(int rocketSpeed)
    {
        xPos -= rocketSpeed;    
    }   
    private boolean checkRemove()
    {
        if (xPos < 0)
        {
            stars.remove(this);
            return true;
        }         
        return false;
    }       

    private boolean collide(int objXPos,int objYPos)
    {
        if (xPos+10 > objXPos &&
        xPos-10 < objXPos &&
        yPos+10 > objYPos &&
        yPos-10 < objYPos)    
        {
            return (true);
        }
        return (false);
    }    
}



class Missile { 
    private static ArrayList<Missile> missiles = new ArrayList<Missile>();     
    
    private int xPos;
    private int yPos;
    private int speed;

    public static void Init() {
        missiles.clear();      
    }    
    public static void Add(Rocket rocket) {
        Missile missile = new Missile();
        missiles.add(missile);
        missile.xPos = rocket.getXPos();
        missile.yPos = rocket.getYPos();
    }
    public static void Draw()
    {
//        for (Missile obj : missiles) {
//            obj.draw();
//        }
        
        for (int i=0;i<missiles.size();i++)
            missiles.get(i).draw();
    }
    public static boolean Move() {
        for (int i=0;i < missiles.size();i++) {
//            if (missiles.get(i).collide(rocket.getXPos(),rocket.getYPos()))
//            {
//                return (true);                
//            }
            missiles.get(i).move();
            if (missiles.get(i).checkRemove())
            {
                missiles.remove(i);
                i--;
            }
        }           
        
        return (false);
    }    
    public static void CheckHit() {
        for (int i=0;i < missiles.size();i++) {
            for (int j=0;j<Star.GetNumStars();j++)
            {
                if (Star.CheckHit(j, missiles.get(i).xPos,missiles.get(i).yPos))
                {
                    missiles.remove(i);
                    i--;
                    break;
                }
            }
        }
    }
    
    
    Missile()
    {
        speed = 2;
    }
    private void draw()
    {
        Drawing.drawCircle(Window.getX(xPos),Window.getYNormal(yPos),0.0,.4,.4,Color.red);
    }
    private void move()
    {
        xPos += speed;    
    }   
    private boolean checkRemove()
    {
        if (xPos > Window.getWidth2())
        {
            return true;
        }         
        return false;
    }       

}


class Drawing {
    private static Graphics2D g;
    private static SpaceShip mainClassInst;

    public static void setDrawingInfo(Graphics2D _g,SpaceShip _mainClassInst) {
        g = _g;
        mainClassInst = _mainClassInst;
    }
////////////////////////////////////////////////////////////////////////////
    public static void drawCircle(int xpos,int ypos,double rot,double xscale,double yscale,Color color)
    {
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );

        g.setColor(color);
        g.fillOval(-10,-10,20,20);

        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }
////////////////////////////////////////////////////////////////////////////
    public static void drawImage(Image image,int xpos,int ypos,double rot,double xscale,
            double yscale) {
        int width = image.getWidth(mainClassInst);
        int height = image.getHeight(mainClassInst);
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );

        g.drawImage(image,-width/2,-height/2,
        width,height,mainClassInst);

        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }
    
    
}


class sound implements Runnable {
    Thread myThread;
    File soundFile;
    public boolean donePlaying = false;
    sound(String _name)
    {
        soundFile = new File(_name);
        myThread = new Thread(this);
        myThread.start();
    }
    public void run()
    {
        try {
        AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
        AudioFormat format = ais.getFormat();
    //    System.out.println("Format: " + format);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        SourceDataLine source = (SourceDataLine) AudioSystem.getLine(info);
        source.open(format);
        source.start();
        int read = 0;
        byte[] audioData = new byte[16384];
        while (read > -1){
            read = ais.read(audioData,0,audioData.length);
            if (read >= 0) {
                source.write(audioData,0,read);
            }
        }
        donePlaying = true;

        source.drain();
        source.close();
        }
        catch (Exception exc) {
            System.out.println("error: " + exc.getMessage());
            exc.printStackTrace();
        }
    }

}