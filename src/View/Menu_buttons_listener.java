package View;


import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Menu_buttons_listener implements EventHandler<MouseEvent> {
    private String message;
    private Stage stage;

    public Menu_buttons_listener(Stage stage, String message){
        this.message = message;
        this.stage = stage;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        //if(message == "menu"){ }
        //else if(message =="play"){}
        if(message == "exit"){
            stage.close();
        }
    }
}
