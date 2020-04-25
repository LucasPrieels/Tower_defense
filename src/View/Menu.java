package View;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Menu extends Parent {

    private Stage theStage;

    public Menu() throws FileNotFoundException {

        Image image = new Image(new FileInputStream("Images/menu.jpg"));
        ImageView imageView = new ImageView(image);


        Button button = new Button("Start Game");
        //je changerai la taille, position, et graphisme du bouton plus tard
        //c'est pas très important

        this.getChildren().addAll(imageView,button);


        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage theStage2 = new Stage();
                Group root2 = new Group();
                theStage2.setScene(new Scene(root2,1920,1080));
                theStage2.show();

                View.Map map = null;

                try {
                    map = new View.Map();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                root2.getChildren().add(map);

            }
        });
    }
}