package sample;

import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.util.Duration;

import static javafx.util.Duration.millis;


public class Demon extends ImageView{
    private boolean male;
    private double speedX;
    private double speedY;
    private AnimationTimer mouvment;
    private HealthBar vie;
    private Timeline explosionAnimation;
    private ImageView image;

    public BooleanProperty isExplosingProperty;
    public BooleanProperty isDeadProperty;
    public BooleanProperty isMovingProperty;
    //public boolean isMovingDown;

    public Demon(int x, int y,boolean masculin,double health){
        this.setX(x);
        this.setY(y);
        this.speedX = randomSpeed();
        this.speedY = randomSpeed();
        image = this;
        male = masculin;
        isMovingProperty = new SimpleBooleanProperty(false);
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
        vie.setValue(health);
        //EXPLOSION--------------------------------------------------------------------------
        isExplosingProperty = new SimpleBooleanProperty(false);
        isDeadProperty = new SimpleBooleanProperty(false);
        isExplosingProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue)
                image.setImage(Data.getData().explosionIMG());
        });
        KeyFrame debutExplosion = new KeyFrame(Duration.ZERO,new KeyValue(this.isExplosingProperty,true));
        KeyFrame finExplosion = new KeyFrame(millis(600),
                new KeyValue(this.isExplosingProperty,false),
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
                    if (image!=null){
                        move();
                    }
                }

            }
        };

        //BAR DE VIE BINDING---------------------------------------------------------
        vie.getHealthBar().layoutXProperty().bind(this.xProperty());
        if (male){
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
            Data.getData().explosionSFX();
            explosionAnimation.play();
            vie.hide();
        }
    }

    private void playEffect(){
        KeyFrame begin = new KeyFrame(Duration.ZERO,new KeyValue(effectProperty(), new Bloom()));
        KeyFrame end = new KeyFrame(millis(30),new KeyValue(effectProperty(),null));
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(begin,end);
        timeline.play();
    }
    synchronized void move() {
        isMovingProperty.set(true);
        /* deplacer le centre de la particule */
        this.setX(this.getX() + speedX * GameConfig.getInstance().getDemonsSpeed());
        this.setY(this.getY() + speedY * GameConfig.getInstance().getDemonsSpeed());
        isMovingProperty.set(false);

        /* detecter les collision avec le bord
         * et si collision modifier le vecteur de la vitesse */

        if (this.getX() < 0 && speedX < 0) {
            speedX *= -1;
        }
        if (this.getY() < 0 && speedY < 0) {
            speedY *= -1;
        }
        if (this.getParent() != null) {
            if (this.getX() > ((Region) this.getParent()).getWidth() - this.getFitWidth() && speedX > 0) {
                speedX *= -1;
            }
            if (this.getY() > ((Region) this.getParent()).getHeight() - this.getFitHeight() && speedY > 0) {
                speedY *= -1;
            }
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

    public boolean isMale() {
        return male;
    }

    private double randomSpeed(){
        double num1 = Main.randomDouble(0.8,1);
        double num2 = Main.randomDouble(-1,-0.8);
        if (Main.randomInt(0,1)==0){
            //isMovingDown = true;
            return num1;
        }else {
            //isMovingDown = false;
            return num2;
        }
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void changeXdirection(){
        this.speedX *= -1;
    }
    public void changeYdirection(){
        this.speedY *= -1;
    }
    public void resetPosition(){
        stop();
        setY(-200);
    }
}
