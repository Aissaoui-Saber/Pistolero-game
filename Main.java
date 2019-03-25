package sample;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {
    public static void animateMouvment(Node node,int x,int y){
        final TranslateTransition translateAnimation = new TranslateTransition(Duration.millis(20),node);
        translateAnimation.setAutoReverse(true);
        translateAnimation.setByX(x);
        translateAnimation.setByY(y);
        translateAnimation.setInterpolator(Interpolator.LINEAR);
        translateAnimation.play();
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Key up = new Key(KeyCode.UP);
        Key down = new Key(KeyCode.DOWN);
        Key left = new Key(KeyCode.LEFT);
        Key right = new Key(KeyCode.RIGHT);
        Pane root = new Pane();
        Image back = new Image(this.getClass().getResource("Images/background.jpg").toExternalForm());
        BackgroundSize bs = new BackgroundSize(1280,720,false,false,false,false);
        root.setBackground(new Background(new BackgroundImage(back,null,null,null,bs)));
        ImageView pistol = new ImageView();
        ImageView demon = new ImageView();
        demon.setImage(new Image(this.getClass().getResource("Images/demon.gif").toExternalForm()));
        demon.setFitWidth(150);
        demon.setFitHeight(106);
        pistol.setFocusTraversable(true);
        Image images[] = {new Image(this.getClass().getResource("Images/Pistol_Vertical.png").toExternalForm()),
                new Image(this.getClass().getResource("Images/Pistol_Horisontal.png").toExternalForm()),
                new Image(this.getClass().getResource("Images/Pistol_Diagonal_Right.png").toExternalForm()),
                new Image(this.getClass().getResource("Images/Pistol_Diagonal_Left.png").toExternalForm())};


        pistol.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == up.getCode()){
                    up.setPressed(true);
                    if (left.isPressed()){
                        pistol.setImage(images[3]);
                        animateMouvment(pistol,-10,-10);
                        //pistol.setX(pistol.getX()-10);
                        //pistol.setY(pistol.getY()-10);
                    }else if (right.isPressed()){
                        pistol.setImage(images[2]);
                        animateMouvment(pistol,+10,-10);
                        //pistol.setX(pistol.getX()+10);
                        //pistol.setY(pistol.getY()-10);
                    }else {
                        pistol.setImage(images[0]);
                        animateMouvment(pistol,0,-10);
                        //pistol.setY(pistol.getY()-10);

                    }
                }
                if (event.getCode() == down.getCode()){
                    down.setPressed(true);
                    if (left.isPressed()){
                        pistol.setImage(images[2]);
                        animateMouvment(pistol,-10,+10);
                        //pistol.setX(pistol.getX()-10);
                        //pistol.setY(pistol.getY()+10);
                    }else if(right.isPressed()){
                        pistol.setImage(images[3]);
                        animateMouvment(pistol,+10,+10);
                        //pistol.setX(pistol.getX()+10);
                        //pistol.setY(pistol.getY()+10);
                    }else {
                        pistol.setImage(images[0]);
                        animateMouvment(pistol,0,+10);
                        //pistol.setY(pistol.getY()+10);
                    }
                }
                if (event.getCode() == left.getCode()){
                    left.setPressed(true);
                    if (up.isPressed()){
                        pistol.setImage(images[3]);
                        animateMouvment(pistol,-10,-10);
                        //pistol.setX(pistol.getX()-10);
                        //pistol.setY(pistol.getY()-10);
                    }else if (down.isPressed()){
                        pistol.setImage(images[2]);
                        animateMouvment(pistol,-10,+10);
                        //pistol.setX(pistol.getX()-10);
                        //pistol.setY(pistol.getY()+10);
                    }else {
                        pistol.setImage(images[1]);
                        animateMouvment(pistol,-10,0);
                        //pistol.setX(pistol.getX()-10);;
                    }
                }
                if (event.getCode() == right.getCode()){
                    right.setPressed(true);
                    if (up.isPressed()){
                        pistol.setImage(images[2]);
                        animateMouvment(pistol,+10,-10);
                        //pistol.setX(pistol.getX()+10);
                        //pistol.setY(pistol.getY()-10);
                    }else if (down.isPressed()){
                        pistol.setImage(images[3]);
                        animateMouvment(pistol,+10,+10);
                        //pistol.setX(pistol.getX()+10);
                        //pistol.setY(pistol.getY()+10);
                    }else {
                        pistol.setImage(images[1]);
                        animateMouvment(pistol,+10,0);
                        //pistol.setX(pistol.getX()+10);
                    }
                }
                event.consume();
            }
        });
        pistol.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //pistol.setImage(images[0]);
                //event.consume();
                if (event.getCode() == up.getCode()){
                    up.setPressed(false);
                    //l.setText("");
                    //l.setText("[UP:"+up.isPressed()+", DOWN:"+down.isPressed()+", LEFT:"+left.isPressed()+", RIGHT:"+right.isPressed()+"]");

                    /*if ((down.isReleased())&&(left.isReleased())&&(right.isReleased())){
                        pistol.setImage(images[0]);
                    }*/
                }
                if (event.getCode() == down.getCode()){
                    down.setPressed(false);
                    //l.setText("");
                    //l.setText("[UP:"+up.isPressed()+", DOWN:"+down.isPressed()+", LEFT:"+left.isPressed()+", RIGHT:"+right.isPressed()+"]");

                    /*if ((up.isReleased())&&(left.isReleased())&&(right.isReleased())){
                        pistol.setImage(images[0]);
                    }*/
                }
                if (event.getCode() == left.getCode()){
                    left.setPressed(false);
                    //l.setText("");
                    //l.setText("[UP:"+up.isPressed()+", DOWN:"+down.isPressed()+", LEFT:"+left.isPressed()+", RIGHT:"+right.isPressed()+"]");

                    /*if ((down.isReleased())&&(up.isReleased())&&(right.isReleased())){
                        pistol.setImage(images[0]);
                    }*/
                }
                if (event.getCode() == right.getCode()){
                    right.setPressed(false);
                    //l.setText("[UP:"+up.isPressed()+", DOWN:"+down.isPressed()+", LEFT:"+left.isPressed()+", RIGHT:"+right.isPressed()+"]");

                    //l.setText("");
                    /*if ((down.isReleased())&&(left.isReleased())&&(up.isReleased())){
                        pistol.setImage(images[0]);
                    }*/
                }
                pistol.setImage(images[0]);
                event.consume();
            }
        });

        /*up.getPressedProprety().addListener((observable, oldValue, newValue)-> {
            if (newValue){
                pistol.setY(pistol.getY()-10);
            }
        });
        down.getPressedProprety().addListener((observable, oldValue, newValue)-> {
            if (newValue){
                pistol.setY(pistol.getY()+10);
            }
        });
        left.getPressedProprety().addListener((observable, oldValue, newValue)-> {
            if (newValue){
                pistol.setX(pistol.getX()-10);
            }
        });
        right.getPressedProprety().addListener((observable, oldValue, newValue)-> {
            if (newValue){
                pistol.setX(pistol.getX()+10);
            }
        });*/
        //pistol.layoutXProperty().bind(Bindings.when(right.getPressedProprety()).then().otherwise(0));
        //right.getPressedProprety().bind(Bindings.);




        pistol.setImage(images[0]);
        pistol.setFitWidth(80);
        pistol.setFitHeight(90);
        primaryStage.setTitle("Hello World");
        root.getChildren().addAll(pistol,demon);
        root.setMaxSize(1280,720);
        root.setMinSize(1280,720);
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
