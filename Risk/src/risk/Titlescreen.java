
package risk;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import static risk.Main.g;

public class Titlescreen {
    static private boolean mainActive;
    static private boolean singleActive;
    static private boolean multiActive;
    static private int fontSize;
    static private boolean onFirstButton;
    static private boolean onSecondButton;
    static private boolean onThirdButton;
    static private Image menuImage;
    static private sound menuMusic = null;
    
    static void reset(){
        mainActive=true;
        fontSize=20;
        onFirstButton=false;
        onSecondButton=false;
        onThirdButton=false;
        menuImage=Toolkit.getDefaultToolkit().getImage("./TitleScreenGothic.png");
        menuMusic=new sound("titlemusic.wav");
    }
    static void drawMenu(int mousePos [],Main m){
        //Array of mouse position seperated
        int x = mousePos[0];
        int y = mousePos[1];
        //Drawing background and setting font
        if (mainActive)
        { drawMain(x, y, m); }
    }
    
    static private void drawMain(int x, int y, Main m) {
        g.drawImage(menuImage,0,0,Window.WINDOW_WIDTH,Window.WINDOW_HEIGHT,m);
            g.setFont(new Font("Viner Hand ITC", Font.ROMAN_BASELINE, fontSize));



            if((x>280&&x<483&&y>412&&y<487)) {
                onFirstButton = true;
                g.setColor(Color.white);
            } else {
                onFirstButton = false;
                g.setColor(Color.red);
            }
            g.drawString("Singleplayer", 320, 450);



            if((x>280&&x<483&&y>520&&y<595)) {
                onSecondButton = true;
                g.setColor(Color.white);
            } else {
                onSecondButton = false;
                g.setColor(Color.red);
            }
            g.drawString("Multiplayer", 320, 560);



            if(x>280 && x<483 && y>620 && y<700) {
                onThirdButton = true;
                g.setColor(Color.white);
            } else {
                onThirdButton = false;
                g.setColor(Color.red);
            }
            g.drawString("Exit", 360, 665);

            g.setColor(Color.red);
    }
    
    
    static boolean isActive(){
        return mainActive;
    }
    
    static public void pressedButton() {
        if (onFirstButton) { activateFirstButton(); }
        else if (onSecondButton) { activateSecondButton(); }
        else if (onThirdButton) { activateThirdButton(); }
    }
    
    static private void activateFirstButton() {
        
    }
    
    static private void activateSecondButton() {
        
    }
    
    static private void activateThirdButton()
    { System.exit(0); }
    
    static void checkMusicLoop(){
        if (menuMusic.donePlaying)       
            menuMusic = new sound("titlemusic.wav");
    }
}

