package View;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Destroy_tower_icon extends Parent {
    public Destroy_tower_icon() throws FileNotFoundException {
        Image icon = new Image(new FileInputStream("Images/moins.png"));
        ImageView imageView = new ImageView(icon);
        imageView.setX(Map.get_canvas_width()-imageView.getFitWidth()-80);
        imageView.setY(Map.get_canvas_height()-imageView.getFitHeight()-100);
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);

        this.getChildren().add(imageView);
    }
}
