package Model;

import View.Map;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Board implements Runnable, Serializable {
    private static Board instance = null;
    private ArrayList<NPC> npcs = new ArrayList<>();
    private  ArrayList<Path_custom> paths;
    private ArrayList<Tower> towers = new ArrayList<>();
    private ArrayList<Asteroid> asteroids = new ArrayList<>();
    private ArrayList<Munition> munitions = new ArrayList<>();
    private int dim_x, dim_y, margin_x, margin_y, max_distance;
    private double proba;
    public static final Object key = new Object();

    private Board(int num_level, double fact){
        dim_x = 500;
        dim_y = 300;
        margin_x = 10;
        margin_y = 10;
        proba = 0.8; //For each increase of a certain number (see Board) in x, there is a probability of proba that we find an asteroid with that x-position
        max_distance = 40; //Max distance from each asteroid to the nearest path
        this.paths = Path_constructor.construct(num_level, dim_x, fact);
    }

    //Singleton
    public static void init(int num_level, double fact){
        if (instance != null){
            throw new AssertionError("Board can't be initalized twice");
        }
        instance = new Board(num_level, fact);
    }

    public static Board get_instance(){
        if (Board.instance == null){
            throw new AssertionError("Board has to be initialized before getting accessed");
        }
        return Board.instance;
    }

    private boolean far_other_paths(Asteroid asteroid, double height_asteroid){
        for (int i=0; i<Board.get_instance().get_paths().size(); i++) {
            Path_custom other_path = Board.get_instance().get_paths().get(i);
            for (Pair<Double, Double> pair: other_path.get_pos()){
                if (distance(pair.getKey(),  pair.getValue(), asteroid.get_pos_x(), asteroid.get_pos_y()) <= (((double)get_width_path(i) / 2) + height_asteroid/2 + (double)Big_NPC.get_radius_static()/2)){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean far_other_asteroids(Asteroid asteroid, double biggest_dim_asteroid){
        for (Asteroid other_asteroid: asteroids){
            if (distance(asteroid.get_pos_x(), asteroid.get_pos_y(), other_asteroid.get_pos_x(), other_asteroid.get_pos_y()) < biggest_dim_asteroid){
                return false;
            }
        }
        return true;
    }

    private boolean not_behind_button(Asteroid asteroid, Map instance){
        if (instance == null) return true;
        ArrayList<ArrayList<Double>> forbidden = instance.get_forbidden();
        for (ArrayList<Double> zone: forbidden){
            if (asteroid.get_pos_x() >= zone.get(0) && asteroid.get_pos_x() <= zone.get(1) && asteroid.get_pos_y() >= zone.get(2) && asteroid.get_pos_y() <= zone.get(3)){
                return false;
            }
        }
        return true;
    }

    public void create_asteroids_random(){
        double height_asteroid = Map.get_size_asteroid(), width_asteroid = Map.get_size_asteroid();
        Map instance = null;
        try{
            instance = Map.get_instance();
        } catch (AssertionError e){}
        if (instance != null) {
            height_asteroid /= Map.get_instance().get_fact_y();
            width_asteroid /= Map.get_instance().get_fact_x();
        }
        for (Path_custom path : paths) {
            for (int i = 0; i < path.get_pos().size(); i += 1500){
                double x = path.get_pos().get(i).getKey(), y = path.get_pos().get(i).getValue();
                if (x < margin_x + width_asteroid || x > dim_x - margin_x - width_asteroid || Math.random() > proba){
                    // Proba is the probability an asteroid is created at each iteration of x
                    continue;
                }
                boolean flag = true;
                int iter = 0;

                while (flag){
                    double dist = (double)max_distance / 2; // In the worst case, offset_x = sqrt(3)*dist and offset_y = dist, such as the distance if max_distance
                    double offset_x = Math.sqrt(3)*(Math.random() * 2 * dist - dist), offset_y = Math.random() * 2 * dist - dist;

                    Asteroid asteroid;
                    double pos_x = Math.max(0, Math.min(dim_x - 1, x + offset_x)), pos_y;
                    if (offset_y > 0){
                        pos_y = Math.min(y + (double)path.get_width() / 2 + height_asteroid/2 + (double)Big_NPC.get_radius_static()/2 + offset_y, dim_y - margin_y - height_asteroid/2 - (double)Big_NPC.get_radius_static()/2);
                    }
                    else{
                        pos_y = Math.max(y - (double)path.get_width() / 2 - height_asteroid/2 - (double)Big_NPC.get_radius_static()/2 + offset_y, margin_y + height_asteroid/2 + (double)Big_NPC.get_radius_static()/2);
                    }
                    asteroid = new Asteroid(pos_x, pos_y);

                    boolean cond = far_other_paths(asteroid, height_asteroid) && far_other_asteroids(asteroid, Math.max(height_asteroid, width_asteroid)) & not_behind_button(asteroid, instance);

                    if (cond){
                        asteroids.add(asteroid);
                        flag = false;}
                    if (!cond && iter >= 5) flag = false; // If we didn't found an adequate position for an asteroid by searching 10 times, we don't put one and go to the next position
                    iter++;
                }
            }
        }
    }

    public boolean remove_npc(NPC npc){
        CopyOnWriteArrayList<Munition> copyMunitions = new CopyOnWriteArrayList<>(Board.get_instance().get_munitions());
        for (Munition munition: copyMunitions){
            if (munition.get_npc() == npc){
                Platform.runLater( () -> {
                    remove_munition(munition); // On retire les autres munitions qui visaient le PNJ détruit sinon elles restent sur place
                });
            }
        }
        boolean res = npcs.contains(npc);
        Board.get_instance().get_npcs().remove(npc);
        return res;
    }

    public void remove_munition(Munition munition){munitions.remove(munition);}

    public void add_npc(NPC npc){ npcs.add(npc);}
    public void add_tower(Tower tower){ towers.add(tower);}
    public void remove_tower(Tower tower){towers.remove(tower);}
    public void add_munition(Munition munition){ munitions.add(munition);}

    public ArrayList<NPC> get_npcs(){ return npcs;} // Utiliser polymorphisme pour éviter répétition? Sans instanceof?
    public ArrayList<Path_custom> get_paths(){ return paths;}
    public ArrayList<Tower> get_towers(){ return towers;}
    public ArrayList<Asteroid> get_asteroids(){ return asteroids;}
    public ArrayList<Munition> get_munitions(){ return munitions;}
    public int get_max_distance(){ return max_distance;}

    public int get_dim_x(){return dim_x;}
    public int get_dim_y(){return dim_y;}
    public int get_num_paths(){return paths.size();}
    public int get_width_path(int num_path){return paths.get(num_path).get_width();}
    public double get_ord_path(int num_path){
        ArrayList<Pair<Double, Double>> pos = paths.get(num_path).get_pos();
        return pos.get(pos.size()-1).getValue();
    }

    public boolean empty(double pos_x, double pos_y, int radius){
        for (NPC npc: npcs){
            if (distance(npc.get_pos_x(), npc.get_pos_y(), pos_x, pos_y) <= radius + npc.get_radius()){
                return false;
            }
        }
        return true;
    }

    private double distance(double pos_x1, double pos_y1, double pos_x2, double pos_y2) {
        return Math.sqrt(Math.pow(pos_x1-pos_x2, 2)+Math.pow(pos_y1-pos_y2, 2));
    }

    public void run(){
        while (true){
            synchronized (key){
                CopyOnWriteArrayList<Munition> copyMunitions = new CopyOnWriteArrayList<>(Board.get_instance().get_munitions());
                for (Munition munition: copyMunitions){
                    munition.update();
                    if (munition.check_shot_npc() && munitions.contains(munition)){ // Check if a munition has been removed from munitions but is still in its copy
                        System.out.println("Munition détruite");
                        remove_munition(munition);
                    }
                }
            }
            try{
                Thread.sleep(1000/Game.get_instance().get_fps());
            } catch(InterruptedException e){
                return;
            }
        }
    }

    public static void set_instance(Board instance) {
        Board.instance = instance;
    }

    public void set_paths(ArrayList<Path_custom> paths){
        this.paths = paths;
    }
}
