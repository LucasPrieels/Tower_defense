package Model;

import java.util.ArrayList;

public class Wave implements Runnable{
    int health_npc;
    int speed_npc;
    float time; //time since beginning of the wave
    float max_time = 30000; //durée vague
    private ArrayList<Integer> time_small_npc;
    private ArrayList<Integer> time_med_npc;
    private ArrayList<Integer> time_big_npc;
    private ArrayList<Small_NPC> small_npcs_list;
    private ArrayList<Medium_NPC> med_npcs_list;
    private ArrayList<Big_NPC> big_npcs_list;
    private Thread t;
    Map map;

    public Wave(int health_npc, int speed_npc, ArrayList<Integer> time_small_npc, ArrayList<Integer>  time_med_npc, ArrayList<Integer> time_big_npc){
        this.health_npc = health_npc;
        this.speed_npc = speed_npc;
        this.time_small_npc = time_small_npc;
        this.time_med_npc = time_med_npc;
        this.time_big_npc = time_big_npc;
        ArrayList<Small_NPC> small_npcs_list = new ArrayList<Small_NPC>();
        ArrayList<Medium_NPC> med_npcs_list = new ArrayList<Medium_NPC>();
        ArrayList<Big_NPC> big_npcs_list = new ArrayList<Big_NPC>();
        //ArrayList<Integer> time_small_npc = new ArrayList<Integer>();
        //ArrayList<Integer> time_med_npc = new ArrayList<Integer>();
        //ArrayList<Integer> time_big_npc = new ArrayList<Integer>();
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

    private void add_npcs_easy_wave(){
        small_npcs_list.add(new Small_NPC(0,0,health_npc));
        small_npcs_list.add(new Small_NPC(0,0,health_npc));
        med_npcs_list.add(new Medium_NPC(0,0,health_npc));
        med_npcs_list.add(new Medium_NPC(0,0,health_npc));
        big_npcs_list.add(new Big_NPC(0,0,health_npc));
        big_npcs_list.add(new Big_NPC(0,0,health_npc));
    }

    private void add_npcs_medium_wave(){
        small_npcs_list.add(new Small_NPC(0,0,health_npc));
        small_npcs_list.add(new Small_NPC(0,0,health_npc));
        small_npcs_list.add(new Small_NPC(0,0,health_npc));
        med_npcs_list.add(new Medium_NPC(0,0,health_npc));
        med_npcs_list.add(new Medium_NPC(0,0,health_npc));
        med_npcs_list.add(new Medium_NPC(0,0,health_npc));
        big_npcs_list.add(new Big_NPC(0,0,health_npc));
        big_npcs_list.add(new Big_NPC(0,0,health_npc));
        big_npcs_list.add(new Big_NPC(0,0,health_npc));
    }

    private void add_npcs_difficult_wave(){
        small_npcs_list.add(new Small_NPC(0,0,health_npc));
        small_npcs_list.add(new Small_NPC(0,0,health_npc));
        small_npcs_list.add(new Small_NPC(0,0,health_npc));
        small_npcs_list.add(new Small_NPC(0,0,health_npc));
        med_npcs_list.add(new Medium_NPC(0,0,health_npc));
        med_npcs_list.add(new Medium_NPC(0,0,health_npc));
        med_npcs_list.add(new Medium_NPC(0,0,health_npc));
        med_npcs_list.add(new Medium_NPC(0,0,health_npc));
        big_npcs_list.add(new Big_NPC(0,0,health_npc));
        big_npcs_list.add(new Big_NPC(0,0,health_npc));
        big_npcs_list.add(new Big_NPC(0,0,health_npc));
        big_npcs_list.add(new Big_NPC(0,0,health_npc));
    }

    private void update(int wave_type){
        if (wave_type == 1){
            add_npcs_easy_wave();
            for(Integer int:time_small_npc){
                if(int==time){
                    Small_NPC npc = small_npcs_list.get(int);
                    map.draw(npc);
                }
            }
            for(Integer int:time_med_npc){
                if(int==time){
                    Medium_NPC npc = med_npcs_list.get(int);
                    map.draw(npc);
                }
            }
            for(Integer int:time_big_npc){
                if(int==time){
                    Big_NPC npc = big_npcs_list.get(int);
                    map.draw(npc);
                }
            }
        }
        else if (wave_type == 2) {
            add_npcs_medium_wave();
            for(Integer int:time_small_npc){
                if(int==time){
                    Small_NPC npc = small_npcs_list.get(int);
                    map.draw(npc);
                }
            }
            for(Integer int:time_med_npc){
                if(int==time){
                    Medium_NPC npc = med_npcs_list.get(int);
                    map.draw(npc);
                }
            }
            for(Integer int:time_big_npc){
                if(int==time){
                    Big_NPC npc = big_npcs_list.get(int);
                    map.draw(npc);
                }
            }
        }
        else {
            add_npcs_difficult_wave();
            for(Integer int:time_small_npc){
                if(int==time){
                    Small_NPC npc = small_npcs_list.get(int);
                    map.draw(npc);
                }
            }
            for(Integer int:time_med_npc){
                if(int==time){
                    Medium_NPC npc = med_npcs_list.get(int);
                    map.draw(npc);
                }
            }
            for(Integer int:time_big_npc){
                if(int==time){
                    Big_NPC npc = big_npcs_list.get(int);
                    map.draw(npc);
                }
            }
        }


    }
}
