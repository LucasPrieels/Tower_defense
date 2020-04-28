package Model;

import java.util.ArrayList;

public class Level {
    private static int num_waves; // Tous les arguments d'un singleton se mettent en static?
    private static ArrayList<Wave> waves = new ArrayList<>();
    private static int[] health_small_npc, health_med_npc, health_big_npc; //i-th element is the health or speed of that type on npc for the i-th wave
    private static double[] speed_small_npc, speed_med_npc, c, speed_big_npc;
    private static ArrayList<ArrayList<Integer>> time_small_npc, time_med_npc, time_big_npc; // time_small_npc[i][j] is the number of small npcs that appear on time j in the i-th wave
    private static Level instance;

    private Level(int num_waves, int[] health_small_npc, double[] speed_small_npc, int[] health_med_npc, double[] speed_med_npc, int[] health_big_npc, double[] speed_big_npc, ArrayList<ArrayList<Integer>> time_small_npc, ArrayList<ArrayList<Integer>> time_med_npc, ArrayList<ArrayList<Integer>> time_big_npc){
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
        create_waves();
    }

    public static Level get_instance(int num_waves, int[] health_small_npc, double[] speed_small_npc, int[] health_med_npc, double[] speed_med_npc, int[] health_big_npc, double[] speed_big_npc, ArrayList<ArrayList<Integer>> time_small_npc, ArrayList<ArrayList<Integer>> time_med_npc, ArrayList<ArrayList<Integer>> time_big_npc){
        if (Level.instance==null){Level.instance = new Level(num_waves, health_small_npc, speed_small_npc, health_med_npc, speed_med_npc, health_big_npc, speed_big_npc, time_small_npc, time_med_npc, time_big_npc);}
        return Level.instance;
    }

    public static void create_waves(){
        for (int i=0; i<num_waves; i++){
            Wave wave = new Wave(health_small_npc[i], speed_small_npc[i], health_small_npc[i], speed_med_npc[i], health_med_npc[i], speed_big_npc[i], time_small_npc.get(i), time_med_npc.get(i), time_big_npc.get(i));
            waves.add(wave);
        }
    }

    public static int get_time_small_npc(int wave, int time){return time_small_npc.get(wave).get(time);}
    public static int get_time_med_npc(int wave, int time){return time_med_npc.get(wave).get(time);}
    public static int get_time_big_npc(int wave, int time){return time_big_npc.get(wave).get(time);}
    public static int get_health_small_npc(int wave){return health_small_npc[wave];}
    public static int get_health_med_npc(int wave){return health_med_npc[wave];}
    public static int get_health_big_npc(int wave){return health_big_npc[wave];}
    public static double get_speed_small_npc(int wave){return speed_small_npc[wave];}
    public static double get_speed_med_npc(int wave){return speed_med_npc[wave];}
    public static double get_speed_big_npc(int wave){return speed_big_npc[wave];}
    //public static int get_time_wave(int wave){return time_waves[wave];}
    public static int get_num_waves(){return num_waves;}
    public static ArrayList<Wave> get_waves(){return waves;}
}
