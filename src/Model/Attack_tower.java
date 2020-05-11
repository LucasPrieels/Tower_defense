package Model;

public abstract class Attack_tower extends Tower implements Runnable{
    private double[] range;
    private int[] power;

    public Attack_tower(Asteroid asteroid, double[] range, int[] power, int[] npc_destroyed_needed, int[] period, int[] price_upgrade, int max_level) {
        super(asteroid, period, price_upgrade, max_level, npc_destroyed_needed);
        this.range = range;
        this.power = power;
        this.thread = new Thread(this);
        Game.get_instance().add_thread(thread);
        thread.start();
    }

    public boolean npc_in_tower_area(NPC npc){
        Asteroid asteroid = get_asteroid();
        return Math.sqrt(Math.pow(npc.get_pos_x()-asteroid.get_pos_x(), 2)+Math.pow(npc.get_pos_y()-asteroid.get_pos_y(), 2)) < range[get_curr_level()];
    }

    public void run(){
        while (true) {
            try{
                if (scan()) { // scan returns true if there is a NPC in the range of the attack tower, and attacks it
                    Thread.sleep(get_period()); // Since a munition has been launched, the tower has to wait before shooting again
                }
                Thread.sleep(1000/Game.get_instance().get_fps()); // Just to avoid checking too frequently and slowing down the code
            } catch(InterruptedException e){
                return;
            } catch(AssertionError ignored){}
        }
    }

    public void add_munition(Munition munition){ Board.get_instance().add_munition(munition);}
    public int get_power(){ return power[get_curr_level()];}
}
