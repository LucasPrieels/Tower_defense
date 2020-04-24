package View;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Map extends Parent {
    private int level = 1;
    private int score = 1;
    private int money = 2;
    private Image background;

    public Map() throws FileNotFoundException {
        if(level==1){
            map_level1();}
    }

    private void map_level1() throws FileNotFoundException {
        //GridPane gridpane = new GridPane();
        Canvas canvas = new Canvas(1920,1080);
        GraphicsContext gc1 = canvas.getGraphicsContext2D();



        if(level==1){
            drawAsteroidsLevel1(gc1);
        }

        drawScoreRectangle(gc1);
        Update_tower_icon update_tower_icon = new Update_tower_icon();


        //gridpane.getChildren().addAll(canvas, update_tower);
        this.getChildren().addAll(canvas, update_tower_icon);


        //update_tower_icon.setOnMouseClicked(new EventHandler<MouseEvent>() {
        //    @Override
        //    public void handle(MouseEvent mouseEvent) {
        //            System.out.println("coucou");
        //    }
        //});

    }

    private void drawAsteroidsLevel1(GraphicsContext gc) throws FileNotFoundException {
        this.background = new Image(new FileInputStream("Images/level1.jpg"));
        gc.drawImage(background,0,0);

        Image planet1 = new Image(new FileInputStream("Images/asteroid1.png"));
        gc.drawImage(planet1,100,100);

        Image planet2 = new Image(new FileInputStream("Images/asteroid2.png"));
        gc.drawImage(planet2,700,200);

        Image planet3 = new Image(new FileInputStream("Images/asteroid3.png"));
        gc.drawImage(planet3,1100,450);

        Image planet5 = new Image(new FileInputStream("Images/asteroid5.png"));
        gc.drawImage(planet5,500,400);

        Image planet6 = new Image(new FileInputStream("Images/asteroid6.png"));
        gc.drawImage(planet6,1100,0);
    }

    private void drawScoreRectangle(GraphicsContext gc) throws FileNotFoundException {
        gc.setFill(Color.rgb(0,0,0,0.5)); //noir transparent
        gc.fillRoundRect(8,8,250,45,15,25);

        Image score_img = new Image(new FileInputStream("Images/score_img.png"));
        gc.drawImage(score_img,15,15);

        Image money_img = new Image(new FileInputStream("Images/money_img.png"));
        gc.drawImage(money_img,130,15);

        gc.setFont(new Font("Arial", 20)); //trouver plus joli si temps
        gc.setFill(Color.WHITE);
        gc.fillText(Integer.toString(score), 50,35);  //Integer.toString

        gc.setFont(new Font("Arial", 20)); //ecrire fct qui donne le score
        gc.setFill(Color.WHITE);
        gc.fillText(Integer.toString(money), 180,35);
    }

}