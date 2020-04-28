package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Menu  {

    private Stage mainStage;
    private AnchorPane mainPane;
    private Scene mainScene;
    private VBox VboxButtons;

    private Button StartButton;
    private Button HelpButton;
    private Button LevelButton;
    private Button ExitButton;

    public  Menu(){

         mainStage = new Stage();
         mainPane = new AnchorPane();
         mainScene = new Scene(mainPane,1000,600);
         VboxButtons = new VBox(20);

         mainPane.getChildren().add(VboxButtons);
         VboxButtons.setAlignment(Pos.CENTER);
         VboxButtons.setPadding(new Insets(15,15,15,15));
         mainStage.setScene(mainScene);

        createbackground();
        createButtons();

    }

    private void createButtons(){

         StartButton = new Button("Start");
         HelpButton = new Button("Help");
         LevelButton = new Button("Level");
         ExitButton = new Button("Exit");

       addButton(StartButton);
       addButton(HelpButton);
       addButton(LevelButton);
       addButton(ExitButton);

        //action
        actionbuttons();
        //



    }




    private void actionbuttons(){
        ExitButton.setOnAction(e-> {
            mainStage.close();
        });

        StartButton.setOnAction(e-> {

            Stage theStage2 = new Stage();
            Group root2 = new Group();
            theStage2.setScene(new Scene(root2, 1920, 1080));
            theStage2.show();

            View.Map map = null;

            try {
                View.Map.init(theStage2);
                map = View.Map.get_instance();
            } catch (FileNotFoundException a) {
                a.printStackTrace();
            }
            mainStage.close();
            root2.getChildren().add(map);

        });
    }

private void addButton(Button button){
        button.setLayoutX(100);
        button.setLayoutY(100);
        VboxButtons.getChildren().add(button);


}



private void createbackground(){
    Image backgroundImage = new Image("/View/menu.jpg");
    BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
    mainPane.setBackground(new Background(background));


}

public Stage getMainStage() { return mainStage; }

}


/*

    private Stage theStage;

    public Menu() throws FileNotFoundException {





        //Mise en page de l'image

        Image image = new Image(new FileInputStream("Images/menu.jpg"));
        ImageView imageView = new ImageView(image);



        Button button = new Button("Start Game");


        this.getChildren().addAll(imageView,button);

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage theStage2 = new Stage();
                Group root2 = new Group();
                theStage2.setScene(new Scene(root2,1920,1080));
                theStage2.show();

                View.Map map = null;

                try {
                    Map.init(theStage2);
                    map = Map.get_instance();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                root2.getChildren().add(map);

            }
        });
    }*/