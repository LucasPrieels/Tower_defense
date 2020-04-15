package Model;

public class Freezing_tower extends Tower implements Runnable{
    private static double[] range = {20.0, 50.0, 100.0};
    private static int[] period = {3, 2, 1};
    private static int[] power = {2, 5, 10};
    private static int[] price_upgrade = {200, 500};
    private static int[] npc_destroyed_needed = {10, 30};
    private static int max_level = 3;
    private int npc_destroyed;

    public Freezing_tower(Asteroid asteroid){
        super(asteroid);
    }

    public void run(){
        try{
            Thread.sleep(period[get_curr_level()]);
            fire();
        } catch(InterruptedException e){ }
    }

    public boolean upgrade() {
        if (super.get_curr_level() != max_level && npc_destroyed >= npc_destroyed_needed[super.get_curr_level()] && Game.pay(price_upgrade[super.get_curr_level()])) {
            increment_curr_level();
            return true;
        }
        return false;
    }

    public void fire(){

    }
}
