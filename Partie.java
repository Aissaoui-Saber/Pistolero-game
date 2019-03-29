package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Partie {
    private Pistol pistolet;
    private int nbrBall;
    private int nbrDemons;
    private int nbrDemonsMorts;
    private ArrayList<Demon> demons;

    public Partie(int nBall,int nDemons){
        nbrDemons = nDemons;
        pistolet = new Pistol(nBall);
        nbrBall = nBall;
        demons = new ArrayList<Demon>();
        //initializeDemonsList();
    }
    public Partie(String loadFilePath){

    }
    public void lancer(){

    }
    public void pause(){

    }
    private void initializeDemonsList(){
        for (int i=0;i<nbrDemons;i++){
            demons.add(new Demon(random(0,1130),random(0,614)));
        }
    }

    public Pistol getPistolet() {
        return pistolet;
    }

    public ArrayList getDemons() {
        return demons;
    }
    private int random(int minIncl,int maxIncl){
        return minIncl + (int)(Math.random() * ((maxIncl - minIncl) + 1));
    }
}
