package View;

import Model.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class Map extends Parent implements Runnable, Serializable {
    private int score, money, wave, timer, npc_destroyed;
    private int level;
    private static double canvas_height, canvas_width;
    private static int size_asteroid = 50, size_small_npc = 35, size_med_npc = 50, size_big_npc = 70, num_diff_asteroid = 8, size_munitions = 13, size_snowflake = 8;
    private static Canvas canvas;
    private GraphicsContext gc;
    private Stage stage;
    private static Map instance = null;
    private transient Image im_small_npc, im_med_npc, im_big_npc, factory_tower_img, level_background, score_img, money_img, wave_img, timer_img, classic_tower_img, freezing_tower_img, classic_munition_img, freezing_munition_img, start_wave_button, star_1, star_2, star_3, menu_button, exit_button, snowflake, win, gameover;
    private ArrayList<Image> planets = new ArrayList<>();
    private ImageView iv_small_npc, iv_med_npc, iv_big_npc, iv_start_wave_button, iv_menu_button, iv_exit_button, iv_classic_munition, iv_freezing_munition;
    private transient Upgrade_tower_icon upgrade_tower_icon;
    private transient Buy_freezing_tower_icon buy_freezing_tower_icon;
    private transient Buy_factory_tower_icon buy_factory_tower_icon;
    private transient Buy_classic_tower_icon buy_classic_tower_icon;
    private transient Destroy_tower_icon destroy_tower_icon;
    private ArrayList<Integer> type_asteroid = new ArrayList<>();
    private ArrayList<Double> pos_x_asteroid = new ArrayList<>(), pos_y_asteroid = new ArrayList<>();
    private static String curr_message;
    private double fact_x, fact_y;
    private static final Object key = new Object();
    private transient Sound game_over_snd, won_snd;
    private boolean game_over  = false;
    private boolean win_game = false;



    private Map(Stage stage, int level) throws FileNotFoundException {
        this.level = level;
        canvas_width = stage.getWidth();
        canvas_height = stage.getHeight();
        canvas = new Canvas(canvas_width, canvas_height);

        gc = canvas.getGraphicsContext2D();
        this.stage = stage;
        create_images();
        init_canvas();
        drawScoreRectangle();
        draw_menu_buttons();
        create_shop();
        fact_x = Map.get_canvas_width()/Board.get_instance().get_dim_x();
        fact_y = Map.get_canvas_height()/Board.get_instance().get_dim_y();
        this.getChildren().addAll(canvas, upgrade_tower_icon, buy_classic_tower_icon, buy_factory_tower_icon, buy_freezing_tower_icon,destroy_tower_icon,iv_start_wave_button,iv_exit_button,iv_menu_button);
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
                level_background = new Image(new FileInputStream("Assets/level" + j + ".jpg"), stage.getWidth(), stage.getHeight(), false, false);
                for (int i = 0; i < num_diff_asteroid; i++) {
                    planets.add(new Image(new FileInputStream("Assets/" + j + "planet" + (i + 1) + ".png"), size_asteroid, size_asteroid, false, false));
                }
            }
        }

        win = new Image(new FileInputStream("Assets/win.png"));
        gameover = new Image(new FileInputStream("Assets/gameover.jpg"));
        score_img = new Image(new FileInputStream("Assets/score_rectangle_1.png"));
        money_img = new Image(new FileInputStream("Assets/score_rectangle_2.png"));
        wave_img = new Image(new FileInputStream("Assets/score_rectangle_3.png"));
        timer_img = new Image(new FileInputStream("Assets/score_rectangle_4.png"));
        start_wave_button = new Image(new FileInputStream("Assets/start_wave.png"));

        im_small_npc = new Image(new FileInputStream("Assets/small_npc.png"), size_small_npc, size_small_npc, false, false);
        im_med_npc = new Image(new FileInputStream("Assets/medium_npc.png"), size_med_npc, size_med_npc, false, false);
        im_big_npc = new Image(new FileInputStream("Assets/big_npc.png"), size_big_npc, size_big_npc, false, false);

        iv_small_npc = new ImageView(im_small_npc);
        iv_small_npc.setRotate(-90);
        iv_med_npc = new ImageView(im_med_npc);
        iv_med_npc.setRotate(-90);
        iv_big_npc = new ImageView(im_big_npc);
        iv_big_npc.setRotate(-90);

        classic_tower_img = new Image(new FileInputStream("Assets/classic_tower.png"));
        freezing_tower_img = new Image(new FileInputStream("Assets/freezing_tower.png"));
        factory_tower_img = new Image(new FileInputStream("Assets/factory_tower.png"));
        star_1 = new Image(new FileInputStream("Assets/1star.png"));
        star_2 = new Image(new FileInputStream("Assets/2star.png"));
        star_3 = new Image(new FileInputStream("Assets/3star.png"));
        snowflake = new Image(new FileInputStream("Assets/flocon.png"), size_snowflake, size_snowflake, false, false);

        classic_munition_img = new Image(new FileInputStream("Assets/Classic_munition.png"), size_munitions, size_munitions, false, false);
        iv_classic_munition = new ImageView(classic_munition_img);
        freezing_munition_img = new Image(new FileInputStream("Assets/Freezing_munition.png"), size_munitions, size_munitions, false, false);
        iv_freezing_munition = new ImageView(freezing_munition_img);

        menu_button = new Image(new FileInputStream("Assets/back_to_menu.png"));
        exit_button = new Image(new FileInputStream("Assets/exit.png"));
    }

    public void first_update_canvas(){
        if (Game.get_instance().get_paths().isEmpty()) Game.get_instance().construct_path(Board.get_instance().get_dim_x(), level);
        if (Board.get_instance().get_asteroids().isEmpty()) Board.get_instance().create_asteroids_random();

        for (Asteroid asteroid : Board.get_instance().get_asteroids()) {
            type_asteroid.add((int) Math.floor(Math.random() * 5.999999) + 1);
            pos_x_asteroid.add(asteroid.get_pos_x() * canvas.getWidth() / Board.get_instance().get_dim_x());
            pos_y_asteroid.add(asteroid.get_pos_y() * canvas.getHeight() / Board.get_instance().get_dim_y());
        }
        draw_paths();
    }

    public void update_canvas() throws FileNotFoundException {
        init_canvas();
        drawScoreRectangle();
        //draw_paths();
        update_npc_canvas();
        update_munitions_canvas();
        show_message_displayed();
        show_towers();
        if (game_over){
            System.out.println("Update  Lose");
            View.Menu_gameover.start_gameover(stage,"gameover");}
        if (win_game){
            System.out.println("Update  win");
            View.Menu_gameover.start_gameover(stage,"wingame");
        }
    }
    public void update_munitions_canvas() {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        CopyOnWriteArrayList<Munition> copyMunitions = new CopyOnWriteArrayList<>(Board.get_instance().get_munitions());
        for (Munition munition : copyMunitions) {
            double pos_x = munition.get_pos_x(), pos_y = munition.get_pos_y();
            if (munition instanceof Classic_munition){
                iv_classic_munition.setRotate(Math.atan(munition.get_dir_y()/munition.get_dir_x())*180/Math.PI);
                gc.drawImage(iv_classic_munition.snapshot(params, null), pos_x*fact_x, pos_y*fact_y);
            }
            else if (munition instanceof Freezing_munition){
                iv_freezing_munition.setRotate(Math.atan(munition.get_dir_y()/munition.get_dir_x())*180/Math.PI);
                gc.drawImage(iv_freezing_munition.snapshot(params, null), pos_x*fact_x, pos_y*fact_y);
            }
            else{
                System.out.println("ERREUR !!! Essaye d'imprimer une munition n'existant pas dans update_munitions_canvas");
            }
        }
    }

    private void show_towers(){
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
            Image img_npc = null;
            double size_x = 0, size_y = 0;
            if (npc instanceof Small_NPC) {
                iv_small_npc.setRotate(npc.get_direction() - 90);
                img_npc = iv_small_npc.snapshot(params, null);
                size_x = (double) size_small_npc / 2;
                size_y = (double) size_small_npc / 2;
                // Pour que ça soit le centre de l'image qui soit à la position spécifiée et pas le coin supérieur gauche
            } else if (npc instanceof Medium_NPC) {
                iv_med_npc.setRotate(npc.get_direction() - 90);
                img_npc = iv_med_npc.snapshot(params, null);
                size_x = (double) size_med_npc / 2;
                size_y = (double) size_med_npc / 2;
            } else if (npc instanceof Big_NPC) {
                iv_big_npc.setRotate(npc.get_direction() - 90);
                img_npc = iv_big_npc.snapshot(params, null);
                size_x = (double) size_big_npc / 2;
                size_y = (double) size_big_npc / 2;
            }

            gc.drawImage(img_npc, pos_x - size_x, pos_y - size_y);

            if (npc.is_frozen() > 0){
                for (int i = 0; i < npc.get_pos_x_snowflakes().size(); i++){
                    gc.drawImage(snowflake, npc.get_pos_x_snowflakes().get(i) * fact_x, npc.get_pos_y_snowflakes().get(i) * fact_y, size_snowflake, size_snowflake);
                }
                if (Math.random() < 0.8){
                    npc.add_snowflake(npc.get_pos_x() + 5, npc.get_pos_y() + Math.random()*size_y/3 - size_y/3);
                }
            }
        }
    }

    public void draw_paths() {
        for (Path2 path2 : Board.get_instance().get_paths()) {
            this.getChildren().add(path2.get_path_ui());
        }
    }

    private void drawScoreRectangle() {
        for (int i = 8; i <= 168; i = i + 40) {
            gc.setFill(Color.rgb(0, 0, 0, 0.5)); //noir transparent
            gc.fillRoundRect(8, i, 120, 35, 15, 25);
        }

        gc.drawImage(score_img, 15, 17.5, 20, 20);
        gc.drawImage(money_img, 15, 57.5, 25, 20);
        gc.drawImage(wave_img, 13, 97.5, 30, 25);
        gc.drawImage(timer_img, 13, 130, 30, 25);
        gc.drawImage(im_big_npc, 13, 175, 30, 25);

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
        gc.fillText(Integer.toString(npc_destroyed) + " PNJ", 50, 193);
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

    private void create_shop() throws FileNotFoundException {
        this.upgrade_tower_icon = new Upgrade_tower_icon();
        this.buy_classic_tower_icon = new Buy_classic_tower_icon();
        this.buy_factory_tower_icon = new Buy_factory_tower_icon();
        this.buy_freezing_tower_icon = new Buy_freezing_tower_icon();
        this.destroy_tower_icon = new Destroy_tower_icon();
        this.iv_start_wave_button = new ImageView(start_wave_button);

        iv_start_wave_button.setX(canvas_width - 6*(iv_start_wave_button.getFitWidth() + 80));
        iv_start_wave_button.setY(canvas_height - iv_start_wave_button.getFitHeight() - 100);
        iv_start_wave_button.setFitHeight(70);
        iv_start_wave_button.setFitWidth(70);

        upgrade_tower_icon.setOnMouseClicked(new ShopListener(gc, "Upgrade_tower", canvas,upgrade_tower_icon));
       buy_classic_tower_icon.setOnMouseClicked(new ShopListener(gc, "Classic_tower", canvas,upgrade_tower_icon));
        buy_factory_tower_icon.setOnMouseClicked(new ShopListener(gc, "Factory_tower", canvas,upgrade_tower_icon));
        buy_freezing_tower_icon.setOnMouseClicked(new ShopListener(gc, "Freezing_tower", canvas,upgrade_tower_icon));
        destroy_tower_icon.setOnMouseClicked(new ShopListener(gc, "Destroy_tower", canvas,upgrade_tower_icon));
        iv_start_wave_button.setOnMouseClicked(new Start_wave_listener());




        String string = new String (" Range: " +" 70\n"+
                " Period: " +" 2000\n"+
                " Power: " +" 4\n"+
                " Price: " +" 100 coins\n"+
                " Upgrade: " +" 50 coins and kill 5 NPC \n"

        );
        Tooltip tooltip=new Tooltip(string);
        Tooltip.install(buy_classic_tower_icon, tooltip);


        String string1 = new String (" Money Produced: " +" 20\n"+
                " Period: " +" 20\n"+
                " Price: " +" 200 coins \n"+
                " Upgrade: " +" 200 coins and kill 10 PNJ \n"

        );
        Tooltip tooltip1 = new Tooltip(string1);
        Tooltip.install(buy_factory_tower_icon, tooltip1);







        String string2 = new String (" Range: " +" 50\n"+
                " Period: " +" 5000\n"+
                " Power: " +" 3\n"+
                " Price: " +" 300 coins\n"+
                " Upgrade: " +" 200 coins and kill 10 PNJ \n"

        );
        Tooltip tooltip2=new Tooltip(string2);
        Tooltip.install(buy_freezing_tower_icon, tooltip2);



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

    public void set_const_message(String message) {
        curr_message = message;
    }

    public void set_temp_message(String message) {
        Thread thread_message = new Thread(this);
        Game.get_instance().add_thread(thread_message);
        curr_message = message;
        thread_message.start();
    }

    public void run() {
        String message = curr_message;
        double t = 0;
        while (t < 3000) {
            try {
                synchronized (key) {
                    Platform.runLater(() -> show_message_displayed());
                    Thread.sleep((long) (1000.0 / Game.get_instance().get_fps()));
                    t += 1000.0 / Game.get_instance().get_fps();
                }
            } catch (InterruptedException e) {
                return;
            }
        }
        if (curr_message == message) curr_message = ""; //Si le message n'a pas changé entretemps, on l'efface
    }

    public void game_over() {
        game_over = true ;
        game_over_snd = TinySound.loadSound("Songs/game_over.wav");
        game_over_snd.play(5);
        System.out.println("Game Over");
        //gc.drawImage(gameover,400,300);

        try {
            update_canvas();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("You Lose");
        //View.Menu_gameover.start_gameover(stage);

    }

    public void game_won(){
        win_game = true;
        won_snd = TinySound.loadSound("Songs/won.wav");
        won_snd.play();
        System.out.println("you win");

        try {
            update_canvas();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("you win 2");
    }

    public void end_game(int score){
        if (score > 0) game_won();
        else game_over();

    }

    public double get_fact_x(){return fact_x;}
    public double get_fact_y(){return fact_y;}
    public int get_level(){return level;}

    public static void set_instance(Map instance) {
        Map.instance = instance;
    }

    public ArrayList<ArrayList<Double>> get_forbidden(){
        ArrayList<ArrayList<Double>> forbidden = new ArrayList<>(); // zones where asteroids can't be placed
        System.out.println(canvas.getWidth());
        forbidden.add(new ArrayList<>(Arrays.asList((canvas.getWidth()-650)/fact_x, canvas.getWidth()/fact_x, (canvas.getHeight()-160)/fact_y, canvas.getHeight()/fact_y)));
        forbidden.add(new ArrayList<>(Arrays.asList(0.0, 60.0/fact_x, 0.0, 220.0/fact_y)));
        forbidden.add(new ArrayList<>(Arrays.asList((canvas_width - 2 * menu_button.getWidth() - 30)/fact_x, canvas_width/fact_x, 0.0, 30.0/fact_y)));
        return forbidden;
    }
}