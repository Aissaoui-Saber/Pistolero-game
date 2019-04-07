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

    private Parent gameScene,menuScene,nouvellePartieScene;

    public GameControler gameControler;
    public MenuControler menuControler;
    public NouvellePartieControler nouvellePartieControler;


    private FXMLLoader menuLoader,gameLoader,nouvellePartieLoader;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //MENU-----------------------------------------------------------
        menuLoader = new FXMLLoader();
        menuLoader.setLocation(getClass().getResource("Menu.fxml"));
        try {
            menuScene = menuLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        menuControler = menuLoader.getController();
        //---------------------------------------------------------------

        //GAME-----------------------------------------------------------
        gameLoader = new FXMLLoader();
        gameLoader.setLocation(getClass().getResource("Game.fxml"));
        try {
            gameScene = gameLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        gameControler = gameLoader.getController();
        //---------------------------------------------------------------

        //NOUVELLE PARTIE------------------------------------------------
        nouvellePartieLoader = new FXMLLoader();
        nouvellePartieLoader.setLocation(getClass().getResource("NouvellePartie.fxml"));
        try {
            nouvellePartieScene = nouvellePartieLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        nouvellePartieControler = nouvellePartieLoader.getController();
        //---------------------------------------------------------------
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
    public void goToNouvellePartieScene(){
        rootScene.getChildren().clear();
        rootScene.getChildren().add(nouvellePartieScene);
    }
    public void reloadGameScene(){
        rootScene.getChildren().remove(gameScene);
        gameControler.clearScene();
        gameLoader = new FXMLLoader();
        gameLoader.setLocation(getClass().getResource("Game.fxml"));
        try {
            gameScene = gameLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        gameControler = gameLoader.getController();
    }
}
