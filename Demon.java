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
        switch (direction){
            case 0:
                if ((image.getY() - 1)>-1){
                    //Main.animateMouvment(this.image, 0,-10);//NORD
                    this.image.setY(this.image.getY()-1);
                }
                break;
            case 1:
                Main.animateMouvment(this.image,10,-10);//NORD-EST
                break;
            case 2:
                Main.animateMouvment(this.image,10,0 );//EST
                break;
            case 3:
                Main.animateMouvment(this.image,10,10);//SUD-EST
                break;
            case 4:
                Main.animateMouvment(this.image,0,10);//SUD
                break;
            case 5:
                Main.animateMouvment(this.image,-10,10);//SUD-OUEST
                break;
            case 6:
                if(image.getX()>0){
                    Main.animateMouvment(this.image,-10,0);//OUEST
                }
                break;
            case 7:
                Main.animateMouvment(this.image,-10,-10);//NORD-OUEST
                break;
        }
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
