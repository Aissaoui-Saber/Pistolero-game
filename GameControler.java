package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameControler implements Initializable {
    @FXML
    private BorderPane gameScene;

    Pistol pistol;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pistol = new Pistol();
        pistol.getImage().requestFocus();
        gameScene.getChildren().add(pistol.getImage());
    }
}
