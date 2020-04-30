package Model;

import View.Map;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class Path2 {
    private int width;
    private double[] pos;

    public Path2(double[] pos, int width){
        this.pos = pos;
        this.width = width;
    }

    public double get_ord(int x){return pos[x];}

    public double[] next_pos(double pos_x, double pos_y, double speed){
        double offset = pos_y-pos[(int)Math.round(pos_x)];
        return new double[]{Math.max(pos_x-speed, 0), Math.max(pos[Math.max((int)(Math.round(pos_x-speed)),0)]+offset, 0)};
    }

    public int get_width(){return width;}

    public Path get_path_ui(){
        double fact_x = Map.get_instance().get_fact_x(), fact_y = Map.get_instance().get_fact_y();
        Path path = new Path();

        MoveTo moveTo = new MoveTo();
        moveTo.setX(0);
        moveTo.setY(pos[0]*fact_y);
        path.getElements().add(moveTo);

        for (int i=1; i<Board.get_dim_x(); i += Math.round((double)Board.get_dim_x()/50)){
            LineTo lineTo = new LineTo();
            lineTo.setX(i*fact_x);
            lineTo.setY(pos[i]*fact_y);
            //System.out.println("Path " + i + " " + pos[i] + " " + i*fact_x + " " + pos[i]*fact_y);
            path.getElements().add(lineTo);
        }
        int last_x = Board.get_dim_x()-1;
        LineTo lineTo = new LineTo();
        lineTo.setX(last_x*fact_x);
        lineTo.setY(pos[last_x]*fact_y);
        path.getElements().add(lineTo);
        return path;
    }
}
