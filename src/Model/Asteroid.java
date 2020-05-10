package Model;

import java.io.Serializable;

public class Asteroid implements Serializable {
    private double pos_x, pos_y;
    private boolean occupied;
    private Tower tower;
    private static int size_gui = 50;

    public Asteroid(double pos_x, double pos_y){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        occupied = false;
        tower = null;
    }

    public double get_pos_x(){return pos_x;}
    public double get_pos_y(){return pos_y;}

    public boolean is_occupied() {return occupied;}

    public void occupy(Tower tower){
        occupied = true;
        this.tower = tower;
    }
    public void unoccupy(){
        occupied = false;
        this.tower = null;
    }
}
