package sample;


import View.Menu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        Menu menu = new Menu();
        primaryStage =  menu.getMainStage();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Tower defense");

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
