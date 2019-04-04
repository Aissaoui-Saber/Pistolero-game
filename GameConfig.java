package sample;

import com.sun.glass.events.KeyEvent;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
import java.util.EnumSet;

public class GameConfig {
    private Key upKey;
    private Key downKey;
    private Key leftKey;
    private Key rightKey;
    private Key fireKey;
    private Key pauseKey;
    private static final String configFilePath = "src/sample/Config.xml";

    private GameConfig(){
        try {
            File fXmlFile = new File(configFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            String nodeValue;
            nodeValue = doc.getElementsByTagName("up").item(0).getTextContent();
            for (KeyCode keyCode : KeyCode.values()) {
                if (keyCode.getName().equals(nodeValue)){
                    upKey = new Key(KeyCode.getKeyCode(nodeValue));
                    break;
                }
            }
            nodeValue = doc.getElementsByTagName("down").item(0).getTextContent();
            for (KeyCode keyCode : KeyCode.values()) {
                if (keyCode.getName().equals(nodeValue)){
                    downKey = new Key(KeyCode.getKeyCode(nodeValue));
                    break;
                }
            }
            nodeValue = doc.getElementsByTagName("left").item(0).getTextContent();
            for (KeyCode keyCode : KeyCode.values()) {
                if (keyCode.getName().equals(nodeValue)){
                    leftKey = new Key(KeyCode.getKeyCode(nodeValue));
                    break;
                }
            }
            nodeValue = doc.getElementsByTagName("right").item(0).getTextContent();
            for (KeyCode keyCode : KeyCode.values()) {
                if (keyCode.getName().equals(nodeValue)){
                    rightKey = new Key(KeyCode.getKeyCode(nodeValue));
                    break;
                }
            }
            nodeValue = doc.getElementsByTagName("fire").item(0).getTextContent();
            for (KeyCode keyCode : KeyCode.values()) {
                if (keyCode.getName().equals(nodeValue)){
                    fireKey = new Key(KeyCode.getKeyCode(nodeValue));
                    break;
                }
            }
            nodeValue = doc.getElementsByTagName("pause").item(0).getTextContent();
            for (KeyCode keyCode : KeyCode.values()) {
                if (keyCode.getName().equals(nodeValue)){
                    pauseKey = new Key(KeyCode.getKeyCode(nodeValue));
                    break;
                }
            }
        } catch (Exception e) {
            upKey = new Key(KeyCode.UP);
            downKey = new Key(KeyCode.DOWN);
            leftKey = new Key(KeyCode.LEFT);
            rightKey = new Key(KeyCode.RIGHT);
            fireKey = new Key(KeyCode.SPACE);
            pauseKey = new Key(KeyCode.ESCAPE);
        }
    }
    public void saveChanges(){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(configFilePath);

            doc.getElementsByTagName("up").item(0).setTextContent(upKey.getCode().getName());
            doc.getElementsByTagName("down").item(0).setTextContent(downKey.getCode().getName());
            doc.getElementsByTagName("left").item(0).setTextContent(leftKey.getCode().getName());
            doc.getElementsByTagName("right").item(0).setTextContent(rightKey.getCode().getName());
            doc.getElementsByTagName("fire").item(0).setTextContent(fireKey.getCode().getName());
            doc.getElementsByTagName("pause").item(0).setTextContent(pauseKey.getCode().getName());

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(configFilePath));
            transformer.transform(source, result);

            System.out.println("Saved");

        } catch (ParserConfigurationException pce) {

        } catch (TransformerException tfe) {

        } catch (IOException ioe) {

        } catch (SAXException sae) {

        }catch (Exception e){

        }
    }

    private static GameConfig instance = new GameConfig();

    public static GameConfig getInstance(){
        if (instance != null){
            return instance;
        }else {
            return new GameConfig();
        }
    }

    public Key getUpKey() {
        return upKey;
    }
    public Key getDownKey(){
        return downKey;
    }

    public Key getLeftKey() {
        return leftKey;
    }

    public Key getRightKey() {
        return rightKey;
    }

    public Key getFireKey() {
        return fireKey;
    }

    public Key getPauseKey() {
        return pauseKey;
    }


    public void setPauseKey(KeyCode pauseKey) {
        this.pauseKey.setCode(pauseKey);
    }

    public void setUpKey(KeyCode upKey) {
        this.upKey.setCode(upKey);
    }

    public void setDownKey(KeyCode downKey) {
        this.downKey.setCode(downKey);
    }

    public void setLeftKey(KeyCode leftKey) {
        this.leftKey.setCode(leftKey);
    }

    public void setRightKey(KeyCode rightKey) {
        this.rightKey.setCode(rightKey);
    }

    public void setFireKey(KeyCode fireKey) {
        this.fireKey.setCode(fireKey);
    }
}
