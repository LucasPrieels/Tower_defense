package Controller;


import Model.Board;
import Model.Game;
import Model.Level;
import View.Main;
import View.Map;
import View.Menu;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import kuusisto.tinysound.TinySound;

public class Menu_buttons_listener implements EventHandler<MouseEvent> {
    private String message;
    private Stage stage;
    private static final Object key = new Object();

    public Menu_buttons_listener(Stage stage, String message){
        this.message = message;
        this.stage = stage;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        Menu.sound();
        synchronized (key) {
            if (message == "exit") {
                Game.get_instance().stop_threads();
                TinySound.shutdown();
                stage.close();
            } else if (message == "menu") {
                Launch_manager.save_data();
                Game.get_instance().stop_threads();

                Game.set_instance(null);
                Level.set_instance(null);
                Board.set_instance(null);
                Map.set_instance(null);
;
                Main.start_static(stage);
            }
        }
    }
}
