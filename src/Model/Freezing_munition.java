package Model;

import javafx.scene.image.Image;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Freezing_munition extends Munition {
    private static int speed = 50;
    private static Image freezing_munition_img;

    public Freezing_munition(Attack_tower tower, NPC npc){
        super(tower, npc, speed);
        Sound shot_snd = TinySound.loadSound("Songs/freezing_munition.wav");
        shot_snd.play();
        try{
            freezing_munition_img = new Image(new FileInputStream("Assets/Freezing_munition.png"), get_size_static_munition(), get_size_static_munition(), false, false);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void action_scanned(NPC npc){ // Munition explodes
        npc.clear_snowflakes();
        Sound freezed_snd = TinySound.loadSound("Songs/freeze.wav");
        freezed_snd.play();
        npc.set_freezed(get_tower().get_power());
    }

    public Image get_image(){ return freezing_munition_img;}
}
