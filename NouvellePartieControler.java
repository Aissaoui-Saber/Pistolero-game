package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class NouvellePartieControler implements Initializable {

    @FXML
    private VBox nouvellePartieLabels;
    @FXML
    private Label nbrBall,nbrDemon;

    private int index = 0,indexNbrBall = 1,indexNbrDemon = 1;

    @FXML
    public void changeCursor(KeyEvent event){
        if (event.getCode() == KeyCode.UP){
            if (index == 0){
                nouvellePartieLabels.getChildren().get(index).setStyle("-fx-underline:false;");
                index = 3;
                nouvellePartieLabels.getChildren().get(index).setStyle("-fx-underline:true;");
            }else {
                nouvellePartieLabels.getChildren().get(index).setStyle("-fx-underline:false;");
                index--;
                nouvellePartieLabels.getChildren().get(index).setStyle("-fx-underline:true;");
            }
        }
        if (event.getCode() == KeyCode.DOWN){
            if (index == 3){
                nouvellePartieLabels.getChildren().get(index).setStyle("-fx-underline:false;");
                index = 0;
                nouvellePartieLabels.getChildren().get(index).setStyle("-fx-underline:true;");
            }else {
                nouvellePartieLabels.getChildren().get(index).setStyle("-fx-underline:false;");
                index++;
                nouvellePartieLabels.getChildren().get(index).setStyle("-fx-underline:true;");
            }
        }
        if (event.getCode() == KeyCode.LEFT){
            if (index == 0){
                if (indexNbrBall > 1){
                    if (indexNbrBall > 20)
                        indexNbrBall-=5;
                    else
                        indexNbrBall--;
                    nbrBall.setText("Nombre de Balls < "+indexNbrBall*5+" >");
                }
            }
            if  (index == 1){
                if (indexNbrDemon > 1){
                    if (indexNbrDemon >100)
                        indexNbrDemon-=10;
                    else
                        indexNbrDemon--;
                    nbrDemon.setText("Nombre de Démons < "+indexNbrDemon+" >");
                }
            }
        }
        if (event.getCode() == KeyCode.RIGHT){
            if (index == 0){
                if (indexNbrBall == 200){
                    indexNbrBall+=5;
                    nbrBall.setText("Nombre de Balls < INFINI >");
                }
                if (indexNbrBall < 200){
                    if (indexNbrBall >= 20)
                        indexNbrBall+=5;
                    else
                        indexNbrBall++;
                    nbrBall.setText("Nombre de Balls < "+indexNbrBall*5+" >");
                }
            }
            if  (index == 1) {
                if (indexNbrDemon >= 100)
                    indexNbrDemon += 10;
                else
                    indexNbrDemon++;
                nbrDemon.setText("Nombre de Démons < " + indexNbrDemon + " >");
            }
        }
        if (event.getCode() == KeyCode.ENTER){
            if (index == 2){
                if (indexNbrBall == 205){
                    Main.rootControler.partieControler.nouvellePartie(-1,indexNbrDemon,false);
                }else {
                    Main.rootControler.partieControler.nouvellePartie(indexNbrBall*5,indexNbrDemon,false);
                }
                Main.rootControler.goToGameScene();
            }
            if (index == 3){
                Main.rootControler.goToMenuScene();
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
