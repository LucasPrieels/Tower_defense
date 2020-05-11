package Controller;

import Model.Game;
import View.Map;
import javafx.application.Platform;

public class Message implements Runnable{
    private static final Object key = new Object();
    private static Map map;

    // Message handles the possibility to print messages on the GUI for the user

    public Message(){ }

    public static void init(Map map){ Message.map = map;}

    public static void set_const_message(String message){ map.set_curr_message(message);} // Message set until another one is given

    public static void set_temp_message(String message) { // Message set foe 3 seconds
        Thread thread_message = new Thread(new Message());
        Game.get_instance().add_thread(thread_message);
        map.set_curr_message(message);
        thread_message.start();
    }

    public void run() {
        String message = map.get_curr_message();
        double t = 0; // Time in ms
        while (t < 3000) {
            try {
                synchronized (key) {
                    Platform.runLater(() -> map.show_message_displayed());
                    Thread.sleep((long) (500.0 / Game.get_instance().get_fps()));
                    t += 500.0 / Game.get_instance().get_fps();
                }
            } catch (InterruptedException e) {
                return; // If the thread is interrupted, we stop it
            }
        }
        if (map.get_curr_message().equals(message)) map.set_curr_message(""); // If the message has not been changed in the 3 seconds, it is deleted
    }
}
