package Model;

import View.Map;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Board implements Runnable, Serializable {
    private static Board instance = null;
    private ArrayList<NPC> npcs = new ArrayList<>();
    private  ArrayList<Path2> paths;
    private ArrayList<Tower> towers = new ArrayList<>();
    private ArrayList<Asteroid> asteroids = new ArrayList<>();
    private ArrayList<Munition> munitions = new ArrayList<>();
    private int dim_x, dim_y, margin_x, margin_y, width_path, max_offset;
    private double proba, size_asteroid;
    public static final Object key = new Object();

    private Board(int dim_x, int dim_y, int margin_x, int margin_y, int width_path, double size_asteroid, double proba, int max_offset, ArrayList<Path2> paths){
        this.dim_x = dim_x;
        this.dim_y = dim_y;
        this.margin_x = margin_x;
        this.margin_y = margin_y;
        this.width_path = width_path;
        // Ajouter exceptions dans les cas où les dimensions et marges ne sont pas compatibles
        this.size_asteroid = size_asteroid;
        this.proba = proba;
        this.max_offset = max_offset;
        this.paths = paths;
    }

    //Singleton
    public static void init(int dim_x, int dim_y, int margin_x, int margin_y, int width_path, double size_asteroid, double proba, int max_offset, ArrayList<Path2> paths){
        if (instance != null){
            throw new AssertionError("Board can't be initalized twice");
        }
        instance = new Board(dim_x, dim_y, margin_x, margin_y, width_path, size_asteroid, proba, max_offset, paths);
    }

    public static Board get_instance(){
        if (Board.instance == null){
            throw new AssertionError("Board has to be initialized before getting accessed");
        }
        return Board.instance;
    }

    public void create_asteroids_random(){
        for (double x=margin_x + Map.get_size_asteroid()/2; x < dim_x - margin_x - Map.get_size_asteroid()/2; x += size_asteroid){
            for (Path2 path : paths) {
                if (Math.random() < proba) { // Proba is the probability an asteroid is created at each iteration of x
                    boolean flag = true;
                    int iter = 0;

                    while (flag){
                        double y = path.get_ord((int)Math.round(x));
                        double offset = Math.random() * 2 * max_offset - max_offset;

                        Asteroid asteroid;
                        if (offset>0) asteroid = new Asteroid(x, Math.min(y + (double)width_path / 2 + Map.get_size_asteroid()/2 + offset, dim_y - margin_y - Map.get_size_asteroid()/2));
                        else asteroid = new Asteroid(x, Math.max(y - (double)width_path / 2 - Map.get_size_asteroid()/2 + offset, margin_y + Map.get_size_asteroid()/2));

                        boolean cond = true;
                        for (int i=0; i<Board.get_instance().get_paths().size(); i++) {
                            Path2 other_path = Board.get_instance().get_paths().get(i);
                            cond = cond & (Math.abs(asteroid.get_pos_y() - other_path.get_ord((int) Math.round(asteroid.get_pos_x()))) >= (((double) Board.get_instance().get_width_path(i) / 2) + (Map.get_size_asteroid() / 2)));
                        }

                        if (cond){
                            asteroids.add(asteroid);
                            x += size_asteroid; // Avoid putting two asteroids to close by ensuring there's at least a distance of size_asteroid between the right side of the left asteroid
                            // and the left side of the right asteroid
                        }
                        if (!cond && iter >= 10) flag = false; // If we didn't found an adequate position for an asteroid by searching 10 times, we don't put one and go to the next position

                        iter++;
                        if (x >= dim_x - margin_x - Map.get_size_asteroid()/2) return; // This position can be used for none of the paths
                    }
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
    public ArrayList<Path2> get_paths(){ return paths;}
    public ArrayList<Tower> get_towers(){ return towers;}
    public ArrayList<Asteroid> get_asteroids(){ return asteroids;}
    public ArrayList<Munition> get_munitions(){ return munitions;}
    public int get_max_offset(){ return max_offset;}

    public int get_dim_x(){return dim_x;}
    public int get_dim_y(){return dim_y;}
    public int get_num_paths(){return paths.size();}
    public int get_width_path(int num_path){return paths.get(num_path).get_width();}
    public double get_ord_path(int num_path){return paths.get(num_path).get_ord(dim_x-1);}

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
                Platform.runLater( () -> {
                    for (Munition munition: copyMunitions){
                        munition.update();
                        if (munition.check_shot_npc() && munitions.contains(munition)){ // Check if a munition has been removed from munitions but is still in its copy
                            System.out.println("Munition détruite" + munition);
                            remove_munition(munition);
                        }
                    }
                });
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

    public void set_paths(ArrayList<Path2> paths){
        this.paths = paths;
    }
}
