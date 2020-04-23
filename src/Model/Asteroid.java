package Model;

public class Asteroid {
    private int pos_x, pos_y;
    private boolean occupied;

    public Asteroid(int pos_x, int pos_y){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        occupied = false;
    }

    public int get_pos_x(){return pos_x;}
    public int get_pos_y(){return pos_y;}

    public boolean is_occupied() {return occupied;}
    public void occupy(Tower tower){occupied = true;}
    public void unoccupy(Tower tower){occupied = false;}
}
