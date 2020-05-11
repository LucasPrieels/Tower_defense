package Model;

import Controller.Update_manager;
import javafx.application.Platform;
import javafx.util.Pair;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Board implements Serializable {
    private static Board instance = null;
    private ArrayList<NPC> npcs = new ArrayList<>();
    private  ArrayList<Path_custom> paths;
    private ArrayList<Tower> towers = new ArrayList<>();
    private ArrayList<Asteroid> asteroids = new ArrayList<>();
    private ArrayList<Munition> munitions = new ArrayList<>();
    private ArrayList<Movable> movables = new ArrayList<>();
    private int dim_x, dim_y, margin_x, margin_y, max_distance;
    private double proba;

    private Board(int num_level, double fact){
        dim_x = 500;
        dim_y = 300;
        margin_x = 10;
        margin_y = 10;
        if (num_level == 2) proba = 0.85; //For each increase of a certain number (see Board) in the points of each path,
            // there is a probability of proba that we find there an asteroid
        else proba = 0.95;
        max_distance = 40; //Max distance from each asteroid to the nearest path
        this.paths = Path_constructor.construct(num_level, dim_x, fact); // Construct paths
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

    private boolean far_other_paths(Asteroid asteroid, double size_asteroid){ // Check that this asteroid doesn't overlap on a path
        for (int i=0; i<Board.get_instance().get_paths().size(); i++) {
            Path_custom other_path = Board.get_instance().get_paths().get(i);
            for (Pair<Double, Double> pair: other_path.get_pos()){
                if (distance(pair.getKey(),  pair.getValue(), asteroid.get_pos_x(), asteroid.get_pos_y()) <= (((double)get_width_path(i) / 2) + size_asteroid/2 + (double)Big_NPC.get_size_static()/2)){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean far_other_asteroids(Asteroid asteroid, double size_asteroid){ // Check that this asteroid doesn't overlap on another asteroid
        for (Asteroid other_asteroid: asteroids){
            if (distance(asteroid.get_pos_x(), asteroid.get_pos_y(), other_asteroid.get_pos_x(), other_asteroid.get_pos_y()) < size_asteroid){
                return false;
            }
        }
        return true;
    }

    private boolean not_behind_button(Asteroid asteroid){ // Checks that this asteroid is not behind a button
        ArrayList<ArrayList<Double>> forbidden = Update_manager.get_forbidden();
        for (ArrayList<Double> zone: forbidden){
            double fact_x = Update_manager.get_fact_x(), fact_y = Update_manager.get_fact_y();
            if (asteroid.get_pos_x()*fact_x >= zone.get(0) && asteroid.get_pos_x()*fact_x <= zone.get(1) && asteroid.get_pos_y()*fact_y >= zone.get(2) && asteroid.get_pos_y()*fact_y <= zone.get(3)){
                return false;
            }
        }
        return true;
    }

    public void create_asteroids_random(){ // Create randomly asteroids
        if (asteroids.size() > 0) return;
        double size_asteroid = Asteroid.get_size_static();
        for (Path_custom path : paths) {
            for (int i = 0; i < path.get_pos().size(); i += 1500){
                double x = path.get_pos().get(i).getKey(), y = path.get_pos().get(i).getValue();
                // If the position chosen is too close to the lower or the upper side of the canvas, we skip it
                if (x < margin_x + size_asteroid/2 || x > dim_x - margin_x - size_asteroid/2 || Math.random() > proba){
                    // Proba is the probability an asteroid is created at each iteration
                    continue;
                }
                boolean flag = true; // True if the while loop still needs to be ran
                int iter = 0;

                while (flag){
                    double dist = (double)max_distance / 2;
                    double offset_x = Math.sqrt(3)*(Math.random() * 2 * dist - dist), offset_y = Math.random() * 2 * dist - dist;
                    // We generate a random position for the asteroid: offsets are the distance in x and y between the current point in path and where the asteroid will be placed
                    // In the worst case, offset_x = sqrt(3)*dist and offset_y = dist, such as the distance between the path and the asteroid is <= max_distance
                    // It allows us to check that every tower placed on an asteroid will not be too fat to be able to shoot at NPCs on the path

                    Asteroid asteroid;
                    double pos_x = Math.max(0, Math.min(x + offset_x, dim_x - 1)), pos_y; // The x position of the asteroid can't be higher than dim_x - 1
                    if (offset_y > 0){
                        pos_y = Math.min(y + (double)path.get_width() / 2 + size_asteroid/2 + Big_NPC.get_size_static()/2 + offset_y, dim_y - margin_y - size_asteroid/2);
                        // Such as no NPC (Big_NPC is the biggest) travelling on the path overlaps on the asteroid, and that the y position of the asteroid is in the screen
                    }
                    else{
                        pos_y = Math.max(y - (double)path.get_width() / 2 - size_asteroid/2 - Big_NPC.get_size_static()/2 + offset_y, margin_y + size_asteroid/2);
                    }
                    asteroid = new Asteroid(pos_x, pos_y);

                    boolean cond = far_other_paths(asteroid, size_asteroid/2) && far_other_asteroids(asteroid, size_asteroid/2) & not_behind_button(asteroid);

                    if (cond){ // If every condition is respected, the asteroid can be added to the board
                        asteroids.add(asteroid);
                        flag = false; // The while loop is stopped
                    }
                    if (!cond && iter >= 5) flag = false; // If we didn't found an adequate position for an asteroid by searching 5 times, we don't put any and go to the next position
                    iter++;
                }
            }
        }
    }



    public boolean empty(double pos_x, double pos_y, int radius){
        // Returns true if there is not NPC at a max distance of radius from the point (pos_x, pos_y)
        for (NPC npc: npcs){
            if (distance(npc.get_pos_x(), npc.get_pos_y(), pos_x, pos_y) <= radius){
                return false;
            }
        }
        return true;
    }

    private double distance(double pos_x1, double pos_y1, double pos_x2, double pos_y2) {
        return Math.sqrt(Math.pow(pos_x1-pos_x2, 2)+Math.pow(pos_y1-pos_y2, 2));
    }



    public void add_npc(NPC npc){
        npcs.add(npc);
        movables.add(npc);
    }
    public void add_tower(Tower tower){
        towers.add(tower);
        movables.add(tower);
    }
    public void remove_tower(Tower tower){
        towers.remove(tower);
        movables.remove(tower);
    }

    public void add_munition(Munition munition){
        munitions.add(munition);
        movables.add(munition);
    }
    public void remove_munition(Munition munition){
        munitions.remove(munition);
        movables.remove(munition);
    }
    public boolean remove_npc(NPC npc){
        CopyOnWriteArrayList<Munition> copyMunitions = new CopyOnWriteArrayList<>(Board.get_instance().get_munitions());
        for (Munition munition: copyMunitions){
            if (munition.get_npc() == npc){
                Platform.runLater( () -> {
                    remove_munition(munition); // Removes the munitions pointing at the NPC removed
                });
            }
        }
        boolean res = npcs.contains(npc);
        npcs.remove(npc);
        movables.remove(npc);
        return res;
    }



    public static void set_instance(Board instance) {
        Board.instance = instance;
    }

    public int get_margin_x(){ return margin_x;}
    public int get_margin_y(){ return margin_y;}
    public int get_max_distance(){ return max_distance;}
    public ArrayList<NPC> get_npcs(){ return npcs;}
    public ArrayList<Path_custom> get_paths(){ return paths;}
    public ArrayList<Tower> get_towers(){ return towers;}
    public ArrayList<Asteroid> get_asteroids(){ return asteroids;}
    public ArrayList<Munition> get_munitions(){ return munitions;}
    public ArrayList<Movable> get_movables(){ return movables;}
    public int get_dim_x(){return dim_x;}
    public int get_dim_y(){return dim_y;}
    public int get_num_paths(){return paths.size();}
    public int get_width_path(int num_path){return paths.get(num_path).get_width();}
    public double get_ord_path(int num_path){
        // Returns the y position of the leftmost point of path number num_path
        ArrayList<Pair<Double, Double>> pos = paths.get(num_path).get_pos();
        return pos.get(pos.size()-1).getValue();
    }
}
