package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import javax.swing.text.html.ImageView;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuControler implements Initializable {
    @FXML
    private VBox menuLabels;
    @FXML
    private StackPane mainPane;

    private Pane gameScene;
    private GameControler gameControler;
    private FXMLLoader loader;

    private int menuIndex = 0;
    @FXML
    public void changeCursor(KeyEvent event){
        if (event.getCode() == KeyCode.UP){
            if (menuIndex == 0){
                menuLabels.getChildren().get(menuIndex).setStyle("-fx-underline:false;");
                menuIndex = 4;
                menuLabels.getChildren().get(menuIndex).setStyle("-fx-underline:true;");
            }else {
                menuLabels.getChildren().get(menuIndex).setStyle("-fx-underline:false;");
                menuIndex--;
                menuLabels.getChildren().get(menuIndex).setStyle("-fx-underline:true;");
            }
        }
        if (event.getCode() == KeyCode.DOWN){
            if (menuIndex == 4){
                menuLabels.getChildren().get(menuIndex).setStyle("-fx-underline:false;");
                menuIndex = 0;
                menuLabels.getChildren().get(menuIndex).setStyle("-fx-underline:true;");
            }else {
                menuLabels.getChildren().get(menuIndex).setStyle("-fx-underline:false;");
                menuIndex++;
                menuLabels.getChildren().get(menuIndex).setStyle("-fx-underline:true;");
            }
        }
        if (event.getCode() == KeyCode.ENTER){
            switch (menuIndex){
                case 0:
                    loader.setLocation(getClass().getResource("Game.fxml"));
                    try {
                        gameScene = loader.load();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    mainPane.getChildren().setAll(gameScene);
                    gameControler = loader.getController();
                    break;
                case 1:
                    //LOAD GAME
                    break;
                case 2:
                    //SAVE GAME
                    break;
                case 3:
                    //OPTION MENU
                    break;
                case 4:
                    //EXIT GAME
                    System.exit(0);
                    break;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loader = new FXMLLoader();
    }
}