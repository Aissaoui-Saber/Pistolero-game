package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Ball {
    private Image ballImage;
    private ImageView ball;
    private int ballSpeed = 20;
    private AnimationTimer mouvment;

    public Ball(int x,int y){
        ballImage = new Image(this.getClass().getResource("Images/Bullet.png").toExternalForm());
        ball = new ImageView(ballImage);
        ball.setX(x);
        ball.setY(y);
        ball.setFitWidth(10);
        ball.setFitHeight(20);
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
