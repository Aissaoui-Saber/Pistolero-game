package sample;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.image.ImageView;


public class Demon extends ImageView{
    private boolean sexe;
    private BooleanProperty deadProperty;
    private int demonSpeed;
    private AnimationTimer mouvment;
    private HealthBar vie;
    private Thread explosion;
    public Demon(int x, int y,boolean masculin){
        this.setX(x);
        this.setY(y);
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

        deadProperty = new SimpleBooleanProperty(true);
        demonSpeed=1;
        mouvment = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveDown();
            }
        };
        vie.getHealthBar().layoutXProperty().bind(this.xProperty());
        vie.getHealthBar().layoutYProperty().bind(Bindings.add(this.yProperty(),106));
        //mouvment.start();
        deadProperty.set(false);
        explosion = new Thread(new Runnable() {
            @Override
            public void run() {
                mouvment.stop();
                image.setImage(Data.getData().explosionIMG());
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                image.setY(-300);
            }
        });
    }

    public void deplacer(int direction){

    }
    public BooleanProperty DeadProperty(){
        return this.deadProperty;
    }
    public void blesser(double value){
        vie.setValue(vie.getValue()-value);
        if (vie.getValue() <= 0){
            this.deadProperty.set(true);
            explosion.start();
        }
    }

    private void moveDown(){
        if (this.getY() < 780){
            if (this.getY() <0){
                this.setY(this.getY() + 20);
            }else {
                this.setY(this.getY() + demonSpeed);
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
}
