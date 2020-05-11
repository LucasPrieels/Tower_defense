package Controller;


import Model.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Upgrade_tower_listener implements EventHandler<MouseEvent>, Runnable{
    private String message;
    private Canvas canvas;
    private static final Object key = new Object();
    private MouseEvent mouseEvent;

    public Upgrade_tower_listener(Canvas canvas){ this.canvas = canvas;}

    public void handle(MouseEvent mouseEvent) { // Shows information about a type of tower if the mouse of the user stays upon the button of this type
        this.mouseEvent = mouseEvent;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for(Tower tower: Board.get_instance().get_towers()){
            if (tower.get_curr_level() == tower.get_max_level()) continue;
            message = "Price : " + tower.get_price_upgrade() +
                    "\nNPCs killed : " + tower.get_npc_destroyed_needed();
            gc.setFont(new Font("Arial", 12));
            gc.setFill(Color.WHITE);
            gc.fillText(message, tower.get_asteroid().get_pos_x()* Update_manager.get_fact_x(), tower.get_asteroid().get_pos_y()*Update_manager.get_fact_y()-20);
        }
    }

    @Override
    public void run() {
        double t = 0;
        while (t < 3000) {
            try {
                synchronized (key) {
                    Platform.runLater(() -> handle(mouseEvent));
                    t += 50.0 / Game.get_instance().get_fps();
                }
                Thread.sleep((long) (50.0 / Game.get_instance().get_fps()));
            } catch (InterruptedException e) {
                return;
            }
        }
        message = " ";
    }
}
