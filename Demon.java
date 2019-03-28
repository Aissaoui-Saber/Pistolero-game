package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Demon{
    private double vie;
    private boolean sexe;
    private double degat;
    private ImageView image;
    private Image img;

    public Demon(int x,int y){
        image = new ImageView();
        img = new Image(this.getClass().getResource("Images/demon.gif").toExternalForm());
        image.setImage(img);
        image.setFitWidth(150);
        image.setFitHeight(106);
        image.setX(x);
        image.setY(y);
        vie = 100;
        sexe = true;
        degat = 20;
    }


    public ImageView getImage() {
        return image;
    }
    public void detruir(){
        this.image.setVisible(false);
    }

    public void deplacer(int direction){

    }
    public boolean isDead(){
        if (this.vie > 0){
            return false;
        }else {
            return true;
        }
    }
    public void blesser(int value){
        this.vie-=value;
    }
}
