package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable {
    private int time_between_waves, init_money, init_score;
    private ArrayList<Wave> waves;
    private static Level instance;

    protected Level(int init_money, int init_score, ArrayList<Wave> waves, int time_between_waves){
        this.init_money = init_money;
        this.init_score = init_score;
        this.waves = waves;
        this.time_between_waves = time_between_waves;
    }

    public static Level get_instance() {
        if (instance == null) {
            throw new AssertionError("Level has to be initialized before getting accessed");
        }
        return instance;
    }

    public static void init(int num_level, int fps, double fact) {
        if (instance != null) {
            throw new AssertionError("Level can't be initialized twice");
        }
        instance = Level_constructor.construct(num_level, fps, fact);
    }

    public int get_num_waves(){ return waves.size();}
    public ArrayList<Wave> get_waves(){ return waves;}
    public int get_init_money(){ return init_money;}
    public int get_init_score(){ return init_score;}
    public static void set_instance(Level instance){ Level.instance = instance;}
    public int get_time_between_waves(){return time_between_waves;}
}
