package Model;

import java.util.ArrayList;

public abstract class Attack_tower extends Tower/* implements Runnable*/{
    private double[] range;
    private int[] power,  npc_destroyed_needed;
    private boolean shot = false;
    private ArrayList<Munition> munitions;

    protected Attack_tower(Asteroid asteroid, double[] range, int[] power, int[] npc_destroyed_needed, int[] period, int[] price_upgrade, int max_level){
        super(asteroid, period, price_upgrade, max_level, npc_destroyed_needed);
        this.range = range;
        this.power = power;
        this.npc_destroyed_needed = npc_destroyed_needed;
    }

    public void add_munition(Munition munition){
        munitions.add(munition);
    }

    public boolean npc_in_tower_area(NPC npc){
        Asteroid asteroid = get_asteroid();
        return Math.sqrt(Math.pow(npc.get_pos_x()-asteroid.get_pos_x(), 2)+Math.pow(npc.get_pos_y()-asteroid.get_pos_y(), 2)) < range[get_curr_level()];
    }

    /*
    public void run(){
        try{
            if (fire()){
                Thread.sleep(get_period());
            }
        } catch(InterruptedException e){
            System.out.println("Erreur dans le sleep du thread des Attack_tower");
        }
    }
    */

    private boolean fire(){
        ArrayList<NPC> npcs = Board.get_npcs();
        for (NPC npc: npcs){
            if (npc_in_tower_area(npc)){
                shoot(npc);
                return true;
            }
        }
        return false;
    }

    protected abstract void shoot(NPC npc);
}
