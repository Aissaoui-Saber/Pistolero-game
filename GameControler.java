package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameControler implements Initializable {
    @FXML
    private Pane gameScene;
    @FXML
    private Label nbrBallLabel = new Label();
    @FXML
    private Label nbrDemonsLabel = new Label();

    private Partie partie;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    private void ballColusion(Ball b){
        Thread t= new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<partie.getDemons().size();i++){
                    int k = i;
                    partie.getBalls().get(partie.getBalls().size()-1).getBallImageView().yProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            if (b.getBallImageView().getBoundsInParent().intersects(((Demon)(partie.getDemons().get(k))).getDemonImage().getBoundsInLocal())){
                                if (!b.isBlocked()){
                                    b.setBlocked(true);
                                    ((Demon)(partie.getDemons().get(k))).blesser(b.getDegat());
                                    b.stop();
                                }
                            }
                        }
                    });
                }
            }
        });
        t.start();
    }

    public void startGame(){
        partie.lancer();
    }

    public void nouvellePartie(int nBall,int nDemons){
        partie = new Partie(nBall,nDemons);

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
            gameScene.getChildren().add(((Demon) partie.getDemons().get(i)).getDemonImage());
            gameScene.getChildren().add(((Demon) partie.getDemons().get(i)).getVie().getHealthBar());
        }
        gameScene.getChildren().add(partie.getPistolet().getPistol());
        gameScene.getChildren().add(n);
        //TIR----------------------------------------------------------------------------------------------------------
        GameConfig.getInstance().getFireKey().getPressedProprety().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    if (partie.getPistolet().nbrBallsProperty.get() > 0){
                        partie.getBalls().add(new Ball(partie.getPistolet().ballOutXProperty.get(),partie.getPistolet().ballOutYProperty.get(),25));
                        ballColusion(partie.getBalls().get(partie.getBalls().size()-1));
                        gameScene.getChildren().add(partie.getBalls().get(partie.getBalls().size()-1).getBallImageView());
                        partie.getBalls().get(partie.getBalls().size()-1).start();
                        partie.getPistolet().nbrBallsProperty.set(partie.getPistolet().nbrBallsProperty.get()-1);
                    }else if (partie.getPistolet().nbrBallsProperty.get() == -1){
                        partie.getBalls().add(new Ball(partie.getPistolet().ballOutXProperty.get(),partie.getPistolet().ballOutYProperty.get(),25));
                        ballColusion(partie.getBalls().get(partie.getBalls().size()-1));
                        gameScene.getChildren().add(partie.getBalls().get(partie.getBalls().size()-1).getBallImageView());
                        partie.getBalls().get(partie.getBalls().size()-1).start();
                    }
                }
            }
        });
        //TIR AVEC LA SOURIS
        /*
        gameScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (partie.getPistolet().nbrBallsProperty.get() > 0){
                    partie.getBalls().add(new Ball(partie.getPistolet().ballOutXProperty.get(),partie.getPistolet().ballOutYProperty.get(),25));
                    ballColusion(partie.getBalls().get(partie.getBalls().size()-1));
                    gameScene.getChildren().add(partie.getBalls().get(partie.getBalls().size()-1).getBallImageView());
                    partie.getBalls().get(partie.getBalls().size()-1).start();
                    partie.getPistolet().nbrBallsProperty.set(partie.getPistolet().nbrBallsProperty.get()-1);
                }else if (partie.getPistolet().nbrBallsProperty.get() == -1){
                    partie.getBalls().add(new Ball(partie.getPistolet().ballOutXProperty.get(),partie.getPistolet().ballOutYProperty.get(),25));
                    ballColusion(partie.getBalls().get(partie.getBalls().size()-1));
                    gameScene.getChildren().add(partie.getBalls().get(partie.getBalls().size()-1).getBallImageView());
                    partie.getBalls().get(partie.getBalls().size()-1).start();
                }
            }
        });*/
        //-------------------------------------------------------------------------------------------------------------
        GameConfig.getInstance().getPauseKey().getPressedProprety().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    partie.pause();
                    Main.rootControler.goToMenuScene();
                }

            }
        });
    }

    public void chargerPartie(String filePath){
        partie = new Partie(filePath);
    }

    public void sauvgarderPartie(){

    }
}
