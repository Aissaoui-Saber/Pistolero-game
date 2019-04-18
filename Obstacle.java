package sample;

import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;


public class Obstacle extends ArrayList<ImageView> {
    private int boxWidth,boxHeight,positionX,positionY;
    public Obstacle(int x,int y){
        positionX = x;
        positionY = y;
        boxHeight = 40;
        boxWidth = 40;
    }
    public void addBox(int ligne,int colonne){
        this.add(new ImageView(Data.getData().boxIMG()));
        this.get(this.size()-1).setFitWidth(boxWidth);
        this.get(this.size()-1).setFitHeight(boxHeight);
        this.get(this.size()-1).setY(ligne*40+positionY);
        this.get(this.size()-1).setX(colonne*40+positionX);
    }
    public void add(int x,int y){
        this.add(new ImageView(Data.getData().boxIMG()));
        this.get(this.size()-1).setFitWidth(boxWidth);
        this.get(this.size()-1).setFitHeight(boxHeight);
        this.get(this.size()-1).setY(x);
        this.get(this.size()-1).setX(y);
    }
    public void randomBoxes(){
        boolean transparent;
        int nbr = Main.randomInt(2,4);
        transparent = Main.randomInt(0, 1) == 1;
        for (int i=0;i<9;i++){
            nbr = 0;
            for (int j=0;j<(1280/boxWidth);j++){
                if ((i%4) == 0){
                    if (transparent){
                        if (nbr>0){
                            nbr--;
                        }else {
                            addBox(i,j);
                            transparent = false;
                            nbr = Main.randomInt(2,4);
                        }
                    }else {
                        if (nbr>0){
                            addBox(i,j);
                            nbr--;
                        }else {
                            transparent = true;
                            nbr = Main.randomInt(2,4);
                        }
                    }
                }
            }
        }
    }

    public void setBoxHeight(int boxHeight) {
        this.boxHeight = boxHeight;
    }

    public void setBoxWidth(int boxWidth) {
        this.boxWidth = boxWidth;
    }

    public int getBoxHeight() {
        return boxHeight;
    }

    public int getBoxWidth() {
        return boxWidth;
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
}
