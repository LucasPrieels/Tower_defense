package Controller;

import Model.*;
import View.Menu;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Start_wave_listener implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent mouseEvent) { // Called when the first wave is launched
        Menu.sound();
        Message.set_temp_message("Game launched");
        Update_manager.delete_start_wave_controller(); // Deletes the icon of the start_wave button

        for (Tower tower: Board.get_instance().get_towers()){
            Thread thread_tower = new Thread(tower);
            Game.get_instance().add_thread(thread_tower);
            System.out.println("Starting thread");
            thread_tower.start(); // If the game has been relaunched from a save, we need to reactivate towers already placed
        }

        Thread thread_game = new Thread(Game.get_instance()); // Launching the Game
        Game.get_instance().add_thread(thread_game);
        thread_game.start();
    }
}
