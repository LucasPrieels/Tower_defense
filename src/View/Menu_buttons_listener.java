package View;


import Model.Board;
import Model.Game;
import Model.Level;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import kuusisto.tinysound.TinySound;

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
        Menu.sound();
        if (message == "exit"){
            Game.get_instance().stop_threads();
            TinySound.shutdown();
            stage.close();
        }
        else if (message == "menu"){
            Menu.save_data();
            Game.get_instance().stop_threads();

            Game.set_instance(null);
            Level.set_instance(null);
            Board.set_instance(null);
            Map.set_instance(null);

            Game game = Game.get_instance();
            Main.start_static(stage);
        }
    }
}
