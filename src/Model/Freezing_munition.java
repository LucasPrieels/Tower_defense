package Model;

public class Freezing_munition extends Munition {
    private int freezing_time;
    private static int speed = 20;

    public Freezing_munition(Tower tower, NPC npc, int freezing_time){
        super(tower, npc, speed);
        this.freezing_time=freezing_time;
    }
}
