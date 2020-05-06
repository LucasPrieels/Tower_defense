package View;

import Model.Game;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kuusisto.tinysound.TinySound;

import java.awt.*;
import java.io.FileNotFoundException;

public class Main extends Application {
    private static Stage theStage;

    public static void start_static(Stage theStage){
        TinySound.init();
        Group root = new Group();
        Scene scene = new Scene(root, theStage.getWidth(), theStage.getHeight(), Color.LIGHTGRAY);
        //Canvas canvas = new Canvas(1920,1080);

        Menu menu = new Menu(theStage);
        root.getChildren().add(menu);

        Main.theStage.setScene(scene);
        Main.theStage.show();
    }

    public void start(Stage theStage){
        Main.theStage = theStage;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Main.theStage.setWidth(screenSize.getWidth());
        Main.theStage.setHeight(screenSize.getHeight());
        Main.theStage.setTitle("Nom du jeu");
        start_static(theStage);
    }

    public static void main() {
        Game game = Game.get_instance();
        launch();
    }
}
