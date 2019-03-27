package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
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

import java.awt.*;
import java.util.LinkedList;
import java.util.Timer;


public class Main extends Application {
    public static void animateMouvment(Node node,int x,int y){
        final TranslateTransition translateAnimation = new TranslateTransition(Duration.millis(20),node);
        translateAnimation.setAutoReverse(true);
        translateAnimation.setByX(x);
        translateAnimation.setByY(y);
        translateAnimation.setInterpolator(Interpolator.LINEAR);
        translateAnimation.play();
    }

    public static int rand(int minIncl,int maxIncl){
        return minIncl + (int)(Math.random() * ((maxIncl - minIncl) + 1));
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Demon demon = new Demon(150,150);
        Pane root = new Pane();
        Pistol pistol = new Pistol(root);
        Image back = new Image(this.getClass().getResource("Images/background.jpg").toExternalForm());
        BackgroundSize bs = new BackgroundSize(1280,720,false,false,false,false);
        root.setBackground(new Background(new BackgroundImage(back,null,null,null,bs)));

        primaryStage.setTitle("PISTOLERO");
        root.getChildren().addAll(demon.getImage());
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
