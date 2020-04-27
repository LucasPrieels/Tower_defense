package View;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ShopListener implements EventHandler<MouseEvent> {
    private GraphicsContext gc;
    private String message;
    private Canvas canvas;

    public ShopListener(GraphicsContext gc, String message){
        this.gc = gc;
        this.message = message;
    }

    public void handle(MouseEvent mouseEvent){
        message(gc);
    }

    private void message(GraphicsContext gc){
        gc.setFill(Color.rgb(0,0,0,0.5)); //noir transparent
        gc.fillRoundRect(890,550,330,45,15,25);

        gc.setFont(new Font("Arial", 20)); //trouver plus joli si temps
        gc.setFill(Color.WHITE);

        if(message == "asteroid"){
            gc.fillText("Cliquez sur un asteroïde", 900,580);
        }
        else if(message == "tower"){
            gc.fillText("Cliquez sur une tour", 900,580);
        }

    }
}