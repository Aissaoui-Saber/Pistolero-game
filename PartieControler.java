package sample;

import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.naming.Binding;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PartieControler implements Initializable {
    @FXML
    public Pane gameScene;
    public ImageView backIMG;
    public HBox hbox;
    @FXML
    private Label nbrBallLabel = new Label();
    @FXML
    private Label nbrDemonsLabel = new Label();


    private Runnable demonGenerator;

    private ImageView gameOverIMG;
    private ImageView bravoIMG;

    private Obstacle obstacles;
    private ArrayList<Demon> demons;
    private ArrayList<Ball> balls;
    private Pistol pistolet;
    private DemonsCollisions demonsCollisions;
    private int nbrBallsTotal;
    private IntegerProperty nbrDemonsTotal;
    private int demonsIterator;

    public IntegerProperty nbrBallsProperty;
    public IntegerProperty nbrDemonsMorts;

    public BooleanProperty partieFini;
    public BooleanProperty partieEnPause;

    private EventHandler<KeyEvent> keyPress;
    private EventHandler<KeyEvent> keyReleased;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        keyPress = event -> {
            if (!pistolet.isExplosingProperty.get()) {
                if (event.getCode() == GameConfig.getInstance().getUpKey().getCode()) {
                    GameConfig.getInstance().getUpKey().setPressed();
                    if (GameConfig.getInstance().getLeftKey().isPressed()) {//UP + LEFT
                        pistolet.setImage(Data.getData().pistolDiagonalLeftIMG());

                    } else if (GameConfig.getInstance().getRightKey().isPressed()) {//UP + RIGHT
                        pistolet.setImage(Data.getData().pistolDiagonalRightIMG());

                    } else {//UP
                        pistolet.setImage(Data.getData().pistolVerticalIMG());

                    }
                }
                if (event.getCode() == GameConfig.getInstance().getDownKey().getCode()) {
                    GameConfig.getInstance().getDownKey().setPressed();
                    if (GameConfig.getInstance().getLeftKey().isPressed()) {//DOWN + LEFT
                        pistolet.setImage(Data.getData().pistolDiagonalRightIMG());

                    } else if (GameConfig.getInstance().getRightKey().isPressed()) {//DOWN + RIGHT
                        pistolet.setImage(Data.getData().pistolDiagonalLeftIMG());

                    } else {
                        pistolet.setImage(Data.getData().pistolVerticalIMG());//DOWN

                    }
                }
                if (event.getCode() == GameConfig.getInstance().getLeftKey().getCode()) {
                    GameConfig.getInstance().getLeftKey().setPressed();
                    if (GameConfig.getInstance().getUpKey().isPressed()) {//LEFT + UP
                        pistolet.setImage(Data.getData().pistolDiagonalLeftIMG());

                    } else if (GameConfig.getInstance().getDownKey().isPressed()) {//LEFT + DOWN
                        pistolet.setImage(Data.getData().pistolDiagonalRightIMG());

                    } else {
                        pistolet.setImage(Data.getData().pistolHorisontalIMG());//LEFT

                    }
                }
                if (event.getCode() == GameConfig.getInstance().getRightKey().getCode()) {
                    GameConfig.getInstance().getRightKey().setPressed();
                    if (GameConfig.getInstance().getUpKey().isPressed()) {//RIGHT + UP
                        pistolet.setImage(Data.getData().pistolDiagonalRightIMG());

                    } else if (GameConfig.getInstance().getDownKey().isPressed()) {//RIGHT + DOWN
                        pistolet.setImage(Data.getData().pistolDiagonalLeftIMG());

                    } else {
                        pistolet.setImage(Data.getData().pistolHorisontalIMG());//RIGHT

                    }
                }
                if (event.getCode() == GameConfig.getInstance().getFireKey().getCode()) {
                    GameConfig.getInstance().getFireKey().setPressed();
                }
            }
            if (event.getCode() == KeyCode.ESCAPE){
                Main.rootControler.goToMenuScene();
                pause();
            }
        };
        keyReleased = event -> {
            if (!pistolet.isExplosingProperty.get()) {
                if (event.getCode() == GameConfig.getInstance().getUpKey().getCode()) {
                    GameConfig.getInstance().getUpKey().setReleased();
                    if (GameConfig.getInstance().getLeftKey().isPressed()) {
                        pistolet.setImage(Data.getData().pistolDiagonalLeftIMG());
                        pistolet.movingDirection = 7;
                    } else if (GameConfig.getInstance().getRightKey().isPressed()) {
                        pistolet.setImage(Data.getData().pistolDiagonalRightIMG());
                        pistolet.movingDirection = 1;
                    } else {
                        pistolet.setImage(Data.getData().pistolVerticalIMG());
                        pistolet.movingDirection = 0;
                    }
                }
                if (event.getCode() == GameConfig.getInstance().getDownKey().getCode()) {
                    GameConfig.getInstance().getDownKey().setReleased();
                    if (GameConfig.getInstance().getLeftKey().isPressed()) {
                        pistolet.setImage(Data.getData().pistolDiagonalRightIMG());
                        pistolet.movingDirection = 5;
                    } else if (GameConfig.getInstance().getRightKey().isPressed()) {
                        pistolet.setImage(Data.getData().pistolDiagonalLeftIMG());
                        pistolet.movingDirection = 3;
                    } else {
                        pistolet.setImage(Data.getData().pistolVerticalIMG());
                        pistolet.movingDirection = 4;
                    }
                }
                if (event.getCode() == GameConfig.getInstance().getLeftKey().getCode()) {
                    GameConfig.getInstance().getLeftKey().setReleased();
                    if (GameConfig.getInstance().getUpKey().isPressed()) {
                        pistolet.setImage(Data.getData().pistolDiagonalLeftIMG());
                        pistolet.movingDirection = 7;
                    } else if (GameConfig.getInstance().getDownKey().isPressed()) {
                        pistolet.setImage(Data.getData().pistolDiagonalRightIMG());
                        pistolet.movingDirection = 5;
                    } else {
                        pistolet.setImage(Data.getData().pistolHorisontalIMG());
                        pistolet.movingDirection = 6;
                    }
                }
                if (event.getCode() == GameConfig.getInstance().getRightKey().getCode()) {
                    GameConfig.getInstance().getRightKey().setReleased();
                    if (GameConfig.getInstance().getUpKey().isPressed()) {
                        pistolet.setImage(Data.getData().pistolDiagonalRightIMG());
                        pistolet.movingDirection = 1;
                    } else if (GameConfig.getInstance().getDownKey().isPressed()) {
                        pistolet.setImage(Data.getData().pistolDiagonalLeftIMG());
                        pistolet.movingDirection = 3;
                    } else {
                        pistolet.setImage(Data.getData().pistolHorisontalIMG());
                        pistolet.movingDirection = 2;
                    }
                }
                if (event.getCode() == GameConfig.getInstance().getFireKey().getCode()) {
                    GameConfig.getInstance().getFireKey().setReleased();
                }
                pistolet.setImage(Data.getData().pistolVerticalIMG());
            }
        };
        gameScene.setOnKeyPressed(keyPress);
        gameScene.setOnKeyReleased(keyReleased);

        //TIR----------------------------------------------------------------------------------------------------
        //TIR AVEC LE CLAVIER
        GameConfig.getInstance().getFireKey().getPressedProprety().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                if (!pistolet.deadProperty.get()) {
                    if (nbrBallsProperty.get() > 0) {//NOMBRE DE BALLS FINI
                        ballColusion(new Ball(pistolet.ballOutXProperty.get(), pistolet.ballOutYProperty.get(), 25));
                        nbrBallsProperty.set(nbrBallsProperty.get() - 1);
                    } else if (nbrBallsProperty.get() == -1) {//NOMBRE DE BALLS INFINI
                        ballColusion(new Ball(pistolet.ballOutXProperty.get(), pistolet.ballOutYProperty.get(), 25));
                    }
                    if (balls.size()>0){//NETOYAGE DES BALLS
                        if (balls.get(0).blocked.get()){
                            balls.remove(0);
                        }
                    }
                }
            }
        });
        //TIR AVEC LA SOURIS
        gameScene.setOnMouseClicked(event -> {
            if (!pistolet.deadProperty.get()) {
                if (nbrBallsProperty.get() > 0) {//NOMBRE DE BALLS FINI
                    ballColusion(new Ball(pistolet.ballOutXProperty.get(), pistolet.ballOutYProperty.get(), 25));
                    nbrBallsProperty.set(nbrBallsProperty.get() - 1);
                } else if (nbrBallsProperty.get() == -1) {//NOMBRE DE BALLS INFINI
                    ballColusion(new Ball(pistolet.ballOutXProperty.get(), pistolet.ballOutYProperty.get(), 25));
                }
                if (balls.size()>0){//NETOYAGE DES BALLS
                    if (balls.get(0).blocked.get()){
                        balls.remove(0);
                    }
                }
            }
        });
        //-------------------------------------------------------------------------------------------------------


        //TOUCHE DE MOUVEMENTS DE PISTOLET
        GameConfig.getInstance().getUpKey().getPressedProprety().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                pistolet.upTimer.start();
            }else {
                pistolet.upTimer.stop();
            }
        });
        GameConfig.getInstance().getDownKey().getPressedProprety().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                pistolet.downTimer.start();
            }else {
                pistolet.downTimer.stop();
            }
        });
        GameConfig.getInstance().getLeftKey().getPressedProprety().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                pistolet.leftTimer.start();
            }else {
                pistolet.leftTimer.stop();
            }
        });
        GameConfig.getInstance().getRightKey().getPressedProprety().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                pistolet.rightTimer.start();
            }else {
                pistolet.rightTimer.stop();
            }
        });
        //-------------------------------------------------------------------------------------------------------
        demonGenerator = () -> {
            int f = 0;
            while (!partieEnPause.get() && !partieFini.get()) {
                if (demonsIterator == nbrDemonsTotal.get()){
                    demonsIterator = 0;
                }
                if (!demons.get(demonsIterator).isDeadProperty.get()) {
                    demons.get(demonsIterator).start();
                    try {
                        Thread.sleep(2000);
                        f++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (f == 5){
                    for (int i=0;i<nbrDemonsTotal.get();i++){
                        if (demons.get(i).getY()>0){
                            demons.get(i).setRandomSpeedX();
                            demons.get(i).setRandomSpeedY();
                            demons.get(i).obstacleColisionIndex = -1;
                        }
                    }
                    f = 0;
                }
                demonsIterator++;
            }
        };
    }
    public void nouvellePartie(int nBall,int nDemons,boolean load){
        if (pistolet != null){
            pistolet = null;
        }
        if (balls != null){
            balls.clear();
            balls = null;
        }
        if (demons != null){
            demons.clear();
            demons = null;
        }
        if (obstacles != null){
            obstacles.clear();
            obstacles = null;
        }
        if (demonsCollisions != null){
            demonsCollisions.clear();
            demonsCollisions = null;
        }
        if  (gameScene != null){
            gameScene.getChildren().clear();
            gameScene.getChildren().add(backIMG);
            gameScene.getChildren().add(hbox);
            System.gc();
        }
        if (load){
            chargerPartie();
            reprendre();
        }else{
            pistolet = new Pistol(600,600);
            balls = new ArrayList<>();
            demons = new ArrayList<>();
            obstacles = new Obstacle();
            obstacles.randomBoxes();
            for (Pane obstacle : obstacles) {
                gameScene.getChildren().add(obstacle);
            }
            nbrBallsTotal = nBall;
            nbrDemonsTotal = new SimpleIntegerProperty(nDemons);
            demonsIterator = 0;
            nbrDemonsMorts = new SimpleIntegerProperty(0);
            nbrBallsProperty = new SimpleIntegerProperty(nBall);
            partieFini = new SimpleBooleanProperty(false);
            partieEnPause = new SimpleBooleanProperty(true);
            demonsCollisions = new DemonsCollisions();
            for (int i=0;i<nbrDemonsTotal.get();i++){
                if (Main.randomInt(0,1) == 1){
                    demons.add(new Demon(Main.randomInt(0,1130),-200,true,100));
                }else {
                    demons.add(new Demon(Main.randomInt(0,1130),-200,false,100));
                }
                gameScene.getChildren().add(demons.get(i));
                gameScene.getChildren().add(demons.get(i).getVie().getHealthBar());
                demonIntersection(i);
            }
            startGame();
        }


        //FIN DE LA PARTIE (GAME OVER / BRAVO)---------------------------------------------------------------------
        gameOverIMG = new ImageView(Data.getData().gameOverIMG());
        gameOverIMG.setFitWidth(400);
        gameOverIMG.setFitHeight(225);
        gameOverIMG.setY(247);
        gameOverIMG.setX(440);

        bravoIMG = new ImageView(Data.getData().bravoIMG());
        bravoIMG.setX(452);
        bravoIMG.setY(172);



        //QUAND LE PISTOLET EST MORT, AFFICHER GAME OVER
        pistolet.deadProperty.addListener((observable, oldValue, newValue) -> {
            gameScene.getChildren().add(gameOverIMG);//AFFICHER GAME OVER
            partieFini.set(true);
            gameScene.requestFocus();
        });
        //--------------------------------------------------------------------------------------------------------



        //NOMBRE DE BALLS LABEL-----------------------------------------------------------------------------------
        /*if (nbrBallsProperty.get() == -1){
            nbrBallLabel.setText("∞ ");
        }*/
        nbrBallLabel.textProperty().bind(nbrBallsProperty.asString());
        //-------------------------------------------------------------------------------------------------------




        //NOMBRE DE DEMONS LABEL---------------------------------------------------------------------------------
        nbrDemonsLabel.setText("  "+nbrDemonsMorts.get()+"/"+nbrDemonsTotal.get()+" ");
        nbrDemonsMorts.addListener((observable, oldValue, newValue) -> {
            nbrDemonsLabel.setText("  "+newValue+"/"+nbrDemonsTotal.get()+" ");
            if ((int)newValue == nbrDemonsTotal.get()){//FIN DE LA PARTIE (TOUT DEMONS MORTS)
                gameScene.getChildren().add(bravoIMG);
                Data.getData().gameWinSFX();
                partieFini.set(true);
            }
        });
        nbrDemonsTotal.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                nbrDemonsLabel.setText("  "+nbrDemonsMorts.get()+"/"+newValue+" ");
            }
        });
        //-------------------------------------------------------------------------------------------------------





        //METRE L'AFFICHAGE DE NOMBRE DE BALLS ET NBR DE DEMONS AU PREMIER PLAN----------------------------------
        Node node = gameScene.getChildren().get(1);
        gameScene.getChildren().remove(1);
        //PISTOLET
        gameScene.getChildren().add(pistolet);
        //TEXT

        //-------------------------------------------------------------------------------------------------------








        //SUPPRIMER LE PISTOLET DE LA SCENE APRES L'EXPLOSION----------------------------------------------------
        pistolet.deadProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue){
                gameScene.getChildren().remove(pistolet);
            }
        });

        //-------------------------------------------------------------------------------------------------------





        //COLLISION DE PISTOLET AVEC LES OBSTACLES---------------------------------------------------------------
        for (int i=0;i<obstacles.size();i++){
            int k = i;
            pistolet.xProperty().addListener((observable, oldValue, newValue) -> {
                if (pistolet.intersects(obstacles.get(k).getBoundsInParent())){
                    pistolet.setX(oldValue.intValue());
                }
            });
            pistolet.yProperty().addListener((observable, oldValue, newValue) -> {
                if (pistolet.intersects(obstacles.get(k).getBoundsInParent())){
                    if (pistolet.intersects(obstacles.get(k).getBoundsInParent())){
                        pistolet.setY(oldValue.intValue());
                    }
                }
            });
        }
        //-------------------------------------------------------------------------------------------------------


        gameScene.getChildren().add(node);
    }




    //INTERSECTION D'UNE BALLS AVEC LES DEMONS ET LES OBSTACLES--------------------------------------------------
    private void ballColusion(Ball b){
        for(int i=0;i<demons.size();i++){
            int k = i;
            b.yProperty().addListener((observable, oldValue, newValue) -> {
                if (!demons.get(k).isDeadProperty.get() && !demons.get(k).isExplosingProperty.get()) {
                    if (b.getBoundsInParent().intersects(demons.get(k).getBoundsInLocal())) {
                        if (!b.blocked.get()) {
                            b.blocked.set(true);
                            demons.get(k).blesser(b.getDegat());
                            b.stop();
                        }
                    }
                }
            });
        }
        for(int i=0;i<obstacles.size();i++){
            int k = i;
            b.yProperty().addListener((observable, oldValue, newValue) -> {
                if (b.getBoundsInParent().intersects(obstacles.get(k).getBoundsInParent())) {
                    if (!b.blocked.get()) {
                        b.blocked.set(true);
                        b.stop();
                        obstacles.playEffect(k);
                    }
                }
            });
        }
        balls.add(b);
        gameScene.getChildren().add(b);
        b.start();
    }
    //-----------------------------------------------------------------------------------------------------------






    public void startGame(){
        Data.getData().backgroundMusic();
        partieEnPause.set(false);
        Thread t = new Thread(demonGenerator);
        t.start();
    }




    public void chargerPartie(){
        try {
            File fXmlFile = new File(GameConfig.gameSaveFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //PISTOLET
            double X = Double.valueOf(doc.getElementsByTagName("pistolet").item(0).getAttributes().item(0).getNodeValue());
            double Y = Double.valueOf(doc.getElementsByTagName("pistolet").item(0).getAttributes().item(1).getNodeValue());
            pistolet = new Pistol((int)X,(int)Y);
            //OBSTACLES
            obstacles = new Obstacle();
            for (int i = 0;i<doc.getElementsByTagName("obstacle").getLength();i++){
                double x = Double.valueOf(doc.getElementsByTagName("obstacle").item(i).getAttributes().item(2).getNodeValue());
                double y = Double.valueOf(doc.getElementsByTagName("obstacle").item(i).getAttributes().item(3).getNodeValue());
                String orientation = doc.getElementsByTagName("obstacle").item(i).getAttributes().item(1).getNodeValue();
                int nbrOfBoxes = Integer.valueOf(doc.getElementsByTagName("obstacle").item(i).getAttributes().item(0).getNodeValue());
                if (orientation.equals("horizontal")){
                    obstacles.addHorizontalBoxes(nbrOfBoxes,(int)x,(int)y);
                }else {
                    obstacles.addVerticalBoxes(nbrOfBoxes,(int)x,(int)y);
                }
                gameScene.getChildren().add(obstacles.get(i));
            }
            //DEMONS
            demons = new ArrayList<>();
            for (int i = 0;i<doc.getElementsByTagName("demon").getLength();i++){
                double x = Double.valueOf(doc.getElementsByTagName("demon").item(i).getAttributes().item(2).getNodeValue());
                double y = Double.valueOf(doc.getElementsByTagName("demon").item(i).getAttributes().item(3).getNodeValue());
                double health = Double.valueOf(doc.getElementsByTagName("demon").item(i).getAttributes().item(0).getNodeValue());
                String Male = doc.getElementsByTagName("demon").item(i).getAttributes().item(1).getNodeValue();
                if (Male.equals("Male")){
                    demons.add(new Demon((int)x,(int)y,true,health));
                }else{
                    demons.add(new Demon((int)x,(int)y,false,health));
                }
                if (health <= 0){
                    demons.get(i).isDeadProperty.set(true);
                }else {
                    demons.get(i).isDeadProperty.set(false);
                }
                demonIntersection(i);
                gameScene.getChildren().add(demons.get(i));
                gameScene.getChildren().add(demons.get(i).getVie().getHealthBar());
                nbrDemonsMorts = new SimpleIntegerProperty(Integer.valueOf(doc.getElementsByTagName("demons").item(0).getAttributes().item(1).getNodeValue()));
                nbrDemonsTotal = new SimpleIntegerProperty(Integer.valueOf(doc.getElementsByTagName("demons").item(0).getAttributes().item(0).getNodeValue()));
            }
            //BALLS
            balls = new ArrayList<>();
            nbrBallsTotal = Integer.valueOf(doc.getElementsByTagName("balls").item(0).getAttributes().item(1).getNodeValue());
            nbrBallsProperty = new SimpleIntegerProperty(Integer.valueOf(doc.getElementsByTagName("balls").item(0).getAttributes().item(0).getNodeValue()));

            partieFini = new SimpleBooleanProperty(false);
            partieEnPause = new SimpleBooleanProperty(true);
            demonsCollisions = new DemonsCollisions();
            demonsIterator = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sauvgarderPartie(){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(GameConfig.gameSaveFilePath);

            //EFFACER LA SAUVEGARDE PRECEDENTE
            while (doc.getElementsByTagName("obstacles").item(0).hasChildNodes()) {
                doc.getElementsByTagName("obstacles").item(0).removeChild(doc.getElementsByTagName("obstacles").item(0).getFirstChild());
            }
            while (doc.getElementsByTagName("demons").item(0).hasChildNodes()) {
                doc.getElementsByTagName("demons").item(0).removeChild(doc.getElementsByTagName("demons").item(0).getFirstChild());
            }
            doc.normalize();


            //PISTOLET
            doc.getElementsByTagName("pistolet").item(0).getAttributes()
                    .item(0).setTextContent(String.valueOf(pistolet.getX()));
            doc.getElementsByTagName("pistolet").item(0).getAttributes()
                    .item(1).setTextContent(String.valueOf(pistolet.getY()));
            //OBSTACLES
            for (Pane obstacle : obstacles) {
                Element ob = doc.createElement("obstacle");
                ob.setAttribute("x", String.valueOf(obstacle.getLayoutX()));
                ob.setAttribute("y", String.valueOf(obstacle.getLayoutY()));
                if (obstacle instanceof Obstacle.horizontalObstacle){
                    ob.setAttribute("orientation", "horizontal");
                    ob.setAttribute("nbrOfBoxes",String.valueOf(((Obstacle.horizontalObstacle)obstacle).getNbrOfBoxes()));
                }else{
                    ob.setAttribute("orientation", "vertical");
                    ob.setAttribute("nbrOfBoxes",String.valueOf(((Obstacle.verticalObstacle)obstacle).getNbrOfBoxes()));
                }

                doc.getElementsByTagName("obstacles").item(0).appendChild(ob);
            }
            //DEMONS
            doc.getElementsByTagName("demons").item(0).getAttributes()
                    .item(0).setTextContent(String.valueOf(demons.size()));
            doc.getElementsByTagName("demons").item(0).getAttributes()
                    .item(1).setTextContent(String.valueOf(nbrDemonsMorts.get()));
            for (Demon demon : demons) {
                Element ob = doc.createElement("demon");
                ob.setAttribute("x", String.valueOf((int) demon.getX()));
                ob.setAttribute("y", String.valueOf((int) demon.getY()));
                ob.setAttribute("health", String.valueOf(demon.getVie().getValue()));

                if (demon.isMale()) {
                    ob.setAttribute("sexe", "Male");
                } else {
                    ob.setAttribute("sexe", "Female");
                }
                doc.getElementsByTagName("demons").item(0).appendChild(ob);
            }
            //BALLS
            doc.getElementsByTagName("balls").item(0).getAttributes()
                    .item(1).setTextContent(String.valueOf(nbrBallsTotal));
            doc.getElementsByTagName("balls").item(0).getAttributes()
                    .item(0).setTextContent(String.valueOf(nbrBallsProperty.get()));

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(GameConfig.gameSaveFilePath));
            transformer.transform(source, result);


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Partie sauvgardé");
            alert.show();


        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void reprendre(){
        partieEnPause.set(false);
        for (Demon demon : demons) {
            if (demon.getY() > 0) {
                demon.start();
            }
        }
        Thread t = new Thread(demonGenerator);
        t.start();
    }


    public void pause(){
        partieEnPause.set(true);
        for (Demon demon : demons) {
            if (demon.getY() > 0) {
                demon.stop();
            }
        }
    }

    private void demonIntersection(int k){
        //LORSQUE UN DEMON TOUCHE LE PISTOLET, LE PISTOLET S'EXPLOSE
        demons.get(k).yProperty().addListener((observable, oldValue, newValue) -> {
            if (demons.get(k).intersects(pistolet)) {
                if (!pistolet.deadProperty.get()){
                    demons.get(k).stop();
                    pistolet.tuer();
                }
            }
        });
        //LORSQUE UN DEMON EST TUEE, INCREMENTER LE NBR DE DEMONS MORTS
        demons.get(k).isDeadProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                nbrDemonsMorts.set(nbrDemonsMorts.get() + 1);
                demons.get(k).yProperty().set(-500);
                gameScene.getChildren().remove(demons.get(k));
                gameScene.getChildren().remove(demons.get(k).getVie().getHealthBar());
            }
        });
        //LORSQUE LE PISTOLET TOUCHE UN DEMON, LE PISTOLET S'EXPLOSE
        pistolet.xProperty().addListener((observable, oldValue, newValue) -> {
            if (pistolet.intersects(demons.get(k).getBoundsInParent())) {
                if (!pistolet.deadProperty.get()) {
                    if (!demons.get(k).isExplosingProperty.get()){
                        pistolet.tuer();
                        demons.get(k).stop();
                    }
                }
            }
        });
        //LORSQUE LE PISTOLET TOUCHE UN DEMON, LE PISTOLET S'EXPLOSE
        pistolet.yProperty().addListener((observable, oldValue, newValue) -> {
            if (pistolet.intersects(demons.get(k).getBoundsInParent())) {
                if (!pistolet.deadProperty.get()) {
                    if (!demons.get(k).isExplosingProperty.get()){
                        pistolet.tuer();
                        demons.get(k).stop();
                    }
                }
            }
        });
        //LORSQUE UN DEMON TOUCHE UN AUTRE DEMON
        for(int j=0;j<demons.size();j++){
            int n = j;
            if (k!=j){
                demons.get(k).xProperty().addListener((observable, oldValue, newValue) -> {
                    if (demons.get(k).getY()>0 && demons.get(n).getY()>0) {
                        if (demons.get(k).intersects(demons.get(n))) {
                            if (demons.get(k).isMale() && demons.get(n).isMale()) {
                                if (!demons.get(k).isExplosingProperty.get()) {
                                    if (!demonsCollisions.exists(k, n)) {
                                        demonsCollisions.add(k, n);
                                        demonsCollisions.destroyOneDemon(demons);
                                    }
                                }
                            } else if ((demons.get(k).isMale() && !demons.get(n).isMale()) || (demons.get(n).isMale() && !demons.get(k).isMale())) {
                                if (!demons.get(k).isExplosingProperty.get() && !demons.get(n).isExplosingProperty.get()) {
                                    if (!demonsCollisions.exists(k, n)) {
                                        demonsCollisions.add(k, n);
                                        int x = ((int) demons.get(k).getX() + (int) demons.get(n).getX()) / 2;
                                        int y = ((int) demons.get(k).getY() + (int) demons.get(n).getY()) / 2;
                                        demons.get(k).resetPosition();
                                        demons.get(n).resetPosition();


                                        if (Main.randomInt(0, 1) == 1) {
                                            demons.add(new Demon(x, y, true, 100));
                                        } else {
                                            demons.add(new Demon(x, y, false, 100));
                                        }
                                        nbrDemonsTotal.set(nbrDemonsTotal.get() + 1);
                                        gameScene.getChildren().add(demons.get(demons.size() - 1));
                                        gameScene.getChildren().add(demons.get(demons.size() - 1).getVie().getHealthBar());
                                        demonIntersection(demons.size() - 1);
                                        demons.get(demons.size() - 1).start();
                                        demonsCollisions.remove(k, n);
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
        //LORSQUE UN DEMON TOUCHE UN OBSTACLE IL CHANGE LA DIRECTION
        for (int j = 0;j<obstacles.size();j++){
            int s = j;

            demons.get(k).yProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (demons.get(k).intersects(obstacles.get(s).getBoundsInParent())) {
                        if (demons.get(k).obstacleColisionIndex != s) {
                            demons.get(k).obstacleColisionIndex = s;
                            if (!demons.get(k).changingDirection.get()) {
                                obstacles.playEffect(s);
                                if ((obstacles.get(s).getLayoutY() > demons.get(k).getY())) {//COLLISION AU DESSUS
                                    if (Math.abs(demons.get(k).getFitHeight() - (obstacles.get(s).getLayoutY() - demons.get(k).getY())) <= 4) {
                                        demons.get(k).changeYdirection();
                                    }
                                }
                                if ((obstacles.get(s).getLayoutY() + obstacles.get(s).getHeight()) - demons.get(k).getY() <= 3) {//COLLISION AU DESSOUS
                                    if (obstacles.get(s).getHeight() - (demons.get(k).getY() - obstacles.get(s).getLayoutY()) <= 3) {
                                        demons.get(k).changeYdirection();
                                    }
                                }
                                if (obstacles.get(s).getLayoutX() > demons.get(k).getX()) {//COLLISION A GAUCHE
                                    if (Math.abs(demons.get(k).getFitWidth() - (obstacles.get(s).getLayoutX() - demons.get(k).getX())) <= 4) {
                                        demons.get(k).changeXdirection();
                                    }
                                }
                                if ((obstacles.get(s).getLayoutX() + obstacles.get(s).getWidth()) - demons.get(k).getX() <= 3) {//COLLISION A DROITE
                                    if (obstacles.get(s).getWidth() - (demons.get(k).getX() - obstacles.get(s).getLayoutX()) <= 3) {
                                        demons.get(k).changeXdirection();
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
        //-------------------------------------------------------------------------------------------------------
    }


}