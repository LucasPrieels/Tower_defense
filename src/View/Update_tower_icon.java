package View;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Update_tower_icon extends Parent {
    public Update_tower_icon() throws FileNotFoundException {
        Image tower = new Image(new FileInputStream("Images/update_tower.png"));
        ImageView imageView = new ImageView(tower);
        imageView.setX(1150);
        imageView.setY(600);

        this.getChildren().add(imageView);
    }
}