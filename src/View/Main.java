package View;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kuusisto.tinysound.TinySound;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main extends Application {
    private static Stage theStage;

    public static void start_static(Stage theStage){
        Group root = new Group();
        Scene scene = new Scene(root, theStage.getWidth(), theStage.getHeight(), Color.LIGHTGRAY);

        View.Menu menu = new Menu(theStage);
        root.getChildren().add(menu);

        Main.theStage.setScene(scene);
        Main.theStage.show();
    }

    public void start(Stage theStage){
        Main.theStage = theStage;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // The game will automatically be in full screen
        Main.theStage.setWidth(screenSize.getWidth());
        Main.theStage.setHeight(screenSize.getHeight());
        Main.theStage.setTitle("Space Defense");
        TinySound.init();
        try{
            new BufferedReader(new FileReader("src/Songs/music.wav")); // Just to throw a FileNotFoundException
            TinySound.loadMusic("Songs/music.wav").play(true, 0.5);
        } catch (FileNotFoundException e){
            System.out.println("Le fichier avec la musique n'a pas été trouvé, le jeu se lance sans.");
        }
        start_static(theStage);
    }

    public static void main(){ launch();}
}
