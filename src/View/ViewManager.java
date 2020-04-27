package View;

import Model.Map;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import Model.*;


import java.util.ArrayList;
import java.util.List;


public class ViewManager {
    private static final double WIDTH = 1000;
    private static final double HEIGHT = 600;
    private static  final double BUTTONS_ALIGNMENT_X = 100;
    private static final double BUTTONS_ALIGNMENT_Y_START = 100;


    private AnchorPane mainPane;
    private PanelMenuSubScene playsubScene;
    private PanelMenuSubScene scoresSubScene;
    private PanelMenuSubScene helpsubScene;
    private PanelMenuSubScene lvlModeSubScene;
    private PanelMenuSubScene subSceneToHide;
    private Scene mainScene;
    private Stage mainStage;
    private List<MenuButton> buttons;

    private List<DifficultyPicker> diffList;
    private int choosenDifficulty;
    private List<MapPicker> mapList;
    private Model.Map choosenMap;



    public ViewManager(){
        buttons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane,WIDTH,HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createButtons();
        createBackground();
        createLabel();
        createSubScenes();





    }



    //Subscene chosen

    private void showSubScene(PanelMenuSubScene subScene){
        if (subSceneToHide != null){
            subSceneToHide.moveSubScene();
        }

        subScene.moveSubScene();
        subSceneToHide= subScene;

    }


    /**
     * Create subScenes
     */
    private void createSubScenes(){
        createPlaySubScene();

        createHelpSubScene();

        createLvlModeSubScene();
    }

    /**
     * create help Subscene
     */
    private void createHelpSubScene(){
        helpsubScene = new PanelMenuSubScene();
        mainPane.getChildren().add(helpsubScene);
        InfoLabel message = new InfoLabel("help");
        message.setLayoutX(120);
        message.setLayoutY(20);
        String text = "Choose a Map and start the game \non the start tab.\n\n" +
                "Check your ranking on score tab.\n\n" +
                "Change the difficulty on lvl \nmode tab.\n\n" +
                "Press the exit button if you want \nto quit the game.";
        TextLabel textLabel = new TextLabel(text,17);
        textLabel.setLayoutX(40);
        textLabel.setLayoutY(70);
        helpsubScene.getPane().getChildren().addAll(message,textLabel);
    }



    /**
     * create difficulty level SubScene
     */
    private void createLvlModeSubScene(){
        lvlModeSubScene = new PanelMenuSubScene();
        mainPane.getChildren().add(lvlModeSubScene);

        InfoLabel message = new InfoLabel("choose difficulty lvl");
        message.setLayoutX(120);
        message.setLayoutY(20);
        lvlModeSubScene.getPane().getChildren().add(message);
        lvlModeSubScene.getPane().getChildren().add(createDifficultyToPick());
    }




    /**
     * create play SubScene
     */

    private void createPlaySubScene(){
        playsubScene = new PanelMenuSubScene();
        mainPane.getChildren().add(playsubScene);

        InfoLabel message = new InfoLabel("Choose a Map");
        message.setLayoutX(120);
        message.setLayoutY(20);

        playsubScene.getPane().getChildren().add(message);
        playsubScene.getPane().getChildren().add(createMapToPick());
        playsubScene.getPane().getChildren().add(createStartButton());
    }


    /**
     * create start of the game button
     */
    private MenuButton createStartButton(){
        MenuButton startButton = new MenuButton("START","red");
        startButton.setLayoutX(365);
        startButton.setLayoutY(300);
    /*startButton.setOnAction(e->{
     //ecrire ici  pour lancer le jeu
        }
    });*/
        return startButton;
    }

    private HBox createMapToPick(){
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setLayoutX(46);
        hBox.setLayoutY(100);
        mapList = new ArrayList<>();
        for(Model.Map map : Map.values()){
            MapPicker mapPicker = new MapPicker(map);
            mapList.add(mapPicker);
            hBox.getChildren().add(mapPicker);
            mapPicker.setOnMouseClicked(e-> {
                for(MapPicker mapa: mapList){
                    mapa.setIsChoosen(false);
                }
                mapPicker.setIsChoosen(true);
                choosenMap = mapPicker.getMap();
            });
        }
        return hBox;
    }




    private HBox createDifficultyToPick(){
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setLayoutX(180);
        hBox.setLayoutY(100);
        diffList = new ArrayList<>();
        int[] difficulties = new int[]{1,2,3};
        for(int number:difficulties){
            DifficultyPicker difficultyPicker = new DifficultyPicker(number);
            if(number==3){
                difficultyPicker.setIsChoosen(true);
                choosenDifficulty = difficultyPicker.getNumber();
            }
            diffList.add(difficultyPicker);
            hBox.getChildren().add(difficultyPicker);
            difficultyPicker.setOnMouseClicked(e-> {
                for(DifficultyPicker diffPicker : diffList){
                    diffPicker.setIsChoosen(false);
                }
                difficultyPicker.setIsChoosen(true);
                choosenDifficulty = difficultyPicker.getNumber();
            });
        }
        return hBox;
    }


    /**
     * create buttons
     */

    private void createButtons(){
        createPlayButton();
        createScoresButton();
        createHelpButton();
        createDifficultyButton();
        createExitButton();
    }

    /*
     *create play button
     */
    private void createPlayButton(){
        MenuButton button = new MenuButton("PLAY","green");
        addButton(button);
        button.setOnAction(e-> {
            showSubScene(playsubScene);
        });
    }

    /**
     * create scores button
     */
    private void createScoresButton(){
        MenuButton button = new MenuButton("SCORES","blue");
        addButton(button);

    }

    /**
     * create help button
     */
    private void createHelpButton(){
        MenuButton button = new MenuButton("HELP","red");
        addButton(button);
        button.setOnAction(e-> {
            showSubScene(helpsubScene);
        });
    }

    /**
     * create level mode button
     */
    private void createDifficultyButton(){
        MenuButton button = new MenuButton("LVL MODE","yellow");
        addButton(button);
        button.setOnAction(e-> {
            showSubScene(lvlModeSubScene);
        });
    }

    /**
     * create exit button
     */
    private void createExitButton(){
        MenuButton button = new MenuButton("Exit","yellow");
        addButton(button);
        button.setOnAction(e-> {
            mainStage.close();
        });
    }

    /**
     * method to add buttons to mainPane
     */
    private void addButton(MenuButton button){
        button.setLayoutX(BUTTONS_ALIGNMENT_X);
        button.setLayoutY(BUTTONS_ALIGNMENT_Y_START+ buttons.size()*100);
        buttons.add(button);
        mainPane.getChildren().add(button);
    }


    /**
     * creates background
     */
    private void createBackground(){

        String imageURL = "/View/menu.jpg";

        Image backgroundImage = new Image(imageURL);
        BackgroundImage background =
                new BackgroundImage(backgroundImage,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        null);
        mainPane.setBackground(new Background(background));
    }

    /*
    *create the logo of the game
     */
    private void createLabel(){
        String text = "Tower Defense";
        TextLabel label = new TextLabel(text,50);
        label.setLayoutX(470);
        label.setLayoutY(20);
        label.setOnMouseEntered(e-> label.setEffect(new DropShadow()));
        label.setOnMouseExited(e -> label.setEffect(null));
        mainPane.getChildren().add(label);
    }

    public Stage getMainStage() { return mainStage; }


}
























