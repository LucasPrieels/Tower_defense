package View;

import Model.Classic_tower;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

public class Buy_classic_tower_icon extends Parent {
    public Buy_classic_tower_icon() throws FileNotFoundException {
        Image icon = new Image(new FileInputStream("Images/classic_tower_icon.png"));
        ImageView imageView = new ImageView(icon);
        imageView.setX(Map.get_canvas_width()-4*(imageView.getFitWidth()+80));
        imageView.setY(Map.get_canvas_height()-imageView.getFitHeight()-100);
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);

        this.getChildren().add(imageView);
    }
}
