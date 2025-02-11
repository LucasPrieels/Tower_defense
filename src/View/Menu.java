package View;

import Model.Game;
import Controller.Launch_manager;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;
import java.io.*;
import static Controller.Launch_manager.load_data;


public class Menu extends Parent implements Serializable{
    private Image image;
    private int level;

    public Menu(Stage theStage){
        try{
            image = new Image(new FileInputStream("Assets/menu.jpg"), theStage.getWidth(), theStage.getHeight(), false, false);
        } catch(FileNotFoundException e){ e.printStackTrace();}
        ImageView imageView = new ImageView(image);

        Button button_start = new_button("Start", 100, 100, 120, 30, "-fx-background-color:  #ffffff");
        Button button_rules = new_button("Rules", 100, 200, 120, 30, "-fx-background-color:  #ffffff");
        Button button_load = new_button("Load last game", 100, 300, 120, 30, "-fx-background-color:  #ffffff");
        Button button_help = new_button("Help", 100, 400, 120, 30, "-fx-background-color:  #ffffff");
        Button button_exit = new_button("Exit", 100, 500, 120, 30, "-fx-background-color:  #ffffff");

        Group vbox = new Group();
        this.getChildren().addAll(imageView, button_rules, button_help, button_start, button_exit, button_load, vbox);
         
        button_exit.setOnMouseClicked(mouseEvent -> {
            sound();
            TinySound.shutdown();
            theStage.close();
        });

        button_rules.setOnAction(actionEvent -> {
            sound();
            String text = "\n\n" +
                    " " +
                    "Help aliens protect themselves from Earthlings searching for new planets. To do this,\n " +
                    "you must eliminate their ships.  Use the towers that you can place on each planet\n " +
                    "present on the map, but beware, these towers are not free of charge.  You have three\n" +
                    " types of towers at your disposal: banks, classic towers and ice towers.  The banks \n" +
                    "will start producing money as soon as you place them on a planet.  Of course, you \n" +
                    "start the game with a certain amount of money in order to buy a few towers. The \n" +
                    "classic towers throw ammunition at the land ships and the ice towers freeze them.  \n" +
                    "This has the effect of slowing them down for a while.  Once all your towers have been\n" +
                    " placed, you can launch the first wave of NPCs.  Each level consists of three waves.  \n" +
                    "You can improve the efficiency of these towers twice for a certain amount of money \n" +
                    "as soon as you have killed enough NPCs.  If an NPC manages to make it all the way \n" +
                    "across the map without you killing it, your score will decrease.  If you reach the \n" +
                    "end of all 3 waves with a score above 0, you will advance to the next level hoping to \n" +
                    "reach the end of level 3 and save the space.  On the other hand, if you reach a score \n" +
                    "of 0, your mission will have failed and space will be invaded by humans.\n\n" +
                    " ";
            vbox_text(vbox, text);
        });
         
        button_help.setOnAction(actionEvent -> {
            sound();
            String text = "\n\n" +
                    " " +
                    "Click on the \"rules\" button to familiarize yourself with the rules of the game.\n\n" +
                    "Once the rules are understood, the \"start\" button will launch the game.  You will\n " +
                    "then have the choice between the 3 available levels.\n\n" +
                    "If you have to leave a game in progress, click on the \"Load last game\" button the \n" +
                    "next time you play in order to resume the game from where you left it.    \n\n" +
                    "The \"exit\" button exits the game.  \n\n" +
                    "          ";
            vbox_text(vbox, text);
        });

        button_load.setOnAction(actionEvent -> {
            sound();
            level = load_data();
            if (level == -1){
                String text = "No game has been saved.";
                vbox_text(vbox, text);
                return;
            }
            Launch_manager.launch_game(theStage);
        });

        button_start.setOnAction(actionEvent -> {
            sound();
            vbox.getChildren().clear();
            vbox.setLayoutX(400);
            vbox.setLayoutY(300);

            HBox menulevels = new HBox(10);
            menulevels.setPadding(new Insets(15));
            menulevels.setSpacing(10);
            Label label_level = new Label("Select Level : ");
            label_level.setFont(Font.font("Cambria", 20));
            label_level.setTextFill(Color.web("#000000"));
            menulevels.getChildren().add(label_level);

            Button level1 = new Button("Level 1");
            Button level2 = new Button("Level 2");
            Button level3 = new Button("Level 3");

            menulevels.getChildren().addAll(level1, level2, level3);

            BorderPane bp = new BorderPane();
            bp.setPadding(new Insets(5));
            bp.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
            bp.setCenter(menulevels);

            BorderPane border = new BorderPane();
            border.getChildren().clear();
            border.setCenter(bp);

            vbox.getChildren().add(border);

            level1.setOnMouseClicked(mouseEvent -> {
                Game.init(1);
                sound();
                Launch_manager.launch_game(theStage);
            });

            level2.setOnMouseClicked(mouseEvent -> {
                Game.init(2);
                sound();
                Launch_manager.launch_game(theStage);
            });

            level3.setOnMouseClicked(mouseEvent -> {
                Game.init(3);
                sound();
                Launch_manager.launch_game(theStage);
            });
        });
    }

    private void vbox_text(Group vbox, String text){ // Shows text in vbox
        vbox.getChildren().clear();
        vbox.setLayoutX(400);
        vbox.setLayoutY(100);

        Label label_help = new Label(text);
        label_help.setFont(Font.font("Cambria", 20));
        label_help.setTextFill(Color.web("#000000"));
        label_help.setWrapText(true);

        BorderPane canvasBorderPane = new BorderPane();
        canvasBorderPane.setPadding(new Insets(5));
        canvasBorderPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
        canvasBorderPane.setCenter(label_help);

        BorderPane border = new BorderPane();
        border.setCenter(canvasBorderPane);
        border.setPadding(new Insets(5));
        border.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), Insets.EMPTY)));

        vbox.getChildren().add(border);
    }

    public static Button new_button(String text, int x, int y, int width, int height, String color){ // Creates a new button
        Button button = new Button(text);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        button.setStyle(color);
        return button;
    }

    public static void sound(){
        Sound button_snd = TinySound.loadSound("Songs/button.wav"); // Makes the sound when clicked on a button
        button_snd.play();
    }

    public static void bad_sound(){
        Sound bad_button_snd = TinySound.loadSound("Songs/bad_button.wav"); // Makes the sound when clicked on a button and there is an error
        bad_button_snd.play();
    }
}