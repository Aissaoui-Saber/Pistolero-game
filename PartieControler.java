package sample;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;

import javax.print.attribute.standard.Media;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PartieControler implements Initializable {
    @FXML
    public Pane gameScene;
    @FXML
    private Label nbrBallLabel = new Label();
    @FXML
    private Label nbrDemonsLabel = new Label();

    private Thread demonGenerator;
    private Thread demonsCollision;

    private ImageView gameOverIMG;
    private ImageView bravoIMG;

    private Obstacle obstacles;
    private ArrayList<Demon> demons;
    private ArrayList<Ball> balls;
    private Pistol pistolet;
    private DemonsCollisions demonsCollisions;

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
        balls = new ArrayList<>();
        demons = new ArrayList<>();
        obstacles = new Obstacle(0,200);
        obstacles.randomBoxes();
        for (int i=0;i<obstacles.size();i++){
            gameScene.getChildren().add(obstacles.get(i));
        }
        nbrDemonsMorts = new SimpleIntegerProperty(0);
        nbrBallsProperty = new SimpleIntegerProperty(nBall);
        partieEnCour = new SimpleBooleanProperty(false);
        demonsCollisions = new DemonsCollisions();





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
        pistolet.deadProperty.addListener(new ChangeListener<Boolean>() {
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
                    Data.getData().gameWinSFX();
                    partieEnCour.set(false);
                }
            }
        });
        //-------------------------------------------------------------------------------------------------------





        //METRE L'AFFICHAGE DE NOMBRE DE BALLS ET NBR DE DEMONS AU PREMIER PLAN----------------------------------
        Node node = gameScene.getChildren().get(1);
        gameScene.getChildren().remove(1);
        //PISTOLET
        gameScene.getChildren().add(pistolet);
        //TEXT

        //-------------------------------------------------------------------------------------------------------




        //TIR----------------------------------------------------------------------------------------------------
        //TIR AVEC LE CLAVIER
        GameConfig.getInstance().getFireKey().getPressedProprety().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    if (!pistolet.deadProperty.get()) {
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
                if (!pistolet.deadProperty.get()) {
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
        //-------------------------------------------------------------------------------------------------------





        //INITIALISATION DE LA LISTE DES DEMONS------------------------------------------------------------------
        for (int i=0;i<nDemons;i++){
            int k = i;
            if (Main.randomInt(0,1) == 1){
                demons.add(new Demon(Main.randomInt(0,1130),-200,true));
            }else {
                demons.add(new Demon(Main.randomInt(0,1130),-200,false));
            }
            gameScene.getChildren().add(demons.get(i));
            gameScene.getChildren().add(demons.get(i).getVie().getHealthBar());

            //LORSQUE UN DEMON TOUCHE LE PISTOLET, LE PISTOLET S'EXPLOSE
            demons.get(k).isMovingProperty.addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (demons.get(k).intersects(pistolet)) {
                            if (!pistolet.deadProperty.get()){
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
            pistolet.xProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (pistolet.intersects(demons.get(k).getBoundsInParent())) {
                        if (!pistolet.deadProperty.get()) {
                            if (!demons.get(k).isExplosingProperty.get()){
                                pistolet.tuer();
                                demons.get(k).stop();
                            }
                        }
                    }
                }
            });
            //LORSQUE LE PISTOLET TOUCHE UN DEMON, LE PISTOLET S'EXPLOSE
            pistolet.yProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (pistolet.intersects(demons.get(k).getBoundsInParent())) {
                        if (!pistolet.deadProperty.get()) {
                            if (!demons.get(k).isExplosingProperty.get()){
                                pistolet.tuer();
                                demons.get(k).stop();
                            }
                        }
                    }
                }
            });
            for (int j = 0;j<obstacles.size();j++){
                int s = j;
                //LORSQUE UN DEMON TOUCHE UN OBSTACLE IL CHANGE LA DIRECTION
                demons.get(k).isMovingProperty.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (demons.get(k).getBoundsInParent().intersects(obstacles.get(s).getBoundsInParent())) {
                            demons.get(k).changeYdirection();
                        }

                    }
                });
            }
        }
        //SUPPRIMER LE PISTOLET DE LA SCENE APRES L'EXPLOSION
        pistolet.deadProperty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue.booleanValue()){
                    gameScene.getChildren().remove(pistolet);
                }
            }
        });
        gameScene.getChildren().add(node);
        //-------------------------------------------------------------------------------------------------------





        //COLLISION DE PISTOLET AVEC LES OBSTACLES---------------------------------------------------------------
        for (int i=0;i<obstacles.size();i++){
            int k = i;
            //LORSQUE LE PISTOLET TOUCHE UN DEMON, LE PISTOLET S'EXPLOSE
            pistolet.xProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                }
            });
            //LORSQUE LE PISTOLET TOUCHE UN DEMON, LE PISTOLET S'EXPLOSE
            pistolet.yProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                }
            });
        }
        //-------------------------------------------------------------------------------------------------------





        //COLLISION DES DEMONS ENTRE EUX-------------------------------------------------------------------------
        for (int i=0;i<demons.size();i++){
            for(int j=0;j<demons.size();j++){
                int m = i;
                int n = j;
                if (i!=j){
                    demons.get(i).isMovingProperty.addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            if (demons.get(m).getBoundsInParent().intersects(demons.get(n).getBoundsInParent())){
                                if (demons.get(m).isMale() && demons.get(n).isMale()){
                                    if (!demons.get(m).isExplosingProperty.get()){
                                        if (!demonsCollisions.exists(m,n)){
                                            demonsCollisions.add(m,n);
                                            demonsCollisions.destroyOneDemon(demons);
                                        }
                                    }
                                }else if (demons.get(m).isMale() && !demons.get(n).isMale()){
                                    if (!demons.get(m).isExplosingProperty.get()){
                                        if (!demonsCollisions.exists(m,n)){
                                            demonsCollisions.add(m,n);
                                            //demons.get(m).changeDirection();
                                            //demons.get(n).changeDirection();
                                            demonsCollisions.remove(m,n);

                                            /*if (randomInt(0,1) == 1){
                                                demons.add(new Demon((int)demons.get(m).getX(),(int)demons.get(m).getY(),true));
                                            }else {
                                                demons.add(new Demon((int)demons.get(m).getX(),(int)demons.get(m).getY(),false));
                                            }
                                            gameScene.getChildren().add(demons.get(demons.size()-1));
                                            gameScene.getChildren().add(demons.get(demons.size()-1).getVie().getHealthBar());
                                            demons.get(demons.size()-1).start();*/
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
        //-------------------------------------------------------------------------------------------------------
    }




    //INTERSECTION D'UNE BALLS AVEC LES DEMONS ET LES OBSTACLES----------------------------------------------------
    private void ballColusion(Ball b){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<demons.size();i++){
                    int k = i;
                    balls.get(balls.size()-1).yProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            if (!demons.get(k).isDeadProperty.get() && !demons.get(k).isExplosingProperty.get()) {
                                if (b.getBoundsInParent().intersects(demons.get(k).getBoundsInLocal())) {
                                    if (!b.blocked.get()) {
                                        b.blocked.set(true);
                                        demons.get(k).blesser(b.getDegat());
                                        b.stop();
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<obstacles.size();i++){
                    int k = i;
                    balls.get(balls.size()-1).yProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            if (b.getBoundsInParent().intersects(obstacles.get(k).getBoundsInLocal())) {
                                if (!b.blocked.get()) {
                                    b.blocked.set(true);
                                    b.stop();
                                }
                            }
                        }
                    });
                }
            }
        });
        t1.start();
        t2.start();
    }
    //-----------------------------------------------------------------------------------------------------------






    public void startGame(){
        partieEnCour.set(true);
        Data.getData().backgroundMusic();

        demonGenerator = new Thread(new Runnable() {
            @Override
            public void run() {
                int f = 0;
                while (partieEnCour.get() && f<demons.size()) {
                    demons.get(f).start();
                    try {
                        Thread.sleep(1000);
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
}