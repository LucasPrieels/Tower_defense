package Model;

public class Small_NPC extends NPC {
    private static int radius = 1;

    public Small_NPC (double pos_x, double pos_y, double speed, int health, Path_custom path){
        super(pos_x, pos_y, speed, health, path);
    }

    public int get_radius(){return radius;}
    public static int get_radius_static(){return radius;}
}
