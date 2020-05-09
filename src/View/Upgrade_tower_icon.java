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
    private static ImageView imageView;
    public Upgrade_tower_icon() throws FileNotFoundException {
        Image tower = new Image(new FileInputStream("Assets/plus.png"));
        this.imageView = new ImageView(tower);
        imageView.setX(Map.get_canvas_width()-2*(imageView.getFitWidth()+80));
        imageView.setY(Map.get_canvas_height()-imageView.getFitHeight()-100);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);

        this.getChildren().add(imageView);
    }
    public static double get_pos_x(){return imageView.getX();}
    public static double get_pos_y(){return imageView.getY();}
    public static double get_width(){return imageView.getFitWidth();}
    public static double get_height(){return imageView.getFitHeight();}


}