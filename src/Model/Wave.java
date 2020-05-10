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
                return;
            }
            iter++;
            try{
                synchronized (key) {
                    if (iter % fps == 0) NPCFactory.add_npcs(time_npc, speed_npc, health_npc, time);
                    update_pos_redrawable();
                    check_munition_shot();
                    Platform.runLater(Update_manager::update_window);
                    if (Game.get_instance().get_score() <= 0){
                        return;
                    }
                }
                Thread.sleep(1000 / fps);
                if (iter % fps == 0) {
                    time++;
                }
            } catch(InterruptedException e){
                return;
            } catch(AssertionError e){
                e.printStackTrace();
            }
        }
        if (Game.get_instance().get_curr_wave() == Level.get_instance().get_num_waves() - 1){ // Si la dernière vague est finie, le jeu continue jusqu'à ce qu'il n'y ait plus de PNJ
            while (Board.get_instance().get_npcs().size() > 0){
                try{
                    synchronized (key) {
                        update_pos_redrawable();
                        Platform.runLater(Update_manager::update_window);
                    }
                    if (Game.get_instance().get_score() <= 0) return;
                    Thread.sleep(1000/fps);
                } catch(InterruptedException e) {
                    return;
                }
            }
        }
    }

    private void update_pos_redrawable(){
        for (Redrawable redrawable: Board.get_instance().get_redrawables()){
            redrawable.update_pos();
        }
    }

    private void check_munition_shot(){
        CopyOnWriteArrayList<Munition> copyMunitions = new CopyOnWriteArrayList<>(Board.get_instance().get_munitions());
        for (Munition munition: copyMunitions){
            if (munition.check_shot_npc() && copyMunitions.contains(munition)){ // Check if a munition has been removed from munitions but is still in its copy
                System.out.println("Munition détruite");
                Board.get_instance().remove_munition(munition);
            }
        }
    }

    public int get_time_wave(){return time_npc.get(0).size();}
    public int get_time(){return time;}
}
