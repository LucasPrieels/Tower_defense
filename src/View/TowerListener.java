package View;

import Model.*;
import javafx.application.Platform;
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
    private GraphicsContext gc;
    private boolean handle_finished = false;
    private String message;
    private Image star;

    public TowerListener(Canvas canvas, ArrayList<Double> pos_x_asteroid, ArrayList<Double> pos_y_asteroid, String message){
        this.canvas = canvas;
        this.pos_x_asteroid = pos_x_asteroid;
        this.pos_y_asteroid = pos_y_asteroid;
        this.message = message;
    }
    @Override
    public void handle(MouseEvent mouseEvent) {
        if (handle_finished){
            return;
        }
        gc = canvas.getGraphicsContext2D();
        Map.get_instance().set_const_message("");
        double fact_x = Map.get_canvas_width()/Board.get_dim_x(), fact_y = Map.get_canvas_height()/Board.get_dim_y();
        if (message == "Upgrade_tower"){
            for (Tower tower: Board.get_towers()){
                if (is_into_circle(tower.get_asteroid().get_pos_x()*fact_x, tower.get_asteroid().get_pos_y()*fact_y, mouseEvent.getX(), mouseEvent.getY(), 50.0)) {
                    if (Game.pay(tower.get_price_upgrade())){
                        if (Game.get_npc_destroyed() >= tower.get_npc_destroyed_needed()){
                            if (tower.get_curr_level() != tower.get_max_level()){
                                tower.upgrade();

                            }
                            else{
                                Map.get_instance().set_temp_message("La tour est déjà à son niveau maximal");
                            }
                        }
                        else{
                            Map.get_instance().set_temp_message("Vous n'avez pas tué assez de PNJs");
                        }
                    }
                    else{
                        Map.get_instance().set_temp_message("Vous n'avez pas assez d'argent");
                    }
                }
            }
        }
        else{
            for (int i = 0; i < pos_x_asteroid.size(); i++) {
                if (is_into_circle(pos_x_asteroid.get(i)*fact_x, pos_y_asteroid.get(i)*fact_y, mouseEvent.getX(), mouseEvent.getY(), 50.0)) {
                    //ATTENTION Faire une factory plutôt que des else if
                    if (Board.get_asteroids().get(i).is_occupied()){
                        Map.get_instance().set_temp_message("Astéroide déjà occupé");
                    }
                    else if (message == "Classic_tower"){
                        if (Game.pay(Game.get_price_classic_tower())){
                            Board.add_tower(new Classic_tower(Board.get_asteroids().get(i)));
                        }
                        else{
                            Map.get_instance().set_temp_message("Vous n'avez pas assez d'argent");
                        }
                    }
                    else if (message == "Freezing_tower"){
                        if (Game.pay(Game.get_price_freezing_tower())){
                            Board.add_tower(new Freezing_tower(Board.get_asteroids().get(i)));
                        }
                        else{
                            Map.get_instance().set_temp_message("Vous n'avez pas assez d'argent");
                        }
                    }
                    else if (message == "Factory_tower"){
                        if (Game.pay(Game.get_price_factory_tower())){
                            Board.add_tower(new Factory_tower(Board.get_asteroids().get(i)));
                        }
                        else{
                            Map.get_instance().set_temp_message("Vous n'avez pas assez d'argent");
                        }
                    }
                    else{
                        System.out.println("Erreur!!!");
                    }
                        try {
                            Controller.Update_manager.get_instance().update_window();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    handle_finished = true;
                    break;
                }
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

    private void create_img() throws FileNotFoundException {
        Image star = new Image(new FileInputStream("Images/star.png"));
    }

}