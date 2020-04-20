package Model;

import java.util.ArrayList;

public class Wave implements Runnable{
    int health_npc;
    int speed_npc;
    float time; //time since beginning of the wave
    float max_time = 30000; //durée vague
    private float dt_small_npc;
    private float dt_med_npc;
    private float dt_big_npc;
    private ArrayList<Small_NPC> small_npcs_list;
    private ArrayList<Medium_NPC> med_npcs_list;
    private ArrayList<Big_NPC> big_npcs_list;
    private Thread t;
    Map map;

    public Wave(int health_npc, int speed_npc, float dt_small_npc, float dt_med_npc, float dt_big_npc){
        this.health_npc = health_npc;
        this.speed_npc = speed_npc;
        this.dt_small_npc = dt_small_npc;
        this.dt_med_npc = dt_med_npc;
        this.dt_big_npc = dt_big_npc;
        ArrayList<Small_NPC> small_npcs_list = new ArrayList<Small_NPC>();
        ArrayList<Medium_NPC> med_npcs_list = new ArrayList<Medium_NPC>();
        ArrayList<Big_NPC> big_npcs_list = new ArrayList<Big_NPC>();
        t = new Thread(this);






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
                time++;
                Thread.sleep(1000);
            }
        }catch(Exception e){};
    }

    private void create_small_npc(){
        small_npcs_list.add(new Small_NPC(0,0,health_npc));
    }

    private void create_med_npc() {
        med_npcs_list.add(new Medium_NPC(0, 0, health_npc));
    }

    private void create_big_npc() {
        big_npcs_list.add(new Big_NPC(0, 0, health_npc));
    }

    private void update(){
        for(time < max_time){
            if(time/dt_small_npc instanceof Integer){
                create_small_npc();
                //map.draw();
            }
            else if(time/dt_med_npc instanceof Integer){
                create_med_npc();
                //map.draw();
            }
            else if(time/dt_big_npc){
                create_big_npc();
                //map.draw();
            }
        }


    }
}
