package Model;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Medium_NPC extends NPC {
    private static int radius = 2;
    private static double size = 45;
    private static Image medium_npc_img;

    public Medium_NPC (double pos_x, double pos_y, double speed, int health, Path_custom path){
        super(pos_x, pos_y, speed, health, path);
        try{
            medium_npc_img = new Image(new FileInputStream("Assets/Medium_NPC.png"), size, size, false, false);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public int get_radius(){return radius;}
    public static int get_radius_static(){return radius;}
    public Image get_image(){ return medium_npc_img;}
    public double get_size(){ return size;}
}
