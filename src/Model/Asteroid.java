package Model;

public class Asteroid {
    private double pos_x, pos_y;
    private boolean occupied;

    public Asteroid(double pos_x, double pos_y){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        occupied = false;
    }

    public double get_pos_x(){return pos_x;}
    public double get_pos_y(){return pos_y;}

    public boolean is_occupied() {return occupied;}
    public void occupy(Tower tower){occupied = true;}
    public void unoccupy(Tower tower){occupied = false;}
}
