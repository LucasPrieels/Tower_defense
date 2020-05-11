package Controller;

import Model.*;
import View.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Update_manager {
    private static Map map;
    private static boolean end_game = false;
    private static Stage stage;

    private Update_manager(){ }

    static Map first_update_window(Stage theStage){
        Update_manager.stage = theStage;
        try{
            Update_manager.map = Map.init(theStage);
            map.draw_paths();
            init_message(map);
        } catch (FileNotFoundException e){e.printStackTrace();}
        return map;
    }

    static void show_asteroids_controller(){
        map.show_asteroids_gui();
    }

    private static void init_message(Map map){
        Message.init(map);
    }

    public static void update_window(){
        //System.out.println("Updating");
        try{
            map.update_canvas();
            check_end_game();
        } catch(AssertionError ignored){}
    }

    private static void check_end_game(){
        if (end_game){
            Game.get_instance().stop_threads();
            if (Game.get_instance().get_score() > 0){
                Sound won_snd = TinySound.loadSound("Songs/won.wav");
                won_snd.play();
                //update_window();
                View.Menu_gameover.start_gameover(stage,"wingame");
            }
            else{
                Sound game_over_snd = TinySound.loadSound("Songs/game_over.wav");
                game_over_snd.play(5);
                //update_window();
                View.Menu_gameover.start_gameover(stage,"gameover");}
        }
    }

    public static void end_game(){ end_game = true;}



    public static void update_gui(){
        update_score_rectangle();
        update_munitions_canvas();
        show_towers();
        update_npc_canvas();
    }

    private static void update_score_rectangle(){
        int score = Game.get_instance().get_score();
        int money = Game.get_instance().get_money();
        int wave = Game.get_instance().get_curr_wave() + 1;
        int timer = Level.get_instance().get_waves().get(Game.get_instance().get_curr_wave()).get_time_wave() + Level.get_instance().get_time_between_waves() - Level.get_instance().get_waves().get(Game.get_instance().get_curr_wave()).get_time();
        int npc_destroyed = Game.get_instance().get_npc_destroyed();

        map.fill_score_rectangle(score, money, wave, timer, npc_destroyed);
    }

    private static void update_munitions_canvas() {
        CopyOnWriteArrayList<Munition> copyMunitions = new CopyOnWriteArrayList<>(Board.get_instance().get_munitions());
        for (Munition munition : copyMunitions) {
            double pos_x = munition.get_pos_x(), pos_y = munition.get_pos_y();

            Image munition_img = munition.get_image();
            ImageView munition_iv = new ImageView(munition_img);
            munition_iv.setRotate(Math.atan(munition.get_dir_y()/munition.get_dir_x())*180/Math.PI);
            map.draw_iv(munition_iv, pos_x, pos_y);
        }
    }

    private static void show_towers(){
        for (Tower tower: Board.get_instance().get_towers()){
            Image tower_img = tower.get_image();
            map.draw_img(tower_img, tower.get_asteroid().get_pos_x() + 4, tower.get_asteroid().get_pos_y());

            for(int k = 0; k<= tower.get_max_level(); k++){
                map.draw_star(tower.get_curr_level()+1, tower.get_asteroid().get_pos_x(), tower.get_asteroid().get_pos_y());
            }
        }
    }

    private static void update_npc_canvas() {
        CopyOnWriteArrayList<NPC> npcs = new CopyOnWriteArrayList<>(Board.get_instance().get_npcs());
        // Car on peut faire des suppressions d'éléments
        for (NPC npc : npcs) {
            ImageView npc_iv = new ImageView(npc.get_image());
            npc_iv.setRotate(npc.get_direction() - 90);
            map.draw_iv(npc_iv, npc.get_pos_x(), npc.get_pos_y());

            if (npc.is_frozen() > 0){
                for (int i = 0; i < npc.get_pos_x_snowflakes().size(); i++){
                    map.draw_snowflake(npc.get_pos_x_snowflakes().get(i), npc.get_pos_y_snowflakes().get(i));
                }
                if (Math.random() < 0.8){
                    npc.add_snowflake(npc.get_pos_x() + npc.get_size()/6, npc.get_pos_y() + Math.random()*npc.get_size()/4);
                }
            }
        }
    }

    public static int get_level() {
        return Game.get_instance().get_num_level();
    }

    public static ArrayList<ArrayList<Double>> get_forbidden(){
        if (map == null) return new ArrayList<>();
        return map.get_forbidden();
    }

    public static int get_size_asteroid(){ return Asteroid.get_size();}
    public static double get_fact_x(){ return map.get_fact_x();}
    public static double get_fact_y(){ return map.get_fact_y();}

    public static void delete_start_wave_controller(){
        map.delete_start_wave_gui();
    }
}
