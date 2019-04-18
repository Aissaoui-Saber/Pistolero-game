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
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class Pistol extends ImageView{
    private ImageView pistol;

    public AnimationTimer upTimer;
    public AnimationTimer rightTimer;
    public AnimationTimer downTimer;
    public AnimationTimer leftTimer;

    public IntegerProperty ballOutXProperty;
    public IntegerProperty ballOutYProperty;

    private Timeline explosionAnimation;

    public BooleanProperty deadProperty;
    public BooleanProperty isExplosingProperty;


    public Pistol(int x,int y){
        //INITIALISATION DE PISTOLET------------------------------------------------------------------------------
        this.setFocusTraversable(true);
        this.setImage(Data.getData().pistolVerticalIMG());
        this.setFitWidth(70);
        this.setFitHeight(79);
        this.setX(x);
        this.setY(y);
        pistol = this;
        deadProperty = new SimpleBooleanProperty(false);
        isExplosingProperty = new SimpleBooleanProperty(false);
        //--------------------------------------------------------------------------------------------------------




        //EXPLOSION------------------------------------------------------------------------------------------------
        isExplosingProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue)
                pistol.setImage(Data.getData().explosionIMG());
        });
        KeyFrame debutExplosion = new KeyFrame(Duration.ZERO, (KeyValue) null);
        KeyFrame finExplosion = new KeyFrame(Duration.millis(1000),new KeyValue(deadProperty,true));
        explosionAnimation = new Timeline();
        explosionAnimation.getKeyFrames().addAll(debutExplosion,finExplosion);
        //--------------------------------------------------------------------------------------------------------




        //BINDING POSITION DE PISTOLET AVEC POSITION DE TIR DES BALLS---------------------------------------------
        ballOutXProperty = new SimpleIntegerProperty();
        ballOutYProperty = new SimpleIntegerProperty();

        ballOutXProperty.bind(Bindings.add(this.xProperty(),30));
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


        //---------------------------------------------------------------------------------------------------------

    }

    //DEPLACEMENT----------------------------------------------------------------------------------------------
    private void moveUp(){
        if (!isExplosingProperty.get()){
            if (this.getY() - GameConfig.getInstance().getPistolSpeed() > 0)
                this.setY(this.getY() - GameConfig.getInstance().getPistolSpeed());
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
        Data.getData().gameOverSFX();
        explosionAnimation.play();
    }

}
