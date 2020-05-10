package Model;

import java.util.ArrayList;
import java.util.List;

public class Level_constructor {
    public Level_constructor(){}

    public static Level construct(int num_level, int fps, double fact){
        if (num_level == 1) return construct_lv1(fps, fact);
        else if (num_level == 2) return construct_lv2(fps, fact);
        else return construct_lv3(fps, fact);
    }

    private static Level construct_lv1(int fps, double fact){
        int init_init_money = 1000;
        int score = 1000;

        int[] health_small_npc = {10, 15, 20};
        int[] health_med_npc = {15, 20, 30};
        int[] health_big_npc = {20, 40, 50};
        double[] speed_small_npc = {15, 20, 25};
        double[] speed_med_npc = {10, 15, 20};
        double[] speed_big_npc = {7.5, 10, 15};

        for (int i=0; i<3; i++){
            speed_small_npc[i] *= (fact/fps);
            speed_med_npc[i] *= (fact/fps);
            speed_big_npc[i] *= (fact/fps);
        }

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

        int time_between_waves = 20;
        int num_waves = time_small_npc.size();
        
        ArrayList<Wave> waves = new ArrayList<>();
        for (int i=0; i<num_waves; i++) waves.add(new Wave(health_small_npc[i], speed_small_npc[i], health_med_npc[i], speed_med_npc[i], health_big_npc[i], speed_big_npc[i], time_small_npc.get(i), time_med_npc.get(i), time_big_npc.get(i), time_between_waves));
        
        return new Level(init_init_money, score, waves, time_between_waves);
    }

    private static Level construct_lv2(int fps, double fact){
        int init_init_money = 1000;
        int score = 500;

        int[] health_small_npc = {15, 20, 25};
        int[] health_med_npc = {20, 25, 35};
        int[] health_big_npc = {30, 50, 60};
        double[] speed_small_npc = {20, 25, 30};
        double[] speed_med_npc = {15, 20, 25};
        double[] speed_big_npc = {10, 15, 20};

        for (int i=0; i<3; i++){
            speed_small_npc[i] *= (fact/fps);
            speed_med_npc[i] *= (fact/fps);
            speed_big_npc[i] *= (fact/fps);
        }

        ArrayList<Integer> time_small_npc1 = new ArrayList<>(List.of(1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_small_npc2 = new ArrayList<>(List.of(1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1));
        ArrayList<Integer> time_small_npc3 = new ArrayList<>(List.of(1, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 3, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0));

        ArrayList<Integer> time_med_npc1 = new ArrayList<>(List.of(0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0));
        ArrayList<Integer> time_med_npc2 = new ArrayList<>(List.of(1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1));
        ArrayList<Integer> time_med_npc3 = new ArrayList<>(List.of(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0));

        ArrayList<Integer> time_big_npc1 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_big_npc2 = new ArrayList<>(List.of(0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0));
        ArrayList<Integer> time_big_npc3 = new ArrayList<>(List.of(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0));

        ArrayList<ArrayList<Integer>> time_small_npc = new ArrayList<>(List.of(time_small_npc1, time_small_npc2, time_small_npc3));
        ArrayList<ArrayList<Integer>> time_med_npc = new ArrayList<>(List.of(time_med_npc1, time_med_npc2, time_med_npc3));
        ArrayList<ArrayList<Integer>> time_big_npc = new ArrayList<>(List.of(time_big_npc1, time_big_npc2, time_big_npc3));

        int time_between_waves = 15;
        int num_waves = time_small_npc.size();

        ArrayList<Wave> waves = new ArrayList<>();
        for (int i=0; i<num_waves; i++) waves.add(new Wave(health_small_npc[i], speed_small_npc[i], health_med_npc[i], speed_med_npc[i], health_big_npc[i], speed_big_npc[i], time_small_npc.get(i), time_med_npc.get(i), time_big_npc.get(i), time_between_waves));

        return new Level(init_init_money, score, waves, time_between_waves);
    }

    private static Level construct_lv3(int fps, double fact){
        int init_init_money = 1000;
        int score = 300;

        int[] health_small_npc = {20, 25, 30};
        int[] health_med_npc = {25, 30, 40};
        int[] health_big_npc = {50, 70, 80};
        double[] speed_small_npc = {25, 30, 35};
        double[] speed_med_npc = {20, 25, 30};
        double[] speed_big_npc = {15, 20, 25};

        for (int i=0; i<3; i++){
            speed_small_npc[i] *= (fact/fps);
            speed_med_npc[i] *= (fact/fps);
            speed_big_npc[i] *= (fact/fps);
        }

        ArrayList<Integer> time_small_npc1 = new ArrayList<>(List.of(1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_small_npc2 = new ArrayList<>(List.of(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1));
        ArrayList<Integer> time_small_npc3 = new ArrayList<>(List.of(1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0));

        ArrayList<Integer> time_med_npc1 = new ArrayList<>(List.of(0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0));
        ArrayList<Integer> time_med_npc2 = new ArrayList<>(List.of(1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 1, 0, 0, 0, 1));
        ArrayList<Integer> time_med_npc3 = new ArrayList<>(List.of(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0));

        ArrayList<Integer> time_big_npc1 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_big_npc2 = new ArrayList<>(List.of(0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0));
        ArrayList<Integer> time_big_npc3 = new ArrayList<>(List.of(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0));

        ArrayList<ArrayList<Integer>> time_small_npc = new ArrayList<>(List.of(time_small_npc1, time_small_npc2, time_small_npc3));
        ArrayList<ArrayList<Integer>> time_med_npc = new ArrayList<>(List.of(time_med_npc1, time_med_npc2, time_med_npc3));
        ArrayList<ArrayList<Integer>> time_big_npc = new ArrayList<>(List.of(time_big_npc1, time_big_npc2, time_big_npc3));

        int time_between_waves = 10;
        int num_waves = time_small_npc.size();

        ArrayList<Wave> waves = new ArrayList<>();
        for (int i=0; i<num_waves; i++) waves.add(new Wave(health_small_npc[i], speed_small_npc[i], health_med_npc[i], speed_med_npc[i], health_big_npc[i], speed_big_npc[i], time_small_npc.get(i), time_med_npc.get(i), time_big_npc.get(i), time_between_waves));

        return new Level(init_init_money, score, waves, time_between_waves);
    }
}
