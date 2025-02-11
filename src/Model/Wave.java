package Model;

import Controller.Update_manager;
import javafx.application.Platform;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Wave implements Runnable, Serializable {
    private int time, max_time; //time since beginning of the wave
    private ArrayList<Integer> health_npc;
    private ArrayList<Double> speed_npc;
    private ArrayList<ArrayList<Integer>> time_npc;
    public static final Object key = new Object();

    public Wave(ArrayList<Integer> health_npc, ArrayList<Double> speed_npc, ArrayList<ArrayList<Integer>> time_npc, int time_between_waves){
        this.health_npc = health_npc;
        this.speed_npc = speed_npc;
        this.time_npc = time_npc;
        max_time = time_npc.get(0).size() + time_between_waves;
    }

    public void run(){
        int iter = 0, fps = Game.get_instance().get_fps();
        while (time < max_time){
            if (time > max_time - Level.get_instance().get_time_between_waves() && Board.get_instance().get_npcs().size() == 0){
                System.out.println("Reussi");
                return; // If no NPC is going to be added in this wave (first condition) and there are no more NPCs on the Board,
                // There is no point in waiting for the next wave, we launch it right away
            }
            iter++;
            try{
                synchronized (key) {
                    if (iter % fps == 0) NPCFactory.add_npcs(time_npc, speed_npc, health_npc, time); // Creates and adds new NPCs
                    update_pos_movable(); // Update the position of all Movable objects
                    check_munition_shot(); // Checks if any NPC has been shot by a munition
                    Platform.runLater(Update_manager::update_window);
                    if (Game.get_instance().get_score() <= 0){
                        return; // Game over
                    }
                }
                Thread.sleep(1000 / fps);
                if (iter % fps == 0) {
                    time++; // There are fps iterations of the code each second (so time is in seconds)
                }
            } catch(InterruptedException e){
                return;
            } catch(AssertionError e){
                e.printStackTrace();
            }
        }
        if (Game.get_instance().get_curr_wave() == Level.get_instance().get_num_waves() - 1){
            // If the last wave is over, the game carries on until there are no NPC left
            while (Board.get_instance().get_npcs().size() > 0){
                try{
                    synchronized (key) {
                        update_pos_movable();
                        check_munition_shot(); // Checks if any NPC has been shot by a munition
                        Platform.runLater(Update_manager::update_window);
                    }
                    if (Game.get_instance().get_score() <= 0){
                        System.out.println("Game over");
                        return;
                    }
                    Thread.sleep(1000/fps);
                } catch(InterruptedException e) {
                    return;
                }
            }
        }
    }

    void update_pos_movable(){
        CopyOnWriteArrayList<Movable> copyMovables = new CopyOnWriteArrayList<>(Board.get_instance().get_movables());
        for (Movable movable : copyMovables){
            movable.update_pos(); // Updates the position of all Movable objects
        }
    }

    void check_munition_shot(){
        CopyOnWriteArrayList<Munition> copyMunitions = new CopyOnWriteArrayList<>(Board.get_instance().get_munitions());
        for (Munition munition: copyMunitions){
            if (munition.scan()){
                System.out.println("Munition détruite");
                Board.get_instance().remove_munition(munition);
            }
        }
    }

    public int get_time_wave(){return time_npc.get(0).size();}
    public int get_time(){return time;}
}
