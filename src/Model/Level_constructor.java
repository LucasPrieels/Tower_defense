package Model;

import java.util.ArrayList;
import java.util.List;

public class Level_constructor {
    private static int init_money, init_score, time_between_waves, fps;
    private static double fact;
    private static ArrayList<Integer> health_small_npc, health_med_npc, health_big_npc;
    private static ArrayList<Double> speed_small_npc, speed_med_npc, speed_big_npc;
    private static ArrayList<Integer> time_small_npc1, time_small_npc2, time_small_npc3, time_med_npc1, time_med_npc2, time_med_npc3, time_big_npc1, time_big_npc2, time_big_npc3;

    public Level_constructor(){}

    public static Level construct(int num_level, int fps, double fact){
        Level_constructor.fps = fps;
        Level_constructor.fact = fact;
        if (num_level == 1) return construct_lv1();
        else if (num_level == 2) return construct_lv2();
        else return construct_lv3();
    }

    private static Level construct_lv1(){
        init_money = 1000;
        init_score = 1000;

        health_small_npc = new ArrayList<>(List.of(10, 15, 20));
        health_med_npc = new ArrayList<>(List.of(15, 20, 30));
        health_big_npc = new ArrayList<>(List.of(20, 40, 50));
        speed_small_npc = new ArrayList<>(List.of(15.0, 20.0, 25.0));
        speed_med_npc = new ArrayList<>(List.of(10.0, 15.0, 20.0));
        speed_big_npc = new ArrayList<>(List.of(7.5, 10.0, 15.0));

        time_small_npc1 = new ArrayList<>(List.of(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        time_small_npc2 = new ArrayList<>(List.of(1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1));
        time_small_npc3 = new ArrayList<>(List.of(1, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 1, 1, 1, 1, 1, 1));

        time_med_npc1 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0));
        time_med_npc2 = new ArrayList<>(List.of(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 1, 0, 0, 0, 1));
        time_med_npc3 = new ArrayList<>(List.of(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0));

        time_big_npc1 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0));
        time_big_npc2 = new ArrayList<>(List.of(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0));
        time_big_npc3 = new ArrayList<>(List.of(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0));

        time_between_waves = 20;

        return prepare_level();
    }

    private static Level construct_lv2(){
        init_money = 1000;
        init_score = 500;

        health_small_npc = new ArrayList<>(List.of(15, 20, 25));
        health_med_npc = new ArrayList<>(List.of(20, 25, 35));
        health_big_npc = new ArrayList<>(List.of(30, 50, 60));
        speed_small_npc = new ArrayList<>(List.of(20.0, 25.0, 30.0));
        speed_med_npc = new ArrayList<>(List.of(15.0, 20.0, 25.0));
        speed_big_npc = new ArrayList<>(List.of(10.0, 15.0, 20.0));

        for (int i=0; i<3; i++){
            speed_small_npc.set(i, fact/fps);
            speed_med_npc.set(i, fact/fps);
            speed_big_npc.set(i, fact/fps);
        }

        time_small_npc1 = new ArrayList<>(List.of(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 0));
        time_small_npc2 = new ArrayList<>(List.of(1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1));
        time_small_npc3 = new ArrayList<>(List.of(1, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 3, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0));

        time_med_npc1 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0));
        time_med_npc2 = new ArrayList<>(List.of(1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1));
        time_med_npc3 = new ArrayList<>(List.of(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0));

        time_big_npc1 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0));
        time_big_npc2 = new ArrayList<>(List.of(0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0));
        time_big_npc3 = new ArrayList<>(List.of(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0));

        return prepare_level();
    }

    private static Level construct_lv3(){
        init_money = 1000;
        init_score = 300;

         health_small_npc = new ArrayList<>(List.of(20, 25, 30));
         health_med_npc = new ArrayList<>(List.of(25, 30, 40));
        health_big_npc = new ArrayList<>(List.of(50, 70, 80));
        speed_small_npc = new ArrayList<>(List.of(25.0, 30.0, 35.0));
        speed_med_npc = new ArrayList<>(List.of(20.0, 25.0, 30.0));
        speed_big_npc = new ArrayList<>(List.of(15.0, 20.0, 25.0));

        for (int i=0; i<3; i++){
            speed_small_npc.set(i, fact/fps);
            speed_med_npc.set(i, fact/fps);
            speed_big_npc.set(i, fact/fps);
        }

        time_small_npc1 = new ArrayList<>(List.of(1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0));
        time_small_npc2 = new ArrayList<>(List.of(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1));
        time_small_npc3 = new ArrayList<>(List.of(1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0));

        time_med_npc1 = new ArrayList<>(List.of(0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0));
        time_med_npc2 = new ArrayList<>(List.of(1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 1, 0, 0, 0, 1));
        time_med_npc3 = new ArrayList<>(List.of(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0));

        time_big_npc1 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0));
        time_big_npc2 = new ArrayList<>(List.of(0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0));
        time_big_npc3 = new ArrayList<>(List.of(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0));

        return prepare_level();
    }

    public static Level prepare_level(){
        for (int i=0; i<3; i++){
            speed_small_npc.set(i, speed_small_npc.get(i)*fact/fps);
            speed_med_npc.set(i, speed_small_npc.get(i)*fact/fps);
            speed_big_npc.set(i, speed_small_npc.get(i)*fact/fps);
        }

        ArrayList<ArrayList<Integer>> time_small_npc = new ArrayList<>(List.of(time_small_npc1, time_small_npc2, time_small_npc3));
        ArrayList<ArrayList<Integer>> time_med_npc = new ArrayList<>(List.of(time_med_npc1, time_med_npc2, time_med_npc3));
        ArrayList<ArrayList<Integer>> time_big_npc = new ArrayList<>(List.of(time_big_npc1, time_big_npc2, time_big_npc3));

        int num_waves = time_small_npc.size();

        ArrayList<Wave> waves = new ArrayList<>();
        for (int i=0; i<num_waves; i++){
            ArrayList<Integer> health_npc = new ArrayList<>(List.of(health_small_npc.get(i), health_med_npc.get(i), health_big_npc.get(i)));
            ArrayList<Double> speed_npc = new ArrayList<>(List.of(speed_small_npc.get(i), speed_med_npc.get(i), speed_big_npc.get(i)));
            ArrayList<ArrayList<Integer>> time_npc = new ArrayList<>(List.of(time_small_npc.get(i), time_med_npc.get(i), time_big_npc.get(i)));

            waves.add(new Wave(health_npc, speed_npc, time_npc, time_between_waves));
        }

        return new Level(init_money, init_score, waves, time_between_waves);
    }
}
