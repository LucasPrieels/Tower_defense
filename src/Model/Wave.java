package Model;

import View.Map;

import java.util.ArrayList;

public class Wave implements Runnable{
    private int health_small_npc, speed_small_npc, health_med_npc, speed_med_npc, health_big_npc, speed_big_npc, time, max_time, curr_wave; //time since beginning of the wave
    private ArrayList<Integer> time_small_npc, time_med_npc, time_big_npc;
    private Thread t;
    private Map map;

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
        t = new Thread(this);
        curr_wave = Game.get_curr_wave();
        max_time = Level.get_time_wave(curr_wave);
        //...
        //health_npc et speed_npc sont les santés et vitesses initiales des PNJ de la vague
        //Les trois ArrayList contiennent une liste d'entiers représentant les temps d'apparition du type respectif de PNJ
        //Par exemple si time_small_npc = {1, 3, 7} ça veut dire qu'il faut faire apparaitre un petit PNJ après 1, 3, et 7 secondes (faire une classe Time ou des threads?)

        //A mon avis le mieux est de faire un ArrayList<ArrayList<NPC>> où chaque élément i de la liste est une liste des PNJ en le temps i
        //Mais attention parce que les NPC doivent être des Small/Medium/Big_NPC et pas simplement des NPC, donc il faut faire du polymorphisme pour les mettre dans la même liste
        //Pour créer un NPC différent en fonction de ce qui est demandé, se renseigner sur les Factories (voir TP 5 sur les design patterns)
    }

    public void run(){
        try{
            time = 0;
            while(time < max_time){
                Thread.sleep(1000);
                time++;
                update();
            }
        }catch(Exception e){
            System.out.println("Erreur dans le thread de la classe Wave");
        };
    }

    private void update(){ //Factory?
        for (int i=0; i<time_small_npc.get(time); i++){
            int radius = Small_NPC.get_radius_static();
            int pos_x = Board.get_dim_x()-radius;
            int pos_y = random_pos_NPC(radius);
            Board.add_npc(new Small_NPC(pos_x, pos_y, speed_small_npc, health_small_npc));
        }
        for (int i=0; i<time_med_npc.get(time); i++){
            int radius = Medium_NPC.get_radius_static();
            int pos_x = Board.get_dim_x()-radius;
            int pos_y = random_pos_NPC(radius);
            Board.add_npc(new Medium_NPC(pos_x, pos_y, speed_med_npc, health_med_npc));
        }
        for (int i=0; i<time_big_npc.get(time); i++){
            int radius = Big_NPC.get_radius_static();
            int pos_x = Board.get_dim_x()-radius;
            int pos_y = random_pos_NPC(radius);
            Board.add_npc(new Big_NPC(pos_x, pos_y, speed_big_npc, health_big_npc));
        }
    }

    private int random_pos_NPC(int radius){
        int num_paths, path_chosen, width, pos_y;
        do {
            num_paths = Board.get_num_paths();
            path_chosen = (int) Math.round(Math.random() * num_paths);
            width = Board.get_width_path(path_chosen)-2*radius;
            pos_y = Board.get_ord_path(path_chosen) + (int)(Math.random()*2*width) - width;
        } while(!Board.empty(Board.get_dim_x(), pos_y, radius));
        return Board.get_ord_path(path_chosen);
    }
}
