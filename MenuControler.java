package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuControler implements Initializable {
    @FXML
    private VBox menuLabels;


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
                    //NEW GAME
                    Main.rootControler.goToNouvellePartieScene();
                    break;
                case 1:
                    //LOAD GAME
                    Main.rootControler.partieControler.nouvellePartie(0,0,true);
                    Main.rootControler.goToGameScene();
                    break;
                case 2:
                    //SAVE GAME
                    if (Main.rootControler.partieControler.partieEnPause != null) {
                        if (Main.rootControler.partieControler.partieFini != null){
                            if (Main.rootControler.partieControler.partieEnPause.get()){
                                if (!Main.rootControler.partieControler.partieFini.get()){
                                    Main.rootControler.partieControler.sauvgarderPartie();
                                }
                            }
                        }
                    }
                    break;
                case 3:
                    //OPTIONS
                    Main.rootControler.goToOptionsScene();
                    break;
                case 4:
                    //EXIT GAME
                    System.exit(0);
                    break;
            }
        }
        if (event.getCode() == KeyCode.ESCAPE){
            if (Main.rootControler.partieControler.partieEnPause != null) {
                if (Main.rootControler.partieControler.partieFini != null){
                    if (Main.rootControler.partieControler.partieEnPause.get()){
                        if (!Main.rootControler.partieControler.partieFini.get()){
                            Main.rootControler.goToGameScene();
                            Main.rootControler.partieControler.reprendre();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
