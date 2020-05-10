package Model;

import View.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.util.Pair;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;

public class Wave implements Runnable, Serializable {
    private int health_small_npc, health_med_npc, health_big_npc, time, max_time; //time since beginning of the wave
    private double speed_small_npc, speed_med_npc, speed_big_npc;
    private ArrayList<Integer> time_small_npc, time_med_npc, time_big_npc;
    private boolean finished = false;
    public static final Object key = new Object();

    public Wave(int health_small_npc, double speed_small_npc, int health_med_npc, double speed_med_npc, int health_big_npc, double speed_big_npc, ArrayList<Integer> time_small_npc, ArrayList<Integer> time_med_npc, ArrayList<Integer> time_big_npc, int time_between_waves){
        this.health_small_npc = health_small_npc;
        this.speed_small_npc = speed_small_npc;
        this.health_med_npc = health_med_npc;
        this.speed_med_npc = speed_med_npc;
        this.health_big_npc = health_big_npc;
        this.speed_big_npc = speed_big_npc;
        this.time_small_npc = time_small_npc;
        this.time_med_npc = time_med_npc;
        this.time_big_npc = time_big_npc;
        max_time = time_small_npc.size() + time_between_waves;
    }

    public void run(){
        int iter = 0, fps = Game.get_instance().get_fps();
        Map.get_instance().set_temp_message("Lancement de la vague " + (Game.get_instance().get_curr_wave() + 1));
        while (time < max_time){
            if (time > max_time - Level.get_instance().get_time_between_waves() && Board.get_instance().get_npcs().size() == 0) return;
            iter++;
            try{
                synchronized (key) {
                    if (iter % fps == 0) add_new_npcs();
                    update_pos_npcs();
                    Platform.runLater(() -> {
                        Controller.Update_manager.update_window();
                    });
                    if (Game.get_instance().get_score() <= 0) return;
                }
                Thread.sleep(1000 / fps);
                if (iter % fps == 0) {
                    time++;
                }
            } catch(InterruptedException | AssertionError e){
                return;
            }
        }
        if (Game.get_instance().get_curr_wave() == Level.get_instance().get_num_waves() - 1){
            while (Board.get_instance().get_npcs().size() > 0){
                try{
                    synchronized (key) {
                        update_pos_npcs();
                        Platform.runLater(() -> {
                            Controller.Update_manager.update_window();
                        });
                    }
                    if (Game.get_instance().get_score() <= 0) return;
                    Thread.sleep(1000/fps);
                } catch(InterruptedException e) {
                    return;
                }
            }
        }
        finished = true;
    }

    private void add_new_npcs(){ //Factory?
        if (time<time_small_npc.size()){ //Ou plutot try except?
            for (int i=0; i<time_small_npc.get(time); i++){
                int radius = Small_NPC.get_radius_static();
                double pos_x = Board.get_instance().get_dim_x()-radius;
                double[] res = random_pos_npc(radius);
                Path_custom path = Board.get_instance().get_paths().get((int)res[0]);
                double pos_y = res[1];
                Board.get_instance().add_npc(new Small_NPC(pos_x, pos_y, speed_small_npc, health_small_npc, path));
            }
        }
        if (time<time_med_npc.size()){
            for (int i=0; i<time_med_npc.get(time); i++){
                int radius = Medium_NPC.get_radius_static();
                double pos_x = Board.get_instance().get_dim_x()-radius;
                double[] res = random_pos_npc(radius);
                Path_custom path = Board.get_instance().get_paths().get((int)res[0]);
                double pos_y = res[1];
                Board.get_instance().add_npc(new Medium_NPC(pos_x, pos_y, speed_med_npc, health_med_npc, path));
            }
        }
        if (time<time_big_npc.size()){
            for (int i=0; i<time_big_npc.get(time); i++){
                int radius = Big_NPC.get_radius_static();
                double pos_x = Board.get_instance().get_dim_x()-radius;
                double[] res = random_pos_npc(radius);
                Path_custom path = Board.get_instance().get_paths().get((int)res[0]);
                double pos_y = res[1];
                Board.get_instance().add_npc(new Big_NPC(pos_x, pos_y, speed_big_npc, health_big_npc, path));
            }
        }
    }

    private double[] random_pos_npc(int radius){
        int num_paths, path_chosen, width;
        double pos_y;
        do {
            num_paths = Board.get_instance().get_num_paths();
            path_chosen = (int) Math.floor(Math.random() * num_paths);
            width = Board.get_instance().get_width_path(path_chosen)-2*radius;
            pos_y = Board.get_instance().get_ord_path(path_chosen) + Math.random()*2*width - width;
            //System.out.println("pos_y " + pos_y + " " + Board.get_instance().get_ord_path(path_chosen));
        } while(!Board.get_instance().empty(Board.get_instance().get_dim_x(), pos_y, radius));
        return new double[]{path_chosen, pos_y};
    }

    private void update_pos_npcs(){
        for (int i=0; i<Board.get_instance().get_npcs().size(); i++){
            NPC npc = Board.get_instance().get_npcs().get(i);
            double speed = npc.get_speed();
            if (npc.is_frozen()>0){
                speed /= 3;
                npc.decrease_freezed(1.0/Game.get_instance().get_fps());
            }
            npc.decrease_curr_ind(speed);
            Pair<Double, Double> pos = npc.get_path().next_pos(npc.get_curr_ind(), npc.get_pos_x(), npc.get_pos_y(), speed);
            npc.set_pos_x(pos.getKey());
            npc.set_pos_y(pos.getValue());
            if (pos.getKey() <= 0){
                Board.get_instance().remove_npc(npc);
                Sound negative_snd = TinySound.loadSound("Songs/negative.wav");
                negative_snd.play(3);
                Game.get_instance().decrease_score(Game.get_instance().get_score_lost());
            }
        }
    }

    public int get_time_wave(){return time_small_npc.size();}
    public boolean get_finished(){return finished;}
    public int get_max_time(){return max_time;}
    public int get_time(){return time;}
}
