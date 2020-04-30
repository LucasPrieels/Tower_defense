package Model;

import java.util.ArrayList;

public class Freezing_tower extends Attack_tower{
    private static double[] range = {50.0, 70.0, 100.0};
    private static int[] power = {3, 5, 10}, npc_destroyed_needed = {10, 30}, period = {5000, 3000, 1500}, price_upgrade = {200, 500};
    private static int max_level = 2; //On compte àpd 0

    public Freezing_tower(Asteroid asteroid){
        super(asteroid, range, power, npc_destroyed_needed, period, price_upgrade, max_level);
    }

    protected void shoot(NPC npc){
        Munition munition = new Freezing_munition(this, npc, power[this.get_curr_level()]);
        add_munition(munition);
        System.out.println("Munition gelante crée");
    }

    public boolean fire(){
        ArrayList<NPC> npcs = Board.get_npcs();
        for (NPC npc: npcs){
            if (npc_in_tower_area(npc) && npc.is_frozen() == 0){
                System.out.println("FIRE gelant!");
                shoot(npc); // On s'arrête dès que le premier PNJ dans la zone d'attaque a été trouvé car c'est dans l'ordre
                // le premier a avoir été tiré donc celui qui est le plus loin sur la plateau
                return true;
            }
        }
        return false;
    }
}
