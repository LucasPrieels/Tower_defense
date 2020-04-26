package Model;


import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;



public class MapPicker extends VBox {
    private ImageView circleImage;
    private ImageView mapImage;

    private static final String CIRCLE_CHOOSEN = "View/red_boxTick.png";
    private static final String CIRCLE_NOT_CHOOSEN = "View/grey_boxTick.png";

    private Map map;

    private boolean isCirclechoosen;

    public MapPicker(Map map){
        this.map = map;
        circleImage = new ImageView(CIRCLE_NOT_CHOOSEN);
        mapImage = new ImageView(map.getUrl());
        isCirclechoosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().add(circleImage);
        this.getChildren().add(mapImage);
    }

    public Map getMap() {
        return map;
    }

    public boolean isCirclechoosen() {
        return isCirclechoosen;
    }

    public void setIsChoosen(boolean isChosen){
        this.isCirclechoosen = isChosen;
        String imageToSet = this.isCirclechoosen ? CIRCLE_CHOOSEN : CIRCLE_NOT_CHOOSEN;
        circleImage.setImage(new Image(imageToSet));
    }


}

