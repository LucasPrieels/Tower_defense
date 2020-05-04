package View;

import Model.Level;
import Model.Wave;
import Model.Game;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.Serializable;

public class Start_wave_listener implements EventHandler<MouseEvent> {
    boolean launched = false;
    @Override
    public void handle(MouseEvent mouseEvent) {
        if (launched){
            Menu.save_data();
            return;
        }
        Thread t = new Thread(Game.get_instance());
        Game.get_instance().add_thread(t);
        t.start();
        launched = true;
    }
}
