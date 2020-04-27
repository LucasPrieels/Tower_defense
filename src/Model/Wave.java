package Model;

import View.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.ArrayList;

public class Wave implements Runnable{
    private int health_small_npc, speed_small_npc, health_med_npc, speed_med_npc, health_big_npc, speed_big_npc, time, max_time, curr_wave; //time since beginning of the wave
    private ArrayList<Integer> time_small_npc, time_med_npc, time_big_npc;
    private boolean finished = false;

    public Wave(int health_small_npc, int speed_small_npc, int health_med_npc, int speed_med_npc, int health_big_npc, int speed_big_npc, ArrayList<Integer> time_small_npc, ArrayList<Integer> time_med_npc, ArrayList<Integer> time_big_npc){
        this.health_small_npc = health_small_npc;
        this.speed_small_npc = speed_small_npc;
        this.health_med_npc = health_med_npc;
        this.speed_med_npc = speed_med_npc;
        this.health_big_npc = health_big_npc;
        this.speed_big_npc = speed_big_npc;
        this.time_small_npc = time_small_npc;
        this.time_med_npc = time_med_npc;
        this.time_big_npc = time_big_npc;
        max_time = time_small_npc.size(); //ATTENTION exception si pas même taille que med et big
        /*
        Thread t = new Thread(this);
        t.start();
        */
        //...
        //health_npc et speed_npc sont les santés et vitesses initiales des PNJ de la vague
        //Les trois ArrayList contiennent une liste d'entiers représentant les temps d'apparition du type respectif de PNJ
        //Par exemple si time_small_npc = {1, 3, 7} ça veut dire qu'il faut faire apparaitre un petit PNJ après 1, 3, et 7 secondes (faire une classe Time ou des threads?)

        //A mon avis le mieux est de faire un ArrayList<ArrayList<NPC>> où chaque élément i de la liste est une liste des PNJ en le temps i
        //Mais attention parce que les NPC doivent être des Small/Medium/Big_NPC et pas simplement des NPC, donc il faut faire du polymorphisme pour les mettre dans la même liste
        //Pour créer un NPC différent en fonction de ce qui est demandé, se renseigner sur les Factories (voir TP 5 sur les design patterns)
    }

    public void run(){
        /*
        while (time<max_time){
            try{
                add_new_npcs();
                update_pos_npcs();
                Controller.Update_manager.get_instance().update_window();
                Thread.sleep(1000);
                time++;
            } catch(InterruptedException e){
                System.out.println("Erreur dans le thread run de la classe Wave");
            }
        }
        finish_wave();
         */

        while (time<max_time || Board.get_npcs().size() != 0){
            System.out.println(time);
            try{
                if (time<max_time) add_new_npcs();
                //else System.out.println("ici2");
                update_pos_npcs();
                //System.out.println("ici");
                //Platform.setImplicitExit(false);
                Platform.runLater( () -> {
                    //System.out.println("Updating");
                    Controller.Update_manager.get_instance().update_window();
                });
                Thread.sleep(1000);
                time++;
            } catch(InterruptedException e){
                System.out.println("Erreur dans le thread run de la classe Wave");
            }
        }
        finished = true;
    }

    /*
    public void run(){
        try{
            time = 0;
            System.out.print(time);
            System.out.println(max_time);
            while(time < max_time){
                Thread.sleep(1000);
                time++;
                System.out.println(1);
                add_new_npcs();
                System.out.println(2);
                update_pos_npcs();
                System.out.println(3);
                Controller.Update_manager.get_instance().update_window();
                System.out.println(4);
                for (NPC npc: Board.get_npcs()){
                    System.out.print(npc.get_pos_x());
                }
                System.out.println();
            }
        }catch(Exception e){
            System.out.println("Erreur dans le thread de la classe Wave");
        };
    }
     */

    private void add_new_npcs(){ //Factory?
        if (time<time_small_npc.size()){ //Ou plutot try except?
            for (int i=0; i<time_small_npc.get(time); i++){
                int radius = Small_NPC.get_radius_static();
                int pos_x = Board.get_dim_x()-radius;
                int[] res = random_pos_npc(radius);
                Path path = Board.get_paths().get(res[0]);
                int pos_y = res[1];
                Board.add_npc(new Small_NPC(pos_x, pos_y, speed_small_npc, health_small_npc, path));
            }
        }
        if (time<time_med_npc.size()){
            for (int i=0; i<time_med_npc.get(time); i++){
                int radius = Medium_NPC.get_radius_static();
                int pos_x = Board.get_dim_x()-radius;
                int[] res = random_pos_npc(radius);
                Path path = Board.get_paths().get(res[0]);
                int pos_y = res[1];
                Board.add_npc(new Medium_NPC(pos_x, pos_y, speed_med_npc, health_med_npc, path));
            }
        }
        if (time<time_big_npc.size()){
            for (int i=0; i<time_big_npc.get(time); i++){
                int radius = Big_NPC.get_radius_static();
                int pos_x = Board.get_dim_x()-radius;
                int[] res = random_pos_npc(radius);
                Path path = Board.get_paths().get(res[0]);
                int pos_y = res[1];
                Board.add_npc(new Big_NPC(pos_x, pos_y, speed_big_npc, health_big_npc, path));
            }
        }
    }

    private int[] random_pos_npc(int radius){
        int num_paths, path_chosen, width, pos_y;
        do {
            num_paths = Board.get_num_paths();
            path_chosen = (int) Math.floor(Math.max(Math.random()-0.0000001,0) * num_paths);
            width = Board.get_width_path(path_chosen)-2*radius;
            pos_y = Board.get_ord_path(path_chosen) + (int)Math.round(Math.random()*2*width) - width;
        } while(!Board.empty(Board.get_dim_x(), pos_y, radius));
        return new int[]{path_chosen, pos_y};
    }

    private void update_pos_npcs(){
        for (int i=0; i<Board.get_npcs().size(); i++){
            NPC npc = Board.get_npcs().get(i);
            int[] pos = npc.get_path().next_pos(npc.get_pos_x(), npc.get_pos_y(), npc.get_speed());
            /*
            System.out.print(npc.get_pos_x());
            System.out.print(" ");
            System.out.print(npc.get_pos_y());
            System.out.print(" ");
            System.out.print(npc.get_speed());
            System.out.print(" ");
            System.out.print(pos[0]);
            System.out.print(" ");
            System.out.println(pos[1]);
             */
            npc.set_pos_x(pos[0]);
            npc.set_pos_y(pos[1]);
            if (pos[0]==0 || pos[1]==0) Board.remove_npc(npc);
        }
    }

    public ArrayList<Integer> get_time_small_npc(){return time_small_npc;}
    public boolean get_finished(){return finished;}
    public int get_max_time(){return max_time;}
}
