package Model;

import javafx.scene.image.Image;
import javafx.util.Pair;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class NPC implements Serializable, Redrawable {
    private double pos_x, pos_y;
    private int health;
    private double speed, freezed = 0.0, curr_ind;
    private Path_custom path;
    private ArrayList<Double> pos_x_snowflakes = new ArrayList<>(), pos_y_snowflakes = new ArrayList<>();
    private static final Object key = new Object();

    protected NPC(double pos_x, double pos_y, double speed, int health, Path_custom path){ //Protected pour empêcher de créer un PNJ sans préciser si il est petit, moyen ou grand
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.speed = speed;
        this.health = health;
        this.path = path;
        curr_ind = path.get_pos_size()-1;
    }

    public double get_pos_x(){return pos_x;}
    public double get_pos_y(){return pos_y;}

    public boolean is_shot(Classic_munition munition){
        synchronized (key) {
            boolean res = check_shot_by_munition(munition);
            if (res) {
                System.out.println(health);
                health -= munition.get_tower().get_power();
                Sound blast_snd = TinySound.loadSound("Songs/blast.wav");
                blast_snd.play(0.8);
                if (health <= 0) {
                    if (Board.get_instance().remove_npc(this)) {
                        Sound destroyed_snd = TinySound.loadSound("Songs/explosion.wav");
                        destroyed_snd.play(2);
                        Game.get_instance().increment_npc_destroyed(); // Only if a NPC has really been removec
                    }
                }
            }
            return res;
        }
    }

    public boolean is_shot(Freezing_munition munition){
        boolean res = check_shot_by_munition(munition);
        if (res){
            pos_x_snowflakes.clear();
            pos_y_snowflakes.clear();
            Sound freezed_snd = TinySound.loadSound("Songs/freeze.wav");
            freezed_snd.play();
            set_freezed(munition.get_tower().get_power());
        }
        return res;
    }

    private void set_freezed(double time){
        freezed = time;
    }

    public void decrease_freezed(double time){
        if (freezed>0){
            freezed -= time;
            freezed = Math.max(freezed, 0);
        }
    }

    private boolean check_shot_by_munition(Munition munition){
        double munition_pos_x = munition.get_pos_x();
        double munition_pos_y = munition.get_pos_y();
        return Math.sqrt(Math.pow(munition_pos_x - pos_x, 2) + Math.pow(munition_pos_y - pos_y, 2)) < 5;
    }

    public Path_custom get_path(){ return path;}
    public double is_frozen(){ return freezed;}
    public ArrayList<Double> get_pos_x_snowflakes(){ return pos_x_snowflakes;}
    public ArrayList<Double> get_pos_y_snowflakes(){ return pos_y_snowflakes;}

    public void add_snowflake(double x, double y){
        pos_x_snowflakes.add(x);
        pos_y_snowflakes.add(y);
    }

    public double get_direction() {
        double dir_x = get_path().next_pos(Math.max((int)Math.round(curr_ind-10), 0), pos_y, speed).getKey() - pos_x;
        double dir_y = get_path().next_pos(Math.max((int)Math.round(curr_ind-10), 0), pos_y, speed).getValue() - pos_y;
        return Math.atan(dir_y/dir_x)*180/Math.PI;
    }

    public abstract int get_radius();

    public abstract Image get_image();
    public abstract double get_size();

    public void update_pos(){
        double curr_speed = speed;
        if (is_frozen() > 0){
            curr_speed /= 3;
            decrease_freezed(1.0/Game.get_instance().get_fps());
        }
        curr_ind -= curr_speed;

        Pair<Double, Double> pos = path.next_pos((int)Math.round(curr_ind), pos_y, curr_speed);
        pos_x = pos.getKey();
        pos_y = pos.getValue();

        if (pos.getKey() <= 0){
            Board.get_instance().remove_npc(this);
            Sound negative_snd = TinySound.loadSound("Songs/negative.wav");
            negative_snd.play(3);
            Game.get_instance().decrease_score(Game.get_instance().get_score_lost());
        }
    }
}
