package Model;

public class Asteroid {
    private double pos_x, pos_y;

    public Asteroid(int pos_x, int pos_y){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }

    public double get_pos_x(){
        return pos_x;
    }
    public double get_pos_y(){
        return pos_y;
    }
}
