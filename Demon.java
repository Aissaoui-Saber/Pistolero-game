package sample;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Demon{
    private double vie;
    private boolean sexe;
    private double degat;
    private ImageView image;
    private BooleanProperty deadProperty;
    private int demonSpeed;
    private AnimationTimer mouvment;
    public Demon(int x,int y){
        image = new ImageView();
        image.setImage(Data.getData().demon1IMG());
        image.setFitWidth(150);
        image.setFitHeight(106);
        image.setX(x);
        image.setY(y);
        vie = 100;
        sexe = true;
        degat = 20;
        deadProperty = new SimpleBooleanProperty(true);
        demonSpeed=1;
        mouvment = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveDown();
            }
        };
        //mouvment.start();
        deadProperty.set(false);
    }


    public ImageView getImage() {
        return image;
    }

    public void deplacer(int direction){

    }
    public BooleanProperty DeadProperty(){
        return this.deadProperty;
    }
    public void blesser(int value){
        this.vie-=value;
        if (this.vie == 0){
            this.deadProperty.set(true);
            mouvment.stop();
            image.setVisible(false);
        }
    }

    private void moveDown(){
        if (image.getY() < 720){
            image.setY(image.getY() + demonSpeed);
        }else{
            mouvment.stop();
        }
    }
    public boolean intersects(Node n){
        return this.image.getBoundsInParent().intersects(n.getBoundsInParent());
    }
    public void stop(){
        this.mouvment.stop();
    }
    public void start(){
        this.mouvment.start();
    }
}
