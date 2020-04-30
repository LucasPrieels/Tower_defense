package Model;

public class Classic_munition extends Munition {
    public static double speed = 30;
    public Classic_munition(Tower tower, NPC npc){
        super(tower, npc, speed);
    }
}
