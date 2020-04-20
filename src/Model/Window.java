package sample;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Window extends Application {

   /* private Map map ;
    private Menu menu;
*/

    @Override
    public void start(Stage stage) throws Exception {


        //Create the GridPane
        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setHgap(25);
        root.setVgap(15);

        Label monlabel = new Label("Enter your user name");
        root.add(monlabel,0,0,2,1)   ;


        //Text composant
        Label labelUserName = new Label("User Name:");
        TextField fieldUserName = new TextField();

        //Button
        Button loginButton = new Button("Login ");

        //Mise en place sur GridPane
        GridPane.setHalignment(labelUserName, HPos.RIGHT);
        root.add(labelUserName,0,1);


        GridPane.setHalignment(fieldUserName, HPos.LEFT);
        root.add(fieldUserName, 1, 1);
        GridPane.setHalignment(loginButton, HPos.RIGHT);
        root.add(loginButton, 1, 3);



        Scene scene = new Scene(root, 300, 300, Color.BLUE);

        stage.setTitle("Window1 ");
        stage.setScene(scene);
        stage.show();



    }


    public void display(){




    }

   public void gameover(){




   }


   public void win(){




   }



    public static void main(String[] args) {
        launch(args);
    }

}
