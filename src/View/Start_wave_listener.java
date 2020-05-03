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
        if (launched) return;
        Thread t = new Thread(Game.get_instance());
        t.start();
        launched = true;
    }
}
