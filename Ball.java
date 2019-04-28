package sample;

import javafx.animation.AnimationTimer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.scene.image.ImageView;



public class Ball extends ImageView{
    private int ballSpeed = 25;
    private AnimationTimer mouvment;
    private double degat;
    public BooleanProperty blocked;

    public Ball(int x,int y,double Degat){
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

    private void moveToTop(){
        if (this.getY() > -20){
            this.setY(this.getY() - ballSpeed);
        }else{
            stop();
        }
    }
    public void start(){
        if (this != null){
            this.mouvment.start();
            Data.getData().ballShootSFX();
        }
    }
    public void stop(){
        this.mouvment.stop();
        Main.rootControler.partieControler.gameScene.getChildren().remove(this);
        this.blocked.set(true);
    }

    public double getDegat() {
        return degat;
    }
}
