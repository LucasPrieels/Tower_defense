package Model;

import Controller.Update_manager;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Small_NPC extends NPC {
    private static double size = 10;
    private static Image small_npc_img;

    public Small_NPC (double pos_x, double pos_y, double speed, int health, Path_custom path){
        super(pos_x, pos_y, speed, health, path);
        try{
            small_npc_img = new Image(new FileInputStream("Assets/Small_NPC.png"), size * Update_manager.get_fact_x(), size * Update_manager.get_fact_y(), false, false);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(NullPointerException ignore){} // If we create a NPC without gui, Update_manager is null but we don't need its image
    }

    public Image get_image(){ return small_npc_img;}
    public static double get_size_static(){ return size;}
    public double get_size(){ return size;}
}
