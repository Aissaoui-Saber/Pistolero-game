package sample;

import javafx.animation.AnimationTimer;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.util.Duration;

import javax.naming.Binding;


public class Pistol extends ImageView{
    private Image images[] = {Data.getData().pistolVerticalIMG(),Data.getData().pistolHorisontalIMG(),
            Data.getData().pistolDiagonalRightIMG(),Data.getData().pistolDiagonalLeftIMG()};

    private ImageView pistol;

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

    private Timeline explosionAnimation;

    public BooleanProperty deadProperty;
    public BooleanProperty isExplosingProperty;

    public Pistol(){
        //INITIALISATION DE PISTOLET------------------------------------------------------------------------------
        this.setFocusTraversable(true);
        this.setImage(images[0]);
        this.setFitWidth(80);
        this.setFitHeight(90);
        this.setX(600);
        this.setY(600);
        this.requestFocus();
        pistol = this;
        deadProperty = new SimpleBooleanProperty(false);
        isExplosingProperty = new SimpleBooleanProperty(false);
        //--------------------------------------------------------------------------------------------------------




        //EXPLOSION------------------------------------------------------------------------------------------------
        isExplosingProperty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue.booleanValue())
                    pistol.setImage(Data.getData().explosionIMG());
            }
        });
        KeyFrame debutExplosion = new KeyFrame(Duration.ZERO,null);
        KeyFrame finExplosion = new KeyFrame(Duration.millis(1000),new KeyValue(deadProperty,true));
        explosionAnimation = new Timeline();
        explosionAnimation.getKeyFrames().addAll(debutExplosion,finExplosion);
        //--------------------------------------------------------------------------------------------------------




        //BINDING POSITION DE PISTOLET AVEC POSITION DE TIR DES BALLS---------------------------------------------
        ballOutXProperty = new SimpleIntegerProperty();
        ballOutYProperty = new SimpleIntegerProperty();

        ballOutXProperty.bind(Bindings.add(this.xProperty(),35));
        ballOutYProperty.bind(this.yProperty());
        //-------------------------------------------------------------------------------------------------------





        //LES ANIMATIONS DES DEPLACEMENTS----------------------------------------------------------------------------
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
        //---------------------------------------------------------------------------------------------------------






        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!isExplosingProperty.get()) {
                    if (event.getCode() == up.getCode()) {
                        up.setPressed();
                        if (left.isPressed()) {
                            pistol.setImage(images[3]);
                        } else if (right.isPressed()) {
                            pistol.setImage(images[2]);
                        } else {
                            pistol.setImage(images[0]);
                        }
                    }
                    if (event.getCode() == down.getCode()) {
                        down.setPressed();
                        if (left.isPressed()) {
                            pistol.setImage(images[2]);
                        } else if (right.isPressed()) {
                            pistol.setImage(images[3]);
                        } else {
                            pistol.setImage(images[0]);
                        }
                    }
                    if (event.getCode() == left.getCode()) {
                        left.setPressed();
                        if (up.isPressed()) {
                            pistol.setImage(images[3]);
                        } else if (down.isPressed()) {
                            pistol.setImage(images[2]);
                        } else {
                            pistol.setImage(images[1]);
                        }
                    }
                    if (event.getCode() == right.getCode()) {
                        right.setPressed();
                        if (up.isPressed()) {
                            pistol.setImage(images[2]);
                        } else if (down.isPressed()) {
                            pistol.setImage(images[3]);
                        } else {
                            pistol.setImage(images[1]);
                        }
                    }
                    if (event.getCode() == fire.getCode()) {
                        fire.setPressed();
                    }
                }
                if (event.getCode() == pause.getCode()){
                    GameConfig.getInstance().getPauseKey().setPressed();
                }
            }
        });
        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!isExplosingProperty.get()) {
                    if (event.getCode() == up.getCode()) {
                        up.setReleased();
                        if (left.isPressed()) {
                            pistol.setImage(images[3]);
                        } else if (right.isPressed()) {
                            pistol.setImage(images[2]);
                        } else {
                            pistol.setImage(images[0]);
                        }
                    }
                    if (event.getCode() == down.getCode()) {
                        down.setReleased();
                        if (left.isPressed()) {
                            pistol.setImage(images[2]);
                        } else if (right.isPressed()) {
                            pistol.setImage(images[3]);
                        } else {
                            pistol.setImage(images[0]);
                        }
                    }
                    if (event.getCode() == left.getCode()) {
                        left.setReleased();
                        if (up.isPressed()) {
                            pistol.setImage(images[3]);
                        } else if (down.isPressed()) {
                            pistol.setImage(images[2]);
                        } else {
                            pistol.setImage(images[1]);
                        }
                    }
                    if (event.getCode() == right.getCode()) {
                        right.setReleased();
                        if (up.isPressed()) {
                            pistol.setImage(images[2]);
                        } else if (down.isPressed()) {
                            pistol.setImage(images[3]);
                        } else {
                            pistol.setImage(images[1]);
                        }
                    }
                    if (event.getCode() == fire.getCode()) {
                        fire.setReleased();
                    }
                    if (event.getCode() == pause.getCode()) {
                        pause.setReleased();
                    }
                    pistol.setImage(images[0]);
                }
            }
        });
    }

    //DEPLACEMENT----------------------------------------------------------------------------------------------
    private void moveUp(){
        if (!isExplosingProperty.get()){
            if (this.getY()-GameConfig.getInstance().getPistolSpeed() > 0)
                this.setY(this.getY()-GameConfig.getInstance().getPistolSpeed());
        }
    }
    private void moveRight(){
        if (!isExplosingProperty.get()) {
            if (this.getX() + GameConfig.getInstance().getPistolSpeed() < 1210)
                this.setX(this.getX() + GameConfig.getInstance().getPistolSpeed());
        }
    }
    private void moveDown() {
        if (!isExplosingProperty.get()) {
            if (this.getY() + GameConfig.getInstance().getPistolSpeed() < 640)
                this.setY(this.getY() + GameConfig.getInstance().getPistolSpeed());
        }
    }
    private void moveLeft(){
        if (!isExplosingProperty.get()){
            if (this.getX() - GameConfig.getInstance().getPistolSpeed() > 0)
                this.setX(this.getX() - GameConfig.getInstance().getPistolSpeed());
        }
    }
    //-----------------------------------------------------------------------------------------------------------


    public void tuer(){
        this.isExplosingProperty.set(true);
        explosionAnimation.play();
    }

}
