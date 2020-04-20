package Model;

public class Small_NPC extends NPC {
    private static int radius = 1;

    public Small_NPC (double pos_x, double pos_y, int health){
        super(pos_x, pos_y, health);
    }

    public int get_radius(){return radius;}
    public static int get_radius_static(){return radius;}
}
