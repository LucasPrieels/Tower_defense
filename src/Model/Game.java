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
        money = Level.get_instance().get_init_money(); // Initial money and score depends on the leveel
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

    public void run(){
        for (int i=0; i<Level.get_instance().get_waves().size(); i++){
            try{
                Thread thread_wave;
                synchronized (key){
                    curr_wave = i;
                    Wave wave = Level.get_instance().get_waves().get(i);
                    thread_wave = new Thread(wave);
                    Game.get_instance().add_thread(thread_wave); // Launching a new wave
                    thread_wave.start();
                }
                thread_wave.join(); // We wait for the wave to be finished before launching another one
                if (score < 0){
                    Update_manager.end_game(); // We can't end the game directly from the Game thread, we have to set a boolean end_game = true
                    // the Game Over menu will be launched at the next iteration of update_window (from the JavaFX thread)
                    return;
                }

            } catch(InterruptedException e){
                return;
            }
        }
        Update_manager.end_game(); // All waves are over
    }

    public boolean pay(int paid){ // Returns true and pays if the user as enough money, returns false otherwise
        if (money>=paid){
            money -= paid;
            return true;
        }
        else{
            return false;
        }
    }

    public void stop_threads(){
        CopyOnWriteArrayList<Thread> copyThreads = new CopyOnWriteArrayList<>(threads);
        for (Thread t: copyThreads){
            t.interrupt();
            try{
                t.join();
            } catch (InterruptedException ignored){}
        }
    }

    public static void set_instance(Game instance){
        Game.instance = instance;
        if (instance != null) instance.set_threads(new ArrayList<>());
    }

    private void set_threads(ArrayList<Thread> threads){this.threads = threads;}
    public void increment_npc_destroyed(){ npc_destroyed++;}
    public void add_thread(Thread thread){threads.add(thread);}
    public void decrease_score(int a){ score -= a;}
    public void increase_money(int a){money += a;}

    public int get_npc_destroyed(){ return npc_destroyed;}
    public int get_score(){ return score;}
    public int get_money(){return money;}
    public int get_fps(){ return fps;}
    public int get_curr_wave(){ return curr_wave;}
    public int get_price_classic_tower(){return price_classic_tower;}
    public int get_price_freezing_tower(){return price_freezing_tower;}
    public int get_price_factory_tower(){return price_factory_tower;}
    public int get_score_lost(){return score_lost;}
    public int get_num_level(){ return num_level;}
}
