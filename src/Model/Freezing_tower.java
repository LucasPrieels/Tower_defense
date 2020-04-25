package Model;

public class Freezing_tower extends Attack_tower{
    private static double[] range = {20.0, 50.0, 100.0};
    private static int[] power = {2, 5, 10}, npc_destroyed_needed = {10, 30}, period = {3000, 2000, 1000}, price_upgrade = {200, 500};
    private static int max_level = 3;

    public Freezing_tower(Asteroid asteroid){
        super(asteroid, range, power, npc_destroyed_needed, period, price_upgrade, max_level);
    }

    protected void shoot(NPC npc){
        Munition munition = new Freezing_munition(this, npc, power[this.get_curr_level()]);
        add_munition(munition);
    }
}
