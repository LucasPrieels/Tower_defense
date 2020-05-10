package Model;

import javafx.scene.image.Image;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Factory_tower extends Tower {
    private static int[] period = {20, 10, 5}, prod_money = {20, 50, 100}, price_upgrade = {200, 500};
    private static int[] npc_destroyed_needed = {10, 30};
    private static int max_level = 2; //On compte àpd 0
    public static final Object key = new Object();
    private transient Sound coin_snd;
    private static Image factory_tower_img;

    public Factory_tower(Asteroid asteroid) {
        super(asteroid, period, price_upgrade, max_level, npc_destroyed_needed);
        this.thread = new Thread(this);
        Game.get_instance().add_thread(thread);
        thread.start();
        try{
            factory_tower_img = new Image(new FileInputStream("Assets/factory_tower.png"), get_size_tower(), get_size_tower(), false, false);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
        //Thread thread_factory_tower = new Thread(this);
        //Game.get_instance().add_thread(thread_factory_tower);
        //thread_factory_tower.start();
    }

    public void run(){
        while (true){
            try{
                Thread.sleep(period[get_curr_level()]*1000);
                synchronized (key){
                    coin_snd = TinySound.loadSound("Songs/coin.wav");
                    coin_snd.play();
                    Game.get_instance().increase_money(prod_money[get_curr_level()]);
                    System.out.println("Argent produit par une usine");
                }
            } catch (InterruptedException e){
                return;
            }
        }
    }

    public Image get_image(){ return factory_tower_img;}
}
