package View;


import Model.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Upgrade_tower_listener implements EventHandler<MouseEvent>, Runnable{
    private String message;
    private GraphicsContext gc;
    private Canvas canvas;
    private static final Object key = new Object();
    private MouseEvent mouseEvent;

public Upgrade_tower_listener(Canvas canvas){
    this.canvas = canvas;
}

public void handle(MouseEvent mouseEvent) {
    this.mouseEvent = mouseEvent;
    gc = canvas.getGraphicsContext2D();
    for(Tower tower: Board.get_instance().get_towers()){
        if(tower instanceof Classic_tower){
            message = "Price : " + Integer.toString(Game.get_instance().get_price_classic_tower()) +
                    "\n NPCs killed needed : " + Integer.toString(tower.get_npc_destroyed_needed());
        }
        else if(tower instanceof Factory_tower){
            message = "Price : " + Integer.toString(Game.get_instance().get_price_factory_tower()) +
                    "\n NPCs killed needed : " + Integer.toString(tower.get_npc_destroyed_needed());
        }
        else if(tower instanceof Freezing_tower){
            message = "Price : " + Integer.toString(Game.get_instance().get_price_freezing_tower())+
                    "\n NPCs killed needed : " + Integer.toString(tower.get_npc_destroyed_needed());
        }
        gc.setFont(new Font("Arial", 12)); //trouver plus joli si temps
        gc.setFill(Color.WHITE);
        gc.fillText(message, tower.get_asteroid().get_pos_x()*Map.get_instance().get_fact_x(), tower.get_asteroid().get_pos_y()*Map.get_instance().get_fact_y()-20);
    }
    }

    @Override
    public void run() {
        double t = 0;
        while (t<3000) {
            try {
                synchronized (key) {
                    Platform.runLater(() -> handle(mouseEvent));
                    Thread.sleep((long) (100.0 / Game.get_instance().get_fps()));
                    t += 100.0 / Game.get_instance().get_fps();
                    System.out.println(t);

                }
            } catch (InterruptedException e) {
                return;
            }
        }
        message = " ";

    }
}
