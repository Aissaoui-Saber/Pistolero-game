package sample;

import javafx.animation.AnimationTimer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Pistol {
    private Image images[] = {new Image(this.getClass().getResource("Images/Pistol_Vertical.png").toExternalForm()),
            new Image(this.getClass().getResource("Images/Pistol_Horisontal.png").toExternalForm()),
            new Image(this.getClass().getResource("Images/Pistol_Diagonal_Right.png").toExternalForm()),
            new Image(this.getClass().getResource("Images/Pistol_Diagonal_Left.png").toExternalForm())};

    private ImageView pistol;

    private int speed;

    AnimationTimer upTimer;
    AnimationTimer rightTimer;
    AnimationTimer downTimer;
    AnimationTimer leftTimer;

    private Key up = GameConfig.getInstance().getUpKey();
    private Key down = GameConfig.getInstance().getDownKey();
    private Key left = GameConfig.getInstance().getLeftKey();
    private Key right = GameConfig.getInstance().getRightKey();

    private double vie;

    public Pistol(){
        //INITIALISATION DE PISTOLET
        pistol = new ImageView();
        pistol.setFocusTraversable(true);
        pistol.setImage(images[0]);
        pistol.setFitWidth(80);
        pistol.setFitHeight(90);
        pistol.setX(600);
        pistol.setY(600);
        speed = 6;
        pistol.requestFocus();
        //LES ANIMATIONS DES DEPLACEMENTS
        upTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveUp();
            }
        };
        rightTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveRight();
            }
        };
        downTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveDown();
            }
        };
        leftTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveLeft();
            }
        };

        up.getPressedProprety().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    upTimer.start();
                }else {
                    upTimer.stop();
                }
            }
        });
        down.getPressedProprety().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    downTimer.start();
                }else {
                    downTimer.stop();
                }
            }
        });
        left.getPressedProprety().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    leftTimer.start();
                }else {
                    leftTimer.stop();
                }
            }
        });
        right.getPressedProprety().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    rightTimer.start();
                }else {
                    rightTimer.stop();
                }
            }
        });

        pistol.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == up.getCode()){
                    up.setPressed();
                    if (left.isPressed()){
                        pistol.setImage(images[3]);
                    }else if (right.isPressed()){
                        pistol.setImage(images[2]);
                    }else {
                        pistol.setImage(images[0]);
                    }
                }
                if (event.getCode() == down.getCode()){
                    down.setPressed();
                    if (left.isPressed()){
                        pistol.setImage(images[2]);
                    }else if(right.isPressed()){
                        pistol.setImage(images[3]);
                    }else {
                        pistol.setImage(images[0]);
                    }
                }
                if (event.getCode() == left.getCode()){
                    left.setPressed();
                    if (up.isPressed()){
                        pistol.setImage(images[3]);
                    }else if (down.isPressed()){
                        pistol.setImage(images[2]);
                    }else {
                        pistol.setImage(images[1]);
                    }
                }
                if (event.getCode() == right.getCode()){
                    right.setPressed();
                    if (up.isPressed()){
                        pistol.setImage(images[2]);
                    }else if (down.isPressed()){
                        pistol.setImage(images[3]);
                    }else {
                        pistol.setImage(images[1]);
                    }
                }
                if (event.getCode() == KeyCode.SPACE){
                    fire();
                }
            }
        });
        pistol.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == up.getCode()){
                    up.setReleased();
                    if (left.isPressed()){
                        pistol.setImage(images[3]);
                    }else if (right.isPressed()){
                        pistol.setImage(images[2]);
                    }else {
                        pistol.setImage(images[0]);
                    }
                }
                if (event.getCode() == down.getCode()){
                    down.setReleased();
                    if (left.isPressed()){
                        pistol.setImage(images[2]);
                    }else if(right.isPressed()){
                        pistol.setImage(images[3]);
                    }else {
                        pistol.setImage(images[0]);
                    }
                }
                if (event.getCode() == left.getCode()){
                    left.setReleased();
                    if (up.isPressed()){
                        pistol.setImage(images[3]);
                    }else if (down.isPressed()){
                        pistol.setImage(images[2]);
                    }else {
                        pistol.setImage(images[1]);
                    }
                }
                if (event.getCode() == right.getCode()){
                    right.setReleased();
                    if (up.isPressed()){
                        pistol.setImage(images[2]);
                    }else if (down.isPressed()){
                        pistol.setImage(images[3]);
                    }else {
                        pistol.setImage(images[1]);
                    }
                }
                if (event.getCode() == KeyCode.ESCAPE){

                }
                pistol.setImage(images[0]);
            }
        });
    }

    public ImageView getImage() {
        return pistol;
    }
    //DEPLACEMENT
    private void moveUp(){
        if (this.pistol.getY()-speed > 0)
            this.pistol.setY(this.pistol.getY()-speed);
    }
    private void moveRight(){
        if(this.pistol.getX()+speed < 1210)
            this.pistol.setX(this.pistol.getX()+speed);
    }
    private void moveDown(){
        if (this.pistol.getY()+speed < 640)
            this.pistol.setY(this.pistol.getY()+speed);
    }
    private void moveLeft(){
        if (this.pistol.getX()-speed > 0)
            this.pistol.setX(this.pistol.getX()-speed);
    }

    public void fire(){

    }
}
