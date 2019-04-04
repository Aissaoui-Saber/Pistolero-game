package sample;

import javafx.animation.AnimationTimer;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javax.naming.Binding;


public class Pistol {
    private Image images[] = {Data.getData().pistolVerticalIMG(),Data.getData().pistolHorisontalIMG(),
            Data.getData().pistolDiagonalRightIMG(),Data.getData().pistolDiagonalLeftIMG()};

    private ImageView pistol;
    private Group g;
    private int speed;
    public IntegerProperty nbrBallsProperty;

    AnimationTimer upTimer;
    AnimationTimer rightTimer;
    AnimationTimer downTimer;
    AnimationTimer leftTimer;

    public IntegerProperty ballOutXProperty;
    public IntegerProperty ballOutYProperty;

    private Key up = GameConfig.getInstance().getUpKey();
    private Key down = GameConfig.getInstance().getDownKey();
    private Key left = GameConfig.getInstance().getLeftKey();
    private Key right = GameConfig.getInstance().getRightKey();
    private Key fire = GameConfig.getInstance().getFireKey();
    private Key pause = GameConfig.getInstance().getPauseKey();

    private double vie;

    public Pistol(int nBalls){
        //INITIALISATION DE PISTOLET
        pistol = new ImageView();
        pistol.setFocusTraversable(true);
        pistol.setImage(images[0]);
        pistol.setFitWidth(80);
        pistol.setFitHeight(90);
        pistol.setX(600);
        pistol.setY(600);
        speed = 10;
        nbrBallsProperty = new SimpleIntegerProperty(nBalls);
        pistol.requestFocus();
        g = new Group();
        g.getChildren().add(pistol);

        ballOutXProperty = new SimpleIntegerProperty();
        ballOutYProperty = new SimpleIntegerProperty();

        ballOutXProperty.bind(Bindings.add(pistol.xProperty(),35));
        ballOutYProperty.bind(pistol.yProperty());

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
                if (event.getCode() == fire.getCode()){
                    fire.setPressed();
                }
                if (event.getCode() == pause.getCode()){
                    GameConfig.getInstance().getPauseKey().setPressed();
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
                if (event.getCode() == fire.getCode()){
                    fire.setReleased();
                }
                if (event.getCode() == pause.getCode()){
                    pause.setReleased();
                }
                pistol.setImage(images[0]);
            }
        });
    }

    public Group getPistol(){
        return g;
    }
    public ImageView getPistolImage(){
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
        /*
        if (nbrBalls.get() == -1){
            g.getChildren().add((new Ball(ballOutX.get(),ballOutY.get())).getBallImageView());
        }else if (nbrBalls.get() > 0){
            g.getChildren().add((new Ball(ballOutX.get(),ballOutY.get())).getBallImageView());
            nbrBalls.set(nbrBalls.get()-1);
        }*/
    }
    public void blesser(int value){
        this.vie -=value;
        if (vie <= 0){
            //g.setVisible(false);
            System.out.println("Blessée");
        }
    }
    public boolean intersects(Node n){
        return this.pistol.getBoundsInParent().intersects(n.getBoundsInParent());
    }
}
