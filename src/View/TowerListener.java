package View;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TowerListener extends Parent implements EventHandler<MouseEvent> {
    private Canvas canvas;
    private ArrayList<Double> pos_x_asteroid;
    private ArrayList<Double> pos_y_asteroid;
    private ArrayList<Integer> occupation_asteroid;
    private int occupied;
    private GraphicsContext gc;
    private int num_asteroid;

    public TowerListener(Canvas canvas, ArrayList pos_x_asteroid, ArrayList pos_y_asteroid, int num_asteroid){
        this.canvas = canvas;
        this.pos_x_asteroid = pos_x_asteroid;
        this.pos_y_asteroid = pos_y_asteroid;
        this.occupation_asteroid = new ArrayList<Integer>();
        this.num_asteroid = num_asteroid;
        //pos_x_asteroid.add(125.0);
        //pos_x_asteroid.add(525.0);
        //pos_x_asteroid.add(731.0);
        //pos_x_asteroid.add(1126.0);
        //pos_x_asteroid.add(1125.0);
        //pos_y_asteroid.add(127.0);
        //pos_y_asteroid.add(424.0);
        //pos_y_asteroid.add(228.0);
        //pos_y_asteroid.add(22.0);
        //pos_y_asteroid.add(471.0);
    }
    @Override
    public void handle(MouseEvent mouseEvent) {
        for (int i = 0;i <= num_asteroid-1;i++) {
            if (is_into_circle(pos_x_asteroid.get(i), pos_y_asteroid.get(i), mouseEvent.getX(), mouseEvent.getY(), 50.0)) {
                occupied = 1;
            }
            else {
                occupied = 0;
            }
            occupation_asteroid.add(occupied);
        }
        //System.out.println(occupation_asteroid);
        try {
            draw_asteroid();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        occupation_asteroid.clear();
    }

    private boolean is_into_circle(Double x, Double y, Double xCircle, Double yCircle, Double r) {
        boolean res = false;
        double dist = Math.sqrt(Math.pow(x - xCircle, 2) + Math.pow(y - yCircle, 2));
        if (dist <= r) {
            res = true;
        }
        return res;
    }

    private void draw_asteroid() throws FileNotFoundException {
        gc = canvas.getGraphicsContext2D();
        for(int i = 0; i<=num_asteroid-1; i++){
            if(occupation_asteroid.get(i) == 1){
                Image tower = new Image(new FileInputStream("Images/cerclejaune.png"));
                gc.drawImage(tower, pos_x_asteroid.get(i)+10, pos_y_asteroid.get(i)+10);
            }
            else {System.out.println("erreur");}
        }
    }


}