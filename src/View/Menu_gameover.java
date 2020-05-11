package View;

import Controller.Menu_buttons_listener;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.*;

public class Menu_gameover extends Parent {
    private Image image;

    public Menu_gameover(Stage theStage, String message){
        if (message.equals("gameover")) {
            try {
                image = new Image(new FileInputStream("Assets/gameover1.jpg"), theStage.getWidth(), theStage.getHeight(), false, false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (message.equals("wingame")) {
            try{
                image = new Image(new FileInputStream("Assets/win1.png"), theStage.getWidth(), theStage.getHeight(), false, false);
            } catch(FileNotFoundException e){
                e.printStackTrace();
            }

        }
        ImageView imageView = new ImageView(image);
        Button button_return = Menu.new_button("Home", 810, 550, 120, 30, "-fx-background-color:  #2E8B57");
        button_return.setOnMouseClicked(new Menu_buttons_listener(theStage, "menu"));
        this.getChildren().addAll(imageView, button_return);
    }

    public static void start_gameover(Stage theStage, String message){
        Group root = new Group();
        Menu_gameover menu_over = new Menu_gameover(theStage, message);
        root.getChildren().add(menu_over);
        Scene scene = new Scene(root, theStage.getWidth(), theStage.getHeight(), Color.LIGHTGRAY);
        theStage.setScene(scene);
        theStage.show();
    }
}
