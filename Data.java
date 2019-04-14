package sample;


import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Data {
    private static Image gameBackgroundIMG;
    private static Image menuBacgroundIMG;
    private static Image pistolDiagonalLeftIMG;
    private static Image pistolDiagonalRightIMG;
    private static Image pistolHorisontalIMG;
    private static Image pistolVerticalIMG;
    private static Image demonMale1IMG;
    private static Image demonFemale1IMG;
    private static Image bulletIMG;
    private static Image gameOverIMG;
    private static Image explosionIMG;
    private static Image bravoIMG;
    private static Dimension2D demonMale1Dimension;
    private static Dimension2D demonFemale1Dimension;

    private static AudioClip ballShootSFX;
    private static AudioClip explosionSFX;
    private static AudioClip gameOverSFX;
    private static MediaPlayer backgroundMusicPlayer;
    private static Media music;
    private static AudioClip gameWinSFX;
    private Data(){
        gameBackgroundIMG = new Image(this.getClass().getResource("Images/background.jpg").toExternalForm());
        menuBacgroundIMG = new Image(this.getClass().getResource("Images/menuBackground.jpg").toExternalForm());
        pistolDiagonalLeftIMG = new Image(this.getClass().getResource("Images/Pistol_Diagonal_Left.png").toExternalForm());
        pistolDiagonalRightIMG = new Image(this.getClass().getResource("Images/Pistol_Diagonal_Right.png").toExternalForm());
        pistolHorisontalIMG = new Image(this.getClass().getResource("Images/Pistol_Horisontal.png").toExternalForm());
        pistolVerticalIMG = new Image(this.getClass().getResource("Images/Pistol_Vertical.png").toExternalForm());
        demonMale1IMG = new Image(this.getClass().getResource("Images/demonM1.gif").toExternalForm());
        demonFemale1IMG = new Image(this.getClass().getResource("Images/demonF1.gif").toExternalForm());
        bulletIMG = new Image(this.getClass().getResource("Images/Bullet.png").toExternalForm());
        gameOverIMG = new Image(this.getClass().getResource("Images/GameOver.png").toExternalForm());
        explosionIMG = new Image(this.getClass().getResource("Images/explosion.gif").toExternalForm());
        bravoIMG = new Image(this.getClass().getResource("Images/bravo.png").toExternalForm());
        demonMale1Dimension = new Dimension2D(100,71);
        demonFemale1Dimension = new Dimension2D(48,72);

        ballShootSFX = new AudioClip(this.getClass().getResource("Sounds/ballShoot.mp3").toString());
        explosionSFX = new AudioClip(this.getClass().getResource("Sounds/explosion.mp3").toString());
        gameOverSFX = new AudioClip(this.getClass().getResource("Sounds/gameOver.mp3").toString());
        gameWinSFX = new AudioClip(this.getClass().getResource("Sounds/gameWin.mp3").toString());

        music = new Media(this.getClass().getResource("Sounds/Cripsy.wav").toString());
        backgroundMusicPlayer = new MediaPlayer(music);


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
    public Image demonMale1IMG(){
        return demonMale1IMG;
    }
    public Image demonFemale1IMG(){return demonFemale1IMG;}
    public Image bulletIMG(){
        return bulletIMG;
    }
    public Image gameOverIMG(){return gameOverIMG;};
    public Image explosionIMG(){return explosionIMG;};
    public Image bravoIMG(){return bravoIMG;};
    public int demonMale1Height(){return (int)demonMale1Dimension.getHeight();}
    public int demonMale1Width(){return (int)demonMale1Dimension.getWidth();}
    public int demonFemale1Height(){return (int)demonFemale1Dimension.getHeight();}
    public int demonFemale1Width(){return (int)demonFemale1Dimension.getWidth();}

    public void ballShootSFX() {
        ballShootSFX.play();
    }
    public void explosionSFX() {
        explosionSFX.play();
    }
    public void backgroundMusic(){
        Thread t =new Thread(new Runnable() {
            @Override
            public void run() {
                backgroundMusicPlayer.setVolume(0.4);
                backgroundMusicPlayer.play();
                backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            }
        });
        t.start();

    }
    public void gameOverSFX(){
        backgroundMusicPlayer.setVolume(0.1);
        gameOverSFX.play();
    }
    public void gameWinSFX(){
        backgroundMusicPlayer.setVolume(0.1);
        gameWinSFX.play();
    }
}
