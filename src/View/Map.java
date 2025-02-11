package View;

import Controller.Menu_buttons_listener;
import Controller.Shop_listener;
import Controller.Start_wave_listener;
import Controller.Update_manager;
import Model.*;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Map extends Parent{
    private static double canvas_height, canvas_width;
    private static int num_diff_asteroid = 8, size_snowflake = 6;
    private static Canvas canvas;
    private GraphicsContext gc;
    private Stage stage;
    private static Map instance = null;
    private transient Image level_background, score_img, money_img, wave_img, timer_img, start_wave_button, menu_button, exit_button, star1, star2, star3, snowflake, im_big_npc,buy_freezing_tower_icon, buy_factory_tower_icon,buy_classic_tower_icon, destroy_tower_icon, upgrade_tower_icon;
    private ArrayList<Image> planets = new ArrayList<>();
    private ImageView iv_start_wave_button, iv_menu_button, iv_exit_button,iv_buy_classic_tower_icon, iv_buy_factory_tower_icon, iv_buy_freezing_tower_icon, iv_destroy_tower_icon, iv_upgrade_tower_icon;
    private ArrayList<Integer> type_asteroid = new ArrayList<>();
    private ArrayList<Double> pos_x_asteroid = new ArrayList<>(), pos_y_asteroid = new ArrayList<>();
    private double fact_x, fact_y;
    private String curr_message = "";

    private Map(Stage stage) throws FileNotFoundException {
        canvas_width = stage.getWidth();
        canvas_height = stage.getHeight();
        canvas = new Canvas(canvas_width, canvas_height);

        gc = canvas.getGraphicsContext2D();
        this.stage = stage;
        create_images(); // Initializes all the images
        draw_score_rectangle();
        draw_menu_buttons();
        create_shop();
        fact_x = Map.get_canvas_width()/Board.get_instance().get_dim_x();
        fact_y = Map.get_canvas_height()/Board.get_instance().get_dim_y();
        this.getChildren().addAll(canvas, iv_upgrade_tower_icon, iv_buy_classic_tower_icon, iv_buy_factory_tower_icon, iv_buy_freezing_tower_icon,iv_destroy_tower_icon,iv_start_wave_button,iv_exit_button,iv_menu_button);
    }

    public static Map init(Stage stage) throws FileNotFoundException {
        if (instance != null) {
            throw new AssertionError("Map can't be initialized twice");
        }
        return instance = new Map(stage);
    }

    public void create_images() throws FileNotFoundException {
        int size_asteroid = Update_manager.get_size_static_asteroid();
        int level = Update_manager.get_level();
        level_background = new Image(new FileInputStream("Assets/level" + level + ".jpg"), stage.getWidth(), stage.getHeight(), false, false);
        for (int i = 0; i < num_diff_asteroid; i++) {
            planets.add(new Image(new FileInputStream("Assets/" + level + "planet" + (i + 1) + ".png"), size_asteroid, size_asteroid, false, false));
        }

        score_img = new Image(new FileInputStream("Assets/score_rectangle_1.png"));
        money_img = new Image(new FileInputStream("Assets/score_rectangle_2.png"));
        wave_img = new Image(new FileInputStream("Assets/score_rectangle_3.png"));
        timer_img = new Image(new FileInputStream("Assets/score_rectangle_4.png"));
        start_wave_button = new Image(new FileInputStream("Assets/start_wave.png"));
        buy_classic_tower_icon = new Image(new FileInputStream("Assets/classic_tower_icon.png"));
        buy_factory_tower_icon = new Image(new FileInputStream("Assets/factory_tower_icon.png"));
        buy_freezing_tower_icon = new Image(new FileInputStream("Assets/freezing_tower_icon.png"));
        destroy_tower_icon = new Image(new FileInputStream("Assets/moins.png"));
        upgrade_tower_icon = new Image(new FileInputStream("Assets/plus.png"));

        star1 = new Image(new FileInputStream("Assets/1star.png"));
        star2 = new Image(new FileInputStream("Assets/2star.png"));
        star3 = new Image(new FileInputStream("Assets/3star.png"));

        snowflake = new Image(new FileInputStream("Assets/flocon.png"));

        menu_button = new Image(new FileInputStream("Assets/back_to_menu.png"));
        exit_button = new Image(new FileInputStream("Assets/exit.png"));

        im_big_npc = new Image(new FileInputStream("Assets/Big_NPC.png")); // For the score rectangle
    }

    private void draw_shop_icons(){
        iv_buy_classic_tower_icon = new ImageView(buy_classic_tower_icon);
        iv_buy_classic_tower_icon.setX(Map.get_canvas_width()-5*(iv_buy_classic_tower_icon.getFitWidth()+70));
        iv_buy_classic_tower_icon.setY(Map.get_canvas_height()-iv_buy_classic_tower_icon.getFitHeight()-90);
        iv_buy_classic_tower_icon.setFitWidth(60);
        iv_buy_classic_tower_icon.setFitHeight(60);

        iv_buy_factory_tower_icon = new ImageView(buy_factory_tower_icon);
        iv_buy_factory_tower_icon.setX(Map.get_canvas_width()-3*(iv_buy_factory_tower_icon.getFitWidth()+70));
        iv_buy_factory_tower_icon.setY(Map.get_canvas_height()-iv_buy_factory_tower_icon.getFitHeight()-90);
        iv_buy_factory_tower_icon.setFitWidth(60);
        iv_buy_factory_tower_icon.setFitHeight(60);

        iv_buy_freezing_tower_icon = new ImageView(buy_freezing_tower_icon);
        iv_buy_freezing_tower_icon.setX(Map.get_canvas_width()-4*(iv_buy_freezing_tower_icon.getFitWidth()+70));
        iv_buy_freezing_tower_icon.setY(Map.get_canvas_height()-iv_buy_freezing_tower_icon.getFitHeight()-90);
        iv_buy_freezing_tower_icon.setFitWidth(60);
        iv_buy_freezing_tower_icon.setFitHeight(60);

        iv_destroy_tower_icon = new ImageView(destroy_tower_icon);
        iv_destroy_tower_icon.setX(Map.get_canvas_width()-iv_destroy_tower_icon.getFitWidth()-70);
        iv_destroy_tower_icon.setY(Map.get_canvas_height()-iv_destroy_tower_icon.getFitHeight()-90);
        iv_destroy_tower_icon.setFitWidth(60);
        iv_destroy_tower_icon.setFitHeight(60);

        iv_upgrade_tower_icon = new ImageView(upgrade_tower_icon);
        iv_upgrade_tower_icon.setX(Map.get_canvas_width()-2*(iv_upgrade_tower_icon.getFitWidth()+70));
        iv_upgrade_tower_icon.setY(Map.get_canvas_height()-iv_upgrade_tower_icon.getFitHeight()-90);
        iv_upgrade_tower_icon.setFitHeight(60);
        iv_upgrade_tower_icon.setFitWidth(60);

        iv_start_wave_button = new ImageView(start_wave_button);
        iv_start_wave_button.setX(canvas_width - 6*(iv_start_wave_button.getFitWidth() + 70));
        iv_start_wave_button.setY(canvas_height - iv_start_wave_button.getFitHeight() - 90);
        iv_start_wave_button.setFitHeight(60);
        iv_start_wave_button.setFitWidth(60);
    }

    private void create_shop() {
        draw_shop_icons();

        iv_upgrade_tower_icon.setOnMouseClicked(new Shop_listener("Upgrade_tower", canvas));
        iv_buy_classic_tower_icon.setOnMouseClicked(new Shop_listener("Classic_tower", canvas));
        iv_buy_factory_tower_icon.setOnMouseClicked(new Shop_listener("Factory_tower", canvas));
        iv_buy_freezing_tower_icon.setOnMouseClicked(new Shop_listener("Freezing_tower", canvas));
        iv_destroy_tower_icon.setOnMouseClicked(new Shop_listener("Destroy_tower", canvas));
        iv_start_wave_button.setOnMouseClicked(new Start_wave_listener());

        String string_classic = " Price: " + Game.get_instance().get_price_classic_tower() + " coins\n";
        Tooltip tooltip=new Tooltip(string_classic);
        Tooltip.install(iv_buy_classic_tower_icon, tooltip);


        String string_factory = " Price: " + Game.get_instance().get_price_factory_tower() + " coins \n";
        Tooltip tooltip1 = new Tooltip(string_factory);
        Tooltip.install(iv_buy_factory_tower_icon, tooltip1);

        String string_freezing = " Price: " + Game.get_instance().get_price_freezing_tower() + " coins\n";
        Tooltip tooltip2=new Tooltip(string_freezing);
        Tooltip.install(iv_buy_freezing_tower_icon, tooltip2);
    }

    public void draw_paths() {
        for (Path_custom path2 : Board.get_instance().get_paths()) {
            Path path = path2.get_path_ui();
            this.getChildren().add(path);
        }
    }

    public void show_asteroids_gui(){
        for (Asteroid asteroid : Board.get_instance().get_asteroids()) {
            type_asteroid.add((int) Math.floor(Math.random() * 6) + 1); // Randomly select an image of asteroid
            pos_x_asteroid.add(asteroid.get_pos_x() * canvas.getWidth() / Board.get_instance().get_dim_x());
            pos_y_asteroid.add(asteroid.get_pos_y() * canvas.getHeight() / Board.get_instance().get_dim_y());
        }
    }

    public void draw_iv(ImageView iv, double x, double y){ // Draws an ImageView on the canvas
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image image = iv.snapshot(params, null);
        gc.drawImage(image, x*fact_x, y*fact_y);
    }

    public void draw_img(Image img, double x, double y){ // Draws an image on the canvas
        gc.drawImage(img, x*fact_x, y*fact_y);
    }

    public void draw_star(int num, double x, double y){ // Draws starts associated with the level of towers
        if (num == 1) gc.drawImage(star1, x*fact_x,y*fact_y,20,15);
        else if (num == 2) gc.drawImage(star2, x*fact_x,y*fact_y,20,15);
        else gc.drawImage(star3, x*fact_x,y*fact_y,20,15);
    }

    public void draw_snowflake(double x, double y){ // Draws snowflakes for freezed NPCs
        gc.drawImage(snowflake, x * fact_x, y * fact_y, size_snowflake, size_snowflake);
    }

    public void fill_score_rectangle(int score, int money, int wave, int timer, int npc_destroyed){ // Fills the rectangle with score, money, etc with data
        gc.setFont(new Font("Arial", 15));
        gc.setFill(Color.WHITE);
        gc.fillText(Integer.toString(score), 50, 33);
        gc.fillText(Integer.toString(money), 50, 73);
        gc.fillText("Wave " + wave, 50, 113);
        gc.fillText(Integer.toString(timer), 50, 153);
        gc.fillText(npc_destroyed + " PNJ", 50, 193);
    }



    public void draw_gui(){
        gc.drawImage(level_background, 0, 0); // Cover the previous images
        draw_asteroids();
        draw_score_rectangle();
        //show_forbidden_zones(); // To see rectangles where no asteroid can be placed (behind buttons)
    }

    private void draw_asteroids(){
        for (int i = 0; i < type_asteroid.size(); i++) {
            gc.drawImage(planets.get(type_asteroid.get(i)), pos_x_asteroid.get(i), pos_y_asteroid.get(i));
        }
    }

    private void draw_score_rectangle() {
        for (int i = 8; i <= 168; i = i + 40) {
            gc.setFill(Color.rgb(0, 0, 0, 0.5)); //noir transparent
            gc.fillRoundRect(8, i, 120, 35, 15, 25);
        }

        gc.drawImage(score_img, 15, 17.5, 20, 20);
        gc.drawImage(money_img, 15, 57.5, 25, 20);
        gc.drawImage(wave_img, 13, 97.5, 30, 25);
        gc.drawImage(timer_img, 13, 130, 30, 25);
        gc.drawImage(im_big_npc, 13, 175, 30, 25);
    }

    private void draw_menu_buttons() {
        iv_exit_button = new ImageView(exit_button);
        iv_exit_button.setX(canvas_width - exit_button.getWidth() - 10);
        iv_exit_button.setY(10);
        iv_exit_button.setOnMouseClicked(new Menu_buttons_listener(stage, "exit"));

        iv_menu_button = new ImageView(menu_button);
        iv_menu_button.setX(canvas_width - 2 * menu_button.getWidth() - 20);
        iv_menu_button.setY(10);
        iv_menu_button.setOnMouseClicked(new Menu_buttons_listener(stage, "menu"));
    }

    public void show_message_displayed() {
        gc.setFill(Color.rgb(0, 0, 0, 0.5)); //noir transparent
        gc.fillRoundRect(canvas.getWidth() - 330, canvas.getHeight() - 150, 330, 45, 15, 25);
        gc.setFont(new Font("Arial", 20)); //trouver plus joli si temps
        gc.setFill(Color.WHITE);
        gc.fillText(curr_message, canvas.getWidth() - 300, canvas.getHeight() - 120);
    }

    public void delete_start_wave_gui(){
        iv_start_wave_button.setX(-100); // Moves the button outside of the canvas
        iv_start_wave_button.setY(-100);
    }

    public static void set_instance(Map instance){ Map.instance = instance;}

    public ArrayList<ArrayList<Double>> get_forbidden(){
        ArrayList<ArrayList<Double>> forbidden = new ArrayList<>(); // zones where asteroids can't be placed
        forbidden.add(new ArrayList<>(Arrays.asList(canvas.getWidth()-440, canvas.getWidth(), canvas.getHeight()-190, canvas.getHeight())));
        forbidden.add(new ArrayList<>(Arrays.asList(0.0, 150.0, 0.0, 220.0)));
        forbidden.add(new ArrayList<>(Arrays.asList(canvas_width - 2 * menu_button.getWidth() - 50, canvas_width, 0.0, 70.0)));
        return forbidden;
    }

    public String get_curr_message(){ return curr_message;}
    public void set_curr_message(String message){ curr_message = message;}
    public static double get_canvas_height(){ return canvas.getHeight();}
    public static double get_canvas_width(){ return canvas.getWidth(); }
    public double get_fact_x(){return fact_x;}
    public double get_fact_y(){return fact_y;}

    /*
    public void show_forbidden_zones(){
        for (ArrayList<Double> forbid: Update_manager.get_forbidden()){
            double xmin = forbid.get(0);
            double xmax = forbid.get(1);
            double ymin = forbid.get(2);
            double ymax = forbid.get(3);
            gc.drawImage(snowflake, xmin, ymin, size_snowflake, size_snowflake);
            gc.drawImage(snowflake, xmin, ymax, size_snowflake, size_snowflake);
            gc.drawImage(snowflake, xmax, ymin, size_snowflake, size_snowflake);
            gc.drawImage(snowflake, xmax, ymax, size_snowflake, size_snowflake);
        }
    }
     */
}