package Model;

import java.util.ArrayList;

public abstract class Attack_tower extends Tower{
    private double[] range;
    private int[] power,  npc_destroyed_needed;
    public static final Object key = new Object();

    protected Attack_tower(Asteroid asteroid, double[] range, int[] power, int[] npc_destroyed_needed, int[] period, int[] price_upgrade, int max_level){
        super(asteroid, period, price_upgrade, max_level, npc_destroyed_needed);
        this.range = range;
        this.power = power;
        this.npc_destroyed_needed = npc_destroyed_needed;
        Thread thread_attack_tower = new Thread(this);
        Game.get_instance().add_thread(thread_attack_tower);
        thread_attack_tower.start();
    }

    public void add_munition(Munition munition){
        Board.get_instance().add_munition(munition);
    }

    public boolean npc_in_tower_area(NPC npc){
        Asteroid asteroid = get_asteroid();
        return Math.sqrt(Math.pow(npc.get_pos_x()-asteroid.get_pos_x(), 2)+Math.pow(npc.get_pos_y()-asteroid.get_pos_y(), 2)) < range[get_curr_level()];
    }

    public void run(){
        while (true) {
            try{
                synchronized (key){
                    if (fire()) {
                        Thread.sleep(get_period());
                    }
                }
                Thread.sleep(200/Game.get_instance().get_fps());
            } catch(InterruptedException | AssertionError e){
                System.out.println("RETURN");
                return;
            }
        }
    }

    public abstract boolean fire();

    protected abstract void shoot(NPC npc);

    public int get_power(){return power[get_curr_level()];}
}
