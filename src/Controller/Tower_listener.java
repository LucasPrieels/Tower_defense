package Controller;

import Model.*;
import View.Menu;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Tower_listener extends Parent implements EventHandler<MouseEvent>, Serializable {
    private ArrayList<Double> pos_x_asteroid;
    private ArrayList<Double> pos_y_asteroid;
    private String message;

    public Tower_listener(ArrayList<Double> pos_x_asteroid, ArrayList<Double> pos_y_asteroid, String message){
        this.pos_x_asteroid = pos_x_asteroid;
        this.pos_y_asteroid = pos_y_asteroid;
        this.message = message;
    }
    @Override
    public void handle(MouseEvent mouseEvent) {
        Message.set_const_message("");

        double fact_x = Update_manager.get_fact_x(), fact_y = Update_manager.get_fact_y();
        CopyOnWriteArrayList<Tower> copyTowers = new CopyOnWriteArrayList<>(Board.get_instance().get_towers());
        if (message.equals("Upgrade_tower") || message.equals("Destroy_tower")){
            for(Tower tower : copyTowers){
                if(message.equals("Upgrade_tower")) {
                    // Checks if tower is close enough from the click to assume that's the one to upgrade
                    if (is_into_circle(tower.get_asteroid().get_pos_x() * fact_x, tower.get_asteroid().get_pos_y() * fact_y, mouseEvent.getX(), mouseEvent.getY(), 50.0)) {
                        // Checks if the tower is not already at its max level
                        if (tower.get_curr_level() != tower.get_max_level()) {
                            // Checks if the user has destroyed enough NPC
                            if (Game.get_instance().get_npc_destroyed() >= tower.get_npc_destroyed_needed()) {
                                // Checks if the user has enough money left, and makes him pay if yes
                                if (Game.get_instance().pay(tower.get_price_upgrade())) {
                                    tower.upgrade();
                                    Menu.sound();
                                } else {
                                    Menu.bad_sound();
                                    Message.set_temp_message("You don't have enough money");
                                }
                            } else {
                                Menu.bad_sound();
                                Message.set_temp_message("You must have killed " + tower.get_npc_destroyed_needed() + " NPCs");
                            }
                        } else {
                            Menu.bad_sound();
                            Message.set_temp_message("The tower is at its maximum level");
                        }
                        return; // The tower which was clicked on has already been found, no need to loop more
                    }
                    // If none of the towers is close enough from the click, the button upgrade is deselected
                }
                else if(message.equals("Destroy_tower")){
                    if (is_into_circle(tower.get_asteroid().get_pos_x() * fact_x, tower.get_asteroid().get_pos_y() * fact_y, mouseEvent.getX(), mouseEvent.getY(), 50.0)){
                        tower.get_thread().interrupt(); // Stops whatever the tower was doing
                        Board.get_instance().remove_tower(tower); // Removes it from the GUI
                        tower.get_asteroid().unoccupy(); // Set its asteroid as unoccupied
                        Menu.sound();
                    }
                    Controller.Update_manager.update_window();
                }
            }
        }
        else{
            for (int i = 0; i < pos_x_asteroid.size(); i++) {
                if (is_into_circle(pos_x_asteroid.get(i)*fact_x, pos_y_asteroid.get(i)*fact_y, mouseEvent.getX(), mouseEvent.getY(), 50.0)) {
                    if (Board.get_instance().get_asteroids().get(i).is_occupied()){
                        Menu.bad_sound();
                        Message.set_temp_message("Asteroid already occupied");
                    }
                    else if (message.equals("Classic_tower")){
                        if (Game.get_instance().pay(Game.get_instance().get_price_classic_tower())){
                            Board.get_instance().add_tower(new Classic_tower(Board.get_instance().get_asteroids().get(i)));
                            Menu.sound();
                        }
                        else{
                            Menu.bad_sound();
                            Message.set_temp_message("You don't have enough money");
                        }
                    }
                    else if (message.equals("Freezing_tower")){
                        if (Game.get_instance().pay(Game.get_instance().get_price_freezing_tower())){
                            Board.get_instance().add_tower(new Freezing_tower(Board.get_instance().get_asteroids().get(i)));
                            Menu.sound();
                        }
                        else{
                            Menu.bad_sound();
                            Message.set_temp_message("You don't have enough money");
                        }
                    }
                    else if (message.equals("Factory_tower")){
                        if (Game.get_instance().pay(Game.get_instance().get_price_factory_tower())){
                            Board.get_instance().add_tower(new Factory_tower(Board.get_instance().get_asteroids().get(i)));
                            Menu.sound();
                        }
                        else{
                            Menu.bad_sound();
                            Message.set_temp_message("You don't have enough money");
                        }
                    }
                    else{
                        throw new AssertionError("Message given to Tower_listener is incorrect");
                    }
                    Controller.Update_manager.update_window();
                    return;
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