package Model;

import javafx.scene.image.Image;

import java.io.Serializable;

public abstract class Munition implements Serializable, Movable, Scanner {
    private NPC npc;
    private Attack_tower tower;
    private double pos_x, pos_y, dir_x, dir_y, speed;
    private static double size_munition = 15;

    public Munition(Attack_tower tower, NPC npc, double speed){
        this.npc = npc;
        this.pos_x = tower.get_asteroid().get_pos_x();
        this.pos_y = tower.get_asteroid().get_pos_y();
        this.speed = speed;
        this.tower = tower;
    }

    public void update_pos(){
        //Direction is updated because the PNJ has moved since
        dir_x = npc.get_pos_x()-pos_x;
        dir_y = npc.get_pos_y()-pos_y;
        double old_dir_x = dir_x;
        dir_x /= Math.sqrt(dir_x*dir_x+dir_y*dir_y); // Direction is normed
        dir_y /= Math.sqrt(old_dir_x*old_dir_x+dir_y*dir_y);
        dir_x *= speed/Game.get_instance().get_fps();
        dir_y *= speed/Game.get_instance().get_fps();

        pos_x += dir_x;
        pos_y += dir_y;
    }

    private boolean check_shot(NPC npc){
        double npc_pos_x = npc.get_pos_x();
        double npc_pos_y = npc.get_pos_y();
        return Math.sqrt(Math.pow(npc_pos_x - pos_x, 2) + Math.pow(npc_pos_y - pos_y, 2)) < 5;
    }

    public boolean scan(){ // Checks if there is a NPC close enough from the munition
        for (NPC npc: Board.get_instance().get_npcs()){
            if (check_shot(npc)){ // Check_shot is different for different types of munition
                action_scanned(npc);
                return true;
            }
        }
        return false;
    }

    public NPC get_npc(){return npc;}
    public double get_pos_x(){return pos_x;}
    public double get_pos_y(){return pos_y;}
    public double get_dir_x(){return dir_x;}
    public double get_dir_y(){return dir_y;}
    public Attack_tower get_tower(){return tower;}
    public static double get_size_static_munition(){return size_munition;}

    public abstract Image get_image();
}

