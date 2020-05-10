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
            classic_munition_img = new Image(new FileInputStream("Assets/Classic_munition.png"), get_size_munition(), get_size_munition(),false, false);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public boolean check_shot_npc(){
        for (NPC npc: Board.get_instance().get_npcs()){
            if (npc.is_shot(this)){
                return true;
            }
        }
        return false;
    }

    public Image get_image(){ return classic_munition_img;}
}
