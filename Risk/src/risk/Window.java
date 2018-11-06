
package risk;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Window {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static final int XBORDER = 0;
    static final int YBORDER = 0;
    static final int YTITLE = 31;
    static final int WINDOW_BORDER = 8; 
    static final int WINDOW_WIDTH = (int) screenSize.getWidth();
    static final int WINDOW_HEIGHT = (int) screenSize.getHeight();    

    static int MIN_X;
    static int MAX_X;
    static int MIN_Y;
    static int MAX_Y;

    static final double frameRate = 60.0;
    static boolean animateFirstTime = true;
    static int xsize = -1;
    static int ysize = -1;


/////////////////////////////////////////////////////////////////////////
    public static int getX(int x) {
        return (x + XBORDER + WINDOW_BORDER);
    }

    public static int getY(int y) {
        return (y + YTITLE );
//        return (y + YBORDER + YTITLE );
        
    }

    public static int getYNormal(int y) {
        return (-y + YTITLE + getHeight2());
//        return (-y + YBORDER + YTITLE + getHeight2());
        
    }
    
    public static int getWidth2() {
        return (xsize - (XBORDER + WINDOW_BORDER));
    }

    public static int getHeight2() {
//        return (ysize - 2 * YBORDER - WINDOW_BORDER - YTITLE);
        return (ysize - WINDOW_BORDER - YTITLE);
    }    
}
