package Model;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

public class Freezing_munition extends Munition {
    private int freezing_time;
    private static int speed = 20;

    public Freezing_munition(Attack_tower tower, NPC npc, int freezing_time){
        super(tower, npc, speed);
        this.freezing_time=freezing_time;
        Sound shot_snd = TinySound.loadSound("Songs/freezing_munition.wav");
        shot_snd.play();
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
