package Model;

public class Asteroid {
    private double pos_x, pos_y;
    private Board board;

    public double get_pos_x(){
        return pos_x;
    }
    public double get_pos_y(){
        return pos_y;
    }

    public Board get_board(){
        return board;
    }
}
