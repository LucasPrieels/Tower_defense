package Model;

import javafx.scene.image.Image;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;
import java.io.Serializable;

public abstract class Tower implements Runnable, Serializable, Movable, Scanner{
    private Asteroid asteroid;
    private int curr_level, max_level;
    private int[] period, price_upgrade, npc_destroyed_needed;
    private transient Sound tower_snd;
    protected transient Thread thread; //enlever si ca marche pas
    private static double size_tower = 50;

    public Tower(Asteroid asteroid, int[] period, int[] price_upgrade, int max_level, int[] npc_destroyed_needed){
        tower_snd = TinySound.loadSound("Songs/tower.wav");
        tower_snd.play();
        this.asteroid = asteroid;
        asteroid.occupy();
        this.period = period;
        this.price_upgrade = price_upgrade;
        this.max_level = max_level;
        this.npc_destroyed_needed = npc_destroyed_needed;
        curr_level = 0;
    }

    public void upgrade() {
        curr_level++;
        tower_snd = TinySound.loadSound("Songs/tower.wav");
        tower_snd.play(3);
    }

    public void update_pos(){} // Towers don't move regularly but are Movable because they can be deleted

    public int get_curr_level(){ return curr_level;}
    public int get_max_level(){ return max_level;}
    public int get_price_upgrade(){return price_upgrade[curr_level];} // Can't be called if curr_level = max_level
    public int get_period(){ return period[curr_level];}
    public Thread get_thread(){return thread;}
    public Asteroid get_asteroid(){ return asteroid;}
    public int get_npc_destroyed_needed(){return npc_destroyed_needed[curr_level];} // Can't be called if curr_level = max_level
    public static double get_size_static(){ return size_tower;}

    public abstract Image get_image();
}
