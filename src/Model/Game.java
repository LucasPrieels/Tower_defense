package Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Game{
    private static int money, npc_destroyed = 0, score, time = 0, curr_wave = 0;
    private static Game instance;

    private Game(){ //All the parameters of the game are here
        // Level
        int money = 1000;
        int score = 2000;
        int num_waves = 3;

        int[] health_small_npc = {3, 5, 8};
        int[] health_med_npc = {5, 10, 15};
        int[] health_big_npc = {10, 20, 30};
        int[] speed_small_npc = {5, 10, 15};
        int[] speed_med_npc = {3, 5, 7};
        int[] speed_big_npc = {1, 2, 3};

        ArrayList<Integer> time_small_npc1 = new ArrayList<>(List.of(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_small_npc2 = new ArrayList<>(List.of(1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1));
        ArrayList<Integer> time_small_npc3 = new ArrayList<>(List.of(1, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 1, 1, 1, 1, 1, 1));

        ArrayList<Integer> time_med_npc1 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_med_npc2 = new ArrayList<>(List.of(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 1, 0, 0, 0, 1));
        ArrayList<Integer> time_med_npc3 = new ArrayList<>(List.of(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0));

        ArrayList<Integer> time_big_npc1 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_big_npc2 = new ArrayList<>(List.of(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0));
        ArrayList<Integer> time_big_npc3 = new ArrayList<>(List.of(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0));

        ArrayList<ArrayList<Integer>> time_small_npc = new ArrayList<>(List.of(time_small_npc1, time_small_npc2, time_small_npc3));
        ArrayList<ArrayList<Integer>> time_med_npc = new ArrayList<>(List.of(time_med_npc1, time_med_npc2, time_med_npc3));
        ArrayList<ArrayList<Integer>> time_big_npc = new ArrayList<>(List.of(time_big_npc1, time_big_npc2, time_big_npc3));

        //Board
        int dim_x = 200;
        int dim_y = 150;
        int margin_x = 15;
        int margin_y = 15;
        int width_path = 7;
        int size_asteroid = 10;
        double proba = 0.5; //For each increase of size_asteroid in x, there is a probability of proba that we find an asteroid with that x-position
        int max_offset = 20; //Max distance from each asteroid to the nearest path

        Path path1 = new Path(/*...*/);
        Path path2 = new Path(/*...*/);
        ArrayList<Path> paths = new ArrayList<>(List.of(path1, path2));

        Level.get_instance(num_waves, health_small_npc, speed_small_npc, health_med_npc, speed_med_npc, health_big_npc, speed_big_npc, time_small_npc, time_med_npc, time_big_npc);
        Board.get_instance(dim_x, dim_y, margin_x, margin_y, width_path, size_asteroid, proba, max_offset, paths);
    }

    public Game get_instance(){
        if (Game.instance==null){Game.instance = new Game();}
        return Game.instance;
    }

    private void begin(){ //Appelé par un listener sur un bouton
        for (Wave wave: Level.get_waves()){
            Thread t = new Thread(wave);
            t.start();
        }
        if (score>=0) won();
        else game_over();
    }

    public static void won(){
        //...
    }

    public static void game_over(){
        //...
    }

    public static boolean pay(int paid){
        if (money>=paid){
            money -= paid;
            return true;
        }
        else{
            return false;
        }
    }

    public static int get_npc_destroyed(){ return npc_destroyed;}
    public static int get_score(){ return score;}
    public static int get_curr_wave() { return curr_wave;}

    public static void increment_curr_wave() { curr_wave++;}
    public static void increment_npc_destroyed(){ npc_destroyed++;}
}
