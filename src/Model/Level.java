package Model;

import java.util.ArrayList;

public class Level {
    private static int num_waves; // Tous les arguments d'un singleton se mettent en static?
    private static ArrayList<Wave> waves;
    private static int[] health_npc, speed_npc;
    private static ArrayList<Integer>[] time_small_npc, time_med_npc, time_big_npc;
    private static Level instance;

    private Level(int num_waves, int[] health_npc, int[] speed_npc, ArrayList<Integer>[] time_small_npc, ArrayList<Integer>[] time_med_npc, ArrayList<Integer>[] time_big_npc){
        Level.num_waves = num_waves;
        Level.health_npc = health_npc;
        Level.speed_npc = speed_npc;
        Level.time_small_npc = time_small_npc;
        Level.time_med_npc = time_med_npc;
        Level.time_big_npc = time_big_npc;
        create_waves();
    }

    public Level get_instance(int num_waves, int[] health_npc, int[] speed_npc, ArrayList<Integer>[] time_small_npc, ArrayList<Integer>[] time_med_npc, ArrayList<Integer>[] time_big_npc){
        if (Level.instance==null){Level.instance = new Level(num_waves, health_npc, speed_npc, time_small_npc, time_med_npc, time_big_npc);}
        return Level.instance;
    }

    public static void create_waves(){
        for (int i=0; i<num_waves; i++){
            Wave wave = new Wave(health_npc[i], speed_npc[i], time_small_npc[i], time_med_npc[i], time_big_npc[i]);
            waves.add(wave);
        }
    }
}
