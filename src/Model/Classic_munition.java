package Model;

import javafx.scene.image.Image;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Classic_munition extends Munition {
    private static double speed = 60;
    private static Image classic_munition_img;

    public Classic_munition(Attack_tower tower, NPC npc){
        super(tower, npc, speed);
        Sound shot_snd = TinySound.loadSound("Songs/classic_munition.wav");
        shot_snd.play();
        try{
            classic_munition_img = new Image(new FileInputStream("Assets/Classic_munition.png"), get_size_static_munition(), get_size_static_munition(),false, false);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void action_scanned(NPC npc){ // Munitions explodes
        npc.decrease_health(get_tower().get_power());
        Sound blast_snd = TinySound.loadSound("Songs/blast.wav");
        blast_snd.play(0.8);
        if (npc.get_health() <= 0) {
            if (Board.get_instance().remove_npc(npc)) {
                Sound destroyed_snd = TinySound.loadSound("Songs/explosion.wav");
                destroyed_snd.play(2);
                Game.get_instance().increment_npc_destroyed(); // Only if a NPC has really been removed
            }
        }
    }

    public Image get_image(){ return classic_munition_img;}
}
