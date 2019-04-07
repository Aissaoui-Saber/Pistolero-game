package sample;


import javafx.scene.image.Image;

public class Data {
    private static Image gameBackgroundIMG;
    private static Image menuBacgroundIMG;
    private static Image pistolDiagonalLeftIMG;
    private static Image pistolDiagonalRightIMG;
    private static Image pistolHorisontalIMG;
    private static Image pistolVerticalIMG;
    private static Image demon1IMG;
    private static Image bulletIMG;
    private static Image gameOverIMG;
    private static Image explosionIMG;
    private static Image bravoIMG;

    private Data(){
        gameBackgroundIMG = new Image(this.getClass().getResource("Images/background.jpg").toExternalForm());
        menuBacgroundIMG = new Image(this.getClass().getResource("Images/menuBackground.jpg").toExternalForm());
        pistolDiagonalLeftIMG = new Image(this.getClass().getResource("Images/Pistol_Diagonal_Left.png").toExternalForm());
        pistolDiagonalRightIMG = new Image(this.getClass().getResource("Images/Pistol_Diagonal_Right.png").toExternalForm());
        pistolHorisontalIMG = new Image(this.getClass().getResource("Images/Pistol_Horisontal.png").toExternalForm());
        pistolVerticalIMG = new Image(this.getClass().getResource("Images/Pistol_Vertical.png").toExternalForm());
        demon1IMG = new Image(this.getClass().getResource("Images/demon.gif").toExternalForm());
        bulletIMG = new Image(this.getClass().getResource("Images/Bullet.png").toExternalForm());
        gameOverIMG = new Image(this.getClass().getResource("Images/GameOver.png").toExternalForm());
        explosionIMG = new Image(this.getClass().getResource("Images/explosion.gif").toExternalForm());
        bravoIMG = new Image(this.getClass().getResource("Images/bravo.png").toExternalForm());

    }
    private static Data instance = new Data();
    public static Data getData() {
        if (instance == null)
            return new Data();
        return instance;
    }
    public Image gameBackgroundIMG(){
        return gameBackgroundIMG;
    }
    public Image menuBacgroundIMG(){
        return menuBacgroundIMG;
    }
    public Image pistolDiagonalLeftIMG(){
        return pistolDiagonalLeftIMG;
    }
    public Image pistolDiagonalRightIMG(){
        return pistolDiagonalRightIMG;
    }
    public Image pistolHorisontalIMG(){
        return pistolHorisontalIMG;
    }
    public Image pistolVerticalIMG(){
        return pistolVerticalIMG;
    }
    public Image demon1IMG(){
        return demon1IMG;
    }
    public Image bulletIMG(){
        return bulletIMG;
    }
    public Image gameOverIMG(){return gameOverIMG;};
    public Image explosionIMG(){return explosionIMG;};
    public Image bravoIMG(){return bravoIMG;};
}
