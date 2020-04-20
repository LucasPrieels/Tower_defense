package Model;

import java.util.ArrayList;

public class Level {
    private static int num_waves; // Tous les arguments d'un singleton se mettent en static?
    private static ArrayList<Wave> waves;
    private static int[] health_small_npc, speed_small_npc, health_med_npc, speed_med_npc, health_big_npc, speed_big_npc, time_wave;
    private static ArrayList<Integer>[] time_small_npc, time_med_npc, time_big_npc; // Must have the same size!
    private static Level instance;

    private Level(int num_waves, int[] health_small_npc, int[] speed_small_npc, int[] health_med_npc, int[] speed_med_npc, int[] health_big_npc, int[] speed_big_npc, ArrayList<Integer>[] time_small_npc, ArrayList<Integer>[] time_med_npc, ArrayList<Integer>[] time_big_npc){
        Level.num_waves = num_waves;
        Level.health_small_npc = health_small_npc;
        Level.speed_small_npc = speed_small_npc;
        Level.health_med_npc = health_med_npc;
        Level.speed_med_npc = speed_med_npc;
        Level.health_big_npc = health_big_npc;
        Level.speed_big_npc = speed_big_npc;
        Level.time_small_npc = time_small_npc;
        Level.time_med_npc = time_med_npc;
        Level.time_big_npc = time_big_npc;
        //Exception pour vérifier que les time_..._npc ont la même taille
        int time_wave[] = new int[num_waves];
        for (int i=0; i<time_small_npc.length; i++){
            time_wave[i] = time_small_npc[i].size();
        }
        create_waves();
    }

    public Level get_instance(int num_waves, int[] health_small_npc, int[] speed_small_npc, int[] health_med_npc, int[] speed_med_npc, int[] health_big_npc, int[] speed_big_npc, ArrayList<Integer>[] time_small_npc, ArrayList<Integer>[] time_med_npc, ArrayList<Integer>[] time_big_npc){
        if (Level.instance==null){Level.instance = new Level(num_waves, health_small_npc, speed_small_npc, health_med_npc, speed_med_npc, health_big_npc, speed_big_npc, time_small_npc, time_med_npc, time_big_npc);}
        return Level.instance;
    }

    public static void create_waves(){
        for (int i=0; i<num_waves; i++){
            Wave wave = new Wave(health_small_npc[i], speed_small_npc[i], health_small_npc[i], speed_med_npc[i], health_med_npc[i], speed_big_npc[i], time_big_npc[i], time_med_npc[i], time_big_npc[i]);
            waves.add(wave);
        }
    }

    public static int get_time_small_npc(int wave, int time){return time_small_npc[wave].get(time);}
    public static int get_time_med_npc(int wave, int time){return time_med_npc[wave].get(time);}
    public static int get_time_big_npc(int wave, int time){return time_big_npc[wave].get(time);}
    public static int get_health_small_npc(int wave){return health_small_npc[wave];}
    public static int get_health_med_npc(int wave){return health_med_npc[wave];}
    public static int get_health_big_npc(int wave){return health_big_npc[wave];}
    public static int get_speed_small_npc(int wave){return speed_small_npc[wave];}
    public static int get_speed_med_npc(int wave){return speed_med_npc[wave];}
    public static int get_speed_big_npc(int wave){return speed_big_npc[wave];}
    public static int get_time_wave(int wave){return time_small_npc[wave].size();}
    public static int get_num_waves(){return num_waves;}
}
