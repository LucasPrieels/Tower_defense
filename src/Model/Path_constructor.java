package Model;

import javafx.scene.shape.Path;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Path_constructor {
    private static double fact; // Number of points to add for each increment of 1 in x or y direction

    public Path_constructor() {}

    public static ArrayList<Path_custom> construct(int num_level, int dim_x, double fact){
        Path_constructor.fact = fact;

        Path_custom path1, path2, path3;
        ArrayList<Path_custom> paths = new ArrayList<>();
        if (num_level == 1) {
            int width1 = 10, start_path1 = 200;
            ArrayList<Pair<Double, Double>> pos_path1 = construct_path_1(dim_x, start_path1, 1);
            path1 = new Path_custom(pos_path1, width1);
            int width2 = 10, start_path2 = 200;  //210
            ArrayList<Pair<Double, Double>> pos_path2 = construct_path_1(dim_x, start_path2, 2);
            path2 = new Path_custom(pos_path2, width2);
            paths = new ArrayList<>(List.of(path1, path2));
        } else if (num_level == 2) {
            int start_path1 = 90, width1 = 10;  //210
            ArrayList<Pair<Double, Double>> pos_path1 = construct_path_2(dim_x, start_path1, 1);
            int start_path2 = 90, width2 = 10;
            path1 = new Path_custom(pos_path1, width1);
            ArrayList<Pair<Double, Double>> pos_path2 = construct_path_2(dim_x, start_path2, 2);
            path2 = new Path_custom(pos_path2, width2);
            int start_path3 = 180, width3 = 10;
            ArrayList<Pair<Double, Double>> pos_path3 = construct_path_2(dim_x, start_path3, 3);
            path3 = new Path_custom(pos_path3, width3);
            paths = new ArrayList<>(List.of(path1, path2, path3));
        } else if (num_level == 3) {
            int start_path1 = 93, width1 = 10;  //210
            ArrayList<Pair<Double, Double>> pos_path1 = construct_path_3(dim_x, start_path1, 1);
            int start_path2 = 213, width2 = 10;
            path1 = new Path_custom(pos_path1, width1);
            ArrayList<Pair<Double, Double>> pos_path2 = construct_path_3(dim_x, start_path2, 2);
            path2 = new Path_custom(pos_path2, width2);
            int start_path3 = 250, width3 = 10;
            ArrayList<Pair<Double, Double>> pos_path3 = construct_path_3(dim_x, start_path3, 3);
            path3 = new Path_custom(pos_path3, width3);
            paths = new ArrayList<>(List.of(path1, path2, path3));
        }
        return paths;
    }

    private static ArrayList<Pair<Double, Double>> construct_path_1(int dim_x, double start, int path_num){
        ArrayList<Pair<Double, Double>> tab = new ArrayList<>();
        double x = 0, y = start;
        if (path_num == 1){
            while (x < dim_x){
                tab.add(new Pair<>(x, y));
                if (x >= (double)dim_x/4 && x < (double)dim_x/4 + 20){
                    Pair<Double, Double> pair = increment_pos(x, y, 1, -7); // Diagonal
                    x = pair.getKey();
                    y = pair.getValue();
                }
                else {
                    Pair<Double, Double> pair = increment_pos(x, y, 1, 0); // Horizontal
                    x = pair.getKey();
                    y = pair.getValue();
                }
            }
        }
        else{
            while (x < dim_x){
                tab.add(new Pair<>(x, y));
                if (x >= (double)dim_x/4 && x < (double)dim_x/4 + 20){
                    Pair<Double, Double> pair = increment_pos(x, y, 1, -7); // Diagonal (pente positive)
                    x = pair.getKey();
                    y = pair.getValue();
                }
                else if (x >= dim_x - 100 && x < dim_x - 80){
                    Pair<Double, Double> pair = increment_pos(x, y, 1, 8); // Diagonal (pente négative)
                    x = pair.getKey();
                    y = pair.getValue();
                }
                else {
                    Pair<Double, Double> pair = increment_pos(x, y, 1, 0); // Horizontal
                    x = pair.getKey();
                    y = pair.getValue();
                }
            }
        }
        return tab;
    }

    private static ArrayList<Pair<Double, Double>> construct_path_2(int dim_x, int start, int path_num){
        ArrayList<Pair<Double, Double>> tab = new ArrayList<>();
        double x = 0, y = start;
        if (path_num == 1){
            while (x < dim_x){
                tab.add(new Pair<>(x, y));
                Pair<Double, Double> pair = increment_pos(x, y, 1, 47*Math.sin(0.01*x-5.5) - 47*Math.sin(0.01*(x-1)-5.5));
                x = pair.getKey();
                y = pair.getValue();
            }
        }
        else if (path_num == 2){
            while (x < dim_x){
                tab.add(new Pair<>(x, y));
                Pair<Double, Double> pair = increment_pos(x, y, 1, -47*Math.sin(0.01*x-5.5) + 47*Math.sin(0.01*(x-1)-5.5));
                x = pair.getKey();
                y = pair.getValue();
            }
        }
        else{
            while (x < dim_x){
                tab.add(new Pair<>(x, y));
                Pair<Double, Double> pair = increment_pos(x, y, 1, -15*Math.sin(0.01*x-5.5) + 15*Math.sin(0.01*(x-1)-5.5));
                x = pair.getKey();
                y = pair.getValue();
            }
        }
        return tab;
    }

    private static ArrayList<Pair<Double, Double>> construct_path_3(int dim_x, int start, int path_num) {
        ArrayList<Pair<Double, Double>> tab = new ArrayList<>();
        double x = 0, y = start;
        if (path_num == 1) {
            while (x < dim_x) {
                if (x < (double) dim_x / 3) {
                    tab.add(new Pair<>(x, y));
                    Pair<Double, Double> pair = increment_pos(x, y, 1, 0.003 * x);
                    x = pair.getKey();
                    y = pair.getValue();
                } else {
                    tab.add(new Pair<>(x, y));
                    Pair<Double, Double> pair = increment_pos(x, y, 1, 0);
                    x = pair.getKey();
                    y = pair.getValue();
                }
            }
        } else if (path_num == 2) {
            while (x < dim_x) {
                if (x < (double) dim_x / 3) {
                    tab.add(new Pair<>(x, y));
                    Pair<Double, Double> pair = increment_pos(x, y, 1, -0.005 * x);
                    x = pair.getKey();
                    y = pair.getValue();
                } else {
                    tab.add(new Pair<>(x, y));
                    Pair<Double, Double> pair = increment_pos(x, y, 1, 0);
                    x = pair.getKey();
                    y = pair.getValue();
                }
            }
        } else {
            while (x < dim_x) {
                if (x < (double) dim_x / 2) {
                    tab.add(new Pair<>(x, y));
                    Pair<Double, Double> pair = increment_pos(x, y, 1, -15 * Math.sin(0.01 * x + 11) + 15 * Math.sin(0.01 * (x - 1) + 11));
                    x = pair.getKey();
                    y = pair.getValue();
                } else {
                    tab.add(new Pair<>(x, y));
                    Pair<Double, Double> pair = increment_pos(x, y, 1, 0);
                    x = pair.getKey();
                    y = pair.getValue();
                }
            }
        }
        return tab;
    }

    private static Pair<Double, Double> increment_pos(double x, double y, double dx, double dy){
        dx /= Math.sqrt(dx*dx + dy*dy);
        dy /= Math.sqrt(dx*dx + dy*dy);
        dx /= fact;
        dy /= fact;
        return new Pair<>(x+dx, y+dy);
    }
}
