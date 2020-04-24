package Model;

public class Freezing_munition extends Munition {

    private int freezing_time;

    public Freezing_munition(Tower tower, NPC npc, int freezing_time){

        super(tower, npc);
        this.freezing_time=freezing_time;
    }
}
