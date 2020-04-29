package Model;

public class Classic_tower extends Attack_tower{
    private static double[] range = {40.0, 100.0, 200.0};
    private static int[] period = {1000, 500, 200}, power = {5, 10, 20}, npc_destroyed_needed = {5, 15}, price_upgrade = {50, 200};
    private static int max_level = 3;

    public Classic_tower(Asteroid asteroid){
        super(asteroid, range, power, npc_destroyed_needed, period, price_upgrade, max_level);
    }

    protected void shoot(NPC npc){
        Munition munition = new Classic_munition(this, npc);
        System.out.println("Munition classique crée");
        add_munition(munition);
    }
}
