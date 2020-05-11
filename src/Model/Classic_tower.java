package Model;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Classic_tower extends Attack_tower{
    private static double[] range = {70.0, 100.0, 200.0};
    private static int[] period = {2000, 1500, 1000}, power = {4, 7, 10}, npc_destroyed_needed = {5, 15}, price_upgrade = {100, 200};
    private static int max_level = 2; //On compte àpd 0
    private static Image classic_tower_img;

    public Classic_tower(Asteroid asteroid){
        super(asteroid, range, power, npc_destroyed_needed, period, price_upgrade, max_level);
        try{
            classic_tower_img = new Image(new FileInputStream("Assets/classic_tower.png"), Tower.get_size_static() / 1.5, Tower.get_size_static(), false, false);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void action_scanned(NPC npc){
        Munition munition = new Classic_munition(this, npc);
        System.out.println("Munition classique créée");
        add_munition(munition);
    }

    public boolean scan(){
        ArrayList<NPC> npcs = Board.get_instance().get_npcs();
        if (npcs.size() == 0) return false;

        // We're shooting in priority at leftmost NPC since there closer to the end
        double mini = -1;
        NPC curr_npc = null;
        for (NPC npc: npcs){
            if ((curr_npc == null || npc.get_pos_x() < mini) && npc_in_tower_area(npc)) {
                mini = npc.get_pos_x();
                curr_npc = npc;
            }
        }
        if (curr_npc != null){
            action_scanned(curr_npc);
            return true;
        }
        return false;
    }

    public Image get_image(){ return classic_tower_img;}
}
