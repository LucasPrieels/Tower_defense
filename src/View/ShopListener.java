package View;

import Model.Asteroid;
import Model.Board;
import Model.Game;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;

public class ShopListener implements EventHandler<MouseEvent>, Runnable, Serializable {
    private GraphicsContext gc;
    private String message;
    private Canvas canvas;
    public static final Object key = new Object();

    public ShopListener(GraphicsContext gc, String message, Canvas canvas){
        this.gc = gc;
        this.message = message;
        this.canvas = canvas;
    }

    public void handle(MouseEvent mouseEvent) {
        Menu.sound();
        message(gc);
    }

    private void message(GraphicsContext gc){
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
        TowerListener towerListener = new TowerListener(canvas, pos_x_asteroid, pos_y_asteroid, message);
        canvas.setOnMouseClicked(towerListener);
        if(message == "Classic_tower" || message == "Freezing_tower" || message == "Factory_tower"){
            Platform.runLater( () -> {
                Map.get_instance().set_const_message("Cliquez sur un astéroïde");
            });
        }
        else if(message == "Upgrade_tower"){
            Platform.runLater( () -> {
                Map.get_instance().set_const_message("Cliquez sur une tour");
            });
        }
        Platform.runLater( () -> {
            //System.out.println("Updating");
            try {
                Controller.Update_manager.get_instance().update_window();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}