package View;


import Model.Board;
import Model.Game;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Start_wave_listener implements EventHandler<MouseEvent> {

    private static String order = "wait";

    @Override
    public void handle(MouseEvent mouseEvent) {
    }

    public static String get_order(){return order;}
}
