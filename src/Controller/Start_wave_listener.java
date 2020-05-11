package Controller;

import Model.*;
import View.Menu;
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
        Message.set_temp_message("Game launched");
        Update_manager.delete_start_wave_controller(); // Deletes the icon of the start_wave button

        for (Tower tower: Board.get_instance().get_towers()){
            Thread thread_tower = new Thread(tower);
            Game.get_instance().add_thread(thread_tower);
            thread_tower.start(); // Si on vient de relancer le jeu d'après une sauvegarde, il faut réactiver les tours déjà placées
        }

        Thread thread_game = new Thread(Game.get_instance());
        Game.get_instance().add_thread(thread_game);
        thread_game.start();
        launched = true;
    }
}
