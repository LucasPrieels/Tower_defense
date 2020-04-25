package Model;

public class Small_NPC extends NPC {
    private static int radius = 1;

    public Small_NPC (int pos_x, int pos_y, int speed, int health, Path path){
        super(pos_x, pos_y, speed, health, path);
    }

    public int get_radius(){return radius;}
    public static int get_radius_static(){return radius;}
}
