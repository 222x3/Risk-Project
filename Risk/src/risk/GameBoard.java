
package risk;
import java.awt.*;
public class GameBoard {
    static private Image MapDraw;
    
    
    static void Reset(){
        MapDraw=Toolkit.getDefaultToolkit().getImage("./RiskMap.jpg");
    }
    
        public static void Draw(Graphics2D g,Main m){
        g.drawImage(MapDraw,0,0,Window.WINDOW_WIDTH,Window.WINDOW_HEIGHT,m);
    }
        
}

