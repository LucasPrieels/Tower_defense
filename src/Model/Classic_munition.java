package Model;

public class Classic_munition extends Munition {
    public static double speed = 30;
    public Classic_munition(Attack_tower tower, NPC npc){
        super(tower, npc, speed);
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
