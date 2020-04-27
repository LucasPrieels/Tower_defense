package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Background extends JPanel {

    public Background(){
    }

    public void paintComponent(Graphics g){

        try {
            Image img = ImageIO.read(new File("images/menu.jpg"));
            g.drawImage(img, 0, 0, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}