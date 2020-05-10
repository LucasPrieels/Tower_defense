package Controller;

import Model.Board;
import Model.Save;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;

public class Launch_manager {
    private static final Object key = new Object();

    public static void launch_game(Stage theStage){
        Group root_play = new Group();
        //theStage.setScene(new Scene(root2,1920,1080));
        theStage.setScene(new Scene(root_play, theStage.getWidth(), theStage.getHeight()));
        theStage.show();
        View.Map map;
        System.out.println("Launching");
        map = Update_manager.first_update_window(theStage);
        if (Board.get_instance().get_asteroids().isEmpty()) Board.get_instance().create_asteroids_random();
        Update_manager.show_asteroids_controller();
        Update_manager.update_window();
        root_play.getChildren().add(map);
    }

    public static void save_data(){
        synchronized (key) {
            try {
                System.out.println("Saving...");
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("towerDefense.serial"));
                Save save = new Save();
                oos.writeObject(save);
                oos.flush();
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int load_data(){
        Save save;
        try{
            System.out.println("Loading...");
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("towerDefense.serial"));
            save = (Save) ois.readObject();
            save.init();
            ois.close();
        } catch (Exception e) {
            return -1;
        }
        return save.get_level();
    }
}
