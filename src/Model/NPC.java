package Model;

import javafx.scene.image.Image;
import javafx.util.Pair;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class NPC implements Serializable, Movable {
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

    public void set_freezed(double time){
        freezed = time;
    }

    public void clear_snowflakes(){
        pos_x_snowflakes.clear();
        pos_y_snowflakes.clear();
    }

    public void decrease_freezed(double time){
        if (freezed>0){
            freezed -= time;
            freezed = Math.max(freezed, 0);
        }
    }

    public void decrease_health(int num){ health -= num;}
    public int get_health(){ return health;}

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
