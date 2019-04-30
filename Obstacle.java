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
    public Obstacle(){
    }

    public void addVerticalBoxes(int nbr,int x,int y){
        this.add(new verticalObstacle(nbr));
        this.get(size()-1).setLayoutX(x);
        this.get(size()-1).setLayoutY(y);
    }
    public void addHorizontalBoxes(int nbr,int x,int y){
        this.add(new horizontalObstacle(nbr));
        this.get(size()-1).setLayoutX(x);
        this.get(size()-1).setLayoutY(y);
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
        int rand = Main.randomInt(4,8);
        for (int i=0;i<rand;i++){
            if (i%2==0){
                add(new horizontalObstacle(Main.randomInt(1,5)));
                int y = Main.randomInt(0,10)*Data.getData().boxWidth();
                int x = Main.randomInt(0,50)*Data.getData().boxWidth();
                while (!isFreePlace(x,y)){
                    x = Main.randomInt(0,50)*Data.getData().boxWidth();
                    y = Main.randomInt(0,10)*Data.getData().boxWidth();
                }
                this.get(size()-1).setLayoutY(y);
                this.get(size()-1).setLayoutX(x);
            }else{
                add(new verticalObstacle(Main.randomInt(1,5)));
                int y = Main.randomInt(0,10)*Data.getData().boxWidth();
                int x = Main.randomInt(0,50)*Data.getData().boxWidth();
                while (!isFreePlace(x,y)){
                    x = Main.randomInt(0,50)*Data.getData().boxWidth();
                    y = Main.randomInt(0,10)*Data.getData().boxWidth();
                }
                this.get(size()-1).setLayoutY(y);
                this.get(size()-1).setLayoutX(x);
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
    private boolean isFreePlace(double x,double y){
        int i=0;
        if (x<0 || x>1280 || y<200 || y>500){
            return false;
        }
        while (i<size()-1){
            if (x >= get(i).getLayoutX() && x <= (get(i).getLayoutX()+get(i).getWidth())){
                if (y >= get(i).getLayoutY() && y <= (get(i).getLayoutY()+get(i).getHeight())){
                    return false;
                }else {
                    if (x == get(i).getWidth()+get(i).getLayoutX()){
                        if (y == get(i).getLayoutY()){
                            return false;
                        }else {
                            return true;
                        }
                    }
                    if (y == get(i).getHeight()+get(i).getLayoutY()){
                        if (x == get(i).getLayoutX()){
                            return false;
                        }else {
                            return true;
                        }
                    }
                }
            }
            if (x+get(size()-1).getWidth() >= get(i).getLayoutX() && x+get(size()-1).getWidth() <= (get(i).getLayoutX()+get(i).getWidth())){
                if (y >= get(i).getLayoutY() && y <= (get(i).getLayoutY()+get(i).getHeight())){
                    return false;
                }else {
                    return true;
                }
            }
            if (x >= get(i).getLayoutX() && x <= (get(i).getLayoutX()+get(i).getWidth())){
                if (y+get(size()-1).getHeight() >= get(i).getLayoutY() && y+get(size()-1).getHeight() <= (get(i).getLayoutY()+get(i).getHeight())){
                    return false;
                }else {
                    return true;
                }
            }
            if (x+get(size()-1).getWidth() >= get(i).getLayoutX() && x+get(size()-1).getWidth() <= (get(i).getLayoutX()+get(i).getWidth())){
                if (y+get(size()-1).getHeight() >= get(i).getLayoutY() && y+get(size()-1).getHeight() <= (get(i).getLayoutY()+get(i).getHeight())){
                    return false;
                }else {
                    return true;
                }
            }

            i++;
        }
        return true;
    }

    public class verticalObstacle extends VBox{
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
        public int getNbrOfBoxes(){
            return this.getChildren().size();
        }
    }
    public class horizontalObstacle extends HBox{
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
        public int getNbrOfBoxes(){
            return this.getChildren().size();
        }
    }
}
