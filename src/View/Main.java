package View;

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
        Scene scene = new Scene(root,1920,1080, Color.LIGHTGRAY);
        //Canvas canvas = new Canvas(1920,1080);

        View.Menu menu = new View.Menu();
        root.getChildren().add(menu);





        theStage.setScene(scene);
        theStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
