package View;

import Model.Game;

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

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Menu extends Parent {

    private Stage theStage;



    private Button level1;
    private Button level2;
    private Button level3;







    View.Map map = null;

   private Image image;
   private ImageView imageView;


    private Button button_rules;
    private Button button_help;
    private Button button_level;
    private Button button_exit;

    private Group vbox;



    private  static String texte;
 //rules
    private  Label label;
    private BorderPane canvas;
//help
    private static String text;
    private Label label_help;
    private BorderPane canvasBorderPane;



    //play
    private Group root_play;
    private int level = 1; // Niveau sélectionné si on n'en sélectionne pas d'autre


    // COLOR
    BorderPane border ;





    public Menu() throws FileNotFoundException {













         image = new Image(new FileInputStream("Images/menu.jpg"));
         imageView = new ImageView(image);
        theStage = Main.get_stage();



        button_rules = new Button("Rules");
        button_rules.setLayoutX(100);
        button_rules.setLayoutY(100);
        button_rules.setPrefWidth(190);
        button_rules.setPrefHeight(49);
        button_rules.setStyle("-fx-background-color:  #DC143C");


        button_help = new Button("Help");
        button_help.setLayoutX(100);
        button_help.setLayoutY(200);
        button_help.setPrefWidth(190);
        button_help.setPrefHeight(49);
        button_help.setStyle("-fx-background-color:  #7fff00");
        // #7cfc00 (green)
        //#ffd700 (gold)
        //#ffa500 (orange)
        //#7fff00


        button_level = new Button("Level");
        button_level.setLayoutX(100);
        button_level.setLayoutY(300);
        button_level.setPrefHeight(49);
        button_level.setPrefWidth(190);
        button_level.setStyle("-fx-background-color:  #ffd700");

        button_exit = new Button("Exit");
        button_exit.setLayoutX(100);
        button_exit.setLayoutY(400);
        button_exit.setPrefWidth(190);
        button_exit.setPrefHeight(49);
        button_exit.setStyle("-fx-background-color:  #ffd700");



         border = new BorderPane();
        border.setPadding(new Insets(20));
        border.setBackground(new Background(new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, new CornerRadii(0), Insets.EMPTY)));



        vbox = new Group();


        this.getChildren().addAll(imageView,button_rules,button_help,button_level,button_exit,vbox);







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


                 texte =  "Tower Defense :\n\n"+"Player start with an appropriate amount of currency and is able to obtain\n more money during game by defeating waves of  enemies and also by\n building a Factory tower."+"There are 3  waves on each level  and  each wave\n of enemies spawns with a short pause in between waves.\n\n The player  buy towers through currency pieces and than he should place\n the tower on a asteroid location." +
                        " Towers can be upgraded at least three\ntimes to have more range, or damage.\n \n" +
                        " The player win the  game when his score is higher than  0 or lose  when his\n  score is  0.\n" +
                        "\n"+"\n"+"\n"+"\n";

                 label = new Label(texte);
                label.setFont(Font.font("Cambria", 20));
                label.setTextFill(Color.web("#000000"));
                label.setWrapText(true);

                canvas = new BorderPane();
                canvas.setPadding(new Insets(10));
                canvas.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
                canvas.setCenter(label);


                vbox.setVisible(true);
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
                                        canvasBorderPane.setPadding(new Insets(10));
                                        canvasBorderPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
                                        canvasBorderPane.setCenter(label_help);

                                        border.getChildren().clear();
                                        border.setCenter(canvasBorderPane);




                                        vbox.setVisible(true);
                                        //vbox.getChildren().add(canvasBorderPane);
                                        vbox.getChildren().add(border);



                                    }
                                }
        );






        button_level.setOnAction(new EventHandler<ActionEvent>() {
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
                canvasBorderPane.setPadding(new Insets(10));
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






        button_exit.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button_exit.setEffect(new DropShadow());
            }
        });

        button_level.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button_level.setEffect(new DropShadow());
            }
        });

        button_help.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button_help.setEffect(new DropShadow());
            }
        });
    }
    public void launch_game(){
        root_play = new Group();
        //theStage.setScene(new Scene(root2,1920,1080));
        theStage.setScene(new Scene(root_play,1000,700));
        theStage.show();

        //View.Map map = null;



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
}