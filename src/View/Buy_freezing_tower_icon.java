package View;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Buy_freezing_tower_icon extends Parent {
    public Buy_freezing_tower_icon() throws FileNotFoundException {
        Image icon = new Image(new FileInputStream("Images/freezing_tower.png"));
        ImageView imageView = new ImageView(icon);
        imageView.setX(970);
        imageView.setY(600);

        this.getChildren().add(imageView);
    }
}