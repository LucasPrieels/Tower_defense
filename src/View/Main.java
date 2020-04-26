package View;

import Model.Game;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Main extends Application {

    public void start(Stage theStage) throws FileNotFoundException {
        theStage.setTitle("Nom du jeu");
        Group root = new Group();
        Scene scene = new Scene(root, theStage.getWidth(), theStage.getHeight(), Color.LIGHTGRAY);
        //Canvas canvas = new Canvas(1920,1080);

        Menu menu = new Menu();
        root.getChildren().add(menu);





        theStage.setScene(scene);
        theStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
