package Model;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Big_NPC extends NPC {
    private static int radius = 3;
    private static int size = 60;
    private static Image big_npc_img;

    public Big_NPC (double pos_x, double pos_y, double speed, int health, Path_custom path){
        super(pos_x, pos_y, speed, health, path);
        try{
            big_npc_img = new Image(new FileInputStream("Assets/Big_NPC.png"), size, size, false, false);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public int get_radius(){return radius;}
    public static int get_radius_static(){return radius;}
    public Image get_image(){ return big_npc_img;}
    public double get_size(){ return size;}
}
