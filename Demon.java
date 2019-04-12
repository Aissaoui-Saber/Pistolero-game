package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.util.Duration;


public class Demon extends ImageView{
    private boolean sexe;
    public BooleanProperty isDeadProperty;
    private double speedX;
    private double speedY;
    private AnimationTimer mouvment;
    private HealthBar vie;
    private Timeline explosionAnimation;
    public BooleanProperty isExplosing;
    public Demon(int x, int y,boolean masculin){
        this.setX(x);
        this.setY(y);
        this.speedX = randomSpeed();
        this.speedY = randomSpeed();
        ImageView image = this;
        sexe = masculin;
        if (masculin){
            this.setImage(Data.getData().demonMale1IMG());
            this.setFitWidth(Data.getData().demonMale1Width());
            this.setFitHeight(Data.getData().demonMale1Height());
            vie = new HealthBar(Data.getData().demonMale1Width());
        }else {
            this.setImage(Data.getData().demonFemale1IMG());
            this.setFitWidth(Data.getData().demonFemale1Width());
            this.setFitHeight(Data.getData().demonFemale1Height());
            vie = new HealthBar(Data.getData().demonFemale1Width());
        }
        //EXPLOSION--------------------------------------------------------------------------
        isExplosing = new SimpleBooleanProperty(false);
        isDeadProperty = new SimpleBooleanProperty(false);
        isExplosing.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue)
                    image.setImage(Data.getData().explosionIMG());
            }
        });
        KeyFrame debutExplosion = new KeyFrame(Duration.ZERO,new KeyValue(this.isExplosing,true));
        KeyFrame finExplosion = new KeyFrame(Duration.millis(600),
                new KeyValue(this.isExplosing,false),
                new KeyValue(this.isDeadProperty,true));
        explosionAnimation = new Timeline();
        explosionAnimation.getKeyFrames().addAll(debutExplosion,finExplosion);
        //-----------------------------------------------------------------------------------

        mouvment = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (image.getY()<0){
                    moveDown();
                }else{
                    move();
                }

            }
        };

        //BAR DE VIE BINDING---------------------------------------------------------
        vie.getHealthBar().layoutXProperty().bind(this.xProperty());
        if (sexe){
            vie.getHealthBar().layoutYProperty().bind(Bindings.add(this.yProperty(),Data.getData().demonMale1Height()));
        }else{
            vie.getHealthBar().layoutYProperty().bind(Bindings.add(this.yProperty(),Data.getData().demonFemale1Height()));
        }

        //---------------------------------------------------------------------------

    }

    public void deplacer(int direction){
    }
    public void blesser(double value){
        vie.setValue(vie.getValue()-value);
        playEffect();
        if (vie.getValue() <= 0){
            mouvment.stop();
            explosionAnimation.play();
            vie.hide();
        }
    }

    private void playEffect(){
        KeyFrame begin = new KeyFrame(Duration.ZERO,new KeyValue(effectProperty(), new Bloom()));
        KeyFrame end = new KeyFrame(Duration.millis(30),new KeyValue(effectProperty(),null));
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(begin,end);
        timeline.play();
    }
    void move() {
        /* deplacer le centre de la particule */
        this.setX(this.getX() + speedX * GameConfig.getInstance().getDemonsSpeed());
        this.setY(this.getY() + speedY * GameConfig.getInstance().getDemonsSpeed());

        /* detecter les collision avec le bord
         * et si collision modifier le vecteur de la vitesse */

        if (this.getX() < 0 && speedX < 0) {
            speedX *= -1;
        }
        if (this.getY() < 0 && speedY < 0) {
            speedY *= -1;
        }
        if (this.getX() > ((Region) getParent()).getWidth()-this.getFitWidth() && speedX > 0) {
            speedX *= -1;
        }
        if (this.getY() > ((Region) getParent()).getHeight()-this.getFitHeight() && speedY > 0) {
            speedY *= -1;
        }
    }
    private void moveDown(){
        if (this.getY() < 780){
            if (this.getY() <0){
                this.setY(this.getY() + 20);
            }else {
                this.setY(this.getY() + 1);
            }

        }else{
            mouvment.stop();
        }
    }

    public HealthBar getVie() {
        return vie;
    }

    public boolean intersects(Node n){
        return this.getBoundsInParent().intersects(n.getBoundsInParent());
    }
    public void stop(){
        this.mouvment.stop();
    }
    public void start(){
        this.mouvment.start();
    }

    private double randomSpeed(){
        double num1 = randomDouble(0.8,1);
        double num2 = randomDouble(-1,-0.8);
        if (randomInt(0,1)==0){
            return num1;
        }else {
            return num2;
        }
    }
    private int randomInt(int minIncl,int maxIncl){
        return minIncl + (int)(Math.random() * ((maxIncl - minIncl) + 1));
    }
    private double randomDouble(double minIncl,double maxIncl){
        return minIncl + (Math.random() * ((maxIncl - minIncl) + 1));
    }
}
