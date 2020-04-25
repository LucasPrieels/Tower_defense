package Model;

import java.util.ArrayList;

public class Board {
    private static Board instance = null;

    private static ArrayList<NPC> npcs = new ArrayList<>();
    private static ArrayList<Path> paths;
    private static ArrayList<Tower> towers = new ArrayList<>();
    private static ArrayList<ArrayList<Asteroid>> asteroids = new ArrayList<>();
    private static ArrayList<Munition> munitions = new ArrayList<>();
    private static int dim_x, dim_y, margin_x, margin_y, width_path, size_asteroid, max_offset;
    private static double proba;

    private Board(int dim_x, int dim_y, int margin_x, int margin_y, int width_path, int size_asteroid, double proba, int max_offset, ArrayList<Path> paths){
        Board.dim_x = dim_x;
        Board.dim_y = dim_y;
        Board.margin_x = margin_x;
        Board.margin_y = margin_y;
        Board.width_path = width_path;
        // Ajouter exceptions dans les cas où les dimensions et marges ne sont pas compatibles
        Board.size_asteroid = size_asteroid;
        Board.proba = proba;
        Board.max_offset = max_offset;
        Board.paths = paths; // Ne pas les créer aléatoirement car ça pourrait créer des conflits (croisements, bloquages de chemins etc)
        for (int i=0; i<dim_x; i++){ asteroids.add(new ArrayList<>());}
        create_asteroids_random();
    }

    //Singleton
    //Attention, le return ne sert à rien car la classe est tout le temps utilisée en static mais la fonction est utile pour initialiser les valeurs
    public static Board get_instance(int dim_x, int dim_y, int margin_x, int margin_y, int width_path, int size_asteroid, double proba, int max_offset, ArrayList<Path> paths){
        if (Board.instance==null){Board.instance = new Board(dim_x, dim_y, margin_x, margin_y, width_path, size_asteroid, proba, max_offset, paths);}
        return Board.instance;
    }

    private static void create_asteroids_random(){
        for (int x=margin_x; x<dim_x-margin_x; x += size_asteroid){
            for (Path path : paths) {
                if (Math.random() < proba) {
                    int y = path.get_ord(x);
                    int offset = (int) Math.round(Math.random() * max_offset);
                    Asteroid asteroid;
                    if (offset > 0) {asteroid = new Asteroid(x, Math.min(y + width_path / 2 + offset, dim_y - margin_y));}
                    else {asteroid = new Asteroid(x, Math.max(y - width_path / 2 - offset, margin_x));}
                    asteroids.get(x).add(asteroid);
                }
            }
        }
    }

    public static void remove_npc(NPC npc){
        npcs.remove(npc);
    }

    public static void add_npc(NPC npc){ npcs.add(npc);}
    public static void add_tower(Tower tower){ towers.add(tower);}
    public static void add_munition(Munition munition){ munitions.add(munition);}

    public static ArrayList<NPC> get_npcs(){ return npcs;} // Utiliser polymorphisme pour éviter répétition? Sans instanceof?
    public static ArrayList<Path> get_paths(){ return paths;}
    public static ArrayList<Tower> get_towers(){ return towers;}
    public static ArrayList<ArrayList<Asteroid>> get_asteroids(){ return asteroids;}
    public static ArrayList<Munition> get_munitions(){ return munitions;}

    public static int get_dim_x(){return dim_x;}
    public static int get_dim_y(){return dim_y;}
    public static int get_num_paths(){return paths.size();}
    public static int get_width_path(int num_path){return paths.get(num_path).get_width();}
    public static int get_ord_path(int num_path){return paths.get(num_path).get_ord(dim_x-1);}

    public static boolean empty(int pos_x, int pos_y, int radius){
        for (NPC npc: npcs){
            if (distance(npc.get_pos_x(), npc.get_pos_y(), pos_x, pos_y) < radius + npc.get_radius()){
                return false;
            }
        }
        return true;
    }

    private static double distance(double pos_x1, double pos_y1, int pos_x2, int pos_y2) {
        return Math.sqrt(Math.pow(pos_x1-pos_x2, 2)+Math.pow(pos_y1-pos_y2, 2));
    }
}
