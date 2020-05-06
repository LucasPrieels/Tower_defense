package View;

import Model.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Start_wave_listener implements EventHandler<MouseEvent> {
    boolean launched = false;
    @Override
    public void handle(MouseEvent mouseEvent) {
        if (launched){
            Menu.bad_sound();
            return;
        }
        Menu.sound();
        for (Tower tower: Board.get_instance().get_towers()){
            Thread thread_tower = new Thread(tower);
            Game.get_instance().add_thread(thread_tower);
            thread_tower.start(); // Si on vient de relancer le jeu d'après une sauvegarde, il faut réactiver les tours déjà placées
        }
        Thread t = new Thread(Game.get_instance());
        Game.get_instance().add_thread(t);
        t.start();
        launched = true;
    }
}
