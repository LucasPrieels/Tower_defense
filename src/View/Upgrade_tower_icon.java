package View;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

public class Upgrade_tower_icon extends Parent {
    public Upgrade_tower_icon() throws FileNotFoundException {
        Image tower = new Image(new FileInputStream("Images/upgrade_tower.png"));
        ImageView imageView = new ImageView(tower);
        imageView.setX(Map.get_canvas_width()-tower.getWidth()-10);
        imageView.setY(Map.get_canvas_height()-imageView.getFitHeight()-100);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);

        this.getChildren().add(imageView);
    }
}