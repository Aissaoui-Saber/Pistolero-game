package sample;

import javafx.animation.AnimationTimer;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

import java.awt.*;


public class Ball extends ImageView{
    //private ImageView ball;
    private int ballSpeed = 20;
    private AnimationTimer mouvment;
    private double degat;
    public BooleanProperty blocked;

    public Ball(int x,int y,double Degat){
        //ball = new ImageView(Data.getData().bulletIMG());
        this.setImage(Data.getData().bulletIMG());
        this.setX(x);
        this.setY(y);
        this.setFitWidth(10);
        this.setFitHeight(20);
        blocked = new SimpleBooleanProperty(false);
        mouvment = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveToTop();
            }
        };
        degat = Degat;
    }

    /*public ImageView getBallImageView() {
        return ball;
    }*/

    private void moveToTop(){
        if (this.getY() > -20){
            this.setY(this.getY() - ballSpeed);
        }else{
            stop();
        }
    }
    public void start(){
        this.mouvment.start();
    }
    public void stop(){
        this.mouvment.stop();
        Main.rootControler.partieControler.gameScene.getChildren().remove(this);
        this.blocked.set(true);
        //this.setY(1000);
    }
    public void hide(){
        //this.getBallImageView().setVisible(false);
    }

    public double getDegat() {
        return degat;
    }

    public void setDegat(double degat) {
        this.degat = degat;
    }
}
