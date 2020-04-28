package View;

import Model.Game;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Menu extends Parent {

    private Stage theStage;

    public Menu() throws FileNotFoundException {

        Image image = new Image(new FileInputStream("Images/menu.jpg"));
        ImageView imageView = new ImageView(image);
        theStage = Main.get_stage();

        Button button_play = new Button("Play");
        button_play.setLayoutX(500);
        button_play.setLayoutY(400);
        Button button_help = new Button("Help");
        button_help.setLayoutX(100);
        button_help.setLayoutY(100);
        Button button_level = new Button("Level");
        button_level.setLayoutX(300);
        button_level.setLayoutY(300);
        Button button_exit = new Button("Exit");
        button_exit.setLayoutX(10);
        button_exit.setLayoutY(10);

        //je changerai la taille, position, et graphisme du bouton plus tard
        //c'est pas très important

        this.getChildren().addAll(imageView,button_play,button_help,button_level,button_exit);

        button_exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                theStage.close();

            }
        });

        button_play.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Group root2 = new Group();
                theStage.setScene(new Scene(root2,1920,1080));
                theStage.show();

                View.Map map = null;

                try {
                    Map.init(theStage);
                    map = Map.get_instance();
                    Thread t = new Thread(Game.get_instance());
                    t.start();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                root2.getChildren().add(map);

            }
        });
    }
}