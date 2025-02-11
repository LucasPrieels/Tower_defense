package Controller;

import Model.*;
import View.Menu;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

public class Shop_listener implements EventHandler<MouseEvent>, Runnable, Serializable {
    private String message;
    private Canvas canvas;
    public static final Object key = new Object();

    public Shop_listener(String message, Canvas canvas){
        this.message = message;
        this.canvas = canvas;
    }

    public void handle(MouseEvent mouseEvent) {
        Menu.sound();
        message();
    }

    private void message(){
        Thread thread_message = new Thread(this);
        Game.get_instance().add_thread(thread_message);
        thread_message.start();
    }

    public void run(){
        ArrayList<Double> pos_x_asteroid = new ArrayList<>(), pos_y_asteroid = new ArrayList<>();
        synchronized (key) {
            for (Asteroid asteroid : Board.get_instance().get_asteroids()) {
                pos_x_asteroid.add(asteroid.get_pos_x());
                pos_y_asteroid.add(asteroid.get_pos_y());
            }
        }
        Tower_listener towerListener = new Tower_listener(pos_x_asteroid, pos_y_asteroid, message);
        canvas.setOnMouseClicked(towerListener); // Each time we click on the canvas we launch the handle of TowerListener

        if(message.equals("Classic_tower") || message.equals("Freezing_tower") || message.equals("Factory_tower")){
            Platform.runLater( () -> Message.set_const_message("Click on an asteroid"));
        }
        else if(message.equals("Upgrade_tower") || message.equals("Destroy_tower")){
            if(message.equals("Upgrade_tower")) {
                Platform.runLater(()->{
                    Thread thread_tower_message = new Thread(new Upgrade_tower_listener(canvas));
                    Game.get_instance().add_thread(thread_tower_message);
                    thread_tower_message.start();
                });
            }
                Platform.runLater( () -> Message.set_const_message("Click on a tower"));
        }
        Platform.runLater(Update_manager::update_window); // So that the new tower is shown on the screen even if the first wave is not yet running
    }
}