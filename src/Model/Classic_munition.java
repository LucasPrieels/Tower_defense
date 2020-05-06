package Model;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

public class Classic_munition extends Munition {
    private static double speed = 30;

    public Classic_munition(Attack_tower tower, NPC npc){
        super(tower, npc, speed);
        Sound shot_snd = TinySound.loadSound("Songs/classic_munition.wav");
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
