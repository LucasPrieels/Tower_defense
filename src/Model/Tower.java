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

    public int get_curr_level(){
        return curr_level;
    }

    public int get_max_level(){
        return max_level;
    }

    public int get_price_upgrade(){
        return price_upgrade[curr_level];
    }

    public int get_period(){
        return period[curr_level];
    }

    public void increment_curr_level(){
        curr_level ++;
    }

    public Thread get_thread(){return thread;}

    public Asteroid get_asteroid(){
        return asteroid;
    }

    public void upgrade() {
        if (get_curr_level() != get_max_level() && Game.get_instance().get_npc_destroyed() >= npc_destroyed_needed[get_curr_level()] && Game.get_instance().pay(get_price_upgrade())) {
            increment_curr_level();
            tower_snd = TinySound.loadSound("Songs/tower.wav");
            tower_snd.play(3);
        }
    }

    public int get_npc_destroyed_needed(){return npc_destroyed_needed[curr_level];}

    public abstract Image get_image();

    public static double get_size(){ return size_tower;}

    public void update_pos(){} // Les tours ne se déplacent pas mais doivent être Redrawable car leur position n'est pas fixée
}
