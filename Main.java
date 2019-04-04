package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import java.io.IOException;
import java.util.LinkedList;
import java.util.Timer;


public class Main extends Application {
    public static RootControler rootControler;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = new Pane();
        FXMLLoader rootLoader = new FXMLLoader();
        rootLoader.setLocation(getClass().getResource("Root.fxml"));
        try {
            root = rootLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        rootControler = rootLoader.getController();
        primaryStage.setTitle("PISTOLERO");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
