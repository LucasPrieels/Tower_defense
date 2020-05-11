package Model;

import Controller.Update_manager;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Medium_NPC extends NPC {
    private static double size = 15;
    private static Image medium_npc_img;

    public Medium_NPC (double pos_x, double pos_y, double speed, int health, Path_custom path){
        super(pos_x, pos_y, speed, health, path);
        try{
            medium_npc_img = new Image(new FileInputStream("Assets/Medium_NPC.png"), size * Update_manager.get_fact_x(), size * Update_manager.get_fact_y(), false, false);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public Image get_image(){ return medium_npc_img;}
    public static double get_size_static(){ return size;}
    public double get_size(){ return size;}
}
