package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.ArrayList;

import static javafx.util.Duration.millis;


public class Obstacle extends ArrayList<Pane> {
    private int positionX,positionY;
    public Obstacle(int x,int y){
        positionX = x;
        positionY = y;
    }
    public void addVerticalBoxes(int nbr){
        this.add(new verticalObstacle(nbr));
    }
    public void addHorizontalBoxes(int nbr){
        this.add(new horizontalObstacle(nbr));
    }

    /*public void addBox(int ligne,int colonne){
        this.add(new ImageView(Data.getData().boxIMG()));
        this.get(this.size()-1).setFitWidth(boxWidth);
        this.get(this.size()-1).setFitHeight(boxHeight);
        this.get(this.size()-1).setY(ligne*30+positionY);
        this.get(this.size()-1).setX(colonne*30+positionX);
    }
    public void add(int x,int y){
        this.add(new ImageView(Data.getData().boxIMG()));
        this.get(this.size()-1).setFitWidth(boxWidth);
        this.get(this.size()-1).setFitHeight(boxHeight);
        this.get(this.size()-1).setY(x);
        this.get(this.size()-1).setX(y);
    }*/
    public void randomBoxes(){
        /*boolean transparent;
        int nbr;
        transparent = Main.randomInt(0, 1) == 1;
        for (int i=0;i<7;i++){
            nbr = 0;
            if ((i%6) == 0) {
                for (int j = 0; j < (1280 / Data.getData().boxWidth()); j++) {
                    if (transparent) {
                        if (nbr > 0) {
                            nbr--;
                        } else {
                            this.add(new horizontalObstacle());
                            ((horizontalObstacle)this.get(size()-1)).addBox();
                            this.get(size()-1).setLayoutY(i*Data.getData().boxWidth()+positionY);
                            this.get(size()-1).setLayoutX(j*Data.getData().boxWidth()+positionX);
                            transparent = false;
                            nbr = Main.randomInt(2, 4);
                        }
                    } else {
                        if (nbr > 0) {
                            ((horizontalObstacle)this.get(size()-1)).addBox();
                            nbr--;
                        } else {
                            transparent = true;
                            nbr = Main.randomInt(2, 4);
                        }
                    }
                }
            }
        }*/
        for (int i=0;i<20;i++){
            if (i%2==0){
                add(new horizontalObstacle(Main.randomInt(1,5)));
                this.get(size()-1).setLayoutY(Main.randomInt(0,10)*Data.getData().boxWidth()+positionY);
                this.get(size()-1).setLayoutX(Main.randomInt(0,50)*Data.getData().boxWidth()+positionX);
            }else{
                add(new verticalObstacle(Main.randomInt(1,5)));
                this.get(size()-1).setLayoutY(Main.randomInt(0,10)*Data.getData().boxWidth()+positionY);
                this.get(size()-1).setLayoutX(Main.randomInt(0,50)*Data.getData().boxWidth()+positionX);
            }
        }
    }
    public void playEffect(int f){
        KeyFrame begin = new KeyFrame(Duration.ZERO,new KeyValue(get(f).effectProperty(), new Bloom()));
        KeyFrame end = new KeyFrame(millis(30),new KeyValue(get(f).effectProperty(),null));
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(begin,end);
        timeline.play();
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    private class verticalObstacle extends VBox{
        public verticalObstacle(){
            this.setWidth(Data.getData().boxWidth());
        }
        public verticalObstacle(int nbrOfBoxes){
            for (int i=0;i<nbrOfBoxes;i++){
                ImageView box = new ImageView();
                box.setImage(Data.getData().boxIMG());
                box.setFitWidth(Data.getData().boxWidth());
                box.setFitHeight(Data.getData().boxHeight());
                getChildren().add(box);
            }
            this.setWidth(Data.getData().boxWidth());
            this.setHeight(Data.getData().boxHeight()*nbrOfBoxes);
        }
        public void addBox(){
            ImageView box = new ImageView();
            box.setImage(Data.getData().boxIMG());
            box.setFitWidth(Data.getData().boxWidth());
            box.setFitHeight(Data.getData().boxHeight());
            getChildren().add(box);
            this.setHeight(this.getHeight()+Data.getData().boxHeight());
        }
    }
    private class horizontalObstacle extends HBox{
        public horizontalObstacle(){
            this.setHeight(Data.getData().boxHeight());
        }
        public horizontalObstacle(int nbrOfBoxes){
            for (int i=0;i<nbrOfBoxes;i++){
                ImageView box = new ImageView();
                box.setImage(Data.getData().boxIMG());
                box.setFitWidth(Data.getData().boxWidth());
                box.setFitHeight(Data.getData().boxHeight());
                getChildren().add(box);
            }
            this.setHeight(Data.getData().boxHeight());
            this.setWidth(Data.getData().boxWidth()*nbrOfBoxes);
        }
        public void addBox(){
            ImageView box = new ImageView();
            box.setImage(Data.getData().boxIMG());
            box.setFitWidth(Data.getData().boxWidth());
            box.setFitHeight(Data.getData().boxHeight());
            getChildren().add(box);
            this.setWidth(this.getWidth()+Data.getData().boxWidth());
        }
    }
}
