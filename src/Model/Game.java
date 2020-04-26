package Model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Game{
    private static int money, npc_destroyed = 0, score, curr_wave = 0, time_between_waves;
    private static Game instance;

    private Game(){ //All the parameters of the game are here
        // Level
        int money = 1000;
        int score = 2000;

        int[] health_small_npc = {3, 5, 8};
        int[] health_med_npc = {5, 10, 15};
        int[] health_big_npc = {10, 20, 30};
        int[] speed_small_npc = {15, 15, 15};
        int[] speed_med_npc = {10, 10, 10};
        int[] speed_big_npc = {7, 7, 7};
/*
        ArrayList<Integer> time_small_npc1 = new ArrayList<>(List.of(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_small_npc2 = new ArrayList<>(List.of(1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1));
        ArrayList<Integer> time_small_npc3 = new ArrayList<>(List.of(1, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 1, 1, 1, 1, 1, 1));

        ArrayList<Integer> time_med_npc1 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_med_npc2 = new ArrayList<>(List.of(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 1, 0, 0, 0, 1));
        ArrayList<Integer> time_med_npc3 = new ArrayList<>(List.of(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0));

        ArrayList<Integer> time_big_npc1 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_big_npc2 = new ArrayList<>(List.of(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0));
        ArrayList<Integer> time_big_npc3 = new ArrayList<>(List.of(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0));

 */
        ArrayList<Integer> time_small_npc1 = new ArrayList<>(List.of(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_small_npc2 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_small_npc3 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

        ArrayList<Integer> time_med_npc1 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_med_npc2 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_med_npc3 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0));

        ArrayList<Integer> time_big_npc1 = new ArrayList<>(List.of(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_big_npc2 = new ArrayList<>(List.of(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_big_npc3 = new ArrayList<>(List.of(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

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

        time_between_waves = 10;

        int start_path1 = 100, width1 = 8;
        int[] pos_path1 = construct_path(dim_x, dim_y, start_path1, width1);
        int start_path2 = 20, width2 = 6;
        int[] pos_path2 = construct_path(dim_x, dim_y, start_path2, width2);
        Path path1 = new Path(pos_path1, width1);
        Path path2 = new Path(pos_path2, width2);
        ArrayList<Path> paths = new ArrayList<>(List.of(path1, path2));
        int num_waves = time_small_npc.size();

        Level.get_instance(num_waves, health_small_npc, speed_small_npc, health_med_npc, speed_med_npc, health_big_npc, speed_big_npc, time_small_npc, time_med_npc, time_big_npc);
        Board.get_instance(dim_x, dim_y, margin_x, margin_y, width_path, size_asteroid, proba, max_offset, paths);
        begin();
    }

    public static Game get_instance(){
        if (Game.instance==null){Game.instance = new Game();}
        return Game.instance;
    }

    private void begin(){ //Appelé par un listener sur un bouton
        for (int i=0; i<Level.get_waves().size(); i++){
            Wave wave = Level.get_waves().get(i);
            Thread t = new Thread(wave);
            t.start();
            /*
            try{
                t.join(); // Normalement il faut le mettre mais ça fonctionne plus si je le mets
            }catch(InterruptedException e){
                System.out.println("Erreur dans le join de la méthode begin() de la classe Game");
            }
            */
            /*
            Platform.setImplicitExit(false);
            wave.setDaemon(true);
            Platform.runLater(wave);
             */
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

    public int[] construct_path(int dim_x, int dim_y, int start, int width){
        int[] tab = new int[dim_x];
        tab[0] = start;
        for (int i=1; i<dim_x; i++){
            int val = tab[0] + ((int)Math.round(Math.random()*4))-2;
            if (val+width > dim_y){
                val = dim_y-width;
            }
            if (val-width < 0){
                val = width;
            }
            tab[i] = val;
        }
        return tab;
    }

    public static int get_time_between_waves(){return time_between_waves;}
}
