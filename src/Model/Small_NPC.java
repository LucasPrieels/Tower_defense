package Model;

import Controller.Update_manager;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Small_NPC extends NPC {
    private static double size = 30;
    private static Image small_npc_img;

    public Small_NPC (double pos_x, double pos_y, double speed, int health, Path_custom path){
        super(pos_x, pos_y, speed, health, path);
        try{
            small_npc_img = new Image(new FileInputStream("Assets/Small_NPC.png"), size, size, false, false);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public Image get_image(){ return small_npc_img;}
    public static double get_size_static(){ return 2* size/(Update_manager.get_fact_x()+Update_manager.get_fact_y());}
    public double get_size(){ return 2* size/(Update_manager.get_fact_x()+Update_manager.get_fact_y());}
}
