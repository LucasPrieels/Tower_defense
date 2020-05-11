package Model;

import javafx.scene.image.Image;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Factory_tower extends Tower implements Runnable{
    private static int[] period = {10000, 7000, 5000}, prod_money = {50, 70, 100}, price_upgrade = {200, 500};
    private static int[] npc_destroyed_needed = {10, 30};
    private static int max_level = 2; // Counting from 0
    private static Image factory_tower_img;
    private boolean interrupted = false;

    public Factory_tower(Asteroid asteroid) {
        super(asteroid, period, price_upgrade, max_level, npc_destroyed_needed);
        this.thread = new Thread(this);
        Game.get_instance().add_thread(thread);
        // Not started yet because we can't produce money before launching the first wave
        try{
            factory_tower_img = new Image(new FileInputStream("Assets/factory_tower.png"), Tower.get_size_static() / 1.5, Tower.get_size_static(), false, false);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void run(){
        while (true){
            if (scan()) {
                action_scanned(new Small_NPC(0, 0, 0, 0, new Path_custom(new ArrayList<>(), 0)));
                if (interrupted) return;
            }
        }
    }

    public boolean scan(){return true;} // The tower doesn't stop producing money

    public void action_scanned(NPC npc){action_scanned();} // We have to put it because other action_scanned need a NPC

    public void action_scanned(){
        try{
            Thread.sleep(period[get_curr_level()]);
        } catch (InterruptedException e){
            interrupted = true;
            return;
        }
        Sound coin_snd = TinySound.loadSound("Songs/coin.wav");
        coin_snd.play();
        Game.get_instance().increase_money(prod_money[get_curr_level()]);
        System.out.println("Argent produit par une usine");
    }

    public Image get_image(){ return factory_tower_img;}
}
