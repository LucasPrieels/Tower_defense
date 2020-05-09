package Model;

public class Big_NPC extends NPC {
    private static int radius = 3;
    private static int size = 20;

    public Big_NPC (double pos_x, double pos_y, double speed, int health, Path2 path){
        super(pos_x, pos_y, speed, health, path);
    }

    public int get_radius(){return radius;} // Pas terrible
    public static int get_radius_static(){return radius;}
    public static int get_size(){return size;}
}
