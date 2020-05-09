package Model;

import View.Main;
import View.Map;
import View.Menu;
import View.Menu_gameover;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Runnable, Serializable {
    private int money, npc_destroyed = 0, score, curr_wave = 0, time_between_waves, fps, price_classic_tower, price_freezing_tower, price_factory_tower, score_lost;
    private static Game instance;
    private ArrayList<ArrayList<Integer>> time_small_npc, time_med_npc, time_big_npc;
    private transient ArrayList<Thread> threads = new ArrayList<>();
    private static final Object key = new Object(), key2 = new Object();
    private Stage stage;
    private boolean game_over = false;
    private ArrayList<Path2> paths = new ArrayList<>();
    private Path2 path1, path2,path3;

    private Game(){ //All the parameters of the game are here
        // Level
        money = 1000;
        score = 500;
        fps = 10;

        int[] health_small_npc = {10, 15, 20};
        int[] health_med_npc = {15, 20, 30};
        int[] health_big_npc = {20, 40, 50};
        double[] speed_small_npc = {15, 15, 15};
        double[] speed_med_npc = {10, 10, 10};
        double[] speed_big_npc = {7, 7, 7};

        for (int i=0; i<3; i++){
            speed_small_npc[i]/=fps;
            speed_med_npc[i]/=fps;
            speed_big_npc[i]/=fps;
        }
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
        ArrayList<Integer> time_small_npc2 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0));
        ArrayList<Integer> time_small_npc3 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0));

        ArrayList<Integer> time_med_npc1 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_med_npc2 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_med_npc3 = new ArrayList<>(List.of(0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0));

        ArrayList<Integer> time_big_npc1 = new ArrayList<>(List.of(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_big_npc2 = new ArrayList<>(List.of(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        ArrayList<Integer> time_big_npc3 = new ArrayList<>(List.of(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

        time_small_npc = new ArrayList<>(List.of(time_small_npc1, time_small_npc2, time_small_npc3));
        time_med_npc = new ArrayList<>(List.of(time_med_npc1, time_med_npc2, time_med_npc3));
        time_big_npc = new ArrayList<>(List.of(time_big_npc1, time_big_npc2, time_big_npc3));

        //Board
        int dim_x = 500;
        int dim_y = 300;
        int margin_x = 15;
        int margin_y = 5;
        double size_asteroid = Map.get_size_asteroid();
        double proba = 1; //For each increase of size_asteroid in x, there is a probability of proba that we find an asteroid with that x-position
        int max_offset = 20; //Max distance from each asteroid to the nearest path

        time_between_waves = 20;

        int width_path = 10;
        int num_waves = time_small_npc.size();

        price_classic_tower = 100;
        price_factory_tower = 200;
        price_freezing_tower = 300;

        score_lost = 100; //Score lost when a PNJ is at the end of the window

        Level.init(num_waves, health_small_npc, speed_small_npc, health_med_npc, speed_med_npc, health_big_npc, speed_big_npc, time_small_npc, time_med_npc, time_big_npc, time_between_waves);
        Board.init(dim_x, dim_y, margin_x, margin_y, width_path, size_asteroid, proba, max_offset, paths);
    }

    public static Game get_instance(){
        if (Game.instance == null) Game.instance = new Game();
        return Game.instance;
    }

    public void run(){ //Appelé par un listener sur un bouton
        Thread thread_munition = new Thread(Board.get_instance());
        Game.get_instance().add_thread(thread_munition);
        thread_munition.start();

        for (int i=0; i<Level.get_instance().get_waves().size(); i++){
            try{
                Thread thread_wave;
                synchronized (key){
                    curr_wave = i;
                    Wave wave = Level.get_instance().get_waves().get(i);
                    thread_wave = new Thread(wave);
                    Game.get_instance().add_thread(thread_wave);
                    thread_wave.start();
                }
                thread_wave.join();
                if (score < 0 ){
                    Map.get_instance().end_game(score);

                    return;
                }

            } catch(InterruptedException e){
                return;
            }
        }
        Map.get_instance().end_game(score);



    }

    public boolean pay(int paid){
        if (money>=paid){
            money -= paid;
            return true;
        }
        else{
            return false;
        }
    }

    public int get_npc_destroyed(){ return npc_destroyed;}
    public int get_score(){ return score;}
    public int get_money(){return money;}
    public void decrease_score(int a){ score -= a;}
    public void increase_money(int a){money += a;}
    public int get_fps(){ return fps;}
    public int get_curr_wave(){ return curr_wave;}

    public void increment_npc_destroyed(){
        npc_destroyed++;
    }

    //public double[] construct_path(int dim_x, int dim_y, int start, int width){
    //    double[] tab = new double[dim_x];
    //    tab[0] = start;
    //    for (int i=1; i<dim_x; i++){
    //        double val = tab[i-1] + Math.random()*2-1;
    //        if (val+width > dim_y){
    //            val = dim_y-width;
    //        }
    //        if (val-width < 0){
    //            val = width;
    //        }
    //        tab[i] = val;
    //    }
    //    return tab;
    //}

    public double[] construct_path_1(int dim_x,int start,int path_num){
        double[] tab = new double[dim_x];
        tab[0] = start;
        for (int i = 1; i < (dim_x/4); i++) {
            double val = tab[0];
            tab[i] = val;
        }
        return tab;
    }
    */

    public double[] construct_path_1(int dim_x, int start, int path_num){
        double[] tab = new double[dim_x];
        tab[0] = start;
        for (int i = 1; i < (dim_x/4); i++) { // Horizontal
            tab[i] = start;
        }
        for (int j = (dim_x/4); j < (dim_x / 4) + 20; j++) { // Diagonal
            double val = -j + tab[0];
            tab[j] = val;
        }
        if(path_num == 1){
            for (int i = (dim_x / 4) + 20; i < dim_x; i++) { // Horizontal
                double val = tab[(dim_x/4)+20-1];
                tab[i] = val;
            }}
        else if (path_num == 2){
            for (int i = (dim_x / 4) + 20; i < dim_x-80; i++) { // Horizontal
                tab[i] = tab[(dim_x/4)+20-1];
            }
            for (int i = dim_x-80; i < dim_x-70; i++){ // Diagonal
                double val = tab[dim_x-70-1] + 0.5*i;
                tab[i] = val;
            }
            for(int i = dim_x-70; i < dim_x; i++){ // Horizontal
                tab[i] = tab[dim_x-70-1];
            }
        }
        return tab;
    }

    public double[] construct_path_2(int dim_x, int start, int path_number){
        double[] tab = new double[dim_x];
        tab[0] = start;
        if(path_number == 1){
            for (int i=1; i<dim_x; i++){
                double val = 47*Math.sin(0.01*i-5.5) + tab[0];
                tab[i] = val;}}
        else if(path_number == 2){
            for(int j=1; j<dim_x;j++){
                double val= -47*Math.sin(0.01*j-5.5) + tab[0];
                tab[j] = val;
            }}
        else if(path_number == 3){
            for(int k=1; k<dim_x;k++){
                double val= -15*Math.sin(0.02*k)+ tab[0];
                tab[k] = val;
            }}
        return tab;
    }

    public double[] construct_path_3(int dim_x, int start, int path_number){
        double[] tab = new double[dim_x];
        tab[0] = start;
        if(path_number == 1 || path_number == 2){
            for (int i=1; i<dim_x/3; i++){
                if(path_number == 1){
                    double val = tab[i-1] + 0.005*i ;  //on commence à dessiner par la gauche
                    tab[i] = val;}
                else{
                    double val = tab[i-1] - 0.005*i ;  //on commence à dessiner par la gauche
                    tab[i] = val;
                }
            }
            for(int j=dim_x/3; j<dim_x; j++){
                double val = tab[dim_x/3-1] + 0.005*((double)dim_x/2);
                tab[j] = val;
            }}
        else if(path_number == 3){
            //for(int k = 1; k<dim_x; k++){
            //    double val = start;
            //    tab[k] = val;}

            for(int k=1; k<dim_x;k++){
                    double val= -15*Math.sin(0.01*k+11)+ tab[0];
                    tab[k] = val;
            }
                //else if(path_number == 3){
                //    double val = -20*Math.cos(0.01*k+7)+ tab[0];
                //    tab[k] = val;
                //}
            //}

            for(int l=dim_x/2;l<dim_x;l++){
                double val = tab[dim_x/2-1];
                tab[l] = val;
            }
            //else if(path_number == 3){
            //    for(int m=1; m<dim_x;m++){
            //        double val = tab[0];
            //        tab[m] = val;

            //}

        }
        return tab;
    }

    public void construct_path(int dim_x, int level) {
        if (level == 1) {
            int width1 = 15, start_path1 = 200;
            double[] pos_path1 = construct_path_1(dim_x, start_path1, 1);
            this.path1 = new Path2(pos_path1, width1);
            int width2 = 15, start_path2 = 200;  //210
            double[] pos_path2 = construct_path_1(dim_x, start_path2, 2);
            this.path2 = new Path2(pos_path2, width2);
            this.paths = new ArrayList<>(List.of(path1, path2));
        } else if (level == 2) {
            int start_path1 = 130, width1 = 15;  //210
            double[] pos_path1 = construct_path_2(dim_x, start_path1, 1);
            int start_path2 = 130, width2 = 10;
            this.path1 = new Path2(pos_path1, width1);
            double[] pos_path2 = construct_path_2(dim_x, start_path2, 2); //elever witdh
            this.path2 = new Path2(pos_path2, width2);
            int start_path3 = 220, width3 = 10;
            double[] pos_path3 = construct_path_2(dim_x, start_path3, 3);
            this.path3 = new Path2(pos_path3, width3);
            this.paths = new ArrayList<>(List.of(path1, path2, path3));
        } else if (level == 3) {
            int start_path1 = 53, width1 = 15;  //210
            double[] pos_path1 = construct_path_3(dim_x, start_path1, 1);
            int start_path2 = 190, width2 = 10;
            this.path1 = new Path2(pos_path1, width1);
            double[] pos_path2 = construct_path_3(dim_x, start_path2, 2); //elever witdh
            this.path2 = new Path2(pos_path2, width2);
            int start_path3 = 230, width3 = 10;
            double[] pos_path3 = construct_path_3(dim_x, start_path3, 3);
            this.path3 = new Path2(pos_path3, width3);
            //int start_path4 = 230, width4 = 10;
            //double[] pos_path4 = construct_path_3(dim_x, start_path4, 4);
            //this.path4 = new Path2(pos_path4, width4);
            this.paths = new ArrayList<>(List.of(path1, path2, path3));
        }
        Board.get_instance().set_paths(paths);
    }

    public int get_time_between_waves(){return time_between_waves;}
    public int get_price_classic_tower(){return price_classic_tower;}
    public int get_price_freezing_tower(){return price_freezing_tower;}
    public int get_price_factory_tower(){return price_factory_tower;}
    public int get_score_lost(){return score_lost;}
    public int get_time_wave(int wave){ return time_small_npc.get(wave).size();}
    
    public static void set_instance(Game instance){
        Game.instance = instance;
        if (instance != null) instance.set_threads(new ArrayList<>());
    }
    public void add_thread(Thread thread){threads.add(thread);}
    public void stop_threads(){
        ArrayList<Thread> copyThreads = threads;
        for (Thread t: copyThreads){
            t.interrupt();
            try{
                t.join(); // Utile?
            } catch (InterruptedException e){}
        }
    }

    public ArrayList<Thread> get_threads(){return threads;}
    public void set_threads(ArrayList<Thread> threads){this.threads = threads;}
    public ArrayList<Path2> get_paths(){return paths;}
}
