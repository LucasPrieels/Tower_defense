package View;

import Model.Save;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.io.*;

public class Menu_gameover extends Parent {
    private Stage theStage;
    private Image image;

    public Menu_gameover(Stage theStage){

           //gameover1.jpg
          //win1.png
        try{
            image = new Image(new FileInputStream("Assets/gameover1.jpg"), theStage.getWidth(), theStage.getHeight(), false, false);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        this.theStage = theStage;



        Button button_return = new_button("Home", 810, 550, 120, 30, "-fx-background-color:  #2E8B57");
        button_return.setOnMouseClicked(new Menu_buttons_listener(theStage, "menu"));

        this.getChildren().addAll(imageView, button_return);

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
