package Controller;

import View.Map;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Update_manager {
    private static Update_manager instance = null;

    private Update_manager(){

    }
    //Singleton
    public static Update_manager get_instance(){
        if (instance == null){instance = new Update_manager();}
        return instance;
    }

    public void update_window() throws FileNotFoundException {
        //System.out.println("Updating");
        Map map = Map.get_instance();
        map.update_canvas();
    }
}
