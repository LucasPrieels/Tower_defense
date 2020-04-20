package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


    public class Tower_menu extends VBox {

        public Tower_menu(String shop, Classic_tower tower1, Freezing_tower tower2, Factory_tower tower3, String color){
            Label t = new Label(shop);
            HBox menu = new HBox(2);
            menu.setPadding(new Insets(1, 2, 3, 2));
            menu.getChildren().addAll(tower1,tower2,tower3);


            getChildren().addAll(t, menu);
        }


    }


