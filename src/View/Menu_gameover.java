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
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.io.*;

public class Menu_gameover extends Parent {
    private Stage theStage;
    private Image image;

    private String message1 = "gameover";
    private String message2 = "wingame";


    public Menu_gameover(Stage theStage,String message){

           //gameover1.jpg
          //win1.png

        if (message == message1) {
            try {
                image = new Image(new FileInputStream("Assets/gameover1.jpg"), theStage.getWidth(), theStage.getHeight(), false, false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (message==message2) {
            try{
                image = new Image(new FileInputStream("Assets/win1.png"), theStage.getWidth(), theStage.getHeight(), false, false);
            } catch(FileNotFoundException e){
                e.printStackTrace();
            }

        }




        ImageView imageView = new ImageView(image);
        this.theStage = theStage;



        Button button_return = new_button("Home", 810, 550, 120, 30, "-fx-background-color:  #2E8B57");
        button_return.setOnMouseClicked(new Menu_buttons_listener(theStage, "menu"));

        this.getChildren().addAll(imageView, button_return);

    }





    //mettre les fonctions




    public static void start_gameover(Stage theStage,String message){
        Group root = new Group();
        Menu_gameover menu_over = new Menu_gameover(theStage,message);
        root.getChildren().add(menu_over);
        Scene scene = new Scene(root, theStage.getWidth(), theStage.getHeight(), Color.LIGHTGRAY);
        theStage.setScene(scene);
        theStage.show();
    }



    private Button new_button(String text, int x, int y, int width, int height, String color) {

        Button button = new Button(text);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        button.setStyle(color);
        return button;



    }

    public static void sound(){
        Sound button_snd = TinySound.loadSound("Songs/button.wav");
        button_snd.play();
    }
}
