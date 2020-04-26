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

public class Menu extends Parent {/*

    private Stage theStage;

    public Menu() throws FileNotFoundException {





        //Mise en page de l'image

        Image image = new Image(new FileInputStream("Images/menu.jpg"));
        ImageView imageView = new ImageView(image);



        Button button = new Button("Start Game");


        this.getChildren().addAll(imageView,button);
















         //Evenement Button
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage theStage2 = new Stage();
                Group root2 = new Group();
                theStage2.setScene(new Scene(root2,1920,1080));
                theStage2.show();

                View.Map map = null;

                try {
                    Map.init(theStage2);
                    map = Map.get_instance();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                root2.getChildren().add(map);

            }
        });
    }*/
}