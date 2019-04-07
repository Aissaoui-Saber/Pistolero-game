package sample;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameControler implements Initializable {
    @FXML
    public Pane gameScene;
    @FXML
    private Label nbrBallLabel = new Label();
    @FXML
    private Label nbrDemonsLabel = new Label();
    @FXML
    private HBox hbox;
    @FXML
    private ImageView backImg;

    public Partie partie;

    private Thread gameOver;
    private Thread netoyage;
    private ImageView gameOverIMG;
    private ImageView bravoIMG;

    private ChangeListener<Boolean> pause;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    private void ballColusion(Ball b){
        Thread ballIntersection= new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<partie.getDemons().size();i++){
                    int k = i;
                    partie.getBalls().get(partie.getBalls().size()-1).getBallImageView().yProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            if (!((Demon)(partie.getDemons().get(k))).DeadProperty().get()) {
                                if (b.getBallImageView().getBoundsInParent().intersects(((Demon) (partie.getDemons().get(k))).getDemonImage().getBoundsInLocal())) {
                                    if (!b.isBlocked()) {
                                        b.setBlocked(true);
                                        ((Demon) (partie.getDemons().get(k))).blesser(b.getDegat());
                                        b.stop();
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
        ballIntersection.start();
    }

    public void startGame(){
        partie.lancer();
    }

    public void nouvellePartie(int nBall,int nDemons){
        partie = new Partie(nBall,nDemons);
        pause = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    partie.pause();
                    Main.rootControler.goToMenuScene();
                    System.out.println("Escape from game listener");
                }
            }
        };
        netoyage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (netoyage != null){
                    int i=0;
                    while (i<gameScene.getChildren().size()){
                        System.out.println(gameScene.getChildren().get(i).getTypeSelector());
                        i++;
                    }
                    System.out.println("----------------");
                }
            }
        });
        gameOverIMG = new ImageView(Data.getData().gameOverIMG());
        gameOverIMG.setFitWidth(400);
        gameOverIMG.setFitHeight(225);
        gameOverIMG.setY(247);
        gameOverIMG.setX(440);

        bravoIMG = new ImageView(Data.getData().bravoIMG());
        bravoIMG.setX(452);
        bravoIMG.setY(172);

        gameOver = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(800);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        //NOMBRE DE BALLS LABEL---------------------------------------------------------
        if (partie.nbrBallsProperty.get() != -1){
            nbrBallLabel.setText(partie.nbrBallsProperty.get()+" ");
        }else {
            nbrBallLabel.setText("∞ ");
        }

        partie.nbrBallsProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                nbrBallLabel.setText(newValue+" ");
            }
        });

        //-------------------------------------------------------------------------------
        //NOMBRE DE DEMONS LABEL---------------------------------------------------------
        int nbrDemons = partie.getDemons().size();
        if (nDemons > -1){
            nbrDemonsLabel.setText("  "+partie.nbrDemonsMorts.get()+"/"+nDemons+" ");
        }else {
            nbrDemonsLabel.setText("∞ ");
        }

        partie.nbrDemonsMorts.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                nbrDemonsLabel.setText("  "+newValue+"/"+nbrDemons+" ");
                if ((int)newValue == nbrDemons){//FIN DE LA PARTIE (TOUT DEMONS MORTS)
                    gameScene.getChildren().add(bravoIMG);
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
                    if (partie.getPistolet().vivant.get()) {
                        System.out.println(gameScene.getChildren().size());
                        if (partie.nbrBallsProperty.get() > 0) {//NOMBRE DE BALLS FINI
                            partie.getBalls().add(new Ball(partie.getPistolet().ballOutXProperty.get(), partie.getPistolet().ballOutYProperty.get(), 25));
                            ballColusion(partie.getBalls().get(partie.getBalls().size() - 1));
                            gameScene.getChildren().add(partie.getBalls().get(partie.getBalls().size() - 1).getBallImageView());
                            partie.getBalls().get(partie.getBalls().size() - 1).start();
                            partie.nbrBallsProperty.set(partie.nbrBallsProperty.get() - 1);
                        } else if (partie.nbrBallsProperty.get() == -1) {//NOMBRE DE BALLS INFINI
                            partie.getBalls().add(new Ball(partie.getPistolet().ballOutXProperty.get(), partie.getPistolet().ballOutYProperty.get(), 25));
                            ballColusion(partie.getBalls().get(partie.getBalls().size() - 1));
                            gameScene.getChildren().add(partie.getBalls().get(partie.getBalls().size() - 1).getBallImageView());
                            partie.getBalls().get(partie.getBalls().size() - 1).start();
                        }
                    }
                }
            }
        });
        //TIR AVEC LA SOURIS
        gameScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (partie.getPistolet().vivant.get()){
                    if (partie.nbrBallsProperty.get() > 0){//NOMBRE DE BALLS FINI
                        partie.getBalls().add(new Ball(partie.getPistolet().ballOutXProperty.get(),partie.getPistolet().ballOutYProperty.get(),25));
                        ballColusion(partie.getBalls().get(partie.getBalls().size()-1));
                        gameScene.getChildren().add(partie.getBalls().get(partie.getBalls().size()-1).getBallImageView());
                        partie.getBalls().get(partie.getBalls().size()-1).start();
                        partie.nbrBallsProperty.set(partie.nbrBallsProperty.get()-1);
                    }else if (partie.nbrBallsProperty.get() == -1){//NOMBRE DE BALLS INFINI
                        partie.getBalls().add(new Ball(partie.getPistolet().ballOutXProperty.get(),partie.getPistolet().ballOutYProperty.get(),25));
                        ballColusion(partie.getBalls().get(partie.getBalls().size()-1));
                        gameScene.getChildren().add(partie.getBalls().get(partie.getBalls().size()-1).getBallImageView());
                        partie.getBalls().get(partie.getBalls().size()-1).start();
                    }
                }
            }
        });
        //-------------------------------------------------------------------------------------------------------------
        GameConfig.getInstance().getPauseKey().getPressedProprety().addListener(pause);
        //FIN DE LA PARTIE--------------------------------------------------------------------------------------
        partie.getPistolet().vivant.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                partie.pause();
                gameOver.start();
                gameScene.getChildren().add(gameOverIMG);
            }
        });
        //------------------------------------------------------------------------------------------------------
        netoyage.start();
    }

    public void chargerPartie(String filePath){
        partie = new Partie(filePath);
    }

    public void sauvgarderPartie(){

    }

    public void clearScene(){
        GameConfig.getInstance().getPauseKey().getPressedProprety().removeListener(pause);
    }
}
