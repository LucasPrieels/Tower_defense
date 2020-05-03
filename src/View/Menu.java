package View;

import Model.Board;
import Model.Game;
import Model.Save;
import Model.Tower;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.*;


public class Menu extends Parent implements Serializable{
    private Stage theStage;
    private Button level1, level2, level3, button_rules, button_help, button_start, button_exit, button_load;
    private Image image;
    private ImageView imageView;
    private Group vbox;
    //rules
    private Label label;
    private BorderPane canvas;
    //help
    private static String text;
    private Label label_help;
    private BorderPane canvasBorderPane;
    //play
    private Group root_play;
    private int level = 1; // Niveau sélectionné si on n'en sélectionne pas d'autre
    // COLOR
    BorderPane border;
    public Menu(Stage theStage) throws FileNotFoundException {
        image = new Image(new FileInputStream("Images/menu.jpg"));
        imageView = new ImageView(image);
        this.theStage = theStage;

        button_start = new Button("Start");
        button_start.setLayoutX(100);
        button_start.setLayoutY(100);
        button_start.setPrefWidth(120);
        button_start.setPrefHeight(30);
        button_start.setStyle("-fx-background-color:  #ffffff");

        button_rules = new Button("Rules");
        button_rules.setLayoutX(100);
        button_rules.setLayoutY(200);
        button_rules.setPrefWidth(120);
        button_rules.setPrefHeight(30);
        button_rules.setStyle("-fx-background-color:  #ffffff");

        button_load = new Button("Load last game");
        button_load.setLayoutX(100);
        button_load.setLayoutY(300);
        button_load.setPrefWidth(120);
        button_load.setPrefHeight(30);
        button_load.setStyle("-fx-background-color:  #ffffff");
         
        button_help = new Button("Help");
        button_help.setLayoutX(100);
        button_help.setLayoutY(400);
        button_help.setPrefWidth(120);
        button_help.setPrefHeight(30);
        button_help.setStyle("-fx-background-color:  #ffffff");
         
        button_exit = new Button("Exit");
        button_exit.setLayoutX(100);
        button_exit.setLayoutY(500);
        button_exit.setPrefWidth(120);
        button_exit.setPrefHeight(30);
        button_exit.setStyle("-fx-background-color:  #ffffff");

        border = new BorderPane();
        border.setPadding(new Insets(5));
        border.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), Insets.EMPTY)));
         
        vbox = new Group();
        this.getChildren().addAll(imageView, button_rules, button_help, button_start, button_exit, button_load, vbox);
         
        button_exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                theStage.close();
            }
        });

        button_rules.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent actionEvent) {
                vbox.getChildren().clear();
                vbox.setLayoutX(400);
                vbox.setLayoutY(100);
                String text =  "Tower Defense :\n\n"+"Player start with an appropriate amount of currency and is able to obtain\n more money during game by defeating waves of  enemies and also by\n building a Factory tower."+"There are 3  waves on each level  and  each wave\n of enemies spawns with a short pause in between waves.\n\n The player  buy towers through currency pieces and than he should place\n the tower on a asteroid location." +
                        " Towers can be upgraded at least three\ntimes to have more range, or damage.\n \n" +
                        " The player win the  game when his score is higher than  0 or lose  when his\n  score is  0.\n" +
                        "\n"+"\n"+"\n"+"\n";

                label = new Label(text);
                label.setFont(Font.font("Cambria", 20));
                label.setTextFill(Color.web("#000000"));
                label.setWrapText(true);

                canvas = new BorderPane();
                canvas.setPadding(new Insets(5));
                canvas.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
                canvas.setCenter(label);

                border.getChildren().clear();
                border.setCenter(canvas);
                vbox.getChildren().add(border); 
            }
        });
         
        button_help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                vbox.getChildren().clear();
                vbox.setLayoutX(400);
                vbox.setLayoutY(100);
                
                text = "HELP:\n\n"+"Press the  play button to start the game.\n\n" +
                        "Change the difficulty on level mode tab.\n\n" +
                        "Press the exit button if you want to quit the game."
                        +"                                                  \n\n "
                        + "                                                 \n\n"
                        + "                 ";
                label_help = new Label(text);
                label_help.setFont(Font.font("Cambria", 20));
                label_help.setTextFill(Color.web("#000000"));
                label_help.setWrapText(true);
                
                canvasBorderPane = new BorderPane();
                canvasBorderPane.setPadding(new Insets(5));
                canvasBorderPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
                canvasBorderPane.setCenter(label_help);
                
                border.getChildren().clear();
                border.setCenter(canvasBorderPane);

                vbox.getChildren().add(border); 
            }
        });

        button_load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                level = load_data();
                for (Tower tower: Board.get_instance().get_towers()){
                    Thread thread_tower = new Thread(tower);
                    thread_tower.start();
                }
                launch_game();
            }
        });

        button_start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
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
                
                vbox.setVisible(true);
                level1  = new Button("Level 1");
                level2  = new Button("Level 2");
                level3  = new Button("Level 3");

                menulevels.getChildren().addAll(level1,level2,level3);

                BorderPane canvasBorderPane = new BorderPane();
                canvasBorderPane.setPadding(new Insets(5));
                canvasBorderPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
                canvasBorderPane.setCenter(menulevels);

                border.getChildren().clear();
                border.setCenter(canvasBorderPane);

                //root.getChildren().add(canvasBorderPane);
                vbox.getChildren().add(border);
                
                level1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {level = 1; launch_game();}
                });

                level2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {level = 2; launch_game();}
                });

                level3.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {level = 3; launch_game();}
                });
            }
        });
    }

    public void launch_game(){
        root_play = new Group();
        //theStage.setScene(new Scene(root2,1920,1080));
        theStage.setScene(new Scene(root_play, theStage.getWidth(), theStage.getHeight()));
        theStage.show();
        View.Map map = null;

        try {
            Map.init(theStage, level);
            map = Map.get_instance();
            try{
                Controller.Update_manager.get_instance().update_window();
            } catch(FileNotFoundException e){}
            //Thread t = new Thread(Game.get_instance());
            //t.start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        root_play.getChildren().add(map);
    }

    public static void save_data(){
        try{
            System.out.println("Saving...");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("towerDefense.serial"));
            Save save = new Save();
            oos.writeObject(save);
            oos.flush();
            oos.close();
        } catch (Exception e) {e.printStackTrace();}
    }

    public static int load_data(){
        Save save = null;
        try{
            System.out.println("Loading...");
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("towerDefense.serial"));
            save = (Save) ois.readObject();
            save.init();
            ois.close();
        } catch (Exception e) {e.printStackTrace();}
        return save.get_level();
    }
}