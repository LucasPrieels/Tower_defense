package View;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Map extends Parent {
    private int level;

    public Map() throws FileNotFoundException {
        Canvas canvas = new Canvas(1920,1080);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image level1 = new Image(new FileInputStream("Images/level1.jpg"));
        gc.drawImage(level1,0,0);

        Image planet1 = new Image(new FileInputStream("Images/asteroid1.png"));
        gc.drawImage(planet1,100,100);

        Image planet2 = new Image(new FileInputStream("Images/asteroid2.png"));
        gc.drawImage(planet2,700,200);

        Image planet3 = new Image(new FileInputStream("Images/asteroid3.png"));
        gc.drawImage(planet3,1100,450);

        Image planet5 = new Image(new FileInputStream("Images/asteroid5.png"));
        gc.drawImage(planet5,500,400);

        Image planet6 = new Image(new FileInputStream("Images/asteroid6.png"));
        gc.drawImage(planet6,1100,0);

        this.getChildren().add(canvas);
    }
}