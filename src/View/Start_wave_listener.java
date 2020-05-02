package View;

import Model.Level;
import Model.Wave;
import Model.Game;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Start_wave_listener implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent mouseEvent) {
        Thread t = new Thread(Game.get_instance());
        t.start();
    }
}
