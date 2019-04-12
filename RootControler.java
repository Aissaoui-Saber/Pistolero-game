package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootControler implements Initializable {
    @FXML
    private StackPane rootScene;//LA RACINE

    private Parent gameScene,menuScene,nouvellePartieScene,optionsScene;

    public PartieControler partieControler;
    public MenuControler menuControler;
    public NouvellePartieControler nouvellePartieControler;
    public OptionsControler optionsControler;


    private FXMLLoader menuLoader,gameLoader,nouvellePartieLoader,optionsLoader;


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
        partieControler = gameLoader.getController();
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

        //OPTIONS------------------------------------------------
        optionsLoader = new FXMLLoader();
        optionsLoader.setLocation(getClass().getResource("Options.fxml"));
        try {
            optionsScene = optionsLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        optionsControler = optionsLoader.getController();
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
    public void goToOptionsScene(){
        rootScene.getChildren().clear();
        rootScene.getChildren().add(optionsScene);
    }
    public void reloadGameScene(){
        rootScene.getChildren().remove(gameScene);
        partieControler.clearScene();
        gameLoader = new FXMLLoader();
        gameLoader.setLocation(getClass().getResource("Game.fxml"));
        try {
            gameScene = gameLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        partieControler = gameLoader.getController();
    }
}
