package Model;

import Controller.Update_manager;
import javafx.scene.paint.Color;
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

    public Path get_path_ui() { // Creates a Path (from JavaFX) to be displayed
        double fact_x = Update_manager.get_fact_x(), fact_y = Update_manager.get_fact_y();
        Path path = new Path();

        double actual_width = width + 20; // +20 so that all the npcs are totally contained in the path

        MoveTo moveTo = new MoveTo();
        moveTo.setX(pos.get(0).getKey()*fact_x);
        moveTo.setY((pos.get(0).getValue() + 2*actual_width/3) * fact_y); // Initial point
        path.getElements().add(moveTo);

        for (int i = 1; i < pos.size(); i++) { // First path of the path
            double apparent_width;
            LineTo lineTo = new LineTo();
            double dy = pos.get(i).getValue() - pos.get(i-1).getValue(); // Difference of y at this point
            double dx = pos.get(i).getKey() - pos.get(i-1).getKey(); // Difference of x at this point
            // In order to have the width of this half-path equals to width perpendicularly to itself,
            // Its width should be apparent_width along x axis
            apparent_width = actual_width*(1-Math.cos(Math.atan(dy/dx))*Math.cos(Math.PI/2 - Math.atan(dy/dx)));

            int sign;
            if (dy <= 0) sign = 1;
            else sign = 0;

            lineTo.setX((pos.get(i).getKey() + sign*apparent_width) * fact_x);
            lineTo.setY((pos.get(i).getValue() + 2*actual_width/3) * fact_y);
            path.getElements().add(lineTo);
            path.setStrokeWidth(0);
        }

        for (int i = pos.size()-1; i > 0; i--){ // Second side of the path
            double apparent_width;
            LineTo lineTo2 = new LineTo();
            double dy = pos.get(i).getValue() - pos.get(i-1).getValue();
            double dx = pos.get(i).getKey() - pos.get(i-1).getKey();
            apparent_width = actual_width*(1-Math.cos(Math.atan(dy/dx))*Math.cos(Math.PI/2 - Math.atan(dy/dx)));

            int sign;
            if (dy > 0) sign = 1;
            else sign = 0;

            lineTo2.setX((pos.get(i).getKey() + sign*apparent_width) * fact_x);
            lineTo2.setY((pos.get(i).getValue() - actual_width/3) * fact_y);
            path.getElements().add(lineTo2);
            path.setStrokeWidth(0);
        }

        LineTo finalLine = new LineTo();
        finalLine.setX(0);
        finalLine.setY((pos.get(0).getValue() + 2*actual_width/3) * fact_y);
        path.getElements().add(finalLine);
        path.setStrokeWidth(0);

        path.setFill(Color.rgb(0,0,0,0.2)); // Darkens the path between the sides

        return path;
    }

    public Pair<Double, Double> next_pos(int curr_ind, double pos_y, double speed){
        // Returns the nex_pos of a NPC currently at position pos_y (associated with the point of the path given by curr_ind) with speed "speed"
        double offset = pos_y-pos.get(Math.min(curr_ind + (int)Math.round(speed), pos.size()-1)).getValue(); // + speed because we search for last position
        double new_pos_x = pos.get(Math.max(curr_ind, 0)).getKey(); // We have to round speed, which can be a double depending on the number of FPS
        double new_pos_y = Math.max(pos.get(Math.max(curr_ind, 0)).getValue() + offset, 0);
        return new Pair<>(new_pos_x, new_pos_y);
    }

    public int get_pos_size(){ return pos.size();}
    public int get_width(){return width;}
    public ArrayList<Pair<Double, Double>> get_pos(){ return pos;}
}
