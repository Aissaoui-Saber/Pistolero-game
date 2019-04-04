package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootControler implements Initializable {
    @FXML
    private StackPane rootScene;//LA RACINE

    private Parent gameScene,menuScene;

    public GameControler gameControler;
    public MenuControler menuControler;

    private FXMLLoader menuLoader,gameLoader;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        menuLoader = new FXMLLoader();
        menuLoader.setLocation(getClass().getResource("Menu.fxml"));
        try {
            menuScene = menuLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        menuControler = menuLoader.getController();

        gameLoader = new FXMLLoader();
        gameLoader.setLocation(getClass().getResource("Game.fxml"));
        try {
            gameScene = gameLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        gameControler = gameLoader.getController();

        goToMenuScene();
    }
    public void goToGameScene(){
        rootScene.getChildren().clear();
        rootScene.getChildren().add(gameScene);
    }
    public void goToMenuScene(){
        rootScene.getChildren().clear();
        rootScene.getChildren().add(menuScene);
    }

}
