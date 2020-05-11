package Model;

import Controller.Update_manager;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Big_NPC extends NPC {
    private static double size = 20;
    private static Image big_npc_img;

    public Big_NPC (double pos_x, double pos_y, double speed, int health, Path_custom path){
        super(pos_x, pos_y, speed, health, path);
        try{
            big_npc_img = new Image(new FileInputStream("Assets/Big_NPC.png"), size * Update_manager.get_fact_x(), size * Update_manager.get_fact_y(), false, false);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(NullPointerException ignore){} // If we create a NPC without gui, Update_manager is null but we don't need its image
    }

    public Image get_image(){ return big_npc_img;}
    public static double get_size_static(){ return size;}
    public double get_size(){ return size;}
}
