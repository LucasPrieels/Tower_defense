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

import java.util.ArrayList;

public class ShopListener implements EventHandler<MouseEvent>, Runnable {
    private GraphicsContext gc;
    private String message;
    private Canvas canvas;

    public ShopListener(GraphicsContext gc, String message, Canvas canvas){
        this.gc = gc;
        this.message = message;
        this.canvas = canvas;
    }

    public void handle(MouseEvent mouseEvent){
        message(gc);
    }

    private void message(GraphicsContext gc){
        gc.setFill(Color.rgb(0,0,0,0.5)); //noir transparent
        gc.fillRoundRect(890,550,330,45,15,25);

        gc.setFont(new Font("Arial", 20)); //trouver plus joli si temps
        gc.setFill(Color.WHITE);

        Thread thread = new Thread(this);
        thread.start();
    }

    public void run(){
        ArrayList<Double> pos_x_asteroid = new ArrayList<>(), pos_y_asteroid = new ArrayList<>();
        for (Asteroid asteroid: Board.get_asteroids()){
            pos_x_asteroid.add(asteroid.get_pos_x());
            pos_y_asteroid.add(asteroid.get_pos_y());
        }
        TowerListener towerListener = new TowerListener(canvas, pos_x_asteroid, pos_y_asteroid, message);
        canvas.setOnMouseClicked(towerListener);
        while (true){
            if(message == "Classic_tower" || message == "Freezing_tower" || message == "Factory_tower"){
                Platform.runLater( () -> {
                    gc.fillText("Cliquez sur un asteroïde", 900,580);
                });
            }
            else if(message == "Upgrade_tower"){
                Platform.runLater( () -> {
                    gc.fillText("Cliquez sur une tour", 900,580);
                });
            }
            try{
                Thread.sleep(200/ Game.get_fps());
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            if (towerListener.get_constructed()){
                break;
            }
        }
        towerListener = null;
    }
}