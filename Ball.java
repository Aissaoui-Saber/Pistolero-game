package sample;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Ball {
    private Image ballImage;
    private ImageView ball;
    private int ballSpeed = 30;
    private AnimationTimer mouvment;
    private Thread ColusionControler;
    private BooleanProperty stop;

    public Ball(int x,int y){
        ballImage = new Image(this.getClass().getResource("Images/Bullet.png").toExternalForm());
        ball = new ImageView(ballImage);
        ball.setX(x);
        ball.setY(y);
        ball.setFitWidth(10);
        ball.setFitHeight(20);
        stop = new SimpleBooleanProperty(false);
        ColusionControler = new Thread(new Runnable() {
            @Override
            public void run() {
                ball.yProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        if (true){

                        }
                    }
                });
            }
        });
        ColusionControler.start();
        mouvment = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveToTop();
            }
        };
        mouvment.start();
    }

    public ImageView getBallImageView() {
        return ball;
    }

    private void moveToTop(){
        if (ball.getY() > -50){
            ball.setY(ball.getY() - ballSpeed);
        }else{
            mouvment.stop();
        }
    }
}
