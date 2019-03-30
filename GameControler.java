package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class GameControler implements Initializable {
    @FXML
    private Pane gameScene;
    @FXML
    private Label nbrBallLabel = new Label();
    @FXML
    private Label nbrDemonsLabel = new Label();

    Partie partie;
    Ball b = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partie = new Partie(28,20);

        //NOMBRE DE BALLS LABEL---------------------------------------------------------
        if (partie.getPistolet().nbrBallsProperty.get() != -1){
            nbrBallLabel.setText(partie.getPistolet().nbrBallsProperty.get()+" ");
        }else {
            nbrBallLabel.setText("∞ ");
        }

        partie.getPistolet().nbrBallsProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                nbrBallLabel.setText(newValue+" ");
            }
        });
        //-------------------------------------------------------------------------------
        //NOMBRE DE DEMONS LABEL---------------------------------------------------------
        int nbrDemons = partie.getDemons().size();
        nbrDemonsLabel.setText("  "+partie.nbrDemonsMorts.get()+"/"+nbrDemons+" ");
        partie.nbrDemonsMorts.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                nbrDemonsLabel.setText("  "+newValue+"/"+nbrDemons+" ");
                if ((int)newValue == nbrDemons){//FIN DE LA PARTIE
                    System.out.println("YOU WIN");
                }
            }
        });
        //------------------------------------------------------------------------------

        Node n = gameScene.getChildren().get(1);
        gameScene.getChildren().remove(1);

        for (int i=0;i<partie.getDemons().size();i++) {
            gameScene.getChildren().add(((Demon) partie.getDemons().get(i)).getImage());
        }
        gameScene.getChildren().add(partie.getPistolet().getPistol());
        gameScene.getChildren().add(partie.b.getBallImageView());
        gameScene.getChildren().add(n);
        //TIR----------------------------------------------------------------------------------------------------------
        GameConfig.getInstance().getFireKey().getPressedProprety().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    if (partie.getPistolet().nbrBallsProperty.get() > 0){
                        partie.b = new Ball(partie.getPistolet().ballOutXProperty.get(),partie.getPistolet().ballOutYProperty.get());
                        gameScene.getChildren().add(partie.b.getBallImageView());
                        partie.b.start();
                        partie.getPistolet().nbrBallsProperty.set(partie.getPistolet().nbrBallsProperty.get()-1);
                    }
                }
            }
        });
        //-------------------------------------------------------------------------------------------------------------
        partie.lancer();
    }
}
