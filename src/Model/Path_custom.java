package Model;

import Controller.Update_manager;
import View.Map;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;

public class Path_custom implements Serializable {
    private int width;
    private ArrayList<Pair<Double, Double>> pos;

    public Path_custom(ArrayList<Pair<Double, Double>> pos, int width){
        this.pos = pos;
        this.width = width;
    }

    public int get_pos_size(){
        return pos.size();
    }

    /*
    public double get_ord(int pos_x){
        for (int i = 0; i<pos.size(); i++){
            if (Math.abs(pos.get(i).getKey() - pos_x) < 2) return pos.get(i).getValue();
        }
        throw new AssertionError("In get_ord in Path_custom.java, pos_x = " + pos_x + " corresponds to none of the positions of this path");
    }
     */

    public Pair<Double, Double> next_pos(int curr_ind, double pos_x, double pos_y, double speed){
        double offset = pos_y-pos.get(Math.min(curr_ind + (int)Math.round(speed), pos.size()-1)).getValue(); // + speed because we search for last position
        double new_pos_x = pos.get(Math.max(curr_ind, 0)).getKey(); // We have to round speed, which can be a double depending on the number of FPS
        double new_pos_y = Math.max(pos.get(Math.max(curr_ind, 0)).getValue() + offset, 0);
        return new Pair<>(new_pos_x, new_pos_y);
    }

    public int get_width(){return width;}

    public Path get_path_ui(){
        double fact_x = Update_manager.get_fact_x(), fact_y = Update_manager.get_fact_y();
        Path path = new Path();

        MoveTo moveTo = new MoveTo();
        moveTo.setX(pos.get(0).getKey());
        moveTo.setY(pos.get(0).getValue()*fact_y);
        path.getElements().add(moveTo);

        for (int i = 1; i < pos.size(); i++){
            LineTo lineTo = new LineTo();
            lineTo.setX(pos.get(i).getKey()*fact_x);
            lineTo.setY(pos.get(i).getValue()*fact_y);
            path.getElements().add(lineTo);
        }
        return path;
    }

    public ArrayList<Pair<Double, Double>> get_pos(){ return pos;}
}
