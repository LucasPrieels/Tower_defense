package Model;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Freezing_tower extends Attack_tower{
    private static double[] range = {80.0, 100.0, 120.0};
    private static int[] power = {5, 10, 15}, npc_destroyed_needed = {10, 30}, period = {3000, 2000, 1000}, price_upgrade = {150, 250};
    private static int max_level = 2; //On compte àpd 0
    private static Image freezing_tower_img;

    public Freezing_tower(Asteroid asteroid){
        super(asteroid, range, power, npc_destroyed_needed, period, price_upgrade, max_level);
        try{
            freezing_tower_img = new Image(new FileInputStream("Assets/freezing_tower.png"), Tower.get_size_static() / 1.5, Tower.get_size_static(), false, false);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void action_scanned(NPC npc){
        Munition munition = new Freezing_munition(this, npc);
        add_munition(munition);
        System.out.println("Munition gelante crée");
    }

    public boolean scan(){
        ArrayList<NPC> npcs = Board.get_instance().get_npcs();
        if (npcs.size() == 0) return false;

        // We're shooting in priority at leftmost NPC since there closer to the end
        double mini = -1;
        NPC curr_npc = null;
        for (NPC npc: npcs){
            if ((curr_npc == null || npc.get_pos_x() < mini) && npc_in_tower_area(npc) && npc.is_frozen() == 0) { // We don't shoot at already frozen NPCs
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

    public Image get_image(){ return freezing_tower_img;}
}
