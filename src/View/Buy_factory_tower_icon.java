package View;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Buy_factory_tower_icon extends Parent {
    public Buy_factory_tower_icon() throws FileNotFoundException {
        Image icon = new Image(new FileInputStream("Images/factory_tower_icon.png"));
        ImageView imageView = new ImageView(icon);
        imageView.setX(Map.get_canvas_width()-3*(icon.getWidth()+10));
        imageView.setY(Map.get_canvas_height()-icon.getHeight()-40);

        this.getChildren().add(imageView);
    }
}