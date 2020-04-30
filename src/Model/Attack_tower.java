package Model;

import java.util.ArrayList;

public abstract class Attack_tower extends Tower implements Runnable{
    private double[] range;
    private int[] power,  npc_destroyed_needed;
    private boolean shot = false;

    protected Attack_tower(Asteroid asteroid, double[] range, int[] power, int[] npc_destroyed_needed, int[] period, int[] price_upgrade, int max_level){
        super(asteroid, period, price_upgrade, max_level, npc_destroyed_needed);
        this.range = range;
        this.power = power;
        this.npc_destroyed_needed = npc_destroyed_needed;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void add_munition(Munition munition){
        Board.add_munition(munition);
    }

    public boolean npc_in_tower_area(NPC npc){
        Asteroid asteroid = get_asteroid();
        return Math.sqrt(Math.pow(npc.get_pos_x()-asteroid.get_pos_x(), 2)+Math.pow(npc.get_pos_y()-asteroid.get_pos_y(), 2)) < range[get_curr_level()];
    }

    public void run(){
        try{
            while (true) {
                if (fire()) {
                    Thread.sleep(get_period());
                }
                Thread.sleep(200/Game.get_fps());
            }
        } catch(InterruptedException e){
            System.out.println("Erreur dans le sleep du thread des Attack_tower");
        }
    }

    private boolean fire(){
        ArrayList<NPC> npcs = Board.get_npcs();
        for (NPC npc: npcs){
            if (npc_in_tower_area(npc)){
                System.out.println("FIRE!");
                shoot(npc); // On s'arrête dès que le premier PNJ dans la zone d'attaque a été trouvé car c'est dans l'ordre
                // le premier a avoir été tiré donc celui qui est le plus loin sur la plateau
                return true;
            }
        }
        return false;
    }

    protected abstract void shoot(NPC npc);
}
