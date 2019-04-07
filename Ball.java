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


public class Ball {
    private ImageView ball;
    private int ballSpeed = 20;
    private AnimationTimer mouvment;
    private double degat;
    private boolean blocked;

    public Ball(int x,int y,double Degat){
        ball = new ImageView(Data.getData().bulletIMG());
        ball.setX(x);
        ball.setY(y);
        ball.setFitWidth(10);
        ball.setFitHeight(20);
        blocked = false;
        mouvment = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveToTop();
            }
        };
        degat = Degat;
    }

    public ImageView getBallImageView() {
        return ball;
    }

    private void moveToTop(){
        if (ball.getY() > -30){
            ball.setY(ball.getY() - ballSpeed);
        }else{
            mouvment.stop();
        }
    }
    public void start(){
        this.mouvment.start();
    }
    public void stop(){
        this.mouvment.stop();
        ball.setY(1000);
    }
    public void hide(){
        this.getBallImageView().setVisible(false);
    }

    public double getDegat() {
        return degat;
    }

    public void setDegat(double degat) {
        this.degat = degat;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
