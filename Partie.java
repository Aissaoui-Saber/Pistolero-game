package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.util.ArrayList;

public class Partie {
    private boolean enCours;
    private Pistol pistolet;
    private int nbrBall;
    private int nbrDemons;
    public IntegerProperty nbrBallsProperty;
    public IntegerProperty nbrDemonsMorts;
    private ArrayList<Demon> demons;
    public ArrayList<Ball> balls;
    private Thread demonGenerator;
    private int demonIterator;

    public Partie(int nBall,int nDemons){
        if (nDemons == -1){
            nbrDemons = nDemons;
            demonIterator = -1;
        }else if (nDemons > 0){
            nbrDemons = nDemons;
            demonIterator = 0;
        }
        pistolet = new Pistol(nBall);
        balls = new ArrayList<Ball>();
        nbrBall = nBall;
        nbrDemonsMorts = new SimpleIntegerProperty(0);
        nbrBallsProperty = new SimpleIntegerProperty(nBall);
        demons = new ArrayList<Demon>();
        initializeDemonsList();
        //COLLISION DEMONS--------------------------------------------------------------------------------------
        for (int i=0;i<demons.size();i++){
            int k = i;
            ((Demon) demons.get(i)).getDemonImage().yProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (((Demon) demons.get(k)).intersects(pistolet.getPistol())){
                        if (pistolet.vivant.get())
                            pistolet.tuer();
                    }
                }
            });
            demons.get(k).DeadProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue){
                        nbrDemonsMorts.set(nbrDemonsMorts.get()+1);
                    }
                }
            });
            pistolet.getPistol().xProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (pistolet.getPistol().intersects(demons.get(k).getDemonImage().getBoundsInParent())){
                        if (pistolet.vivant.get()){
                            pistolet.tuer();
                        }
                    }
                }
            });
            pistolet.getPistol().yProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (pistolet.getPistol().intersects(demons.get(k).getDemonImage().getBoundsInParent())){
                        if (pistolet.vivant.get()){
                            pistolet.tuer();
                        }
                    }
                }
            });
        }
        //------------------------------------------------------------------------------------------------------
        //COLLISION PISTOL--------------------------------------------------------------------------------------

        //------------------------------------------------------------------------------------------------------
    }
    public Partie(String loadFilePath){

    }
    public void lancer(){
        demonGenerator = new Thread(new Runnable() {
            @Override
            public void run() {
                if (demonIterator == -1){//NOMBRE DE DEMON INFINI
                    while (demonGenerator!=null){
                        demons.add(new Demon(random(0,1130),-150));
                        demons.get(demons.size()-1).start();
                        try {
                            Thread.currentThread().sleep(2000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }else if (demonIterator == 0){//NOMBRE DE DEMON FINI
                    while (demonIterator<demons.size() && demonGenerator!=null){
                        demons.get(demonIterator).start();
                        demonIterator++;
                        try {
                            Thread.currentThread().sleep(2000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        demonGenerator.start();
        enCours = true;
    }
    public void pause(){
        demonGenerator = null;
        for(int i=0;i<demons.size();i++){
            demons.get(i).stop();
        }
        enCours = false;
    }
    public void reprendre(){
        enCours = true;
    }
    private void initializeDemonsList(){
        for (int i=0;i<nbrDemons;i++){
            demons.add(new Demon(random(0,1130),-150));
            //demons.add(new Demon(600,600));
        }
    }

    public Pistol getPistolet() {
        return pistolet;
    }

    public ArrayList getDemons() {
        return demons;
    }
    public ArrayList<Ball> getBalls(){
        return this.balls;
    }
    private int random(int minIncl,int maxIncl){
        return minIncl + (int)(Math.random() * ((maxIncl - minIncl) + 1));
    }
    public boolean estEnCours(){
        return this.enCours;
    }
}
