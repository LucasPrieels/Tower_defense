package Model;

import View.Map;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable {
    private int num_waves, time_between_waves;
    private ArrayList<Wave> waves = new ArrayList<>();
    private int[] health_small_npc, health_med_npc, health_big_npc; //i-th element is the health or speed of that type on npc for the i-th wave
    private double[] speed_small_npc, speed_med_npc, speed_big_npc;
    private ArrayList<ArrayList<Integer>> time_small_npc, time_med_npc, time_big_npc; // time_small_npc[i][j] is the number of small npcs that appear on time j in the i-th wave
    private static Level instance;

    private Level(int num_waves, int[] health_small_npc, double[] speed_small_npc, int[] health_med_npc, double[] speed_med_npc, int[] health_big_npc, double[] speed_big_npc, ArrayList<ArrayList<Integer>> time_small_npc, ArrayList<ArrayList<Integer>> time_med_npc, ArrayList<ArrayList<Integer>> time_big_npc, int time_between_waves){
        this.num_waves = num_waves;
        this.health_small_npc = health_small_npc;
        this.speed_small_npc = speed_small_npc;
        this.health_med_npc = health_med_npc;
        this.speed_med_npc = speed_med_npc;
        this.health_big_npc = health_big_npc;
        this.speed_big_npc = speed_big_npc;
        this.time_small_npc = time_small_npc;
        this.time_med_npc = time_med_npc;
        this.time_big_npc = time_big_npc;
        this.time_between_waves = time_between_waves;
        //Exception pour vérifier que les time_..._npc ont la même taille
        create_waves();
    }
    public static Level get_instance() {
        if (instance == null) {
            throw new AssertionError("Level has to be initialized before getting accessed");
        }
        return instance;
    }

    public static void init(int num_waves, int[] health_small_npc, double[] speed_small_npc, int[] health_med_npc, double[] speed_med_npc, int[] health_big_npc, double[] speed_big_npc, ArrayList<ArrayList<Integer>> time_small_npc, ArrayList<ArrayList<Integer>> time_med_npc, ArrayList<ArrayList<Integer>> time_big_npc, int time_between_waves) {
        if (instance != null) {
            throw new AssertionError("Level can't be initialized twice");
        }
        instance = new Level(num_waves, health_small_npc, speed_small_npc, health_med_npc, speed_med_npc, health_big_npc, speed_big_npc, time_small_npc, time_med_npc, time_big_npc, time_between_waves);;
    }

    public void create_waves(){
        for (int i=0; i<num_waves; i++){
            Wave wave = new Wave(health_small_npc[i], speed_small_npc[i], health_med_npc[i], speed_med_npc[i], health_big_npc[i], speed_big_npc[i], time_small_npc.get(i), time_med_npc.get(i), time_big_npc.get(i), time_between_waves);
            waves.add(wave);
        }
    }

    public int get_time_small_npc(int wave, int time){return time_small_npc.get(wave).get(time);}
    public int get_time_med_npc(int wave, int time){return time_med_npc.get(wave).get(time);}
    public int get_time_big_npc(int wave, int time){return time_big_npc.get(wave).get(time);}
    public int get_health_small_npc(int wave){return health_small_npc[wave];}
    public int get_health_med_npc(int wave){return health_med_npc[wave];}
    public int get_health_big_npc(int wave){return health_big_npc[wave];}
    public double get_speed_small_npc(int wave){return speed_small_npc[wave];}
    public double get_speed_med_npc(int wave){return speed_med_npc[wave];}
    public double get_speed_big_npc(int wave){return speed_big_npc[wave];}
    //public int get_time_wave(int wave){return time_waves[wave];}
    public int get_num_waves(){return num_waves;}
    public ArrayList<Wave> get_waves(){return waves;}

    public static void set_instance(Level instance) {
        Level.instance = instance;
    }
}
