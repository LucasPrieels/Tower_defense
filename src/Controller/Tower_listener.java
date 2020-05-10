package Controller;

import Model.*;
import View.Map;
import View.Menu;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Tower_listener extends Parent implements EventHandler<MouseEvent>, Serializable {
    private Canvas canvas;
    private ArrayList<Double> pos_x_asteroid;
    private ArrayList<Double> pos_y_asteroid;
    private GraphicsContext gc;
    private boolean handle_finished = false;
    private String message;

    public Tower_listener(Canvas canvas, ArrayList<Double> pos_x_asteroid, ArrayList<Double> pos_y_asteroid, String message){
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
        Menu.sound();
        gc = canvas.getGraphicsContext2D();
        Message.set_const_message("");
        double fact_x = Map.get_canvas_width()/Board.get_instance().get_dim_x(), fact_y = Map.get_canvas_height()/Board.get_instance().get_dim_y();
        CopyOnWriteArrayList<Tower> copyTowers = new CopyOnWriteArrayList<>(Board.get_instance().get_towers());
        if (message == "Upgrade_tower"|| message == "Destroy_tower"){
            for(Tower tower : copyTowers){
                if(message == "Upgrade_tower") {
                    if (is_into_circle(tower.get_asteroid().get_pos_x() * fact_x, tower.get_asteroid().get_pos_y() * fact_y, mouseEvent.getX(), mouseEvent.getY(), 50.0)) {
                        if (Game.get_instance().pay(tower.get_price_upgrade())) {
                            if (Game.get_instance().get_npc_destroyed() >= tower.get_npc_destroyed_needed()) {
                                if (tower.get_curr_level() != tower.get_max_level()) {
                                    tower.upgrade();
                                } else {
                                    Menu.bad_sound();
                                    Message.set_temp_message("The tower is at its maximum level");
                                }
                            } else {
                                Menu.bad_sound();
                                Message.set_temp_message("You must have killed " + tower.get_npc_destroyed_needed() + " NPCs");
                            }
                        } else {
                            Menu.bad_sound();
                            Message.set_temp_message("You don't have enough money");
                        }
                    }
                }
                else if(message == "Destroy_tower"){
                    if (is_into_circle(tower.get_asteroid().get_pos_x() * fact_x, tower.get_asteroid().get_pos_y() * fact_y, mouseEvent.getX(), mouseEvent.getY(), 50.0)){
                        tower.get_thread().interrupt();
                        Board.get_instance().remove_tower(tower);
                        tower.get_asteroid().unoccupy();


                    }
                    else{
                        Message.set_temp_message("There is no tower");
                    }
                    Controller.Update_manager.update_window();
                }
            }
        }
        else{
            for (int i = 0; i < pos_x_asteroid.size(); i++) {
                if (is_into_circle(pos_x_asteroid.get(i)*fact_x, pos_y_asteroid.get(i)*fact_y, mouseEvent.getX(), mouseEvent.getY(), 50.0)) {
                    //ATTENTION Faire une factory plutôt que des else if
                    if (Board.get_instance().get_asteroids().get(i).is_occupied()){
                        Menu.bad_sound();
                        Message.set_temp_message("Asteroid already occupied");
                    }
                    else if (message == "Classic_tower"){
                        if (Game.get_instance().pay(Game.get_instance().get_price_classic_tower())){
                            Board.get_instance().add_tower(new Classic_tower(Board.get_instance().get_asteroids().get(i)));
                        }
                        else{
                            Menu.bad_sound();
                            Message.set_temp_message("You don't have enough money");
                        }
                    }
                    else if (message == "Freezing_tower"){
                        if (Game.get_instance().pay(Game.get_instance().get_price_freezing_tower())){
                            Board.get_instance().add_tower(new Freezing_tower(Board.get_instance().get_asteroids().get(i)));
                        }
                        else{
                            Menu.bad_sound();
                            Message.set_temp_message("You don't have enough money");
                        }
                    }
                    else if (message == "Factory_tower"){
                        if (Game.get_instance().pay(Game.get_instance().get_price_factory_tower())){
                            Board.get_instance().add_tower(new Factory_tower(Board.get_instance().get_asteroids().get(i)));
                        }
                        else{
                            Menu.bad_sound();
                            Message.set_temp_message("You don't have enough money");
                        }
                    }
                    //else if (message == "Destroy_tower"){
                    //    Menu.bad_sound();
                    //    Message.set_temp_message("Il n'y a pas de tour sur l'astéroïde");
                    //}
                    else{
                        System.out.println("Error!!!");
                    }
                    Controller.Update_manager.update_window();
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


}