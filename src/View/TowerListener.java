package View;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TowerListener implements EventHandler<MouseEvent> {
    private Canvas canvas;
    private ArrayList<Double> pos_x_asteroid;
    private ArrayList<Double> pos_y_asteroid;

    public TowerListener(Canvas canvas){
        this.canvas = canvas;
        this.pos_x_asteroid = new ArrayList<Double>();
        this.pos_y_asteroid = new ArrayList<Double>();
        pos_x_asteroid.add(125.0);
        pos_x_asteroid.add(525.0);
        pos_x_asteroid.add(731.0);
        pos_x_asteroid.add(1126.0);
        pos_x_asteroid.add(1125.0);
        pos_y_asteroid.add(127.0);
        pos_y_asteroid.add(424.0);
        pos_y_asteroid.add(228.0);
        pos_y_asteroid.add(22.0);
        pos_y_asteroid.add(471.0);
    }
    @Override
    public void handle(MouseEvent mouseEvent) {
        for (int i = 0;(i <= 4);i++) {
            if (is_into_circle(pos_x_asteroid.get(i), pos_y_asteroid.get(i), mouseEvent.getX(), mouseEvent.getY(), 33.0)) {
                System.out.println("ok");
            }
            else {
                System.out.println("not ok");
            }
        }
    }

    private boolean is_into_circle(Double x, Double y, Double xCircle, Double yCircle, Double r) {
        boolean res = false;
        double dist = Math.sqrt(Math.pow(x - xCircle, 2) + Math.pow(y - yCircle, 2));
        if (dist <= r) {
            res = true;
        }
        return res;
    }
}
