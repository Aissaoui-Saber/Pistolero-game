package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameControler implements Initializable {
    @FXML
    private Pane gameScene;
    @FXML
    private Label nbrBallLabel = new Label();

    Partie partie;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partie = new Partie(-1,10);
        if (partie.getPistolet().nbrBallsProperty().get() != -1){
            nbrBallLabel.setText(partie.getPistolet().nbrBallsProperty().get()+" ");
        }else {
            nbrBallLabel.setText("∞ ");
        }

        partie.getPistolet().nbrBallsProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > 0)
                    nbrBallLabel.setText(String.valueOf(newValue)+" ");
            }
        });
        gameScene.getChildren().add(partie.getPistolet().getPistol());
        for (int i=0;i<partie.getDemons().size();i++){
            gameScene.getChildren().add(((Demon) partie.getDemons().get(i)).getImage());
        }
        partie.lancer();
    }
}
