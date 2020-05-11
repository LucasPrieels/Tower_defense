package Model;

import Controller.Update_manager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game implements Runnable, Serializable{
    private int money, npc_destroyed = 0, score, curr_wave = 0, fps, price_classic_tower, price_freezing_tower, price_factory_tower, score_lost, num_level;
    private double fact;
    private static Game instance;
    private transient ArrayList<Thread> threads = new ArrayList<>();
    private static final Object key = new Object();

    private Game(int num_level){
        fps = 20;
        fact = 15; // Number of points for each increment of 1 in x or y (~ resolution)

        price_factory_tower = 100;
        price_classic_tower = 200;
        price_freezing_tower = 200;

        score_lost = 100; //Score lost when a PNJ is at the end of the window

        Level.init(num_level, fps, fact);
        money = Level.get_instance().get_init_money();
        score = Level.get_instance().get_init_score();
        this.num_level = num_level;

        Board.init(num_level, fact);
    }

    public static Game get_instance() {
        if (instance == null) {
            throw new AssertionError("Game has to be initialized before getting accessed");
        }
        return instance;
    }

    public static void init(int num_level) {
        if (instance != null) {
            throw new AssertionError("Game can't be initialized twice");
        }
        instance = new Game(num_level);
    }

    public void run(){ //Appelé par un listener sur un bouton
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
                if (score < 0){
                    Update_manager.end_game();
                    return;
                }

            } catch(InterruptedException e){
                return;
            }
        }
        Update_manager.end_game();
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

    public int get_price_classic_tower(){return price_classic_tower;}
    public int get_price_freezing_tower(){return price_freezing_tower;}
    public int get_price_factory_tower(){return price_factory_tower;}
    public int get_score_lost(){return score_lost;}

    public static void set_instance(Game instance){
        Game.instance = instance;
        if (instance != null) instance.set_threads(new ArrayList<>());
    }

    public void add_thread(Thread thread){threads.add(thread);}

    public void stop_threads(){
        CopyOnWriteArrayList<Thread> copyThreads = new CopyOnWriteArrayList<>(threads);
        for (Thread t: copyThreads){
            t.interrupt();
            try{
                t.join(); // Utile?
            } catch (InterruptedException ignored){}
        }
    }

    public void set_threads(ArrayList<Thread> threads){this.threads = threads;}
    public int get_num_level(){ return num_level;}
}
