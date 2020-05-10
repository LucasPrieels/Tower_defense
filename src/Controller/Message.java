package Controller;

import Model.Game;
import View.Map;
import javafx.application.Platform;

public class Message implements Runnable{
    private static final Object key = new Object();
    private static Map map;

    public void Message(){ }

    public static void init(Map map){
        Message.map = map;
    }

    public static void set_const_message(String message) {
        map.set_curr_message(message);
    }

    public static void set_temp_message(String message) {
        Thread thread_message = new Thread();
        Game.get_instance().add_thread(thread_message);
        map.set_curr_message(message);
        thread_message.start();
    }

    public void run() {
        String message = map.get_curr_message();
        double t = 0;
        while (t < 3000) {
            try {
                synchronized (key) {
                    Platform.runLater(() -> map.show_message_displayed());
                    Thread.sleep((long) (1000.0 / Game.get_instance().get_fps()));
                    t += 1000.0 / Game.get_instance().get_fps();
                }
            } catch (InterruptedException e) {
                return;
            }
        }
        if (map.get_curr_message() == message) map.set_curr_message(""); //Si le message n'a pas changé entretemps, on l'efface
    }
}
