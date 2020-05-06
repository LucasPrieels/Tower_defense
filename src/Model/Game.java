package Model;

import View.Map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Runnable, Serializable {
    private int money, npc_destroyed = 0, score, curr_wave = 0, time_between_waves, fps, price_classic_tower, price_freezing_tower, price_factory_tower, score_lost;
    private static Game instance;
    private ArrayList<ArrayList<Integer>> time_small_npc, time_med_npc, time_big_npc;
    private transient ArrayList<Thread> threads = new ArrayList<>();
    private static final Object key = new Object(), key2 = new Object();

    private Game(){ //All the parameters of the game are here
        // Level
        money = 1000;
        score = 1000;
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
        int margin_y = 15;
        int width_path = 7;
        double size_asteroid = Map.get_size_asteroid();
        double proba = 1; //For each increase of size_asteroid in x, there is a probability of proba that we find an asteroid with that x-position
        int max_offset = 20; //Max distance from each asteroid to the nearest path

        time_between_waves = 20;

        int start_path1 = 237, width1 = 15;  //210
        double[] pos_path1 = construct_path(dim_x, dim_y, start_path1, width1,2);
        int start_path2 = 100, width2 = 10;
        double[] pos_path2 = construct_path(dim_x, dim_y, start_path2, width2,1);
        Path2 path1 = new Path2(pos_path1, width1);
        Path2 path2 = new Path2(pos_path2, width2);
        ArrayList<Path2> paths = new ArrayList<>(List.of(path1, path2));
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
                if (score <= 0){
                    Map.get_instance().game_over();
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

    public double[] construct_path(int dim_x, int dim_y, int start, int width, int path_number){
           double[] tab = new double[dim_x];
            tab[0] = start;
            for (int i=1; i<dim_x/3; i++){
                if(path_number == 1){
                double val = tab[i-1] + 0.005*i ;  //on commence à dessiner par la gauche
                tab[i] = val;}
                else if(path_number == 2){
                    double val = tab[i-1] - 0.005*i ;  //on commence à dessiner par la gauche
                    tab[i] = val;
                }
            }
            for(int j=dim_x/3; j<dim_x; j++){
                double val = tab[dim_x/3-1] + 0.005*(dim_x/2);
                tab[j] = val;
            }
            return tab;
        }

    //public double[] construct_path_2(int dim_x, int dim_y, int start, int width){
    //    double[] tab = new double[dim_x];
    //    tab[0] = start;
    //    for (int i=1; i<dim_x/3; i++){
    //        double val = tab[i-1] - 0.005*i ;  //on commence à dessiner par la gauche
    //        tab[i] = val;
    //    }
    //    for(int j=dim_x/3; j<dim_x; j++){
    //        double val = tab[dim_x/3-1] + 0.005*(dim_x/2);
    //         tab[j] = val;
    //    }
    //    return tab;
    //}





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
        }
        for (Thread t: copyThreads){
            try{
                t.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Thread> get_threads(){return threads;}
    public void set_threads(ArrayList<Thread> threads){this.threads = threads;}
}
