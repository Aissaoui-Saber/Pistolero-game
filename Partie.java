package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Partie {
    private Pistol pistolet;
    private int nbrBall;
    private int nbrDemons;
    public IntegerProperty nbrDemonsMorts;
    private ArrayList<Demon> demons;
    public ArrayList<Ball> balls;

    public Partie(int nBall,int nDemons){
        nbrDemons = nDemons;
        pistolet = new Pistol(nBall);
        balls = new ArrayList<Ball>();
        //b = new Ball(0,0);
        //b.hide();
        nbrBall = nBall;
        nbrDemonsMorts = new SimpleIntegerProperty(0);
        demons = new ArrayList<Demon>();
        initializeDemonsList();
        //COLLISION DEMONS--------------------------------------------------------------------------------------
        for (int i=0;i<demons.size();i++){
            int k = i;
            ((Demon) demons.get(i)).getImage().yProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (((Demon) demons.get(k)).intersects(pistolet.getPistolImage())){
                        pistolet.blesser(100);
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
        }
        //------------------------------------------------------------------------------------------------------
    }
    public Partie(String loadFilePath){

    }
    public void lancer(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i<demons.size()){
                    demons.get(i).start();
                    i++;
                    try {
                        Thread.currentThread().sleep(2000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }
    public void pause(){

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
}
