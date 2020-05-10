package Model;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Classic_tower extends Attack_tower{
    private static double[] range = {70.0, 100.0, 200.0};
    private static int[] period = {2000, 1500, 700}, power = {4, 7, 10}, npc_destroyed_needed = {5, 15}, price_upgrade = {50, 200};
    private static int max_level = 2; //On compte àpd 0
    private static Image classic_tower_img;

    public Classic_tower(Asteroid asteroid){
        super(asteroid, range, power, npc_destroyed_needed, period, price_upgrade, max_level);
        try{
            classic_tower_img = new Image(new FileInputStream("Assets/classic_tower.png"), get_size_tower() / 1.5, get_size_tower(), false, false);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    protected void shoot(NPC npc){
        Munition munition = new Classic_munition(this, npc);
        System.out.println("Munition classique crée");
        add_munition(munition);
    }

    public boolean fire(){
        ArrayList<NPC> npcs = Board.get_instance().get_npcs();
        if (npcs.size() == 0) return false;

        //On cherche le PNJ qui se trouve le plus à gauche de l'écran, pour lui tirer dessus en priorité
        double mini = -1;
        NPC curr_npc = null;
        for (NPC npc: npcs){
            if ((curr_npc == null || npc.get_pos_y() < mini) && npc_in_tower_area(npc)) {
                mini = npc.get_pos_y();
                curr_npc = npc;
            }
        }
        if (curr_npc != null){
            shoot(curr_npc);
            return true;
        }
        return false;
    }

    public Image get_image(){ return classic_tower_img;}
}
