package View;

import Model.*;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Map extends Parent implements Runnable, Serializable {
    private int level, score, money, wave, timer, npc_destroyed, npc_destroyed_needed;
    private static double canvas_height, canvas_width;
    private static int size_asteroid = 50, size_small_npc = 35, size_med_npc = 50, size_big_npc = 70, num_diff_asteroid = 8, size_munitions = 10;
    private static Canvas canvas;
    private GraphicsContext gc;
    private Stage stage;
    private static Map instance = null;
    private transient Image im_small_npc, im_med_npc, im_big_npc, factory_tower_img, level_background, score_img, money_img,wave_img,timer_img, classic_tower_img, freezing_tower_img, classic_munition_img, freezing_munition_img, start_wave_button,star_1,star_2, star_3,menu_button,exit_button ;
    private ArrayList<Image> planets = new ArrayList<>();
    private ImageView iv_small_npc, iv_med_npc, iv_big_npc,iv_start_wave_button, iv_menu_button, iv_exit_button;
    private transient Upgrade_tower_icon upgrade_tower_icon;
    private transient Buy_freezing_tower_icon buy_freezing_tower_icon;
    private transient Buy_factory_tower_icon buy_factory_tower_icon;
    private transient Buy_classic_tower_icon buy_classic_tower_icon;
    private ArrayList<Integer> type_asteroid = new ArrayList<>();
    private ArrayList<Double> pos_x_asteroid = new ArrayList<>(), pos_y_asteroid = new ArrayList<>();
    private static String curr_message;
    private double fact_x, fact_y;

    private Map(Stage stage, int level) throws FileNotFoundException {
        this.level = level;
        canvas_width = stage.getWidth();
        canvas_height = stage.getHeight();
        canvas = new Canvas(canvas_width, canvas_height);
        gc = canvas.getGraphicsContext2D();
        this.stage = stage;
        create_images();
        //draw_paths();
        init_canvas();
        drawScoreRectangle();
        draw_menu_buttons();
        for (Asteroid asteroid : Board.get_instance().get_asteroids()) {
            type_asteroid.add((int) Math.floor(Math.random() * 5.999999) + 1);
            pos_x_asteroid.add(asteroid.get_pos_x() * canvas.getWidth() / Board.get_instance().get_dim_x());
            pos_y_asteroid.add(asteroid.get_pos_y() * canvas.getHeight() / Board.get_instance().get_dim_y());
        }
        create_shop();
        fact_x = Map.get_canvas_width()/Board.get_instance().get_dim_x();
        fact_y = Map.get_canvas_height()/Board.get_instance().get_dim_y();
        this.getChildren().addAll(canvas, upgrade_tower_icon, buy_classic_tower_icon, buy_factory_tower_icon, buy_freezing_tower_icon,iv_start_wave_button,iv_exit_button,iv_menu_button);
    }

    //Singleton
    public static Map get_instance() {
        if (instance == null) {
            throw new AssertionError("Map has to be initialized before getting accessed");
        }
        return instance;
    }

    public static void init(Stage stage, int level) throws FileNotFoundException {
        if (instance != null) {
            throw new AssertionError("Map can't be initialized twice");
        }
        instance = new Map(stage, level);
    }

    private void init_canvas() throws FileNotFoundException {
        gc.drawImage(level_background, 0, 0); //modifier
        for (int i = 0; i < type_asteroid.size(); i++) {
            gc.drawImage(planets.get(type_asteroid.get(i)), pos_x_asteroid.get(i), pos_y_asteroid.get(i));
        }
    }

    public void create_images() throws FileNotFoundException {
        for (int j = 1; j < 4; j++) {
            if (level == j) {
                level_background = new Image(new FileInputStream("Images/level" + j + ".jpg"), stage.getWidth(), stage.getHeight(), false, false);
                for (int i = 0; i < num_diff_asteroid; i++) {
                    planets.add(new Image(new FileInputStream("Images/" + j + "planet" + (i + 1) + ".png"), size_asteroid, size_asteroid, false, false));
                }
            }
        }

        score_img = new Image(new FileInputStream("Images/score_rectangle_1.png"));
        money_img = new Image(new FileInputStream("Images/score_rectangle_2.png"));
        wave_img = new Image(new FileInputStream("Images/score_rectangle_3.png"));
        timer_img = new Image(new FileInputStream("Images/score_rectangle_4.png"));
        start_wave_button = new Image(new FileInputStream("Images/start_wave.png"));

        im_small_npc = new Image(new FileInputStream("Images/small_npc.png"), size_small_npc, size_small_npc, false, false);
        im_med_npc = new Image(new FileInputStream("Images/medium_npc.png"), size_med_npc, size_med_npc, false, false);
        im_big_npc = new Image(new FileInputStream("Images/big_npc.png"), size_big_npc, size_big_npc, false, false);

        iv_small_npc = new ImageView(im_small_npc);
        iv_small_npc.setRotate(-90);
        iv_med_npc = new ImageView(im_med_npc);
        iv_med_npc.setRotate(-90);
        iv_big_npc = new ImageView(im_big_npc);
        iv_big_npc.setRotate(-90);

        classic_tower_img = new Image(new FileInputStream("Images/classic_tower.png"));
        freezing_tower_img = new Image(new FileInputStream("Images/freezing_tower.png"));
        factory_tower_img = new Image(new FileInputStream("Images/factory_tower.png"));
        star_1 = new Image(new FileInputStream("Images/1star.png"));
        star_2 = new Image(new FileInputStream("Images/2star.png"));
        star_3 = new Image(new FileInputStream("Images/3star.png"));

        classic_munition_img = new Image(new FileInputStream("Images/Classic_munition.png"), size_munitions, size_munitions, false, false);
        freezing_munition_img = new Image(new FileInputStream("Images/Freezing_munition.png"), size_munitions, size_munitions, false, false);

        menu_button = new Image(new FileInputStream("Images/back_to_menu.png"));
        exit_button = new Image(new FileInputStream("Images/exit.png"));

    }

    public void update_canvas() throws FileNotFoundException {
        init_canvas();
        drawScoreRectangle();
        draw_paths();
        update_npc_canvas();
        update_munitions_canvas();
        show_message_displayed();
        show_towers();
        //System.out.println("Updated");
    }

    public void update_munitions_canvas(){
        for (Munition munition: Board.get_instance().get_munitions()){
            double pos_x = munition.get_pos_x(), pos_y = munition.get_pos_y();
            if (munition instanceof Classic_munition){
                gc.drawImage(classic_munition_img, pos_x*fact_x, pos_y*fact_y);
            }
            else if (munition instanceof Freezing_munition){
                gc.drawImage(freezing_munition_img, pos_x*fact_x, pos_y*fact_y);
            }
            else{
                System.out.println("ERREUR !!! Essaye d'imprimer une munition n'existant pas dans update_munitions_canvas");
            }
        }
    }

    private void show_towers() throws FileNotFoundException {
        for (Tower tower: Board.get_instance().get_towers()){
            if (tower instanceof Classic_tower){
                gc.drawImage(classic_tower_img, (tower.get_asteroid().get_pos_x()*fact_x)-(double)classic_tower_img.getWidth()/2+(double)classic_tower_img.getHeight()/2, (tower.get_asteroid().get_pos_y()*fact_y)-(double)classic_tower_img.getWidth()/2+(double)classic_tower_img.getWidth()/2);

                //gc.drawImage(star, tower.get_asteroid().get_pos_x()*fact_x,tower.get_asteroid().get_pos_y()*fact_y,20,15);
            }
            else if (tower instanceof Freezing_tower){
                gc.drawImage(freezing_tower_img, (tower.get_asteroid().get_pos_x()*fact_x)-(double)freezing_tower_img.getWidth()/2+(double)freezing_tower_img.getHeight()/2, (tower.get_asteroid().get_pos_y()*fact_y)-(double)freezing_tower_img.getWidth()/2+(double)freezing_tower_img.getWidth()/2);
            }
            else if (tower instanceof Factory_tower){
                gc.drawImage(factory_tower_img, (tower.get_asteroid().get_pos_x()*fact_x)-(double)factory_tower_img.getWidth()/2+(double)factory_tower_img.getHeight()/2, (tower.get_asteroid().get_pos_y()*fact_y)-(double)factory_tower_img.getWidth()/2+(double)factory_tower_img.getWidth()/2);
            }
            else{
                System.out.println("ERREUR !!! Essaye d'imprimer une tour n'existant pas dans show_towers");
            }
            for(int k = 0; k<= tower.get_max_level(); k++){
                if(tower.get_curr_level() == 0){
                    gc.drawImage(star_1, tower.get_asteroid().get_pos_x()*fact_x,tower.get_asteroid().get_pos_y()*fact_y,20,15);
                }
                else if(tower.get_curr_level() == 1){
                    gc.drawImage(star_2, tower.get_asteroid().get_pos_x()*fact_x,tower.get_asteroid().get_pos_y()*fact_y,20,15);
                }
                else if(tower.get_curr_level() == 2){
                    gc.drawImage(star_3, tower.get_asteroid().get_pos_x()*fact_x,tower.get_asteroid().get_pos_y()*fact_y,20,15);
                }
            }
        }
    }

    private void show_message_displayed() {
        gc.setFill(Color.rgb(0,0,0,0.5)); //noir transparent
        gc.fillRoundRect(canvas.getWidth()-330,canvas.getHeight()-160,330,45,15,25);
        gc.setFont(new Font("Arial", 20)); //trouver plus joli si temps
        gc.setFill(Color.WHITE);
        gc.fillText(curr_message, canvas.getWidth()-300, canvas.getHeight()-130);
    }

    public void update_npc_canvas() {
        CopyOnWriteArrayList<NPC> npcs = new CopyOnWriteArrayList<>(Board.get_instance().get_npcs());
        // Car on peut faire des suppressions d'éléments
        for (NPC npc : npcs) {
            //System.out.println(npc.get_pos_x() + " " + npc.get_pos_y() + " " + pos_x + " " + pos_y);
            //System.out.println(canvas.getHeight()/Board.get_dim_y());
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);

            double pos_x = npc.get_pos_x()*fact_x;
            double pos_y = npc.get_pos_y()*fact_x;

            //ATTENTION A CHANGER, ça ne permet pas d'ajouter facilement un nv type de PNJ
            //On peut garder les instanceof? Ou il faut faire un liste avec les petits une avec les grands etc dans Board?
            if (npc instanceof Small_NPC) {
                gc.drawImage(iv_small_npc.snapshot(params, null), pos_x/* - (double) size_small_npc / 2*/, pos_y/* - (double) size_small_npc / 2*/);
                // Le moins est là pour que ça soit le centre de l'image qui soit à la position spécifiée et pas le coin supérieur gauche
            } else if (npc instanceof Medium_NPC) {
                gc.drawImage(iv_med_npc.snapshot(params, null), pos_x/* - (double) size_med_npc / 2*/, pos_y/* - (double) size_med_npc / 2*/);
            } else if (npc instanceof Big_NPC) {
                gc.drawImage(iv_big_npc.snapshot(params, null), pos_x/* - (double) size_big_npc / 2*/, pos_y/* - (double) size_big_npc / 2*/);
            }
        }
    }

    public void draw_paths() {
        for (Path2 path2 : Board.get_instance().get_paths()) {
            this.getChildren().add(path2.get_path_ui());
        }
    }

    private void drawScoreRectangle() {
        for(int i = 8;i<=168;i=i+40){
            gc.setFill(Color.rgb(0, 0, 0, 0.5)); //noir transparent
            gc.fillRoundRect(8, i, 120, 35, 15, 25);
        }

        gc.drawImage(score_img, 15, 17.5,20,20);
        gc.drawImage(money_img, 15, 57.5,25,20);
        gc.drawImage(wave_img, 13, 97.5,30,25);
        gc.drawImage(timer_img, 13, 130,30,25);
        gc.drawImage(im_big_npc, 13, 175,30,25);

        this.score = Game.get_instance().get_score();
        this.money = Game.get_instance().get_money();
        this.wave = Game.get_instance().get_curr_wave() + 1;
        this.timer = Game.get_instance().get_time_wave(Game.get_instance().get_curr_wave()) + Game.get_instance().get_time_between_waves() - Level.get_instance().get_waves().get(Game.get_instance().get_curr_wave()).get_time();
        this.npc_destroyed = Game.get_instance().get_npc_destroyed();


        gc.setFont(new Font("Arial", 18)); //trouver plus joli si temps
        gc.setFill(Color.WHITE);
        gc.fillText(Integer.toString(score), 50, 33);
        gc.fillText(Integer.toString(money), 50, 73);
        gc.fillText("Wave " + Integer.toString(wave), 50, 113);
        gc.fillText(Integer.toString(timer), 50, 153);
        gc.fillText(Integer.toString(npc_destroyed) + " PNJ" , 50, 193);

        //this.iv_start_wave_button = new ImageView(start_wave_button);
        //iv_start_wave_button.setX(15);
        //iv_start_wave_button.setY(170);
        //iv_start_wave_button.setFitHeight(40);
        //iv_start_wave_button.setFitWidth(40);
    }

    private void draw_menu_buttons(){
        iv_exit_button = new ImageView(exit_button);
        iv_exit_button.setX(canvas_width - exit_button.getWidth()-10);
        iv_exit_button.setY(10);

        iv_menu_button = new ImageView(menu_button);
        iv_menu_button.setX(canvas_width - 2*menu_button.getWidth()-20);
        iv_menu_button.setY(10);

        iv_exit_button.setOnMouseClicked(new Menu_buttons_listener(stage, "exit"));

    }

    private void create_shop() throws FileNotFoundException {
        this.upgrade_tower_icon = new Upgrade_tower_icon();
        this.buy_classic_tower_icon = new Buy_classic_tower_icon();
        this.buy_factory_tower_icon = new Buy_factory_tower_icon();
        this.buy_freezing_tower_icon = new Buy_freezing_tower_icon();
        this.iv_start_wave_button = new ImageView(start_wave_button);

        iv_start_wave_button.setX(canvas_width-5*(iv_start_wave_button.getFitWidth()+80));
        iv_start_wave_button.setY(canvas_height-iv_start_wave_button.getFitHeight()-100);
        iv_start_wave_button.setFitHeight(70);
        iv_start_wave_button.setFitWidth(70);

        upgrade_tower_icon.setOnMouseClicked(new ShopListener(gc, "Upgrade_tower", canvas));
        buy_classic_tower_icon.setOnMouseClicked(new ShopListener(gc, "Classic_tower", canvas));
        buy_factory_tower_icon.setOnMouseClicked(new ShopListener(gc, "Factory_tower", canvas));
        buy_freezing_tower_icon.setOnMouseClicked(new ShopListener(gc, "Freezing_tower", canvas));
        iv_start_wave_button.setOnMouseClicked(new Start_wave_listener());
    }

    public static double get_canvas_height() {
        return canvas.getHeight();
    }

    public static double get_canvas_width() {
        return canvas.getWidth();
    }

    public static double get_size_asteroid() {
        return size_asteroid;
    }

    public void set_const_message(String message){
        curr_message = message;
    }

    public void set_temp_message(String message) {
        Thread thread_message = new Thread(this);
        curr_message = message;
        thread_message.start();
    }

    public void run() {
        String message = curr_message;
        double t = 0;
        while (t < 3000) {
            Platform.runLater(() -> show_message_displayed());
            try {
                Thread.sleep((long) (1000.0 / Game.get_instance().get_fps()));
            } catch (InterruptedException e) {
                System.out.println("Erreur dans le thread d'affichage du message");
            }
            t += 1000.0 / Game.get_instance().get_fps();
        }
        if (curr_message == message) curr_message = ""; //Si le message n'a pas changé entretemps, on l'efface
    }

    public double get_fact_x(){return fact_x;}
    public double get_fact_y(){return fact_y;}
    public int get_level(){return level;}

    public static void set_instance(Map instance) {
        Map.instance = instance;
    }
}