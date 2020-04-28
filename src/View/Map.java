package View;

import Model.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Map extends Parent {
    private int level = 1, score = 1, money = 2;
    private static double canvas_height, canvas_width;
    private Image background;
    private static int size_asteroid = 50, size_small_npc = 35, size_med_npc = 50, size_big_npc = 70, num_diff_asteroid = 8;
    private static Canvas canvas;
    private GraphicsContext gc;
    private Stage stage;
    private static Map instance = null;
    private Image im_small_npc, im_med_npc, im_big_npc, level1, score_img, money_img;
    private ArrayList<Image> planets = new ArrayList<>();
    private ImageView iv_small_npc, iv_med_npc, iv_big_npc;
    private Update_tower_icon update_tower_icon;
    private Buy_freezing_tower_icon buy_freezing_tower_icon;
    private Buy_factory_tower_icon buy_factory_tower_icon;
    private Buy_classic_tower_icon buy_classic_tower_icon;
    private ArrayList<Integer> num_asteroid = new ArrayList<>();
    private ArrayList<Double>  pos_x_asteroid = new ArrayList<>(), pos_y_asteroid = new ArrayList<>();

    private Map(Stage stage) throws FileNotFoundException {
        canvas_width = stage.getWidth();
        canvas_height = stage.getHeight();
        canvas = new Canvas(canvas_width, canvas_height);
        gc = canvas.getGraphicsContext2D();
        this.stage = stage;
        create_images();
        //draw_paths();
        init_canvas();
        drawScoreRectangle();
        create_shop();
        for (int x = 0; x < Board.get_asteroids().size(); x++){
            for (Asteroid asteroid: Board.get_asteroids().get(x)){
                num_asteroid.add((int)Math.floor(Math.random()*5.999999) + 1);
                pos_x_asteroid.add(asteroid.get_pos_x()*canvas.getWidth()/Board.get_dim_x());
                pos_y_asteroid.add(asteroid.get_pos_y()*canvas.getHeight()/Board.get_dim_y());
            }
        }

        this.getChildren().addAll(canvas, update_tower_icon,buy_classic_tower_icon,buy_factory_tower_icon,buy_freezing_tower_icon);
    }

    //Singleton
    public static Map get_instance(){
        if (instance==null){throw new AssertionError("Map has to be initialized before getting accessed");}
        return instance;
    }

    public static void init(Stage stage) throws FileNotFoundException{
        if (instance != null){
            throw new AssertionError("Map can't be initialized twice");
        }
        instance = new Map(stage);
    }

    private void init_canvas(){
        gc.drawImage(level1,0,0);
        for (int i=0; i<num_asteroid.size(); i++){
            gc.drawImage(planets.get(num_asteroid.get(i)), pos_x_asteroid.get(i), pos_y_asteroid.get(i));
        }
    }

    public void create_images() throws FileNotFoundException {
        level1 = new Image(new FileInputStream("Images/level1.jpg"), stage.getWidth(), stage.getHeight(), false, false);
        for (int i=0; i<num_diff_asteroid; i++){

            planets.add(new Image(new FileInputStream("Images/asteroid" + (i+1) + ".png"), size_asteroid,  size_asteroid,  false, false));
        }

        score_img = new Image(new FileInputStream("Images/score_img.png"));
        money_img = new Image(new FileInputStream("Images/money_img.png"));

        im_small_npc = new Image(new FileInputStream("Images/small_npc.png"), size_small_npc,  size_small_npc,  false, false);
        im_med_npc = new Image(new FileInputStream("Images/medium_npc.png"), size_med_npc,  size_med_npc,  false, false);
        im_big_npc = new Image(new FileInputStream("Images/big_npc.png"), size_big_npc,  size_big_npc,  false, false);

        iv_small_npc = new ImageView(im_small_npc);
        iv_small_npc.setRotate(-90);
        iv_med_npc = new ImageView(im_med_npc);
        iv_med_npc.setRotate(-90);
        iv_big_npc = new ImageView(im_big_npc);
        iv_big_npc.setRotate(-90);
    }

    public void update_canvas(){
        init_canvas();
        drawScoreRectangle();
        draw_paths();
        update_npc_canvas();
        System.out.println("Updated");
    }

    public void update_npc_canvas(){
        CopyOnWriteArrayList<NPC> npcs = new CopyOnWriteArrayList<>(Board.get_npcs());
        // Car on peut faire des suppressions d'éléments
        for (NPC npc: npcs){
            double pos_x = npc.get_pos_x()*canvas.getWidth()/Board.get_dim_x();
            double pos_y = npc.get_pos_y()*canvas.getHeight()/Board.get_dim_y();
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);

            //ATTENTION A CHANGER, ça ne permet pas d'ajouter facilement un nv type de PNJ
            //On peut garder les instanceof? Ou il faut faire un liste avec les petits une avec les grands etc dans Board?
            if (npc instanceof Small_NPC){
                gc.drawImage(iv_small_npc.snapshot(params, null), pos_x, pos_y);
            }
            else if (npc instanceof Medium_NPC){
                gc.drawImage(iv_med_npc.snapshot(params, null), pos_x, pos_y);
            }
            else if (npc instanceof Big_NPC){
                System.out.println(pos_x + " " + pos_y);
                gc.drawImage(iv_big_npc.snapshot(params, null), pos_x, pos_y);
            }
        }
        System.out.println();
    }

    public void draw_paths(){
        for (Path2 path2: Board.get_paths()){
            this.getChildren().add(path2.get_path_ui());
        }
    }

    private void drawScoreRectangle(){
        gc.setFill(Color.rgb(0,0,0,0.5)); //noir transparent
        gc.fillRoundRect(8,8,250,45,15,25);
        gc.drawImage(score_img,15,15);
        gc.drawImage(money_img,130,15);

        gc.setFont(new Font("Arial", 20)); //trouver plus joli si temps
        gc.setFill(Color.WHITE);
        gc.fillText(Integer.toString(score), 50,35);  //Integer.toString

        gc.setFont(new Font("Arial", 20)); //ecrire fct qui donne le score
        gc.setFill(Color.WHITE);
        gc.fillText(Integer.toString(money), 180,35);
    }

    private void create_shop() throws FileNotFoundException {
        this.update_tower_icon = new Update_tower_icon();
        this.buy_classic_tower_icon = new Buy_classic_tower_icon();
        this.buy_factory_tower_icon = new Buy_factory_tower_icon();
        this.buy_freezing_tower_icon = new Buy_freezing_tower_icon();

        update_tower_icon.setOnMouseClicked(new ShopListener(gc,"tower"));
        buy_classic_tower_icon.setOnMouseClicked(new ShopListener(gc,"asteroid"));
        buy_factory_tower_icon.setOnMouseClicked(new ShopListener(gc,"asteroid"));
        buy_freezing_tower_icon.setOnMouseClicked(new ShopListener(gc,"asteroid"));
        canvas.setOnMouseClicked(new TowerListener(canvas, pos_x_asteroid,pos_y_asteroid,num_asteroid.size()));

    }

    public static double get_canvas_height(){return canvas.getHeight();}
    public static double get_canvas_width(){return canvas.getWidth();}
    public static double get_size_asteroid(){return size_asteroid;}
}