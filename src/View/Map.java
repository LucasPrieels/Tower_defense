package View;

import Model.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Map extends Parent {
    private int level = 1;
    private int score = 1;
    private int money = 2;
    private Image background;
    private int size_asteroid = 50, size_small_npc = 35, size_med_npc = 50, size_big_npc = 70;
    private Canvas canvas;
    private GraphicsContext gc;
    private Stage stage;
    private static Map instance = null;
    private Image im_small_npc, im_med_npc, im_big_npc, level1, planet1, planet2, planet3, planet4, planet5, planet6, score_img, money_img;
    private ImageView iv_small_npc, iv_med_npc, iv_big_npc;

    private Map(Stage stage) throws FileNotFoundException {
        canvas = new Canvas(stage.getWidth(),stage.getHeight());
        gc = canvas.getGraphicsContext2D();
        this.stage = stage;
        create_images();
        if (level == 1){
            init_canvas_level1();
        }
        drawScoreRectangle();
        Update_tower_icon update_tower_icon = new Update_tower_icon();


        //gridpane.getChildren().addAll(canvas, update_tower);
        this.getChildren().addAll(canvas, update_tower_icon);


        //update_tower_icon.setOnMouseClicked(new EventHandler<MouseEvent>() {
        //    @Override
        //    public void handle(MouseEvent mouseEvent) {
        //            System.out.println("coucou");
        //    }
        //});

        Game game = Game.get_instance();
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

    private void init_canvas_level1(){
        gc.drawImage(level1,0,0);
        gc.drawImage(planet1,100,100);
        gc.drawImage(planet2,700,200);
        gc.drawImage(planet3,1100,450);
        gc.drawImage(planet5,500,400);
        gc.drawImage(planet6,1100,0);
    }

    public void create_images() throws FileNotFoundException {
        level1 = new Image(new FileInputStream("Images/level1.jpg"), stage.getWidth(), stage.getHeight(), false, false);
        planet1 = new Image(new FileInputStream("Images/asteroid1.png"), size_asteroid,  size_asteroid,  false, false);
        planet2 = new Image(new FileInputStream("Images/asteroid2.png"), size_asteroid,  size_asteroid,  false, false);
        planet3 = new Image(new FileInputStream("Images/asteroid3.png"), size_asteroid,  size_asteroid,  false, false);
        planet4 = new Image(new FileInputStream("Images/asteroid4.png"), size_asteroid,  size_asteroid,  false, false);
        planet5 = new Image(new FileInputStream("Images/asteroid5.png"), size_asteroid,  size_asteroid,  false, false);
        planet6 = new Image(new FileInputStream("Images/asteroid6.png"), size_asteroid,  size_asteroid,  false, false);

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

    public void update_npc_canvas(){
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (level==1){
            init_canvas_level1();
        }
        drawScoreRectangle();
        for (NPC npc: Board.get_npcs()){
            int pos_x = (int)Math.round(npc.get_pos_x()*canvas.getWidth()/Board.get_dim_x());
            int pos_y = (int)Math.round(npc.get_pos_y()*canvas.getHeight()/Board.get_dim_y());
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
                gc.drawImage(iv_big_npc.snapshot(params, null), pos_x, pos_y);
            }
        }
        System.out.println("Updated");
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

}