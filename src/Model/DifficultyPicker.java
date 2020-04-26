package Model;


import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class DifficultyPicker extends VBox {
    private ImageView circleImage;
    private TextLabel numberLabel;

    private static final String CIRCLE_CHOOSEN = "View/red_boxTick.png";
    private static final String CIRCLE_NOT_CHOOSEN = "View/grey_boxTick.png";

    private int number;

    private boolean isCirclechoosen;

    public DifficultyPicker(int number){
        this.number = number;
        circleImage = new ImageView(CIRCLE_NOT_CHOOSEN);
        numberLabel = new TextLabel(Integer.toString(number),25);
        isCirclechoosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().add(circleImage);
        this.getChildren().add(numberLabel);
    }

    public int getNumber() {
        return number;
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

