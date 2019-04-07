package sample;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sun.awt.windows.ThemeReader;

import javax.naming.Binding;


public class Demon{
    private boolean sexe;
    private double degat;
    private ImageView image;
    private BooleanProperty deadProperty;
    private int demonSpeed;
    private AnimationTimer mouvment;
    private HealthBar vie;
    private Thread explosion;
    public Demon(int x,int y){
        image = new ImageView();
        image.setX(x);
        image.setY(y);
        vie = new HealthBar(150);
        image.setImage(Data.getData().demon1IMG());
        image.setFitWidth(150);
        image.setFitHeight(106);

        sexe = true;
        degat = 20;
        deadProperty = new SimpleBooleanProperty(true);
        demonSpeed=1;
        mouvment = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveDown();
            }
        };
        vie.getHealthBar().layoutXProperty().bind(image.xProperty());
        vie.getHealthBar().layoutYProperty().bind(Bindings.add(image.yProperty(),106));
        //mouvment.start();
        deadProperty.set(false);
        explosion = new Thread(new Runnable() {
            @Override
            public void run() {
                mouvment.stop();
                image.setImage(Data.getData().explosionIMG());
                vie.hide();
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                image.setY(1000);
                //((Pane)this.image.getParent()).getChildren().remove(this.vie.getHealthBar());
                //((Pane)this.image.getParent()).getChildren().remove(this.image);
            }
        });
    }


    public ImageView getDemonImage() {
        return image;
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
        if (image.getY() < 720){
            if (image.getY() <0){
                image.setY(image.getY() + 20);
            }else {
                image.setY(image.getY() + demonSpeed);
            }

        }else{
            mouvment.stop();
        }
    }

    public HealthBar getVie() {
        return vie;
    }

    public boolean intersects(Node n){
        return this.image.getBoundsInParent().intersects(n.getBoundsInParent());
    }
    public void stop(){
        this.mouvment.stop();
    }
    public void start(){
        this.mouvment.start();
    }
}
