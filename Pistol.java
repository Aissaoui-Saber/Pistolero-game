package sample;

import javafx.animation.AnimationTimer;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;


public class Pistol extends Pane {
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

    private Key up = new Key(KeyCode.UP);
    private Key down = new Key(KeyCode.DOWN);
    private Key left = new Key(KeyCode.LEFT);
    private Key right = new Key(KeyCode.RIGHT);

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
        speed = 8;

        //System.out.println("["+(int)pistol.getX()+","+(int)pistol.getY()+"]");

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

        pistol.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == up.getCode()){
                    up.setPressed(true);
                    upTimer.start();
                    if (left.isPressed()){
                        pistol.setImage(images[3]);
                        leftTimer.start();
                    }else if (right.isPressed()){
                        pistol.setImage(images[2]);
                        rightTimer.start();
                    }else {
                        pistol.setImage(images[0]);
                    }
                }
                if (event.getCode() == down.getCode()){
                    down.setPressed(true);
                    downTimer.start();
                    if (left.isPressed()){
                        pistol.setImage(images[2]);
                        leftTimer.start();
                    }else if(right.isPressed()){
                        pistol.setImage(images[3]);
                        rightTimer.start();
                    }else {
                        pistol.setImage(images[0]);
                    }
                }
                if (event.getCode() == left.getCode()){
                    left.setPressed(true);
                    leftTimer.start();
                    if (up.isPressed()){
                        pistol.setImage(images[3]);
                        upTimer.start();
                    }else if (down.isPressed()){
                        pistol.setImage(images[2]);
                        downTimer.start();
                    }else {
                        pistol.setImage(images[1]);
                    }
                }
                if (event.getCode() == right.getCode()){
                    right.setPressed(true);
                    rightTimer.start();
                    if (up.isPressed()){
                        pistol.setImage(images[2]);
                        upTimer.start();
                    }else if (down.isPressed()){
                        pistol.setImage(images[3]);
                        downTimer.start();
                    }else {
                        pistol.setImage(images[1]);
                    }
                }
                //System.out.println("["+(int)pistol.getX()+","+(int)pistol.getY()+"]");
                event.consume();
            }
        });
        pistol.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == up.getCode()){
                    up.setPressed(false);
                    upTimer.stop();
                }
                if (event.getCode() == down.getCode()){
                    down.setPressed(false);
                    downTimer.stop();
                }
                if (event.getCode() == left.getCode()){
                    left.setPressed(false);
                    leftTimer.stop();
                }
                if (event.getCode() == right.getCode()){
                    right.setPressed(false);
                    rightTimer.stop();
                }
                pistol.setImage(images[0]);
                event.consume();
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
}
