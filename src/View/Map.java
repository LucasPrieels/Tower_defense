package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Map extends JPanel {
    private Graphics2D g;
    Image space;

    public Map(){
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void paint(Graphics g){
        this.g = (Graphics2D)g;

        Image asteroid1 = new ImageIcon("images/asteroid1.png").getImage();

        this.space = new ImageIcon("images/level1.jpg").getImage();
        ((Graphics2D) g).drawImage(space,0,0,null);

        g.drawImage(asteroid1,100,200,80,80,null);




    }

    public void redraw(){
        this.repaint();
    }


}