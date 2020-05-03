package Model;

public class Freezing_munition extends Munition {
    private int freezing_time;
    private static int speed = 20;

    public Freezing_munition(Attack_tower tower, NPC npc, int freezing_time){
        super(tower, npc, speed);
        this.freezing_time=freezing_time;
    }

    public boolean check_shot_npc(){
        for (NPC npc: Board.get_instance().get_npcs()){
            if (npc.is_shot(this)){
                return true;
            }
        }
        return false;
    }
}
