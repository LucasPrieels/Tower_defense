package Controller;

import Model.*;
import View.Map;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.CopyOnWriteArrayList;

public class Update_manager {

    private Update_manager(){ }

    public static void update_window(){
        //System.out.println("Updating");
        try{
            Map map = Map.get_instance();
            map.update_canvas();
        } catch(AssertionError e){}
    }
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

        Map.get_instance().fill_score_rectangle(score, money, wave, timer, npc_destroyed);
    }

    public static void update_munitions_canvas() {
        CopyOnWriteArrayList<Munition> copyMunitions = new CopyOnWriteArrayList<>(Board.get_instance().get_munitions());
        for (Munition munition : copyMunitions) {
            double pos_x = munition.get_pos_x(), pos_y = munition.get_pos_y();

            Image munition_img = munition.get_image();
            ImageView munition_iv = new ImageView(munition_img);
            munition_iv.setRotate(Math.atan(munition.get_dir_y()/munition.get_dir_x())*180/Math.PI);
            Map.get_instance().draw_iv(munition_iv, pos_x, pos_y);
        }
    }

    private static void show_towers(){
        for (Tower tower: Board.get_instance().get_towers()){
            Image tower_img = tower.get_image();
            Map.get_instance().draw_img(tower_img, tower.get_asteroid().get_pos_x(), tower.get_asteroid().get_pos_y());

            for(int k = 0; k<= tower.get_max_level(); k++){
                Map.get_instance().draw_star(tower.get_curr_level()+1, tower.get_asteroid().get_pos_x(), tower.get_asteroid().get_pos_y());
            }
        }
    }

    public static void update_npc_canvas() {
        CopyOnWriteArrayList<NPC> npcs = new CopyOnWriteArrayList<>(Board.get_instance().get_npcs());
        // Car on peut faire des suppressions d'éléments
        for (NPC npc : npcs) {
            ImageView npc_iv = new ImageView(npc.get_image());
            npc_iv.setRotate(npc.get_direction() - 90);
            Map.get_instance().draw_iv(npc_iv, npc.get_pos_x(), npc.get_pos_y());

            if (npc.is_frozen() > 0){
                for (int i = 0; i < npc.get_pos_x_snowflakes().size(); i++){
                    Map.get_instance().draw_snowflake(npc.get_pos_x_snowflakes().get(i), npc.get_pos_y_snowflakes().get(i));
                }
                if (Math.random() < 0.8){
                    npc.add_snowflake(npc.get_pos_x() + npc.get_size()/6, npc.get_pos_y() + Math.random()*npc.get_size()/3 - npc.get_size()/6 + + npc.get_size()/6);
                }
            }
        }
    }

    public static int get_level() {
        return Game.get_instance().get_num_level();
    }
}
