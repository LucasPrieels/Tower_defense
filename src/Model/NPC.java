package Model;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class NPC implements Serializable {
    private double pos_x, pos_y;
    private int health;
    private double speed, freezed = 0.0;
    private Path2 path;
    private transient Sound blast_snd, destroyed_snd, freezed_snd;
    private ArrayList<Double> pos_x_snowflakes = new ArrayList<>(), pos_y_snowflakes = new ArrayList<>();

    protected NPC(double pos_x, double pos_y, double speed, int health, Path2 path){ //Protected pour empêcher de créer un PNJ sans préciser si il est petit, moyen ou grand
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.speed = speed;
        this.health = health;
        this.path = path;
    }

    public double get_pos_x(){return pos_x;}
    public double get_pos_y(){return pos_y;}

    public void set_pos_x(double pos_x){this.pos_x = pos_x;}
    public void set_pos_y(double pos_y){this.pos_y = pos_y;}

    public boolean is_shot(Classic_munition munition){
        boolean res = check_shot_by_munition(munition);
        if (res){
            health -= munition.get_tower().get_power();
            blast_snd = TinySound.loadSound("Songs/blast.wav");
            blast_snd.play(0.8);
            if (health <= 0){
                if (Board.get_instance().remove_npc(this)){
                    destroyed_snd = TinySound.loadSound("Songs/explosion.wav");
                    destroyed_snd.play(2);
                    Game.get_instance().increment_npc_destroyed(); // Only if a NPC has really been removec
                }
            }
        }
        return res;
    }

    public boolean is_shot(Freezing_munition munition){
        boolean res = check_shot_by_munition(munition);
        if (res){
            pos_x_snowflakes.clear();
            pos_y_snowflakes.clear();
            freezed_snd = TinySound.loadSound("Songs/freeze.wav");
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
        if(Math.sqrt(Math.pow(munition_pos_x - pos_x, 2)+ Math.pow(munition_pos_y - pos_y, 2))<10) return true;
        return false;
    }

    public Path2 get_path(){ return path;}
    public double get_speed(){ return speed;}
    public double is_frozen(){ return freezed;}
    public ArrayList<Double> get_pos_x_snowflakes(){ return pos_x_snowflakes;}
    public ArrayList<Double> get_pos_y_snowflakes(){ return pos_y_snowflakes;}
    public void add_snowflake(double x, double y){
        pos_x_snowflakes.add(x);
        pos_y_snowflakes.add(y);
    }

    public double get_direction() {
        double dir_x = get_path().next_pos(pos_x, pos_y, speed)[0] - pos_x;
        double dir_y = get_path().next_pos(pos_x, pos_y, speed)[1] - pos_y;
        return Math.atan(dir_y/dir_x)*180/Math.PI;
    }

    public abstract int get_radius();
}
