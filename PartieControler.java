package sample;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class PartieControler implements Initializable {
    @FXML
    public Pane gameScene;
    @FXML
    private Label nbrBallLabel = new Label();
    @FXML
    private Label nbrDemonsLabel = new Label();

    private Thread netoyage;
    private Thread demonGenerator;

    private ImageView gameOverIMG;
    private ImageView bravoIMG;

    private ArrayList<Demon> demons;
    private ArrayList<Ball> balls;
    private Pistol pistolet;

    public IntegerProperty nbrBallsProperty;
    public IntegerProperty nbrDemonsMorts;

    private BooleanProperty partieEnCour;

    private ChangeListener<Boolean> pause;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    //GENERATEUR DE DEMONS-----------------------------------------------------------------------------------

    //-------------------------------------------------------------------------------------------------------


    public void nouvellePartie(int nBall,int nDemons){

        /*pause = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    partie.pause();
                    Main.rootControler.goToMenuScene();
                    System.out.println("Escape from game listener");
                }
            }
        };*/
        pistolet = new Pistol();
        balls = new ArrayList<Ball>();
        demons = new ArrayList<Demon>();
        nbrDemonsMorts = new SimpleIntegerProperty(0);
        nbrBallsProperty = new SimpleIntegerProperty(nBall);
        partieEnCour = new SimpleBooleanProperty(false);

        Runnable r = new Runnable() {
            @Override
            public void run() {

            }
        };


        //SUPPRIMER LES BALLS ET LES DEMONS NON UTILS--------------------------------------------------------------
        netoyage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (netoyage!=null){
                    if (demons.size()>0){
                        if (demons.get(0).DeadProperty().get()){
                            //gameScene.getChildren().remove(demons.get(0).getDemonImage());
                            //gameScene.getChildren().remove(demons.get(0).getVie().getHealthBar());
                            demons.remove(0);
                        }
                    }
                    if (balls.size()>0){
                        if (balls.get(0).isBlocked()){
                            //gameScene.getChildren().remove(balls.get(0).getBallImageView());
                            balls.remove(0);
                        }
                    }
                }
            }
        });
        //---------------------------------------------------------------------------------------------------------
        //FIN DE LA PARTIE (GAME OVER / BRAVO)---------------------------------------------------------------------
        gameOverIMG = new ImageView(Data.getData().gameOverIMG());
        gameOverIMG.setFitWidth(400);
        gameOverIMG.setFitHeight(225);
        gameOverIMG.setY(247);
        gameOverIMG.setX(440);

        bravoIMG = new ImageView(Data.getData().bravoIMG());
        bravoIMG.setX(452);
        bravoIMG.setY(172);

        //QUAND LE PISTOLET MORT, AFFICHER GAME OVER
        pistolet.vivant.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });
                t.start();
                gameScene.getChildren().add(gameOverIMG);//AFFICHER GAME OVER
            }
        });
        //--------------------------------------------------------------------------------------------------------
        //NOMBRE DE BALLS LABEL-----------------------------------------------------------------------------------
        if (nbrBallsProperty.get() != -1){
            nbrBallLabel.setText(nbrBallsProperty.get()+" ");
        }else {
            nbrBallLabel.setText("∞ ");
        }

        nbrBallsProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                nbrBallLabel.setText(newValue+" ");
            }
        });

        //-------------------------------------------------------------------------------------------------------
        //NOMBRE DE DEMONS LABEL---------------------------------------------------------------------------------
        if (nDemons > -1){
            nbrDemonsLabel.setText("  "+nbrDemonsMorts.get()+"/"+nDemons+" ");
        }else {
            nbrDemonsLabel.setText("∞ ");
        }

        nbrDemonsMorts.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                nbrDemonsLabel.setText("  "+newValue+"/"+nDemons+" ");
                if ((int)newValue == nDemons){//FIN DE LA PARTIE (TOUT DEMONS MORTS)
                    gameScene.getChildren().add(bravoIMG);
                }
            }
        });
        //-------------------------------------------------------------------------------------------------------

        //METRE L'AFFICHAGE DE NOMBRE DE BALLS ET NBR DE DEMONS AU PREMIER PLAN----------------------------------
        Node n = gameScene.getChildren().get(1);
        gameScene.getChildren().remove(1);
        //PISTOLET
        gameScene.getChildren().add(pistolet.getPistol());
        //TEXT
        gameScene.getChildren().add(n);
        //-------------------------------------------------------------------------------------------------------

        //TIR----------------------------------------------------------------------------------------------------
        //TIR AVEC LE CLAVIER
        GameConfig.getInstance().getFireKey().getPressedProprety().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    if (pistolet.vivant.get()) {
                        if (nbrBallsProperty.get() > 0) {//NOMBRE DE BALLS FINI
                            balls.add(new Ball(pistolet.ballOutXProperty.get(), pistolet.ballOutYProperty.get(), 25));
                            ballColusion(balls.get(balls.size() - 1));
                            gameScene.getChildren().add(balls.get(balls.size() - 1).getBallImageView());
                            balls.get(balls.size() - 1).start();
                            nbrBallsProperty.set(nbrBallsProperty.get() - 1);
                        } else if (nbrBallsProperty.get() == -1) {//NOMBRE DE BALLS INFINI
                            balls.add(new Ball(pistolet.ballOutXProperty.get(), pistolet.ballOutYProperty.get(), 25));
                            ballColusion(balls.get(balls.size() - 1));
                            gameScene.getChildren().add(balls.get(balls.size() - 1).getBallImageView());
                            balls.get(balls.size() - 1).start();
                        }
                    }
                }
            }
        });
        //TIR AVEC LA SOURIS
        gameScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (pistolet.vivant.get()) {
                    if (nbrBallsProperty.get() > 0) {//NOMBRE DE BALLS FINI
                        balls.add(new Ball(pistolet.ballOutXProperty.get(), pistolet.ballOutYProperty.get(), 25));
                        ballColusion(balls.get(balls.size() - 1));
                        gameScene.getChildren().add(balls.get(balls.size() - 1).getBallImageView());
                        balls.get(balls.size() - 1).start();
                        nbrBallsProperty.set(nbrBallsProperty.get() - 1);
                    } else if (nbrBallsProperty.get() == -1) {//NOMBRE DE BALLS INFINI
                        balls.add(new Ball(pistolet.ballOutXProperty.get(), pistolet.ballOutYProperty.get(), 25));
                        ballColusion(balls.get(balls.size() - 1));
                        gameScene.getChildren().add(balls.get(balls.size() - 1).getBallImageView());
                        balls.get(balls.size() - 1).start();
                    }
                }
            }
        });
        //-------------------------------------------------------------------------------------------------------
    }

    //INTERSECTION D'UNE BALLS AVEC LES DEMONS-------------------------------------------------------------------
    private void ballColusion(Ball b){
        Thread ballIntersection= new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<demons.size();i++){
                    int k = i;
                    balls.get(balls.size()-1).getBallImageView().yProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            if (!((Demon)(demons.get(k))).DeadProperty().get()) {
                                if (b.getBallImageView().getBoundsInParent().intersects(((Demon) (demons.get(k))).getDemonImage().getBoundsInLocal())) {
                                    if (!b.isBlocked()) {
                                        b.setBlocked(true);
                                        ((Demon) (demons.get(k))).blesser(b.getDegat());
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
    //-----------------------------------------------------------------------------------------------------------

    public void startGame(){
        partieEnCour.set(true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    demons.add(new Demon(random(0, 1130), -150));
                    gameScene.getChildren().add(demons.get(demons.size() - 1).getDemonImage());
                    gameScene.getChildren().add(demons.get(demons.size() - 1).getVie().getHealthBar());
                    //LORSQUE UN DEMON TOUCHE LE PISTOLET, LE PISTOLET S'EXPLOSE
                    demons.get(demons.size() - 1).getDemonImage().yProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            if (demons.get(demons.size() - 1).intersects(pistolet.getPistol())) {
                                if (pistolet.vivant.get())
                                    pistolet.tuer();
                            }
                        }
                    });
                    //LORSQUE UN DEMON EST TUEE, INCREMENTER LE NBR DE DEMONS MORTS
                    demons.get(demons.size() - 1).DeadProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            if (newValue) {
                                nbrDemonsMorts.set(nbrDemonsMorts.get() + 1);
                            }
                        }
                    });
                    //LORSQUE LE PISTOLET TOUCHE UN DEMON, LE PISTOLET S'EXPLOSE
                    pistolet.getPistol().xProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            if (pistolet.getPistol().intersects(demons.get(demons.size() - 1).getDemonImage().getBoundsInParent())) {
                                if (pistolet.vivant.get()) {
                                    pistolet.tuer();
                                }
                            }
                        }
                    });
                    //LORSQUE LE PISTOLET TOUCHE UN DEMON, LE PISTOLET S'EXPLOSE
                    pistolet.getPistol().yProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            if (pistolet.getPistol().intersects(demons.get(demons.size() - 1).getDemonImage().getBoundsInParent())) {
                                if (pistolet.vivant.get()) {
                                    pistolet.tuer();
                                }
                            }
                        }
                    });

                    demons.get(demons.size() - 1).start();
                    demons.get(demons.size() - 1).getDemonImage().yProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            if (demons.get(demons.size() - 1).intersects(pistolet.getPistol())) {
                                if (pistolet.vivant.get())
                                    pistolet.tuer();
                            }
                        }
                    });
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void chargerPartie(String filePath){

    }

    public void sauvgarderPartie(){

    }

    public void clearScene(){
        GameConfig.getInstance().getPauseKey().getPressedProprety().removeListener(pause);
    }
    private int random(int minIncl,int maxIncl){
        return minIncl + (int)(Math.random() * ((maxIncl - minIncl) + 1));
    }
}
