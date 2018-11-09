
package risk;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import static risk.Main.g;

public class Titlescreen {
    static private boolean active;
    static private int fontSize;
    static private boolean onFirstButton;
    static private boolean onSecondButton;
    static private boolean onThirdButton;
    static private Image menuImage;
    static private sound menuMusic = null;
    
    static void reset(){
        active=true;
        fontSize=20;
        onFirstButton=false;
        onSecondButton=false;
        onThirdButton=false;
        menuImage=Toolkit.getDefaultToolkit().getImage("./Floating Embers.gif");
        menuMusic=new sound("titlemusic.wav");
    }
    static void drawMenu(int mousePos [],Main m){
        //Array of mouse position seperated
        int x = mousePos[0];
        int y = mousePos[1];
        //Drawing background and setting font
        g.drawImage(menuImage,0,0,Window.WINDOW_WIDTH,Window.WINDOW_HEIGHT,m);
        g.setFont(new Font("Viner Hand ITC", Font.ROMAN_BASELINE, fontSize));    
        
        
        
        if((x>280&&x<483&&y>412&&y<487))
            g.setColor(Color.white);
        else
            g.setColor(Color.red);
        g.drawString("Singleplayer", 320, 450);
        
        
        
        if((x>280&&x<483&&y>520&&y<595))
            g.setColor(Color.white);
        else
            g.setColor(Color.red);
        g.drawString("Multiplayer", 320, 560);
        
        
        
        
        if(x>280 && x<483 && y>620 && y<700)
            g.setColor(Color.white);
        else
            g.setColor(Color.red);
        g.drawString("Exit", 360, 665);
        
        g.setColor(Color.red);
    }
    
    
    static boolean isActive(){
        return active;
    }
    static void checkMusicLoop(){
        if (menuMusic.donePlaying)       
            menuMusic = new sound("titlemusic.wav");
        
    }
}

