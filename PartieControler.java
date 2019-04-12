package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

public class PartieControler implements Initializable {
    @FXML
    public Pane gameScene;
    @FXML
    private Label nbrBallLabel = new Label();
    @FXML
    private Label nbrDemonsLabel = new Label();

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
                partieEnCour.set(false);
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
        nbrDemonsLabel.setText("  "+nbrDemonsMorts.get()+"/"+nDemons+" ");
        nbrDemonsMorts.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                nbrDemonsLabel.setText("  "+newValue+"/"+nDemons+" ");
                if ((int)newValue == nDemons){//FIN DE LA PARTIE (TOUT DEMONS MORTS)
                    gameScene.getChildren().add(bravoIMG);
                    partieEnCour.set(false);
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
                            gameScene.getChildren().add(balls.get(balls.size() - 1));
                            balls.get(balls.size() - 1).start();
                            nbrBallsProperty.set(nbrBallsProperty.get() - 1);
                        } else if (nbrBallsProperty.get() == -1) {//NOMBRE DE BALLS INFINI
                            balls.add(new Ball(pistolet.ballOutXProperty.get(), pistolet.ballOutYProperty.get(), 25));
                            ballColusion(balls.get(balls.size() - 1));
                            gameScene.getChildren().add(balls.get(balls.size() - 1));
                            balls.get(balls.size() - 1).start();
                        }
                        if (balls.size()>0){
                            if (balls.get(0).blocked.get()){
                                balls.remove(0);
                            }
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
                        gameScene.getChildren().add(balls.get(balls.size() - 1));
                        balls.get(balls.size() - 1).start();
                        nbrBallsProperty.set(nbrBallsProperty.get() - 1);
                    } else if (nbrBallsProperty.get() == -1) {//NOMBRE DE BALLS INFINI
                        balls.add(new Ball(pistolet.ballOutXProperty.get(), pistolet.ballOutYProperty.get(), 25));
                        ballColusion(balls.get(balls.size() - 1));
                        gameScene.getChildren().add(balls.get(balls.size() - 1));
                        balls.get(balls.size() - 1).start();
                    }
                    if (balls.get(0).blocked.get()){
                        balls.remove(0);
                    }
                }
            }
        });

        //NETOYAGE DES BALLS

        //-------------------------------------------------------------------------------------------------------
        //INITIALISATION DE LA LISTE DES DEMONS------------------------------------------------------------------
        for (int i=0;i<nDemons;i++){
            int k = i;
            if (randomInt(0,1) == 1){
                demons.add(new Demon(randomInt(0,1130),-200,true));
            }else {
                demons.add(new Demon(randomInt(0,1130),-200,false));
            }
            gameScene.getChildren().add(demons.get(i));
            gameScene.getChildren().add(demons.get(i).getVie().getHealthBar());

            //LORSQUE UN DEMON TOUCHE LE PISTOLET, LE PISTOLET S'EXPLOSE
            demons.get(k).yProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (demons.get(k).intersects(pistolet.getPistol())) {
                        if (pistolet.vivant.get()){
                            demons.get(k).stop();
                            pistolet.tuer();
                        }

                    }
                }
            });
            demons.get(k).xProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (demons.get(k).intersects(pistolet.getPistol())) {
                        if (pistolet.vivant.get()){
                            demons.get(k).stop();
                            pistolet.tuer();
                        }
                    }
                }
            });
            //LORSQUE UN DEMON EST TUEE, INCREMENTER LE NBR DE DEMONS MORTS
            demons.get(k).isDeadProperty.addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue) {
                        nbrDemonsMorts.set(nbrDemonsMorts.get() + 1);
                        demons.get(k).yProperty().set(-500);
                        gameScene.getChildren().remove(demons.get(k));
                        gameScene.getChildren().remove(demons.get(k).getVie().getHealthBar());
                    }
                }
            });
            //LORSQUE LE PISTOLET TOUCHE UN DEMON, LE PISTOLET S'EXPLOSE
            pistolet.getPistol().xProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (pistolet.getPistol().intersects(demons.get(k).getBoundsInParent())) {
                        if (pistolet.vivant.get()) {
                            if (!demons.get(k).isExplosing.get()){
                                pistolet.tuer();
                                demons.get(k).stop();
                            }
                        }
                    }
                }
            });
            //LORSQUE LE PISTOLET TOUCHE UN DEMON, LE PISTOLET S'EXPLOSE
            pistolet.getPistol().yProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (pistolet.getPistol().intersects(demons.get(k).getBoundsInParent())) {
                        if (pistolet.vivant.get()) {
                            if (!demons.get(k).isExplosing.get()){
                                pistolet.tuer();
                                demons.get(k).stop();
                            }
                        }
                    }
                }
            });
        }
        gameScene.getChildren().add(n);
        //-------------------------------------------------------------------------------------------------------
    }

    //INTERSECTION D'UNE BALLS AVEC LES DEMONS-------------------------------------------------------------------
    private void ballColusion(Ball b){
        for(int i=0;i<demons.size();i++){
            int k = i;
            balls.get(balls.size()-1).yProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (!((Demon)(demons.get(k))).isDeadProperty.get() && !((Demon)(demons.get(k))).isExplosing.get()) {
                        if (b.getBoundsInParent().intersects(((Demon) (demons.get(k))).getBoundsInLocal())) {
                            if (!b.blocked.get()) {
                                demons.get(k).effectProperty().set(new SepiaTone());
                                b.blocked.set(true);
                                ((Demon) (demons.get(k))).blesser(b.getDegat());
                                b.stop();
                            }
                        }
                    }
                }
            });
        }
    }
    //-----------------------------------------------------------------------------------------------------------

    public void startGame(){
        partieEnCour.set(true);
        demonGenerator = new Thread(new Runnable() {
            @Override
            public void run() {
                int f = 0;
                while (partieEnCour.get()) {
                    demons.get(f).start();
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    f++;
                }
            }
        });
        demonGenerator.start();
    }

    public void chargerPartie(String filePath){

    }

    public void sauvgarderPartie(){

    }

    public void clearScene(){
        GameConfig.getInstance().getPauseKey().getPressedProprety().removeListener(pause);
    }
    private int randomInt(int minIncl,int maxIncl){
        return minIncl + (int)(Math.random() * ((maxIncl - minIncl) + 1));
    }
    private double randomDouble(double minIncl,double maxIncl){
        return minIncl + (Math.random() * ((maxIncl - minIncl) + 1));
    }
}