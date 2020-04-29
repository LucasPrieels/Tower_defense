package Model;

public class Asteroid {
    private double pos_x, pos_y;
    private boolean occupied;
    private Tower tower;

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
